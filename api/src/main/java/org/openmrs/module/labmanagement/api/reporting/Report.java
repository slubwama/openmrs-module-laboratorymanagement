package org.openmrs.module.labmanagement.api.reporting;

import java.util.ArrayList;
import java.util.List;

public class Report<T extends ReportGenerator> {

	private String uuid;

	private int order;

	private String name;

	private String systemName;

	private ReportParameter[] parameters;

	private Class<T> reportGeneratorClass;

	public Report() {
	}

	public Report(String uuid, int order, String name, String systemName, ReportParameter[] parameters,
	    Class<T> reportGeneratorClass) {
		this.uuid = uuid;
		this.order = order;
		this.name = name;
		this.systemName = systemName;
		this.parameters = parameters;
		this.reportGeneratorClass = reportGeneratorClass;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public ReportParameter[] getParameters() {
		return parameters;
	}

	public void setParameters(ReportParameter[] parameters) {
		this.parameters = parameters;
	}

	public Class<T> getReportGeneratorClass() {
		return reportGeneratorClass;
	}

	public void setReportGeneratorClass(Class<T> reportGeneratorClass) {
		this.reportGeneratorClass = reportGeneratorClass;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public static List<Report> getAllReports(){
        List<Report> reports = new ArrayList<>();

        return reports;
    }
}
