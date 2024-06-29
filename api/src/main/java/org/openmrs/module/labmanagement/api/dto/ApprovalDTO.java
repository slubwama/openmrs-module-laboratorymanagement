package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.ApprovalResult;

public class ApprovalDTO {
    public ApprovalResult result;
    public String remarks;

    public ApprovalResult getResult() {
        return result;
    }

    public void setResult(ApprovalResult result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
