package org.openmrs.module.labmanagement.api.reporting.impl;

import org.openmrs.Concept;
import org.openmrs.module.labmanagement.api.dto.ObsDto;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItem;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.util.*;
import java.util.stream.Collectors;

public class DailyTestRegisterReport extends TestRegisterReport {

    protected Map<Integer, String> testResultConceptIds = null;

    protected void parseResultColumns(Concept concept, int maxDepth) {
        if(concept == null) {
            return ;
        }
        if(concept.isNumeric() ||concept.getDatatype() != null && concept.getDatatype().isCoded()
                || concept.getDatatype() != null && concept.getDatatype().isNumeric()
                || concept.getDatatype() != null && concept.getDatatype().isText()){
            testResultConceptIds.put(concept.getConceptId(), concept.getDisplayString());
        }
        if(maxDepth == 0){
            return;
        }
        if(concept.getSetMembers() != null && !concept.getSetMembers().isEmpty()){
            if(!concept.getSetMembers().isEmpty()){
                for(Concept setMember : concept.getSetMembers()){
                    parseResultColumns(setMember, maxDepth - 1);
                }
            }
        }
    }

    @Override
    protected void setFilters(TestRequestReportItemFilter filter, GenericObject parameters) {

    }

    @Override
    protected void writeRow(CSVWriter csvWriter, TestRequestReportItem row, Map<Integer, List<ObsDto>> observations) {
        List<String> columnValues = new ArrayList<>(Arrays.asList(TIMESTAMP_FORMATTER.format(row.getDateCreated()),
                row.getRequestDate() == null ? null : DATE_FORMATTER.format(row.getRequestDate()),
                row.getAtLocationName(),
                row.getRequestNo(),
                row.getOrderNumber(),
                row.getReferredIn() != null && row.getReferredIn() ? "Referral" : "Patient",
                row.getReferredIn() != null && row.getReferredIn() ? row.getReferralFromFacilityName() :
                        formatName(row.getPatientFamilyName(), row.getPatientMiddleName(), row.getPatientGivenName()),
                row.getReferredIn() != null && row.getReferredIn() ? row.getReferralInExternalRef() : row.getPatientIdentifier(),
                formatName(row.getProviderFamilyName(), row.getProviderMiddleName(), row.getProviderGivenName()),
                formatTestName(row.getTestName(), row.getTestShortName()),
                row.getRequireRequestApproval() != null && row.getRequireRequestApproval()  ? "Yes" : "No",
                formatName(row.getRequestApprovalFamilyName(), row.getRequestApprovalMiddleName(), row.getRequestApprovalGivenName()),
                row.getRequestApprovalDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getRequestApprovalDate()),
                row.getRequestApprovalRemarks(),
                row.getStatus() == null ? null : row.getStatus().toString(),
                row.getSampleTypeName(),
                row.getSampleAccessionNumber(),
                row.getSampleExternalRef(),
                row.getSampleProvidedRef(),
                row.getCollectionDate() == null ?  null : TIMESTAMP_FORMATTER.format(row.getCollectionDate()),
                formatName(row.getCollectedByFamilyName(), row.getCollectedByMiddleName(), row.getCollectedByGivenName()),
                row.getSampleAtLocationName(),
                row.getReferredOut() != null && row.getReferredOut() ? "Yes" : "No",
                row.getReferralToFacilityName(),
                row.getReferralOutDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getReferralOutDate()),
                row.getWorksheetNo(),
                row.getWorksheetAtLocationName() != null ? row.getWorksheetAtLocationName() : row.getResultAtLocationName() != null ? row.getResultAtLocationName() : row.getSampleAtLocationName() != null ? row.getSampleAtLocationName() : row.getToLocationName() ,
                row.getResultStatus(),
                formatName(row.getResultByFamilyName(), row.getResultByMiddleName(), row.getResultByGivenName()),
                row.getResultDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getResultDate()),
                row.getResultStatus(),
                row.getResultRequireApproval() != null &&  row.getResultRequireApproval() ? "Yes" : "No",
                formatName(row.getCurrentApprovalByFamilyName(), row.getCurrentApprovalByMiddleName(), row.getCurrentApprovalByGivenName()),
                row.getResultApprovalDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getResultApprovalDate()),
                row.getResultRemarks(),
                row.getCompleted() != null && row.getCompleted() ? "Yes" : "No",
                row.getCompletedDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getCompletedDate()),
                formatResult(observations.getOrDefault(row.getOrderId(), null))
                ));

        if(testResultConceptIds != null){
            List<ObsDto> rowObservations = observations.getOrDefault(row.getOrderId(), null);
            Map<Integer, List<ObsDto>> obsConcept;
            if(rowObservations != null){
                obsConcept = rowObservations.stream().collect(Collectors.groupingBy(ObsDto::getConceptId));
            } else {
                obsConcept = null;
            }
            columnValues.addAll(
            testResultConceptIds.keySet().stream().map(p->
            {
               if(obsConcept == null){
                   return null;
               }
               List<ObsDto> colDos =obsConcept.getOrDefault(p, null);
               return colDos == null || colDos.isEmpty() ? null : getObsDtoValue(colDos.get(0));
            }).collect(Collectors.toList()));
        }
        writeLineToCsv(csvWriter,columnValues.toArray(new String[0]));
    }

    @Override
    protected void writeHeaders(CSVWriter csvWriter) {

        List<String> headers = new ArrayList<>(Arrays.asList("Date Created",
                "Request Date",
                "Location",
                "Request Number",
                "Order Number",
                "Type",
                "Entity",
                "Identity",
                "Orderer",
                "Test",
                "Request Approval",
                "Request Approved By",
                "Request Approved Date",
                "Request Approval Remarks",
                "Request Status",
                "Sample Type",
                "Sample ID",
                "External Reference",
                "Provided Reference",
                "Collection Date",
                "Collected By",
                "Collected At",
                "Referred Out",
                "Reference Location",
                "Referred Date",
                "Worksheet",
                "Diagnostic Center",
                "Results Status",
                "Results By",
                "Results Date",
                "Results Status",
                "Result Approval",
                "Results Last Approved By",
                "Results Last Approved Date",
                "Results Remarks",
                "Complete",
                "Completed Date",
                "Result"));
        if(testConcept != null) {
            testResultConceptIds = new HashMap<>();
            parseResultColumns(testConcept, 3);
            headers.addAll(testResultConceptIds.values());
        }


        writeLineToCsv(csvWriter, headers.toArray(new String[0]));
    }
}
