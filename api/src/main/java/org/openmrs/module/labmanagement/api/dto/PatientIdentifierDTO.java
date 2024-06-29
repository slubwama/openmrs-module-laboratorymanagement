package org.openmrs.module.labmanagement.api.dto;

public class PatientIdentifierDTO {
    private Integer patientId;
    private String identifier;
    private String identifierTypeUuid;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierTypeUuid() {
        return identifierTypeUuid;
    }

    public void setIdentifierTypeUuid(String identifierTypeUuid) {
        this.identifierTypeUuid = identifierTypeUuid;
    }
}
