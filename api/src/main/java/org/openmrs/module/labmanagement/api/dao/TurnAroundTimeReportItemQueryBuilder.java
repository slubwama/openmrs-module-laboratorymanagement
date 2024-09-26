package org.openmrs.module.labmanagement.api.dao;

import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class TurnAroundTimeReportItemQueryBuilder {
    public StringBuilder build(TestRequestReportItemFilter filter,  HashMap<String, Object> parameterList,
                               HashMap<String, Collection> parameterWithList, BiConsumer<StringBuilder,String> appendFilter){
        StringBuilder hqlQuery = new StringBuilder("select tri.id as testRequestItemId, tr.requestDate as requestDate, \n" +
                "o.orderNumber as orderNumber,  tr.requestNo as requestNo,\n" +
                "oc.conceptId as orderConceptId,\n" +
                "p.patientId as patientId,\n" +
                "tri.requestApprovalDate as requestApprovalDate,\n" +
                "tri.completedDate as completedDate,\n" +
                "tri.dateCreated as dateCreated,\n" +
                "rb.userId as resultBy,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +
                "s.collectionDate as collectionDate,\n" +
                "fr.resultDate as resultDate\n" +

                "from labmanagement.TestRequestItem tri left join\n" +
                " tri.testRequest tr left join\n" +
                " tri.order o left join\n" +
                " tri.initialSample s left join\n" +
                " tri.finalResult fr left join\n" +
                " tri.testRequestItemSamples tris left join" +
                " tris.worksheetItems wsi left join " +
                " wsi.worksheet ws left join " +
                " o.concept oc left join\n" +
                " tr.patient p left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " fr.resultBy rb left join ws.atLocation wal left join\n" +
                " fr.atLocation fral\n");

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
            appendFilter.accept(hqlFilter, "tr.dateCreated >= :trdcmn");
            parameterList.putIfAbsent("trdcmn", filter.getStartDate());
        }

        if (filter.getEndDate() != null) {
            appendFilter.accept(hqlFilter, "tr.dateCreated <= :trdcmx");
            parameterList.putIfAbsent("trdcmx", filter.getEndDate());
        }

        appendFilter.accept(hqlFilter, "s.id is not null");

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

        if(filter.getTesterUserId() != null) {
            appendFilter.accept(hqlFilter, "(fr.resultBy.userId = :rbyuid)");
            parameterList.put("rbyuid", filter.getTesterUserId());
        }

        if (filter.getDiagonisticLocationId() != null) {
            appendFilter.accept(hqlFilter, "(wsi.id is not null and wal.id = :wallid) or (wsi.id is null and fral.id = :wallid)");
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
