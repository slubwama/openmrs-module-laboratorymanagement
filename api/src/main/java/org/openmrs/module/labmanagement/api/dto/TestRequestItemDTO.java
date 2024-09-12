package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.ApprovalResult;
import org.openmrs.module.labmanagement.api.model.ReferralOutOrigin;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestStatus;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestRequestItemDTO extends TestRequestTestDTO {
    private Integer id;
    private Integer orderId;
    private Integer orderConceptId;
    private String testName;
    private String testShortName;
    private String orderUuid;
    private String orderNumber;
    private String atLocationUuid;
    private String atLocationName;
    private Integer toLocationId;
    private String toLocationUuid;
    private String toLocationName;
    private TestRequestItemStatus status;
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
    private Boolean requireRequestApproval;
    private ApprovalResult requestApprovalResult;
    private String requestApprovalByUuid;
    private Integer requestApprovalBy;
    private String requestApprovalGivenName;
    private String requestApprovalMiddleName;
    private String requestApprovalFamilyName;
    private Date requestApprovalDate;
    private String requestApprovalRemarks;
    private Integer initialSampleId;
    /*private String initialSampleUuid;
    private Integer finalResultId;
    private String finalResultUuid;*/
    private String uuid;
    private String encounterUuid;
    private String referralOutSampleUuid;
    private Boolean Completed;
    private String testRequestUuid;
    private String testRequestNo;
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
    private Integer returnCount;
    private List<SampleDTO> samples;

    private String patientUuid;
    private Integer patientId;
    private String patientGivenName;
    private String patientMiddleName;
    private String patientFamilyName;
    private String patientIdentifier;
    private Order.Urgency urgency;
    private Integer testRequestItemSampleId;
    private String testRequestItemSampleUuid;
    private TestResultDTO testResult;
    private Concept testConcept;
    private String worksheetNo;
    private String worksheetUuid;
    private Map<String, Object> requestContextItems;
    private Date completedDate;
    private Date resultDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public TestRequestItemStatus getStatus() {
        return status;
    }

    public void setStatus(TestRequestItemStatus status) {
        this.status = status;
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

    public Boolean getRequireRequestApproval() {
        return requireRequestApproval;
    }

    public void setRequireRequestApproval(Boolean requireRequestApproval) {
        this.requireRequestApproval = requireRequestApproval;
    }

    public String getRequestApprovalByUuid() {
        return requestApprovalByUuid;
    }

    public void setRequestApprovalByUuid(String requestApprovalByUuid) {
        this.requestApprovalByUuid = requestApprovalByUuid;
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

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public String getReferralOutSampleUuid() {
        return referralOutSampleUuid;
    }

    public void setReferralOutSampleUuid(String referralOutSampleUuid) {
        this.referralOutSampleUuid = referralOutSampleUuid;
    }

    public Boolean getCompleted() {
        return Completed;
    }

    public void setCompleted(Boolean completed) {
        Completed = completed;
    }

    public String getTestRequestUuid() {
        return testRequestUuid;
    }

    public void setTestRequestUuid(String testRequestUuid) {
        this.testRequestUuid = testRequestUuid;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getAtLocationUuid() {
        return atLocationUuid;
    }

    public void setAtLocationUuid(String atLocationUuid) {
        this.atLocationUuid = atLocationUuid;
    }

    public String getAtLocationName() {
        return atLocationName;
    }

    public void setAtLocationName(String atLocationName) {
        this.atLocationName = atLocationName;
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

    public Integer getReferralToFacilityId() {
        return referralToFacilityId;
    }

    public void setReferralToFacilityId(Integer referralToFacilityId) {
        this.referralToFacilityId = referralToFacilityId;
    }

    public ApprovalResult getRequestApprovalResult() {
        return requestApprovalResult;
    }

    public void setRequestApprovalResult(ApprovalResult requestApprovalResult) {
        this.requestApprovalResult = requestApprovalResult;
    }

    public boolean isVoided() {
        return voided;
    }

    public Integer getRequestApprovalBy() {
        return requestApprovalBy;
    }

    public void setRequestApprovalBy(Integer requestApprovalBy) {
        this.requestApprovalBy = requestApprovalBy;
    }

    public Integer getInitialSampleId() {
        return initialSampleId;
    }

    public void setInitialSampleId(Integer initialSampleId) {
        this.initialSampleId = initialSampleId;
    }

    public List<SampleDTO> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleDTO> samples) {
        this.samples = samples;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(Integer toLocationId) {
        this.toLocationId = toLocationId;
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

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;

    }

    public Integer getTestRequestItemSampleId() {
        return testRequestItemSampleId;
    }

    public void setTestRequestItemSampleId(Integer testRequestItemSampleId) {
        this.testRequestItemSampleId = testRequestItemSampleId;
    }

    public String getTestRequestItemSampleUuid() {
        return testRequestItemSampleUuid;
    }

    public void setTestRequestItemSampleUuid(String testRequestItemSampleUuid) {
        this.testRequestItemSampleUuid = testRequestItemSampleUuid;
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

    public Map<String, Object> getRequestContextItems() {
        return requestContextItems;
    }

    public void setRequestContextItems(Map<String, Object> requestContextItems) {
        this.requestContextItems = requestContextItems;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public String getTestRequestNo() {
        return testRequestNo;
    }

    public void setTestRequestNo(String testRequestNo) {
        this.testRequestNo = testRequestNo;
    }
}
