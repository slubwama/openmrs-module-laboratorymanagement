package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.labmanagement.api.jobs.AsyncTaskJob;
import org.openmrs.module.labmanagement.api.model.BatchJob;

import java.util.Date;
import java.util.function.Function;

public abstract class ReportGenerator extends AsyncTaskJob {

	@Override
	public void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
		throw new NotImplementedException();
	}

	protected Date getTurnAroundStartDate(Date sampleCollectionDate, Date requestApprovalDate, Date requestCreatedDate){
		Date requestDate = requestApprovalDate != null ? requestApprovalDate : requestCreatedDate;
		if(sampleCollectionDate == null){
			return requestDate;
		}
		if(requestDate == null){
			return sampleCollectionDate;
		}
		return sampleCollectionDate.after(requestDate) ? sampleCollectionDate : requestDate;
	}
}
