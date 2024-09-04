package org.openmrs.module.labmanagement.api.reporting.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.ObsDto;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItem;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.reporting.ObsValue;
import org.openmrs.module.labmanagement.api.reporting.ReportGenerator;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class TestRegisterReport extends ReportGenerator {

	protected final Log log = LogFactory.getLog(this.getClass());

	protected LabManagementService labManagementService = null;

	protected BatchJob batchJob = null;
	protected Concept testConcept = null;

	@Override
	public void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
		this.batchJob = batchJob;
		labManagementService = Context.getService(LabManagementService.class);
		Integer pageSize = GlobalProperties.GetReportingRecordsPageSize();

		if (!restoreExecutionState(batchJob, labManagementService, log)) {
			return;
		}

		if (shouldStopExecution.apply(batchJob)) {
			return;
		}

		try {
			parameters =  parameters == null ? GenericObject.parseJson(batchJob.getParameters()) : parameters;
		}
		catch (Exception e) {
			labManagementService.failBatchJob(batchJob.getUuid(), "Failed to read parameters");
			log.error(e.getMessage(), e);
			return;
		}

		Date startDate = getStartDate(parameters);
		Date endDate = getEndDate(parameters);
		String diagnosticLocationUuid = getDiagnosticLocation(parameters);
		String testTypeUuid = getTestType(parameters);
		ObsValue testOutcome = getTestOutcome(parameters);
		String patientUuid = getPatient(parameters);
		String referralLocationUuid = getReferralLocation(parameters);
		String testApproverUuid = getTestApprover(parameters);

		CSVWriter csvWriter = null;
		Writer writer = null;

		try {
			boolean hasMoreRecords = true;
			TestRequestReportItemFilter filter = new TestRequestReportItemFilter();
			filter.setStartDate(startDate);
			if (endDate != null) {
				filter.setEndDate(DateUtil.endOfDay(endDate));
			}

			if (!StringUtils.isBlank(diagnosticLocationUuid)) {
				Location location = Context.getLocationService().getLocationByUuid(diagnosticLocationUuid);
				if (location == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report diagnostic location parameter not found");
					return;
				}
				filter.setDiagonisticLocationId(location.getId());
			}

			if (!StringUtils.isBlank(patientUuid)) {
				Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);
				if (patient == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report patient parameter not found");
					return;
				}
				filter.setPatientId(patient.getId());
			}

			if (!StringUtils.isBlank(referralLocationUuid)) {
				ReferralLocation referralLocation = labManagementService.getReferralLocationByUuid(referralLocationUuid);
				if (referralLocation == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report reference location parameter not found");
					return;
				}
				filter.setReferralLocationId(referralLocation.getId());
			}


			if (!StringUtils.isBlank(testTypeUuid)) {
				testConcept = Context.getConceptService().getConceptByUuid(testTypeUuid);
				if (testConcept == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report test type parameter not found");
					return;
				}
				filter.setTestConceptId(testConcept.getId());
			}

			if(testOutcome != null && StringUtils.isNotBlank(testOutcome.getConceptUuid())) {
				Concept concept = Context.getConceptService().getConceptByUuid(testOutcome.getConceptUuid());
				if(concept == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report test outcome concept parameter not found");
					return;
				}
				if(testConcept == null){
					testConcept = concept;
				}else if(!testConcept.getId().equals(concept.getId())){
					labManagementService.failBatchJob(batchJob.getUuid(), "Report test type and outcome concept parameter do not match");
					return;
				}
				filter.setObsValue(testOutcome);
			}


			if (!StringUtils.isBlank(testApproverUuid)) {
				User user = Context.getUserService().getUserByUuid(testApproverUuid);
				if (user == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report test approver parameter not found");
					return;
				}
				filter.setApproverUserId(user.getId());
			}

			filter.setLimit(pageSize);
			setFilters(filter, parameters);

			boolean hasAppendedHeaders = false;
			while (hasMoreRecords) {
				if (shouldStopExecution.apply(batchJob)) {
					return;
				}
				filter.setStartIndex(pageIndex);
				filter.setTestRequestItemIdMin(lastRecordProcessed);
				filter.setTestRequestIdMin(lastTestRequestProcessed);

				Result<TestRequestReportItem> data = labManagementService.findTestRequestReportItems(filter);
				if (shouldStopExecution.apply(batchJob)) {
					return;
				}
				Map<Integer, List<ObsDto>> observations =  labManagementService.getObservations(data.getData().stream()
						.map(TestRequestReportItem::getOrderId).distinct().collect(Collectors.toList()));

				if (shouldStopExecution.apply(batchJob)) {
					return;
				}

				if (!hasAppendedHeaders) {
					if (writer == null) {
						writer = Files.newBufferedWriter(resultsFile.toPath(), StandardCharsets.UTF_8,
						    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					}
					if (csvWriter == null) {
						csvWriter = createCsvWriter(writer);
					}
					if (!hasRestoredExecutionState) {
						writeHeaders(csvWriter);
					}
					hasAppendedHeaders = true;
				}
				if (!data.getData().isEmpty()) {
					for (TestRequestReportItem row : data.getData()) {
						writeRow(csvWriter, row, observations);
					}
					csvWriter.flush();
					recordsProcessed += data.getData().size();
					pageIndex++;
					TestRequestReportItem lastRecord = data.getData().get(data.getData().size() - 1);
					updateExecutionState(batchJob, executionState, pageIndex, recordsProcessed, lastRecord.getTestRequestItemId(), lastRecord.getTestRequestId(), labManagementService, null);
				} else if (pageIndex == 0) {
					updateExecutionState(batchJob, executionState, pageIndex, recordsProcessed, null, null,
					    labManagementService, null);
				}

				hasMoreRecords = data.getData().size() >= pageSize;
			}

			csvWriter.close();
			long fileSizeInBytes = Files.size(resultsFile.toPath());
			completeBatchJob(batchJob, fileSizeInBytes, "csv", fileSizeInBytes <= (1024 * 1024), labManagementService);
		}
		catch (IOException e) {
			labManagementService.failBatchJob(batchJob.getUuid(), "Input/Output error: " + e.getMessage());
			log.error(e.getMessage(), e);
		}
		finally {
			if (csvWriter != null) {
				try {
					try {
						csvWriter.flush();
					}
					catch (Exception e) {}
					csvWriter.close();
				}
				catch (Exception csvWriterException) {}
			}
			if (writer != null) {
				try {
					try {
						writer.flush();
					}
					catch (Exception e) {}
					writer.close();
				}
				catch (Exception we) {}
			}
		}

	}


	protected abstract void setFilters(TestRequestReportItemFilter filter, GenericObject parameters);

	protected abstract void writeRow(CSVWriter csvWriter, TestRequestReportItem row, Map<Integer, List<ObsDto>> observations);

	protected abstract void writeHeaders(CSVWriter csvWriter);
}
