package org.openmrs.module.labmanagement.tasks;

public interface StartupTask {
	
	void execute();
	
	int getPriority();
}
