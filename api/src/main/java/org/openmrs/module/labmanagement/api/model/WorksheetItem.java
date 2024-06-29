package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "labmanagement.WorksheetItem")
@Table(name = "labmgmt_worksheet_item")
public class WorksheetItem extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worksheet_item_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worksheet_id", nullable = false)
    private Worksheet worksheet;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private WorksheetItemStatus status;

    @Column(name = "completed_date")
    private Date completedDate;

    @Column(name = "cancelled_date")
    private Date cancelledDate;


    @Column(name = "cancellation_remarks", length = 500)
    private String cancellationRemarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_request_item_sample_id")
    private TestRequestItemSample testRequestItemSample;

    public TestRequestItemSample getTestRequestItemSample() {
        return testRequestItemSample;
    }

    public void setTestRequestItemSample(TestRequestItemSample testRequestItemSample) {
        this.testRequestItemSample = testRequestItemSample;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.worksheet = worksheet;
    }

    public WorksheetItemStatus getStatus() {
        return status;
    }

    public void setStatus(WorksheetItemStatus status) {
        this.status = status;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getCancellationRemarks() {
        return cancellationRemarks;
    }

    public void setCancellationRemarks(String cancellationRemarks) {
        this.cancellationRemarks = cancellationRemarks;
    }

}
