package org.openmrs.module.labmanagement.api.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Properties;

public class GlobalProperties {

	/**
	 * Logger for this class and subclasses
	 */
	protected final Log log = LogFactory.getLog(getClass());

	public static final String ENVIRONMENT = ModuleConstants.MODULE_ID + ".environment";

	public static final String TEST_SEARCH_MAX_INTERMEDIATE_RESULT = ModuleConstants.MODULE_ID
	        + ".testSearchMaxIntermediateResult";

	public static final String OTHER_REFERENCE_LAB_CONCEPT = ModuleConstants.MODULE_ID + ".otherReferenceLabConcept";

	public  static  final String LABORATORY_ENCOUNTER_TYPE = ModuleConstants.MODULE_ID + ".laboratoryEncounterUuid";

	public static final String LABORATORY_PROVIDER_ENCOUNTER_ROLE = ModuleConstants.MODULE_ID + ".laboratoryEncounterRoleUuid";

	public static final String REQUIRE_REFERRAL_TEST_REQUEST_APPROVAL = ModuleConstants.MODULE_ID + ".requireReferralTestRequestApproval";
	public static final String REQUIRE_TEST_REQUEST_APPROVAL = ModuleConstants.MODULE_ID + ".requireTestRequestApproval";

	public static final String OWNER_CAN_APPROVE_TEST_REQUESTS = ModuleConstants.MODULE_ID + ".ownerCanApproveTestRequests";

	public static final String CLINICAL_NOTES_CONCEPT_UUID = ModuleConstants.MODULE_ID + ".clinicalNotesConceptUuid";

	public static final String CARE_SETTING_FOR_REFERRALS = ModuleConstants.MODULE_ID + ".careSettingForReferrals";
	public static final String UNKNOWN_PROVIDER_UUID = ModuleConstants.MODULE_ID + ".unknownProviderUuid";

	public static final String PATIENT_IDENTIFIER_TYPES = ModuleConstants.MODULE_ID + ".patientIdentifierTypes";

	public static final String TEST_RESULT_EDIT_TIMEOUT = ModuleConstants.MODULE_ID + ".testResultEditTimeout";

	public static final String AUTO_RELEASE_SAMPLES = ModuleConstants.MODULE_ID + ".autoReleaseSamples";

	public static final String HEALTH_CENTER_NAME = ModuleConstants.MODULE_ID + ".healthCenterName";

	public static final String UGEMR_HEALTH_CENTER_NAME = "ugandaemr.healthCenterName";

	public static final String UNKNOWN_CONCEPT_UUID = ModuleConstants.MODULE_ID + ".unknownConceptUuid";

	public static final String TEST_CONFIGS_MAX_UPLOAD_FILE_SIZE = ModuleConstants.MODULE_ID + ".testConfigsMaxUploadFileSize";

	public static final String DEFAULT_VISIT_TYPE = ModuleConstants.MODULE_ID + ".defaultVisitType";

	public static final String PRINT_LOGO = ModuleConstants.MODULE_ID + ".printLogoUri";

	public static final String PRINT_LOGO_TEXT = ModuleConstants.MODULE_ID + ".printLogoText";

	public static final String BATCH_JOB_EXPIRY_IN_MINUTES = ModuleConstants.MODULE_ID + ".batchJobExpiryInMinutes";

	public static final String APPLICATION_ROOT_URL = ModuleConstants.MODULE_ID + ".applicationRootUrl";

	public static final String ENABLE_DATA_MIGRATION = ModuleConstants.MODULE_ID + ".enableDataMigration";

	public static final String LAST_MIGRATED_ORDER_ID = ModuleConstants.MODULE_ID + ".lastMigratedOrderId";




	public static GlobalProperty setGlobalProperty(String property, String propertyValue) {
		GlobalProperty globalProperty = new GlobalProperty();
		globalProperty.setProperty(property);
		globalProperty.setPropertyValue(propertyValue);
		return Context.getAdministrationService().saveGlobalProperty(globalProperty);
	}

	public static String getGlobalProperty(String property) {
		return Context.getAdministrationService().getGlobalProperty(property);
	}

	public static void saveGlobalProperty(GlobalProperty property) {
		Context.getAdministrationService().saveGlobalProperty(property);
	}

	public static String getPrintLogo() {
		try {
			return getGlobalProperty(PRINT_LOGO);
		}
		catch (Exception exception) {}
		return null;
	}

	public static String getPrintLogoText() {
		try {
			return getGlobalProperty(PRINT_LOGO_TEXT);
		}
		catch (Exception exception) {}
		return null;
	}

	///////////////////////////////

	public static boolean isDevelopment() {
		return ModuleConstants.DEV_ENVIRONMENT.equalsIgnoreCase(getGlobalProperty(ENVIRONMENT));
	}

	public static Integer getTestSearchMaxConceptIntermediateResult() {
		try {
			return Integer.parseInt(getGlobalProperty(TEST_SEARCH_MAX_INTERMEDIATE_RESULT));
		}
		catch (Exception exception) {}
		return 50;
	}

	public static long getTestConfigsMaxUploadSize() {
		try {
			return Long.parseLong(getGlobalProperty(TEST_CONFIGS_MAX_UPLOAD_FILE_SIZE));
		}
		catch (Exception exception) {}
		return 2;

	}

	public static String getOtherReferenceLabConcept() {
		try {
			return getGlobalProperty(OTHER_REFERENCE_LAB_CONCEPT);
		}
		catch (Exception exception) {}
		return null;
	}

