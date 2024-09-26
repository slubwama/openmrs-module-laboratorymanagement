package org.openmrs.module.labmanagement.web.controller;

import org.apache.commons.io.IOUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller("${rootrootArtifactId}.TestConfigImportTemplateController")
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-config-import-template")
public class TestConfigImportTemplateController {

		@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object getTemplate(HttpServletResponse response)
	        throws IOException {
		boolean authenticated = Context.isAuthenticated();
		if (!authenticated) {
			return Context.getMessageSourceService().getMessage("labmanagement.authrequired");
		}

		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Disposition", "attachment;FileName=\"Import_Test_Configurations.xlsx\"");
		InputStream in = getClass()
				.getResourceAsStream("/templates/Import_Test_Configurations.xlsx");
		return IOUtils.toByteArray(in);
	}
}
