package org.openmrs.module.labmanagement.api.dto;

public class UserPersonNameDTO {
	Integer userId;
	Integer personId;
	Integer patientId;
	private String givenName;
	private String middleName;
	private String familyName;
	private String uuid;
	private String patientIdentifier;
	Integer USERID;
	Integer PATIENTID;
	Integer PERSONID;
	private String GIVENNAME;
	private String MIDDLENAME;
	private String FAMILYNAME;
	private String UUID;
	private String PATIENTIDENTIFIER;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPatientIdentifier() {
		return patientIdentifier;
	}

	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}

	public Integer getUSERID() {
		return USERID;
	}

	public void setUSERID(Integer USERID) {
		this.USERID = USERID;
		this.userId = USERID;
	}

	public Integer getPATIENTID() {
		return PATIENTID;
	}

	public void setPATIENTID(Integer PATIENTID) {
		this.PATIENTID = PATIENTID;
		this.patientId = PATIENTID;
	}

	public String getGIVENNAME() {
		return GIVENNAME;
	}

	public void setGIVENNAME(String GIVENNAME) {
		this.GIVENNAME = GIVENNAME;
		this.givenName = GIVENNAME;
	}

	public String getMIDDLENAME() {
		return MIDDLENAME;
	}

	public void setMIDDLENAME(String MIDDLENAME) {
		this.MIDDLENAME = MIDDLENAME;
		this.middleName = MIDDLENAME;
	}

	public String getFAMILYNAME() {
		return FAMILYNAME;
	}

	public void setFAMILYNAME(String FAMILYNAME) {
		this.FAMILYNAME = FAMILYNAME;
		this.familyName = FAMILYNAME;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
		this.uuid = UUID;
	}

	public String getPATIENTIDENTIFIER() {
		return PATIENTIDENTIFIER;
	}

	public void setPATIENTIDENTIFIER(String PATIENTIDENTIFIER) {
		this.PATIENTIDENTIFIER = PATIENTIDENTIFIER;
		this.patientIdentifier = PATIENTIDENTIFIER;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getPERSONID() {
		return PERSONID;
	}

	public void setPERSONID(Integer PERSONID) {
		this.PERSONID = PERSONID;
		this.personId = PERSONID;
	}
}
