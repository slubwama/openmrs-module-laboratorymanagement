package org.openmrs.module.labmanagement.api.model;

public enum WorksheetItemStatus {
    PENDING,
    RESULTED,
    RESULTED_REJECTED,
    CANCELLED;

    public static boolean canRegisterTestResults(WorksheetItemStatus status){
        return  status != null && status != CANCELLED;
    }
}
