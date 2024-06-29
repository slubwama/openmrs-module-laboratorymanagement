package org.openmrs.module.labmanagement.api.model;

public enum TestRequestStatus {
    IN_PROGRESS(),
    CANCELLED(),
    COMPLETED();

    public static boolean isRequestApproveable(TestRequestStatus testRequestStatus) {
        return testRequestStatus != null && (testRequestStatus == IN_PROGRESS);
    }

    public static boolean isNotCompleted(TestRequestStatus testRequestStatus) {
        return testRequestStatus != null && (testRequestStatus != COMPLETED && testRequestStatus != CANCELLED);
    }

    public static boolean canReleaseSamplesForTesting(TestRequestStatus testRequestStatus) {
        return testRequestStatus != null && (testRequestStatus == IN_PROGRESS);
    }

}
