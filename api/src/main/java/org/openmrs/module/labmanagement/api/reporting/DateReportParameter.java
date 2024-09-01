package org.openmrs.module.labmanagement.api.reporting;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openmrs.module.labmanagement.api.LabManagementException;
import org.openmrs.module.labmanagement.api.utils.DateUtil;

import java.util.Date;

public class DateReportParameter extends ReportParameterValue<Date> {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Override
    public Object getMapValue() {
        if(getValue() == null) return null;
        return DateUtil.formatDateForJson(getValue());
    }

    @Override
    public Date parseValue(Object value) {
        return parse(value);
    }

    @Override
    public boolean isValueSet() {
        return getValue() != null;
    }

    public static Date parse(Object value) {
        if(value == null) return null;
        if(value instanceof String){
            String[] supportedFormats = { DATE_FORMAT, "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ssZ",
                    "yyyy-MM-dd'T'HH:mm:ssXXX", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
            IllegalArgumentException pex = null;
            for (int i = 0; i < supportedFormats.length; i++) {
                try {
                    return DateTime.parse((String)value, DateTimeFormat.forPattern(supportedFormats[i])).toDate();
                }
                catch (IllegalArgumentException ex) {
                    pex = ex;
                }
            }
            throw new LabManagementException("Error converting date - correct format (ISO8601 Long): yyyy-MM-dd'T'HH:mm:ss.SSSZ", pex);
        }

        if (Date.class.isAssignableFrom(value.getClass())) {
            return (Date)value;
        }
        throw new LabManagementException("Don't know how to convert from " + value.getClass() + " to " + Date.class);
    }
}
