package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class TestRequestSampleDTO {
    private String sampleTypeUuid;
    private String accessionNumber;
    private String externalRef;
    private List<TestRequestItemDTO> tests;

    public String getSampleTypeUuid() {
        return sampleTypeUuid;
    }

    public void setSampleTypeUuid(String sampleTypeUuid) {
        this.sampleTypeUuid = sampleTypeUuid;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public List<TestRequestItemDTO> getTests() {
        return tests;
    }

    public void setTests(List<TestRequestItemDTO> tests) {
        this.tests = tests;
    }
}
