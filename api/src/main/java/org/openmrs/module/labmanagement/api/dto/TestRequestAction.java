package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.ApprovalResult;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class TestRequestAction {
    private TestRequestActionType actionType;
    private ApprovalResult action;
    private String remarks;
    private List<String> records;
    private String uuid;
    private String testRequestUuid;
    private Date actionDate;
    private LinkedHashMap<String, Object> parameters;

    public ApprovalResult getAction() {
        return action;
    }

    public void setAction(ApprovalResult action) {
        this.action = action;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<String> getRecords() {
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TestRequestActionType getActionType() {
        return actionType;
    }

    public void setActionType(TestRequestActionType actionType) {
        this.actionType = actionType;
    }

    public String getTestRequestUuid() {
        return testRequestUuid;
    }

    public void setTestRequestUuid(String testRequestUuid) {
        this.testRequestUuid = testRequestUuid;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public LinkedHashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedHashMap<String, Object> parameters) {
        this.parameters = parameters;
    }
}
