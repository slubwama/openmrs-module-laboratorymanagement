package org.openmrs.module.labmanagement.api;

import java.util.Arrays;
import java.util.List;

public class Privileges {

	/**
	 * App: labmanagement.dashboard: Able to view lab management application dashboard
	 */
	public static final String APP_LABMANAGEMENT_DASHBOARD = "App: labmanagement.dashboard";

	/**
	 * App: labmanagement.approvalconfigurations: Able to view lab approval configurations
	 */
	public static final String APP_LABMANAGEMENT_APPROVALCONFIGURATIONS = "App: labmanagement.approvalconfigurations";

	/**
	 * Task: labmanagement.approvalconfigurations.mutate: Able to create and update lab approval configurations
	 */
	public static final String TASK_LABMANAGEMENT_APPROVALCONFIGURATIONS_MUTATE = "Task: labmanagement.approvalconfigurations.mutate";

	/**
	 * App: labmanagement.testconfigurations: Able to view lab test configurations
	 */
	public static final String APP_LABMANAGEMENT_TESTCONFIGURATIONS = "App: labmanagement.testconfigurations";

	/**
	 * Task: labmanagement.testconfigurations.mutate: Able to create and update lab test configurations
	 */
	public static final String TASK_LABMANAGEMENT_TESTCONFIGURATIONS_MUTATE = "Task: labmanagement.testconfigurations.mutate";

	/**
	 * App: labmanagement.testrequests: Able to view lab tests requested
	 */
	public static final String APP_LABMANAGEMENT_TESTREQUESTS = "App: labmanagement.testrequests";

	/**
	 * Task: labmanagement.testrequests.mutate: Able to create and update lab test requests
	 */
	public static final String TASK_LABMANAGEMENT_TESTREQUESTS_MUTATE = "Task: labmanagement.testrequests.mutate";

	/**
	 * Task: labmanagement.testrequests.approve: Able to approve lab test requests
	 */
	public static final String TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE = "Task: labmanagement.testrequests.approve";

	/**
	 * App: labmanagement.samples: Able to view lab samples
	 */
	public static final String APP_LABMANAGEMENT_SAMPLES = "App: labmanagement.samples";

	/**
	 * Task: labmanagement.samples.collect: Able to collect lab samples
	 */
	public static final String TASK_LABMANAGEMENT_SAMPLES_COLLECT = "Task: labmanagement.samples.collect";

	/**
	 * Task: labmanagement.samples.mutate: Able to create, update and reuse lab samples
	 */
	public static final String TASK_LABMANAGEMENT_SAMPLES_MUTATE = "Task: labmanagement.samples.mutate";

	/**
	 * App: labmanagement.storage: Able to view lab storage
	 */
	public static final String APP_LABMANAGEMENT_STORAGE = "App: labmanagement.storage";

	/**
	 * Task: labmanagement.storage.mutate: Able to create and update lab storage
	 */
	public static final String TASK_LABMANAGEMENT_STORAGE_MUTATE = "Task: labmanagement.storage.mutate";

	/**
	 * App: labmanagement.worksheets: Able to view lab worksheets
	 */
	public static final String APP_LABMANAGEMENT_WORKSHEETS = "App: labmanagement.worksheets";

	/**
	 * Task: labmanagement.worksheets.mutate: Able to create and update lab worksheets
	 */
	public static final String TASK_LABMANAGEMENT_WORKSHEETS_MUTATE = "Task: labmanagement.worksheets.mutate";

	/**
	 * App: labmanagement.testresults: Able to view lab test results
	 */
	public static final String APP_LABMANAGEMENT_TESTRESULTS = "App: labmanagement.testresults";

	/**
	 * Task: labmanagement.testresults.mutate: Able to create and update lab test results
	 */
	public static final String TASK_LABMANAGEMENT_TESTRESULTS_MUTATE = "Task: labmanagement.testresults.mutate";

	/**
	 * Task: labmanagement.testresults.approve: Able to approve lab test results
	 */
	public static final String TASK_LABMANAGEMENT_TESTRESULTS_APPROVE = "Task: labmanagement.testresults.approve";

	/**
	 * App: labmanagement.reports: Able to view lab reports
	 */
	public static final String APP_LABMANAGEMENT_REPORTS = "App: labmanagement.reports";

	/**
	 * Task: labmanagement.reports.mutate: Able to create lab reports
	 */
	public static final String TASK_LABMANAGEMENT_REPORTS_MUTATE = "Task: labmanagement.reports.mutate";

	/**
	 * App: labmanagement.repository: Able to access sample repository
	 */
	public static final String APP_LABMANAGEMENT_REPOSITORY = "App: labmanagement.repository";

	/**
	 * Task: labmanagement.repository.mutate: Able to manage sample repository
	 */
	public static final String TASK_LABMANAGEMENT_REPOSITORY_MUTATE = "Task: labmanagement.repository.mutate";



    public static final List<String> ALL = Arrays.asList(	APP_LABMANAGEMENT_DASHBOARD,
	APP_LABMANAGEMENT_APPROVALCONFIGURATIONS,
	TASK_LABMANAGEMENT_APPROVALCONFIGURATIONS_MUTATE,
	APP_LABMANAGEMENT_TESTCONFIGURATIONS,
	TASK_LABMANAGEMENT_TESTCONFIGURATIONS_MUTATE,
	APP_LABMANAGEMENT_TESTREQUESTS,
	TASK_LABMANAGEMENT_TESTREQUESTS_MUTATE,
	TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE,
	APP_LABMANAGEMENT_SAMPLES,
	TASK_LABMANAGEMENT_SAMPLES_COLLECT,
	TASK_LABMANAGEMENT_SAMPLES_MUTATE,
	APP_LABMANAGEMENT_STORAGE,
	TASK_LABMANAGEMENT_STORAGE_MUTATE,
	APP_LABMANAGEMENT_WORKSHEETS,
	TASK_LABMANAGEMENT_WORKSHEETS_MUTATE,
	APP_LABMANAGEMENT_TESTRESULTS,
	TASK_LABMANAGEMENT_TESTRESULTS_MUTATE,
	TASK_LABMANAGEMENT_TESTRESULTS_APPROVE,
	APP_LABMANAGEMENT_REPORTS,
	TASK_LABMANAGEMENT_REPORTS_MUTATE,
	APP_LABMANAGEMENT_REPOSITORY,
	TASK_LABMANAGEMENT_REPOSITORY_MUTATE);
}
