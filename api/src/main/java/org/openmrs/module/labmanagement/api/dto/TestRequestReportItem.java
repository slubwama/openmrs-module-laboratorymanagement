package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.ApprovalResult;
import org.openmrs.module.labmanagement.api.model.ReferralOutOrigin;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;

import java.math.BigDecimal;
import java.util.Date;

public class TestRequestReportItem {
    private Integer testRequestItemId;
    private Integer testRequestId;
    private Integer patientId;
    private String patientGivenName;
    private String patientMiddleName;
    private String patientFamilyName;
    private String patientIdentifier;
    private Integer providerId;
    private String providerUuid;
    private String providerGivenName;
    private String providerMiddleName;
    private String providerFamilyName;
    private Boolean referredIn;
    private Integer referralFromFacilityId;
    private String referralFromFacilityName;
    private String referralInExternalRef;
    private Date requestDate;
    private Date registeredDate;
    private String requestNo;
    private String atLocationId;
    private String atLocationName;
    private Order.Urgency urgency;
    private String careSettingName;
    private BigDecimal volume;
    private Integer containerCount;

    private Integer orderId;
    private Integer orderConceptId;
    private String testName;
    private String testShortName;
    private String orderNumber;
    private Integer toLocationId;
    private String toLocationName;
    private TestRequestItemStatus status;
    private ReferralOutOrigin referralOutOrigin;
    private Integer referralOutBy;
    private String referralOutByGivenName;
    private String referralOutByMiddleName;
    private String referralOutByFamilyName;
    private Date referralOutDate;
    private Integer referralToFacilityId;
    private String referralToFacilityName;
    private Boolean requireRequestApproval;
    private ApprovalResult requestApprovalResult;
    private Integer requestApprovalBy;
    private String requestApprovalGivenName;
    private String requestApprovalMiddleName;
    private String requestApprovalFamilyName;
    private Date requestApprovalDate;
    private String requestApprovalRemarks;
    private String uuid;
    private Boolean Completed;
    private Date completedDate;
    private Integer creator;
    private String creatorGivenName;
    private String creatorMiddleName;
    private String creatorFamilyName;
    private Date dateCreated;

    private String sampleProvidedRef;
    private String sampleAccessionNumber;
    private String sampleExternalRef;
    private Integer sampleTypeId;
    private Integer sampleContainerTypeId;
    private String sampleContainerTypeName;
    private String sampleTypeName;
    private String sampleAtLocationName;
    private Integer volumeUnitId;
    private String volumeUnitName;
    private Integer collectedBy;
    private String collectedByGivenName;
    private String collectedByMiddleName;
    private String collectedByFamilyName;
    private Date collectionDate;
    private Boolean referredOut;
    private SampleStatus sampleStatus;


    private String worksheetAtLocationName;
    private Integer resultBy;
    private String resultByGivenName;
    private String resultByMiddleName;
    private String resultByFamilyName;
    private String resultStatus;
    private String resultRemarks;
    private Date resultDate;
    private Boolean resultRequireApproval;
    private String worksheetNo;
    private Integer ObsId;
    private Boolean resultCompleted;
    private Date resultApprovalDate;;
    private Integer currentApprovalBy;
    private String currentApprovalByGivenName;
    private String currentApprovalByMiddleName;
    private String currentApprovalByFamilyName;
    private String resultAtLocationName;

    public Integer getTestRequestItemId() {
        return testRequestItemId;
    }

