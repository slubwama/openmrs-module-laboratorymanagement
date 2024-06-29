package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class TestConfigSearchFilter extends SearchFilter {
    private String  testConfigUuid;
    private Integer  testConfigId;
    private Boolean active;
    private String testGroupUuid;
    private Integer testGroupId;
    private Integer testId;
    private String testUuid;
    private List<Integer> testIds;
    private List<String> testUuids;
    private List<Integer> testOrGroupIds;
    private  Integer approvalFlowId;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTestGroupUuid() {
        return testGroupUuid;
    }

    public void setTestGroupUuid(String testGroupUuid) {
        this.testGroupUuid = testGroupUuid;
    }

    public Integer getTestGroupId() {
        return testGroupId;
    }

    public void setTestGroupId(Integer testGroupId) {
        this.testGroupId = testGroupId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestUuid() {
        return testUuid;
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    public List<Integer> getTestIds() {
        return testIds;
    }

    public void setTestIds(List<Integer> testIds) {
        this.testIds = testIds;
    }

    public List<String> getTestUuids() {
        return testUuids;
    }

    public void setTestUuids(List<String> testUuids) {
        this.testUuids = testUuids;
    }

    public String getTestConfigUuid() {
        return testConfigUuid;
    }

    public void setTestConfigUuid(String testConfigUuid) {
        this.testConfigUuid = testConfigUuid;
    }

    public Integer getTestConfigId() {
        return testConfigId;
    }

    public void setTestConfigId(Integer testConfigId) {
        this.testConfigId = testConfigId;
    }

    public List<Integer> getTestOrGroupIds() {
        return testOrGroupIds;
    }

    public void setTestOrGroupIds(List<Integer> testOrGroupIds) {
        this.testOrGroupIds = testOrGroupIds;
    }

    public Integer getApprovalFlowId() {
        return approvalFlowId;
    }

    public void setApprovalFlowId(Integer approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }
}
