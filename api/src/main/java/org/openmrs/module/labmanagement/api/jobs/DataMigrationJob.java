package org.openmrs.module.labmanagement.api.jobs;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.openmrs.EncounterType;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.BatchJob;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.Pair;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.io.Writer;

public class DataMigrationJob extends AsyncTaskJob {

    private static final AtomicBoolean isAlreadyRunning = new AtomicBoolean(false);

    protected final Log log = LogFactory.getLog(this.getClass());

    protected LabManagementService labManagementService = null;

    public void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
        if (isAlreadyRunning.get()) {
            log.debug("Data migration job is already running");
            return;
        }
        try {
            if (isAlreadyRunning.getAndSet(true)) {
                log.debug("Data migration job is already running");
                return;
            }
            executeInternal(batchJob, shouldStopExecution);
        }
        catch (Exception exception) {
            log.error("Error occurred while executing data migration job");
            log.error(exception);
        }
        finally {
            isAlreadyRunning.set(false);
        }
    }
    protected void executeInternal(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
        if (!GlobalProperties.enableDataMigration()) {
            log.debug("Data migration is not enabled under settings");
            return;
        }

        String laboratoryEncounterType = GlobalProperties.getLaboratoryEncounterType();
        if(StringUtils.isBlank(laboratoryEncounterType)) {
            log.debug("LaboratoryEncounterType is not set under settings");
            return;
        }
        labManagementService = Context.getService(LabManagementService.class);
        EncounterType encounterType = Context.getEncounterService().getEncounterTypeByUuid(laboratoryEncounterType);
        if(encounterType == null) {
            log.debug("LaboratoryEncounterType set under settings does not exist");
            return;
        }

        if (!restoreExecutionState(batchJob, labManagementService, log)) {
            return;
        }

        if (shouldStopExecution.apply(batchJob)) {
            return;
        }

        GenericObject parameters = null;

        try {
            parameters = StringUtils.isBlank(batchJob.getParameters()) ?  new GenericObject() : GenericObject.parseJson(batchJob.getParameters());
        }
        catch (Exception e) {
            labManagementService.failBatchJob(batchJob.getUuid(), "Failed to read parameters");
            log.error(e.getMessage(), e);
            return;
        }

        Date startDate = getStartDate(parameters);
        Date endDate = getEndDate(parameters);
        Integer afterOrderId =  lastRecordProcessed;
        Integer limit = getLimit(parameters);
        if(afterOrderId == null){
            afterOrderId = GlobalProperties.getLastMigratedOrderId();
        }
        if(endDate != null){
            endDate = DateUtil.endOfDay(endDate);
        }

        CSVWriter csvWriter = null;
        Writer writer = null;

        try{
            if(limit == null) limit = 100;
            if(afterOrderId == null) afterOrderId = 0;
            boolean hasAppendedHeaders = false;
            boolean hasMoreRecords = true;
            MessageSourceService messageSourceService = Context.getMessageSourceService();
            while (hasMoreRecords) {
                if (shouldStopExecution.apply(batchJob)) {
                    return;
                }
                List<Order> data = labManagementService.getOrdersToMigrate(encounterType.getId(),afterOrderId, limit, startDate, endDate);
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
                if (!data.isEmpty()) {
                    for (Order row : data) {
                        try {
                            Pair<Boolean, String> result = labManagementService.migrateOrder(row);
                            writeRow(csvWriter, row.getOrderId(), result.getValue1(), result.getValue2(), messageSourceService);
                        }catch (Exception exception){
                            log.error(exception.toString());
                            exception.printStackTrace();
                            writeRow(csvWriter, row.getOrderId(), false, exception.toString(), messageSourceService);
                        }
                    }
                    csvWriter.flush();
                    recordsProcessed += data.size();
                    pageIndex++;
                    Order lastRecord = data.get(data.size() - 1);
                    GlobalProperties.setLastMigratedOrderId(lastRecord.getId());
                    updateExecutionState(batchJob, executionState, 0, recordsProcessed, lastRecord.getId(),null,
                            labManagementService, null);
                } else if (pageIndex == 0) {
                    updateExecutionState(batchJob, executionState, 0, recordsProcessed, null,null,
                            labManagementService, null);
                }

                hasMoreRecords = data.size() >= limit;
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

    protected void writeRow(CSVWriter csvWriter, Integer orderId, Boolean success, String error, MessageSourceService messageSourceService) {
        String[] columns = new String[3];
        int columnIndex = 0;
        columns[columnIndex++] = Integer.toString(orderId);
        columns[columnIndex++] = success ? "Y" : "N";
        columns[columnIndex] = error;
        writeLineToCsv(csvWriter, columns);
    }

    protected void writeHeaders(CSVWriter csvWriter) {
        MessageSourceService messageSourceService = Context.getMessageSourceService();
        String[] columns = new String[3];
        int columnIndex = 0;
        columns[columnIndex++] = messageSourceService.getMessage("labmanagement.datamigration.orderid");
        columns[columnIndex++] = messageSourceService.getMessage("labmanagement.datamigration.success");
        columns[columnIndex] = messageSourceService.getMessage("labmanagement.datamigration.error");
        writeLineToCsv(csvWriter, columns);
    }
}
