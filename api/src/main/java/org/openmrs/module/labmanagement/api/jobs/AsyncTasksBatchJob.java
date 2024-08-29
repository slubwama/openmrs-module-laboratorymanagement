package org.openmrs.module.labmanagement.api.jobs;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.BatchJobType;
import org.openmrs.module.labmanagement.api.reporting.Report;
import org.openmrs.module.labmanagement.api.reporting.ReportGenerator;
import org.openmrs.module.labmanagement.api.utils.FileUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.SchedulerService;
import org.openmrs.scheduler.TaskDefinition;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncTasksBatchJob extends AbstractTask {

	private static final String TaskName = "Lab Asynchronous Batch Jobs";

	private static AtomicBoolean isAlreadyRunning = new AtomicBoolean(false);

	protected final Log log = LogFactory.getLog(this.getClass());

	private static final Queue<String> stopExecutionQueue = new ConcurrentLinkedQueue<String>();

	private volatile boolean shutDown = false;

	private volatile Integer idleTicks = 0;

	private List<Report> allReports = null;

	@Override
	public void execute() {
		shutDown = false;
		if (isAlreadyRunning.get()) {
			log.debug("Async tasks job is already running");
			return;
		}
		try {
			if (isAlreadyRunning.getAndSet(true)) {
				log.debug("Async tasks job is already running");
				return;
			}
			startExecuting();
			executeInternal();
		}
		catch (Exception exception) {
			log.error("Error occurred while executing Async tasks batch job");
			log.error(exception.getMessage(), exception);
		}
		finally {
			isAlreadyRunning.set(false);
			stopExecuting();
		}
	}

	protected void executeInternal() {
		LabManagementService labManagementService = Context.getService(LabManagementService.class);
		String previousBatchJobUuid = null;
		int previousBatchJobSeenCount = 0;
		while (!shutDown) {
			BatchJob batchJob = labManagementService.getNextActiveBatchJob();
			if (batchJob == null) {
				idleTicks++;
				if (idleTicks >= 5) {
					stopThisJob();
				} else if (idleTicks.equals(1)) {
					cleanUpExpiredJobs();
				}
				break;
			}
			idleTicks = 0;
			if (previousBatchJobUuid == null || !previousBatchJobUuid.equals(batchJob.getUuid())) {
				previousBatchJobSeenCount = 0;
				previousBatchJobUuid = batchJob.getUuid();
			} else {
				if (previousBatchJobSeenCount < 4) {
					previousBatchJobSeenCount++;
					previousBatchJobUuid = batchJob.getUuid();
				} else {
					throw new LabManagementException(String.format(
					    Context.getMessageSourceService().getMessage("labmanagement.batchjob.samebatchjobseen"),
					    batchJob.getDescription(), batchJob.getUuid(), Integer.toString(previousBatchJobSeenCount)));
				}
			}

			try {
				executeBatchJob(batchJob, labManagementService);
			}
			catch (Exception exception) {
				log.error(exception.getMessage(), exception);
				try {
					labManagementService.failBatchJob(batchJob.getUuid(), String.format(Context.getMessageSourceService()
					        .getMessage("labmanagement.batchjob.reportunexpectederror"), exception.getMessage()));
				}
				catch (Exception silentException) {
					log.error(silentException.getMessage(), silentException);
				}
			}
		}
	}

	private void executeBatchJob(BatchJob batchJob, LabManagementService labManagementService) {
		// Check if the batch job is expired
		if (batchJob.getExpiration() != null && (new Date()).after(batchJob.getExpiration())) {
			labManagementService.expireBatchJob(batchJob.getUuid(),
			    Context.getMessageSourceService().getMessage("labmanagement.batchjob.batchjobexpired"));
			return;
		}

		if (BatchJobType.Migration.equals(batchJob.getBatchJobType())) {
			executeDataMigrationBatchJob(batchJob, labManagementService);
		}

		if (!BatchJobType.Report.equals(batchJob.getBatchJobType())) {
			labManagementService.failBatchJob(batchJob.getUuid(),
			    Context.getMessageSourceService().getMessage("labmanagement.batchjob.batchjobnotsupported"));
			return;
		}

		executeReportBatchJob(batchJob, labManagementService);
	}

	private void executeReportBatchJob(BatchJob batchJob, LabManagementService labManagementService) {
		Properties properties = null;
		try {
			properties = GlobalProperties.fromString(batchJob.getParameters());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			labManagementService.failBatchJob(batchJob.getUuid(),
					Context.getMessageSourceService().getMessage("labmanagement.batchjob.parameterformatnotvalid"));
			return;
		}

		if (allReports == null) {
			allReports = labManagementService.getReports();
		}

		String reportSystemName = properties.getProperty("param.report");
		if (StringUtils.isBlank(reportSystemName)) {
			labManagementService.failBatchJob(batchJob.getUuid(),
					String.format(
							Context.getMessageSourceService().getMessage("labmanagement.batchjob.fieldvaluenotexist"),
							"report system name"));
			return;
		}

		Optional<Report> report = allReports.stream().filter(p -> reportSystemName.equals(p.getSystemName())).findAny();
		if (!report.isPresent()) {
			labManagementService.failBatchJob(batchJob.getUuid(),
					String.format(
							Context.getMessageSourceService().getMessage("labmanagement.batchjob.fieldvaluenotexist"),
							"report by system name"));
			return;
		}

		ReportGenerator reportGenerator = null;
		try {
			reportGenerator = (ReportGenerator) report.get().getReportGeneratorClass().newInstance();
		} catch (Exception exception) {
			log.error(exception.getMessage(), exception);
			labManagementService
					.failBatchJob(batchJob.getUuid(),
							String.format(
									Context.getMessageSourceService()
											.getMessage("labmanagement.batchjob.failedtocreatereportgenerator"),
									exception.getMessage()));
			return;
		}

		labManagementService.updateBatchJobRunning(batchJob.getUuid());

		reportGenerator.execute(batchJob, p -> {
			String batchJobToStop = null;
			if (shutDown) {
				stopExecutionQueue.clear();
				return true;
			}
			boolean result = false;
			while ((batchJobToStop = stopExecutionQueue.poll()) != null) {
				if (batchJobToStop.equals(p.getUuid())) {
					result = true;
					break;
				}
			}
			stopExecutionQueue.clear();
			return result;
		});
	}

	private void executeDataMigrationBatchJob(BatchJob batchJob, LabManagementService labManagementService) {
		Properties properties = null;
		try {
			if(StringUtils.isNotBlank(batchJob.getParameters())) {
				properties = GlobalProperties.fromString(batchJob.getParameters());
			}else{
				properties=new Properties();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			labManagementService.failBatchJob(batchJob.getUuid(),
					Context.getMessageSourceService().getMessage("labmanagement.batchjob.parameterformatnotvalid"));
			return;
		}

		labManagementService.updateBatchJobRunning(batchJob.getUuid());
		DataMigrationJob dataMigrationJob = new DataMigrationJob();
		dataMigrationJob.execute(batchJob, p -> {
			String batchJobToStop = null;
			if (shutDown) {
				stopExecutionQueue.clear();
				return true;
			}
			boolean result = false;
			while ((batchJobToStop = stopExecutionQueue.poll()) != null) {
				if (batchJobToStop.equals(p.getUuid())) {
					result = true;
					break;
				}
			}
			stopExecutionQueue.clear();
			return result;
		});
	}

	@Override
	public void shutdown() {
		super.shutdown();
		shutDown = true;
	}

	public static boolean stopBatchJob(BatchJob batchJob) {
		stopExecutionQueue.add(batchJob.getUuid());
		return true;
	}

	protected void stopThisJob() {
		SchedulerService schedulerService = Context.getSchedulerService();
		TaskDefinition taskDefinition = schedulerService.getTaskByName(TaskName);
		if (taskDefinition != null) {
			try {
				schedulerService.shutdownTask(taskDefinition);
			}
			catch (SchedulerException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void queueBatchJob(BatchJob batchJob) {
		SchedulerService schedulerService = Context.getSchedulerService();
		TaskDefinition taskDefinition = schedulerService.getTaskByName(TaskName);
		if (taskDefinition != null) {
			schedulerService.scheduleIfNotRunning(taskDefinition);
		}
	}

	private void silentDelete(File file) {
		try {
			if (file.exists()) {
				file.delete();
			}
		}
		catch (Exception exception) {
			log.error(exception.getMessage(), exception);
		}
	}

	public void cleanUpExpiredJobs() {
		LabManagementService labManagementService = Context.getService(LabManagementService.class);
		List<BatchJob> batchJobs = labManagementService.getExpiredBatchJobs();
		for (BatchJob batchJob : batchJobs) {
			File file = new File(FileUtil.getBatchJobFolder(), batchJob.getUuid());
			silentDelete(file);
			file = new File(FileUtil.getBatchJobFolder(), batchJob.getUuid() + ".step1");
			silentDelete(file);
			labManagementService.deleteBatchJob(batchJob);
		}
	}

}
