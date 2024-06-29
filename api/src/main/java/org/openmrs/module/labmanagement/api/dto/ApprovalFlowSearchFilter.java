package org.openmrs.module.labmanagement.api.dto;

public class ApprovalFlowSearchFilter extends SearchFilter {
    private Integer approvalFlowId;
    private String approvalFlowUuid;
    private String nameOrSystemName;
    private String approvalConfigUuid;
    private Integer approvalConfigId;

    public Integer getApprovalFlowId() {
        return approvalFlowId;
    }

    public void setApprovalFlowId(Integer approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }

    public String getApprovalFlowUuid() {
        return approvalFlowUuid;
    }

    public void setApprovalFlowUuid(String approvalFlowUuid) {
        this.approvalFlowUuid = approvalFlowUuid;
    }

    public String getNameOrSystemName() {
        return nameOrSystemName;
    }

    public void setNameOrSystemName(String nameOrSystemName) {
        this.nameOrSystemName = nameOrSystemName;
    }

    public String getApprovalConfigUuid() {
        return approvalConfigUuid;
    }

    public void setApprovalConfigUuid(String approvalConfigUuid) {
        this.approvalConfigUuid = approvalConfigUuid;
    }

    public Integer getApprovalConfigId() {
        return approvalConfigId;
    }

    public void setApprovalConfigId(Integer approvalConfigId) {
        this.approvalConfigId = approvalConfigId;
    }
}
