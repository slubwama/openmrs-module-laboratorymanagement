package org.openmrs.module.labmanagement.api.reporting;

public enum ReportParameter {
	Date(true, false, false, false), StartDate(true, false,
	        false, false), EndDate(true, false, false,
			false), Location(false, true, false, false
	), Patient(false, false, false,
			true), Limit(false, false, false, false
	);

	private boolean isDate;

	private boolean isLocation;

	private boolean isBoolean;

	private boolean isPatient;

	ReportParameter(boolean isDate, boolean isLocation, boolean isBoolean,
					boolean isPatient) {
		this.isDate = isDate;
		this.isLocation = isLocation;
		this.isBoolean = isBoolean;
		this.isPatient = isPatient;
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

}
