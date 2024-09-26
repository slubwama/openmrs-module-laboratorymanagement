package org.openmrs.module.labmanagement.api.dao;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Obs;
import org.openmrs.module.labmanagement.api.LabManagementException;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;
import org.openmrs.module.labmanagement.api.reporting.ObsValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class TestRequestReportItemQueryBuilder {
    public StringBuilder build(TestRequestReportItemFilter filter,  HashMap<String, Object> parameterList,
                               HashMap<String, Collection> parameterWithList, BiConsumer<StringBuilder,String> appendFilter){
        StringBuilder hqlQuery = new StringBuilder("select tri.id as testRequestItemId, \n" +
                "o.orderId as orderId,\n" +
                "o.orderNumber as orderNumber, tr.requestDate as requestDate, tr.requestNo as requestNo,\n" +
                "oc.conceptId as orderConceptId,\n" +

                "tri.status as status,\n" +
                "cs.name as careSettingName,\n" +

                "tr.referredIn as referredIn,\n" +
                "rff.concept.id as referralFromFacilityId,\n" +
                "coalesce(tr.referralFromFacilityName, rff.name) as referralFromFacilityName,\n" +
                "tr.referralInExternalRef as referralInExternalRef,\n" +

                "p.patientId as patientId,\n" +
                "pv.person.id as providerId,\n" +
                "al.name as atLocationName,\n" +
                "toal.name as toLocationName,\n" +

                "tri.referredOut as referredOut,\n" +
                "tri.referralOutOrigin as referralOutOrigin,\n" +
                "rob.userId as referralOutBy,\n" +
                "tri.referralOutDate as referralOutDate,\n" +
                "rtf.concept.id as referralToFacilityId,\n" +
                "coalesce(tri.referralToFacilityName, rtf.name) as referralToFacilityName,\n" +
                "tri.requireRequestApproval as requireRequestApproval,\n" +
                "tri.requestApprovalResult as requestApprovalResult,\n" +
                "rab.userId as requestApprovalBy,\n" +
                "tri.requestApprovalDate as requestApprovalDate,\n" +
                "tri.requestApprovalRemarks as requestApprovalRemarks,\n" +
                "tri.completed as completed,\n" +
                "tri.completedDate as completedDate,\n" +
                "tri.creator.userId as creator,\n" +
                "tri.dateCreated as dateCreated,\n" +

                "st.conceptId as sampleTypeId,\n" +
                "sal.name as sampleAtLocationName,\n" +
                "ct.conceptId as sampleContainerTypeId,\n" +
                "clb.userId as collectedBy,\n" +
                "s.collectionDate as collectionDate,\n" +
                "s.containerCount as containerCount,\n" +
                "s.providedRef as sampleProvidedRef,\n" +
                "s.volume as volume,\n" +
                "vu.conceptId as volumeUnitId,\n" +
                "s.accessionNumber as sampleAccessionNumber,\n" +
                "s.externalRef as sampleExternalRef,\n" +

                "ws.worksheetNo as worksheetNo,\n" +
                "wal.name as worksheetAtLocationName,\n" +

                "fr.obs.id as obsId," +
                "rb.userId as resultBy,\n" +
                "fr.status as resultStatus,\n" +
                "fr.resultDate as resultDate,\n" +
                "fr.requireApproval as resultRequireApproval,\n" +
                "fr.remarks as resultRemarks,\n" +
                "frca.approvalDate as resultApprovalDate,\n" +
                "fr.completed as resultCompleted,\n" +
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
                " tri.toLocation toal left join\n" +
                " fr.atLocation fral left join\n" +
                " tri.referralOutBy rob left join\n" +
                " tri.requestApprovalBy rab left join\n" +
                " o.concept oc left join\n" +
                " tr.referralFromFacility rff left join\n" +
                " tr.careSetting cs left join\n" +
                " tr.patient p left join\n" +
                //" tri.referralOutSample ros left join\n" +
                " tri.referralToFacility rtf left join\n" +
                " s.collectedBy clb left join\n" +
                " s.atLocation sal left join\n" +
                " s.containerType ct left join\n" +
                " s.volumeUnit vu left join\n" +
                " s.sampleType st left join\n" +
                " ws.atLocation wal left join\n" +
                " tr.provider pv left join\n" +
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
            appendFilter.accept(hqlFilter, "(fr.resultBy.userId = :rbyuid)");
            parameterList.put("rbyuid", filter.getTesterUserId());
        }

        if (filter.getDiagonisticLocationId() != null) {
            appendFilter.accept(hqlFilter, "(wsi.id is not null and wal.id = :wallid) or (wsi.id is null and fral.id = :wallid) or (wsi.id is null  and fral.id is null and sal.id = :wallid) or (s.id is null and tri.toLocation.id = :wallid)");
            parameterList.put("wallid", filter.getDiagonisticLocationId());
        }

        appendFilter.accept(hqlFilter, "tri.voided = 0");

        if(filter.getObsValue() != null) {


            StringBuilder obsBuilder= new StringBuilder();
            applyObsValueFilter(obsBuilder, filter.getObsValue(), parameterList, appendFilter, 2,0);
            if(obsBuilder.length()>0) {
                appendFilter.accept(hqlFilter, "fr.id is not null");
                appendFilter.accept(hqlFilter, obsBuilder.toString());
            }
        }

        if (hqlFilter.length() > 0) {
            hqlQuery.append(" where ");
            hqlQuery.append(hqlFilter);
        }
        return hqlQuery;
    }


    private Integer applyObsValueFilter(StringBuilder obsBuilder, ObsValue obsValue,HashMap<String, Object> parameterList,
                                     BiConsumer<StringBuilder,String> appendFilter,
                                     int maxDepth, Integer paramIndex){
        if(maxDepth == 0) throw new LabManagementException("Depth of observation not supported");
        if(obsValue == null) return paramIndex;
        if(obsValue.isNumeric()) {

            if(obsValue.getMinValue() != null && obsValue.getMaxValue() != null) {
                appendFilter.accept(obsBuilder, "exists ( from Obs obs  where obs.order.id = tri.order.id and obs.concept.id = :obsCptId"+paramIndex.toString()+" and obs.valueNumeric >= :obsMin"+paramIndex.toString()+" and obs.valueNumeric <= :obsMax"+paramIndex.toString()+" and obs.voided=0 )");
                parameterList.put("obsMin"+paramIndex.toString(), obsValue.getMinValue().doubleValue());
                parameterList.put("obsMax"+paramIndex.toString(), obsValue.getMaxValue().doubleValue());
                parameterList.putIfAbsent("obsCptId"+paramIndex.toString(), obsValue.getConceptId());
            }else if(obsValue.getMinValue() != null) {
                appendFilter.accept(obsBuilder, "exists ( from Obs obs  where obs.order.id = tri.order.id and obs.concept.id = :obsCptId"+paramIndex.toString()+" and obs.valueNumeric >= :obsMin"+paramIndex.toString()+" and obs.voided=0 )");
                parameterList.put("obsMin"+paramIndex.toString(), obsValue.getMinValue().doubleValue());
                parameterList.putIfAbsent("obsCptId"+paramIndex.toString(), obsValue.getConceptId());
            } else if(obsValue.getMaxValue() != null) {
                appendFilter.accept(obsBuilder, "exists ( from Obs obs  where obs.order.id = tri.order.id and obs.concept.id = :obsCptId"+paramIndex.toString()+" and obs.valueNumeric <= :obsMax"+paramIndex.toString()+" and obs.voided=0 )");
                parameterList.put("obsMax"+paramIndex.toString(), obsValue.getMaxValue().doubleValue());
                parameterList.putIfAbsent("obsCptId"+paramIndex.toString(), obsValue.getConceptId());
            }
        }
        else if(obsValue.isText() && !StringUtils.isBlank(obsValue.getValueText())){
            appendFilter.accept(obsBuilder, "exists ( from Obs obs  where obs.order.id = tri.order.id and obs.concept.id = :obsCptId"+paramIndex.toString()+" and lower(obs.valueText) = lower(:obsText"+paramIndex.toString()+") and obs.voided=0 )");
            parameterList.put("obsText"+paramIndex.toString(), obsValue.getValueText());
            parameterList.putIfAbsent("obsCptId"+paramIndex.toString(), obsValue.getConceptId());
        }
        else if(obsValue.isCoded() && obsValue.getValueConceptId() != null){
            appendFilter.accept(obsBuilder, "exists ( from Obs obs  where obs.order.id = tri.order.id and obs.concept.id = :obsCptId"+paramIndex.toString()+" and obs.valueCoded.id = :obsCoded"+paramIndex.toString()+" and obs.voided=0 )");
            parameterList.putIfAbsent("obsCoded"+paramIndex.toString(), obsValue.getValueConceptId());
            parameterList.putIfAbsent("obsCptId"+paramIndex.toString(), obsValue.getConceptId());
        }

        if(obsValue.getGroupMembers() != null && !obsValue.getGroupMembers().isEmpty()) {
            paramIndex = paramIndex + 1;
            maxDepth = maxDepth - 1;
            for(ObsValue groupMember : obsValue.getGroupMembers()){
                paramIndex = applyObsValueFilter(obsBuilder, groupMember, parameterList, appendFilter, maxDepth, paramIndex) + 1;
            }
        }
        Obs obs;
        return paramIndex;
    }
}
