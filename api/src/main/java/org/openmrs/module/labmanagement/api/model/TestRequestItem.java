package org.openmrs.module.labmanagement.api.model;

import org.openmrs.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "labmanagement.TestRequestItem")
@Table(name = "labmgmt_test_request_item")
public class TestRequestItem extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_request_item_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_location_id", nullable = false)
    private Location atLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    @Column(name = "referred_out", nullable = false)
    private Boolean referredOut = false;

    @Column(name = "referral_out_origin", length = 50)
    @Enumerated(EnumType.STRING)
    private ReferralOutOrigin referralOutOrigin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_out_by")
    private User referralOutBy;

    @Column(name = "referral_out_date")
    private Date referralOutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_to_facility_id")
    private ReferralLocation referralToFacility;

    @Column(name = "referral_to_facility_name")
    private String referralToFacilityName;

    @Column(name = "require_request_approval")
    private Boolean requireRequestApproval;

    @Column(name = "request_approval_result", length = 50)
    @Enumerated(EnumType.STRING)
    private ApprovalResult requestApprovalResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_approval_by")
    private User requestApprovalBy;

    @Column(name = "request_approval_date")
    private Date requestApprovalDate;

    @Column(name = "request_approval_remarks", length = 500)
    private String requestApprovalRemarks;

    @Column(name = "initial_sample_id")
    private Integer initialSampleId;

    @Column(name = "final_result_id")
    private Integer finalResultId;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TestRequestItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_out_sample_id")
    private Sample referralOutSample;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_request_id", nullable = false)
    private TestRequest testRequest;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "returnCount")
    private Integer returnCount;

    @OneToMany(mappedBy = "testRequestItem", cascade = CascadeType.ALL)
    private Set<TestRequestItemSample> testRequestItemSamples;

    public TestRequest getTestRequest() {
        return testRequest;
    }

    public void setTestRequest(TestRequest testRequest) {
        this.testRequest = testRequest;
    }

    public Sample getReferralOutSample() {
        return referralOutSample;
    }

    public void setReferralOutSample(Sample referralOutSample) {
        this.referralOutSample = referralOutSample;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Location getAtLocation() {
        return atLocation;
    }

    public void setAtLocation(Location atLocation) {
        this.atLocation = atLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }

    public Boolean getReferredOut() {
        return referredOut;
    }

    public void setReferredOut(Boolean referredOut) {
        this.referredOut = referredOut;
    }

    public ReferralOutOrigin getReferralOutOrigin() {
        return referralOutOrigin;
    }

    public void setReferralOutOrigin(ReferralOutOrigin referralOutOrigin) {
        this.referralOutOrigin = referralOutOrigin;
    }

    public User getReferralOutBy() {
        return referralOutBy;
    }

    public void setReferralOutBy(User referralOutBy) {
        this.referralOutBy = referralOutBy;
    }

    public Date getReferralOutDate() {
        return referralOutDate;
    }

    public void setReferralOutDate(Date referralOutDate) {
        this.referralOutDate = referralOutDate;
    }

    public ReferralLocation getReferralToFacility() {
        return referralToFacility;
    }

    public void setReferralToFacility(ReferralLocation referralToFacility) {
        this.referralToFacility = referralToFacility;
    }

    public String getReferralToFacilityName() {
        return referralToFacilityName;
    }

    public void setReferralToFacilityName(String referralToFacilityName) {
        this.referralToFacilityName = referralToFacilityName;
    }

    public Boolean getRequireRequestApproval() {
        return requireRequestApproval;
    }

    public void setRequireRequestApproval(Boolean requireRequestApproval) {
        this.requireRequestApproval = requireRequestApproval;
    }

    public ApprovalResult getRequestApprovalResult() {
        return requestApprovalResult;
    }

    public void setRequestApprovalResult(ApprovalResult requestApprovalResult) {
        this.requestApprovalResult = requestApprovalResult;
    }

    public User getRequestApprovalBy() {
        return requestApprovalBy;
    }

    public void setRequestApprovalBy(User requestApprovalBy) {
        this.requestApprovalBy = requestApprovalBy;
    }

    public Date getRequestApprovalDate() {
        return requestApprovalDate;
    }

    public void setRequestApprovalDate(Date requestApprovalDate) {
        this.requestApprovalDate = requestApprovalDate;
    }

    public String getRequestApprovalRemarks() {
        return requestApprovalRemarks;
    }

    public void setRequestApprovalRemarks(String requestApprovalRemarks) {
        this.requestApprovalRemarks = requestApprovalRemarks;
    }

    public Integer getInitialSampleId() {
        return initialSampleId;
    }

    public void setInitialSampleId(Integer initialSampleId) {
        this.initialSampleId = initialSampleId;
    }

    public Integer getFinalResultId() {
        return finalResultId;
    }

    public void setFinalResultId(Integer finalResultId) {
        this.finalResultId = finalResultId;
    }

    public TestRequestItemStatus getStatus() {
        return status;
    }

    public void setStatus(TestRequestItemStatus status) {
        this.status = status;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }



    public Set<TestRequestItemSample> getTestRequestItemSamples() {
        return testRequestItemSamples;
    }

    public void setTestRequestItemSamples(Set<TestRequestItemSample> testRequestItemSamples) {
        this.testRequestItemSamples = testRequestItemSamples;
    }

    public TestRequestItemSample addTestResult(TestRequestItemSample testRequestItemSample) {
        getTestRequestItemSamples().add(testRequestItemSample);
        testRequestItemSample.setTestRequestItem(this);
        return testRequestItemSample;
    }

    public TestRequestItemSample removeTestResult(TestRequestItemSample testRequestItemSample) {
        getTestRequestItemSamples().remove(testRequestItemSample);
        testRequestItemSample.setSample(null);
        return testRequestItemSample;
    }
}
