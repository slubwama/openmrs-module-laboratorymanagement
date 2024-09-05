package org.openmrs.module.labmanagement.api.reporting.impl;

import org.openmrs.Concept;
import org.openmrs.module.labmanagement.api.dto.ObsDto;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItem;
import org.openmrs.module.labmanagement.api.dto.TestRequestReportItemFilter;
import org.openmrs.module.labmanagement.api.reporting.GenericObject;
import org.openmrs.module.labmanagement.api.utils.csv.CSVWriter;

import java.util.*;
import java.util.stream.Collectors;

public class PatientDiagonisticHistoryReport extends TestRegisterReport {

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
                row.getStatus() == null ? null : row.getStatus().toString(),
                row.getResultStatus(),
                formatName(row.getResultByFamilyName(), row.getResultByMiddleName(), row.getResultByGivenName()),
                row.getResultDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getResultDate()),
                row.getResultRemarks(),
                row.getCompleted() != null && row.getCompleted() ? "Yes" : "No",
                row.getCompletedDate() == null ? null : TIMESTAMP_FORMATTER.format(row.getCompletedDate())
                ));

        if(testResultConceptIds == null){
            columnValues.add(formatResult(observations.getOrDefault(row.getOrderId(), null)));
        }

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
                "Request Status",
                "Results Status",
                "Results By",
                "Results Date",
                "Results Remarks",
                "Complete",
                "Completed Date"));
        if(testConcept == null){
            headers.add("Result");
        }
        if(testConcept != null) {
            testResultConceptIds = new HashMap<>();
            parseResultColumns(testConcept, 3);
            headers.addAll(testResultConceptIds.values());
        }


        writeLineToCsv(csvWriter, headers.toArray(new String[0]));
    }
}
