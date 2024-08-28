package org.openmrs.module.labmanagement.api.dto;

import java.util.Date;

public class DashboardMetricsDTO {
    private String uuid;
    Long testsOrdered = 0L;
    Long testsToAccept = 0L;
    Long testsForSampleCollection = 0L;

    Long testsInProgress = 0L;
    Long testsOnWorksheet = 0L;
    Long testResultsRejected = 0L;

    Long testsPendingApproval = 0L;
    Long testsCompleted = 0L;

    Long testsRejected = 0L;

    Long testsReferredOut = 0L;
    Long testsReferredOutLab = 0L;
    Long testsReferredOutLabResulted = 0L;
    Long testsReferredOutProvider = 0L;

    public DashboardMetricsDTO() {
        uuid = new Date().getTime() + "";
    }

    public Long getTestsOrdered() {
        return testsToAccept + testsOrdered;
    }

    public void setTestsOrdered(Long testsOrdered) {
        this.testsOrdered = testsOrdered;
    }

    public Long getTestsToAccept() {
        return testsToAccept;
    }

    public void setTestsToAccept(Long testsToAccept) {
        this.testsToAccept = testsToAccept;
    }

    public Long getTestsForSampleCollection() {
        return testsForSampleCollection;
    }

    public void setTestsForSampleCollection(Long testsForSampleCollection) {
        this.testsForSampleCollection = testsForSampleCollection;
    }

    public Long getTestsInProgress() {
        return testsInProgress;
    }

    public void setTestsInProgress(Long testsInProgress) {
        this.testsInProgress = testsInProgress;
    }

    public Long getTestsOnWorksheet() {
        return testsOnWorksheet;
    }

    public void setTestsOnWorksheet(Long testsOnWorksheet) {
        this.testsOnWorksheet = testsOnWorksheet;
    }

    public Long getTestResultsRejected() {
        return testResultsRejected;
    }

    public void setTestResultsRejected(Long testResultsRejected) {
        this.testResultsRejected = testResultsRejected;
    }

    public Long getTestsPendingApproval() {
        return testsPendingApproval;
    }

    public void setTestsPendingApproval(Long testsPendingApproval) {
        this.testsPendingApproval = testsPendingApproval;
    }

    public Long getTestsCompleted() {
        return testsCompleted;
    }

    public void setTestsCompleted(Long testsCompleted) {
        this.testsCompleted = testsCompleted;
    }

    public Long getTestsRejected() {
        return testsRejected;
    }

    public void setTestsRejected(Long testsRejected) {
        this.testsRejected = testsRejected;
    }

    public Long getTestsReferredOut() {
        return testsReferredOutLab + testsReferredOutProvider;
    }

    public void setTestsReferredOut(Long testsReferredOut) {
        this.testsReferredOut = testsReferredOut;
    }

    public Long getTestsReferredOutLab() {
        return testsReferredOutLab;
    }

    public void setTestsReferredOutLab(Long testsReferredOutLab) {
        this.testsReferredOutLab = testsReferredOutLab;
    }

    public Long getTestsReferredOutLabResulted() {
        return testsReferredOutLabResulted;
    }

    public void setTestsReferredOutLabResulted(Long testsReferredOutLabResulted) {
        this.testsReferredOutLabResulted = testsReferredOutLabResulted;
    }

    public Long getTestsReferredOutProvider() {
        return testsReferredOutProvider;
    }

    public void setTestsReferredOutProvider(Long testsReferredOutProvider) {
        this.testsReferredOutProvider = testsReferredOutProvider;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
