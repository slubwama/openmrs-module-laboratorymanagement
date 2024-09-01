package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;

public class ConceptReportParameter extends ReportParameterValue<String> {
    private Concept concept;

    public Integer getConceptId() {
        return concept == null ? null : concept.getConceptId();
    }

    @Override
    public boolean isValueSet() {
        return getConceptId() != null;
    }

    @Override
    public String parseValue(Object value) {
        String conceptUuid = StringReportParameter.parse(value);
        if(StringUtils.isBlank(conceptUuid)){
            return conceptUuid;
        }
        Concept valueConcept = Context.getConceptService().getConceptByUuid(conceptUuid);
        if(valueConcept == null){
            throw new LabManagementException("Concept with uuid not found");
        }
        concept = valueConcept;
        return concept.getUuid();
    }

    @Override
    public Object getMapValue() {
        return getValue();
    }
}
