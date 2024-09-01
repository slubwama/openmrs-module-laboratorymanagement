package org.openmrs.module.labmanagement.api.reporting;

public class ParameterRule {
    private ReportParameter reportParameter;
    private boolean isRequired;

    public ParameterRule(ReportParameter reportParameter, boolean isRequired) {
        this.reportParameter = reportParameter;
        this.isRequired = isRequired;
    }

    public ReportParameter getReportParameter() {
        return reportParameter;
    }

    public void setReportParameter(ReportParameter reportParameter) {
        this.reportParameter = reportParameter;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
