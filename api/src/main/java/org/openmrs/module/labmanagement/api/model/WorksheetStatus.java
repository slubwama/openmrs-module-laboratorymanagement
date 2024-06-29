package org.openmrs.module.labmanagement.api.model;

public enum WorksheetStatus {
    PENDING,
    RESULTED,
    CANCELLED;

    public static boolean canEditWorksheet(WorksheetStatus status){
        return status != null && status.equals(PENDING);
    }
}
