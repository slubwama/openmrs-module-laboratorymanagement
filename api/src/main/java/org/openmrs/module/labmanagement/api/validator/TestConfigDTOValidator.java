package org.openmrs.module.labmanagement.api.validator;

import org.apache.commons.lang.StringUtils;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.labmanagement.api.dto.TestConfigDTO;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.TestConfig;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Handler(supports = { TestConfigDTO.class }, order = 50)
public class TestConfigDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return TestConfigDTO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MessageSourceService messageSourceService = Context.getMessageSourceService();
		if (target == null) {
			errors.reject(messageSourceService.getMessage("error.general"));
			return;
		}

		if (Context.getAuthenticatedUser() == null) {
			errors.reject(messageSourceService.getMessage("labmanagement.authrequired"));
			return;
		}

		LabManagementService service = Context.getService(LabManagementService.class);
		TestConfigDTO object = (TestConfigDTO) target;
		TestConfig testConfig = null;
		if (object.getUuid() != null) {
			testConfig = service.getTestConfigurationByUuid(object.getUuid());
			if (testConfig == null) {
				errors.rejectValue("uuid",
						String.format(
								messageSourceService.getMessage("labmanagement.notexists"),
								"Test configuration"
						));
				return;
			}
		}

		if (testConfig == null) {
			if (StringUtils.isBlank(object.getTestUuid()) && StringUtils.isBlank(object.getTestUuid())) {
				errors.rejectValue("testUuid",
						String.format(
								messageSourceService.getMessage("labmanagement.conceptrequired"),
								"Test"
						));
				return;
			}
		}

		if (object.getRequireApproval() == null) {
			errors.rejectValue("requireApproval",
					String.format(
							messageSourceService.getMessage("labmanagement.fieldrequired"),
							"requireApproval"
					));
			return;
		}else if(object.getRequireApproval().equals(Boolean.TRUE) && StringUtils.isBlank(object.getApprovalFlowUuid())){
			errors.rejectValue("approvalFlowUuid",
					String.format(
							messageSourceService.getMessage("labmanagement.fieldrequiredif"),
							messageSourceService.getMessage("labmanagement.approvalflow"),
							messageSourceService.getMessage("labmanagement.requireapproval")
					));
			return;
		}

		if (object.getEnabled() == null) {
			errors.rejectValue("enabled",
					String.format(
							messageSourceService.getMessage("labmanagement.fieldrequired"),
							"enabled"
					));
			return;
		}
	}
}
