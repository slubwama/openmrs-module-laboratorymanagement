package org.openmrs.module.labmanagement.api;

public class LabManagementException extends RuntimeException {
	
	public LabManagementException() {
	}
	
	public LabManagementException(String message) {
		super(message);
	}
	
	public LabManagementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LabManagementException(Throwable cause) {
		super(cause);
	}
	
	public LabManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
