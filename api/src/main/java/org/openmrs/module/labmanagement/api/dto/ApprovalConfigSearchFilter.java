package org.openmrs.module.labmanagement.api.dto;

public class ApprovalConfigSearchFilter extends SearchFilter {
    private Integer approvalConfigId;
    private String approvalConfigUuid;
    private String approvalTitle;

    public Integer getApprovalConfigId() {
        return approvalConfigId;
    }

    public void setApprovalConfigId(Integer approvalConfigId) {
        this.approvalConfigId = approvalConfigId;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public String getApprovalConfigUuid() {
        return approvalConfigUuid;
    }

    public void setApprovalConfigUuid(String approvalConfigUuid) {
        this.approvalConfigUuid = approvalConfigUuid;
    }
}
