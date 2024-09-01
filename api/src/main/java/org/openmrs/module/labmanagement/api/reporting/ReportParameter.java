package org.openmrs.module.labmanagement.api.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

public enum ReportParameter {
	Date(DateReportParameter.class, true, false, false, false, false, false, false, false, false),
	StartDate(DateReportParameter.class,true, false, false, false, false, false, false, false, false),
	EndDate(DateReportParameter.class,true, false, false, false, false, false, false, false, false),
	Location(LocationReportParameter.class, false, true, false, false , false, false, false, false, false),
	Patient(PatientReportParameter.class,false, false, false, true, false, false, false, false, false),
	ReferralLocation(ReferralLocationReportParameter.class,false, false, false, false , true, false, false, false, true),
	Limit(IntegerReportParameter.class, false, false, false, false , false, false, false, false, false),
	DiagnosticLocation(LocationReportParameter.class,false, false, false, false , true, false, false, false, false),
	TestType(ConceptReportParameter.class, false, false, false, false , false, true, false, false, false),
	TestOutcome(ObsReportParameter.class, false, false, false, false , false, false, true, false, false),
	TestApprover(UserReportParameter.class, false, false, false, false , false, false, false, true, false);


	private final boolean isDate;
	private final boolean isLocation;
	private final boolean isDiagnosticLocation;
	private final boolean isBoolean;
	private final boolean isPatient;
	private final boolean isTestType;
	private final boolean isTestOutcome;
	private final boolean isTestApprover;
	private final boolean isReferralLocation;
	private final Class parameterParserClass;


	ReportParameter(Class parameterParserClass, boolean isDate, boolean isLocation, boolean isBoolean,
					boolean isPatient, boolean isDiagnosticLocation, boolean isTestType, boolean isTestOutcome, boolean isTestApprover, boolean isReferralLocation) {
		this.isDate = isDate;
		this.isLocation = isLocation;
		this.isBoolean = isBoolean;
		this.isPatient = isPatient;
		this.isDiagnosticLocation = isDiagnosticLocation;
		this.isTestType = isTestType;
		this.isTestOutcome = isTestOutcome;
		this.isTestApprover = isTestApprover;
		this.parameterParserClass = parameterParserClass;
		this.isReferralLocation = isReferralLocation;

	}

	public static ReportParameter findByName(String name) {
		return findInList(name, values());
	}

	public static ReportParameter findInList(String name, ReportParameter[] parameterList) {
		ReportParameter result = null;
		for (ReportParameter enumValue : parameterList) {
			if (enumValue.name().equalsIgnoreCase(name)) {
				result = enumValue;
				break;
			}
		}
		return result;
	}


	public static ParameterRule findInList(String name, ParameterRule[] parameterList) {
		ParameterRule result = null;
		for (ParameterRule enumValue : parameterList) {
			if (enumValue.getReportParameter().name().equalsIgnoreCase(name)) {
				result = enumValue;
				break;
			}
		}
		return result;
	}

	public boolean isDate() {
		return isDate;
	}

	public boolean isLocation() {
		return isLocation;
	}

	public boolean isBoolean() {
		return isBoolean;
	}

	public boolean isPatient() {
		return isPatient;
	}

	public boolean isDiagnosticLocation() {
		return isDiagnosticLocation;
	}

	public boolean isTestType() {
		return isTestType;
	}

	public boolean isTestOutcome() {
		return isTestOutcome;
	}

	public boolean isTestApprover() {
		return isTestApprover;
	}

	public boolean isReferralLocation() {
		return isReferralLocation;
	}

	public Class getParameterParserClass() {
		return parameterParserClass;
	}

	public static GenericObject parseParameters(String parameters) throws JsonProcessingException {
		if(StringUtils.isBlank(parameters)) {
			return new GenericObject();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(parameters, GenericObject.class);
	}
}