	public static String getDefaultVisitType() {
		try {
			return getGlobalProperty(DEFAULT_VISIT_TYPE);
		}
		catch (Exception exception) {}
		return null;
	}

	public  static String getLaboratoryEncounterType(){
		try {
			return getGlobalProperty(LABORATORY_ENCOUNTER_TYPE);
		}
		catch (Exception exception) {}
		return null;
	}

	public  static String getLaboratoryProviderEncounterRole(){
		try {
			return getGlobalProperty(LABORATORY_PROVIDER_ENCOUNTER_ROLE);
		}
		catch (Exception exception) {}
		return null;
	}

	public static boolean getRequireReferralTestRequestApproval() {
		try {
			return Boolean.parseBoolean(getGlobalProperty(REQUIRE_REFERRAL_TEST_REQUEST_APPROVAL));
		}
		catch (Exception exception) {}
		return true;
	}

	public static boolean getRequireTestRequestApproval() {
		try {
			return Boolean.parseBoolean(getGlobalProperty(REQUIRE_TEST_REQUEST_APPROVAL));
		}
		catch (Exception exception) {}
		return true;
	}

	public static boolean getOwnerCanApproveTestRequests() {
		try {
			return Boolean.parseBoolean(getGlobalProperty(OWNER_CAN_APPROVE_TEST_REQUESTS));
		}
		catch (Exception exception) {}
		return true;
	}

	public static String getClinicalNotesConceptUuid() {
		try {
			return getGlobalProperty(CLINICAL_NOTES_CONCEPT_UUID);
		}
		catch (Exception exception) {}
		return null;
	}

	public static String getUnknownProviderUuid() {
		try {
			return getGlobalProperty(UNKNOWN_PROVIDER_UUID);
		}
		catch (Exception exception) {}
		return null;
	}

	public static  String getCareSettingForReferrals(){
		try {
			return getGlobalProperty(CARE_SETTING_FOR_REFERRALS);
		}
		catch (Exception exception) {}
		return null;
	}

	public static  String getPatientIdentifierTypes(){
		try {
			return getGlobalProperty(PATIENT_IDENTIFIER_TYPES);
		}
		catch (Exception exception) {}
		return null;
	}

	public static Integer getTestResultEditTimeout(){
		try {
			Integer value = Integer.parseInt(getGlobalProperty(TEST_RESULT_EDIT_TIMEOUT));
			return value < 0  ? 0 : value;
		}
		catch (Exception exception) {}
		return 0;
	}


	public static boolean getAutoReleaseSamples() {
		try {
			return Boolean.parseBoolean(getGlobalProperty(AUTO_RELEASE_SAMPLES));
		}
		catch (Exception exception) {}
		return true;
	}

	/////////////////////////////

	public static String getUnknownConceptUuid() {
		try {
			return getGlobalProperty(UNKNOWN_CONCEPT_UUID);
		}
		catch (Exception exception) {}
		return null;
	}
	public static String getHealthCenterName() {
		try {
			return getGlobalProperty(HEALTH_CENTER_NAME);
		}
		catch (Exception exception) {}
		return null;
	}

	public static String getUgandaEMRHealthCenterName() {
		try {
			return getGlobalProperty(UGEMR_HEALTH_CENTER_NAME);
		}
		catch (Exception exception) {}
		return null;
	}

	public static Integer getBatchJobExpiryInMinutes() {
		try {
			return Math.max(1440, Integer.parseInt(getGlobalProperty(BATCH_JOB_EXPIRY_IN_MINUTES)));
		}
		catch (Exception exception) {}
		return 1440;
	}

	public static String getApplicationRootUrl() {
		try {
			return getGlobalProperty(APPLICATION_ROOT_URL);
		}
		catch (Exception exception) {}
		return null;
	}

	public static String toString(Properties properties, String description) throws IOException {
		try(StringWriter stringWriter = new StringWriter()) {
			properties.store(stringWriter, description);
			return stringWriter.toString();
		}
	}

	public static Properties fromString(String propertiesString) throws IOException {
		final Properties properties = new Properties();
		try(StringReader stringReader = new StringReader(propertiesString)) {
			properties.load(stringReader);
			return properties;
		}
	}

	public static Integer GetReportingRecordsPageSize() {
		try {
			Integer result = Integer.parseInt(getGlobalProperty("labmanagement.reportingRecordsPageSize"));
			return result <= 0 ? 1000 : result;
		}
		catch (Exception exception) {}
		return 1000;
	}

	public static BigDecimal GetReportingCalculationsNoDaysInMonth() {
		try {
			BigDecimal result = new BigDecimal(getGlobalProperty("labmanagement.reportingCalculationsNoDaysInMonth"));
			return result.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.valueOf(30.5) : result;
		}
		catch (Exception exception) {}
		return BigDecimal.valueOf(30.5);
	}

	public static boolean enableDataMigration() {
		try {
			return Boolean.parseBoolean(getGlobalProperty(ENABLE_DATA_MIGRATION));
		}
		catch (Exception exception) {}
		return true;
	}


	public static Integer getLastMigratedOrderId() {
		try {
			return Integer.parseInt(getGlobalProperty(LAST_MIGRATED_ORDER_ID));
		}
		catch (Exception exception) {}
		return 0;
	}

	public static void setLastMigratedOrderId(Integer value) {
		setGlobalProperty(LAST_MIGRATED_ORDER_ID, String.valueOf(value));
	}
}
