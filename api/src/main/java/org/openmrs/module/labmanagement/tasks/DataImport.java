package org.openmrs.module.labmanagement.tasks;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;

import java.util.Set;

import org.openmrs.module.dataexchange.DataImporter;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;

public class DataImport implements StartupTask {

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public void execute() {
		log.debug("Importing lab management privileges and roles");
		try {
			DataImporter dataImporter = Context.getRegisteredComponent("dataImporter", DataImporter.class);
			log.info("Start import of lab management privileges");
			dataImporter.importData("labmgmt/metadata/Role_Privilege.xml");
			log.info("lab management privileges imported");
		}
		catch (Exception exception) {
			log.error("Error while importing lab management privileges and roles", exception);
		}

		try{
			if(StringUtils.isBlank(GlobalProperties.getHealthCenterName())){
				if(!StringUtils.isBlank(GlobalProperties.getUgandaEMRHealthCenterName())){
					Context.getAdministrationService().setGlobalProperty(GlobalProperties.HEALTH_CENTER_NAME,GlobalProperties.getUgandaEMRHealthCenterName());
				}
			}
		}catch (Exception exception) {
			log.error("Error while updating default health center name property", exception);
		}

		try{
			applyRoleAssignments();
		}catch (Exception exception) {
			log.error("Error while updating parent-child role assignments", exception);
		}

	}

	public void applyRoleAssignments(){
		String organizationalClinicianRoleName = "Organizational: Clinician";
		String organizationalLaboratoryRoleName = "Organizational: Laboratory";

		String laboratoryClinician="Laboratory Clinician";
		String laboratoryAdministrator="Laboratory Administrator";

		UserService userService = Context.getUserService();
		applyParentRoleAssignments(userService, organizationalClinicianRoleName,laboratoryClinician);
		applyParentRoleAssignments(userService, organizationalLaboratoryRoleName,laboratoryAdministrator);

	}

	public void  applyParentRoleAssignments(UserService userService, String childRoleName, String parentRoleName){
		Role parentRole = userService.getRole(parentRoleName);
		if(parentRole != null){
			Set<Role> childRoles = parentRole.getChildRoles();
			if( childRoles != null && childRoles.stream().noneMatch(childRole -> childRole.getName().equals(childRoleName))) {
				Role  childRole = userService.getRole(childRoleName);
				if(childRole != null){
					childRoles.add(childRole);
					userService.saveRole(parentRole);
				}
			}
		}

	}

	@Override
	public int getPriority() {
		return 10;
	}
}
