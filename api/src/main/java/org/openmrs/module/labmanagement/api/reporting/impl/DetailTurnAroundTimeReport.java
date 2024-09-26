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
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.ReferralLocation;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.reporting.ObsValue;
import org.openmrs.module.labmanagement.api.reporting.ReportGenerator;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.TurnAroundTimeCalculator;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DetailTurnAroundTimeReport extends ReportGenerator {

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
		String patientUuid = getPatient(parameters);
		String referralLocationUuid = getReferralLocation(parameters);
		String testerUuid = getTester(parameters);

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


			if (!StringUtils.isBlank(testerUuid)) {
				User user = Context.getUserService().getUserByUuid(testerUuid);
				if (user == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report tester parameter not found");
					return;
				}
				filter.setTesterUserId(user.getId());
			}

			filter.setLimit(pageSize);

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

	protected void writeRow(CSVWriter csvWriter, TestRequestReportItem row, Map<Integer, List<ObsDto>> observations) {

		Long turnAroundTime = TurnAroundTimeCalculator.getTurnAroundTime(
				getTurnAroundStartDate(row.getCollectionDate(),row.getRequestApprovalDate() ,row.getDateCreated()),
		row.getResultDate() != null ? row.getResultDate() : row.getCompletedDate());

		writeLineToCsv(csvWriter, TIMESTAMP_FORMATTER.format(row.getDateCreated()),
				row.getRequestNo(),
				row.getOrderNumber(),
				row.getReferredIn() != null && row.getReferredIn() ? "Referral" : "Patient",
				row.getReferredIn() != null && row.getReferredIn() ? row.getReferralFromFacilityName() :
						formatName(row.getPatientFamilyName(), row.getPatientMiddleName(), row.getPatientGivenName()),
				row.getReferredIn() != null && row.getReferredIn() ? row.getReferralInExternalRef() : row.getPatientIdentifier(),
				formatTestName(row.getTestName(), row.getTestShortName()),
				formatName(row.getResultByFamilyName(), row.getResultByMiddleName(), row.getResultByGivenName()),
				TurnAroundTimeCalculator.formatTurnAroundTime(turnAroundTime),
				String.format("%.0f", Math.floor((double) turnAroundTime / 60000))
				);
	}


	protected void writeHeaders(CSVWriter csvWriter) {
		writeLineToCsv(csvWriter, "Date Created",
				"Request Number",
				"Order Number",
				"Type",
				"Entity",
				"Identity",
				"Test",
				"Results By",
				"Turn Around Time",
				"Turn Around Time Minutes");
	}
}
