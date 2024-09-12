package org.openmrs.module.labmanagement.api.model;

import org.openmrs.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "labmanagement.SampleActivity")
@Table(name = "labmgmt_sample_activity")
public class SampleActivity extends BaseChangeableOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_activity_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id")
    private Sample sample;

    @Column(name = "activity_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SampleActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Location source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Location destination;

    @Column(name = "source_state", nullable = false, length = 50)
    private String sourceState;

    @Column(name = "destination_state", nullable = false, length = 50)
    private String destinationState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_by", nullable = false)
    private User activityBy;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_sample_id")
    private Sample toSample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reused_checkout")
    private SampleActivity reusedCheckout;

    @Column(name = "volume", precision = 18, scale = 4)
    private BigDecimal volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volume_unit")
    private Concept volumeUnit;

    @Column(name = "thaw_cycles")
    private Integer thawCycles;

    @JoinColumn(name = "storage_unit_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private StorageUnit storageUnit;

    @Column(name = "activity_date")
    private Date activityDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_person_id")
    private User responsiblePerson;

    @Column(name = "responsible_person_other", length = 150)
    private String responsiblePersonOther;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public SampleActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(SampleActivityType activityType) {
        this.activityType = activityType;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getSourceState() {
        return sourceState;
    }

    public void setSourceState(String sourceState) {
        this.sourceState = sourceState;
    }

    public String getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(String destinationState) {
        this.destinationState = destinationState;
    }

    public User getActivityBy() {
        return activityBy;
    }

    public void setActivityBy(User activityBy) {
        this.activityBy = activityBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Sample getToSample() {
        return toSample;
    }

    public void setToSample(Sample toSample) {
        this.toSample = toSample;
    }

    public SampleActivity getReusedCheckout() {
        return reusedCheckout;
    }

    public void setReusedCheckout(SampleActivity reusedCheckout) {
        this.reusedCheckout = reusedCheckout;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Integer getThawCycles() {
        return thawCycles;
    }

    public void setThawCycles(Integer thawCycles) {
        this.thawCycles = thawCycles;
    }

    public Concept getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(Concept volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public StorageUnit getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(StorageUnit storageUnit) {
        this.storageUnit = storageUnit;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public User getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(User responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePersonOther() {
        return responsiblePersonOther;
    }

    public void setResponsiblePersonOther(String responsiblePersonOther) {
        this.responsiblePersonOther = responsiblePersonOther;
    }
}
