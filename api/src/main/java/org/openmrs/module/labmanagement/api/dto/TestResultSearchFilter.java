package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;

import java.util.List;

public class TestResultSearchFilter extends SearchFilter {
    private Integer testResultId;
    private String testResultUuid;
    private List<Integer> testRequestItemIds;
    private List<Integer> worksheetItemIds;
    private Boolean requireApproval;
    private Boolean completed;
    private Boolean completedResult;
    private Integer patientId;
    private boolean permApproval=false;
    private boolean includeApprovals=false;

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public String getTestResultUuid() {
        return testResultUuid;
    }

    public void setTestResultUuid(String testResultUuid) {
        this.testResultUuid = testResultUuid;
    }

    public List<Integer> getTestRequestItemIds() {
        return testRequestItemIds;
    }

    public void setTestRequestItemIds(List<Integer> testRequestItemIds) {
        this.testRequestItemIds = testRequestItemIds;
    }

    public List<Integer> getWorksheetItemIds() {
        return worksheetItemIds;
    }

    public void setWorksheetItemIds(List<Integer> worksheetItemIds) {
        this.worksheetItemIds = worksheetItemIds;
    }

    public Boolean getRequireApproval() {
        return requireApproval;
    }

    public void setRequireApproval(Boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompletedResult() {
        return completedResult;
    }

    public void setCompletedResult(Boolean completedResult) {
        this.completedResult = completedResult;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public boolean getPermApproval() {
        return permApproval;
    }

    public void setPermApproval(boolean permApproval) {
        this.permApproval = permApproval;
    }

    public boolean getIncludeApprovals() {
        return includeApprovals;
    }

    public void setIncludeApprovals(boolean includeApprovals) {
        this.includeApprovals = includeApprovals;
    }
}
