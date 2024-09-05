package org.openmrs.module.labmanagement.api.dto;

public class SummarizedTestReportItem {
    private Integer orderConceptId;
    private String testName;
    private String testShortName;
    Long testsCompleted = 0L;
    private Integer  testerUserId;
    private String testerFamilyName;
    private String testerMiddleName;
    private String testerGivenName;

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

    public Long getTestsCompleted() {
        return testsCompleted;
    }

    public void setTestsCompleted(Long testsCompleted) {
        this.testsCompleted = testsCompleted;
    }

    public Integer getTesterUserId() {
        return testerUserId;
    }

    public void setTesterUserId(Integer testerUserId) {
        this.testerUserId = testerUserId;
    }

    public String getTesterFamilyName() {
        return testerFamilyName;
    }

    public void setTesterFamilyName(String testerFamilyName) {
        this.testerFamilyName = testerFamilyName;
    }

    public String getTesterMiddleName() {
        return testerMiddleName;
    }

    public void setTesterMiddleName(String testerMiddleName) {
        this.testerMiddleName = testerMiddleName;
    }

    public String getTesterGivenName() {
        return testerGivenName;
    }

    public void setTesterGivenName(String testerGivenName) {
        this.testerGivenName = testerGivenName;
    }
}
