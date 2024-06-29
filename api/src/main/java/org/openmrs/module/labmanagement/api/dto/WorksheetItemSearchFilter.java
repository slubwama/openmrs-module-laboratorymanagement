package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.WorksheetItemStatus;
import org.openmrs.module.labmanagement.api.model.WorksheetStatus;

import java.util.Date;
import java.util.List;

public class WorksheetItemSearchFilter extends SearchFilter {
    private List<String> TestRequestItemSampleIds;
    private Integer worksheetItemId;
    private String worksheetItemUuid;
    private Integer itemLocationId;
    private List<Integer> worksheetIds;
    private List<Integer> testConceptIds;
    private List<WorksheetItemStatus> worksheetItemStatuses;
    private String sampleRef;
    private Integer patientId;
    private Order.Urgency urgency;
    private boolean includeTestResultId = false;
    private boolean includeTestConcept = false;
    private boolean includeTestResult = false;
    private  boolean isIncludeTestResultApprovals = false;

    public Integer getWorksheetItemId() {
        return worksheetItemId;
    }

    public void setWorksheetItemId(Integer worksheetItemId) {
        this.worksheetItemId = worksheetItemId;
    }

    public String getWorksheetItemUuid() {
        return worksheetItemUuid;
    }

    public void setWorksheetItemUuid(String worksheetItemUuid) {
        this.worksheetItemUuid = worksheetItemUuid;
    }

    public Integer getItemLocationId() {
        return itemLocationId;
    }

    public void setItemLocationId(Integer itemLocationId) {
        this.itemLocationId = itemLocationId;
    }

    public List<Integer> getWorksheetIds() {
        return worksheetIds;
    }

    public void setWorksheetIds(List<Integer> worksheetIds) {
        this.worksheetIds = worksheetIds;
    }

    public List<Integer> getTestConceptIds() {
        return testConceptIds;
    }

    public void setTestConceptIds(List<Integer> testConceptIds) {
        this.testConceptIds = testConceptIds;
    }

    public List<WorksheetItemStatus> getWorksheetItemStatuses() {
        return worksheetItemStatuses;
    }

    public void setWorksheetItemStatuses(List<WorksheetItemStatus> worksheetItemStatuses) {
        this.worksheetItemStatuses = worksheetItemStatuses;
    }

    public String getSampleRef() {
        return sampleRef;
    }

    public void setSampleRef(String sampleRef) {
        this.sampleRef = sampleRef;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public boolean getIncludeTestResultId() {
        return includeTestResultId;
    }

    public void setIncludeTestResultId(boolean includeTestResultId) {
        this.includeTestResultId = includeTestResultId;
    }

    public boolean getIncludeTestConcept() {
        return includeTestConcept;
    }

    public void setIncludeTestConcept(boolean includeTestConcept) {
        this.includeTestConcept = includeTestConcept;
    }

    public boolean getIncludeTestResult() {
        return includeTestResult;
    }

    public void setIncludeTestResult(boolean includeTestResult) {
        this.includeTestResult = includeTestResult;
    }

    public List<String> getTestRequestItemSampleIds() {
        return TestRequestItemSampleIds;
    }

    public void setTestRequestItemSampleIds(List<String> testRequestItemSampleIds) {
        TestRequestItemSampleIds = testRequestItemSampleIds;
    }

    public boolean getIncludeTestResultApprovals() {
        return isIncludeTestResultApprovals;
    }

    public void setIncludeTestResultApprovals(boolean includeTestResultApprovals) {
        isIncludeTestResultApprovals = includeTestResultApprovals;
    }
}
