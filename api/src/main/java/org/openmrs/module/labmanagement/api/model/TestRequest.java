package org.openmrs.module.labmanagement.api.model;

import org.openmrs.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity(name = "labmanagement.TestRequest")
@Table(name = "labmgmt_test_request")
public class TestRequest extends BaseOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_request_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @Column(name = "request_no", length = 100)
    private String requestNo;

    @Column(name = "urgency", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Order.Urgency urgency;

    @Column(name = "clinical_note", nullable = true, length = 500)
    private String clinicalNote;

    @Column(name = "request_reason", nullable = true, length = 500)
    private String requestReason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_setting_id")
    private CareSetting careSetting;

    @Column(name = "date_stopped")
    private Date dateStopped;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TestRequestStatus status;

    @Column(name = "at_location_id", nullable = false)
    private Integer atLocationId;

    @Column(name = "referred_in", nullable = false)
    private Boolean referredIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_from_facility_id")
    private ReferralLocation referralFromFacility;

    @Column(name = "referral_from_facility_name")
    private String referralFromFacilityName;

    @Column(name = "referral_in_external_ref", length = 50)
    private String referralInExternalRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Order.Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Order.Urgency urgency) {
        this.urgency = urgency;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public CareSetting getCareSetting() {
        return careSetting;
    }

    public void setCareSetting(CareSetting careSetting) {
        this.careSetting = careSetting;
    }

    public Date getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
    }

    public TestRequestStatus getStatus() {
        return status;
    }

    public void setStatus(TestRequestStatus status) {
        this.status = status;
    }

    public Integer getAtLocationId() {
        return atLocationId;
    }

    public void setAtLocationId(Integer atLocationId) {
        this.atLocationId = atLocationId;
    }

    public Boolean getReferredIn() {
        return referredIn;
    }

    public void setReferredIn(Boolean referredIn) {
        this.referredIn = referredIn;
    }

    public ReferralLocation getReferralFromFacility() {
        return referralFromFacility;
    }

    public void setReferralFromFacility(ReferralLocation referralFromFacility) {
        this.referralFromFacility = referralFromFacility;
    }

    public String getReferralFromFacilityName() {
        return referralFromFacilityName;
    }

    public void setReferralFromFacilityName(String referralFromFacilityName) {
        this.referralFromFacilityName = referralFromFacilityName;
    }

    public String getReferralInExternalRef() {
        return referralInExternalRef;
    }

    public void setReferralInExternalRef(String referralInExternalRef) {
        this.referralInExternalRef = referralInExternalRef;
    }

    public String getClinicalNote() {
        return clinicalNote;
    }

    public void setClinicalNote(String clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }
}
