package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.WorksheetItemStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class WorksheetItemDTO {
    private String uuid;
    private Integer id;
    private Integer worksheetId;
    private String worksheetNo;
    private String worksheetUuid;
    private Integer sampleId;
    private String sampleUuid;
    private String sampleProvidedRef;
    private String sampleAccessionNumber;
    private String sampleExternalRef;
    private WorksheetItemStatus status;
    private Date completedDate;
    private Date cancelledDate;
    private String cancellationRemarks;
    private String testRequestItemUuid;
    private String testRequestItemSampleUuid;
    private Integer testId;
    private String testUuid;
    private String testName;
    private String testShortName;
    private String orderUuid;
    private String patientUuid;
    private Integer patientId;
    private String referralFromFacilityUuid;
    private Integer referralFromFacilityId;
    private String referralFromFacilityName;
    private String referralInExternalRef;
    private String patientGivenName;
    private String patientMiddleName;
    private String patientFamilyName;
    private String patientIdentifier;
    private String toLocationUuid;
    private String toLocationName;
    private Order.Urgency urgency;

    private String sampleTypeName;
    private Integer sampleTypeId;
    private String sampleTypeUuid;
    private Integer sampleContainerTypeId;
    private String sampleContainerTypeUuid;
    private String sampleContainerTypeName;
    private Integer sampleContainerCount;
    private BigDecimal sampleVolume;
    private Integer sampleVolumeUnitId;
    private String sampleVolumeUnitUuid;
    private String sampleVolumeUnitName;
    private Integer sampleCollectedBy;
    private String sampleCollectedByUuid;
    private String sampleCollectedByGivenName;
    private String sampleCollectedByMiddleName;
    private String sampleCollectedByFamilyName;
    private Date sampleCollectionDate;
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
    private String testResultUuid;
    private TestResultDTO testResult;
    private Concept testConcept;
    private Map<String, Object> requestContextItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(Integer worksheetId) {
        this.worksheetId = worksheetId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSampleProvidedRef() {
        return sampleProvidedRef;
    }

    public void setSampleProvidedRef(String sampleProvidedRef) {
        this.sampleProvidedRef = sampleProvidedRef;
    }

    public String getSampleAccessionNumber() {
        return sampleAccessionNumber;
    }

    public void setSampleAccessionNumber(String sampleAccessionNumber) {
        this.sampleAccessionNumber = sampleAccessionNumber;
    }

    public String getSampleExternalRef() {
        return sampleExternalRef;
    }

    public void setSampleExternalRef(String sampleExternalRef) {
        this.sampleExternalRef = sampleExternalRef;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestShortName() {
        return testShortName;
    }

    public void setTestShortName(String testShortName) {
        this.testShortName = testShortName;
    }

    public String getTestUuid() {
        return testUuid;
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
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

    public String getToLocationUuid() {
        return toLocationUuid;
    }

    public void setToLocationUuid(String toLocationUuid) {
        this.toLocationUuid = toLocationUuid;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public void setToLocationName(String toLocationName) {
        this.toLocationName = toLocationName;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public Integer getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(Integer sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public Integer getSampleContainerTypeId() {
        return sampleContainerTypeId;
    }

    public void setSampleContainerTypeId(Integer sampleContainerTypeId) {
        this.sampleContainerTypeId = sampleContainerTypeId;
    }

    public String getSampleContainerTypeUuid() {
        return sampleContainerTypeUuid;
    }

    public void setSampleContainerTypeUuid(String sampleContainerTypeUuid) {
        this.sampleContainerTypeUuid = sampleContainerTypeUuid;
    }

    public String getSampleContainerTypeName() {
        return sampleContainerTypeName;
    }

    public void setSampleContainerTypeName(String sampleContainerTypeName) {
        this.sampleContainerTypeName = sampleContainerTypeName;
    }

    public Integer getSampleContainerCount() {
        return sampleContainerCount;
    }

    public void setSampleContainerCount(Integer sampleContainerCount) {
        this.sampleContainerCount = sampleContainerCount;
    }

    public BigDecimal getSampleVolume() {
        return sampleVolume;
    }

    public void setSampleVolume(BigDecimal sampleVolume) {
        this.sampleVolume = sampleVolume;
    }

    public Integer getSampleVolumeUnitId() {
        return sampleVolumeUnitId;
    }

    public void setSampleVolumeUnitId(Integer sampleVolumeUnitId) {
        this.sampleVolumeUnitId = sampleVolumeUnitId;
    }

    public String getSampleVolumeUnitUuid() {
        return sampleVolumeUnitUuid;
    }

    public void setSampleVolumeUnitUuid(String sampleVolumeUnitUuid) {
        this.sampleVolumeUnitUuid = sampleVolumeUnitUuid;
    }

    public String getSampleVolumeUnitName() {
        return sampleVolumeUnitName;
    }

    public void setSampleVolumeUnitName(String sampleVolumeUnitName) {
        this.sampleVolumeUnitName = sampleVolumeUnitName;
    }

    public Integer getSampleCollectedBy() {
        return sampleCollectedBy;
    }

    public void setSampleCollectedBy(Integer sampleCollectedBy) {
        this.sampleCollectedBy = sampleCollectedBy;
    }

    public String getSampleCollectedByUuid() {
        return sampleCollectedByUuid;
    }

    public void setSampleCollectedByUuid(String sampleCollectedByUuid) {
        this.sampleCollectedByUuid = sampleCollectedByUuid;
    }

    public String getSampleCollectedByGivenName() {
        return sampleCollectedByGivenName;
    }

    public void setSampleCollectedByGivenName(String sampleCollectedByGivenName) {
        this.sampleCollectedByGivenName = sampleCollectedByGivenName;
    }

    public String getSampleCollectedByMiddleName() {
        return sampleCollectedByMiddleName;
    }

    public void setSampleCollectedByMiddleName(String sampleCollectedByMiddleName) {
        this.sampleCollectedByMiddleName = sampleCollectedByMiddleName;
    }

    public String getSampleCollectedByFamilyName() {
        return sampleCollectedByFamilyName;
    }

    public void setSampleCollectedByFamilyName(String sampleCollectedByFamilyName) {
        this.sampleCollectedByFamilyName = sampleCollectedByFamilyName;
    }

    public Date getSampleCollectionDate() {
        return sampleCollectionDate;
    }

    public void setSampleCollectionDate(Date sampleCollectionDate) {
        this.sampleCollectionDate = sampleCollectionDate;
    }

    public String getSampleTypeUuid() {
        return sampleTypeUuid;
    }

    public void setSampleTypeUuid(String sampleTypeUuid) {
        this.sampleTypeUuid = sampleTypeUuid;
    }

    public String getTestResultUuid() {
        return testResultUuid;
    }

    public void setTestResultUuid(String testResultUuid) {
        this.testResultUuid = testResultUuid;
    }

    public String getTestRequestItemSampleUuid() {
        return testRequestItemSampleUuid;
    }

    public void setTestRequestItemSampleUuid(String testRequestItemSampleUuid) {
        this.testRequestItemSampleUuid = testRequestItemSampleUuid;
    }

    public String getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(String sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    public String getTestRequestItemUuid() {
        return testRequestItemUuid;
    }

    public void setTestRequestItemUuid(String testRequestItemUuid) {
        this.testRequestItemUuid = testRequestItemUuid;
    }

    public TestResultDTO getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResultDTO testResult) {
        this.testResult = testResult;
    }

    public Concept getTestConcept() {
        return testConcept;
    }

    public void setTestConcept(Concept testConcept) {
        this.testConcept = testConcept;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getWorksheetNo() {
        return worksheetNo;
    }

    public void setWorksheetNo(String worksheetNo) {
        this.worksheetNo = worksheetNo;
    }

    public String getWorksheetUuid() {
        return worksheetUuid;
    }

    public void setWorksheetUuid(String worksheetUuid) {
        this.worksheetUuid = worksheetUuid;
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
}