    public void setTestRequestItemId(Integer testRequestItemId) {
        this.testRequestItemId = testRequestItemId;
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

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderUuid() {
        return providerUuid;
    }

    public void setProviderUuid(String providerUuid) {
        this.providerUuid = providerUuid;
    }

    public String getProviderGivenName() {
        return providerGivenName;
    }

    public void setProviderGivenName(String providerGivenName) {
        this.providerGivenName = providerGivenName;
    }

    public String getProviderMiddleName() {
        return providerMiddleName;
    }

    public void setProviderMiddleName(String providerMiddleName) {
        this.providerMiddleName = providerMiddleName;
    }

    public String getProviderFamilyName() {
        return providerFamilyName;
    }

    public void setProviderFamilyName(String providerFamilyName) {
        this.providerFamilyName = providerFamilyName;
    }

    public Boolean getReferredIn() {
        return referredIn;
    }

    public void setReferredIn(Boolean referredIn) {
        this.referredIn = referredIn;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getAtLocationId() {
        return atLocationId;
    }

    public void setAtLocationId(String atLocationId) {
        this.atLocationId = atLocationId;
    }

    public String getAtLocationName() {
        return atLocationName;
    }

    public void setAtLocationName(String atLocationName) {
        this.atLocationName = atLocationName;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderConceptId() {
        return orderConceptId;
    }

    public void setOrderConceptId(Integer orderConceptId) {
        this.orderConceptId = orderConceptId;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(Integer toLocationId) {
        this.toLocationId = toLocationId;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public void setToLocationName(String toLocationName) {
        this.toLocationName = toLocationName;
    }

    public TestRequestItemStatus getStatus() {
        return status;
    }

    public void setStatus(TestRequestItemStatus status) {
        this.status = status;
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

    public Integer getRequestApprovalBy() {
        return requestApprovalBy;
    }

    public void setRequestApprovalBy(Integer requestApprovalBy) {
        this.requestApprovalBy = requestApprovalBy;
    }

    public String getRequestApprovalGivenName() {
        return requestApprovalGivenName;
    }

    public void setRequestApprovalGivenName(String requestApprovalGivenName) {
        this.requestApprovalGivenName = requestApprovalGivenName;
    }

    public String getRequestApprovalMiddleName() {
        return requestApprovalMiddleName;
    }

    public void setRequestApprovalMiddleName(String requestApprovalMiddleName) {
        this.requestApprovalMiddleName = requestApprovalMiddleName;
    }

    public String getRequestApprovalFamilyName() {
        return requestApprovalFamilyName;
    }

    public void setRequestApprovalFamilyName(String requestApprovalFamilyName) {
        this.requestApprovalFamilyName = requestApprovalFamilyName;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getCompleted() {
        return Completed;
    }

    public void setCompleted(Boolean completed) {
        Completed = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorGivenName() {
        return creatorGivenName;
    }

    public void setCreatorGivenName(String creatorGivenName) {
        this.creatorGivenName = creatorGivenName;
    }

    public String getCreatorMiddleName() {
        return creatorMiddleName;
    }

    public void setCreatorMiddleName(String creatorMiddleName) {
        this.creatorMiddleName = creatorMiddleName;
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

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSampleAtLocationName() {
        return sampleAtLocationName;
    }

    public void setSampleAtLocationName(String sampleAtLocationName) {
        this.sampleAtLocationName = sampleAtLocationName;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Integer getVolumeUnitId() {
        return volumeUnitId;
    }

    public void setVolumeUnitId(Integer volumeUnitId) {
        this.volumeUnitId = volumeUnitId;
    }

    public String getVolumeUnitName() {
        return volumeUnitName;
    }

    public void setVolumeUnitName(String volumeUnitName) {
        this.volumeUnitName = volumeUnitName;
    }

    public Integer getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(Integer collectedBy) {
        this.collectedBy = collectedBy;
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

    public Boolean getReferredOut() {
        return referredOut;
    }

    public void setReferredOut(Boolean referredOut) {
        this.referredOut = referredOut;
    }

    public SampleStatus getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(SampleStatus sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getWorksheetAtLocationName() {
        return worksheetAtLocationName;
    }

    public void setWorksheetAtLocationName(String worksheetAtLocationName) {
        this.worksheetAtLocationName = worksheetAtLocationName;
    }

    public Integer getResultBy() {
        return resultBy;
    }

    public void setResultBy(Integer resultBy) {
        this.resultBy = resultBy;
    }

    public String getResultByGivenName() {
        return resultByGivenName;
    }

    public void setResultByGivenName(String resultByGivenName) {
        this.resultByGivenName = resultByGivenName;
    }

    public String getResultByMiddleName() {
        return resultByMiddleName;
    }

    public void setResultByMiddleName(String resultByMiddleName) {
        this.resultByMiddleName = resultByMiddleName;
    }

    public String getResultByFamilyName() {
        return resultByFamilyName;
    }

    public void setResultByFamilyName(String resultByFamilyName) {
        this.resultByFamilyName = resultByFamilyName;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public String getWorksheetNo() {
        return worksheetNo;
    }

    public void setWorksheetNo(String worksheetNo) {
        this.worksheetNo = worksheetNo;
    }

    public Integer getObsId() {
        return ObsId;
    }

    public void setObsId(Integer obsId) {
        ObsId = obsId;
    }

    public Boolean getResultCompleted() {
        return resultCompleted;
    }

    public void setResultCompleted(Boolean resultCompleted) {
        this.resultCompleted = resultCompleted;
    }

    public Integer getCurrentApprovalBy() {
        return currentApprovalBy;
    }

    public void setCurrentApprovalBy(Integer currentApprovalBy) {
        this.currentApprovalBy = currentApprovalBy;
    }

    public String getCurrentApprovalByGivenName() {
        return currentApprovalByGivenName;
    }

    public void setCurrentApprovalByGivenName(String currentApprovalByGivenName) {
        this.currentApprovalByGivenName = currentApprovalByGivenName;
    }

    public String getCurrentApprovalByMiddleName() {
        return currentApprovalByMiddleName;
    }

    public void setCurrentApprovalByMiddleName(String currentApprovalByMiddleName) {
        this.currentApprovalByMiddleName = currentApprovalByMiddleName;
    }

    public String getCurrentApprovalByFamilyName() {
        return currentApprovalByFamilyName;
    }

    public void setCurrentApprovalByFamilyName(String currentApprovalByFamilyName) {
        this.currentApprovalByFamilyName = currentApprovalByFamilyName;
    }

    public Integer getTestRequestId() {
        return testRequestId;
    }

    public void setTestRequestId(Integer testRequestId) {
        this.testRequestId = testRequestId;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Boolean getResultRequireApproval() {
        return resultRequireApproval;
    }

    public void setResultRequireApproval(Boolean resultRequireApproval) {
        this.resultRequireApproval = resultRequireApproval;
    }

    public Date getResultApprovalDate() {
        return resultApprovalDate;
    }

    public void setResultApprovalDate(Date resultApprovalDate) {
        this.resultApprovalDate = resultApprovalDate;
    }

    public String getSampleContainerTypeName() {
        return sampleContainerTypeName;
    }

    public void setSampleContainerTypeName(String sampleContainerTypeName) {
        this.sampleContainerTypeName = sampleContainerTypeName;
    }

    public String getCareSettingName() {
        return careSettingName;
    }

    public void setCareSettingName(String careSettingName) {
        this.careSettingName = careSettingName;
    }

    public Integer getContainerCount() {
        return containerCount;
    }

    public void setContainerCount(Integer containerCount) {
        this.containerCount = containerCount;
    }

    public String getResultRemarks() {
        return resultRemarks;
    }

    public void setResultRemarks(String resultRemarks) {
        this.resultRemarks = resultRemarks;
    }

    public String getResultAtLocationName() {
        return resultAtLocationName;
    }

    public void setResultAtLocationName(String resultAtLocationName) {
        this.resultAtLocationName = resultAtLocationName;
    }
}
