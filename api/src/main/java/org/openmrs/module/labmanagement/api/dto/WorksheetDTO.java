package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.WorksheetStatus;

import java.util.Date;
import java.util.List;

public class WorksheetDTO {
    private Integer id;
    private String uuid;
    private Integer atLocationId;
    private String atLocationUuid;
    private String atLocationName;
    private Date worksheetDate;
    private String worksheetNo;
    private String remarks;
    private Integer testId;
    private String testUuid;
    private String testName;
    private String testShortName;
    private Integer diagnosisTypeId;
    private String diagnosisTypeUuid;
    private String diagnosisTypeName;
    private WorksheetStatus status;
    private Integer responsiblePersonId;
    private String responsiblePersonUuid;
    private String responsiblePersonGivenName;
    private String responsiblePersonMiddleName;
    private String responsiblePersonFamilyName;
    private String responsiblePersonOther;

    private boolean voided;
    private Integer creator;
    private String creatorUuid;
    private String creatorGivenName;
    private String creatorFamilyName;
    private Date dateCreated;
    private Integer changedBy;
    private String changedByUuid;
    private String changedByGivenName;
    private String changedByFamilyName;
    private Date dateChanged;
    private List<WorksheetItemDTO> worksheetItems;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAtLocationId() {
        return atLocationId;
    }

    public void setAtLocationId(Integer atLocationId) {
        this.atLocationId = atLocationId;
    }

    public Date getWorksheetDate() {
        return worksheetDate;
    }

    public void setWorksheetDate(Date worksheetDate) {
        this.worksheetDate = worksheetDate;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getDiagnosisTypeId() {
        return diagnosisTypeId;
    }

    public void setDiagnosisTypeId(Integer diagnosisTypeId) {
        this.diagnosisTypeId = diagnosisTypeId;
    }

    public WorksheetStatus getStatus() {
        return status;
    }

    public void setStatus(WorksheetStatus status) {
        this.status = status;
    }

    public Integer getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Integer responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getResponsiblePersonOther() {
        return responsiblePersonOther;
    }

    public void setResponsiblePersonOther(String responsiblePersonOther) {
        this.responsiblePersonOther = responsiblePersonOther;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAtLocationUuid() {
        return atLocationUuid;
    }

    public void setAtLocationUuid(String atLocationUuid) {
        this.atLocationUuid = atLocationUuid;
    }

    public String getAtLocationName() {
        return atLocationName;
    }

    public void setAtLocationName(String atLocationName) {
        this.atLocationName = atLocationName;
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestShortName() {
        return testShortName;
    }

    public void setTestShortName(String testShortName) {
        this.testShortName = testShortName;
    }

    public String getDiagnosisTypeName() {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName) {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    public String getResponsiblePersonUuid() {
        return responsiblePersonUuid;
    }

    public void setResponsiblePersonUuid(String responsiblePersonUuid) {
        this.responsiblePersonUuid = responsiblePersonUuid;
    }

    public String getResponsiblePersonGivenName() {
        return responsiblePersonGivenName;
    }

    public void setResponsiblePersonGivenName(String responsiblePersonGivenName) {
        this.responsiblePersonGivenName = responsiblePersonGivenName;
    }

    public String getResponsiblePersonMiddleName() {
        return responsiblePersonMiddleName;
    }

    public void setResponsiblePersonMiddleName(String responsiblePersonMiddleName) {
        this.responsiblePersonMiddleName = responsiblePersonMiddleName;
    }

    public String getResponsiblePersonFamilyName() {
        return responsiblePersonFamilyName;
    }

    public void setResponsiblePersonFamilyName(String responsiblePersonFamilyName) {
        this.responsiblePersonFamilyName = responsiblePersonFamilyName;
    }

    public boolean getVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public String getCreatorGivenName() {
        return creatorGivenName;
    }

    public void setCreatorGivenName(String creatorGivenName) {
        this.creatorGivenName = creatorGivenName;
    }

    public String getCreatorFamilyName() {
        return creatorFamilyName;
    }

    public void setCreatorFamilyName(String creatorFamilyName) {
        this.creatorFamilyName = creatorFamilyName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedByUuid() {
        return changedByUuid;
    }

    public void setChangedByUuid(String changedByUuid) {
        this.changedByUuid = changedByUuid;
    }

    public String getChangedByGivenName() {
        return changedByGivenName;
    }

    public void setChangedByGivenName(String changedByGivenName) {
        this.changedByGivenName = changedByGivenName;
    }

    public String getChangedByFamilyName() {
        return changedByFamilyName;
    }

    public void setChangedByFamilyName(String changedByFamilyName) {
        this.changedByFamilyName = changedByFamilyName;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getTestUuid() {
        return testUuid;
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    public String getDiagnosisTypeUuid() {
        return diagnosisTypeUuid;
    }

    public void setDiagnosisTypeUuid(String diagnosisTypeUuid) {
        this.diagnosisTypeUuid = diagnosisTypeUuid;
    }

    public List<WorksheetItemDTO> getWorksheetItems() {
        return worksheetItems;
    }

    public void setWorksheetItems(List<WorksheetItemDTO> worksheetItems) {
        this.worksheetItems = worksheetItems;
    }
}
