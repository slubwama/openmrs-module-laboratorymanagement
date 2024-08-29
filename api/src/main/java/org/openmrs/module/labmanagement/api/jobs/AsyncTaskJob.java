package org.openmrs.module.labmanagement.api.jobs;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.BatchJobStatus;
import org.openmrs.module.labmanagement.api.reporting.ReportParameter;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.FileUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.NumberFormatUtil;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

public abstract class AsyncTaskJob {
    private volatile boolean stopExecution = false;

    protected Integer pageIndex = 0;

    protected Integer recordsProcessed = 0;

    protected Integer lastRecordProcessed = 0;

    protected Integer lastLabOperationProcessed = 0;

    protected Integer executionStep = 0;

    protected File resultsFile = null;

    protected Properties executionState = null;

    protected boolean hasRestoredExecutionState = false;

    protected static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    protected static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MMM-yyyy");

    public void stop() {
        stopExecution = true;
    }

    public boolean shouldStop() {
        return stopExecution;
    }

    public abstract void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution);

    public void setRecordsProcessed(Properties properties, Integer recordsProcessed) {
        setParameter(properties, "RecordsProcessed", "Records Processed", recordsProcessed.toString(),
                NumberFormatUtil.integerDisplayFormat(recordsProcessed));
    }

    public Integer getRecordsProcessed(Properties properties) {
        try {
            String key = "param.RecordsProcessed.value";
            if (properties.containsKey(key)) {
                return Integer.parseInt(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public void setCurrentPageIndex(Properties properties, Integer currentPageIndex) {
        setParameter(properties, "CurrentPageIndex", "Current Page", currentPageIndex.toString(),
                NumberFormatUtil.integerDisplayFormat(currentPageIndex + 1));
    }

    public Integer getCurrentPageIndex(Properties properties) {
        try {
            String key = "param.CurrentPageIndex.value";
            if (properties.containsKey(key)) {
                return Integer.parseInt(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public void setLastRecordProcessed(Properties properties, Integer lastRecordProcessed) {
        setParameter(properties, "LastRecordProcessed", "Last Record Processed", lastRecordProcessed.toString(),
                NumberFormatUtil.integerDisplayFormat(lastRecordProcessed));
    }

    public void setExecutionStep(Properties properties, Integer lastRecordProcessed) {
        setParameter(properties, "ExecutionStep", "Execution Step", executionStep.toString(),
                NumberFormatUtil.integerDisplayFormat(executionStep));
    }

    public Integer getExecutionStep(Properties properties) {
        try {
            String key = "param.ExecutionStep.value";
            if (properties.containsKey(key)) {
                return Integer.parseInt(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Integer getLastRecordProcessed(Properties properties) {
        try {
            String key = "param.LastRecordProcessed.value";
            if (properties.containsKey(key)) {
                return Integer.parseInt(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Date getReportParameterDate(Properties properties, ReportParameter reportParameter) {
        try {
            String key = "param." + reportParameter.name() + ".value";
            if (properties.containsKey(key)) {
                return DateUtil.parseDate(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public String getReportParameterString(Properties properties, ReportParameter reportParameter) {
        try {
            String key = "param." + reportParameter.name() + ".value";
            if (properties.containsKey(key)) {
                return properties.getProperty(key);
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Boolean getReportParameterBoolean(Properties properties, ReportParameter reportParameter) {
        try {
            String key = "param." + reportParameter.name() + ".value";
            if (properties.containsKey(key)) {
                return Boolean.valueOf(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public BigDecimal getReportParameterBigDecimal(Properties properties, ReportParameter reportParameter) {
        try {
            String key = "param." + reportParameter.name() + ".value";
            if (properties.containsKey(key)) {
                return new BigDecimal(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Date getDate(Properties properties) {
        return getReportParameterDate(properties, ReportParameter.Date);
    }

    public Date getStartDate(Properties properties) {
        return getReportParameterDate(properties, ReportParameter.StartDate);
    }

    public Date getEndDate(Properties properties) {
        return getReportParameterDate(properties, ReportParameter.EndDate);
    }


    public String getLocation(Properties properties) {
        return getReportParameterString(properties, ReportParameter.Location);
    }

    public String getPatient(Properties properties) {
        return getReportParameterString(properties, ReportParameter.Patient);
    }

    public Integer getLimit(Properties properties) {
        try {
            String key = "param." + ReportParameter.Limit.name() + ".value";
            if (properties.containsKey(key)) {
                return Integer.parseInt(properties.getProperty(key));
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public void setParameter(Properties properties, String parameterName, String parameterDescription, String value,
                             String valueDescription) {
        properties.setProperty(String.format("param.%s.description", parameterName), parameterDescription);
        properties.setProperty(String.format("param.%s.value", parameterName), value);
        properties.setProperty(String.format("param.%s.value.desc", parameterName), valueDescription);
    }

    protected boolean restoreExecutionState(BatchJob batchJob, LabManagementService labManagementService, Log log) {
        boolean resetExecutionState = false;
        if (batchJob.getExecutionState() != null) {
            try {
                executionState = GlobalProperties.fromString(batchJob.getExecutionState());
                pageIndex = getCurrentPageIndex(executionState);
                recordsProcessed = getRecordsProcessed(executionState);
                lastRecordProcessed = getLastRecordProcessed(executionState);
                executionStep = getExecutionStep(executionState);
                hasRestoredExecutionState = true;
            }
            catch (IOException e) {
                resetExecutionState = true;
            }
        }
        pageIndex = pageIndex == null ? 0 : pageIndex;
        recordsProcessed = recordsProcessed == null ? 0 : recordsProcessed;
        lastRecordProcessed = lastRecordProcessed == null ? 0 : lastRecordProcessed;
        lastLabOperationProcessed = lastLabOperationProcessed == null ? 0 : lastLabOperationProcessed;
        resultsFile = new File(FileUtil.getBatchJobFolder(), batchJob.getUuid());
        if (resetExecutionState) {
            executionState = null;
            if (resultsFile.exists()) {
                try {
                    resultsFile.delete();
                    hasRestoredExecutionState = false;
                }
                catch (Exception exception) {
                    hasRestoredExecutionState = false;
                    labManagementService.failBatchJob(batchJob.getUuid(), "Failed to delete the existing results file "
                            + resultsFile.toString());
                    log.error(exception.getMessage(), exception);
                    return false;
                }
            }
        }
        if (executionState == null) {
            executionState = new Properties();
        }
        return true;
    }

    protected void updateExecutionState(BatchJob batchJob, Properties properties, Integer currentPageIndex,
                                        Integer recordsProcessed, Integer lastRecordProcessed, LabManagementService labManagementService)
            throws IOException {
        updateExecutionState(batchJob, properties, currentPageIndex, recordsProcessed, lastRecordProcessed,
                labManagementService, null);
    }

    protected void updateExecutionState(BatchJob batchJob, Properties properties, Integer currentPageIndex,
                                        Integer recordsProcessed, Integer lastRecordProcessed,
                                        LabManagementService labManagementService, Integer executionStep) throws IOException {

        setCurrentPageIndex(properties, currentPageIndex);
        if (recordsProcessed != null) {
            setRecordsProcessed(properties, recordsProcessed);
        }
        if (lastRecordProcessed != null) {
            setLastRecordProcessed(properties, lastRecordProcessed);
        }
        if (executionStep != null) {
            setExecutionStep(properties, executionStep);
        }
        pageIndex = currentPageIndex;
        this.recordsProcessed = recordsProcessed;
        this.lastRecordProcessed = lastRecordProcessed;
        this.lastLabOperationProcessed = lastLabOperationProcessed;
        this.executionStep = executionStep;

        if (executionState != null) {
            labManagementService.updateBatchJobExecutionState(batchJob.getUuid(),
                    GlobalProperties.toString(executionState, null));
        }
    }

    public void completeBatchJob(BatchJob batchJob, Long outputArtifactSize, String outputArtifactFileExt,
                                 Boolean outputArtifactViewable, LabManagementService labManagementService) throws IOException {
        BatchJob job = labManagementService.getBatchJobByUuid(batchJob.getUuid());
        job.setStatus(BatchJobStatus.Completed);
        job.setEndTime(new Date());
        job.setOutputArtifactFileExt(outputArtifactFileExt);
        job.setOutputArtifactViewable(outputArtifactViewable);
        job.setOutputArtifactSize(outputArtifactSize);
        labManagementService.saveBatchJob(job);
    }

    protected CSVWriter createCsvWriter(Writer writer) {
        return new CSVWriter(writer);
    }

    protected void writeLineToCsv(CSVWriter csvWriter, String... columns) {
        csvWriter.writeNext(columns, false);
    }

    protected String emptyIfNull(String value) {
        return value == null ? "" : value;
    }
}
