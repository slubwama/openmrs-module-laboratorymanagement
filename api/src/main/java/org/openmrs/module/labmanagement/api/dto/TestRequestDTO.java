package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.TestRequestStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestRequestDTO implements Serializable {
    private String uuid;
    private Integer id;
    private String patientUuid;
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
    private Date requestDate;
    private String requestNo;
    private Order.Urgency urgency;
    private String careSettingUuid;
    private String careSettingName;
    private Date dateStopped;
    private TestRequestStatus status;
    private String atLocationUuid;
    private String atLocationName;
    private Boolean referredIn;
    private String referralFromFacilityUuid;
    private Integer referralFromFacilityId;
    private String referralFromFacilityName;
    private String referralInExternalRef;
    private String clinicalNote;
    private String requestReason;
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
    private List<TestRequestItemDTO> tests;
    private List<TestRequestSampleDTO> samples;
    private Map<String, Object> requestContextItems;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProviderUuid() {
        return providerUuid;
    }

    public void setProviderUuid(String providerUuid) {
        this.providerUuid = providerUuid;
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

    public String getAtLocationName() {
        return atLocationName;
    }

    public void setAtLocationName(String atLocationName) {
        this.atLocationName = atLocationName;
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

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public String getCareSettingUuid() {
        return careSettingUuid;
    }

    public void setCareSettingUuid(String careSettingUuid) {
        this.careSettingUuid = careSettingUuid;
    }

    public String getCareSettingName() {
        return careSettingName;
    }

    public void setCareSettingName(String careSettingName) {
        this.careSettingName = careSettingName;
    }

    public Date getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
    }

    public TestRequestStatus getStatus() {
        return status;
    }

    public void setStatus(TestRequestStatus status) {
        this.status = status;
    }

    public String getAtLocationUuid() {
        return atLocationUuid;
    }

    public void setAtLocationUuid(String atLocationUuid) {
        this.atLocationUuid = atLocationUuid;
    }

    public Boolean getReferredIn() {
        return referredIn;
    }

    public void setReferredIn(Boolean referredIn) {
        this.referredIn = referredIn;
    }

    public String getReferralFromFacilityUuid() {
        return referralFromFacilityUuid;
    }

    public void setReferralFromFacilityUuid(String referralFromFacilityUuid) {
        this.referralFromFacilityUuid = referralFromFacilityUuid;
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

    public String getClinicalNote() {
        return clinicalNote;
    }

    public void setClinicalNote(String clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public boolean isVoided() {
        return voided;
    }

    public List<TestRequestSampleDTO> getSamples() {
        return samples;
    }

    public void setSamples(List<TestRequestSampleDTO> samples) {
        this.samples = samples;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getReferralFromFacilityId() {
        return referralFromFacilityId;
    }

    public void setReferralFromFacilityId(Integer referralFromFacilityId) {
        this.referralFromFacilityId = referralFromFacilityId;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public List<TestRequestItemDTO> getTests() {
        return tests;
    }

    public void setTests(List<TestRequestItemDTO> tests) {
        this.tests = tests;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public Map<String, Object> getRequestContextItems() {
        return requestContextItems;
    }

    public void setRequestContextItems(Map<String, Object> requestContextItems) {
        this.requestContextItems = requestContextItems;
    }
}
