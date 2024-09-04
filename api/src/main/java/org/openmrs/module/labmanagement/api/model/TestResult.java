package org.openmrs.module.labmanagement.api.model;

import org.openmrs.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "labmanagement.TestResult")
@Table(name = "labmgmt_test_result")
public class TestResult extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worksheet_item_id")
    private WorksheetItem worksheetItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_request_item_sample_id", nullable = false)
    private TestRequestItemSample testRequestItemSample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obs_id", nullable = false)
    private Obs obs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_by", nullable = false)
    private User resultBy;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "result_date", nullable = false)
    private Date resultDate;

    @Column(name = "require_approval", nullable = false)
    private Boolean requireApproval = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_approval_id")
    private TestApproval currentApproval;

    @Column(name = "additional_tests_required")
    private Boolean additionalTestsRequired;

    @Column(name = "archive_sample")
    private Boolean archiveSample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_activity_id")
    private SampleActivity sampleActivity;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "completed")
    private Boolean completed = false;

    @Column(name = "completed_result")
    private Boolean completedResult = false;

    @Column(name = "completed_date", nullable = true)
    private Date completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_location_id", nullable = true)
    private Location atLocation;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SampleActivity getSampleActivity() {
        return sampleActivity;
    }

    public void setSampleActivity(SampleActivity sampleActivity) {
        this.sampleActivity = sampleActivity;
    }

    public Boolean getArchiveSample() {
        return archiveSample;
    }

    public void setArchiveSample(Boolean archiveSample) {
        this.archiveSample = archiveSample;
    }

    public Boolean getAdditionalTestsRequired() {
        return additionalTestsRequired;
    }

    public void setAdditionalTestsRequired(Boolean additionalTestsRequired) {
        this.additionalTestsRequired = additionalTestsRequired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorksheetItem getWorksheetItem() {
        return worksheetItem;
    }

    public void setWorksheetItem(WorksheetItem worksheetItem) {
        this.worksheetItem = worksheetItem;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TestRequestItemSample getTestRequestItemSample() {
        return testRequestItemSample;
    }

    public void setTestRequestItemSample(TestRequestItemSample testRequestItemSample) {
        this.testRequestItemSample = testRequestItemSample;
    }

    public Obs getObs() {
        return obs;
    }

    public void setObs(Obs obs) {
        this.obs = obs;
    }

    public User getResultBy() {
        return resultBy;
    }

    public void setResultBy(User resultBy) {
        this.resultBy = resultBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Boolean getRequireApproval() {
        return requireApproval;
    }

    public void setRequireApproval(Boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public TestApproval getCurrentApproval() {
        return currentApproval;
    }

    public void setCurrentApproval(TestApproval currentApproval) {
        this.currentApproval = currentApproval;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompletedResult() {
        return completedResult;
    }

    public void setCompletedResult(Boolean completedResult) {
        this.completedResult = completedResult;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Location getAtLocation() {
        return atLocation;
    }

    public void setAtLocation(Location atLocation) {
        this.atLocation = atLocation;
    }

    public void completeTestResult(String status){
        setStatus(status);
        setCompleted(true);
        setCompletedResult(true);
        setCompletedDate(new Date());
    }
}
