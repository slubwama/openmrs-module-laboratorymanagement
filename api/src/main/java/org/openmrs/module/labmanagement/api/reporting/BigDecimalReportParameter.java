package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class BigDecimalReportParameter extends ReportParameterValue<BigDecimal> {

    @Override
    public boolean isValueSet() {
        return getValue() != null;
    }

    @Override
    public BigDecimal getMapValue() {
        return (BigDecimal) getValue();
    }

    @Override
    public BigDecimal parseValue(Object value) {
        return parse(value);
    }

    public static BigDecimal parse(Object mapValue) {
        String value = StringReportParameter.parse(mapValue);
        if(StringUtils.isBlank(value)){
            return null;
        }else{
            return new BigDecimal(value);
        }
    }
}
