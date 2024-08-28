/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.labmanagement.dao;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.labmanagement.EntityUtil;
import org.openmrs.module.labmanagement.api.dao.LabManagementDao;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class LabManagementDaoTest extends BaseModuleContextSensitiveTest {

    private EntityUtil entityUtil;
    @Autowired
    DbSessionFactory sessionFactory;
    private LabManagementDao daoInstance;

    private LabManagementDao dao() {
        if (daoInstance == null) {
            daoInstance = new LabManagementDao();
            daoInstance.setSessionFactory(sessionFactory);
        }
        return daoInstance;
    }

    @Before
    public void setup() throws Exception {
        initializeInMemoryDatabase();
        //executeDataSet(EntityUtil.STOCK_OPERATION_TYPE_DATA_SET);
    }

    private EntityUtil eu() {
        if (entityUtil == null) {
            Drug drug = Context.getConceptService().getDrug(3);
            Concept concept = drug.getConcept();
            Role role = Context.getUserService().getRole("Provider");
            Patient patient = Context.getPatientService().getPatient(2);
            User user = Context.getUserService().getUser(501);
            Location location = Context.getLocationService().getLocation(1);
            entityUtil = new EntityUtil(drug, user, location, role, concept, patient);
        }
        return entityUtil;
    }

    @Test
    public void saveWorksheet_shouldSaveAllProperties(){
//Given
        Worksheet worksheet=eu().newWorksheet(dao());

//When
        dao().saveWorksheet(worksheet);

//Let's clean up the cache to be sure getWorksheetByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        Worksheet savedWorksheet = dao().getWorksheetByUuid(worksheet.getUuid());
        assertThat(savedWorksheet, hasProperty("uuid", is(worksheet.getUuid())));
        assertThat(savedWorksheet, hasProperty("creator", is(worksheet.getCreator())));
        assertThat(savedWorksheet, hasProperty("dateCreated", is(worksheet.getDateCreated())));
        assertThat(savedWorksheet, hasProperty("changedBy", is(worksheet.getChangedBy())));
        assertThat(savedWorksheet, hasProperty("dateChanged", is(worksheet.getDateChanged())));
        assertThat(savedWorksheet, hasProperty("voided", is(worksheet.getVoided())));
        assertThat(savedWorksheet, hasProperty("dateVoided", is(worksheet.getDateVoided())));
        assertThat(savedWorksheet, hasProperty("voidedBy", is(worksheet.getVoidedBy())));
        assertThat(savedWorksheet, hasProperty("voidReason", is(worksheet.getVoidReason())));
        assertThat(savedWorksheet, hasProperty("atLocation", is(worksheet.getAtLocation())));
        assertThat(savedWorksheet, hasProperty("worksheetDate", is(worksheet.getWorksheetDate())));
        assertThat(savedWorksheet, hasProperty("test", is(worksheet.getTest())));
        assertThat(savedWorksheet, hasProperty("diagnosisType", is(worksheet.getDiagnosisType())));
        assertThat(savedWorksheet, hasProperty("status", is(worksheet.getStatus())));
        assertThat(savedWorksheet, hasProperty("responsiblePerson", is(worksheet.getResponsiblePerson())));
        assertThat(savedWorksheet, hasProperty("responsiblePersonOther", is(worksheet.getResponsiblePersonOther())));

        savedWorksheet = dao().getWorksheetById(worksheet.getId());
        assertThat(savedWorksheet, hasProperty("uuid", is(worksheet.getUuid())));
        assertThat(savedWorksheet, hasProperty("creator", is(worksheet.getCreator())));
        assertThat(savedWorksheet, hasProperty("dateCreated", is(worksheet.getDateCreated())));
        assertThat(savedWorksheet, hasProperty("changedBy", is(worksheet.getChangedBy())));
        assertThat(savedWorksheet, hasProperty("dateChanged", is(worksheet.getDateChanged())));
        assertThat(savedWorksheet, hasProperty("voided", is(worksheet.getVoided())));
        assertThat(savedWorksheet, hasProperty("dateVoided", is(worksheet.getDateVoided())));
        assertThat(savedWorksheet, hasProperty("voidedBy", is(worksheet.getVoidedBy())));
        assertThat(savedWorksheet, hasProperty("voidReason", is(worksheet.getVoidReason())));
        assertThat(savedWorksheet, hasProperty("atLocation", is(worksheet.getAtLocation())));
        assertThat(savedWorksheet, hasProperty("worksheetDate", is(worksheet.getWorksheetDate())));
        assertThat(savedWorksheet, hasProperty("test", is(worksheet.getTest())));
        assertThat(savedWorksheet, hasProperty("diagnosisType", is(worksheet.getDiagnosisType())));
        assertThat(savedWorksheet, hasProperty("status", is(worksheet.getStatus())));
        assertThat(savedWorksheet, hasProperty("responsiblePerson", is(worksheet.getResponsiblePerson())));
        assertThat(savedWorksheet, hasProperty("responsiblePersonOther", is(worksheet.getResponsiblePersonOther())));
    }

    @Test
    public void saveTestRequestItem_shouldSaveAllProperties(){
//Given
        TestRequestItem testRequestItem=eu().newTestRequestItem(dao());

//When
        dao().saveTestRequestItem(testRequestItem);

//Let's clean up the cache to be sure getTestRequestItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestRequestItem savedTestRequestItem = dao().getTestRequestItemByUuid(testRequestItem.getUuid());
        assertThat(savedTestRequestItem, hasProperty("uuid", is(testRequestItem.getUuid())));
        assertThat(savedTestRequestItem, hasProperty("creator", is(testRequestItem.getCreator())));
        assertThat(savedTestRequestItem, hasProperty("dateCreated", is(testRequestItem.getDateCreated())));
        assertThat(savedTestRequestItem, hasProperty("changedBy", is(testRequestItem.getChangedBy())));
        assertThat(savedTestRequestItem, hasProperty("dateChanged", is(testRequestItem.getDateChanged())));
        assertThat(savedTestRequestItem, hasProperty("voided", is(testRequestItem.getVoided())));
        assertThat(savedTestRequestItem, hasProperty("dateVoided", is(testRequestItem.getDateVoided())));
        assertThat(savedTestRequestItem, hasProperty("voidedBy", is(testRequestItem.getVoidedBy())));
        assertThat(savedTestRequestItem, hasProperty("voidReason", is(testRequestItem.getVoidReason())));
        assertThat(savedTestRequestItem, hasProperty("order", is(testRequestItem.getOrder())));
        assertThat(savedTestRequestItem, hasProperty("atLocation", is(testRequestItem.getAtLocation())));
        assertThat(savedTestRequestItem, hasProperty("toLocation", is(testRequestItem.getToLocation())));
        assertThat(savedTestRequestItem, hasProperty("referredOut", is(testRequestItem.getReferredOut())));
        assertThat(savedTestRequestItem, hasProperty("referralOutOrigin", is(testRequestItem.getReferralOutOrigin())));
        assertThat(savedTestRequestItem, hasProperty("referralOutBy", is(testRequestItem.getReferralOutBy())));
        assertThat(savedTestRequestItem, hasProperty("referralOutDate", is(testRequestItem.getReferralOutDate())));

        assertThat(savedTestRequestItem, hasProperty("referralToFacility", is(testRequestItem.getReferralToFacility())));
        assertThat(savedTestRequestItem, hasProperty("referralToFacilityName", is(testRequestItem.getReferralToFacilityName())));

        assertThat(savedTestRequestItem, hasProperty("requireRequestApproval", is(testRequestItem.getRequireRequestApproval())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalResult", is(testRequestItem.getRequestApprovalResult())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalBy", is(testRequestItem.getRequestApprovalBy())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalDate", is(testRequestItem.getRequestApprovalDate())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalRemarks", is(testRequestItem.getRequestApprovalRemarks())));
        assertThat(savedTestRequestItem, hasProperty("initialSampleId", is(testRequestItem.getInitialSampleId())));
        assertThat(savedTestRequestItem, hasProperty("finalResultId", is(testRequestItem.getFinalResultId())));
        assertThat(savedTestRequestItem, hasProperty("status", is(testRequestItem.getStatus())));
        assertThat(savedTestRequestItem, hasProperty("encounter", is(testRequestItem.getEncounter())));
        assertThat(savedTestRequestItem, hasProperty("referralOutSample", is(testRequestItem.getReferralOutSample())));
        assertThat(savedTestRequestItem, hasProperty("testRequest", is(testRequestItem.getTestRequest())));

        savedTestRequestItem = dao().getTestRequestItemById(testRequestItem.getId());
        assertThat(savedTestRequestItem, hasProperty("uuid", is(testRequestItem.getUuid())));
        assertThat(savedTestRequestItem, hasProperty("creator", is(testRequestItem.getCreator())));
        assertThat(savedTestRequestItem, hasProperty("dateCreated", is(testRequestItem.getDateCreated())));
        assertThat(savedTestRequestItem, hasProperty("changedBy", is(testRequestItem.getChangedBy())));
        assertThat(savedTestRequestItem, hasProperty("dateChanged", is(testRequestItem.getDateChanged())));
        assertThat(savedTestRequestItem, hasProperty("voided", is(testRequestItem.getVoided())));
        assertThat(savedTestRequestItem, hasProperty("dateVoided", is(testRequestItem.getDateVoided())));
        assertThat(savedTestRequestItem, hasProperty("voidedBy", is(testRequestItem.getVoidedBy())));
        assertThat(savedTestRequestItem, hasProperty("voidReason", is(testRequestItem.getVoidReason())));
        assertThat(savedTestRequestItem, hasProperty("order", is(testRequestItem.getOrder())));
        assertThat(savedTestRequestItem, hasProperty("atLocation", is(testRequestItem.getAtLocation())));
        assertThat(savedTestRequestItem, hasProperty("toLocation", is(testRequestItem.getToLocation())));
        assertThat(savedTestRequestItem, hasProperty("referredOut", is(testRequestItem.getReferredOut())));
        assertThat(savedTestRequestItem, hasProperty("referralOutOrigin", is(testRequestItem.getReferralOutOrigin())));
        assertThat(savedTestRequestItem, hasProperty("referralOutBy", is(testRequestItem.getReferralOutBy())));
        assertThat(savedTestRequestItem, hasProperty("referralOutDate", is(testRequestItem.getReferralOutDate())));

        assertThat(savedTestRequestItem, hasProperty("referralToFacility", is(testRequestItem.getReferralToFacility())));
        assertThat(savedTestRequestItem, hasProperty("referralToFacilityName", is(testRequestItem.getReferralToFacilityName())));

        assertThat(savedTestRequestItem, hasProperty("requireRequestApproval", is(testRequestItem.getRequireRequestApproval())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalResult", is(testRequestItem.getRequestApprovalResult())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalBy", is(testRequestItem.getRequestApprovalBy())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalDate", is(testRequestItem.getRequestApprovalDate())));
        assertThat(savedTestRequestItem, hasProperty("requestApprovalRemarks", is(testRequestItem.getRequestApprovalRemarks())));
        assertThat(savedTestRequestItem, hasProperty("initialSampleId", is(testRequestItem.getInitialSampleId())));
        assertThat(savedTestRequestItem, hasProperty("finalResultId", is(testRequestItem.getFinalResultId())));
        assertThat(savedTestRequestItem, hasProperty("status", is(testRequestItem.getStatus())));
        assertThat(savedTestRequestItem, hasProperty("encounter", is(testRequestItem.getEncounter())));
        assertThat(savedTestRequestItem, hasProperty("referralOutSample", is(testRequestItem.getReferralOutSample())));
        assertThat(savedTestRequestItem, hasProperty("testRequest", is(testRequestItem.getTestRequest())));
    }

    @Test
    public void saveTestResult_shouldSaveAllProperties(){
//Given
        TestResult testResult=eu().newTestResult(dao());

//When
        dao().saveTestResult(testResult);

//Let's clean up the cache to be sure getTestResultByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestResult savedTestResult = dao().getTestResultByUuid(testResult.getUuid());
        assertThat(savedTestResult, hasProperty("uuid", is(testResult.getUuid())));
        assertThat(savedTestResult, hasProperty("creator", is(testResult.getCreator())));
        assertThat(savedTestResult, hasProperty("dateCreated", is(testResult.getDateCreated())));
        assertThat(savedTestResult, hasProperty("changedBy", is(testResult.getChangedBy())));
        assertThat(savedTestResult, hasProperty("dateChanged", is(testResult.getDateChanged())));
        assertThat(savedTestResult, hasProperty("voided", is(testResult.getVoided())));
        assertThat(savedTestResult, hasProperty("dateVoided", is(testResult.getDateVoided())));
        assertThat(savedTestResult, hasProperty("voidedBy", is(testResult.getVoidedBy())));
        assertThat(savedTestResult, hasProperty("voidReason", is(testResult.getVoidReason())));
        assertThat(savedTestResult, hasProperty("worksheetItem", is(testResult.getWorksheetItem())));
        assertThat(savedTestResult, hasProperty("testRequestItemSample", is(testResult.getTestRequestItemSample())));
        assertThat(savedTestResult, hasProperty("order", is(testResult.getOrder())));
        assertThat(savedTestResult, hasProperty("obs", is(testResult.getObs())));
        assertThat(savedTestResult, hasProperty("resultBy", is(testResult.getResultBy())));
        assertThat(savedTestResult, hasProperty("status", is(testResult.getStatus())));
        assertThat(savedTestResult, hasProperty("resultDate", is(testResult.getResultDate())));
        assertThat(savedTestResult, hasProperty("requireApproval", is(testResult.getRequireApproval())));
        assertThat(savedTestResult, hasProperty("currentApproval", is(testResult.getCurrentApproval())));
        assertThat(savedTestResult, hasProperty("additionalTestsRequired", is(testResult.getAdditionalTestsRequired())));
        assertThat(savedTestResult, hasProperty("archiveSample", is(testResult.getArchiveSample())));
        assertThat(savedTestResult, hasProperty("sampleActivity", is(testResult.getSampleActivity())));
        assertThat(savedTestResult, hasProperty("remarks", is(testResult.getRemarks())));

        savedTestResult = dao().getTestResultById(testResult.getId());
        assertThat(savedTestResult, hasProperty("uuid", is(testResult.getUuid())));
        assertThat(savedTestResult, hasProperty("creator", is(testResult.getCreator())));
        assertThat(savedTestResult, hasProperty("dateCreated", is(testResult.getDateCreated())));
        assertThat(savedTestResult, hasProperty("changedBy", is(testResult.getChangedBy())));
        assertThat(savedTestResult, hasProperty("dateChanged", is(testResult.getDateChanged())));
        assertThat(savedTestResult, hasProperty("voided", is(testResult.getVoided())));
        assertThat(savedTestResult, hasProperty("dateVoided", is(testResult.getDateVoided())));
        assertThat(savedTestResult, hasProperty("voidedBy", is(testResult.getVoidedBy())));
        assertThat(savedTestResult, hasProperty("voidReason", is(testResult.getVoidReason())));
        assertThat(savedTestResult, hasProperty("worksheetItem", is(testResult.getWorksheetItem())));
        assertThat(savedTestResult, hasProperty("testRequestItemSample", is(testResult.getTestRequestItemSample())));
        assertThat(savedTestResult, hasProperty("order", is(testResult.getOrder())));
        assertThat(savedTestResult, hasProperty("obs", is(testResult.getObs())));
        assertThat(savedTestResult, hasProperty("resultBy", is(testResult.getResultBy())));
        assertThat(savedTestResult, hasProperty("status", is(testResult.getStatus())));
        assertThat(savedTestResult, hasProperty("resultDate", is(testResult.getResultDate())));
        assertThat(savedTestResult, hasProperty("requireApproval", is(testResult.getRequireApproval())));
        assertThat(savedTestResult, hasProperty("currentApproval", is(testResult.getCurrentApproval())));
        assertThat(savedTestResult, hasProperty("additionalTestsRequired", is(testResult.getAdditionalTestsRequired())));
        assertThat(savedTestResult, hasProperty("archiveSample", is(testResult.getArchiveSample())));
        assertThat(savedTestResult, hasProperty("sampleActivity", is(testResult.getSampleActivity())));
        assertThat(savedTestResult, hasProperty("remarks", is(testResult.getRemarks())));
    }

    @Test
    public void saveBatchJobOwner_shouldSaveAllProperties(){
//Given
        BatchJobOwner batchJobOwner=eu().newBatchJobOwner(dao());

//When
        dao().saveBatchJobOwner(batchJobOwner);

//Let's clean up the cache to be sure getBatchJobOwnerByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        BatchJobOwner savedBatchJobOwner = dao().getBatchJobOwnerByUuid(batchJobOwner.getUuid());
        assertThat(savedBatchJobOwner, hasProperty("uuid", is(batchJobOwner.getUuid())));
        assertThat(savedBatchJobOwner, hasProperty("batchJob", is(batchJobOwner.getBatchJob())));
        assertThat(savedBatchJobOwner, hasProperty("owner", is(batchJobOwner.getOwner())));
        assertThat(savedBatchJobOwner, hasProperty("dateCreated", is(batchJobOwner.getDateCreated())));

        savedBatchJobOwner = dao().getBatchJobOwnerById(batchJobOwner.getId());
        assertThat(savedBatchJobOwner, hasProperty("uuid", is(batchJobOwner.getUuid())));
        assertThat(savedBatchJobOwner, hasProperty("batchJob", is(batchJobOwner.getBatchJob())));
        assertThat(savedBatchJobOwner, hasProperty("owner", is(batchJobOwner.getOwner())));
        assertThat(savedBatchJobOwner, hasProperty("dateCreated", is(batchJobOwner.getDateCreated())));
    }

    @Test
    public void saveTestRequestItemSample_shouldSaveAllProperties(){
//Given
        TestRequestItemSample testRequestItemSample=eu().newTestRequestItemSample(dao());

//When
        dao().saveTestRequestItemSample(testRequestItemSample);

//Let's clean up the cache to be sure getTestRequestItemSampleByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestRequestItemSample savedTestRequestItemSample = dao().getTestRequestItemSampleByUuid(testRequestItemSample.getUuid());
        assertThat(savedTestRequestItemSample, hasProperty("uuid", is(testRequestItemSample.getUuid())));
        assertThat(savedTestRequestItemSample, hasProperty("creator", is(testRequestItemSample.getCreator())));
        assertThat(savedTestRequestItemSample, hasProperty("dateCreated", is(testRequestItemSample.getDateCreated())));
        assertThat(savedTestRequestItemSample, hasProperty("changedBy", is(testRequestItemSample.getChangedBy())));
        assertThat(savedTestRequestItemSample, hasProperty("dateChanged", is(testRequestItemSample.getDateChanged())));
        assertThat(savedTestRequestItemSample, hasProperty("voided", is(testRequestItemSample.getVoided())));
        assertThat(savedTestRequestItemSample, hasProperty("dateVoided", is(testRequestItemSample.getDateVoided())));
        assertThat(savedTestRequestItemSample, hasProperty("voidedBy", is(testRequestItemSample.getVoidedBy())));
        assertThat(savedTestRequestItemSample, hasProperty("voidReason", is(testRequestItemSample.getVoidReason())));
        assertThat(savedTestRequestItemSample, hasProperty("testRequestItem", is(testRequestItemSample.getTestRequestItem())));
        assertThat(savedTestRequestItemSample, hasProperty("sample", is(testRequestItemSample.getSample())));

        savedTestRequestItemSample = dao().getTestRequestItemSampleById(testRequestItemSample.getId());
        assertThat(savedTestRequestItemSample, hasProperty("uuid", is(testRequestItemSample.getUuid())));
        assertThat(savedTestRequestItemSample, hasProperty("creator", is(testRequestItemSample.getCreator())));
        assertThat(savedTestRequestItemSample, hasProperty("dateCreated", is(testRequestItemSample.getDateCreated())));
        assertThat(savedTestRequestItemSample, hasProperty("changedBy", is(testRequestItemSample.getChangedBy())));
        assertThat(savedTestRequestItemSample, hasProperty("dateChanged", is(testRequestItemSample.getDateChanged())));
        assertThat(savedTestRequestItemSample, hasProperty("voided", is(testRequestItemSample.getVoided())));
        assertThat(savedTestRequestItemSample, hasProperty("dateVoided", is(testRequestItemSample.getDateVoided())));
        assertThat(savedTestRequestItemSample, hasProperty("voidedBy", is(testRequestItemSample.getVoidedBy())));
        assertThat(savedTestRequestItemSample, hasProperty("voidReason", is(testRequestItemSample.getVoidReason())));
        assertThat(savedTestRequestItemSample, hasProperty("testRequestItem", is(testRequestItemSample.getTestRequestItem())));
        assertThat(savedTestRequestItemSample, hasProperty("sample", is(testRequestItemSample.getSample())));
    }

    @Test
    public void saveBatchJob_shouldSaveAllProperties(){
//Given
        BatchJob batchJob=eu().newBatchJob(dao());

//When
        dao().saveBatchJob(batchJob);

//Let's clean up the cache to be sure getBatchJobByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        BatchJob savedBatchJob = dao().getBatchJobByUuid(batchJob.getUuid());
        assertThat(savedBatchJob, hasProperty("uuid", is(batchJob.getUuid())));
        assertThat(savedBatchJob, hasProperty("creator", is(batchJob.getCreator())));
        assertThat(savedBatchJob, hasProperty("dateCreated", is(batchJob.getDateCreated())));
        assertThat(savedBatchJob, hasProperty("changedBy", is(batchJob.getChangedBy())));
        assertThat(savedBatchJob, hasProperty("dateChanged", is(batchJob.getDateChanged())));
        assertThat(savedBatchJob, hasProperty("voided", is(batchJob.getVoided())));
        assertThat(savedBatchJob, hasProperty("dateVoided", is(batchJob.getDateVoided())));
        assertThat(savedBatchJob, hasProperty("voidedBy", is(batchJob.getVoidedBy())));
        assertThat(savedBatchJob, hasProperty("voidReason", is(batchJob.getVoidReason())));
        assertThat(savedBatchJob, hasProperty("batchJobType", is(batchJob.getBatchJobType())));
        assertThat(savedBatchJob, hasProperty("status", is(batchJob.getStatus())));
        assertThat(savedBatchJob, hasProperty("description", is(batchJob.getDescription())));
        assertThat(savedBatchJob, hasProperty("startTime", is(batchJob.getStartTime())));
        assertThat(savedBatchJob, hasProperty("endTime", is(batchJob.getEndTime())));
        assertThat(savedBatchJob, hasProperty("expiration", is(batchJob.getExpiration())));
        assertThat(savedBatchJob, hasProperty("parameters", is(batchJob.getParameters())));
        assertThat(savedBatchJob, hasProperty("privilegeScope", is(batchJob.getPrivilegeScope())));
        assertThat(savedBatchJob, hasProperty("locationScope", is(batchJob.getLocationScope())));
        assertThat(savedBatchJob, hasProperty("executionState", is(batchJob.getExecutionState())));
        assertThat(savedBatchJob, hasProperty("cancelReason", is(batchJob.getCancelReason())));
        assertThat(savedBatchJob, hasProperty("cancelledBy", is(batchJob.getCancelledBy())));
        assertThat(savedBatchJob, hasProperty("cancelledDate", is(batchJob.getCancelledDate())));
        assertThat(savedBatchJob, hasProperty("exitMessage", is(batchJob.getExitMessage())));
        assertThat(savedBatchJob, hasProperty("completedDate", is(batchJob.getCompletedDate())));
        assertThat(savedBatchJob, hasProperty("outputArtifactSize", is(batchJob.getOutputArtifactSize())));
        assertThat(savedBatchJob, hasProperty("outputArtifactFileExt", is(batchJob.getOutputArtifactFileExt())));
        assertThat(savedBatchJob, hasProperty("outputArtifactViewable", is(batchJob.getOutputArtifactViewable())));

        savedBatchJob = dao().getBatchJobById(batchJob.getId());
        assertThat(savedBatchJob, hasProperty("uuid", is(batchJob.getUuid())));
        assertThat(savedBatchJob, hasProperty("creator", is(batchJob.getCreator())));
        assertThat(savedBatchJob, hasProperty("dateCreated", is(batchJob.getDateCreated())));
        assertThat(savedBatchJob, hasProperty("changedBy", is(batchJob.getChangedBy())));
        assertThat(savedBatchJob, hasProperty("dateChanged", is(batchJob.getDateChanged())));
        assertThat(savedBatchJob, hasProperty("voided", is(batchJob.getVoided())));
        assertThat(savedBatchJob, hasProperty("dateVoided", is(batchJob.getDateVoided())));
        assertThat(savedBatchJob, hasProperty("voidedBy", is(batchJob.getVoidedBy())));
        assertThat(savedBatchJob, hasProperty("voidReason", is(batchJob.getVoidReason())));
        assertThat(savedBatchJob, hasProperty("batchJobType", is(batchJob.getBatchJobType())));
        assertThat(savedBatchJob, hasProperty("status", is(batchJob.getStatus())));
        assertThat(savedBatchJob, hasProperty("description", is(batchJob.getDescription())));
        assertThat(savedBatchJob, hasProperty("startTime", is(batchJob.getStartTime())));
        assertThat(savedBatchJob, hasProperty("endTime", is(batchJob.getEndTime())));
        assertThat(savedBatchJob, hasProperty("expiration", is(batchJob.getExpiration())));
        assertThat(savedBatchJob, hasProperty("parameters", is(batchJob.getParameters())));
        assertThat(savedBatchJob, hasProperty("privilegeScope", is(batchJob.getPrivilegeScope())));
        assertThat(savedBatchJob, hasProperty("locationScope", is(batchJob.getLocationScope())));
        assertThat(savedBatchJob, hasProperty("executionState", is(batchJob.getExecutionState())));
        assertThat(savedBatchJob, hasProperty("cancelReason", is(batchJob.getCancelReason())));
        assertThat(savedBatchJob, hasProperty("cancelledBy", is(batchJob.getCancelledBy())));
        assertThat(savedBatchJob, hasProperty("cancelledDate", is(batchJob.getCancelledDate())));
        assertThat(savedBatchJob, hasProperty("exitMessage", is(batchJob.getExitMessage())));
        assertThat(savedBatchJob, hasProperty("completedDate", is(batchJob.getCompletedDate())));
        assertThat(savedBatchJob, hasProperty("outputArtifactSize", is(batchJob.getOutputArtifactSize())));
        assertThat(savedBatchJob, hasProperty("outputArtifactFileExt", is(batchJob.getOutputArtifactFileExt())));
        assertThat(savedBatchJob, hasProperty("outputArtifactViewable", is(batchJob.getOutputArtifactViewable())));
    }

    @Test
    public void saveSample_shouldSaveAllProperties(){
//Given
        Sample sample=eu().newSample(dao());

//When
        dao().saveSample(sample);

//Let's clean up the cache to be sure getSampleByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        Sample savedSample = dao().getSampleByUuid(sample.getUuid());
        assertThat(savedSample, hasProperty("uuid", is(sample.getUuid())));
        assertThat(savedSample, hasProperty("creator", is(sample.getCreator())));
        assertThat(savedSample, hasProperty("dateCreated", is(sample.getDateCreated())));
        assertThat(savedSample, hasProperty("changedBy", is(sample.getChangedBy())));
        assertThat(savedSample, hasProperty("dateChanged", is(sample.getDateChanged())));
        assertThat(savedSample, hasProperty("voided", is(sample.getVoided())));
        assertThat(savedSample, hasProperty("dateVoided", is(sample.getDateVoided())));
        assertThat(savedSample, hasProperty("voidedBy", is(sample.getVoidedBy())));
        assertThat(savedSample, hasProperty("voidReason", is(sample.getVoidReason())));
        assertThat(savedSample, hasProperty("parentSample", is(sample.getParentSample())));
        assertThat(savedSample, hasProperty("sampleType", is(sample.getSampleType())));
        assertThat(savedSample, hasProperty("atLocation", is(sample.getAtLocation())));
        assertThat(savedSample, hasProperty("containerType", is(sample.getContainerType())));
        assertThat(savedSample, hasProperty("collectedBy", is(sample.getCollectedBy())));
        assertThat(savedSample, hasProperty("collectionDate", is(sample.getCollectionDate())));
        assertThat(savedSample, hasProperty("containerCount", is(sample.getContainerCount())));
        assertThat(savedSample, hasProperty("accessionNumber", is(sample.getAccessionNumber())));
        assertThat(savedSample, hasProperty("providedRef", is(sample.getProvidedRef())));
        assertThat(savedSample, hasProperty("externalRef", is(sample.getExternalRef())));
        assertThat(savedSample, hasProperty("referredOut", is(sample.getReferredOut())));
        assertThat(savedSample, hasProperty("currentSampleActivity", is(sample.getCurrentSampleActivity())));
        assertThat(savedSample, hasProperty("status", is(sample.getStatus())));
        assertThat(savedSample, hasProperty("encounter", is(sample.getEncounter())));
        assertThat(savedSample, hasProperty("testRequest", is(sample.getTestRequest())));

        savedSample = dao().getSampleById(sample.getId());
        assertThat(savedSample, hasProperty("uuid", is(sample.getUuid())));
        assertThat(savedSample, hasProperty("creator", is(sample.getCreator())));
        assertThat(savedSample, hasProperty("dateCreated", is(sample.getDateCreated())));
        assertThat(savedSample, hasProperty("changedBy", is(sample.getChangedBy())));
        assertThat(savedSample, hasProperty("dateChanged", is(sample.getDateChanged())));
        assertThat(savedSample, hasProperty("voided", is(sample.getVoided())));
        assertThat(savedSample, hasProperty("dateVoided", is(sample.getDateVoided())));
        assertThat(savedSample, hasProperty("voidedBy", is(sample.getVoidedBy())));
        assertThat(savedSample, hasProperty("voidReason", is(sample.getVoidReason())));
        assertThat(savedSample, hasProperty("parentSample", is(sample.getParentSample())));
        assertThat(savedSample, hasProperty("sampleType", is(sample.getSampleType())));
        assertThat(savedSample, hasProperty("atLocation", is(sample.getAtLocation())));
        assertThat(savedSample, hasProperty("containerType", is(sample.getContainerType())));
        assertThat(savedSample, hasProperty("collectedBy", is(sample.getCollectedBy())));
        assertThat(savedSample, hasProperty("collectionDate", is(sample.getCollectionDate())));
        assertThat(savedSample, hasProperty("containerCount", is(sample.getContainerCount())));
        assertThat(savedSample, hasProperty("accessionNumber", is(sample.getAccessionNumber())));
        assertThat(savedSample, hasProperty("providedRef", is(sample.getProvidedRef())));
        assertThat(savedSample, hasProperty("externalRef", is(sample.getExternalRef())));
        assertThat(savedSample, hasProperty("referredOut", is(sample.getReferredOut())));
        assertThat(savedSample, hasProperty("currentSampleActivity", is(sample.getCurrentSampleActivity())));
        assertThat(savedSample, hasProperty("status", is(sample.getStatus())));
        assertThat(savedSample, hasProperty("encounter", is(sample.getEncounter())));
        assertThat(savedSample, hasProperty("testRequest", is(sample.getTestRequest())));
    }

    @Test
    public void saveApprovalFlow_shouldSaveAllProperties(){
//Given
        ApprovalFlow approvalFlow=eu().newApprovalFlow(dao());

//When
        dao().saveApprovalFlow(approvalFlow);

//Let's clean up the cache to be sure getApprovalFlowByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        ApprovalFlow savedApprovalFlow = dao().getApprovalFlowByUuid(approvalFlow.getUuid());
        assertThat(savedApprovalFlow, hasProperty("uuid", is(approvalFlow.getUuid())));
        assertThat(savedApprovalFlow, hasProperty("creator", is(approvalFlow.getCreator())));
        assertThat(savedApprovalFlow, hasProperty("dateCreated", is(approvalFlow.getDateCreated())));
        assertThat(savedApprovalFlow, hasProperty("changedBy", is(approvalFlow.getChangedBy())));
        assertThat(savedApprovalFlow, hasProperty("dateChanged", is(approvalFlow.getDateChanged())));
        assertThat(savedApprovalFlow, hasProperty("voided", is(approvalFlow.getVoided())));
        assertThat(savedApprovalFlow, hasProperty("dateVoided", is(approvalFlow.getDateVoided())));
        assertThat(savedApprovalFlow, hasProperty("voidedBy", is(approvalFlow.getVoidedBy())));
        assertThat(savedApprovalFlow, hasProperty("voidReason", is(approvalFlow.getVoidReason())));
        assertThat(savedApprovalFlow, hasProperty("name", is(approvalFlow.getName())));
        assertThat(savedApprovalFlow, hasProperty("levelOne", is(approvalFlow.getLevelOne())));
        assertThat(savedApprovalFlow, hasProperty("levelTwo", is(approvalFlow.getLevelTwo())));
        assertThat(savedApprovalFlow, hasProperty("levelThree", is(approvalFlow.getLevelThree())));
        assertThat(savedApprovalFlow, hasProperty("levelFour", is(approvalFlow.getLevelFour())));
        assertThat(savedApprovalFlow, hasProperty("systemName", is(approvalFlow.getSystemName())));
        assertThat(savedApprovalFlow, hasProperty("levelOneAllowOwner", is(approvalFlow.getLevelOneAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelTwoAllowOwner", is(approvalFlow.getLevelTwoAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelThreeAllowOwner", is(approvalFlow.getLevelThreeAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelFourAllowOwner", is(approvalFlow.getLevelFourAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelTwoAllowPrevious", is(approvalFlow.getLevelTwoAllowPrevious())));
        assertThat(savedApprovalFlow, hasProperty("levelThreeAllowPrevious", is(approvalFlow.getLevelThreeAllowPrevious())));
        assertThat(savedApprovalFlow, hasProperty("levelFourAllowPrevious", is(approvalFlow.getLevelFourAllowPrevious())));

        savedApprovalFlow = dao().getApprovalFlowById(approvalFlow.getId());
        assertThat(savedApprovalFlow, hasProperty("uuid", is(approvalFlow.getUuid())));
        assertThat(savedApprovalFlow, hasProperty("creator", is(approvalFlow.getCreator())));
        assertThat(savedApprovalFlow, hasProperty("dateCreated", is(approvalFlow.getDateCreated())));
        assertThat(savedApprovalFlow, hasProperty("changedBy", is(approvalFlow.getChangedBy())));
        assertThat(savedApprovalFlow, hasProperty("dateChanged", is(approvalFlow.getDateChanged())));
        assertThat(savedApprovalFlow, hasProperty("voided", is(approvalFlow.getVoided())));
        assertThat(savedApprovalFlow, hasProperty("dateVoided", is(approvalFlow.getDateVoided())));
        assertThat(savedApprovalFlow, hasProperty("voidedBy", is(approvalFlow.getVoidedBy())));
        assertThat(savedApprovalFlow, hasProperty("voidReason", is(approvalFlow.getVoidReason())));
        assertThat(savedApprovalFlow, hasProperty("name", is(approvalFlow.getName())));
        assertThat(savedApprovalFlow, hasProperty("levelOne", is(approvalFlow.getLevelOne())));
        assertThat(savedApprovalFlow, hasProperty("levelTwo", is(approvalFlow.getLevelTwo())));
        assertThat(savedApprovalFlow, hasProperty("levelThree", is(approvalFlow.getLevelThree())));
        assertThat(savedApprovalFlow, hasProperty("levelFour", is(approvalFlow.getLevelFour())));
        assertThat(savedApprovalFlow, hasProperty("systemName", is(approvalFlow.getSystemName())));
        assertThat(savedApprovalFlow, hasProperty("levelOneAllowOwner", is(approvalFlow.getLevelOneAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelTwoAllowOwner", is(approvalFlow.getLevelTwoAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelThreeAllowOwner", is(approvalFlow.getLevelThreeAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelFourAllowOwner", is(approvalFlow.getLevelFourAllowOwner())));
        assertThat(savedApprovalFlow, hasProperty("levelTwoAllowPrevious", is(approvalFlow.getLevelTwoAllowPrevious())));
        assertThat(savedApprovalFlow, hasProperty("levelThreeAllowPrevious", is(approvalFlow.getLevelThreeAllowPrevious())));
        assertThat(savedApprovalFlow, hasProperty("levelFourAllowPrevious", is(approvalFlow.getLevelFourAllowPrevious())));
    }

    @Test
    public void saveTestResultDocument_shouldSaveAllProperties(){
//Given
        TestResultDocument testResultDocument=eu().newTestResultDocument(dao());

//When
        dao().saveTestResultDocument(testResultDocument);

//Let's clean up the cache to be sure getTestResultDocumentByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestResultDocument savedTestResultDocument = dao().getTestResultDocumentByUuid(testResultDocument.getUuid());
        assertThat(savedTestResultDocument, hasProperty("uuid", is(testResultDocument.getUuid())));
        assertThat(savedTestResultDocument, hasProperty("creator", is(testResultDocument.getCreator())));
        assertThat(savedTestResultDocument, hasProperty("dateCreated", is(testResultDocument.getDateCreated())));
        assertThat(savedTestResultDocument, hasProperty("changedBy", is(testResultDocument.getChangedBy())));
        assertThat(savedTestResultDocument, hasProperty("dateChanged", is(testResultDocument.getDateChanged())));
        assertThat(savedTestResultDocument, hasProperty("voided", is(testResultDocument.getVoided())));
        assertThat(savedTestResultDocument, hasProperty("dateVoided", is(testResultDocument.getDateVoided())));
        assertThat(savedTestResultDocument, hasProperty("voidedBy", is(testResultDocument.getVoidedBy())));
        assertThat(savedTestResultDocument, hasProperty("voidReason", is(testResultDocument.getVoidReason())));
        assertThat(savedTestResultDocument, hasProperty("testResult", is(testResultDocument.getTestResult())));
        assertThat(savedTestResultDocument, hasProperty("documentType", is(testResultDocument.getDocumentType())));
        assertThat(savedTestResultDocument, hasProperty("documentName", is(testResultDocument.getDocumentName())));
        assertThat(savedTestResultDocument, hasProperty("documentProvider", is(testResultDocument.getDocumentProvider())));
        assertThat(savedTestResultDocument, hasProperty("documentProviderRef", is(testResultDocument.getDocumentProviderRef())));
        assertThat(savedTestResultDocument, hasProperty("remarks", is(testResultDocument.getRemarks())));

        savedTestResultDocument = dao().getTestResultDocumentById(testResultDocument.getId());
        assertThat(savedTestResultDocument, hasProperty("uuid", is(testResultDocument.getUuid())));
        assertThat(savedTestResultDocument, hasProperty("creator", is(testResultDocument.getCreator())));
        assertThat(savedTestResultDocument, hasProperty("dateCreated", is(testResultDocument.getDateCreated())));
        assertThat(savedTestResultDocument, hasProperty("changedBy", is(testResultDocument.getChangedBy())));
        assertThat(savedTestResultDocument, hasProperty("dateChanged", is(testResultDocument.getDateChanged())));
        assertThat(savedTestResultDocument, hasProperty("voided", is(testResultDocument.getVoided())));
        assertThat(savedTestResultDocument, hasProperty("dateVoided", is(testResultDocument.getDateVoided())));
        assertThat(savedTestResultDocument, hasProperty("voidedBy", is(testResultDocument.getVoidedBy())));
        assertThat(savedTestResultDocument, hasProperty("voidReason", is(testResultDocument.getVoidReason())));
        assertThat(savedTestResultDocument, hasProperty("testResult", is(testResultDocument.getTestResult())));
        assertThat(savedTestResultDocument, hasProperty("documentType", is(testResultDocument.getDocumentType())));
        assertThat(savedTestResultDocument, hasProperty("documentName", is(testResultDocument.getDocumentName())));
        assertThat(savedTestResultDocument, hasProperty("documentProvider", is(testResultDocument.getDocumentProvider())));
        assertThat(savedTestResultDocument, hasProperty("documentProviderRef", is(testResultDocument.getDocumentProviderRef())));
        assertThat(savedTestResultDocument, hasProperty("remarks", is(testResultDocument.getRemarks())));
    }

    @Test
    public void saveApprovalConfig_shouldSaveAllProperties(){
//Given
        ApprovalConfig approvalConfig=eu().newApprovalConfig(dao());

//When
        dao().saveApprovalConfig(approvalConfig);

//Let's clean up the cache to be sure getApprovalConfigByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        ApprovalConfig savedApprovalConfig = dao().getApprovalConfigByUuid(approvalConfig.getUuid());
        assertThat(savedApprovalConfig, hasProperty("uuid", is(approvalConfig.getUuid())));
        assertThat(savedApprovalConfig, hasProperty("creator", is(approvalConfig.getCreator())));
        assertThat(savedApprovalConfig, hasProperty("dateCreated", is(approvalConfig.getDateCreated())));
        assertThat(savedApprovalConfig, hasProperty("changedBy", is(approvalConfig.getChangedBy())));
        assertThat(savedApprovalConfig, hasProperty("dateChanged", is(approvalConfig.getDateChanged())));
        assertThat(savedApprovalConfig, hasProperty("voided", is(approvalConfig.getVoided())));
        assertThat(savedApprovalConfig, hasProperty("dateVoided", is(approvalConfig.getDateVoided())));
        assertThat(savedApprovalConfig, hasProperty("voidedBy", is(approvalConfig.getVoidedBy())));
        assertThat(savedApprovalConfig, hasProperty("voidReason", is(approvalConfig.getVoidReason())));
        assertThat(savedApprovalConfig, hasProperty("approvalTitle", is(approvalConfig.getApprovalTitle())));
        assertThat(savedApprovalConfig, hasProperty("privilege", is(approvalConfig.getPrivilege())));
        assertThat(savedApprovalConfig, hasProperty("pendingStatus", is(approvalConfig.getPendingStatus())));
        assertThat(savedApprovalConfig, hasProperty("returnedStatus", is(approvalConfig.getReturnedStatus())));
        assertThat(savedApprovalConfig, hasProperty("rejectedStatus", is(approvalConfig.getRejectedStatus())));
        assertThat(savedApprovalConfig, hasProperty("approvedStatus", is(approvalConfig.getApprovedStatus())));

        savedApprovalConfig = dao().getApprovalConfigById(approvalConfig.getId());
        assertThat(savedApprovalConfig, hasProperty("uuid", is(approvalConfig.getUuid())));
        assertThat(savedApprovalConfig, hasProperty("creator", is(approvalConfig.getCreator())));
        assertThat(savedApprovalConfig, hasProperty("dateCreated", is(approvalConfig.getDateCreated())));
        assertThat(savedApprovalConfig, hasProperty("changedBy", is(approvalConfig.getChangedBy())));
        assertThat(savedApprovalConfig, hasProperty("dateChanged", is(approvalConfig.getDateChanged())));
        assertThat(savedApprovalConfig, hasProperty("voided", is(approvalConfig.getVoided())));
        assertThat(savedApprovalConfig, hasProperty("dateVoided", is(approvalConfig.getDateVoided())));
        assertThat(savedApprovalConfig, hasProperty("voidedBy", is(approvalConfig.getVoidedBy())));
        assertThat(savedApprovalConfig, hasProperty("voidReason", is(approvalConfig.getVoidReason())));
        assertThat(savedApprovalConfig, hasProperty("approvalTitle", is(approvalConfig.getApprovalTitle())));
        assertThat(savedApprovalConfig, hasProperty("privilege", is(approvalConfig.getPrivilege())));
        assertThat(savedApprovalConfig, hasProperty("pendingStatus", is(approvalConfig.getPendingStatus())));
        assertThat(savedApprovalConfig, hasProperty("returnedStatus", is(approvalConfig.getReturnedStatus())));
        assertThat(savedApprovalConfig, hasProperty("rejectedStatus", is(approvalConfig.getRejectedStatus())));
        assertThat(savedApprovalConfig, hasProperty("approvedStatus", is(approvalConfig.getApprovedStatus())));
    }

    @Test
    public void saveSampleActivity_shouldSaveAllProperties(){
//Given
        SampleActivity sampleActivity=eu().newSampleActivity(dao());

//When
        dao().saveSampleActivity(sampleActivity);

//Let's clean up the cache to be sure getSampleActivityByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        SampleActivity savedSampleActivity = dao().getSampleActivityByUuid(sampleActivity.getUuid());
        assertThat(savedSampleActivity, hasProperty("uuid", is(sampleActivity.getUuid())));
        assertThat(savedSampleActivity, hasProperty("creator", is(sampleActivity.getCreator())));
        assertThat(savedSampleActivity, hasProperty("dateCreated", is(sampleActivity.getDateCreated())));
        assertThat(savedSampleActivity, hasProperty("changedBy", is(sampleActivity.getChangedBy())));
        assertThat(savedSampleActivity, hasProperty("dateChanged", is(sampleActivity.getDateChanged())));
        assertThat(savedSampleActivity, hasProperty("voided", is(sampleActivity.getVoided())));
        assertThat(savedSampleActivity, hasProperty("dateVoided", is(sampleActivity.getDateVoided())));
        assertThat(savedSampleActivity, hasProperty("voidedBy", is(sampleActivity.getVoidedBy())));
        assertThat(savedSampleActivity, hasProperty("voidReason", is(sampleActivity.getVoidReason())));
        assertThat(savedSampleActivity, hasProperty("sample", is(sampleActivity.getSample())));
        assertThat(savedSampleActivity, hasProperty("activityType", is(sampleActivity.getActivityType())));
        assertThat(savedSampleActivity, hasProperty("source", is(sampleActivity.getSource())));
        assertThat(savedSampleActivity, hasProperty("destination", is(sampleActivity.getDestination())));
        assertThat(savedSampleActivity, hasProperty("sourceState", is(sampleActivity.getSourceState())));
        assertThat(savedSampleActivity, hasProperty("destinationState", is(sampleActivity.getDestinationState())));
        assertThat(savedSampleActivity, hasProperty("activityBy", is(sampleActivity.getActivityBy())));
        assertThat(savedSampleActivity, hasProperty("remarks", is(sampleActivity.getRemarks())));
        assertThat(savedSampleActivity, hasProperty("status", is(sampleActivity.getStatus())));
        assertThat(savedSampleActivity, hasProperty("toSample", is(sampleActivity.getToSample())));
        assertThat(savedSampleActivity, hasProperty("reusedCheckout", is(sampleActivity.getReusedCheckout())));
        assertThat(savedSampleActivity, hasProperty("volume", is(sampleActivity.getVolume())));
        assertThat(savedSampleActivity, hasProperty("thawCycles", is(sampleActivity.getThawCycles())));

        savedSampleActivity = dao().getSampleActivityById(sampleActivity.getId());
        assertThat(savedSampleActivity, hasProperty("uuid", is(sampleActivity.getUuid())));
        assertThat(savedSampleActivity, hasProperty("creator", is(sampleActivity.getCreator())));
        assertThat(savedSampleActivity, hasProperty("dateCreated", is(sampleActivity.getDateCreated())));
        assertThat(savedSampleActivity, hasProperty("changedBy", is(sampleActivity.getChangedBy())));
        assertThat(savedSampleActivity, hasProperty("dateChanged", is(sampleActivity.getDateChanged())));
        assertThat(savedSampleActivity, hasProperty("voided", is(sampleActivity.getVoided())));
        assertThat(savedSampleActivity, hasProperty("dateVoided", is(sampleActivity.getDateVoided())));
        assertThat(savedSampleActivity, hasProperty("voidedBy", is(sampleActivity.getVoidedBy())));
        assertThat(savedSampleActivity, hasProperty("voidReason", is(sampleActivity.getVoidReason())));
        assertThat(savedSampleActivity, hasProperty("sample", is(sampleActivity.getSample())));
        assertThat(savedSampleActivity, hasProperty("activityType", is(sampleActivity.getActivityType())));
        assertThat(savedSampleActivity, hasProperty("source", is(sampleActivity.getSource())));
        assertThat(savedSampleActivity, hasProperty("destination", is(sampleActivity.getDestination())));
        assertThat(savedSampleActivity, hasProperty("sourceState", is(sampleActivity.getSourceState())));
        assertThat(savedSampleActivity, hasProperty("destinationState", is(sampleActivity.getDestinationState())));
        assertThat(savedSampleActivity, hasProperty("activityBy", is(sampleActivity.getActivityBy())));
        assertThat(savedSampleActivity, hasProperty("remarks", is(sampleActivity.getRemarks())));
        assertThat(savedSampleActivity, hasProperty("status", is(sampleActivity.getStatus())));
        assertThat(savedSampleActivity, hasProperty("toSample", is(sampleActivity.getToSample())));
        assertThat(savedSampleActivity, hasProperty("reusedCheckout", is(sampleActivity.getReusedCheckout())));
        assertThat(savedSampleActivity, hasProperty("volume", is(sampleActivity.getVolume())));
        assertThat(savedSampleActivity, hasProperty("thawCycles", is(sampleActivity.getThawCycles())));
    }

    @Test
    public void saveTestConfig_shouldSaveAllProperties(){
//Given
        TestConfig testConfig=eu().newTestConfig(dao());

//When
        dao().saveTestConfig(testConfig);

//Let's clean up the cache to be sure getTestConfigByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestConfig savedTestConfig = dao().getTestConfigByUuid(testConfig.getUuid());
        assertThat(savedTestConfig, hasProperty("uuid", is(testConfig.getUuid())));
        assertThat(savedTestConfig, hasProperty("creator", is(testConfig.getCreator())));
        assertThat(savedTestConfig, hasProperty("dateCreated", is(testConfig.getDateCreated())));
        assertThat(savedTestConfig, hasProperty("changedBy", is(testConfig.getChangedBy())));
        assertThat(savedTestConfig, hasProperty("dateChanged", is(testConfig.getDateChanged())));
        assertThat(savedTestConfig, hasProperty("voided", is(testConfig.getVoided())));
        assertThat(savedTestConfig, hasProperty("dateVoided", is(testConfig.getDateVoided())));
        assertThat(savedTestConfig, hasProperty("voidedBy", is(testConfig.getVoidedBy())));
        assertThat(savedTestConfig, hasProperty("voidReason", is(testConfig.getVoidReason())));
        assertThat(savedTestConfig, hasProperty("test", is(testConfig.getTest())));
        assertThat(savedTestConfig, hasProperty("requireApproval", is(testConfig.getRequireApproval())));
        assertThat(savedTestConfig, hasProperty("approvalFlow", is(testConfig.getApprovalFlow())));
        assertThat(savedTestConfig, hasProperty("testGroup", is(testConfig.getTestGroup())));
        assertThat(savedTestConfig, hasProperty("enabled", is(testConfig.getEnabled())));

        savedTestConfig = dao().getTestConfigById(testConfig.getId());
        assertThat(savedTestConfig, hasProperty("uuid", is(testConfig.getUuid())));
        assertThat(savedTestConfig, hasProperty("creator", is(testConfig.getCreator())));
        assertThat(savedTestConfig, hasProperty("dateCreated", is(testConfig.getDateCreated())));
        assertThat(savedTestConfig, hasProperty("changedBy", is(testConfig.getChangedBy())));
        assertThat(savedTestConfig, hasProperty("dateChanged", is(testConfig.getDateChanged())));
        assertThat(savedTestConfig, hasProperty("voided", is(testConfig.getVoided())));
        assertThat(savedTestConfig, hasProperty("dateVoided", is(testConfig.getDateVoided())));
        assertThat(savedTestConfig, hasProperty("voidedBy", is(testConfig.getVoidedBy())));
        assertThat(savedTestConfig, hasProperty("voidReason", is(testConfig.getVoidReason())));
        assertThat(savedTestConfig, hasProperty("test", is(testConfig.getTest())));
        assertThat(savedTestConfig, hasProperty("requireApproval", is(testConfig.getRequireApproval())));
        assertThat(savedTestConfig, hasProperty("approvalFlow", is(testConfig.getApprovalFlow())));
        assertThat(savedTestConfig, hasProperty("testGroup", is(testConfig.getTestGroup())));
        assertThat(savedTestConfig, hasProperty("enabled", is(testConfig.getEnabled())));
    }

    @Test
    public void saveTestApproval_shouldSaveAllProperties(){
//Given
        TestApproval testApproval=eu().newTestApproval(dao());

//When
        dao().saveTestApproval(testApproval);

//Let's clean up the cache to be sure getTestApprovalByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestApproval savedTestApproval = dao().getTestApprovalByUuid(testApproval.getUuid());
        assertThat(savedTestApproval, hasProperty("uuid", is(testApproval.getUuid())));
        assertThat(savedTestApproval, hasProperty("creator", is(testApproval.getCreator())));
        assertThat(savedTestApproval, hasProperty("dateCreated", is(testApproval.getDateCreated())));
        assertThat(savedTestApproval, hasProperty("changedBy", is(testApproval.getChangedBy())));
        assertThat(savedTestApproval, hasProperty("dateChanged", is(testApproval.getDateChanged())));
        assertThat(savedTestApproval, hasProperty("voided", is(testApproval.getVoided())));
        assertThat(savedTestApproval, hasProperty("dateVoided", is(testApproval.getDateVoided())));
        assertThat(savedTestApproval, hasProperty("voidedBy", is(testApproval.getVoidedBy())));
        assertThat(savedTestApproval, hasProperty("voidReason", is(testApproval.getVoidReason())));
        assertThat(savedTestApproval, hasProperty("approvalFlow", is(testApproval.getApprovalFlow())));
        assertThat(savedTestApproval, hasProperty("approvalConfig", is(testApproval.getApprovalConfig())));
        assertThat(savedTestApproval, hasProperty("approvalResult", is(testApproval.getApprovalResult())));
        assertThat(savedTestApproval, hasProperty("remarks", is(testApproval.getRemarks())));
        assertThat(savedTestApproval, hasProperty("activatedDate", is(testApproval.getActivatedDate())));
        assertThat(savedTestApproval, hasProperty("approvalDate", is(testApproval.getApprovalDate())));
        assertThat(savedTestApproval, hasProperty("approvedBy", is(testApproval.getApprovedBy())));
        assertThat(savedTestApproval, hasProperty("nextApproval", is(testApproval.getNextApproval())));
        assertThat(savedTestApproval, hasProperty("currentApprovalLevel", is(testApproval.getCurrentApprovalLevel())));
        assertThat(savedTestApproval, hasProperty("testResult", is(testApproval.getTestResult())));

        savedTestApproval = dao().getTestApprovalById(testApproval.getId());
        assertThat(savedTestApproval, hasProperty("uuid", is(testApproval.getUuid())));
        assertThat(savedTestApproval, hasProperty("creator", is(testApproval.getCreator())));
        assertThat(savedTestApproval, hasProperty("dateCreated", is(testApproval.getDateCreated())));
        assertThat(savedTestApproval, hasProperty("changedBy", is(testApproval.getChangedBy())));
        assertThat(savedTestApproval, hasProperty("dateChanged", is(testApproval.getDateChanged())));
        assertThat(savedTestApproval, hasProperty("voided", is(testApproval.getVoided())));
        assertThat(savedTestApproval, hasProperty("dateVoided", is(testApproval.getDateVoided())));
        assertThat(savedTestApproval, hasProperty("voidedBy", is(testApproval.getVoidedBy())));
        assertThat(savedTestApproval, hasProperty("voidReason", is(testApproval.getVoidReason())));
        assertThat(savedTestApproval, hasProperty("approvalFlow", is(testApproval.getApprovalFlow())));
        assertThat(savedTestApproval, hasProperty("approvalConfig", is(testApproval.getApprovalConfig())));
        assertThat(savedTestApproval, hasProperty("approvalResult", is(testApproval.getApprovalResult())));
        assertThat(savedTestApproval, hasProperty("remarks", is(testApproval.getRemarks())));
        assertThat(savedTestApproval, hasProperty("activatedDate", is(testApproval.getActivatedDate())));
        assertThat(savedTestApproval, hasProperty("approvalDate", is(testApproval.getApprovalDate())));
        assertThat(savedTestApproval, hasProperty("approvedBy", is(testApproval.getApprovedBy())));
        assertThat(savedTestApproval, hasProperty("nextApproval", is(testApproval.getNextApproval())));
        assertThat(savedTestApproval, hasProperty("currentApprovalLevel", is(testApproval.getCurrentApprovalLevel())));
        assertThat(savedTestApproval, hasProperty("testResult", is(testApproval.getTestResult())));
    }

    @Test
    public void saveWorksheetItem_shouldSaveAllProperties(){
//Given
        WorksheetItem worksheetItem=eu().newWorksheetItem(dao());

//When
        dao().saveWorksheetItem(worksheetItem);

//Let's clean up the cache to be sure getWorksheetItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        WorksheetItem savedWorksheetItem = dao().getWorksheetItemByUuid(worksheetItem.getUuid());
        assertThat(savedWorksheetItem, hasProperty("uuid", is(worksheetItem.getUuid())));
        assertThat(savedWorksheetItem, hasProperty("creator", is(worksheetItem.getCreator())));
        assertThat(savedWorksheetItem, hasProperty("dateCreated", is(worksheetItem.getDateCreated())));
        assertThat(savedWorksheetItem, hasProperty("changedBy", is(worksheetItem.getChangedBy())));
        assertThat(savedWorksheetItem, hasProperty("dateChanged", is(worksheetItem.getDateChanged())));
        assertThat(savedWorksheetItem, hasProperty("voided", is(worksheetItem.getVoided())));
        assertThat(savedWorksheetItem, hasProperty("dateVoided", is(worksheetItem.getDateVoided())));
        assertThat(savedWorksheetItem, hasProperty("voidedBy", is(worksheetItem.getVoidedBy())));
        assertThat(savedWorksheetItem, hasProperty("voidReason", is(worksheetItem.getVoidReason())));
        assertThat(savedWorksheetItem, hasProperty("worksheet", is(worksheetItem.getWorksheet())));
        assertThat(savedWorksheetItem, hasProperty("testRequestItemSample", is(worksheetItem.getTestRequestItemSample())));
        assertThat(savedWorksheetItem, hasProperty("status", is(worksheetItem.getStatus())));
        assertThat(savedWorksheetItem, hasProperty("completedDate", is(worksheetItem.getCompletedDate())));
        assertThat(savedWorksheetItem, hasProperty("cancelledDate", is(worksheetItem.getCancelledDate())));
        assertThat(savedWorksheetItem, hasProperty("cancellationRemarks", is(worksheetItem.getCancellationRemarks())));

        savedWorksheetItem = dao().getWorksheetItemById(worksheetItem.getId());
        assertThat(savedWorksheetItem, hasProperty("uuid", is(worksheetItem.getUuid())));
        assertThat(savedWorksheetItem, hasProperty("creator", is(worksheetItem.getCreator())));
        assertThat(savedWorksheetItem, hasProperty("dateCreated", is(worksheetItem.getDateCreated())));
        assertThat(savedWorksheetItem, hasProperty("changedBy", is(worksheetItem.getChangedBy())));
        assertThat(savedWorksheetItem, hasProperty("dateChanged", is(worksheetItem.getDateChanged())));
        assertThat(savedWorksheetItem, hasProperty("voided", is(worksheetItem.getVoided())));
        assertThat(savedWorksheetItem, hasProperty("dateVoided", is(worksheetItem.getDateVoided())));
        assertThat(savedWorksheetItem, hasProperty("voidedBy", is(worksheetItem.getVoidedBy())));
        assertThat(savedWorksheetItem, hasProperty("voidReason", is(worksheetItem.getVoidReason())));
        assertThat(savedWorksheetItem, hasProperty("worksheet", is(worksheetItem.getWorksheet())));
        assertThat(savedWorksheetItem, hasProperty("testRequestItemSample", is(worksheetItem.getTestRequestItemSample())));
        assertThat(savedWorksheetItem, hasProperty("status", is(worksheetItem.getStatus())));
        assertThat(savedWorksheetItem, hasProperty("completedDate", is(worksheetItem.getCompletedDate())));
        assertThat(savedWorksheetItem, hasProperty("cancelledDate", is(worksheetItem.getCancelledDate())));
        assertThat(savedWorksheetItem, hasProperty("cancellationRemarks", is(worksheetItem.getCancellationRemarks())));
    }

    @Test
    public void saveTestRequest_shouldSaveAllProperties(){
//Given
        TestRequest testRequest=eu().newTestRequest(dao());

//When
        dao().saveTestRequest(testRequest);

//Let's clean up the cache to be sure getTestRequestByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        TestRequest savedTestRequest = dao().getTestRequestByUuid(testRequest.getUuid());
        assertThat(savedTestRequest, hasProperty("uuid", is(testRequest.getUuid())));
        assertThat(savedTestRequest, hasProperty("creator", is(testRequest.getCreator())));
        assertThat(savedTestRequest, hasProperty("dateCreated", is(testRequest.getDateCreated())));
        assertThat(savedTestRequest, hasProperty("changedBy", is(testRequest.getChangedBy())));
        assertThat(savedTestRequest, hasProperty("dateChanged", is(testRequest.getDateChanged())));
        assertThat(savedTestRequest, hasProperty("voided", is(testRequest.getVoided())));
        assertThat(savedTestRequest, hasProperty("dateVoided", is(testRequest.getDateVoided())));
        assertThat(savedTestRequest, hasProperty("voidedBy", is(testRequest.getVoidedBy())));
        assertThat(savedTestRequest, hasProperty("voidReason", is(testRequest.getVoidReason())));
        assertThat(savedTestRequest, hasProperty("patient", is(testRequest.getPatient())));
        assertThat(savedTestRequest, hasProperty("visit", is(testRequest.getVisit())));
        assertThat(savedTestRequest, hasProperty("encounter", is(testRequest.getEncounter())));
        assertThat(savedTestRequest, hasProperty("provider", is(testRequest.getProvider())));
        assertThat(savedTestRequest, hasProperty("requestNo", is(testRequest.getRequestNo())));
        assertThat(savedTestRequest, hasProperty("urgency", is(testRequest.getUrgency())));
        assertThat(savedTestRequest, hasProperty("creator", is(testRequest.getCreator())));
        assertThat(savedTestRequest, hasProperty("careSetting", is(testRequest.getCareSetting())));
        assertThat(savedTestRequest, hasProperty("status", is(testRequest.getStatus())));
        assertThat(savedTestRequest, hasProperty("atLocation", is(testRequest.getAtLocation())));
        assertThat(savedTestRequest, hasProperty("referredIn", is(testRequest.getReferredIn())));
        assertThat(savedTestRequest, hasProperty("referralFromFacility", is(testRequest.getReferralFromFacility())));
        assertThat(savedTestRequest, hasProperty("referralFromFacilityName", is(testRequest.getReferralFromFacilityName())));
        assertThat(savedTestRequest, hasProperty("referralInExternalRef", is(testRequest.getReferralInExternalRef())));

        savedTestRequest = dao().getTestRequestById(testRequest.getId());
        assertThat(savedTestRequest, hasProperty("uuid", is(testRequest.getUuid())));
        assertThat(savedTestRequest, hasProperty("creator", is(testRequest.getCreator())));
        assertThat(savedTestRequest, hasProperty("dateCreated", is(testRequest.getDateCreated())));
        assertThat(savedTestRequest, hasProperty("changedBy", is(testRequest.getChangedBy())));
        assertThat(savedTestRequest, hasProperty("dateChanged", is(testRequest.getDateChanged())));
        assertThat(savedTestRequest, hasProperty("voided", is(testRequest.getVoided())));
        assertThat(savedTestRequest, hasProperty("dateVoided", is(testRequest.getDateVoided())));
        assertThat(savedTestRequest, hasProperty("voidedBy", is(testRequest.getVoidedBy())));
        assertThat(savedTestRequest, hasProperty("voidReason", is(testRequest.getVoidReason())));
        assertThat(savedTestRequest, hasProperty("patient", is(testRequest.getPatient())));
        assertThat(savedTestRequest, hasProperty("visit", is(testRequest.getVisit())));
        assertThat(savedTestRequest, hasProperty("encounter", is(testRequest.getEncounter())));
        assertThat(savedTestRequest, hasProperty("provider", is(testRequest.getProvider())));
        assertThat(savedTestRequest, hasProperty("requestNo", is(testRequest.getRequestNo())));
        assertThat(savedTestRequest, hasProperty("urgency", is(testRequest.getUrgency())));
        assertThat(savedTestRequest, hasProperty("creator", is(testRequest.getCreator())));
        assertThat(savedTestRequest, hasProperty("careSetting", is(testRequest.getCareSetting())));
        assertThat(savedTestRequest, hasProperty("status", is(testRequest.getStatus())));
        assertThat(savedTestRequest, hasProperty("atLocation", is(testRequest.getAtLocation())));
        assertThat(savedTestRequest, hasProperty("referredIn", is(testRequest.getReferredIn())));
        assertThat(savedTestRequest, hasProperty("referralFromFacility", is(testRequest.getReferralFromFacility())));
        assertThat(savedTestRequest, hasProperty("referralFromFacilityName", is(testRequest.getReferralFromFacilityName())));
        assertThat(savedTestRequest, hasProperty("referralInExternalRef", is(testRequest.getReferralInExternalRef())));
    }

    @Test
    public void saveReferralLocation_shouldSaveAllProperties(){
//Given
        ReferralLocation referralLocation=eu().newReferralLocation(dao());

//When
        dao().saveReferralLocation(referralLocation);

//Let's clean up the cache to be sure getReferralLocationByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.flushSession();

//Then
        ReferralLocation savedReferralLocation = dao().getReferralLocationByUuid(referralLocation.getUuid());
        assertThat(savedReferralLocation, hasProperty("uuid", is(referralLocation.getUuid())));
        assertThat(savedReferralLocation, hasProperty("creator", is(referralLocation.getCreator())));
        assertThat(savedReferralLocation, hasProperty("dateCreated", is(referralLocation.getDateCreated())));
        assertThat(savedReferralLocation, hasProperty("changedBy", is(referralLocation.getChangedBy())));
        assertThat(savedReferralLocation, hasProperty("dateChanged", is(referralLocation.getDateChanged())));
        assertThat(savedReferralLocation, hasProperty("voided", is(referralLocation.getVoided())));
        assertThat(savedReferralLocation, hasProperty("dateVoided", is(referralLocation.getDateVoided())));
        assertThat(savedReferralLocation, hasProperty("voidedBy", is(referralLocation.getVoidedBy())));
        assertThat(savedReferralLocation, hasProperty("voidReason", is(referralLocation.getVoidReason())));
        assertThat(savedReferralLocation, hasProperty("concept", is(referralLocation.getConcept())));
        assertThat(savedReferralLocation, hasProperty("patient", is(referralLocation.getPatient())));
        assertThat(savedReferralLocation, hasProperty("referrerIn", is(referralLocation.getReferrerIn())));
        assertThat(savedReferralLocation, hasProperty("referrerOut", is(referralLocation.getReferrerOut())));
        assertThat(savedReferralLocation, hasProperty("enabled", is(referralLocation.getEnabled())));
        assertThat(savedReferralLocation, hasProperty("name", is(referralLocation.getName())));
        assertThat(savedReferralLocation, hasProperty("acronym", is(referralLocation.getAcronym())));

        savedReferralLocation = dao().getReferralLocationById(referralLocation.getId());
        assertThat(savedReferralLocation, hasProperty("uuid", is(referralLocation.getUuid())));
        assertThat(savedReferralLocation, hasProperty("creator", is(referralLocation.getCreator())));
        assertThat(savedReferralLocation, hasProperty("dateCreated", is(referralLocation.getDateCreated())));
        assertThat(savedReferralLocation, hasProperty("changedBy", is(referralLocation.getChangedBy())));
        assertThat(savedReferralLocation, hasProperty("dateChanged", is(referralLocation.getDateChanged())));
        assertThat(savedReferralLocation, hasProperty("voided", is(referralLocation.getVoided())));
        assertThat(savedReferralLocation, hasProperty("dateVoided", is(referralLocation.getDateVoided())));
        assertThat(savedReferralLocation, hasProperty("voidedBy", is(referralLocation.getVoidedBy())));
        assertThat(savedReferralLocation, hasProperty("voidReason", is(referralLocation.getVoidReason())));
        assertThat(savedReferralLocation, hasProperty("concept", is(referralLocation.getConcept())));
        assertThat(savedReferralLocation, hasProperty("patient", is(referralLocation.getPatient())));
        assertThat(savedReferralLocation, hasProperty("referrerIn", is(referralLocation.getReferrerIn())));
        assertThat(savedReferralLocation, hasProperty("referrerOut", is(referralLocation.getReferrerOut())));
        assertThat(savedReferralLocation, hasProperty("enabled", is(referralLocation.getEnabled())));
        assertThat(savedReferralLocation, hasProperty("name", is(referralLocation.getName())));
        assertThat(savedReferralLocation, hasProperty("acronym", is(referralLocation.getAcronym())));
    }
}
