package org.openmrs.module.labmanagement.api.dao;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class SampleReportItemQueryBuilder {
    public StringBuilder build(TestRequestReportItemFilter filter,  HashMap<String, Object> parameterList,
                               HashMap<String, Collection> parameterWithList, BiConsumer<StringBuilder,String> appendFilter){
        StringBuilder hqlQuery = new StringBuilder("select tri.id as testRequestItemId, \n" +
                "o.orderId as orderId,\n" +
                "o.orderNumber as orderNumber, tr.requestDate as requestDate, tr.requestNo as requestNo, tri.dateCreated as dateCreated, tri.creator.userId as creator,\n" +
                "oc.conceptId as orderConceptId,\n" +
                "tr.referredIn as referredIn,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +
                "p.patientId as patientId,\n" +
                "al.name as atLocationName,\n" +
                "tri.referredOut as referredOut,\n" +
                "tri.referralOutOrigin as referralOutOrigin,\n" +
                "rob.userId as referralOutBy,\n" +
                "tri.referralOutDate as referralOutDate,\n" +
                "rtf.concept.id as referralToFacilityId,\n" +
                "coalesce(tri.referralToFacilityName, rtf.name) as referralToFacilityName,\n" +
                "tri.requestApprovalResult as requestApprovalResult,\n" +
                "rab.userId as requestApprovalBy,\n" +
                "tri.requestApprovalDate as requestApprovalDate,\n" +
                "st.conceptId as sampleTypeId,\n" +
                "sal.name as sampleAtLocationName,\n" +
                "clb.userId as collectedBy,\n" +
                "s.collectionDate as collectionDate,\n" +
                "s.accessionNumber as sampleAccessionNumber,\n" +
                "s.providedRef as sampleProvidedRef,\n" +
                "s.externalRef as sampleExternalRef,\n" +
                "wal.name as worksheetAtLocationName,\n" +
                "rb.userId as resultBy,\n" +
                "fr.resultDate as resultDate,\n" +
                "frca.approvalDate as resultApprovalDate,\n" +
                "fral.name as resultAtLocationName,\n" +
                "frcaab.userId as currentApprovalBy\n" +
                "from labmanagement.TestRequestItem tri left join\n" +
                " tri.testRequest tr left join\n" +
                " tri.order o left join\n" +
                " tri.initialSample s left join\n" +
                " tri.finalResult fr left join\n" +
                " fr.currentApproval frca left join \n" +
                " tri.testRequestItemSamples tris left join" +
                " tris.worksheetItems wsi left join " +
                " wsi.worksheet ws left join " +
                " tri.atLocation al left join\n" +
                " fr.atLocation fral left join\n" +
                " tri.referralOutBy rob left join\n" +
                " tri.requestApprovalBy rab left join\n" +
                " o.concept oc left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " tr.patient p left join\n" +
                " tri.referralToFacility rtf left join\n" +
                " s.collectedBy clb left join\n" +
                " s.atLocation sal left join\n" +
                " s.sampleType st left join\n" +
                " ws.atLocation wal left join\n" +
                " fr.resultBy rb left join\n" +
                " frca.approvedBy frcaab\n");

        StringBuilder hqlFilter = new StringBuilder();
        if (filter.getTestRequestItemIdMin() !=null) {
            appendFilter.accept(hqlFilter, "tri.id > :triim");
            parameterList.put("triim", filter.getTestRequestItemIdMin());
        }

        if (filter.getTestRequestIdMin() !=null) {
            appendFilter.accept(hqlFilter, "tr.id >= :trim");
            parameterList.put("trim", filter.getTestRequestIdMin());
        }

        if (filter.getStartDate() != null) {
            appendFilter.accept(hqlFilter, "tri.dateCreated >= :trdcmn");
            parameterList.putIfAbsent("trdcmn", filter.getStartDate());
        }

        if (filter.getEndDate() != null) {
            appendFilter.accept(hqlFilter, "tri.dateCreated <= :trdcmx");
            parameterList.putIfAbsent("trdcmx", filter.getEndDate());
        }

        appendFilter.accept(hqlFilter, "s.id is not null");


        if(StringUtils.isNotBlank(filter.getReferenceNumber())){
            appendFilter.accept(hqlFilter, "s.accessionNumber = :sref or s.providedRef = :sref or s.externalRef = :sref");
            parameterList.put("sref", filter.getReferenceNumber());
        }

        if (filter.getPatientId() != null) {
            appendFilter.accept(hqlFilter, "p.patientId = :pid");
            parameterList.put("pid", filter.getPatientId());
        }

        if (filter.getTestConceptId() != null) {
            appendFilter.accept(hqlFilter, "oc.conceptId = :ocid");
            parameterList.put("ocid", filter.getTestConceptId());
        }

        if(filter.getReferralLocationId() != null) {
            appendFilter.accept(hqlFilter, "rff.id = :rfid");
            parameterList.put("rfid", filter.getReferralLocationId());
        }

        if(filter.getApproverUserId() != null) {
            appendFilter.accept(hqlFilter, "(fr.requireApproval = 1 and frcaab.userId = :apuid) or (fr.requireApproval = 0 and rb.userId = :apuid)");
            parameterList.put("apuid", filter.getApproverUserId());
        }

        if(filter.getTesterUserId() != null) {
            appendFilter.accept(hqlFilter, "(tri.creator.userId = :rbyuid or rab.userId = :rbyuid or clb.userId or fr.resultBy.userId = :rbyuid or frcaab.userId  = :rbyuid)");
            parameterList.put("rbyuid", filter.getTesterUserId());
        }

        if (filter.getDiagonisticLocationId() != null) {
            appendFilter.accept(hqlFilter, "(wsi.id is not null and wal.id = :wallid) or (wsi.id is null and fral.id = :wallid) or (wsi.id is null  and fral.id is null and sal.id = :wallid) or (s.id is null and tri.toLocation.id = :wallid)");
            parameterList.put("wallid", filter.getDiagonisticLocationId());
        }

        appendFilter.accept(hqlFilter, "tri.voided = 0");

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }
        return hqlQuery;
    }
}
