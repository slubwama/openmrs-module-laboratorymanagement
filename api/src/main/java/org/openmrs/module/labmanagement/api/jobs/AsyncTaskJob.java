package org.openmrs.module.labmanagement.api.jobs;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.BatchJobStatus;
import org.openmrs.module.labmanagement.api.reporting.*;
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

    protected Integer executionStep = 0;

    protected File resultsFile = null;

    public GenericObject parameters = null;

    protected GenericObject executionState = null;

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

    public void setRecordsProcessed(GenericObject properties, Integer recordsProcessed) {
        setParameter(properties, "RecordsProcessed", "Records Processed", recordsProcessed.toString(),
                NumberFormatUtil.integerDisplayFormat(recordsProcessed));
    }

    public Integer getRecordsProcessed(GenericObject properties) {
        try {
            String key = "RecordsProcessed";
            if (properties.containsKey(key)) {
                StringReportParameter stringReportParameter=new StringReportParameter();
                stringReportParameter.setValueFromMap(properties.get(key));
                return  stringReportParameter.isValueSet() ?  Integer.parseInt(stringReportParameter.getValue()) : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public void setCurrentPageIndex(GenericObject properties, Integer currentPageIndex) {
        setParameter(properties, "CurrentPageIndex", "Current Page", currentPageIndex.toString(),
                NumberFormatUtil.integerDisplayFormat(currentPageIndex + 1));
    }

    public Integer getCurrentPageIndex(GenericObject properties) {
        try {
            String key = "CurrentPageIndex";
            if (properties.containsKey(key)) {
                StringReportParameter stringReportParameter=new StringReportParameter();
                stringReportParameter.setValueFromMap(properties.get(key));
                return  stringReportParameter.isValueSet() ?  Integer.parseInt(stringReportParameter.getValue()) : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public void setLastRecordProcessed(GenericObject properties, Integer lastRecordProcessed) {
        setParameter(properties, "LastRecordProcessed", "Last Record Processed", lastRecordProcessed.toString(),
                NumberFormatUtil.integerDisplayFormat(lastRecordProcessed));
    }

    public void setExecutionStep(GenericObject properties, Integer lastRecordProcessed) {
        setParameter(properties, "ExecutionStep", "Execution Step", executionStep.toString(),
                NumberFormatUtil.integerDisplayFormat(executionStep));
    }

    public Integer getExecutionStep(GenericObject properties) {
        try {
            String key = "ExecutionStep";
            if (properties.containsKey(key)) {
                StringReportParameter stringReportParameter=new StringReportParameter();
                stringReportParameter.setValueFromMap(properties.get(key));
                return  stringReportParameter.isValueSet() ?  Integer.parseInt(stringReportParameter.getValue()) : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Integer getLastRecordProcessed(GenericObject properties) {
        try {
            String key = "LastRecordProcessed";
            if (properties.containsKey(key)) {
                StringReportParameter stringReportParameter=new StringReportParameter();
                stringReportParameter.setValueFromMap(properties.get(key));
                return  stringReportParameter.isValueSet() ?  Integer.parseInt(stringReportParameter.getValue()) : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Date getReportParameterDate(GenericObject properties, ReportParameter reportParameter) {
        try {
            String key = reportParameter.name();
            if (properties.containsKey(key)) {
                DateReportParameter dateReportParameter=new DateReportParameter();
                dateReportParameter.setValueFromMap(properties.get(key));
                return  dateReportParameter.isValueSet() ?  dateReportParameter.getValue() : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public ObsValue getReportParameterObs(GenericObject properties, ReportParameter reportParameter) {
        try {
            String key = reportParameter.name();
            if (properties.containsKey(key)) {
                ObsReportParameter obsReportParameter=new ObsReportParameter();
                obsReportParameter.setValueFromMap(properties.get(key));
                return  obsReportParameter.isValueSet() ?  obsReportParameter.getValue() : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public String getReportParameterString(GenericObject properties, ReportParameter reportParameter) {
        try {
            String key = reportParameter.name();
            if (properties.containsKey(key)) {
                StringReportParameter stringReportParameter=new StringReportParameter();
                stringReportParameter.setValueFromMap(properties.get(key));
                return  stringReportParameter.getValue();
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Boolean getReportParameterBoolean(Properties properties, ReportParameter reportParameter) {
        try {
            String key = reportParameter.name();
            if (properties.containsKey(key)) {
                BooleanReportParameter booleanReportParameter=new BooleanReportParameter();
                booleanReportParameter.setValueFromMap(properties.get(key));
                return  booleanReportParameter.isValueSet() ?  booleanReportParameter.getValue() : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public BigDecimal getReportParameterBigDecimal(Properties properties, ReportParameter reportParameter) {
        try {
            String key = reportParameter.name();
            if (properties.containsKey(key)) {
                BigDecimalReportParameter bigDecimalReportParameter=new BigDecimalReportParameter();
                bigDecimalReportParameter.setValueFromMap(properties.get(key));
                return  bigDecimalReportParameter.isValueSet() ?  bigDecimalReportParameter.getValue() : null;
            }
        }
        catch (Exception exception) {}
        return null;
    }

    public Date getDate(GenericObject properties) {
        return getReportParameterDate(properties, ReportParameter.Date);
    }

    public Date getStartDate(GenericObject properties) {
        return getReportParameterDate(properties, ReportParameter.StartDate);
    }

    public Date getEndDate(GenericObject properties) {
        return getReportParameterDate(properties, ReportParameter.EndDate);
    }

    public String getLocation(GenericObject properties) {
        return getReportParameterString(properties, ReportParameter.Location);
    }

    public String getPatient(GenericObject properties) {
        return getReportParameterString(properties, ReportParameter.Patient);
    }

    public String getTestType(GenericObject properties) {
        return getReportParameterString(properties, ReportParameter.TestType);
    }

    public String getTestApprover(GenericObject properties) {
        return getReportParameterString(properties, ReportParameter.TestApprover);
    }

    public String getDiagnosticLocation(GenericObject properties) {
        return getReportParameterString(properties, ReportParameter.DiagnosticLocation);
    }

    public ObsValue getTestOutcome(GenericObject properties) {
        return getReportParameterObs(properties, ReportParameter.TestOutcome);
    }

    public Integer getLimit(GenericObject properties) {
        try {
            String key =  ReportParameter.Limit.name();
            StringReportParameter stringReportParameter=new StringReportParameter();
            stringReportParameter.setValueFromMap(properties.get(key));
            return  stringReportParameter.isValueSet() ?  Integer.parseInt(stringReportParameter.getValue()) : null;

        }
        catch (Exception exception) {}
        return null;
    }

    public void setParameter(GenericObject properties, String parameterName, String parameterDescription, String value,
                             String valueDescription) {
        StringReportParameter stringReportParameter = new StringReportParameter();
        stringReportParameter.setValueDescription(valueDescription);
        stringReportParameter.setValue(value);
        stringReportParameter.setDescription(parameterDescription);
        properties.put(parameterName, stringReportParameter.toMap());
    }

    protected boolean restoreExecutionState(BatchJob batchJob, LabManagementService labManagementService, Log log) {
        boolean resetExecutionState = false;
        if (batchJob.getExecutionState() != null) {
            try {
                executionState =  GenericObject.parseJson(batchJob.getExecutionState());
                pageIndex = getCurrentPageIndex(executionState);
                recordsProcessed = getRecordsProcessed(executionState);
                lastRecordProcessed = getLastRecordProcessed(executionState);
                executionStep = getExecutionStep(executionState);
                hasRestoredExecutionState = true;
            }
            catch (Exception e) {
                resetExecutionState = true;
            }
        }
        pageIndex = pageIndex == null ? 0 : pageIndex;
        recordsProcessed = recordsProcessed == null ? 0 : recordsProcessed;
        lastRecordProcessed = lastRecordProcessed == null ? 0 : lastRecordProcessed;
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
            executionState = new GenericObject();
        }
        return true;
    }

    protected void updateExecutionState(BatchJob batchJob, GenericObject properties, Integer currentPageIndex,
                                        Integer recordsProcessed, Integer lastRecordProcessed, LabManagementService labManagementService)
            throws IOException {
        updateExecutionState(batchJob, properties, currentPageIndex, recordsProcessed, lastRecordProcessed,
                labManagementService, null);
    }

    protected void updateExecutionState(BatchJob batchJob, GenericObject properties, Integer currentPageIndex,
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
        this.executionStep = executionStep;

        if (executionState != null) {
            labManagementService.updateBatchJobExecutionState(batchJob.getUuid(),
                    GenericObject.toJson(executionState));
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

    public GenericObject getParameters() {
        return parameters;
    }

    public void setParameters(GenericObject parameters) {
        this.parameters = parameters;
    }
}
