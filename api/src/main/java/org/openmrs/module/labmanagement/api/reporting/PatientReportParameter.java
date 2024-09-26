package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;

public class PatientReportParameter extends ReportParameterValue<String> {
    private Patient patient;

    public Integer getPatientId() {
        return patient == null ? null : patient.getId();
    }

    @Override
    public Object getMapValue() {
        return getValue();
    }

    @Override
    public String parseValue(Object value) {
        String patientUuuid = StringReportParameter.parse(value);
        if(StringUtils.isBlank(patientUuuid)){
            return patientUuuid;
        }
        Patient patientEntity = Context.getPatientService().getPatientByUuid(patientUuuid);
        if(patientEntity == null){
            throw new LabManagementException("Patient with uuid not found");
        }
        patient = patientEntity;
        return patientEntity.getUuid();
    }

    @Override
    public boolean isValueSet() {
        return getPatientId() != null;
    }
}
