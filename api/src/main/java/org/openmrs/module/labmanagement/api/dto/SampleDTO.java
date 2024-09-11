package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Concept;
import org.openmrs.module.labmanagement.api.model.ReferralOutOrigin;
import org.openmrs.module.labmanagement.api.model.Sample;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestItem;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SampleDTO extends TestRequestSampleDTO {
    private Integer id;
    private String patientUuid;
    private Integer patientId;
    private String patientIdentifier;
    private String patientGivenName;
    private String patientMiddleName;
    private String patientFamilyName;
    private String referralFromFacilityUuid;
    private Integer referralFromFacilityId;
    private String referralFromFacilityName;
    private String referralInExternalRef;
    private String parentSampleUuid;
    private String sampleTypeName;
    private String atLocationUuid;
    private String atLocationName;
    private Integer sampleTypeId;
    private Integer containerTypeId;
    private String containerTypeUuid;
    private String containerTypeName;
    private BigDecimal volume;
    private Integer volumeUnitId;
    private String volumeUnitUuid;
    private String volumeUnitName;
    private Integer collectedBy;
    private String collectedByUuid;
    private String collectedByGivenName;
    private String collectedByMiddleName;
    private String collectedByFamilyName;
    private Date collectionDate;
    private Integer containerCount;
    private String providedRef;
    private String accessionNumber;
    private String externalRef;
    private Boolean referredOut;
    private ReferralOutOrigin referralOutOrigin;
    private Integer referralOutBy;
    private String referralOutByUuid;
    private String referralOutByGivenName;
    private String referralOutByMiddleName;
    private String referralOutByFamilyName;
    private Date referralOutDate;
    private String referralToFacilityUuid;
    private Integer referralToFacilityId;
    private String referralToFacilityName;
    private String currentSampleActivityUuid;
    private SampleStatus status;
    private String encounterUuid;
    private Integer testRequestId;
    private String testRequestUuid;
    private String testRequestNo;
    private Integer testRequestItemId;
    private String testRequestItemSampleUuid;
    private Integer testRequestItemSampleId;
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
    private Set<String> sampleTestItemUuids;
    private Map<String, Object> requestContextItems;
    private String StorageUnitUuid;
    private String StorageUnitName;
    private String StorageUuid;
    private String StorageName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentSampleUuid() {
        return parentSampleUuid;
    }

    public void setParentSampleUuid(String parentSampleUuid) {
        this.parentSampleUuid = parentSampleUuid;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getAtLocationName() {
        return atLocationName;
    }

    public void setAtLocationName(String atLocationName) {
        this.atLocationName = atLocationName;
    }

    public Integer getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(Integer sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public Integer getContainerTypeId() {
        return containerTypeId;
    }

    public void setContainerTypeId(Integer containerTypeId) {
        this.containerTypeId = containerTypeId;
    }

    public String getContainerTypeUuid() {
        return containerTypeUuid;
    }

    public void setContainerTypeUuid(String containerTypeUuid) {
        this.containerTypeUuid = containerTypeUuid;
    }

    public String getContainerTypeName() {
        return containerTypeName;
    }

    public void setContainerTypeName(String containerTypeName) {
        this.containerTypeName = containerTypeName;
    }

    public Integer getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(Integer collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getCollectedByUuid() {
        return collectedByUuid;
    }

    public void setCollectedByUuid(String collectedByUuid) {
        this.collectedByUuid = collectedByUuid;
    }

    public String getCollectedByGivenName() {
        return collectedByGivenName;
    }

    public void setCollectedByGivenName(String collectedByGivenName) {
        this.collectedByGivenName = collectedByGivenName;
    }

    public String getCollectedByMiddleName() {
        return collectedByMiddleName;
    }

    public void setCollectedByMiddleName(String collectedByMiddleName) {
        this.collectedByMiddleName = collectedByMiddleName;
    }

    public String getCollectedByFamilyName() {
        return collectedByFamilyName;
    }

    public void setCollectedByFamilyName(String collectedByFamilyName) {
        this.collectedByFamilyName = collectedByFamilyName;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Integer getContainerCount() {
        return containerCount;
    }

    public void setContainerCount(Integer containerCount) {
        this.containerCount = containerCount;
    }

    public String getProvidedRef() {
        return providedRef;
    }

    public void setProvidedRef(String providedRef) {
        this.providedRef = providedRef;
    }

    public Boolean getReferredOut() {
        return referredOut;
    }

    public void setReferredOut(Boolean referredOut) {
        this.referredOut = referredOut;
    }

    public String getCurrentSampleActivityUuid() {
        return currentSampleActivityUuid;
    }

    public void setCurrentSampleActivityUuid(String currentSampleActivityUuid) {
        this.currentSampleActivityUuid = currentSampleActivityUuid;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public void setStatus(SampleStatus status) {
        this.status = status;
    }

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public String getTestRequestUuid() {
        return testRequestUuid;
    }

    public void setTestRequestUuid(String testRequestUuid) {
        this.testRequestUuid = testRequestUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedByUuid() {
        return changedByUuid;
    }

    public void setChangedByUuid(String changedByUuid) {
        this.changedByUuid = changedByUuid;
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

    public String getPatientUuid() {
        return patientUuid;
    }

    public void setPatientUuid(String patientUuid) {
        this.patientUuid = patientUuid;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getAtLocationUuid() {
        return atLocationUuid;
    }

    public void setAtLocationUuid(String atLocationUuid) {
        this.atLocationUuid = atLocationUuid;
    }

    @Override
    public String getAccessionNumber() {
        return accessionNumber;
    }

    @Override
    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    @Override
    public String getExternalRef() {
        return externalRef;
    }

    @Override
    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public String getPatientGivenName() {
        return patientGivenName;
    }

    public void setPatientGivenName(String patientGivenName) {
        this.patientGivenName = patientGivenName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public String getPatientFamilyName() {
        return patientFamilyName;
    }

    public void setPatientFamilyName(String patientFamilyName) {
        this.patientFamilyName = patientFamilyName;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public Integer getTestRequestItemId() {
        return testRequestItemId;
    }

    public void setTestRequestItemId(Integer testRequestItemId) {
        this.testRequestItemId = testRequestItemId;
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

    public Integer getVolumeUnitId() {
        return volumeUnitId;
    }

    public void setVolumeUnitId(Integer volumeUnitId) {
        this.volumeUnitId = volumeUnitId;
    }

    public String getReferralToFacilityUuid() {
        return referralToFacilityUuid;
    }

    public void setReferralToFacilityUuid(String referralToFacilityUuid) {
        this.referralToFacilityUuid = referralToFacilityUuid;
    }

    public String getReferralToFacilityName() {
        return referralToFacilityName;
    }

    public void setReferralToFacilityName(String referralToFacilityName) {
        this.referralToFacilityName = referralToFacilityName;
    }

    public Set<String> getSampleTestItemUuids() {
        return sampleTestItemUuids;
    }

    public void setSampleTestItemUuids(Set<String> sampleTestItemUuids) {
        this.sampleTestItemUuids = sampleTestItemUuids;
    }

    public ReferralOutOrigin getReferralOutOrigin() {
        return referralOutOrigin;
    }

    public void setReferralOutOrigin(ReferralOutOrigin referralOutOrigin) {
        this.referralOutOrigin = referralOutOrigin;
    }

    public Integer getReferralOutBy() {
        return referralOutBy;
    }

    public void setReferralOutBy(Integer referralOutBy) {
        this.referralOutBy = referralOutBy;
    }

    public String getReferralOutByUuid() {
        return referralOutByUuid;
    }

    public void setReferralOutByUuid(String referralOutByUuid) {
        this.referralOutByUuid = referralOutByUuid;
    }

    public String getReferralOutByGivenName() {
        return referralOutByGivenName;
    }

    public void setReferralOutByGivenName(String referralOutByGivenName) {
        this.referralOutByGivenName = referralOutByGivenName;
    }

    public String getReferralOutByMiddleName() {
        return referralOutByMiddleName;
    }

    public void setReferralOutByMiddleName(String referralOutByMiddleName) {
        this.referralOutByMiddleName = referralOutByMiddleName;
    }

    public String getReferralOutByFamilyName() {
        return referralOutByFamilyName;
    }

    public void setReferralOutByFamilyName(String referralOutByFamilyName) {
        this.referralOutByFamilyName = referralOutByFamilyName;
    }

    public Date getReferralOutDate() {
        return referralOutDate;
    }

    public void setReferralOutDate(Date referralOutDate) {
        this.referralOutDate = referralOutDate;
    }

    public Integer getReferralToFacilityId() {
        return referralToFacilityId;
    }

    public void setReferralToFacilityId(Integer referralToFacilityId) {
        this.referralToFacilityId = referralToFacilityId;
    }

    public Integer getTestRequestId() {
        return testRequestId;
    }

    public void setTestRequestId(Integer testRequestId) {
        this.testRequestId = testRequestId;
    }

    public String getTestRequestItemSampleUuid() {
        return testRequestItemSampleUuid;
    }

    public void setTestRequestItemSampleUuid(String testRequestItemSampleUuid) {
        this.testRequestItemSampleUuid = testRequestItemSampleUuid;
    }

    public Integer getTestRequestItemSampleId() {
        return testRequestItemSampleId;
    }

    public void setTestRequestItemSampleId(Integer testRequestItemSampleId) {
        this.testRequestItemSampleId = testRequestItemSampleId;
    }

    public String getReferralFromFacilityUuid() {
        return referralFromFacilityUuid;
    }

    public void setReferralFromFacilityUuid(String referralFromFacilityUuid) {
        this.referralFromFacilityUuid = referralFromFacilityUuid;
    }

    public Integer getReferralFromFacilityId() {
        return referralFromFacilityId;
    }

    public void setReferralFromFacilityId(Integer referralFromFacilityId) {
        this.referralFromFacilityId = referralFromFacilityId;
    }

    public String getReferralFromFacilityName() {
        return referralFromFacilityName;
    }

    public void setReferralFromFacilityName(String referralFromFacilityName) {
        this.referralFromFacilityName = referralFromFacilityName;
    }

    public String getReferralInExternalRef() {
        return referralInExternalRef;
    }

    public void setReferralInExternalRef(String referralInExternalRef) {
        this.referralInExternalRef = referralInExternalRef;
    }

    public Map<String, Object> getRequestContextItems() {
        return requestContextItems;
    }

    public void setRequestContextItems(Map<String, Object> requestContextItems) {
        this.requestContextItems = requestContextItems;
    }

    public String getTestRequestNo() {
        return testRequestNo;
    }

    public void setTestRequestNo(String testRequestNo) {
        this.testRequestNo = testRequestNo;
    }

    public String getStorageUnitUuid() {
        return StorageUnitUuid;
    }

    public void setStorageUnitUuid(String storageUnitUuid) {
        StorageUnitUuid = storageUnitUuid;
    }

    public String getStorageUnitName() {
        return StorageUnitName;
    }

    public void setStorageUnitName(String storageUnitName) {
        StorageUnitName = storageUnitName;
    }

    public String getStorageUuid() {
        return StorageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        StorageUuid = storageUuid;
    }

    public String getStorageName() {
        return StorageName;
    }

    public void setStorageName(String storageName) {
        StorageName = storageName;
    }
}
