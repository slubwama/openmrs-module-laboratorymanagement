package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "labmanagement.ApprovalConfig")
@Table(name = "labmgmt_approval_config")
public class ApprovalConfig extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_config_id", nullable = false)
    private Integer id;

    @Column(name = "approval_title", nullable = false, length = 100)
    private String approvalTitle;

    @Column(name = "privilege", nullable = false)
    private String privilege;

    @Column(name = "pending_status", nullable = false, length = 100)
    private String pendingStatus;

    @Column(name = "returned_status", nullable = false, length = 100)
    private String returnedStatus;

    @Column(name = "rejected_status", nullable = false, length = 100)
    private String rejectedStatus;

    @Column(name = "approved_status", nullable = false, length = 100)
    private String approvedStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(String pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    public String getReturnedStatus() {
        return returnedStatus;
    }

    public void setReturnedStatus(String returnedStatus) {
        this.returnedStatus = returnedStatus;
    }

    public String getRejectedStatus() {
        return rejectedStatus;
    }

    public void setRejectedStatus(String rejectedStatus) {
        this.rejectedStatus = rejectedStatus;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

}
