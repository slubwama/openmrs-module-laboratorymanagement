package org.openmrs.module.labmanagement.api.model;

import org.openmrs.module.labmanagement.api.dto.TestResultDTO;

import java.util.Arrays;
import java.util.List;

public enum TestRequestItemStatus {
    REFERRED_OUT_PROVIDER(),
    REQUEST_APPROVAL(),
    SAMPLE_COLLECTION(),
    REFERRED_OUT_LAB(),
    IN_PROGRESS(),
    CANCELLED(),
    COMPLETED();

    public static List<TestRequestItemStatus> getStatusesNotApplicableForWorksheet(){
        return Arrays.asList(REFERRED_OUT_PROVIDER, REFERRED_OUT_LAB, CANCELLED);
    }

    public static boolean isRequestApproveable(TestRequestItemStatus testRequestItemStatus, ApprovalResult action) {
        return testRequestItemStatus != null && (testRequestItemStatus.equals(REQUEST_APPROVAL) ||
                (action == ApprovalResult.REJECTED && canReject(testRequestItemStatus)));
    }

    public  static  boolean isPending(TestRequestItemStatus testRequestItemStatus){
        return !isCompletedProcess(testRequestItemStatus) && !isCancelled(testRequestItemStatus);
    }

    public  static  boolean isCompletedProcess(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(COMPLETED) ||
                testRequestItemStatus.equals(CANCELLED) ||
                testRequestItemStatus.equals(REFERRED_OUT_PROVIDER));
    }

    public  static  boolean isCancelled(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && testRequestItemStatus.equals(CANCELLED);
    }

    public  static  boolean canModifyTestSamples(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(SAMPLE_COLLECTION));
    }

    public  static  boolean canModifyReferralInformation(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(SAMPLE_COLLECTION) || testRequestItemStatus.equals(IN_PROGRESS) );
    }

    public static boolean canReleaseSamplesForTesting(TestRequestItemStatus testRequestItemStatus) {
        return testRequestItemStatus != null && (testRequestItemStatus.equals(SAMPLE_COLLECTION) ||
                testRequestItemStatus.equals(REFERRED_OUT_LAB) ||
                testRequestItemStatus.equals(IN_PROGRESS));
    }

    public static boolean needsReleaseSamplesForTesting(TestRequestItemStatus testRequestItemStatus) {
        return testRequestItemStatus != null && (testRequestItemStatus.equals(SAMPLE_COLLECTION) ||
                testRequestItemStatus.equals(REFERRED_OUT_LAB));
    }

    public  static  boolean canApprove(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(TestRequestItemStatus.REQUEST_APPROVAL));
    }
    public  static  boolean canReject(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(REQUEST_APPROVAL) ||
                testRequestItemStatus.equals(SAMPLE_COLLECTION) ||
                testRequestItemStatus.equals(IN_PROGRESS));
    }

    public  static  boolean canUpdateTestResult(TestRequestItemStatus testRequestItemStatus, TestResultDTO testResult){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(IN_PROGRESS) ||
                testRequestItemStatus.equals(REFERRED_OUT_LAB) ||
                (testResult != null && !testResult.getRequireApproval() && testRequestItemStatus.equals(COMPLETED)) );
    }

    public  static  boolean canDoSampleCollection(TestRequestItemStatus testRequestItemStatus){
        return testRequestItemStatus != null && (testRequestItemStatus.equals(SAMPLE_COLLECTION) ||
                testRequestItemStatus.equals(IN_PROGRESS));
    }
}
