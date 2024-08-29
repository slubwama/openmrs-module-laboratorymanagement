package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;

import java.util.Date;
import java.util.List;

public class TestRequestItemSearchFilter extends SearchFilter {
    private Integer testRequestItemId;
    private String testRequestItemUuid;
    private List<Integer> testRequestIds;
    private List<TestRequestItemStatus> itemStatuses;
    private List<Integer> testConceptIds;
    private Integer itemLocationId;
    private Boolean referredOut;
    private Integer patientId;
    private boolean includeTestSamples = false;
    private Boolean pendingResultApproval;
    private boolean onlyPendingResultApproval = false;
    private boolean includeTestResult = false;

    private boolean includeTestResultApprovals = false;
    private boolean permApproval=false;
    private boolean includeTestConcept = false;
    private boolean includeTestWorksheetInfo=false;
    private RequestItemMatchOptions itemMatch;

    public Integer getTestRequestItemId() {
        return testRequestItemId;
    }

    public void setTestRequestItemId(Integer testRequestItemId) {
        this.testRequestItemId = testRequestItemId;
    }

    public String getTestRequestItemUuid() {
        return testRequestItemUuid;
    }

    public void setTestRequestItemUuid(String testRequestItemUuid) {
        this.testRequestItemUuid = testRequestItemUuid;
    }

    public List<Integer> getTestRequestIds() {
        return testRequestIds;
    }

    public void setTestRequestIds(List<Integer> testRequestIds) {
        this.testRequestIds = testRequestIds;
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

    public boolean getIncludeTestSamples() {
        return includeTestSamples;
    }

    public void setIncludeTestSamples(boolean includeTestSamples) {
        this.includeTestSamples = includeTestSamples;
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

    public boolean getIncludeTestResult() {
        return includeTestResult;
    }

    public void setIncludeTestResult(boolean includeTestResult) {
        this.includeTestResult = includeTestResult;
    }

    public boolean getPermApproval() {
        return permApproval;
    }

    public void setPermApproval(boolean permApproval) {
        this.permApproval = permApproval;
    }

    public boolean getIncludeTestConcept() {
        return includeTestConcept;
    }

    public void setIncludeTestConcept(boolean includeTestConcept) {
        this.includeTestConcept = includeTestConcept;
    }

    public boolean getIncludeTestWorksheetInfo() {
        return includeTestWorksheetInfo;
    }

    public void setIncludeTestWorksheetInfo(boolean includeTestWorksheetInfo) {
        this.includeTestWorksheetInfo = includeTestWorksheetInfo;
    }

    public boolean getIncludeTestResultApprovals() {
        return includeTestResultApprovals;
    }

    public void setIncludeTestResultApprovals(boolean includeTestResultApprovals) {
        this.includeTestResultApprovals = includeTestResultApprovals;
    }

    public RequestItemMatchOptions getItemMatch() {
        return itemMatch;
    }

    public void setItemMatch(RequestItemMatchOptions itemMatch) {
        this.itemMatch = itemMatch;
    }
}
