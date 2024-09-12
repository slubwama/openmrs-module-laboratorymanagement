package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.module.labmanagement.api.model.SampleActivityType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO for {@link org.openmrs.module.labmanagement.api.model.SampleActivity}
 */
public class SampleActivityDTO implements Serializable {
    private Integer id;
    private String uuid;
    private String sampleUuid;
    private SampleActivityType activityType;
    private String sourceUuid;
    private String sourceName;
    private String destinationUuid;
    private String destinationName;
    private String sourceState;
    private String destinationState;
    private Integer activityBy;
    private String activityByUuid;
    private String activityByGivenName;
    private String activityByMiddleName;
    private String activityByFamilyName;

    private String remarks;
    private String status;
    private BigDecimal volume;
    private Integer volumeUnit;
    private String volumeUnitUuid;
    private String volumeUnitName;
    private Integer thawCycles;
    private String storageUnitUuid;
    private String storageUnitName;
    private String storageUuid;
    private String storageName;
    private Date activityDate;
    private Integer responsiblePerson;
    private String responsiblePersonUuid;
    private String responsiblePersonGivenName;
    private String responsiblePersonMiddleName;
    private String responsiblePersonFamilyName;
    private String responsiblePersonOther;
    private Integer creator;
    private String creatorUuid;
    private String creatorGivenName;
    private String creatorMiddleName;
    private String creatorFamilyName;
    private Date dateCreated;

    public String getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(String sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    public SampleActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(SampleActivityType activityType) {
        this.activityType = activityType;
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationUuid() {
        return destinationUuid;
    }

    public void setDestinationUuid(String destinationUuid) {
        this.destinationUuid = destinationUuid;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
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

    public Integer getActivityBy() {
        return activityBy;
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

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getVolumeUnitUuid() {
        return volumeUnitUuid;
    }

    public void setVolumeUnitUuid(String volumeUnitUuid) {
        this.volumeUnitUuid = volumeUnitUuid;
    }

    public String getVolumeUnitName() {
        return volumeUnitName;
    }

    public void setVolumeUnitName(String volumeUnitName) {
        this.volumeUnitName = volumeUnitName;
    }

    public Integer getThawCycles() {
        return thawCycles;
    }

    public void setThawCycles(Integer thawCycles) {
        this.thawCycles = thawCycles;
    }

    public String getStorageUnitUuid() {
        return storageUnitUuid;
    }

    public void setStorageUnitUuid(String storageUnitUuid) {
        this.storageUnitUuid = storageUnitUuid;
    }


    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getResponsiblePersonUuid() {
        return responsiblePersonUuid;
    }

    public void setResponsiblePersonUuid(String responsiblePersonUuid) {
        this.responsiblePersonUuid = responsiblePersonUuid;
    }

    public String getResponsiblePersonGivenName() {
        return responsiblePersonGivenName;
    }

    public void setResponsiblePersonGivenName(String responsiblePersonGivenName) {
        this.responsiblePersonGivenName = responsiblePersonGivenName;
    }

    public String getResponsiblePersonMiddleName() {
        return responsiblePersonMiddleName;
    }

    public void setResponsiblePersonMiddleName(String responsiblePersonMiddleName) {
        this.responsiblePersonMiddleName = responsiblePersonMiddleName;
    }

    public String getResponsiblePersonFamilyName() {
        return responsiblePersonFamilyName;
    }

    public void setResponsiblePersonFamilyName(String responsiblePersonFamilyName) {
        this.responsiblePersonFamilyName = responsiblePersonFamilyName;
    }

    public String getResponsiblePersonOther() {
        return responsiblePersonOther;
    }

    public void setResponsiblePersonOther(String responsiblePersonOther) {
        this.responsiblePersonOther = responsiblePersonOther;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setActivityBy(Integer activityBy) {
        this.activityBy = activityBy;
    }

    public String getActivityByGivenName() {
        return activityByGivenName;
    }

    public void setActivityByGivenName(String activityByGivenName) {
        this.activityByGivenName = activityByGivenName;
    }

    public String getActivityByMiddleName() {
        return activityByMiddleName;
    }

    public void setActivityByMiddleName(String activityByMiddleName) {
        this.activityByMiddleName = activityByMiddleName;
    }

    public String getActivityByFamilyName() {
        return activityByFamilyName;
    }

    public void setActivityByFamilyName(String activityByFamilyName) {
        this.activityByFamilyName = activityByFamilyName;
    }

    public Integer getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(Integer volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public String getStorageUnitName() {
        return storageUnitName;
    }

    public void setStorageUnitName(String storageUnitName) {
        this.storageUnitName = storageUnitName;
    }

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public Integer getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Integer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getCreatorMiddleName() {
        return creatorMiddleName;
    }

    public void setCreatorMiddleName(String creatorMiddleName) {
        this.creatorMiddleName = creatorMiddleName;
    }

    public String getActivityByUuid() {
        return activityByUuid;
    }

    public void setActivityByUuid(String activityByUuid) {
        this.activityByUuid = activityByUuid;
    }
}
