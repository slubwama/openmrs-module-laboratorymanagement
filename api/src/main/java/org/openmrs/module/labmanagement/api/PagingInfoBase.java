package org.openmrs.module.labmanagement.api;

public abstract class PagingInfoBase implements IPagingInfo {
	
	public Boolean hasMoreResults() {
		return getPageIndex() != null && getPageSize() != null && getTotalRecordCount() != null
		        && ((getPageIndex() + 1) * getPageSize()) < getTotalRecordCount();
	}
}
