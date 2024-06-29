package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class TestApprovalSearchFilter extends SearchFilter {
    private Integer testApprovalId;
    private String testApprovalUuid;

    public Integer getTestApprovalId() {
        return testApprovalId;
    }

    public void setTestApprovalId(Integer testApprovalId) {
        this.testApprovalId = testApprovalId;
    }

    public String getTestApprovalUuid() {
        return testApprovalUuid;
    }

    public void setTestApprovalUuid(String testApprovalUuid) {
        this.testApprovalUuid = testApprovalUuid;
    }

    private List<Integer> testResultIds;

    public List<Integer> getTestResultIds() {
        return testResultIds;
    }

    public void setTestResultIds(List<Integer> testResultIds) {
        this.testResultIds = testResultIds;
    }
}
