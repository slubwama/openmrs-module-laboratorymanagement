package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class WorksheetTestResultDTO {
    private String uuid;
    private String worksheetUuid;
    private List<TestResultDTO> testResults;

    public String getWorksheetUuid() {
        return worksheetUuid;
    }

    public void setWorksheetUuid(String worksheetUuid) {
        this.worksheetUuid = worksheetUuid;
    }

    public List<TestResultDTO> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResultDTO> testResults) {
        this.testResults = testResults;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
