package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;

public class StringReportParameter extends ReportParameterValue<String> {

    @Override
    public Object getMapValue() {
        return getValue();
    }

    @Override
    public String parseValue(Object value) {
        return parse(value);
    }

    @Override
    public boolean isValueSet() {
        return StringUtils.isNotBlank(getValue());
    }

    public static String parse(Object value) {
        if(value == null) return null;
        if(value instanceof String){
            return (String)value;
        }
        return value.toString();
    }
}
