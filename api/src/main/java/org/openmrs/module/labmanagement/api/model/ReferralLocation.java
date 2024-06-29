package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Patient;

import javax.persistence.*;

@Entity(name = "labmanagement.ReferralLocation")
@Table(name = "labmgmt_referral_location")
public class ReferralLocation extends BaseChangeableOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referral_location_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concept_id")
    private Concept concept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "referrer_in", nullable = false)
    private Boolean referrerIn = false;

    @Column(name = "referrer_out", nullable = false)
    private Boolean referrerOut;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "name", length = 250)
    private String name;

    @Column(name = "acronym", length = 250)
    private String acronym;

    @Column(name = "is_system", nullable = false)
    private Boolean system;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }
}
