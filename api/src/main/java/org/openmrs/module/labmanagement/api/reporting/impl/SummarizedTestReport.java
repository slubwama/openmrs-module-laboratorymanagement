package org.openmrs.module.labmanagement.api.reporting.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.reporting.ReportGenerator;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SummarizedTestReport extends ReportGenerator {

    protected final Log log = LogFactory.getLog(this.getClass());

    protected LabManagementService labManagementService = null;

    protected BatchJob batchJob = null;

    @Override
    public void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
        this.batchJob = batchJob;
        labManagementService = Context.getService(LabManagementService.class);

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

        CSVWriter csvWriter = null;
        Writer writer = null;

        try {
            if (endDate != null) {
                endDate = (DateUtil.endOfDay(endDate));
            }

            Integer diagnosticLocationId = null;
            if (!StringUtils.isBlank(diagnosticLocationUuid)) {
                Location location = Context.getLocationService().getLocationByUuid(diagnosticLocationUuid);
                if (location == null) {
                    labManagementService.failBatchJob(batchJob.getUuid(), "Report diagnostic location parameter not found");
                    return;
                }
                diagnosticLocationId = location.getId();
            }

            if (shouldStopExecution.apply(batchJob)) {
                return;
            }

            TestConfigSearchFilter filter = new TestConfigSearchFilter();
            Map<Integer, List<TestConfigDTO>> testConfigResult = labManagementService.findTestConfigurations(filter)
                    .getData().stream().collect(Collectors.groupingBy(TestConfigDTO::getTestId));
            List<SummarizedTestReportItem> data = labManagementService.getSummarizedTestReport(startDate, endDate, diagnosticLocationId);
            if (shouldStopExecution.apply(batchJob)) {
                return;
            }
            resetExecutionState(batchJob, labManagementService, log);
            writer = Files.newBufferedWriter(resultsFile.toPath(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            csvWriter = createCsvWriter(writer);
            writeHeaders(csvWriter);

            if (!data.isEmpty()) {
                data = data.stream().peek(p-> p.setTestName(formatTestName(p.getTestName(), p.getTestShortName()))).sorted(Comparator.comparing(SummarizedTestReportItem::getTestName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)))
                        .collect(Collectors.toList());

                for (SummarizedTestReportItem row : data) {
                    writeRow(csvWriter, row, testConfigResult);
                }

                csvWriter.flush();
                recordsProcessed += data.size();
                pageIndex++;
                SummarizedTestReportItem lastRecord = data.get(data.size() - 1);
                updateExecutionState(batchJob, executionState, pageIndex, recordsProcessed, lastRecord.getOrderConceptId(), null, labManagementService, null);
            } else if (pageIndex == 0) {
                updateExecutionState(batchJob, executionState, pageIndex, recordsProcessed, null, null,
                        labManagementService, null);
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

    protected void writeRow(CSVWriter csvWriter, SummarizedTestReportItem row, Map<Integer, List<TestConfigDTO>> testConfig) {
        String group = null;
        List<TestConfigDTO> testConfigs =  testConfig.getOrDefault(row.getOrderConceptId(), null);
        if(testConfigs != null && !testConfigs.isEmpty()) {
            group = testConfigs.get(0).getTestGroupName();
        }
        writeLineToCsv(csvWriter,row.getTestName(), group,  row.getTestsCompleted() == null ? "0" : Long.toString(row.getTestsCompleted()));
    }

    protected void writeHeaders(CSVWriter csvWriter) {
        writeLineToCsv(csvWriter, "Test", "Group", "Count");
    }
}
