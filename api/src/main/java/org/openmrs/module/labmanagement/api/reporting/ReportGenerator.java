package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.labmanagement.api.jobs.AsyncTaskJob;
import org.openmrs.module.labmanagement.api.model.BatchJob;

import java.util.function.Function;

public abstract class ReportGenerator extends AsyncTaskJob {

	@Override
	public void execute(BatchJob batchJob, Function<BatchJob, Boolean> shouldStopExecution) {
		throw new NotImplementedException();
	}

}
