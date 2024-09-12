package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.StorageStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;

import java.util.Date;
import java.util.List;

public class SampleSearchFilter extends SearchFilter {
    private Integer sampleId;
    private String sampleUuid;
    private Integer patientId;
    private Integer sampleTypeId;
    private Date minCollectionDate;
    private Date maxCollectionDate;
    private List<SampleStatus> sampleStatuses;
    private List<StorageStatus> storageStatuses;
    private  List<TestRequestItemStatus> testRequestItemStatuses;
    private Integer testRequestId;
    private List<Integer> testRequestIds;
    private List<Integer> testRequestItemConceptIds;
    private String reference;
    private boolean includeSamplesInStorage;
    private boolean includeTests;
    private boolean allTests = true;
    private Integer locationId;
    private Integer testItemlocationId;
    private  List<Integer> sampleIds;
    private Date minActivatedDate;
    private Date maxActivatedDate;
    private Order.Urgency Urgency;
    private boolean forWorksheet = false;
    private boolean referenceOrForWorksheet = false;
    private Integer storageId;
    private Boolean repository;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(Integer sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public Date getMinCollectionDate() {
        return minCollectionDate;
    }

    public void setMinCollectionDate(Date minCollectionDate) {
        this.minCollectionDate = minCollectionDate;
    }

    public Date getMaxCollectionDate() {
        return maxCollectionDate;
    }

    public void setMaxCollectionDate(Date maxCollectionDate) {
        this.maxCollectionDate = maxCollectionDate;
    }

    public List<SampleStatus> getSampleStatuses() {
        return sampleStatuses;
    }

    public void setSampleStatuses(List<SampleStatus> sampleStatuses) {
        this.sampleStatuses = sampleStatuses;
    }

    public Integer getTestRequestId() {
        return testRequestId;
    }

    public void setTestRequestId(Integer testRequestId) {
        this.testRequestId = testRequestId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean getIncludeSamplesInStorage() {
        return includeSamplesInStorage;
    }

    public void setIncludeSamplesInStorage(boolean includeSamplesInStorage) {
        this.includeSamplesInStorage = includeSamplesInStorage;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(String sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public List<Integer> getSampleIds() {
        return sampleIds;
    }

    public void setSampleIds(List<Integer> sampleIds) {
        this.sampleIds = sampleIds;
    }

    public List<Integer> getTestRequestIds() {
        return testRequestIds;
    }

    public void setTestRequestIds(List<Integer> testRequestIds) {
        this.testRequestIds = testRequestIds;
    }

    public boolean getIncludeTests() {
        return includeTests;
    }

    public void setIncludeTests(boolean includeTests) {
        this.includeTests = includeTests;
    }

    public List<Integer> getTestRequestItemConceptIds() {
        return testRequestItemConceptIds;
    }

    public void setTestRequestItemConceptIds(List<Integer> testRequestItemConceptIds) {
        this.testRequestItemConceptIds = testRequestItemConceptIds;
    }

    public boolean getAllTests() {
        return allTests;
    }

    public void setAllTests(boolean allTests) {
        this.allTests = allTests;
    }

    public Integer getTestItemlocationId() {
        return testItemlocationId;
    }

    public void setTestItemlocationId(Integer testItemlocationId) {
        this.testItemlocationId = testItemlocationId;
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

    public Order.Urgency getUrgency() {
        return Urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        Urgency = urgency;
    }

    public boolean getForWorksheet() {
        return forWorksheet;
    }

    public void setForWorksheet(boolean forWorksheet) {
        this.forWorksheet = forWorksheet;
    }

    public boolean getReferenceOrForWorksheet() {
        return referenceOrForWorksheet;
    }

    public void setReferenceOrForWorksheet(boolean referenceOrForWorksheet) {
        this.referenceOrForWorksheet = referenceOrForWorksheet;
    }

    public List<TestRequestItemStatus> getTestRequestItemStatuses() {
        return testRequestItemStatuses;
    }

    public void setTestRequestItemStatuses(List<TestRequestItemStatus> testRequestItemStatuses) {
        this.testRequestItemStatuses = testRequestItemStatuses;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public List<StorageStatus> getStorageStatuses() {
        return storageStatuses;
    }

    public void setStorageStatuses(List<StorageStatus> storageStatuses) {
        this.storageStatuses = storageStatuses;
    }

    public Boolean getRepository() {
        return repository;
    }

    public void setRepository(Boolean repository) {
        this.repository = repository;
    }
}
