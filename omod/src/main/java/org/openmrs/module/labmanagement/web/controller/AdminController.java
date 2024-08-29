package org.openmrs.module.labmanagement.web.controller;

import org.apache.commons.io.IOUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.BatchJobDTO;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.model.BatchJobStatus;
import org.openmrs.module.labmanagement.api.model.BatchJobType;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller("${rootrootArtifactId}.AdminController")
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/admin")
public class AdminController {

		@RequestMapping(method = RequestMethod.GET, path = "migrate")
	public void getLogo(HttpServletResponse response) throws IOException {
			BatchJobDTO batchJob=new BatchJobDTO();
			batchJob.setBatchJobType(BatchJobType.Migration);
			batchJob.setStatus(BatchJobStatus.Pending);
			batchJob.setDescription("Migrate old laboratory orders");
			batchJob.setParameters("");
			Context.getService(LabManagementService.class).saveBatchJob(batchJob);

	}
}
