package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.ApprovalResult;

import javax.validation.constraints.Size;
import java.util.Date;

public class TestApprovalDTO {
    private Integer id;
    private String uuid;
    private Integer approvedBy;

    private String approvedByGivenName;
    private String approvedByMiddleName;
    private String approvedByFamilyName;
    private String approvedByUuid;
    private Integer testResultId;
    private String testResultUuid;
    private String approvalTitle;
    private ApprovalResult approvalResult;
    private String remarks;
    private Date activatedDate;
    private Date approvalDate;
    private Integer currentApprovalLevel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public ApprovalResult getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(ApprovalResult approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApprovedByGivenName() {
        return approvedByGivenName;
    }

    public void setApprovedByGivenName(String approvedByGivenName) {
        this.approvedByGivenName = approvedByGivenName;
    }

    public String getApprovedByMiddleName() {
        return approvedByMiddleName;
    }

    public void setApprovedByMiddleName(String approvedByMiddleName) {
        this.approvedByMiddleName = approvedByMiddleName;
    }

    public String getApprovedByFamilyName() {
        return approvedByFamilyName;
    }

    public void setApprovedByFamilyName(String approvedByFamilyName) {
        this.approvedByFamilyName = approvedByFamilyName;
    }

    public String getApprovedByUuid() {
        return approvedByUuid;
    }

    public void setApprovedByUuid(String approvedByUuid) {
        this.approvedByUuid = approvedByUuid;
    }

    public String getTestResultUuid() {
        return testResultUuid;
    }

    public void setTestResultUuid(String testResultUuid) {
        this.testResultUuid = testResultUuid;
    }
}
