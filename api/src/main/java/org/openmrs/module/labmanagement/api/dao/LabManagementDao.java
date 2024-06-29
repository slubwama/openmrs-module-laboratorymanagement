/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.labmanagement.api.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.openmrs.*;
import org.openmrs.Order;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.labmanagement.api.dto.TestApprovalDTO;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.patientqueueing.model.PatientQueue;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.api.db.hibernate.search.LuceneQuery;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({ "unchecked" })
public class LabManagementDao extends DaoBase {


    public TestRequestItemSample getTestRequestItemSampleById(Integer id) {
        return (TestRequestItemSample) getSession().createCriteria(TestRequestItemSample.class).add(Restrictions.eq("id", id)).uniqueResult();

    }

    public void deleteTestRequestItemSamples(List<Integer> testRequestItemSamplesIds) {
        if(testRequestItemSamplesIds == null || testRequestItemSamplesIds.isEmpty()) return ;
        DbSession session = getSession();
        Query query = session.createQuery("DELETE labmanagement.TestRequestItemSample WHERE id in :id");
        query.setParameterList("id", testRequestItemSamplesIds);
        query.executeUpdate();
    }

    public void deleteSampleById(Integer sampleId) {
        if(sampleId == null) return ;
        DbSession session = getSession();
        Query query = session.createQuery("DELETE labmanagement.Sample WHERE id in :id");
        query.setParameter("id", sampleId);
        query.executeUpdate();
    }

    public List<TestRequestItemSample> getTestRequestItemSamples(TestRequestItem testRequestItem, Boolean voided) {
         Criteria criteria = getSession().createCriteria(TestRequestItemSample.class).add(Restrictions.eq("testRequestItem", testRequestItem));
         if(voided != null){
             criteria = criteria.add(Restrictions.eq("voided", voided));
         }
        return (List<TestRequestItemSample>) criteria.list();
    }

    public List<TestRequestItemSample> getTestRequestItemSamples(Sample sample){
        return getTestRequestItemSamples(sample, false);
    }
    public List<TestRequestItemSample> getTestRequestItemSamples(Sample sample, Boolean voided) {
        Criteria criteria = getSession().createCriteria(TestRequestItemSample.class).add(Restrictions.eq("sample", sample));
        if(voided != null){
            criteria = criteria.add(Restrictions.eq("voided", voided));
        }
        return (List<TestRequestItemSample>) criteria.list();
    }

