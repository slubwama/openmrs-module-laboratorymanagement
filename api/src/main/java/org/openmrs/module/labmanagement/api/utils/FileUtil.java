package org.openmrs.module.labmanagement.api.utils;

import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;

public class FileUtil {
	
	public static File getWorkingDirectory() {
		File workingDirectory = OpenmrsUtil.getDirectoryInApplicationDataDirectory(ModuleConstants.APP_DATA_WORKING_DIR);
		return workingDirectory;
	}
	
	public static File getBatchJobFolder() {
		File folder = new File(getWorkingDirectory(), "batchjob");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}
}
