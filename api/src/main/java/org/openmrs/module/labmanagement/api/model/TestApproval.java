package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "labmanagement.TestApproval")
@Table(name = "labmgmt_test_approval")
public class TestApproval extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_approval_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_flow_id", nullable = false)
    private ApprovalFlow approvalFlow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_config_id", nullable = false)
    private ApprovalConfig approvalConfig;

    @Column(name = "approval_result", length = 100)
    @Enumerated(EnumType.STRING)
    private ApprovalResult approvalResult;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "activated_date")
    private Date activatedDate;

    @Column(name = "approval_date")
    private Date approvalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_approval_id")
    private TestApproval nextApproval;

    @Column(name = "current_approval_level", nullable = false)
    private Integer currentApprovalLevel;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_result_id", nullable = false)
    private TestResult testResult;

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ApprovalConfig getApprovalConfig() {
        return approvalConfig;
    }

    public void setApprovalConfig(ApprovalConfig approvalConfig) {
        this.approvalConfig = approvalConfig;
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

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public TestApproval getNextApproval() {
        return nextApproval;
    }

    public void setNextApproval(TestApproval nextApproval) {
        this.nextApproval = nextApproval;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public ApprovalFlow getApprovalFlow() {
        return approvalFlow;
    }

    public void setApprovalFlow(ApprovalFlow approvalFlow) {
        this.approvalFlow = approvalFlow;
    }
}
