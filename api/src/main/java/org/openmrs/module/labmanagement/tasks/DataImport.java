package org.openmrs.module.labmanagement.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;

import java.lang.reflect.Method;
import org.openmrs.module.dataexchange.DataImporter;

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
	}

	@Override
	public int getPriority() {
		return 10;
	}
}
