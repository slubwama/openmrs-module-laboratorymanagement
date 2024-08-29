package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;

import java.util.Date;
import java.util.List;

public class TestRequestSearchFilter extends SearchFilter {
    private Integer testRequestId;
    private String testRequestUuid;
    private List<TestRequestItemStatus> itemStatuses;
    private List<SampleStatus> sampleStatuses;
    private Order.Urgency urgency;
    private TestItemSampleCriteria testItemSampleCriteria = TestItemSampleCriteria.AND;
    private List<Integer> testConceptIds;
    private Integer itemLocationId;
    private Date minActivatedDate;
    private Date maxActivatedDate;
    private Boolean referredIn;
    private Boolean referredOut;
    private Integer patientId;
    private boolean includeTestItems;
    private boolean includeTestRequestItemSamples = false;
    private boolean includeTestRequestSamples = false;
    private boolean includeAllTests = false;
    private boolean testConceptForRequestOnly =false;
    private boolean includeTestItemTestResult = false;
    private boolean includeTestItemTestResultApprovals = false;
    private Boolean pendingResultApproval;
    private boolean onlyPendingResultApproval = false;
    private boolean permApproval=false;
    private boolean includeTestItemConcept = false;
    private boolean includeTestItemWorksheetInfo=false;
    private RequestItemMatchOptions requestItemMatch;

    public Integer getTestRequestId() {
        return testRequestId;
    }

    public void setTestRequestId(Integer testRequestId) {
        this.testRequestId = testRequestId;
    }

    public String getTestRequestUuid() {
        return testRequestUuid;
    }

    public void setTestRequestUuid(String testRequestUuid) {
        this.testRequestUuid = testRequestUuid;
    }

    public List<TestRequestItemStatus> getItemStatuses() {
        return itemStatuses;
    }

    public void setItemStatuses(List<TestRequestItemStatus> itemStatuses) {
        this.itemStatuses = itemStatuses;
    }

    public List<Integer> getTestConceptIds() {
        return testConceptIds;
    }

    public void setTestConceptIds(List<Integer> testConceptIds) {
        this.testConceptIds = testConceptIds;
    }

    public Integer getItemLocationId() {
        return itemLocationId;
    }

    public void setItemLocationId(Integer itemLocationId) {
        this.itemLocationId = itemLocationId;
    }

    public Boolean getReferredIn() {
        return referredIn;
    }

    public void setReferredIn(Boolean referredIn) {
        this.referredIn = referredIn;
    }

    public Boolean getReferredOut() {
        return referredOut;
    }

    public void setReferredOut(Boolean referredOut) {
        this.referredOut = referredOut;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getMinActivatedDate() {
        return minActivatedDate;
    }

    public void setMinActivatedDate(Date minActivatedDate) {
        this.minActivatedDate = minActivatedDate;
    }

    public Date getMaxActivatedDate() {
        return maxActivatedDate;
    }

    public void setMaxActivatedDate(Date maxActivatedDate) {
        this.maxActivatedDate = maxActivatedDate;
    }

    public boolean getIncludeTestItems() {
        return includeTestItems;
    }

    public void setIncludeTestItems(boolean includeTestItems) {
        this.includeTestItems = includeTestItems;
    }

    public boolean getIncludeAllTests() {
        return includeAllTests;
    }

    public void setIncludeAllTests(boolean includeAllTests) {
        this.includeAllTests = includeAllTests;
    }

    public boolean isTestConceptForRequestOnly() {
        return testConceptForRequestOnly;
    }

    public void setTestConceptForRequestOnly(boolean testConceptForRequestOnly) {
        this.testConceptForRequestOnly = testConceptForRequestOnly;
    }

    public boolean getIncludeTestRequestItemSamples() {
        return includeTestRequestItemSamples;
    }

    public void setIncludeTestRequestItemSamples(boolean includeTestRequestItemSamples) {
        this.includeTestRequestItemSamples = includeTestRequestItemSamples;
    }

    public boolean getIncludeTestRequestSamples() {
        return includeTestRequestSamples;
    }

    public void setIncludeTestRequestSamples(boolean includeTestRequestSamples) {
        this.includeTestRequestSamples = includeTestRequestSamples;
    }

    public List<SampleStatus> getSampleStatuses() {
        return sampleStatuses;
    }

    public void setSampleStatuses(List<SampleStatus> sampleStatuses) {
        this.sampleStatuses = sampleStatuses;
    }

    public TestItemSampleCriteria getTestItemSampleCriteria() {
        return testItemSampleCriteria;
    }

    public void setTestItemSampleCriteria(TestItemSampleCriteria testItemSampleCriteria) {
        this.testItemSampleCriteria = testItemSampleCriteria;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public boolean getIncludeTestItemTestResult() {
        return includeTestItemTestResult;
    }

    public void setIncludeTestItemTestResult(boolean includeTestItemTestResult) {
        this.includeTestItemTestResult = includeTestItemTestResult;
    }

    public Boolean getPendingResultApproval() {
        return pendingResultApproval;
    }

    public void setPendingResultApproval(Boolean pendingResultApproval) {
        this.pendingResultApproval = pendingResultApproval;
    }

    public boolean getOnlyPendingResultApproval() {
        return onlyPendingResultApproval;
    }

    public void setOnlyPendingResultApproval(boolean onlyPendingResultApproval) {
        this.onlyPendingResultApproval = onlyPendingResultApproval;
    }

    public boolean getPermApproval() {
        return permApproval;
    }

    public void setPermApproval(boolean permApproval) {
        this.permApproval = permApproval;
    }

    public boolean getIncludeTestItemConcept() {
        return includeTestItemConcept;
    }

    public void setIncludeTestItemConcept(boolean includeTestItemConcept) {
        this.includeTestItemConcept = includeTestItemConcept;
    }

    public boolean getIncludeTestItemWorksheetInfo() {
        return includeTestItemWorksheetInfo;
    }

    public void setIncludeTestItemWorksheetInfo(boolean includeTestItemWorksheetInfo) {
        this.includeTestItemWorksheetInfo = includeTestItemWorksheetInfo;
    }

    public boolean getIncludeTestItemTestResultApprovals() {
        return includeTestItemTestResultApprovals;
    }

    public void setIncludeTestItemTestResultApprovals(boolean includeTestItemTestResultApprovals) {
        this.includeTestItemTestResultApprovals = includeTestItemTestResultApprovals;
    }

    public RequestItemMatchOptions getRequestItemMatch() {
        return requestItemMatch;
    }

    public void setRequestItemMatch(RequestItemMatchOptions requestItemMatch) {
        this.requestItemMatch = requestItemMatch;
    }
}
