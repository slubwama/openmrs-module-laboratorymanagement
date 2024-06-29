package org.openmrs.module.labmanagement.api.dto;

public class ReferralLocationSearchFilter extends SearchFilter {
    private Integer conceptId;
    private Integer patientId;
    private String conceptUuid;
    private String patientUuid;
    private String referralLocationUuid;
    private Integer referralLocationId;
    private Boolean Active;
    private Boolean referrerIn;
    private Boolean referrerOut;
    private String name;
    private String acronym;

    public Integer getConceptId() {
        return conceptId;
    }

    public void setConceptId(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getConceptUuid() {
        return conceptUuid;
    }

    public void setConceptUuid(String conceptUuid) {
        this.conceptUuid = conceptUuid;
    }

    public String getPatientUuid() {
        return patientUuid;
    }

    public void setPatientUuid(String patientUuid) {
        this.patientUuid = patientUuid;
    }

    public String getReferralLocationUuid() {
        return referralLocationUuid;
    }

    public void setReferralLocationUuid(String referralLocationUuid) {
        this.referralLocationUuid = referralLocationUuid;
    }

    public Integer getReferralLocationId() {
        return referralLocationId;
    }

    public void setReferralLocationId(Integer referralLocationId) {
        this.referralLocationId = referralLocationId;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public Boolean getReferrerIn() {
        return referrerIn;
    }

    public void setReferrerIn(Boolean referrerIn) {
        this.referrerIn = referrerIn;
    }

    public Boolean getReferrerOut() {
        return referrerOut;
    }

    public void setReferrerOut(Boolean referrerOut) {
        this.referrerOut = referrerOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
}
