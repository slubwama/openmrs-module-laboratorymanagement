package org.openmrs.module.labmanagement.api.reporting;

import liquibase.pro.packaged.B;
import org.openmrs.module.labmanagement.api.jobs.AsyncTaskJob;
import org.openmrs.module.labmanagement.api.jobs.DataMigrationJob;
import org.openmrs.module.labmanagement.api.model.BatchJobType;
import org.openmrs.module.labmanagement.api.reporting.impl.*;

import java.util.ArrayList;
import java.util.List;

public class Report {

	private String uuid;

	private int order;

	private BatchJobType batchJobType;

	private String name;

	private String systemName;

	private ParameterRule[] parameters;

	private Class reportGeneratorClass;

	public Report() {
	}

	public Report(String uuid, int order, BatchJobType batchJobType, String name, String systemName, ParameterRule[] parameters,
	    Class reportGeneratorClass) {
		this.uuid = uuid;
		this.batchJobType = batchJobType;
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

	public BatchJobType getBatchJobType() {
		return batchJobType;
	}

	public void setBatchJobType(BatchJobType batchJobType) {
		this.batchJobType = batchJobType;
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

	public ParameterRule[] getParameters() {
		return parameters;
	}

	public void setParameters(ParameterRule[] parameters) {
		this.parameters = parameters;
	}

	public Class getReportGeneratorClass() {
		return reportGeneratorClass;
	}

	public void setReportGeneratorClass(Class reportGeneratorClass) {
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
		reports.add(new Report("2ca9be51-6770-11ef-b6d8-00155d50337b", 1000, BatchJobType.Migration,"Data Migration","DATA_MIGRATION_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate)}, DataMigrationJob.class));

		reports.add(new Report("2ca9be51-6771-11ef-b6d8-00155d50337b", 10, BatchJobType.Report,"Daily Test Register","DAILY_TEST_REGISTER_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate)
				, isOptional(ReportParameter.TestType), isOptional(ReportParameter.Tester), isOptional(ReportParameter.TestOutcome)
				, isOptional(ReportParameter.Patient), isOptional(ReportParameter.ReferralLocation), isOptional(ReportParameter.DiagnosticLocation)}, DailyTestRegisterReport.class));

		reports.add(new Report("2ca9be51-6772-11ef-b6d8-00155d50437b", 20, BatchJobType.Report,"Summarized Test Report","SUMMARIZED_TEST_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate),
				 isOptional(ReportParameter.DiagnosticLocation)}, SummarizedTestReport.class));

		reports.add(new Report("2ca9be51-6773-11ef-b6d8-00155d50437b", 30, BatchJobType.Report,"Individual Personnel Performance","INDIVIDUAL_PERFORMANCE_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate),
				isOptional(ReportParameter.DiagnosticLocation), isOptional(ReportParameter.Tester)}, IndividualPerformanceReport.class));

		reports.add(new Report("2ca9be51-6775-11ef-b6d8-00155d50437b", 40, BatchJobType.Report,"Summarized Turnaround Time","SUMMARY_TAT_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate)
				, isOptional(ReportParameter.TestType), isOptional(ReportParameter.Tester)
				, isOptional(ReportParameter.Patient), isOptional(ReportParameter.ReferralLocation), isOptional(ReportParameter.DiagnosticLocation)}, SummarizedTurnAroundTimeReport.class));

		reports.add(new Report("2ca9be51-6769-11ef-b6d8-00155d50437b", 41, BatchJobType.Report,"Detail Turnaround Time","DETAIL_TAT_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate)
				, isOptional(ReportParameter.TestType), isOptional(ReportParameter.Tester)
				, isOptional(ReportParameter.Patient), isOptional(ReportParameter.ReferralLocation), isOptional(ReportParameter.DiagnosticLocation)}, DetailTurnAroundTimeReport.class));

		reports.add(new Report("2ca9be51-6776-11ef-b6d8-00155d50437b", 50, BatchJobType.Report,"Chain of Custody For Sample Movement","SAMPLE_MOVEMENT_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate),
				isOptional(ReportParameter.DiagnosticLocation), isOptional(ReportParameter.Patient), isOptional(ReportParameter.Tester), isOptional(ReportParameter.ReferenceNumber)}, SampleCustodyReport.class));

		reports.add(new Report("2ca9be51-6777-11ef-b6d8-00155d50437b", 60, BatchJobType.Report,"System Audit Trail Reports","AUDIT_TRAIL_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate),
				isOptional(ReportParameter.TestType), isOptional(ReportParameter.Tester)
				, isOptional(ReportParameter.Patient), isOptional(ReportParameter.ReferralLocation), isOptional(ReportParameter.DiagnosticLocation)}, AuditTestReport.class));

		reports.add(new Report("2ca9be51-6778-11ef-b6d8-00155d50437b", 70, BatchJobType.Report,"Patient specific diagnostic history","PATIENT_DIAGNOSTIC_REPORT", new ParameterRule[]{isRequired(ReportParameter.StartDate), isRequired(ReportParameter.EndDate),
				isOptional(ReportParameter.DiagnosticLocation), isOptional(ReportParameter.TestType), isRequired(ReportParameter.Patient)}, PatientDiagonisticHistoryReport.class));

        return reports;
    }

	public static ParameterRule isRequired(ReportParameter parameter) {
		return new ParameterRule(parameter, true);
	}

	public static  ParameterRule isOptional(ReportParameter parameter) {
		return new ParameterRule(parameter, false);
	}
}