    public TestRequestItemSample getTestRequestItemSampleByUuid(String uuid) {
        return (TestRequestItemSample) getSession().createCriteria(TestRequestItemSample.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public TestRequestItemSample saveTestRequestItemSample(TestRequestItemSample testRequestItemSample) {
        getSession().saveOrUpdate(testRequestItemSample);
        return testRequestItemSample;
    }

    public TestRequest getTestRequestById(Integer id) {
        return (TestRequest) getSession().createCriteria(TestRequest.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestRequest getTestRequestByUuid(String uuid) {
        return (TestRequest) getSession().createCriteria(TestRequest.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }
    public List<TestRequestItem> getTestRequestItemsByTestRequestId(List<Integer> ids, boolean includeAll) {
        Criteria criteria =  getSession().createCriteria(TestRequestItem.class);
        criteria.add(Restrictions.in("testRequest.id", ids.toArray()));
        if(!includeAll){
            criteria.add(Restrictions.in("voided",false));
        }
        return criteria.list();
    }

    public List<TestRequestItem> getTestRequestItemsByUuid(List<String> uuids, boolean includeAll) {
        Criteria criteria =  getSession().createCriteria(TestRequestItem.class);
        criteria.add(Restrictions.in("uuid", uuids.toArray()));
        if(!includeAll){
            criteria.add(Restrictions.in("voided",false));
        }
        return criteria.list();
    }

    public TestRequest saveTestRequest(TestRequest testRequest) {
        getSession().saveOrUpdate(testRequest);
        return testRequest;
    }
    public SampleActivity getSampleActivityById(Integer id) {
        return (SampleActivity) getSession().createCriteria(SampleActivity.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public SampleActivity getSampleActivityByUuid(String uuid) {
        return (SampleActivity) getSession().createCriteria(SampleActivity.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public SampleActivity saveSampleActivity(SampleActivity sampleActivity) {
        getSession().saveOrUpdate(sampleActivity);
        return sampleActivity;
    }

    public List<SampleActivity> getSampleActivityBySample(Sample sample) {
        return (List<SampleActivity>) getSession().createCriteria(SampleActivity.class).add(Restrictions.eq("sample", sample)).list();
    }

    public List<TestResult> getSampleTestResults(Sample sample) {
        return (List<TestResult>)  getSession().createCriteria(TestResult.class).add(Restrictions.eq("sample", sample)).list();
    }

    public TestResult getTestResultById(Integer id) {
        return (TestResult) getSession().createCriteria(TestResult.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestResult getTestResultByUuid(String uuid) {
        return (TestResult) getSession().createCriteria(TestResult.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public List<TestResult> getTestResultsByUuid(List<String> uuids, boolean includeAll) {
        Criteria criteria =  getSession().createCriteria(TestResult.class);
        criteria.add(Restrictions.in("uuid", uuids.toArray()));
        if(!includeAll){
            criteria.add(Restrictions.in("voided",false));
        }
        return criteria.list();
    }

    public List<TestResult> getTestResultsBySampleId(Integer sampleId, boolean includeVoided){
        Query query = getSession().createQuery("select tr from labmanagement.TestResult tr join tr.testRequestItemSample tris where tris.sample.id = :sid " + (includeVoided ? "" : " and tris.voided = 0 and tr.voided = 0 "));
        query.setParameter("sid", sampleId);
        return (List<TestResult>) query.list();
    }

    public List<TestResult> getTestResultByWorksheetItem(String worksheetItemUuid, boolean includeVoided){
        Query query = getSession().createQuery("select tr from labmanagement.TestResult tr join tr.worksheetItem wsi where wsi.uuid = :uuid " + (includeVoided ? "" : " and wsi.voided = 0 and tr.voided = 0 "));
        query.setParameter("uuid", worksheetItemUuid);
        return (List<TestResult>) query.list();
    }

    public List<TestResult> getTestResultByTestRequestItemSample(String testRequestItemSampleUuid, boolean includeVoided){
        Query query = getSession().createQuery("select tr from labmanagement.TestResult tr join tr.testRequestItemSample tris where tris.uuid = :trsid " + (includeVoided ? "" : " and tris.voided = 0 and tr.voided = 0 "));
        query.setParameter("trsid", testRequestItemSampleUuid);
        return (List<TestResult>) query.list();
    }

    public List<TestResult> getTestResultsByWorksheetItem(WorksheetItem worksheetItem) {
        return (List<TestResult>) getSession().createCriteria(TestResult.class)
                .add(Restrictions.eq("worksheetItem", worksheetItem))
                .add(Restrictions.eq("voided", false))
                .list();
    }

    public  boolean worksheetHasTestResults(Worksheet worksheet){
        DbSession session = getSession();
        Query query = session.createQuery("select t from labmanagement.TestResult t join t.worksheetItem wi join wi.worksheet w where w.id = :id");
        query.setParameter("id", worksheet.getId());
        query.setFirstResult(0);
        query.setMaxResults(1);

        List<TestResult> result = (List<TestResult>) query.list();
        return result != null && !result.isEmpty();
    }

    public TestResult saveTestResult(TestResult testResult) {
        getSession().saveOrUpdate(testResult);
        return testResult;
    }

    public List<WorksheetItem> getWorksheetItemsBySampleId(Integer sampleId, boolean includeVoided){
        Query query = getSession().createQuery("select w from labmanagement.WorksheetItem w join w.testRequestItemSample tris where tris.sample.id = :sid " + (includeVoided ? "" : " and tris.voided = 0 and w.voided = 0 "));
        query.setParameter("sid", sampleId);
        return (List<WorksheetItem>) query.list();
    }

    public List<WorksheetItem> getWorksheetItemsByTestRequestItemSampleUuid(String testRequestItemSampleUuid, boolean includeVoided){
        Query query = getSession().createQuery("select w from labmanagement.WorksheetItem w join w.testRequestItemSample tris where tris.uuid = :tisid " + (includeVoided ? "" : " and tris.voided = 0 and w.voided = 0 "));
        query.setParameter("tisid", testRequestItemSampleUuid);
        return (List<WorksheetItem>) query.list();
    }

    public Worksheet getWorksheetById(Integer id) {
        return (Worksheet) getSession().createCriteria(Worksheet.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public void deleteWorksheetById(Integer workSheetId, String reason, int voidedBy) {
        if(workSheetId == null) return ;
       DbSession session = getSession();
        Query query = session.createQuery("Update labmanagement.Worksheet SET voided=1, dateVoided=:dateVoided, voidedBy=:voidedBy, voidReason=:reason WHERE id = :id");
        query.setInteger("id", workSheetId);
        query.setDate("dateVoided", new Date());
        query.setInteger("voidedBy", voidedBy);
        query.setString("reason", reason);
         int affectedRecords = query.executeUpdate();

    }

    public Worksheet getWorksheetByUuid(String uuid) {
        return (Worksheet) getSession().createCriteria(Worksheet.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public Worksheet saveWorksheet(Worksheet worksheet) {
        getSession().saveOrUpdate(worksheet);
        return worksheet;
    }

    public TestResultDocument getTestResultDocumentById(Integer id) {
        return (TestResultDocument) getSession().createCriteria(TestResultDocument.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestResultDocument getTestResultDocumentByUuid(String uuid) {
        return (TestResultDocument) getSession().createCriteria(TestResultDocument.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public TestResultDocument saveTestResultDocument(TestResultDocument testResultDocument) {
        getSession().saveOrUpdate(testResultDocument);
        return testResultDocument;
    }

    public ApprovalConfig getApprovalConfigById(Integer id) {
        return (ApprovalConfig) getSession().createCriteria(ApprovalConfig.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public ApprovalConfig getApprovalConfigByUuid(String uuid) {
        return (ApprovalConfig) getSession().createCriteria(ApprovalConfig.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public ApprovalConfig saveApprovalConfig(ApprovalConfig approvalConfig) {
        getSession().saveOrUpdate(approvalConfig);
        return approvalConfig;
    }

    public ApprovalFlow getApprovalFlowById(Integer id) {
        return (ApprovalFlow) getSession().createCriteria(ApprovalFlow.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public ApprovalFlow getApprovalFlowByUuid(String uuid) {
        return (ApprovalFlow) getSession().createCriteria(ApprovalFlow.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public ApprovalFlow saveApprovalFlow(ApprovalFlow approvalFlow) {
        getSession().saveOrUpdate(approvalFlow);
        return approvalFlow;
    }

    public WorksheetItem getWorksheetItemById(Integer id) {
        return (WorksheetItem) getSession().createCriteria(WorksheetItem.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public WorksheetItem getWorksheetItemByUuid(String uuid) {
        return (WorksheetItem) getSession().createCriteria(WorksheetItem.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public List<WorksheetItem> getWorksheetItemsByWorksheetId(Integer worksheetId) {
        return (List<WorksheetItem>) getSession().createCriteria(WorksheetItem.class)
                .add(Restrictions.eq("worksheet.id", worksheetId))
                .add(Restrictions.eq("voided", false))
                .list();
    }


    public WorksheetItem saveWorksheetItem(WorksheetItem worksheetItem) {
        getSession().saveOrUpdate(worksheetItem);
        return worksheetItem;
    }

    public void deleteWorksheetItemsById(List<Integer> workSheetItemIds) {
        if(workSheetItemIds == null || workSheetItemIds.isEmpty()) return ;
        DbSession session = getSession();
        Query query = session.createQuery("DELETE labmanagement.WorksheetItem WHERE id in (:id)");
        query.setParameterList("id", workSheetItemIds);
        query.executeUpdate();
    }

    public void deleteWorksheetItemsByWorksheetId(Integer workSheetId) {
        if(workSheetId == null) return ;
        DbSession session = getSession();
        Query query = session.createQuery("DELETE labmanagement.WorksheetItem WHERE worksheet.id = :id");
        query.setParameter("id", workSheetId);
        query.executeUpdate();
    }

    public BatchJob getBatchJobById(Integer id) {
        return (BatchJob) getSession().createCriteria(BatchJob.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public BatchJob getBatchJobByUuid(String uuid) {
        return (BatchJob) getSession().createCriteria(BatchJob.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public BatchJob saveBatchJob(BatchJob batchJob) {
        getSession().saveOrUpdate(batchJob);
        return batchJob;
    }

    public Sample getSampleById(Integer id) {
        return (Sample) getSession().createCriteria(Sample.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public Sample getSampleByUuid(String uuid) {
        return (Sample) getSession().createCriteria(Sample.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public List<Sample> getTestRequestSamplesByUuid(Integer testRequestId, List<String> sampleUuids) {
        return (List<Sample>) getSession().createCriteria(Sample.class)
                .add(Restrictions.in("uuid", sampleUuids))
                .add(Restrictions.eq("testRequest.id", testRequestId)).list();
    }

    public List<Sample> getSamplesByTestRequest(TestRequest testRequest) {
        return (List<Sample>) getSession().createCriteria(Sample.class).add(Restrictions.eq("testRequest", testRequest)).list();
    }

    public List<Sample> getSamplesByTestRequestItem(TestRequestItem testRequestItem) {
        Query query = getSession().createQuery("select s from labmanagement.TestRequestItemSample tris join tris.sample s where tris.testRequestItem.id = : trii and tris.voided = 0 and s.voided = 0 ");
        query.setParameter("trii", testRequestItem.getId());
        return (List<Sample>) query.list();
    }

    public Sample saveSample(Sample sample) {
        getSession().saveOrUpdate(sample);
        return sample;
    }

    public TestApproval getTestApprovalById(Integer id) {
        return (TestApproval) getSession().createCriteria(TestApproval.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestApproval getTestApprovalByUuid(String uuid) {
        return (TestApproval) getSession().createCriteria(TestApproval.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public TestApproval saveTestApproval(TestApproval testApproval) {
        getSession().saveOrUpdate(testApproval);
        return testApproval;
    }

    public TestRequestItem getTestRequestItemById(Integer id) {
        return (TestRequestItem) getSession().createCriteria(TestRequestItem.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestRequestItem getTestRequestItemByUuid(String uuid) {
        return (TestRequestItem) getSession().createCriteria(TestRequestItem.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public TestRequestItem saveTestRequestItem(TestRequestItem testRequestItem) {
        getSession().saveOrUpdate(testRequestItem);
        return testRequestItem;
    }

    public TestConfig getTestConfigById(Integer id) {
        return (TestConfig) getSession().createCriteria(TestConfig.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public TestConfig getTestConfigByConcept(Integer conceptId) {
        return (TestConfig) getSession().createCriteria(TestConfig.class)
                .add(Restrictions.eq("test.id", conceptId))
                .add(Restrictions.eq("voided", false)).uniqueResult();
    }

    public TestConfig getTestConfigByUuid(String uuid) {
        return (TestConfig) getSession().createCriteria(TestConfig.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public TestConfig saveTestConfig(TestConfig testConfig) {
        getSession().saveOrUpdate(testConfig);
        return testConfig;
    }

    public BatchJobOwner getBatchJobOwnerById(Integer id) {
        return (BatchJobOwner) getSession().createCriteria(BatchJobOwner.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public BatchJobOwner getBatchJobOwnerByUuid(String uuid) {
        return (BatchJobOwner) getSession().createCriteria(BatchJobOwner.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public BatchJobOwner saveBatchJobOwner(BatchJobOwner batchJobOwner) {
        getSession().saveOrUpdate(batchJobOwner);
        return batchJobOwner;
    }

    private List<PatientIdentifierDTO> getPatientIdentifiersByPatientIds(List<Integer> ids, List<String> patientIdentifierTypeIds) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        Query query = getSession().createQuery("select pi.patient.patientId as patientId, pi.identifier as identifier, pi.identifierType.uuid as identifierTypeUuid from PatientIdentifier pi where pi.patient.patientId in (:ids) and pi.identifierType.uuid in (:cnt1)")
                .setParameterList("ids", ids)
                .setParameterList("cnt1",  patientIdentifierTypeIds);
        query = query.setResultTransformer(new AliasToBeanResultTransformer(PatientIdentifierDTO.class));
        return query.list();
    }

    private List<UserPersonNameDTO> getPatientNameByPatientIds(List<Integer> ids, boolean includePatientIdentifier) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select p.uuid as uuid, up.person_id as patientId, p.given_name as givenName, p.middle_name as middleName, p.family_name as familyName " +
                        (includePatientIdentifier ? ",(select pi.identifier from patient_identifier pi where pi.patient_id = up.person_id order by pi.preferred desc, pi.patient_identifier_id asc limit 1) as patientIdentifier" : "") +
                        " from person up join person_name p on up.person_id = p.person_id where up.person_id in (:ids)")
                .setParameterList("ids", ids);
        query = query.setResultTransformer(new AliasToBeanResultTransformer(UserPersonNameDTO.class));
        return query.list();
    }

    private List<UserPersonNameDTO> getPersonNameByPersonIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();

        Query query = sessionFactory.getCurrentSession().createSQLQuery("select up.uuid as uuid, up.person_id as personId, p.given_name as givenName, p.middle_name as middleName, p.family_name as familyName " +
                        " from person up join person_name p on up.person_id = p.person_id  where up.person_id in (:ids)")
                .setParameterList("ids", ids);
        query = query.setResultTransformer(new AliasToBeanResultTransformer(UserPersonNameDTO.class));
        return query.list();
    }

    public List<ConceptNameDTO> getConceptNamesByConceptIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        Query query = getSession().createQuery("select cc.concept.conceptId as conceptId, cc.name as name, cc.conceptNameType as conceptNameType, cc.locale as locale, cc.localePreferred as localePreferred from ConceptName cc where cc.concept.conceptId in (:ids) and cc.conceptNameType = :cnt1 order by cc.conceptNameType")
                .setParameterList("ids", ids)
                .setParameter("cnt1",  ConceptNameType.FULLY_SPECIFIED);

        query = query.setResultTransformer(new AliasToBeanResultTransformer(ConceptNameDTO.class));
        return query.list();
    }

    public String getConceptName(Map<Integer, List<ConceptNameDTO>> conceptNameDTOS, Integer conceptId, ConceptNameType conceptNameType){
        if( conceptId == null || conceptNameDTOS == null || conceptNameDTOS.isEmpty() || !conceptNameDTOS.containsKey(conceptId)) return null;

        List<ConceptNameDTO> conceptNames = conceptNameDTOS.get(conceptId).stream().filter(p -> p.getConceptId().equals(conceptId) && p.conceptNameType == conceptNameType).collect(Collectors.toList());
        ConceptNameDTO conceptName = ConceptNameDTO.getPreferredConceptName(conceptNames);
        return conceptName == null ? null : conceptName.getName();
    }

    public String getConceptName(List<ConceptNameDTO> conceptNameDTOS, Integer conceptId, ConceptNameType conceptNameType){
        if( conceptId == null || conceptNameDTOS == null || conceptNameDTOS.isEmpty()) return null;
        List<ConceptNameDTO> conceptNames = conceptNameDTOS.stream().filter(p -> p.getConceptId().equals(conceptId) && p.conceptNameType == conceptNameType).collect(Collectors.toList());
        ConceptNameDTO conceptName = ConceptNameDTO.getPreferredConceptName(conceptNames);
         return conceptName == null ? null : conceptName.getName();
    }

    public List<ConceptNameDTO> getConceptNamesAndShortsByConceptIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        Query query = getSession().createQuery("select cc.concept.conceptId as conceptId, cc.name as name, cc.conceptNameType as conceptNameType, cc.locale as locale, cc.localePreferred as localePreferred from ConceptName cc where cc.concept.conceptId in (:ids) and cc.conceptNameType in (:cnt1, :cnt2) order by cc.conceptNameType")
                .setParameterList("ids", ids)
                .setParameter("cnt1",  ConceptNameType.FULLY_SPECIFIED)
                .setParameter("cnt2", ConceptNameType.SHORT);


        query = query.setResultTransformer(new AliasToBeanResultTransformer(ConceptNameDTO.class));
        ConceptNameType p;
        return query.list();
    }
    public List<UserPersonNameDTO> getPersonNameByUserIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        Query query = sessionFactory.getCurrentSession().createQuery("select u.userId as userId, p.givenName as givenName, p.middleName as middleName, p.familyName as familyName from User u join u.person up join up.names p where u.userId in (:ids)")
                .setParameterList("ids", ids);
        query = query.setResultTransformer(new AliasToBeanResultTransformer(UserPersonNameDTO.class));
        return query.list();
    }

    public Result<TestConfigDTO> findTestConfigurations(TestConfigSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select tc.uuid as uuid, tc.id as id,\n" +
                "t.conceptId as testId,\n" +
                "t.uuid as testUuid,\n" +
                "tg.conceptId as testGroupId,\n" +
                "tg.uuid as testGroupUuid,\n" +
                "af.id as approvalFlowId,\n" +
                "af.uuid as approvalFlowUuid,\n" +
                "af.name as approvalFlowName,\n" +
                "tc.requireApproval as requireApproval,\n" +
                "tc.enabled as enabled,\n" +
                "tc.creator.userId as creator,\n" +
                "tc.creator.uuid as creatorUuid,\n" +
                "tc.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "tc.dateChanged as dateChanged,\n" +
                "tc.voided as voided \n" +
                "from labmanagement.TestConfig tc left join\n" +
                " tc.test t left join\n" +
                " tc.testGroup tg left join tc.approvalFlow af left join tc.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getTestConfigUuid())) {
            appendFilter(hqlFilter, "tc.uuid = :uuid");
            parameterList.put("uuid", filter.getTestConfigUuid());
        }

        if (filter.getTestConfigId() != null) {
            appendFilter(hqlFilter, "tc.id = :id");
            parameterList.put("id", filter.getTestConfigId());
        }

        if (!StringUtils.isBlank(filter.getTestUuid())) {
            appendFilter(hqlFilter, "t.uuid = :testUuid");
            parameterList.put("testUuid", filter.getTestUuid());
        }

        if (filter.getTestId() != null) {
            appendFilter(hqlFilter, "t.conceptId = :testId");
            parameterList.put("testId", filter.getTestId());
        }

        if (!StringUtils.isBlank(filter.getTestGroupUuid())) {
            appendFilter(hqlFilter, "tg.uuid = :testGroupUuid");
            parameterList.put("testGroupUuid", filter.getTestGroupUuid());
        }

        if (filter.getTestGroupId() != null) {
            appendFilter(hqlFilter, "tg.conceptId = :testGroupId");
            parameterList.put("testGroupId", filter.getTestGroupId());
        }

        if(filter.getApprovalFlowId() != null){
            appendFilter(hqlFilter, "af.id = :afid");
            parameterList.put("afid", filter.getApprovalFlowId());

        }

        if(filter.getActive() != null){
            appendFilter(hqlFilter, "tc.enabled = :enabled");
            parameterList.put("enabled", filter.getActive());
        }

        StringBuilder itemFilter = new StringBuilder();
        if (filter.getTestIds() != null && !filter.getTestIds().isEmpty()) {
            appendORFilter(itemFilter, "t.conceptId in (:testIds)");
            parameterWithList.putIfAbsent("testIds", filter.getTestIds());
        }

        if (filter.getTestUuids() != null && !filter.getTestUuids().isEmpty()) {
            appendORFilter(itemFilter, "t.uuid in (:testUuids)");
            parameterWithList.putIfAbsent("testUuids", filter.getTestUuids());
        }

        if (itemFilter.length() > 0) {
            appendFilter(hqlFilter, itemFilter.toString());
        }

        if(filter.getTestOrGroupIds() != null && !filter.getTestOrGroupIds().isEmpty()){
            StringBuilder testOrGroupFilter = new StringBuilder();
            appendORFilter(testOrGroupFilter, "t.conceptId in (:tis)");
            appendORFilter(testOrGroupFilter, "tg.conceptId in (:tis)");
            parameterWithList.putIfAbsent("tis", filter.getTestOrGroupIds());
            appendFilter(hqlFilter, testOrGroupFilter.toString());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "tc.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestConfigDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }
        result.setData(executeQuery(TestConfigDTO.class, hqlQuery, result, " order by tg.id asc", parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p -> Arrays.asList(
                            p.getTestId(),
                            p.getTestGroupId()
                    )).flatMap(Collection::stream)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());
            if (!conceptNamesToFetch.isEmpty()) {
                Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
                for (TestConfigDTO testConfigDTO : result.getData()) {
                    testConfigDTO.setTestName(getConceptName(conceptNameDTOs,testConfigDTO.getTestId(),ConceptNameType.FULLY_SPECIFIED));
                    testConfigDTO.setTestShortName(getConceptName(conceptNameDTOs,testConfigDTO.getTestId(),ConceptNameType.SHORT));
                    testConfigDTO.setTestGroupName(getConceptName(conceptNameDTOs,testConfigDTO.getTestGroupId(),ConceptNameType.FULLY_SPECIFIED));
                }
            }
            result.getData().sort(Comparator.comparing(TestConfigDTO::getTestName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                            .thenComparing(TestConfigDTO::getTestGroupName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));
        }
        return result;
    }



    public Result<ApprovalFlowDTO> findApprovalFlows(ApprovalFlowSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select af.uuid as uuid, af.id as id,\n" +
                "af.name as name,\n" +
                "af.systemName as systemName,\n" +
                "l1.uuid as levelOneUuid,\n" +
                "l1.approvalTitle as levelOneApprovalTitle,\n" +
                "l2.uuid as levelTwoUuid,\n" +
                "l2.approvalTitle as levelTwoApprovalTitle,\n" +
                "l3.uuid as levelThreeUuid,\n" +
                "l3.approvalTitle as levelThreeApprovalTitle,\n" +
                "l4.uuid as levelFourUuid,\n" +
                "l4.approvalTitle as levelFourApprovalTitle,\n" +
                "af.levelOneAllowOwner as levelOneAllowOwner,\n" +
                "af.levelTwoAllowOwner as levelTwoAllowOwner,\n" +
                "af.levelThreeAllowOwner as levelThreeAllowOwner,\n" +
                "af.levelFourAllowOwner as levelFourAllowOwner,\n" +
                "af.levelTwoAllowPrevious as levelTwoAllowPrevious,\n" +
                "af.levelThreeAllowPrevious as levelThreeAllowPrevious,\n" +
                "af.levelFourAllowPrevious as levelFourAllowPrevious,\n" +
                "af.creator.userId as creator,\n" +
                "af.creator.uuid as creatorUuid,\n" +
                "af.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "af.dateChanged as dateChanged,\n" +
                "af.voided as voided\n" +
                "from labmanagement.ApprovalFlow af left join\n" +
                " af.levelOne l1 left join\n" +
                " af.levelTwo l2 left join af.levelThree l3 left join af.levelFour l4 left join af.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();

        if (!StringUtils.isBlank(filter.getApprovalFlowUuid())) {
            appendFilter(hqlFilter, "af.uuid = :uuid");
            parameterList.put("uuid", filter.getApprovalFlowUuid());
        }

        if (filter.getApprovalFlowId() != null) {
            appendFilter(hqlFilter, "af.id = :id");
            parameterList.put("id", filter.getApprovalFlowId());
        }

        if (!StringUtils.isBlank(filter.getNameOrSystemName())) {

            appendFilter(hqlFilter, "lower(af.name) = :name or lower(af.systemName) = :name" );
            parameterList.put("name", filter.getNameOrSystemName().toLowerCase());
        }

        if (filter.getApprovalConfigId() != null) {

            appendFilter(hqlFilter, "l1.id = :acid or l2.id = :acid or l3.id = :acid or l4.id = :acid" );
            parameterList.put("acid", filter.getApprovalConfigId());
        }

        if (!StringUtils.isBlank(filter.getApprovalConfigUuid())) {

            appendFilter(hqlFilter, "l1.uuid = :acuuid or l2.uuid = :acuuid or l3.uuid = :acuuid or l4.uuid = :acuuid" );
            parameterList.put("acuuid", filter.getApprovalConfigUuid());
        }

        if (!StringUtils.isBlank(filter.getSearchText())) {
            appendFilter(hqlFilter, "lower(af.name) like :q or lower(af.systemName) like :q or lower(l1.approvalTitle) like :q  or lower(l2.approvalTitle) like :q  or lower(l3.approvalTitle) like :q  or lower(l4.approvalTitle) like :q" );
            parameterList.put("q", "%" + filter.getSearchText().toLowerCase() + "%");
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "af.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<ApprovalFlowDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }
        result.setData(executeQuery(ApprovalFlowDTO.class, hqlQuery, result, " order by af.name asc", parameterList, parameterWithList));

        return result;
    }

    public List<Concept> getConcepts(Collection<Integer> conceptIds) {
        if (conceptIds == null || conceptIds.isEmpty()) return new ArrayList<>();
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(Concept.class, "c");
        criteria.add(Restrictions.in("c.conceptId", conceptIds));
        return criteria.list();
    }

    public List<TestConfig> getTestConfigsByIds(Collection<Integer> testConfigIds) {
        if (testConfigIds == null || testConfigIds.isEmpty()) return new ArrayList<>();
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(TestConfig.class, "t");
        criteria.add(Restrictions.in("t.id", testConfigIds));
        return criteria.list();
    }

    public List<ApprovalFlow> getApprovalFlowsBySystemName(List<String> approvalFlowSystemNames) {
        if (approvalFlowSystemNames == null || approvalFlowSystemNames.isEmpty()) return new ArrayList<>();
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(ApprovalFlow.class, "c");
        if(approvalFlowSystemNames.size() == 1){
            criteria.add(Restrictions.eq("c.systemName", approvalFlowSystemNames.get(0)).ignoreCase());
        }else {
            LogicalExpression orFilter =  Restrictions.or(Restrictions.eq("c.systemName", approvalFlowSystemNames.get(0)).ignoreCase(),
                    Restrictions.eq("c.systemName", approvalFlowSystemNames.get(1)).ignoreCase());
            for (int i = 2; i < approvalFlowSystemNames.size(); i++) {
                orFilter = Restrictions.or(orFilter, Restrictions.eq("c.systemName", approvalFlowSystemNames.get(i)).ignoreCase());
            }
        }
        criteria.add(Restrictions.eq("c.voided", false));
        return criteria.list();
    }

    public Result<ApprovalConfigDTO> findApprovalConfigurations(ApprovalConfigSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select ac.uuid as uuid, ac.id as id,\n" +
                "ac.approvalTitle as approvalTitle,\n" +
                "ac.privilege as privilege,\n" +
                "ac.pendingStatus as pendingStatus,\n" +
                "ac.returnedStatus as returnedStatus,\n" +
                "ac.rejectedStatus as rejectedStatus,\n" +
                "ac.approvedStatus as approvedStatus,\n" +
                "ac.creator.userId as creator,\n" +
                "ac.creator.uuid as creatorUuid,\n" +
                "ac.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "ac.dateChanged as dateChanged,\n" +
                "ac.voided as voided\n" +
                "from labmanagement.ApprovalConfig ac left join ac.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();

        if (!StringUtils.isBlank(filter.getApprovalConfigUuid())) {
            appendFilter(hqlFilter, "ac.uuid = :uuid");
            parameterList.put("uuid", filter.getApprovalConfigUuid());
        }

        if (filter.getApprovalConfigId() != null) {
            appendFilter(hqlFilter, "ac.id = :id");
            parameterList.put("id", filter.getApprovalConfigId());
        }

        if (!StringUtils.isBlank(filter.getApprovalTitle())) {

            appendFilter(hqlFilter, "lower(ac.approvalTitle) = :approvalTitle " );
            parameterList.put("approvalTitle", filter.getApprovalTitle().toLowerCase());
        }

        if (!StringUtils.isBlank(filter.getSearchText())) {
            appendFilter(hqlFilter, "lower(ac.approvalTitle) like :q or lower(ac.privilege) like :q or lower(ac.pendingStatus) like :q  or lower(ac.returnedStatus) like :q  or lower(ac.rejectedStatus) like :q  or lower(ac.approvedStatus) like :q" );
            parameterList.put("q", "%" + filter.getSearchText().toLowerCase() + "%");
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "ac.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<ApprovalConfigDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }
        result.setData(executeQuery(ApprovalConfigDTO.class, hqlQuery, result, " order by ac.approvalTitle asc", parameterList, parameterWithList));

        return result;
    }

    public boolean CheckApprovalConfigUsage(Integer approvalConfigId){
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(TestApproval.class, "c");
        criteria.add(Restrictions.eq("c.id", approvalConfigId));
        criteria.add(Restrictions.eq("c.voided", false));
        criteria.setMaxResults(1);
        criteria.setFirstResult(0);
        Object result = criteria.uniqueResult();
        return result != null;
    }

    public boolean patientQueueExists(Encounter encounter, Location locationTo, Location locationFrom, PatientQueue.Status status){
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(PatientQueue.class, "c");
        criteria.add(Restrictions.eq("c.encounter", encounter));
        criteria.add(Restrictions.eq("c.status", status));
        criteria.add(Restrictions.eq("c.locationTo", locationTo));
        criteria.add(Restrictions.eq("c.locationFrom", locationFrom));
        criteria.add(Restrictions.eq("c.dateCreated",  OpenmrsUtil.firstSecondOfDay(encounter.getEncounterDatetime())));
        criteria.add(Restrictions.between("c.dateCreated",
                        OpenmrsUtil.firstSecondOfDay(encounter.getEncounterDatetime()), OpenmrsUtil.getLastMomentOfDay(encounter.getEncounterDatetime())));
        criteria.setMaxResults(1);
        criteria.setFirstResult(0);
        Object result = criteria.uniqueResult();
        return result != null;
    }

    public ReferralLocation getReferralLocationById(Integer id) {
        return (ReferralLocation) getSession().createCriteria(ReferralLocation.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public ReferralLocation getReferralLocationByUuid(String uuid) {
        return (ReferralLocation) getSession().createCriteria(ReferralLocation.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
    }

    public ReferralLocation saveReferralLocation(ReferralLocation referralLocation) {
        getSession().saveOrUpdate(referralLocation);
        return referralLocation;
    }

    public Result<ReferralLocationDTO> findReferralLocations(ReferralLocationSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select rl.uuid as uuid, rl.id as id, rl.name as name, rl.acronym as acronym,\n" +
                "c.conceptId as conceptId,\n" +
                "c.uuid as conceptUuid,\n" +
                "p.patientId as patientId,\n" +
                "p.uuid as patientUuid,\n" +
                "rl.referrerIn as referrerIn,\n" +
                "rl.referrerOut as referrerOut,\n" +
                "rl.enabled as enabled,\n" +
                "rl.system as system,\n" +
                "rl.creator.userId as creator,\n" +
                "rl.creator.uuid as creatorUuid,\n" +
                "rl.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "rl.dateChanged as dateChanged,\n" +
                "rl.voided as voided \n" +
                "from labmanagement.ReferralLocation rl left join\n" +
                " rl.concept c left join\n" +
                " rl.patient p left join\n" +
                " rl.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getReferralLocationUuid())) {
            appendFilter(hqlFilter, "rl.uuid = :uuid");
            parameterList.put("uuid", filter.getReferralLocationUuid());
        }

        if (filter.getReferralLocationId() != null) {
            appendFilter(hqlFilter, "rl.id = :id");
            parameterList.put("id", filter.getReferralLocationId());
        }

        if (!StringUtils.isBlank(filter.getConceptUuid())) {
            appendFilter(hqlFilter, "c.uuid = :conceptUuid");
            parameterList.put("conceptUuid", filter.getConceptUuid());
        }

        if (filter.getConceptId() != null) {
            appendFilter(hqlFilter, "c.conceptId = :conceptId");
            parameterList.put("conceptId", filter.getConceptId());
        }

        if (!StringUtils.isBlank(filter.getPatientUuid())) {
            appendFilter(hqlFilter, "p.uuid = :patientUuid");
            parameterList.put("patientUuid", filter.getPatientUuid());
        }

        if (filter.getPatientId() != null) {
            appendFilter(hqlFilter, "p.patientId = :patientId");
            parameterList.put("patientId", filter.getPatientId());
        }

        if (!StringUtils.isBlank(filter.getName())) {
            appendFilter(hqlFilter, "lower(rl.name) = :name");
            parameterList.put("name", filter.getName().toLowerCase());
        }

        if (!StringUtils.isBlank(filter.getAcronym())) {
            appendFilter(hqlFilter, "lower(rl.acronym) = :acronym");
            parameterList.put("acronym", filter.getAcronym().toLowerCase());
        }

        if(filter.getReferrerIn() != null){
            appendFilter(hqlFilter, "rl.referrerIn = :referrerIn");
            parameterList.put("referrerIn", filter.getReferrerIn());
        }

        if(filter.getReferrerOut() != null){
            appendFilter(hqlFilter, "rl.referrerOut = :referrerOut");
            parameterList.put("referrerOut", filter.getReferrerOut());
        }

        if(filter.getActive() != null){
            appendFilter(hqlFilter, "rl.enabled = :enabled");
            parameterList.put("enabled", filter.getActive());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "rl.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }
        PersonName pn;
        if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = "%" + filter.getSearchText().toLowerCase() + "%";
            appendFilter(textSearch, "lower(rl.name) like :qtxt or lower(rl.acronym) like :qtxt");
            appendORFilter(textSearch, "exists ( from ConceptName cn where cn.concept.id = c.conceptId and lower(cn.name) like :qtxt and cn.voided = 0 )");
            appendORFilter(textSearch, "exists ( from PersonName pn where pn.person.id = p.id and ( lower(pn.givenName) like :qtxt or lower(pn.familyName) like :qtxt or lower(pn.middleName) like :qtxt ) )");
            parameterList.putIfAbsent("qtxt", q);
            appendFilter(hqlFilter, textSearch.toString());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<ReferralLocationDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }
        result.setData(executeQuery(ReferralLocationDTO.class, hqlQuery, result, " order by rl.id asc", parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {

            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(ReferralLocationDTO::getConceptId
                    )
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());
            List<Integer> patientIds = result.getData().stream().map(ReferralLocationDTO::getPatientId).filter(Objects::nonNull).collect(Collectors.toList());
            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
            Map<Integer,List<UserPersonNameDTO>> patientNames = getPatientNameByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), true).stream().collect(Collectors.groupingBy(p -> p.getPatientId()));

            for (ReferralLocationDTO referralLocationDTO : result.getData()) {
                referralLocationDTO.setConceptName(getConceptName(conceptNameDTOs,referralLocationDTO.getConceptId(), ConceptNameType.FULLY_SPECIFIED));
                if (referralLocationDTO.getPatientId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = patientNames.get(referralLocationDTO.getPatientId());
                    if (userPersonNameDTO != null) {
                        referralLocationDTO.setPatientFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        referralLocationDTO.setPatientMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        referralLocationDTO.setPatientGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }
            }

            result.getData().sort(Comparator.comparing(ReferralLocationDTO::getDisplayName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                    .thenComparing(ReferralLocationDTO::getConceptName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                    .thenComparing(ReferralLocationDTO::getPatientFamilyName, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));
        }
        return result;
    }

    public Result<TestRequestDTO> findTestRequests(TestRequestSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
Provider provider;
        StringBuilder hqlQuery = new StringBuilder("select tr.uuid as uuid, tr.id as id, tr.requestDate as requestDate,\n" +
                "tr.requestNo as requestNo,\n" +
                "tr.urgency as urgency,\n" +
                "tr.clinicalNote as clinicalNote,\n" +
                "tr.requestReason as requestReason,\n" +
                "cs.uuid as careSettingUuid,\n" +
                "cs.name as careSettingName,\n" +
                "tr.dateStopped as dateStopped,\n" +
                "tr.status as status,\n" +
                "tr.referredIn as referredIn,\n" +
                "rff.uuid as referralFromFacilityUuid,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +
                "p.patientId as patientId,\n" +
                "p.uuid as patientUuid,\n" +
                "pv.person.id as providerId,\n" +
                "pv.uuid as providerUuid,\n" +
                "tr.creator.userId as creator,\n" +
                "tr.creator.uuid as creatorUuid,\n" +
                "tr.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "tr.dateChanged as dateChanged,\n" +
                "tr.voided as voided \n" +
                "from labmanagement.TestRequest tr left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " tr.careSetting cs left join\n" +
                " tr.patient p left join\n" +
                " tr.provider pv left join\n" +
                " tr.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getTestRequestUuid())) {
            appendFilter(hqlFilter, "tr.uuid = :uuid");
            parameterList.put("uuid", filter.getTestRequestUuid());
        }

        if (filter.getTestRequestId() != null) {
            appendFilter(hqlFilter, "tr.id = :id");
            parameterList.put("id", filter.getTestRequestId());
        }

        if(filter.getMinActivatedDate() != null){
            appendFilter(hqlFilter, "tr.dateCreated >= :mindc");
            parameterList.put("mindc", filter.getMinActivatedDate());
        }

        if(filter.getMaxActivatedDate() != null){
            appendFilter(hqlFilter, "tr.dateCreated <= :maxdc");
            parameterList.put("maxdc", filter.getMaxActivatedDate());
        }

        StringBuilder itemCheck = null;
        if((filter.getItemStatuses()!= null && !filter.getItemStatuses().isEmpty()) ||
                (filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty()) ||
                filter.getReferredOut() != null || filter.getItemLocationId() != null || filter.getPendingResultApproval() != null){
            itemCheck=new StringBuilder("exists ( from labmanagement.TestRequestItem tri ");
            if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())){
                itemCheck.append("left join tri.order trio ");
            }

            if(filter.getPendingResultApproval() != null && filter.getPendingResultApproval()){
                itemCheck.append("left join tri.testRequestItemSamples tris left join tris.testResults tristr left join tristr.currentApproval tristrca ");
            }

            itemCheck.append(" where tri.testRequest.id = tr.id and ");
            if((filter.getItemStatuses()!= null && !filter.getItemStatuses().isEmpty()) ) {
                itemCheck.append("tri.status in :tristatus and ");
                parameterWithList.put("tristatus", filter.getItemStatuses());
            }

            if(filter.getItemLocationId() != null){
                itemCheck.append("tri.toLocation.locationId = :tlid and ");
                parameterList.put("tlid", filter.getItemLocationId());
            }

            if(filter.getReferredOut() != null){
                itemCheck.append("tri.referredOut = :trifo and ");
                parameterList.put("trifo", filter.getReferredOut());
            }

            itemCheck.append("tri.voided = 0 ");

            if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())){
                itemCheck.append("and trio.concept.conceptId in :trioc and trio.voided = 0 ");
                parameterWithList.put("trioc", filter.getTestConceptIds());
            }

            if(filter.getPendingResultApproval() != null){
                itemCheck.append("and tris.voided=0 and tristr.requireApproval=1 and tristr.completed=0 and tristr.voided = 0 ");
                if(filter.getOnlyPendingResultApproval()){
                    itemCheck.append("and tristrca.approvalResult is null");
                }
            }

            itemCheck.append(")");
        }

        StringBuilder sampleCheck =null;
        if((filter.getSampleStatuses() != null && !filter.getSampleStatuses().isEmpty())){
            sampleCheck=new StringBuilder("exists ( from labmanagement.Sample ls where ls.testRequest.id = tr.id and ");
            if((filter.getSampleStatuses()!= null && !filter.getSampleStatuses().isEmpty()) ) {
                sampleCheck.append("ls.status in :lsstatus and ");
                parameterWithList.put("lsstatus", filter.getSampleStatuses());
            }
            sampleCheck.append("ls.voided = 0 ");
            sampleCheck.append(")");
        }

        if(itemCheck != null && itemCheck.length() > 0 && sampleCheck != null && sampleCheck.length() > 0){
            appendFilter(hqlFilter, "(" + itemCheck.toString() +
                    ((filter.getTestItemSampleCriteria() != null && filter.getTestItemSampleCriteria().equals(TestItemSampleCriteria.OR)) ?  " OR " : " AND ")
                    + sampleCheck.toString() + ")");
        }else if(itemCheck != null && itemCheck.length() > 0){
            appendFilter(hqlFilter, itemCheck.toString());
        }else if(sampleCheck != null && sampleCheck.length() > 0){
            appendFilter(hqlFilter, sampleCheck.toString());
        }

        if(filter.getReferredIn() != null){
            appendFilter(hqlFilter, "tr.referredIn = :refin");
            parameterList.put("refin", filter.getReferredIn());
        }

        if (filter.getPatientId() != null) {
            appendFilter(hqlFilter, "tr.patient.id = :patientId");
            parameterList.put("patientId", filter.getPatientId());
        }

        if(filter.getUrgency() != null){
            appendFilter(hqlFilter, "tr.urgency = :urgency");
            parameterList.put("urgency", filter.getUrgency());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "tr.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = filter.getSearchText().toLowerCase() + "%";
            String qLast = "%" + filter.getSearchText().toLowerCase() ;
            appendFilter(textSearch, "lower(tr.requestNo) like :qtxt or lower(tr.requestNo) like :qtxtl");
            appendORFilter(textSearch, "exists ( from PersonName pn where pn.person.id = tr.patient.id  and ( lower(pn.givenName) like :qtxt or lower(pn.familyName) like :qtxt or lower(pn.middleName) like :qtxt ) )");
            appendORFilter(textSearch, "exists ( from labmanagement.TestRequestItem tri2 join tri2.order o2 join o2.concept oc2 join oc2.names cn2 where tri2.testRequest.id = tr.id and lower(cn2.name) like :qtxt and cn2.voided = 0 )");
            parameterList.putIfAbsent("qtxt", q);
            parameterList.putIfAbsent("qtxtl", qLast);
            appendFilter(hqlFilter, textSearch.toString());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestRequestDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1str.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "datecreated":
                        orderByBuilder.append(String.format("%1str.dateCreated %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by tr.id asc");
        }

        result.setData(executeQuery(TestRequestDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .filter(p-> p.getReferralFromFacilityId() != null && StringUtils.isBlank(p.getReferralFromFacilityName()))
                    .map(TestRequestDTO::getReferralFromFacilityId
                    )
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());
            List<Integer> patientIds = result.getData().stream().map(TestRequestDTO::getPatientId).filter(Objects::nonNull).collect(Collectors.toList());
            List<Integer> personIds = result.getData().stream().map(TestRequestDTO::getProviderId).filter(Objects::nonNull).collect(Collectors.toList());
            Map<Integer,List<UserPersonNameDTO>> patientNames = getPatientNameByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), false).stream().collect(Collectors.groupingBy(p -> p.getPatientId()));
            Map<Integer,List<UserPersonNameDTO>> personNames = getPersonNameByPersonIds(personIds.stream().distinct().collect(Collectors.toList())).stream().collect(Collectors.groupingBy(p -> p.getPersonId()));
            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
            List<String> patientIdentifierTypeIds = new ArrayList<>();
            String patientIdentifierSetting = GlobalProperties.getPatientIdentifierTypes();
            Map<Integer, List<PatientIdentifierDTO>> patientIdentifiers = null;
            if(StringUtils.isNotBlank(patientIdentifierSetting)) {
                patientIdentifierTypeIds = Arrays.stream(patientIdentifierSetting.split(",")).map(p->p.trim()).collect(Collectors.toList());
                patientIdentifiers = getPatientIdentifiersByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), patientIdentifierTypeIds).stream().collect(Collectors.groupingBy(PatientIdentifierDTO::getPatientId));
            }

            for (TestRequestDTO testRequestDTO : result.getData()) {
                if (testRequestDTO.getPatientId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = patientNames.get(testRequestDTO.getPatientId());
                    if (userPersonNameDTO != null) {
                        testRequestDTO.setPatientFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        testRequestDTO.setPatientMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        testRequestDTO.setPatientGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }

                if (testRequestDTO.getProviderId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = personNames.get(testRequestDTO.getProviderId());
                    if (userPersonNameDTO != null) {
                        testRequestDTO.setProviderFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        testRequestDTO.setProviderMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        testRequestDTO.setProviderGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }

                if (StringUtils.isBlank(testRequestDTO.getReferralFromFacilityName())) {
                    testRequestDTO.setReferralFromFacilityName(getConceptName(conceptNameDTOs,testRequestDTO.getReferralFromFacilityId(), ConceptNameType.FULLY_SPECIFIED));
                }

                if (patientIdentifiers != null && !patientIdentifiers.isEmpty()) {
                    List<PatientIdentifierDTO> identifiers = patientIdentifiers.get(testRequestDTO.getPatientId());
                    if (identifiers != null && !identifiers.isEmpty()) {
                        for (String patientIdentifier : patientIdentifierTypeIds) {
                            Optional<PatientIdentifierDTO> identifier = identifiers.stream()
                                    .filter(p -> patientIdentifier.equals(p.getIdentifierTypeUuid())).findFirst();
                            if (identifier.isPresent()) {
                                testRequestDTO.setPatientIdentifier(identifier.get().getIdentifier());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public Result<TestRequestItemDTO> findTestRequestItems(TestRequestItemSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();

        StringBuilder hqlQuery = new StringBuilder("select tri.uuid as uuid, tri.id as id, \n" +
                "o.orderId as orderId,\n" +
                "o.orderNumber as orderNumber,\n" +
                "o.uuid as orderUuid,\n" +
                "oc.conceptId as orderConceptId,\n" +
                "oc.uuid as testUuid,\n" +
                "al.uuid as atLocationUuid,\n" +
                "al.name as atLocationName,\n" +
                "tl.uuid as toLocationUuid,\n" +
                "tl.name as toLocationName,\n" +
                "tri.referredOut as referredOut,\n" +
                "tri.returnCount as returnCount,\n" +
                "tri.status as status,\n" +
                "tri.referralOutOrigin as referralOutOrigin,\n" +
                "rob.userId as referralOutBy,\n" +
                "rob.uuid as referralOutByUuid,\n" +
                "tri.referralOutDate as referralOutDate,\n" +
                "rtf.uuid as referralToFacilityUuid,\n" +
                "rtf.concept.id as referralToFacilityId,\n" +
                "coalesce(tri.referralToFacilityName, rtf.name) as referralToFacilityName,\n" +
                "tri.requireRequestApproval as requireRequestApproval,\n" +
                "tri.requestApprovalResult as requestApprovalResult,\n" +
                "rab.userId as requestApprovalBy,\n" +
                "rab.uuid as requestApprovalByUuid,\n" +
                "tri.requestApprovalDate as requestApprovalDate,\n" +
                "tri.requestApprovalRemarks as requestApprovalRemarks,\n" +
                "tri.initialSampleId as initialSampleId,\n" +
                "tri.finalResultId as finalResultId,\n" +
                "e.uuid as encounterUuid,\n" +
                "ros.uuid as referralOutSampleUuid,\n" +
                "tri.completed as completed,\n" +
                "tr.uuid as testRequestUuid,\n" +
                "tri.creator.userId as creator,\n" +
                "tri.creator.uuid as creatorUuid,\n" +
                "tri.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "tri.dateChanged as dateChanged,\n" +
                "tri.voided as voided \n" +
                "from labmanagement.TestRequestItem tri left join\n" +
                " tri.testRequest tr left join\n" +
                " tri.order o left join\n" +
                (filter.getPendingResultApproval() != null && filter.getPendingResultApproval() ?
                        " tri.testRequestItemSamples tris left join tris.testResults tristr left join tristr.currentApproval tristrca left join "
                        : "") +
                " tri.atLocation al left join\n" +
                " tri.toLocation tl left join\n" +
                " tri.referralOutBy rob left join\n" +
                " tri.requestApprovalBy rab left join\n" +
                " tri.changedBy cb left join\n" +
                " o.concept oc left join\n" +
                " tri.encounter e left join\n" +
                " tri.referralOutSample ros left join\n" +
                " tri.referralToFacility rtf\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getTestRequestItemUuid())) {
            appendFilter(hqlFilter, "tri.uuid = :uuid");
            parameterList.put("uuid", filter.getTestRequestItemUuid());
        }

        if (filter.getTestRequestItemId() != null) {
            appendFilter(hqlFilter, "tri.id = :id");
            parameterList.put("id", filter.getTestRequestItemId());
        }

        if(filter.getTestRequestIds() != null && !filter.getTestRequestIds().isEmpty()){
            appendFilter(hqlFilter, "tr.id in :trid");
            parameterWithList.put("trid", filter.getTestRequestIds());
        }

        if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())){
            appendFilter(hqlFilter, "oc.conceptId in :tric");
            parameterWithList.put("tric", filter.getTestConceptIds());
        }

        if(filter.getItemStatuses() != null && !filter.getItemStatuses().isEmpty()){
            appendFilter(hqlFilter, "tri.status in :status");
            parameterWithList.put("status", filter.getItemStatuses());
        }

        if(filter.getItemLocationId() != null){
            appendFilter(hqlFilter, "tl.locationId = :tlid");
            parameterList.put("tlid", filter.getItemLocationId());
        }

        if(filter.getReferredOut() != null){
            appendFilter(hqlFilter,"tri.referredOut = :trifo");
            parameterList.put("trifo", filter.getReferredOut());
        }

        if (filter.getPatientId() != null) {
            appendFilter(hqlFilter, "tr.patient.id = :patientId");
            parameterList.put("patientId", filter.getPatientId());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "tri.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if(filter.getPendingResultApproval() != null && filter.getPendingResultApproval()) {
            appendFilter(hqlFilter, "tris.voided=0 and tristr.requireApproval=1 and tristr.completed=0 and tristr.voided = 0");
            if (filter.getOnlyPendingResultApproval()) {
                appendFilter(hqlFilter, "tristrca.approvalResult is null");
            }
        }

        if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = filter.getSearchText().toLowerCase() + "%";
            String qLast = "%" + filter.getSearchText().toLowerCase();
            appendFilter(textSearch, "lower(o.orderNumber) like :qtxt or lower(o.orderNumber) like :qtxtl");
            appendORFilter(textSearch, "exists ( from ConceptName cn where cn.concept.id = oc.conceptId and lower(cn.name) like :qtxt and cn.voided = 0 )");
            parameterList.putIfAbsent("qtxt", q);
            parameterList.putIfAbsent("qtxtl", qLast);
            appendFilter(hqlFilter, textSearch.toString());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestRequestItemDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1stri.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "status":
                        orderByBuilder.append(String.format("%1stri.status %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by tri.id asc");
        }

        result.setData(executeQuery(TestRequestItemDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p-> Arrays.asList(p.getOrderConceptId(),p.getReferralToFacilityId() != null && StringUtils.isBlank(p.getReferralToFacilityName()) ? p.getReferralToFacilityId() : null ))
                    .flatMap(Collection::stream)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());

            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));

            for (TestRequestItemDTO testRequestItemDTO : result.getData()) {
                if (StringUtils.isBlank(testRequestItemDTO.getReferralToFacilityName())) {
                    testRequestItemDTO.setReferralToFacilityName(getConceptName(conceptNameDTOs,testRequestItemDTO.getReferralToFacilityId(), ConceptNameType.FULLY_SPECIFIED));
                }
                testRequestItemDTO.setTestName(getConceptName(conceptNameDTOs,testRequestItemDTO.getOrderConceptId(), ConceptNameType.FULLY_SPECIFIED));
                testRequestItemDTO.setTestShortName(getConceptName(conceptNameDTOs,testRequestItemDTO.getOrderConceptId(), ConceptNameType.SHORT));
            }
        }
        return result;
    }


    private StringBuilder newSampleReferenceQuery(List<String> tokenizedName, String escapedName, boolean searchKeywords) {
        StringBuilder query = new StringBuilder();
        query.append("(");
        if (searchKeywords) {
            query.append(" accessionNumber:(\"" + escapedName + "\")^0.7");
            if (!tokenizedName.isEmpty()) {
                query.append(" OR (");
                Iterator var5 = tokenizedName.iterator();

                while (var5.hasNext()) {
                    String token = (String) var5.next();
                    query.append(" (accessionNumber:(");
                    query.append(token);
                    query.append(")^0.6 OR accessionNumber:(");
                    query.append(token);
                    query.append("*)^0.3 OR accessionNumber:(");
                    query.append(token);
                    query.append("~0.8)^0.1)");
                }

                query.append(")^0.3");
            }
        } else {
            query.append(" accessionNumber:\"" + escapedName + "\"");
        }

        query.append(") OR (");
        if (searchKeywords) {
            query.append(" externalRef:(\"" + escapedName + "\")^0.7");
            if (!tokenizedName.isEmpty()) {
                query.append(" OR (");
                Iterator var5 = tokenizedName.iterator();

                while (var5.hasNext()) {
                    String token = (String) var5.next();
                    query.append(" (externalRef:(");
                    query.append(token);
                    query.append(")^0.6 OR externalRef:(");
                    query.append(token);
                    query.append("*)^0.3 OR externalRef:(");
                    query.append(token);
                    query.append("~0.8)^0.1)");
                }

                query.append(")^0.3");
            }
        } else {
            query.append(" externalRef:\"" + escapedName + "\"");
        }

        query.append(")");
        return query;
    }

    protected LuceneQuery<Sample> newSampleQuery(String referenceNumber, boolean includeAll) {
        if (StringUtils.isBlank(referenceNumber)) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        String barcodeQuery = LuceneQuery.escapeQuery(referenceNumber);
        List tokenizedName = Arrays.asList(barcodeQuery.trim().split("\\+"));
        query.append("((");
        query.append(this.newSampleReferenceQuery(tokenizedName, barcodeQuery, true));
        query.append(")^0.3 OR providerRef:(\"").append(barcodeQuery).append("\")^0.6)");

        Class sampleClass = Sample.class;
        Session session = getCurrentHibernateSession();
        LuceneQuery<Sample> itemsQuery = LuceneQuery.newQuery(sampleClass, session, query.toString());
        if (!includeAll) {
            itemsQuery.include("voided", Boolean.valueOf(false));
        }
        return itemsQuery;
    }

    public List<Integer> searchSampleReferenceNumbers(String text, boolean includeAll, int maxItems) {
        LuceneQuery referenceNumberQuery = this.newSampleQuery(text, includeAll);
        if (referenceNumberQuery == null) return new ArrayList<>();
        List sampleIds = referenceNumberQuery.listProjection("id");
        if (!sampleIds.isEmpty()) {
            CollectionUtils.transform(sampleIds, new Transformer() {
                public Object transform(Object input) {
                    return ((Object[]) input)[0];
                }
            });
            int maxSize = sampleIds.size() < maxItems ? sampleIds.size() : maxItems;
            sampleIds = sampleIds.subList(0, maxSize);
            return sampleIds;
        }
        return new ArrayList<>();
    }

    public Result<SampleDTO> findSamples(SampleSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select s.uuid as uuid, s.id as id, ps.uuid as parentSampleUuid,\n" +
                "st.conceptId as sampleTypeId,\n" +
                "st.uuid as sampleTypeUuid,\n" +
                "al.uuid as atLocationUuid,\n" +
                "al.name as atLocationName,\n" +
                "ct.conceptId as containerTypeId,\n" +
                "ct.uuid as containerTypeUuid,\n" +
                "clb.userId as collectedBy,\n" +
                "clb.uuid as collectedByUuid,\n" +
                "s.collectionDate as collectionDate,\n" +
                "s.containerCount as containerCount,\n" +
                "s.providedRef as providedRef,\n" +
                "s.volume as volume,\n" +
                "vu.conceptId as volumeUnitId,\n" +
                "vu.uuid as volumeUnitUuid,\n" +
                "s.accessionNumber as accessionNumber,\n" +
                "s.externalRef as externalRef,\n" +
                "s.referredOut as referredOut,\n" +
                "s.referralOutOrigin as referralOutOrigin,\n" +
                "rob.userId as referralOutBy,\n" +
                "rob.uuid as referralOutByUuid,\n" +
                "s.referralOutDate as referralOutDate,\n" +
                "rtf.uuid as referralToFacilityUuid,\n" +
                "rtf.concept.id as referralToFacilityId,\n" +
                "coalesce(s.referralToFacilityName, rtf.name) as referralToFacilityName,\n" +
                "s.status as status,\n" +
                "e.uuid as encounterUuid,\n" +
                "tr.id as testRequestId,\n" +
                "tr.uuid as testRequestUuid,\n" +
                "p.patientId as patientId,\n" +
                "p.uuid as patientUuid,\n" +
                "rff.uuid as referralFromFacilityUuid,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +
                "s.creator.userId as creator,\n" +
                "s.creator.uuid as creatorUuid,\n" +
                "s.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "s.dateChanged as dateChanged,\n" +
                "s.voided as voided \n" +
                (filter.getForWorksheet() ?
                        " ,trise.uuid as testRequestItemSampleUuid ,trise.id as testRequestItemSampleId from labmanagement.TestRequestItemSample trise left join trise.worksheetItems twsi  left join trise.testResults ttr left join trise.sample s  left join\n" :
                        "from labmanagement.Sample s left join\n" ) +
                " s.sampleType st left join\n" +
                " s.atLocation al left join\n" +
                " s.containerType ct left join\n" +
                " s.volumeUnit vu left join\n" +
                " s.referralOutBy rob left join\n" +
                " s.referralToFacility rtf left join\n" +
                " s.collectedBy clb left join\n" +
                " s.currentSampleActivity csa left join\n" +
                " s.testRequest tr left join\n" +
                " tr.patient p left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " s.parentSample ps left join\n" +
                " s.encounter e left join\n" +
                " s.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();

        if (!StringUtils.isBlank(filter.getSampleUuid())) {
            appendFilter(hqlFilter, "s.uuid = :uuid");
            parameterList.put("uuid", filter.getSampleUuid());
        }

        List<Integer> sampleIds = new ArrayList<>();
        if(filter.getSampleId() != null){
            sampleIds.add(filter.getSampleId());
        }
        if(filter.getSampleIds() != null){
            sampleIds.addAll(filter.getSampleIds());
        }
        if (sampleIds.size() == 1) {
            appendFilter(hqlFilter, "s.id = :id");
            parameterList.put("id", sampleIds.get(0));
        }else if(!sampleIds.isEmpty()){
            appendFilter(hqlFilter, "s.id in :ids");
            parameterWithList.put("ids", sampleIds);
        }

        if(filter.getForWorksheet()){
            appendFilter(hqlFilter, "twsi.id is null and ttr.id is null");
        }

        List<Integer> testRequestIds = new ArrayList<>();
        if(filter.getTestRequestId() != null) testRequestIds.add(filter.getTestRequestId());
        if(filter.getTestRequestIds() != null) testRequestIds.addAll(filter.getTestRequestIds());

        if (!testRequestIds.isEmpty()) {
            if(testRequestIds.size() == 1) {
                if (filter.getIncludeSamplesInStorage()) {
                    appendFilter(hqlFilter, "tr.id = :trid or (tr.patient.id = :patientId and s.status in :activeStatus)");
                    parameterList.put("patientId", filter.getPatientId());
                    parameterList.put("trid", testRequestIds.get(0));
                    parameterWithList.put("activeStatus", SampleStatus.getActiveStatuses());
                } else {
                    appendFilter(hqlFilter, "tr.id = :trid");
                    parameterList.put("trid", testRequestIds.get(0));
                }
            }else{
                if (filter.getIncludeSamplesInStorage() && filter.getPatientId() != null) {
                    appendFilter(hqlFilter, "tr.id in :trid or (tr.patient.id = :patientId and s.status in :activeStatus)");
                    parameterList.put("patientId", filter.getPatientId());
                    parameterWithList.put("trid", testRequestIds);
                    parameterWithList.put("activeStatus", SampleStatus.getActiveStatuses());
                } else {
                    appendFilter(hqlFilter, "tr.id in :trid");
                    parameterWithList.put("trid", testRequestIds);
                }
            }
        }

        if(filter.getSampleTypeId() != null){
            appendFilter(hqlFilter, "st.id = :stid");
            parameterList.put("stid", filter.getSampleTypeId());
        }

        if (filter.getSampleStatuses() != null) {
            appendFilter(hqlFilter, "s.status = :status");
            parameterWithList.put("status", filter.getSampleStatuses());
        }

        if(StringUtils.isNotBlank(filter.getReference())){
            appendFilter(hqlFilter, "s.accessionNumber = :ref or s.externalRef = :ref or s.providedRef = :ref");
            parameterList.put("ref", filter.getReference());
        }

        if (filter.getPatientId() != null) {
            appendFilter(hqlFilter, "tr.patient.id = :patientId");
            parameterList.put("patientId", filter.getPatientId());
        }

        if(filter.getMinActivatedDate() != null){
            appendFilter(hqlFilter, "tr.dateCreated >= :mindc");
            parameterList.put("mindc", filter.getMinActivatedDate());
        }

        if(filter.getMaxActivatedDate() != null){
            appendFilter(hqlFilter, "tr.dateCreated <= :maxdc");
            parameterList.put("maxdc", filter.getMaxActivatedDate());
        }

        if(filter.getMinCollectionDate() != null){
            appendFilter(hqlFilter, "s.collectionDate >= :mincd");
            parameterList.put("mincd", filter.getMinCollectionDate());
        }

        if(filter.getMaxCollectionDate() != null){
            appendFilter(hqlFilter, "s.collectionDate <= :maxcd");
            parameterList.put("maxcd", filter.getMaxCollectionDate());
        }

        if (filter.getLocationId() != null) {
            appendFilter(hqlFilter, "al.id = :alid");
            parameterList.put("alid", filter.getLocationId());
        }

        if (filter.getUrgency() != null) {
            appendFilter(hqlFilter, "tr.urgency = :urgency");
            parameterList.put("urgency", filter.getUrgency());
        }

        if((filter.getTestRequestItemConceptIds() != null && !filter.getTestRequestItemConceptIds().isEmpty()) ||
                (filter.getTestRequestItemStatuses() != null && !filter.getTestRequestItemStatuses().isEmpty()) ||
                (filter.getTestItemlocationId() != null)  ){
            StringBuilder itemCheck=new StringBuilder("exists ( from labmanagement.TestRequestItemSample tris join tris.testRequestItem tri2 ");
            if((filter.getTestRequestItemConceptIds() != null && !filter.getTestRequestItemConceptIds().isEmpty()) ){
                itemCheck.append("left join tri2.order o2 join o2.concept oc2 ");
            }
            itemCheck.append(" where tris.sample.id = s.id and tris.voided=0 and ");


            if((filter.getTestRequestItemStatuses() != null && !filter.getTestRequestItemStatuses().isEmpty())){
                itemCheck.append("tri2.status in :tr2status and ");
                parameterWithList.put("tr2status", filter.getTestRequestItemStatuses());
            }

            if(filter.getTestItemlocationId() != null){
                itemCheck.append("tri2.toLocation.locationId = :tlid2 and ");
                parameterList.put("tlid2", filter.getTestItemlocationId());
            }

            if((filter.getTestRequestItemConceptIds() != null && !filter.getTestRequestItemConceptIds().isEmpty())){
                itemCheck.append("oc2.conceptId in :tcids and ");
                parameterWithList.put("tcids", filter.getTestRequestItemConceptIds());
            }

            itemCheck.append("tri2.voided = 0 ");
            itemCheck.append(")");
            appendFilter(hqlFilter, itemCheck.toString());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "s.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        /*if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = filter.getSearchText().toLowerCase() + "%";
            String qLast = "%" + filter.getSearchText().toLowerCase() ;
            appendFilter(textSearch, "lower(s.accessionNumber) like :qtxt or lower(s.accessionNumber) like :qtxtl or lower(s.externalRef) like :qtxt or lower(s.externalRef) like :qtxtl");
            parameterList.putIfAbsent("qtxt", q);
            parameterList.putIfAbsent("qtxtl", qLast);
            appendFilter(hqlFilter, textSearch.toString());
        }*/

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<SampleDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1ss.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "datecreated":
                        orderByBuilder.append(String.format("%1ss.dateCreated %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by s.id desc");
        }

        result.setData(executeQuery(SampleDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .flatMap(p-> Stream.of(p.getSampleTypeId(), p.getContainerTypeId(), p.getVolumeUnitId(),
                            StringUtils.isBlank(p.getReferralToFacilityName()) && p.getReferralToFacilityId() != null ?
                                    p.getReferralToFacilityId() : null,
                            p.getReferralFromFacilityId() != null && StringUtils.isBlank(p.getReferralFromFacilityName()) ? p.getReferralFromFacilityId() : null)
                    )
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());
            List<Integer> patientIds = result.getData().stream().map(SampleDTO::getPatientId).filter(Objects::nonNull).collect(Collectors.toList());
            Map<Integer,List<UserPersonNameDTO>> patientNames = getPatientNameByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), false).stream().collect(Collectors.groupingBy(p -> p.getPatientId()));
            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
            List<String> patientIdentifierTypeIds = new ArrayList<>();
            String patientIdentifierSetting = GlobalProperties.getPatientIdentifierTypes();
            Map<Integer, List<PatientIdentifierDTO>> patientIdentifiers = null;
            if(StringUtils.isNotBlank(patientIdentifierSetting)) {
                patientIdentifierTypeIds = Arrays.stream(patientIdentifierSetting.split(",")).map(p->p.trim()).collect(Collectors.toList());
                patientIdentifiers = getPatientIdentifiersByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), patientIdentifierTypeIds).stream().collect(Collectors.groupingBy(PatientIdentifierDTO::getPatientId));
            }

            for (SampleDTO sampleDTO : result.getData()) {
                if (sampleDTO.getPatientId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = patientNames.get(sampleDTO.getPatientId());
                    if (userPersonNameDTO != null) {
                        sampleDTO.setPatientFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        sampleDTO.setPatientMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        sampleDTO.setPatientGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }
                if (patientIdentifiers != null && !patientIdentifiers.isEmpty()) {
                    List<PatientIdentifierDTO> identifiers = patientIdentifiers.get(sampleDTO.getPatientId());
                    if (identifiers != null && !identifiers.isEmpty()) {
                        for (String patientIdentifier : patientIdentifierTypeIds) {
                            Optional<PatientIdentifierDTO> identifier = identifiers.stream()
                                    .filter(p -> patientIdentifier.equals(p.getIdentifierTypeUuid())).findFirst();
                            if (identifier.isPresent()) {
                                sampleDTO.setPatientIdentifier(identifier.get().getIdentifier());
                                break;
                            }
                        }
                    }
                }

                sampleDTO.setSampleTypeName(getConceptName(conceptNameDTOs,sampleDTO.getSampleTypeId(), ConceptNameType.FULLY_SPECIFIED));
                sampleDTO.setContainerTypeName(getConceptName(conceptNameDTOs,sampleDTO.getContainerTypeId(), ConceptNameType.FULLY_SPECIFIED));
                sampleDTO.setVolumeUnitName(getConceptName(conceptNameDTOs,sampleDTO.getVolumeUnitId(), ConceptNameType.FULLY_SPECIFIED));

                if (StringUtils.isBlank(sampleDTO.getReferralToFacilityName()) ) {
                    sampleDTO.setReferralToFacilityName(getConceptName(conceptNameDTOs,sampleDTO.getReferralToFacilityId(), ConceptNameType.FULLY_SPECIFIED));
                }

                if (StringUtils.isBlank(sampleDTO.getReferralFromFacilityName())) {
                    sampleDTO.setReferralFromFacilityName(getConceptName(conceptNameDTOs,sampleDTO.getReferralFromFacilityId(), ConceptNameType.FULLY_SPECIFIED));
                }
            }
        }
        return result;
    }

    public Map<Integer, List<SampleDTO>> getTestRequestItemSampleRefs(List<Integer> testRequestItemIds) {
        if (testRequestItemIds == null || testRequestItemIds.isEmpty()) return new HashMap<>();
       List<SampleDTO> testRequestItemSampleRefs = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = testRequestItemIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            testRequestItemSampleRefs.addAll(getTestRequestItemSampleRefsInternal(batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return testRequestItemSampleRefs.stream().collect(Collectors.groupingBy(SampleDTO::getTestRequestItemId));
    }

    private List<SampleDTO> getTestRequestItemSampleRefsInternal(List<Integer> testRequestItemIds){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select s.uuid as uuid, s.id as id,\n" +
                "s.providedRef as providedRef,\n" +
                "s.accessionNumber as accessionNumber,\n" +
                "s.externalRef as externalRef,\n" +
                "s.status as status,\n" +
                "tris.uuid as testRequestItemSampleUuid,\n" +
                "tris.testRequestItem.id as testRequestItemId\n" +
                "from labmanagement.TestRequestItemSample tris join tris.sample s\n");

        StringBuilder hqlFilter = new StringBuilder();
        appendFilter(hqlFilter, "tris.testRequestItem.id in :ids");
        parameterWithList.put("ids", testRequestItemIds);
        appendFilter(hqlFilter, "tris.voided = :voided");
        appendFilter(hqlFilter, "s.voided = :voided");
        parameterList.put("voided", false);

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<SampleDTO> result = new Result<>();
        return executeQuery(SampleDTO.class, hqlQuery, result, null, parameterList, parameterWithList);
    }

    public Map<Integer, List<WorksheetItemDTO>> getTestRequestItemWorksheetRefs(List<Integer> testRequestItemIds) {
        if (testRequestItemIds == null || testRequestItemIds.isEmpty()) return new HashMap<>();
        List<WorksheetItemDTO> testRequestItemSampleRefs = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = testRequestItemIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            testRequestItemSampleRefs.addAll(getTestRequestItemWorksheetRefsInternal(batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return testRequestItemSampleRefs.stream().collect(Collectors.groupingBy(WorksheetItemDTO::getWorksheetId));
    }

    private List<WorksheetItemDTO> getTestRequestItemWorksheetRefsInternal(List<Integer> testRequestItemIds){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select tris.testRequestItem.id as worksheetId,\n" +
                "ws.worksheetNo as worksheetNo,\n" +
                "ws.uuid as worksheetUuid\n" +
                "from labmanagement.TestRequestItemSample tris join tris.worksheetItems wsi join wsi.worksheet ws\n");

        StringBuilder hqlFilter = new StringBuilder();
        appendFilter(hqlFilter, "tris.testRequestItem.id in :ids");
        parameterWithList.put("ids", testRequestItemIds);
        appendFilter(hqlFilter, "tris.voided = :voided");
        parameterList.put("voided", false);

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<SampleDTO> result = new Result<>();
        return executeQuery(WorksheetItemDTO.class, hqlQuery, result, null, parameterList, parameterWithList);
    }

    public List<Sample> getChildSamples(Sample sample){
        if (sample == null) return new ArrayList<>();
        DbSession dbSession = getSession();
        Criteria criteria = dbSession.createCriteria(Sample.class, "s");
        criteria.add(Restrictions.eq("s.parentSample", sample));
        return criteria.list();
    }

    public Map<Integer, List<TestRequestItemDTO>> getTestRequestItemRefsBySampleIds(List<Integer> sampleIds) {
        if (sampleIds == null || sampleIds.isEmpty()) return new HashMap<>();
        List<TestRequestItemDTO> testRequestItemRefs = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = sampleIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            testRequestItemRefs.addAll(getTestRequestItemRefsInternal(batch, null));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return testRequestItemRefs.stream().collect(Collectors.groupingBy(TestRequestItemDTO::getInitialSampleId));
    }

    public Map<Integer, List<TestRequestItemDTO>> getTestRequestItemRefsByTestRequestSampleIds(List<Integer> testRequestItemSampleIds) {
        if (testRequestItemSampleIds == null || testRequestItemSampleIds.isEmpty()) return new HashMap<>();
        List<TestRequestItemDTO> testRequestItemRefs = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = testRequestItemSampleIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            testRequestItemRefs.addAll(getTestRequestItemRefsInternal(null, batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return testRequestItemRefs.stream().collect(Collectors.groupingBy(TestRequestItemDTO::getTestRequestItemSampleId));
    }

    private List<TestRequestItemDTO> getTestRequestItemRefsInternal(List<Integer> sampleIds, List<Integer> testRequestItemSampleIds){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select tri.uuid as uuid, tri.id as id, tri.status as status,\n" +
                "tris.sample.id as initialSampleId,\n" +
                "tris.id as testRequestItemSampleId,\n" +
                "tris.uuid as testRequestItemSampleUuid,\n" +
                "oc.conceptId as orderConceptId,\n" +
                "oc.uuid as testUuid,\n" +
                "tl.id as toLocationId,\n" +
                "tl.uuid as toLocationUuid,\n" +
                "tl.name as toLocationName," +
                "o.urgency as urgency," +
                "p.id as patientId,\n" +
                "p.uuid as patientUuid\n" +
                "from labmanagement.TestRequestItemSample tris join " +
                "tris.testRequestItem tri left join " +
                "tri.order o left join " +
                "o.concept oc left join " +
                "o.patient p left join " +
                "tri.toLocation tl\n");

        StringBuilder hqlFilter = new StringBuilder();

        if((testRequestItemSampleIds == null || testRequestItemSampleIds.isEmpty())
                && (sampleIds == null || sampleIds.isEmpty())){
            return  new ArrayList<>();
        }

        if(testRequestItemSampleIds != null && !testRequestItemSampleIds.isEmpty()){
            appendFilter(hqlFilter, "tris.id in :trisids");
            parameterWithList.put("trisids", testRequestItemSampleIds);
        }

        if(sampleIds != null && !sampleIds.isEmpty()) {
            appendFilter(hqlFilter, "tris.sample.id in :ids");
            parameterWithList.put("ids", sampleIds);
        }

        appendFilter(hqlFilter, "tris.voided = :voided");
        appendFilter(hqlFilter, "tri.voided = :voided");
        parameterList.put("voided", false);

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestRequestItemDTO> result = new Result<>();
        result.setData(executeQuery(TestRequestItemDTO.class, hqlQuery, result, null, parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p-> p.getOrderConceptId())
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());

            List<Integer> patientIds = result.getData().stream().map(TestRequestItemDTO::getPatientId).filter(Objects::nonNull).collect(Collectors.toList());
            Map<Integer,List<UserPersonNameDTO>> patientNames = getPatientNameByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), false).stream().collect(Collectors.groupingBy(p -> p.getPatientId()));
            List<String> patientIdentifierTypeIds = new ArrayList<>();
            String patientIdentifierSetting = GlobalProperties.getPatientIdentifierTypes();
            Map<Integer, List<PatientIdentifierDTO>> patientIdentifiers = null;
            if(StringUtils.isNotBlank(patientIdentifierSetting)) {
                patientIdentifierTypeIds = Arrays.stream(patientIdentifierSetting.split(",")).map(p->p.trim()).collect(Collectors.toList());
                patientIdentifiers = getPatientIdentifiersByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), patientIdentifierTypeIds).stream().collect(Collectors.groupingBy(PatientIdentifierDTO::getPatientId));
            }

            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
            for (TestRequestItemDTO testRequestItemDTO : result.getData()) {
                testRequestItemDTO.setTestName(getConceptName(conceptNameDTOs,testRequestItemDTO.getOrderConceptId(),ConceptNameType.FULLY_SPECIFIED));
                testRequestItemDTO.setTestShortName(getConceptName(conceptNameDTOs,testRequestItemDTO.getOrderConceptId(),ConceptNameType.SHORT));

                if (testRequestItemDTO.getPatientId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = patientNames.get(testRequestItemDTO.getPatientId());
                    if (userPersonNameDTO != null) {
                        testRequestItemDTO.setPatientFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        testRequestItemDTO.setPatientMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        testRequestItemDTO.setPatientGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }

                if (patientIdentifiers != null && !patientIdentifiers.isEmpty()) {
                    List<PatientIdentifierDTO> identifiers = patientIdentifiers.get(testRequestItemDTO.getPatientId());
                    if (identifiers != null && !identifiers.isEmpty()) {
                        for (String patientIdentifier : patientIdentifierTypeIds) {
                            Optional<PatientIdentifierDTO> identifier = identifiers.stream()
                                    .filter(p -> patientIdentifier.equals(p.getIdentifierTypeUuid())).findFirst();
                            if (identifier.isPresent()) {
                                testRequestItemDTO.setPatientIdentifier(identifier.get().getIdentifier());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return result.getData();
    }

    public void updateOrderInstructions(Order order, String instructions){
        DbSession session = getSession();
        Query query = session.createSQLQuery("UPDATE orders SET instructions = :insttxt WHERE order_id = :p");
        query.setParameter("p", order);
        query.setParameter("insttxt", instructions);
        query.executeUpdate();
        session.refresh(order);
    }

    public Result<WorksheetDTO> findWorksheets(WorksheetSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        Provider provider;
        StringBuilder hqlQuery = new StringBuilder("select ws.uuid as uuid, ws.id as id, ws.worksheetDate as worksheetDate,\n" +
                "al.uuid as atLocationUuid,\n" +
                "al.name as atLocationName,\n" +
                "ws.worksheetNo as worksheetNo,\n" +
                "ws.remarks as remarks,\n" +
                "t.uuid as testUuid,\n" +
                "t.conceptId as testId,\n" +
                "dt.uuid as diagnosisTypeUuid,\n" +
                "dt.conceptId as diagnosisTypeId,\n" +
                "ws.status as status,\n" +
                "rp.userId as responsiblePersonId,\n" +
                "rp.uuid as responsiblePersonUuid,\n" +
                "ws.responsiblePersonOther as responsiblePersonOther,\n" +
                "ws.creator.userId as creator,\n" +
                "ws.creator.uuid as creatorUuid,\n" +
                "ws.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "ws.dateChanged as dateChanged,\n" +
                "ws.voided as voided \n" +
                "from labmanagement.Worksheet ws left join\n" +
                " ws.atLocation al left join\n" +
                " ws.test t left join\n" +
                " ws.diagnosisType dt left join\n" +
                " ws.responsiblePerson rp left join\n" +
                " ws.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getWorksheetUuid())) {
            appendFilter(hqlFilter, "ws.uuid = :uuid");
            parameterList.put("uuid", filter.getWorksheetUuid());
        }

        if (filter.getWorksheetId() != null) {
            appendFilter(hqlFilter, "ws.id = :id");
            parameterList.put("id", filter.getWorksheetId());
        }

        if(filter.getMinActivatedDate() != null){
            appendFilter(hqlFilter, "ws.dateCreated >= :mindc");
            parameterList.put("mindc", filter.getMinActivatedDate());
        }

        if(filter.getMaxActivatedDate() != null){
            appendFilter(hqlFilter, "ws.dateCreated <= :maxdc");
            parameterList.put("maxdc", filter.getMaxActivatedDate());
        }

        if(filter.getAtLocationId() != null){
            appendFilter(hqlFilter, "al.id = :alid");
            parameterList.put("alid", filter.getAtLocationId());
        }

        if(filter.getWorksheetStatuses() != null && !filter.getWorksheetStatuses().isEmpty()){
            appendFilter(hqlFilter, "ws.status in :status");
            parameterWithList.put("status", filter.getWorksheetStatuses());
        }

        if(filter.getResponsiblePersonUserId() != null){
            appendFilter(hqlFilter, "rp.userId = :rpid");
            parameterList.put("rpid", filter.getResponsiblePersonUserId());
        }

        StringBuilder itemCheck = null;
        if((filter.getWorksheetItemStatuses()!= null && !filter.getWorksheetItemStatuses().isEmpty()) ||
                (filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty()) ||
               StringUtils.isNotBlank(filter.getSampleRef()) || filter.getPatientId() != null ||
                filter.getUrgency() != null){
            itemCheck=new StringBuilder("exists ( from labmanagement.WorksheetItem wsi ");
            if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())
             || (filter.getWorksheetItemStatuses()!= null && !filter.getWorksheetItemStatuses().isEmpty())
                    || filter.getPatientId() != null || filter.getUrgency() != null || StringUtils.isNotBlank(filter.getSampleRef())){
                itemCheck.append("left join wsi.testRequestItemSample tris left join  tris.testRequestItem tri ");
            }

            if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())
                    || filter.getPatientId() != null || filter.getUrgency() != null){
                itemCheck.append("left join tri.order trio ");
            }

            if(StringUtils.isNotBlank(filter.getSampleRef())){
                itemCheck.append("left join tris.sample wsis ");
            }
            itemCheck.append(" where wsi.worksheet.id = ws.id and ");


            if((filter.getWorksheetItemStatuses()!= null && !filter.getWorksheetItemStatuses().isEmpty())){
                itemCheck.append("wsi.status in :wsiss and ");
                parameterWithList.put("wsiss", filter.getWorksheetItemStatuses());
            }

            if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())){
                itemCheck.append("trio.concept.conceptId in :trioc and trio.voided = 0  and ");
                parameterWithList.put("trioc", filter.getTestConceptIds());
            }

            if(filter.getUrgency() != null){
                appendFilter(hqlFilter, "trio.urgency = :urgency and ");
                parameterList.put("urgency", filter.getUrgency());
            }

            if(filter.getPatientId() != null){
                itemCheck.append("trio.patient.patientId = :pid and ");
                parameterList.put("pid", filter.getPatientId());
            }

            if(StringUtils.isNotBlank(filter.getSampleRef())){
                itemCheck.append("(wsis.accessionNumber = :srf or wsis.externalRef = :srf) and ");
                parameterList.put("srf", filter.getSampleRef());
            }

            itemCheck.append("wsi.voided = 0 ");
            itemCheck.append(")");
            appendFilter(hqlFilter, itemCheck.toString());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "ws.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = filter.getSearchText().toLowerCase() + "%";
            String qLast = "%" + filter.getSearchText().toLowerCase() ;
            appendFilter(textSearch, "lower(ws.worksheetNo) like :qtxt or lower(ws.worksheetNo) like :qtxtl");
            parameterList.putIfAbsent("qtxt", q);
            parameterList.putIfAbsent("qtxtl", qLast);
            appendFilter(hqlFilter, textSearch.toString());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<WorksheetDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1sws.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "datecreated":
                        orderByBuilder.append(String.format("%1sws.dateCreated %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by ws.id asc");
        }

        result.setData(executeQuery(WorksheetDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p -> Arrays.asList(p.getTestId(), p.getDiagnosisTypeId())
                    ).flatMap(Collection::stream)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());

            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));

            for (WorksheetDTO worksheetDTO : result.getData()) {
                worksheetDTO.setTestName(getConceptName(conceptNameDTOs,worksheetDTO.getTestId(),ConceptNameType.FULLY_SPECIFIED));
                worksheetDTO.setTestShortName(getConceptName(conceptNameDTOs,worksheetDTO.getTestId(),ConceptNameType.SHORT));

                worksheetDTO.setDiagnosisTypeName(getConceptName(conceptNameDTOs,worksheetDTO.getDiagnosisTypeId(),ConceptNameType.FULLY_SPECIFIED));
            }
        }
        return result;
    }

    public Result<WorksheetItemDTO> findWorksheetItems(WorksheetItemSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();
        StringBuilder hqlQuery = new StringBuilder("select wsi.uuid as uuid, wsi.id as id, wsi.worksheet.id as worksheetId,\n" +
                "s.uuid as sampleUuid,\n" +
                "s.accessionNumber as sampleAccessionNumber,\n" +
                "s.externalRef as sampleExternalRef,\n" +
                "s.providedRef as sampleProvidedRef,\n" +
                "wsi.status as status,\n" +
                "wsi.completedDate as completedDate,\n" +
                "wsi.cancelledDate as cancelledDate,\n" +
                "wsi.cancellationRemarks as cancellationRemarks,\n" +
                "c.uuid as testUuid,\n" +
                "trise.uuid as testRequestItemSampleUuid,\n" +
                "tri.uuid as testRequestItemUuid,\n" +
                "c.conceptId as testId,\n" +
                "wsi.creator.userId as creator,\n" +
                "wsi.creator.uuid as creatorUuid,\n" +
                "wsi.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "wsi.dateChanged as dateChanged,\n" +
                "st.conceptId as sampleTypeId,\n" +
                "st.uuid as sampleTypeUuid,\n" +
                "ct.conceptId as sampleContainerTypeId,\n" +
                "ct.uuid as sampleContainerTypeUuid,\n" +
                "clb.userId as sampleCollectedBy,\n" +
                "clb.uuid as sampleCollectedByUuid,\n" +
                "s.collectionDate as sampleCollectionDate,\n" +
                "s.containerCount as sampleContainerCount,\n" +
                "s.volume as sampleVolume,\n" +
                "vu.conceptId as sampleVolumeUnitId,\n" +
                "vu.uuid as sampleVolumeUnitUuid,\n" +
                "p.patientId as patientId,\n" +
                "p.uuid as patientUuid,\n" +
                "rff.uuid as referralFromFacilityUuid,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +
                "trio.urgency as urgency,\n" +
                "tl.uuid as toLocationUuid,\n" +
                "tl.name as toLocationName," +
                "trio.uuid as orderUuid," +
                (filter.getIncludeTestResultId() ? "(select max(uuid) from labmanagement.TestResult ltr1 where ltr1.worksheetItem.id = wsi.id and ltr1.voided = 0 ) as testResultUuid," : "") +
                "wsi.voided as voided \n" +
                "from labmanagement.WorksheetItem wsi left join\n" +
                " wsi.testRequestItemSample trise left join\n" +
                " trise.sample s left join\n" +
                " trise.testRequestItem tri left join\n" +
                " tri.testRequest tr left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " tri.order trio left join\n" +
                " trio.concept c left join\n" +
                " s.sampleType st left join\n" +
                " s.containerType ct left join\n" +
                " s.volumeUnit vu left join\n" +
                " s.collectedBy clb left join\n" +
                " trio.patient p left join\n" +
                " tri.toLocation tl left join\n" +
                " wsi.changedBy cb\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getWorksheetItemUuid())) {
            appendFilter(hqlFilter, "wsi.uuid = :uuid");
            parameterList.put("uuid", filter.getWorksheetItemUuid());
        }

        if (filter.getWorksheetItemId() != null) {
            appendFilter(hqlFilter, "wsi.id = :id");
            parameterList.put("id", filter.getWorksheetItemId());
        }

        if(filter.getWorksheetIds() != null && !filter.getWorksheetIds().isEmpty()){
            appendFilter(hqlFilter, "wsi.worksheet.id in :wids");
            parameterWithList.put("wids", filter.getWorksheetIds());
        }

        if((filter.getWorksheetItemStatuses()!= null && !filter.getWorksheetItemStatuses().isEmpty())){
            appendFilter(hqlFilter, "wsi.status in :wsiss");
            parameterWithList.put("wsiss", filter.getWorksheetItemStatuses());
        }

        if(filter.getItemLocationId() != null){
            appendFilter(hqlFilter, "tri.toLocation.id = :alid");
            parameterList.put("alid", filter.getItemLocationId());
        }

        if((filter.getTestConceptIds()!= null && !filter.getTestConceptIds().isEmpty())){
            appendFilter(hqlFilter, "c.conceptId in :trioc");
            parameterWithList.put("trioc", filter.getTestConceptIds());
        }

        if(filter.getUrgency() != null){
            appendFilter(hqlFilter, "trio.urgency = :urgency");
            parameterList.put("urgency", filter.getUrgency());
        }

        if(filter.getPatientId() != null){
            appendFilter(hqlFilter,"trio.patient.patientId = :pid");
            parameterList.put("pid", filter.getPatientId());
        }

        if(StringUtils.isNotBlank(filter.getSampleRef())){
            appendFilter(hqlFilter,"(wsis.accessionNumber = :srf or wsis.externalRef = :srf)");
            parameterList.put("srf", filter.getSampleRef());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "wsi.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<WorksheetItemDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1swsi.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "datecreated":
                        orderByBuilder.append(String.format("%1swsi.dateCreated %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by wsi.id asc");
        }

        result.setData(executeQuery(WorksheetItemDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p -> Arrays.asList(p.getTestId(),p.getSampleTypeId(), p.getSampleContainerTypeId(),
                            p.getSampleVolumeUnitId(),
                            p.getReferralFromFacilityId() != null && StringUtils.isBlank(p.getReferralFromFacilityName()) ? p.getReferralFromFacilityId() : null)
                    ).flatMap(Collection::stream)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());

            List<Integer> patientIds = result.getData().stream().map(WorksheetItemDTO::getPatientId).filter(Objects::nonNull).collect(Collectors.toList());
            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));
            Map<Integer,List<UserPersonNameDTO>> patientNames = getPatientNameByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()),false).stream().collect(Collectors.groupingBy(p -> p.getPatientId()));

            List<String> patientIdentifierTypeIds = new ArrayList<>();
            String patientIdentifierSetting = GlobalProperties.getPatientIdentifierTypes();
            Map<Integer, List<PatientIdentifierDTO>> patientIdentifiers = null;
            if(StringUtils.isNotBlank(patientIdentifierSetting)) {
                patientIdentifierTypeIds = Arrays.stream(patientIdentifierSetting.split(",")).map(p->p.trim()).collect(Collectors.toList());
                patientIdentifiers = getPatientIdentifiersByPatientIds(patientIds.stream().distinct().collect(Collectors.toList()), patientIdentifierTypeIds).stream().collect(Collectors.groupingBy(PatientIdentifierDTO::getPatientId));
            }


            for (WorksheetItemDTO worksheetItemDTO : result.getData()) {
                worksheetItemDTO.setTestName(getConceptName(conceptNameDTOs,worksheetItemDTO.getTestId(),ConceptNameType.FULLY_SPECIFIED));
                worksheetItemDTO.setTestShortName(getConceptName(conceptNameDTOs,worksheetItemDTO.getTestId(),ConceptNameType.SHORT));

                if (worksheetItemDTO.getPatientId() != null) {
                    List<UserPersonNameDTO> userPersonNameDTO = patientNames.get(worksheetItemDTO.getPatientId());
                    if (userPersonNameDTO != null) {
                        worksheetItemDTO.setPatientFamilyName(userPersonNameDTO.get(0).getFamilyName());
                        worksheetItemDTO.setPatientMiddleName(userPersonNameDTO.get(0).getMiddleName());
                        worksheetItemDTO.setPatientGivenName(userPersonNameDTO.get(0).getGivenName());
                    }
                }
                if (patientIdentifiers != null && !patientIdentifiers.isEmpty()) {
                    List<PatientIdentifierDTO> identifiers = patientIdentifiers.get(worksheetItemDTO.getPatientId());
                    if (identifiers != null && !identifiers.isEmpty()) {
                        for (String patientIdentifier : patientIdentifierTypeIds) {
                            Optional<PatientIdentifierDTO> identifier = identifiers.stream()
                                    .filter(p -> patientIdentifier.equals(p.getIdentifierTypeUuid())).findFirst();
                            if (identifier.isPresent()) {
                                worksheetItemDTO.setPatientIdentifier(identifier.get().getIdentifier());
                                break;
                            }
                        }
                    }
                }

                worksheetItemDTO.setSampleTypeName(getConceptName(conceptNameDTOs,worksheetItemDTO.getSampleTypeId(),ConceptNameType.FULLY_SPECIFIED));
                worksheetItemDTO.setSampleContainerTypeName(getConceptName(conceptNameDTOs,worksheetItemDTO.getSampleContainerTypeId(),ConceptNameType.FULLY_SPECIFIED));
                worksheetItemDTO.setSampleVolumeUnitName(getConceptName(conceptNameDTOs,worksheetItemDTO.getSampleVolumeUnitId(),ConceptNameType.FULLY_SPECIFIED));
                if (StringUtils.isBlank(worksheetItemDTO.getReferralFromFacilityName())) {
                    worksheetItemDTO.setReferralFromFacilityName(getConceptName(conceptNameDTOs,worksheetItemDTO.getReferralFromFacilityId(), ConceptNameType.FULLY_SPECIFIED));
                }
            }
        }
        return result;
    }


    public void deleteLocation(Integer locationId) {

        DbSession session = getSession();
        Query query = session.createQuery("DELETE FROM Location WHERE locationId = :p");
        query.setParameter("p", locationId);
        query.executeUpdate();
    }

    public void deleteLocationAttributes(List<Integer> locationAttributeIds) {

        DbSession session = getSession();
        Query query = session.createQuery("DELETE FROM LocationAttribute WHERE locationAttributeId in (:p)");
        query.setParameterList("p", locationAttributeIds);
        query.executeUpdate();
    }

    public void deleteStockManagementParty(Location location) {
        try {
            DbSession session = getSession();
            Query query = session.createQuery("DELETE FROM stockmanagement.Party p WHERE p.location = :id");
            query.setParameter("id", location);
            query.executeUpdate();
        } catch (Exception exception) {
            LogFactory.getLog(this.getClass()).error(exception);
        }
    }

    public void deleteLocationTreeNodes(Integer locationId) {
        try {
            DbSession session = getSession();
            Query query = session
                    .createQuery("DELETE FROM stockmanagement.LocationTree WHERE parentLocationId = :p or childLocationId = :p");
            query.setParameter("p", locationId);
            query.executeUpdate();
        } catch (Exception exception) {
            LogFactory.getLog(this.getClass()).error(exception);
        }
    }

    public Result<TestResultDTO> findTestResults(TestResultSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();

        StringBuilder hqlQuery = new StringBuilder("select trt.uuid as uuid, trt.id as id, \n" +
                "wsi.id as worksheetItemId,\n" +
                "wsi.uuid as worksheetItemUuid,\n" +
                "ws.worksheetNo as worksheetNo,\n" +
                "ws.uuid as worksheetUuid,\n" +
                "s.id as sampleId,\n" +
                "s.uuid as sampleUuid,\n" +
                "s.accessionNumber as sampleAccessionNumber,\n" +
                "s.externalRef as sampleExternalRef,\n" +
                "s.providedRef as sampleProvidedRef,\n" +
                "o.orderId as orderId,\n" +
                "o.uuid as orderUuid,\n" +
                "o.orderNumber as orderNumber,\n" +
                "oc.conceptId as orderConceptId,\n" +
                "oc.uuid as testUuid,\n" +
                "tri.id as testRequestItemId,\n" +
                "tri.uuid as testRequestItemUuid,\n" +
                "tris.uuid as testRequestItemSampleUuid,\n" +
                "trt.obs.id as obsId," +
                "rb.userId as resultBy,\n" +
                "rb.uuid as resultByUuid,\n" +
                "trt.status as status,\n" +
                "trt.resultDate as resultDate,\n" +
                "trt.requireApproval as requireApproval,\n" +
                "ca.id as currentApprovalId,\n" +
                "ca.uuid as currentApprovalUuid,\n" +
                "trt.additionalTestsRequired as additionalTestsRequired,\n" +
                "trt.archiveSample as archiveSample,\n" +
                "trt.remarks as remarks,\n" +
                "trt.completedDate as completedDate,\n" +
                "trt.completed as completed,\n" +
                "trt.completedResult as completedResult,\n" +
                "trt.creator.userId as creator,\n" +
                "trt.creator.uuid as creatorUuid,\n" +
                "trt.dateCreated as dateCreated,\n" +
                "cb.userId as changedBy,\n" +
                "cb.uuid as changedByUuid,\n" +
                "trt.dateChanged as dateChanged,\n" +
                "ca.currentApprovalLevel as currentApprovalLevel,\n" +
                "ac.privilege as approvalPrivilege,\n" +
                "af.levelOneAllowOwner as approvalFlowLevelOneAllowOwner,\n" +
                "af.levelTwoAllowOwner as approvalFlowLevelTwoAllowOwner,\n" +
                "af.levelThreeAllowOwner as approvalFlowLevelThreeAllowOwner,\n" +
                "af.levelFourAllowOwner as approvalFlowLevelFourAllowOwner,\n" +
                "af.levelTwoAllowPrevious as approvalFlowLevelTwoAllowPrevious,\n" +
                "af.levelThreeAllowPrevious as approvalFlowLevelThreeAllowPrevious,\n" +
                "af.levelFourAllowPrevious as approvalFlowLevelFourAllowPrevious,\n" +

                "trt.voided as voided \n" +
                "from labmanagement.TestResult trt left join\n" +
                " trt.worksheetItem wsi left join\n" +
                " wsi.worksheet ws left join\n" +
                " trt.order o left join\n" +
                " trt.testRequestItemSample tris left join\n" +
                " tris.sample s left join\n" +
                " tris.testRequestItem tri left join\n" +
                " trt.resultBy rb left join\n" +
                " trt.currentApproval ca left join\n" +
                " ca.approvalConfig ac left join\n" +
                " ca.approvalFlow af left join\n" +
                " tri.changedBy cb left join\n" +
                " o.concept oc\n"
                );

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getTestResultUuid())) {
            appendFilter(hqlFilter, "trt.uuid = :uuid");
            parameterList.put("uuid", filter.getTestResultUuid());
        }

        if (filter.getTestResultId() != null) {
            appendFilter(hqlFilter, "trt.id = :id");
            parameterList.put("id", filter.getTestResultId());
        }

        if(filter.getWorksheetItemIds() != null && !filter.getWorksheetItemIds().isEmpty()){
            appendFilter(hqlFilter, "wsi.id in :wsid");
            parameterWithList.put("wsid", filter.getWorksheetItemIds());
        }

        if(filter.getTestRequestItemIds() != null && !filter.getTestRequestItemIds().isEmpty()){
            appendFilter(hqlFilter, "tri.id in :trid");
            parameterWithList.put("trid", filter.getTestRequestItemIds());
        }

        if(filter.getRequireApproval()!= null){
            appendFilter(hqlFilter, "trt.requireApproval = :trra");
            parameterList.put("trra", filter.getRequireApproval());
        }

        if(filter.getCompleted()!= null){
            appendFilter(hqlFilter, "trt.completed = :trcp");
            parameterList.put("trcp", filter.getCompleted());
        }

        if(filter.getCompletedResult()!= null){
            appendFilter(hqlFilter, "trt.completedResult = :trcr");
            parameterList.put("trcr", filter.getCompletedResult());
        }

        if(filter.getPatientId()!= null){
            appendFilter(hqlFilter, "o.patient.patientId = :ptid");
            parameterList.put("ptid", filter.getPatientId());
        }

        if(filter.getVoided() != null){
            appendFilter(hqlFilter, "trt.voided = :voided");
            parameterList.put("voided", filter.getVoided());
        }

        if(!StringUtils.isBlank(filter.getSearchText())){
            StringBuilder textSearch=new StringBuilder();
            String q = filter.getSearchText().toLowerCase() + "%";
            String qLast = "%" + filter.getSearchText().toLowerCase();
            appendFilter(textSearch, "lower(o.orderNumber) like :qtxt or lower(o.orderNumber) like :qtxtl");
            appendORFilter(textSearch, "exists ( from ConceptName cn where cn.concept.id = oc.conceptId and lower(cn.name) like :qtxt and cn.voided = 0 )");
            parameterList.putIfAbsent("qtxt", q);
            parameterList.putIfAbsent("qtxtl", qLast);
            appendFilter(hqlFilter, textSearch.toString());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestResultDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1strt.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                    case "status":
                        orderByBuilder.append(String.format("%1strt.status %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by trt.id asc");
        }

        result.setData(executeQuery(TestResultDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        if (!result.getData().isEmpty()) {
            List<Integer> conceptNamesToFetch = result.getData()
                    .stream()
                    .map(p-> Arrays.asList(p.getOrderConceptId()))
                    .flatMap(Collection::stream)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());

            Map<Integer, List<ConceptNameDTO>> conceptNameDTOs = getConceptNamesAndShortsByConceptIds(conceptNamesToFetch).stream().collect(Collectors.groupingBy(ConceptNameDTO::getConceptId));

            for (TestResultDTO testResultDTO : result.getData()) {
                testResultDTO.setTestName(getConceptName(conceptNameDTOs,testResultDTO.getOrderConceptId(), ConceptNameType.FULLY_SPECIFIED));
                testResultDTO.setTestShortName(getConceptName(conceptNameDTOs,testResultDTO.getOrderConceptId(), ConceptNameType.SHORT));
            }
        }
        return result;
    }

    public List<Obs> getObsByIds(List<Integer> observationIds) {
        if (observationIds == null || observationIds.isEmpty()) return new ArrayList<>();
        List<Obs> obsList = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = observationIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            obsList.addAll(getObsByIdsInternal(batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return obsList;
    }

    private List<Obs> getObsByIdsInternal(List<Integer> observationIds){
        Criteria criteria = getSession().createCriteria(Obs.class).add(Restrictions.in("obsId", observationIds));
        criteria = criteria.add(Restrictions.eq("voided", false));
        return (List<Obs>) criteria.list();
    }

    public List<Concept> getConceptsByIds(List<Integer> conceptIds) {
        if (conceptIds == null || conceptIds.isEmpty()) return new ArrayList<>();
        List<Concept> conceptList = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = conceptIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            conceptList.addAll(getConceptsByIdsInternal(batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return conceptList;
    }

    private List<Concept> getConceptsByIdsInternal(List<Integer> conceptIds){
        Criteria criteria = getSession().createCriteria(Concept.class).add(Restrictions.in("conceptId", conceptIds));
        return (List<Concept>) criteria.list();
    }

    public List<TestApprovalDTO> getPreviousTestApprovalRefs(List<Integer> testResultIds) {
        if (testResultIds == null || testResultIds.isEmpty()) return new ArrayList<>();
        List<TestApprovalDTO> testApprovalDTOS = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = testResultIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            testApprovalDTOS.addAll(getPreviousTestApprovalRefsInternal(batch));
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);
        return testApprovalDTOS;
    }

    public List<TestApprovalDTO> getPreviousTestApprovalRefsInternal(List<Integer> testResultIds) {
        if (testResultIds == null || testResultIds.isEmpty()) return new ArrayList<>();
        Query query = getSession().createQuery("select ta.id, ta.testResult.id as testResultId, ab.userId as approvedBy from labmanagement.TestApproval ta left join ta.approvedBy ab where ta.testResult.id in (:ids) and ta.voided=0")
                .setParameterList("ids", testResultIds);


        query = query.setResultTransformer(new AliasToBeanResultTransformer(TestApprovalDTO.class));
        ConceptNameType p;
        return query.list();
    }

    public List<TestApprovalDTO> getTestApprovals(List<Integer> testResultIds) {
        if (testResultIds == null || testResultIds.isEmpty()) return new ArrayList<>();
        List<TestApprovalDTO> testApprovalDTOS = new ArrayList<>();
        int startIndex = 0;
        boolean hasMoreRecordsToPull = true;
        do {
            List<Integer> batch = testResultIds.stream().skip(startIndex * 100).limit(100).collect(Collectors.toList());
            if(batch.isEmpty()){
                break;
            }
            TestApprovalSearchFilter testApprovalSearchFilter = new TestApprovalSearchFilter();
            testApprovalSearchFilter.setTestResultIds(batch);
            testApprovalDTOS.addAll(findTestApprovals(testApprovalSearchFilter).getData());
            hasMoreRecordsToPull = batch.size() >= 100;
            startIndex++;
        } while (hasMoreRecordsToPull);


        if(!testApprovalDTOS.isEmpty()) {
            List<UserPersonNameDTO> personNames = getPersonNameByUserIds(testApprovalDTOS.stream().map(p -> Arrays.asList(
                    p.getApprovedBy()
            )).flatMap(Collection::stream).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
            for (TestApprovalDTO testApprovalDTO : testApprovalDTOS) {
                if (testApprovalDTO.getApprovedBy() != null) {
                    Optional<UserPersonNameDTO> userPersonNameDTO = personNames.stream().filter(p -> p.getUserId().equals(testApprovalDTO.getApprovedBy())).findFirst();
                    if (userPersonNameDTO.isPresent()) {
                        testApprovalDTO.setApprovedByFamilyName(userPersonNameDTO.get().getFamilyName());
                        testApprovalDTO.setApprovedByMiddleName(userPersonNameDTO.get().getMiddleName());
                        testApprovalDTO.setApprovedByGivenName(userPersonNameDTO.get().getGivenName());
                    }
                }
            }
        }

        return testApprovalDTOS;
    }

    public Result<TestApprovalDTO> findTestApprovals(TestApprovalSearchFilter filter){
        HashMap<String, Object> parameterList = new HashMap<>();
        HashMap<String, Collection> parameterWithList = new HashMap<>();

        StringBuilder hqlQuery = new StringBuilder("select ta.uuid as uuid, ta.id as id, \n" +
                "ab.userId as approvedBy,\n" +
                "ab.uuid as approvedByUuid,\n" +
                "ta.testResult.id as testResultId,\n" +
                "ta.testResult.uuid as testResultUuid,\n" +
                "ac.approvalTitle as approvalTitle,\n" +
                "ta.approvalResult as approvalResult,\n" +
                "ta.remarks as remarks,\n" +
                "ta.activatedDate as activatedDate,\n" +
                "ta.approvalDate as approvalDate,\n" +
                "ta.currentApprovalLevel as currentApprovalLevel\n" +
                "from labmanagement.TestApproval ta left join\n" +
                " ta.approvedBy ab left join\n" +
                " ta.approvalConfig ac\n"
        );

        StringBuilder hqlFilter = new StringBuilder();
        if (!StringUtils.isBlank(filter.getTestApprovalUuid())) {
            appendFilter(hqlFilter, "ta.uuid = :uuid");
            parameterList.put("uuid", filter.getTestApprovalUuid());
        }

        if (filter.getTestApprovalId() != null) {
            appendFilter(hqlFilter, "ta.id = :id");
            parameterList.put("id", filter.getTestApprovalId());
        }

        if(filter.getTestResultIds() != null && !filter.getTestResultIds().isEmpty()){
            appendFilter(hqlFilter, "ta.testResult.id in :mids");
            parameterWithList.put("mids", filter.getTestResultIds());
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }

        Result<TestApprovalDTO> result = new Result<>();
        if (filter.getLimit() != null) {
            result.setPageIndex(filter.getStartIndex());
            result.setPageSize(filter.getLimit());
        }

        StringBuilder orderByBuilder = new StringBuilder();
        if(filter.getSortOrders() != null && !filter.getSortOrders().isEmpty()){
            for(SortField sortField : filter.getSortOrders()){
                if(sortField.getField() == null) continue;
                switch (sortField.getField().toLowerCase()){
                    case "id":
                        orderByBuilder.append(String.format("%1sta.id %2s ",
                                orderByBuilder.length() > 0 ? ", " : "",
                                sortField.isAscending() ? "asc" : "desc"));
                        break;
                }
            }
            if(orderByBuilder.length() > 0){
                orderByBuilder.insert(0, " order by ");
            }
        }
        if(orderByBuilder.length() == 0){
            orderByBuilder.append(" order by ta.id desc");
        }

        result.setData(executeQuery(TestApprovalDTO.class, hqlQuery, result, orderByBuilder.toString(), parameterList, parameterWithList));

        return result;
    }
}
