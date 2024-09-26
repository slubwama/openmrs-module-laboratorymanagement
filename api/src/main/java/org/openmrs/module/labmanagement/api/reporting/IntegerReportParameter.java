package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;

public class IntegerReportParameter extends ReportParameterValue<Integer> {

    @Override
    public boolean isValueSet() {
        return getValue() != null;
    }

    @Override
    public Integer getMapValue() {
        return (Integer) getValue();
    }

    @Override
    public Integer parseValue(Object value) {
        return parse(value);
    }

    public static Integer parse(Object mapValue) {
        String value = StringReportParameter.parse(mapValue);
        if(StringUtils.isBlank(value)){
            return null;
        }else{
            return Integer.parseInt(value);
        }
    }
}
