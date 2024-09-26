package org.openmrs.module.labmanagement.api.reporting;

import org.openmrs.module.labmanagement.api.LabManagementException;

public class BooleanReportParameter extends ReportParameterValue<Boolean> {

    @Override
    public Object getMapValue() {
        if(getValue() == null) return null;
        return getValue();
    }

    @Override
    public Boolean parseValue(Object value) {
        return parse(value);
    }

    @Override
    public boolean isValueSet() {
        return getValue() != null;
    }

    public static Boolean parse(Object value) {
        if(value == null) return null;
        if(value instanceof String){
            String boolValue = (String)value;
            if(boolValue.equalsIgnoreCase("true")) return true;
            if(boolValue.equalsIgnoreCase("false")) return false;
            throw new LabManagementException("Error converting date to boolean");
        }

        if (Boolean.class.isAssignableFrom(value.getClass())) {
            return (Boolean) value;
        }
        throw new LabManagementException("Don't know how to convert from " + value.getClass() + " to " + Boolean.class);
    }
}
