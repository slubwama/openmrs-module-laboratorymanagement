package org.openmrs.module.labmanagement.api.dto;


import org.openmrs.User;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;


public class TestConfigDTO {

    private Integer id;

    private Integer testId;

    private String testUuid;
    private String testName;
    private String testShortName;
    private Boolean requireApproval = false;

    private Integer approvalFlowId;
    private String approvalFlowUuid;
    private String approvalFlowName;

    private Integer testGroupId;
    private String testGroupName;

    private String testGroupUuid;

    private Boolean enabled;

    @Size(max = 38)
    private String uuid;

    private boolean voided;

    private Integer creator;
    private String creatorUuid;

    private String creatorGivenName;

    private String creatorFamilyName;

    private Date dateCreated;

    private Integer changedBy;
    private String changedByUuid;

    private String changedByGivenName;

    private String changedByFamilyName;

    private Date dateChanged;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Boolean getRequireApproval() {
        return requireApproval;
    }

    public void setRequireApproval(Boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public Integer getApprovalFlowId() {
        return approvalFlowId;
    }

    public void setApprovalFlowId(Integer approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }

    public Integer getTestGroupId() {
        return testGroupId;
    }

    public void setTestGroupId(Integer testGroupId) {
        this.testGroupId = testGroupId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTestUuid() {
        return testUuid;
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    public String getTestGroupUuid() {
        return testGroupUuid;
    }

    public void setTestGroupUuid(String testGroupUuid) {
        this.testGroupUuid = testGroupUuid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApprovalFlowUuid() {
        return approvalFlowUuid;
    }

    public void setApprovalFlowUuid(String approvalFlowUuid) {
        this.approvalFlowUuid = approvalFlowUuid;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getApprovalFlowName() {
        return approvalFlowName;
    }

    public void setApprovalFlowName(String approvalFlowName) {
        this.approvalFlowName = approvalFlowName;
    }

    public String getTestGroupName() {
        return testGroupName;
    }

    public void setTestGroupName(String testGroupName) {
        this.testGroupName = testGroupName;
    }

    public boolean getVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorGivenName() {
        return creatorGivenName;
    }

    public void setCreatorGivenName(String creatorGivenName) {
        this.creatorGivenName = creatorGivenName;
    }

    public String getCreatorFamilyName() {
        return creatorFamilyName;
    }

    public void setCreatorFamilyName(String creatorFamilyName) {
        this.creatorFamilyName = creatorFamilyName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedByGivenName() {
        return changedByGivenName;
    }

    public void setChangedByGivenName(String changedByGivenName) {
        this.changedByGivenName = changedByGivenName;
    }

    public String getChangedByFamilyName() {
        return changedByFamilyName;
    }

    public void setChangedByFamilyName(String changedByFamilyName) {
        this.changedByFamilyName = changedByFamilyName;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getChangedByUuid() {
        return changedByUuid;
    }

    public void setChangedByUuid(String changedByUuid) {
        this.changedByUuid = changedByUuid;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public String getTestShortName() {
        return testShortName;
    }

    public void setTestShortName(String testShortName) {
        this.testShortName = testShortName;
    }
}
