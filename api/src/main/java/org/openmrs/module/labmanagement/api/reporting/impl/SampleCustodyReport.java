package org.openmrs.module.labmanagement.api.reporting.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.labmanagement.api.dto.SampleActivityDTO;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItem;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.SampleActivityType;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.reporting.ReportGenerator;
import org.openmrs.module.labmanagement.api.reporting.ReportParameter;
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

public class SampleCustodyReport extends ReportGenerator {

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
		String testerUuid = getTester(parameters);
		String referenceNumber = getReportParameterString(parameters, ReportParameter.ReferenceNumber);

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

			if (!StringUtils.isBlank(testerUuid)) {
				User user = Context.getUserService().getUserByUuid(testerUuid);
				if (user == null) {
					labManagementService.failBatchJob(batchJob.getUuid(), "Report tester parameter not found");
					return;
				}
				filter.setTesterUserId(user.getId());
			}

			if(StringUtils.isNotBlank(referenceNumber)) {
				filter.setReferenceNumber(referenceNumber);
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

				Result<TestRequestReportItem> data = labManagementService.findSampleCustodyReportItems(filter);

				if (shouldStopExecution.apply(batchJob)) {
					return;
				}

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

					int fetchIndex = 0;
					int rowIndex = 0;
					int batchSize = 50;
					Map<Integer, List<SampleActivityDTO>> sampleActivityMap = null;
					for (TestRequestReportItem row : data.getData()) {
						if(fetchIndex == rowIndex){
						 	List<Integer> sampleIds =	data.getData().stream().skip(rowIndex).limit(batchSize).map(TestRequestReportItem::getSampleId).distinct().collect(Collectors.toList());
							sampleActivityMap = labManagementService.getSampleActivitiesForReport(sampleIds).stream().collect(Collectors.groupingBy(SampleActivityDTO::getSampleId));
						 	fetchIndex = fetchIndex + batchSize;
						}

						writeRow(csvWriter, row, sampleActivityMap);
						rowIndex++;
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


	protected void writeRow(CSVWriter csvWriter, TestRequestReportItem row, Map<Integer, List<SampleActivityDTO>> sampleActivityMap) throws IOException {


	List<String> rowColumns = new ArrayList<>( Arrays.asList(TIMESTAMP_FORMATTER.format(row.getDateCreated()),
				formatName(row.getCreatorFamilyName(), row.getCreatorMiddleName(), row.getCreatorGivenName()),
				row.getAtLocationName(),
				row.getRequestNo(),
				row.getOrderNumber(),
				row.getReferredIn() != null && row.getReferredIn() ? "Referral" : "Patient",
				row.getReferredIn() != null && row.getReferredIn() ? row.getReferralFromFacilityName() :
						formatName(row.getPatientFamilyName(), row.getPatientMiddleName(), row.getPatientGivenName()),
				row.getReferredIn() != null && row.getReferredIn() ? row.getReferralInExternalRef() : row.getPatientIdentifier(),
				formatTestName(row.getTestName(), row.getTestShortName()),
				//row.getRequireRequestApproval() != null && row.getRequireRequestApproval()  ? "Yes" : row.getRequireRequestApproval() != null ? "No" : "",
				formatName(row.getRequestApprovalFamilyName(), row.getRequestApprovalMiddleName(), row.getRequestApprovalGivenName()),
				row.getRequestApprovalDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getRequestApprovalDate()),
				row.getSampleTypeName(),
				row.getSampleAccessionNumber(),
				row.getSampleProvidedRef(),
				row.getSampleExternalRef(),
				row.getCollectionDate() == null ?  null : TIMESTAMP_FORMATTER.format(row.getCollectionDate()),
				formatName(row.getCollectedByFamilyName(), row.getCollectedByMiddleName(), row.getCollectedByGivenName()),
				row.getSampleAtLocationName(),
				row.getReferredOut() != null && row.getReferredOut() ? "Yes" : row.getReferredOut() != null ? "No" : "",
				formatName(row.getReferralOutByFamilyName(), row.getReferralOutByMiddleName(), row.getReferralOutByGivenName()),
				row.getReferralToFacilityName(),
				row.getReferralOutDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getReferralOutDate()),
				row.getWorksheetAtLocationName() != null ? row.getWorksheetAtLocationName() : row.getResultAtLocationName() != null ? row.getResultAtLocationName() :  row.getSampleAtLocationName() ,
				formatName(row.getResultByFamilyName(), row.getResultByMiddleName(), row.getResultByGivenName()),
				row.getResultDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getResultDate()),
				//row.getResultRequireApproval() != null &&  row.getResultRequireApproval() ? "Yes" : row.getResultRequireApproval() != null ? "No" : "",
				formatName(row.getCurrentApprovalByFamilyName(), row.getCurrentApprovalByMiddleName(), row.getCurrentApprovalByGivenName()),
				row.getResultApprovalDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getResultApprovalDate())
		));

		List<SampleActivityDTO> sampleActivityDTOS = sampleActivityMap == null ? new ArrayList<>() :
				sampleActivityMap.getOrDefault(row.getSampleId(), new ArrayList<>());
		Optional<SampleActivityDTO> archiveActivity = sampleActivityDTOS.stream().filter(p->p.getActivityType() == SampleActivityType.ARCHIVE).reduce((x,y)-> y);
		addActivityColumns(rowColumns, archiveActivity);

		Optional<SampleActivityDTO> checkoutActivity = sampleActivityDTOS.stream().filter(p->p.getActivityType() == SampleActivityType.CHECKOUT).reduce((x,y)-> y);
		addActivityColumns(rowColumns, checkoutActivity);

		Optional<SampleActivityDTO> disposeActivity = sampleActivityDTOS.stream().filter(p->p.getActivityType() == SampleActivityType.DISPOSAL).reduce((x,y)-> y);
		addActivityColumns(rowColumns, disposeActivity);
		rowColumns.add(
		sampleActivityDTOS.stream().filter(p-> (!archiveActivity.isPresent() || !archiveActivity.get().getId().equals(p.getSampleId())) &&
						(!checkoutActivity.isPresent() || !checkoutActivity.get().getId().equals(p.getSampleId())) &&
						(!disposeActivity.isPresent() || !disposeActivity.get().getId().equals(p.getSampleId()))
		).map(p->
			String.format("Activity: %1s%2sDate: %3s%4sEntry Date: %5s%6sBy: %7s%8sResponsible Person: %9s%10s",
			p.getActivityType().name(),
			"\r\n",
			DATE_FORMATTER.format(p.getActivityDate() == null ? p.getDateCreated() : p.getActivityDate()),
					"\r\n",
			TIMESTAMP_FORMATTER.format(p.getDateCreated()),
					"\r\n",
			formatName(p.getActivityByFamilyName(),
					p.getActivityByMiddleName(),
					p.getActivityByGivenName()),
					"\r\n",
			p.getResponsiblePersonOther() != null ? p.getResponsiblePersonOther() :
					formatName(p.getActivityByFamilyName(),
							p.getActivityByMiddleName(),
							p.getActivityByGivenName()),
					"\r\n")
		).collect(Collectors.joining("\r\n"))
		);
		writeLineToCsv(csvWriter, rowColumns.toArray(new String[0]));
	}

	private void addActivityColumns(List<String> rowColumns, Optional<SampleActivityDTO> disposeActivity) {
		if(disposeActivity.isPresent()) {
			rowColumns.add(DATE_FORMATTER.format(disposeActivity.get().getActivityDate() == null ?
					disposeActivity.get().getDateCreated() : disposeActivity.get().getActivityDate()));
			rowColumns.add(TIMESTAMP_FORMATTER.format(disposeActivity.get().getDateCreated()));
			rowColumns.add(formatName(disposeActivity.get().getActivityByFamilyName(),
					disposeActivity.get().getActivityByMiddleName(),
					disposeActivity.get().getActivityByGivenName()));
			rowColumns.add(disposeActivity.get().getResponsiblePersonOther() != null ? disposeActivity.get().getResponsiblePersonOther() :
					formatName(disposeActivity.get().getActivityByFamilyName(),
							disposeActivity.get().getActivityByMiddleName(),
							disposeActivity.get().getActivityByGivenName()));

		}else{
			rowColumns.add(null);
			rowColumns.add(null);
			rowColumns.add(null);
			rowColumns.add(null);
		}
	}

	protected void writeHeaders(CSVWriter csvWriter) {

		List<String> headers = new ArrayList<>(Arrays.asList("Date Created", "Created By",
				"Location",
				"Request Number",
				"Order Number",
				"Type",
				"Entity",
				"Identity",
				"Test",
				"Request Approved By",
				"Request Approved Date",
				"Sample Type",
				"Sample ID",
				"Provided ID",
				"External ID",
				"Collection Date",
				"Collected By",
				"Collected At",
				"Referred Out",
				"Referred Out By",
				"Reference Location",
				"Referred Date",
				"Diagnostic Center",
				"Results By",
				"Results Date",
				"Results Last Approved By",
				"Results Last Approved Date",

				"Archive Date",
				"Archive Entry Date",
				"Archive By",
				"Archive Responsible Person",

				"Check-Out Date",
				"Check-Out Entry Date",
				"Check-Out By",
				"Check-Out Responsible Person",

				"Disposal Date",
				"Disposal Entry Date",
				"Disposal By",
				"Disposal Responsible Person",

				"Other Activity"
				));
		writeLineToCsv(csvWriter, headers.toArray(new String[0]));
	}
}
