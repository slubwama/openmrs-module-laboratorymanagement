package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.reporting.ObsValue;

import java.util.Date;

public class TestRequestReportItemFilter {
    Integer patientId;
    Integer diagonisticLocationId;
    Date startDate;
    Date endDate;
    Integer testConceptId;
    ObsValue obsValue;
    Integer referralLocationId;
    Integer testerUserId;
    Integer approverUserId;
    Integer limit;
    Integer startIndex;
    Integer testRequestItemIdMin;
    Integer testRequestIdMin;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDiagonisticLocationId() {
        return diagonisticLocationId;
    }

    public void setDiagonisticLocationId(Integer diagonisticLocationId) {
        this.diagonisticLocationId = diagonisticLocationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTestConceptId() {
        return testConceptId;
    }

    public void setTestConceptId(Integer testConceptId) {
        this.testConceptId = testConceptId;
    }

    public ObsValue getObsValue() {
        return obsValue;
    }

    public void setObsValue(ObsValue obsValue) {
        this.obsValue = obsValue;
    }

    public Integer getReferralLocationId() {
        return referralLocationId;
    }

    public void setReferralLocationId(Integer referralLocationId) {
        this.referralLocationId = referralLocationId;
    }

    public Integer getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId(Integer approverUserId) {
        this.approverUserId = approverUserId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTestRequestItemIdMin() {
        return testRequestItemIdMin;
    }

    public void setTestRequestItemIdMin(Integer testRequestItemIdMin) {
        this.testRequestItemIdMin = testRequestItemIdMin;
    }

    public Integer getTestRequestIdMin() {
        return testRequestIdMin;
    }

    public void setTestRequestIdMin(Integer testRequestIdMin) {
        this.testRequestIdMin = testRequestIdMin;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getTesterUserId() {
        return testerUserId;
    }

    public void setTesterUserId(Integer testerUserId) {
        this.testerUserId = testerUserId;
    }
}
