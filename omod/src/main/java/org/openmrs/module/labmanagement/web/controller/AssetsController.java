package org.openmrs.module.labmanagement.web.controller;

import org.apache.commons.io.IOUtils;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("${rootrootArtifactId}.AssetsController")
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/assets")
public class AssetsController {

		@RequestMapping(method = RequestMethod.GET, path = "logo")
	public void getLogo(HttpServletResponse response)
	        throws IOException {
			IOUtils.copy(AssetsController.class.getResourceAsStream("/assets/logo.png"), response.getOutputStream());
	}
}
