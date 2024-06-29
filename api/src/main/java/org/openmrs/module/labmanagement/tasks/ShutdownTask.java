package org.openmrs.module.labmanagement.tasks;

public interface ShutdownTask {
	
	void execute();
	
	int getPriority();
}
