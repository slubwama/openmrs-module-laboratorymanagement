package org.openmrs.module.labmanagement.web.controller;

import org.apache.commons.io.IOUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.BatchJob;
import org.openmrs.module.labmanagement.api.utils.DateUtil;
import org.openmrs.module.labmanagement.api.utils.FileUtil;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller("${rootrootArtifactId}.LabBatchJobArtifactController")
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/batch-job-artifact")
public class LabBatchJobArtifactController {

	@RequestMapping(method = RequestMethod.GET)
	public void getArtifact(@RequestParam(value = "id", required = true) String batchJobUuid,
	        @RequestParam(value = "download", required = false) String download, HttpServletResponse response)
	        throws IOException {
		boolean authenticated = Context.isAuthenticated();
		if (!authenticated) {
			response.setContentType("text/plain");
			response.getOutputStream().print(
			    Context.getMessageSourceService().getMessage("labmanagement.authrequired"));
			return;
		}
		LabManagementService labManagementService = Context.getService(LabManagementService.class);
		BatchJob batchJob = labManagementService.getBatchJobByUuid(batchJobUuid);
		if (batchJob == null) {
			response.setStatus(404);
			response.setContentType("text/plain");
			response.getOutputStream().print("Artifact not found");
			return;
		}

		boolean hasAccess = true;
		if (batchJob.getPrivilegeScope() != null) {
			hasAccess = Context.hasPrivilege(batchJob.getPrivilegeScope());
		}

		if (!hasAccess) {
			response.setContentType("text/plain");
			response.getOutputStream().print(Context.getMessageSourceService().getMessage("labmanagement.notauthorised"));
			return;
		}

		File pathToResource = new File(FileUtil.getBatchJobFolder(), batchJob.getUuid());
		if (!pathToResource.exists()) {
			response.setStatus(404);
			response.setContentType("text/plain");
			response.getOutputStream().print("Artifact file not found");
			return;
		}

		String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

		if (download == null && batchJob.getOutputArtifactFileExt() != null) {
			switch (batchJob.getOutputArtifactFileExt()) {
				case "csv":
					contentType = "text/csv";
					break;
				case "pdf":
					contentType = "application/pdf";
					break;
				case "xls":
					contentType = "application/vnd.ms-excel";
					break;
				case ".xlsx":
					contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
					break;
				case "xml":
					contentType = "application/xml";
					break;
			}
		}

		FileSystemResource fileSystemResource = new FileSystemResource(pathToResource);
		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment;filename=\"" + getFileName(batchJob) + "\""); // custom header FileName=\"{1}\"
		response.getOutputStream().write(IOUtils.toByteArray(fileSystemResource.getInputStream()));
	}

	private String getFileName(BatchJob batchJob) {
		String fileName = batchJob.getDescription();
		if (fileName == null) {
			fileName = batchJob.getUuid();
		}
		if (fileName.length() > 30) {
			fileName = fileName.substring(0, 29);
		}
		fileName = fileName + "-" + DateUtil.formatForFile(batchJob.getDateCreated());
		if (batchJob.getOutputArtifactFileExt() != null) {
			fileName = fileName + "." + batchJob.getOutputArtifactFileExt();
		}
		return fileName.replace("\"", "");
	}
}
