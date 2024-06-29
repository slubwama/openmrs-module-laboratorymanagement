package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "labmanagement.Worksheet")
@Table(name = "labmgmt_worksheet")
public class Worksheet extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worksheet_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_location_id", nullable = false)
    private Location atLocation;

    @Column(name = "worksheet_date", nullable = false)
    private Date worksheetDate;
    @Column(name = "worksheet_no", nullable = true)
    private String worksheetNo;

    @Column(name = "remarks", nullable = true)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = true)
    private Concept test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_type_id", nullable = true)
    private Concept diagnosisType;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private WorksheetStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_person")
    private User responsiblePerson;

    @Column(name = "responsible_person_other", length = 150)
    private String responsiblePersonOther;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getAtLocation() {
        return atLocation;
    }

    public void setAtLocation(Location atLocation) {
        this.atLocation = atLocation;
    }

    public Date getWorksheetDate() {
        return worksheetDate;
    }

    public void setWorksheetDate(Date worksheetDate) {
        this.worksheetDate = worksheetDate;
    }

    public Concept getTest() {
        return test;
    }

    public void setTest(Concept test) {
        this.test = test;
    }

    public Concept getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(Concept diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public WorksheetStatus getStatus() {
        return status;
    }

    public void setStatus(WorksheetStatus status) {
        this.status = status;
    }

    public User getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(User responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePersonOther() {
        return responsiblePersonOther;
    }

    public void setResponsiblePersonOther(String responsiblePersonOther) {
        this.responsiblePersonOther = responsiblePersonOther;
    }

    public String getWorksheetNo() {
        return worksheetNo;
    }

    public void setWorksheetNo(String worksheetNo) {
        this.worksheetNo = worksheetNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
