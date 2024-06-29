package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.Concept;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "labmanagement.TestConfig")
@Table(name = "labmgmt_test_config")
public class TestConfig extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_config_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Concept test;

    @Column(name = "require_approval", nullable = false)
    private Boolean requireApproval = false;

    @JoinColumn(name = "approval_flow_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ApprovalFlow approvalFlow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_group_id")
    private Concept testGroup;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Concept getTest() {
        return test;
    }

    public void setTest(Concept test) {
        this.test = test;
    }

    public Boolean getRequireApproval() {
        return requireApproval;
    }

    public void setRequireApproval(Boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public ApprovalFlow getApprovalFlow() {
        return approvalFlow;
    }

    public void setApprovalFlow(ApprovalFlow approvalFlow) {
        this.approvalFlow = approvalFlow;
    }

    public Concept getTestGroup() {
        return testGroup;
    }

    public void setTestGroup(Concept testGroup) {
        this.testGroup = testGroup;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
