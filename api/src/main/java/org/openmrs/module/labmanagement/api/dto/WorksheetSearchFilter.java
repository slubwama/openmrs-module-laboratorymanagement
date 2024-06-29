package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.WorksheetItemStatus;
import org.openmrs.module.labmanagement.api.model.WorksheetStatus;

import java.util.Date;
import java.util.List;

public class WorksheetSearchFilter extends SearchFilter {
    private Integer worksheetId;
    private String worksheetUuid;
    private Integer atLocationId;
    private List<Integer> testConceptIds;
    private List<WorksheetStatus> worksheetStatuses;
    private List<WorksheetItemStatus> worksheetItemStatuses;
    private boolean testConceptForWorksheetOnly =false;
    private boolean allItems = true;
    private Date minActivatedDate;
    private Date maxActivatedDate;
    private String sampleRef;
    private Integer patientId;
    private Integer responsiblePersonUserId;
    private Order.Urgency urgency;
    private boolean includeWorksheetItems = false;
    private boolean includeTestResultIds = false;
    private boolean includeWorksheetItemConcept = false;
    private boolean includeWorksheetItemTestResult = false;
    private boolean includeTestItemTestResultApprovals;

    public Integer getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(Integer worksheetId) {
        this.worksheetId = worksheetId;
    }

    public String getWorksheetUuid() {
        return worksheetUuid;
    }

    public void setWorksheetUuid(String worksheetUuid) {
        this.worksheetUuid = worksheetUuid;
    }

    public List<Integer> getTestConceptIds() {
        return testConceptIds;
    }

    public void setTestConceptIds(List<Integer> testConceptIds) {
        this.testConceptIds = testConceptIds;
    }

    public List<WorksheetStatus> getWorksheetStatuses() {
        return worksheetStatuses;
    }

    public void setWorksheetStatuses(List<WorksheetStatus> worksheetStatuses) {
        this.worksheetStatuses = worksheetStatuses;
    }

    public List<WorksheetItemStatus> getWorksheetItemStatuses() {
        return worksheetItemStatuses;
    }

    public void setWorksheetItemStatuses(List<WorksheetItemStatus> worksheetItemStatuses) {
        this.worksheetItemStatuses = worksheetItemStatuses;
    }

    public boolean getTestConceptForWorksheetOnly() {
        return testConceptForWorksheetOnly;
    }

    public void setTestConceptForWorksheetOnly(boolean testConceptForWorksheetOnly) {
        this.testConceptForWorksheetOnly = testConceptForWorksheetOnly;
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

    public Integer getResponsiblePersonUserId() {
        return responsiblePersonUserId;
    }

    public void setResponsiblePersonUserId(Integer responsiblePersonUserId) {
        this.responsiblePersonUserId = responsiblePersonUserId;
    }

    public Integer getAtLocationId() {
        return atLocationId;
    }

    public void setAtLocationId(Integer atLocationId) {
        this.atLocationId = atLocationId;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public boolean getIncludeWorksheetItems() {
        return includeWorksheetItems;
    }

    public void setIncludeWorksheetItems(boolean includeWorksheetItems) {
        this.includeWorksheetItems = includeWorksheetItems;
    }

    public boolean getAllItems() {
        return allItems;
    }

    public void setAllItems(boolean allItems) {
        this.allItems = allItems;
    }

    public boolean getIncludeTestResultIds() {
        return includeTestResultIds;
    }

    public void setIncludeTestResultIds(boolean includeTestResultIds) {
        this.includeTestResultIds = includeTestResultIds;
    }

    public boolean getIncludeWorksheetItemConcept() {
        return includeWorksheetItemConcept;
    }

    public void setIncludeWorksheetItemConcept(boolean includeWorksheetItemConcept) {
        this.includeWorksheetItemConcept = includeWorksheetItemConcept;
    }

    public boolean getIncludeWorksheetItemTestResult() {
        return includeWorksheetItemTestResult;
    }

    public void setIncludeWorksheetItemTestResult(boolean includeWorksheetItemTestResult) {
        this.includeWorksheetItemTestResult = includeWorksheetItemTestResult;
    }

    public boolean getIncludeTestItemTestResultApprovals() {
        return includeTestItemTestResultApprovals;
    }

    public void setIncludeTestItemTestResultApprovals(boolean includeTestItemTestResultApprovals) {
        this.includeTestItemTestResultApprovals = includeTestItemTestResultApprovals;
    }
}
