package org.openmrs.module.labmanagement.api.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.BatchJobDTO;
import org.openmrs.module.labmanagement.api.model.BatchJobType;
import org.openmrs.module.labmanagement.api.reporting.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.*;

@Handler(supports = { BatchJobDTO.class }, order = 50)
public class LabBatchJobValidator implements Validator {
	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean supports(Class<?> aClass) {
		return BatchJobDTO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MessageSourceService messageSourceService = Context.getMessageSourceService();
		if (target == null) {
			errors.reject(messageSourceService.getMessage("error.general"));
			return;
		}

		BatchJobDTO object = (BatchJobDTO) target;

		if (object.getBatchJobType() == null) {
			errors.rejectValue("batchJobType",
			    String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "Batch Job Type"));
			return;
		} else {
			BatchJobType batchJobType = object.getBatchJobType();
			if(batchJobType == null || (!batchJobType.equals(BatchJobType.Report) && !batchJobType.equals(BatchJobType.Migration))){
				errors.rejectValue("batchJobType", String.format(
						messageSourceService.getMessage("labmanagement.batchjob.fieldvaluenotexist"), "Batch Job Type"));
				return;
			}
		}

		if (StringUtils.isBlank(object.getDescription())) {
			errors.rejectValue("description",
			    String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "description"));
			return;
		} else if (object.getDescription().length() > 255) {
			errors.rejectValue("description",
			    String.format(messageSourceService.getMessage("labmanagement.batchjob.exceedslimit"), "description", 255));
			return;
		}

		/*if (StringUtils.isBlank(object.getParameters())) {
			errors.rejectValue("parameters",
					String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "parameters"));
			return;
		} else if (object.getParameters().length() > 5000) {
			errors.rejectValue("parameters",
					String.format(messageSourceService.getMessage("labmanagement.batchjob.exceedslimit"), "parameters", 5000));
			return;
		}*/

		Map<String,?> properties = object.getParametersMap();
		if(properties == null){
			errors.rejectValue("parameters", String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "parameters"));
			return;
		}

		if(properties.isEmpty()){
			errors.rejectValue("parameters", String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "parameters"));
			return;
		}

		String reportSystemName = null;
		if((!properties.containsKey("report")) || StringUtils.isBlank(reportSystemName = StringReportParameter.parse(properties.getOrDefault("report", null)))){
			errors.rejectValue("parameters", String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldrequired"), "report"));
			return;
		}

		final String finalReportSystemName = reportSystemName;
		Optional<Report> reportOptional = Report.getAllReports().stream().filter(p->p.getSystemName().equals(finalReportSystemName)).findAny();
		if(!reportOptional.isPresent()){
			errors.rejectValue("parameters", String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldvaluenotexist"), "report"));
			return;
		}

		LabManagementService labManagementService = Context.getService(LabManagementService.class);
		Report report = reportOptional.get();
		ParameterRule[] reportParameters = report.getParameters();
		object.setReportParameters(new HashMap<>());
		for(Map.Entry<String,?> property : properties.entrySet()) {
			String propertyName = (String) property.getKey();
			if (propertyName.equals("report")) {
				continue;
			}

			ParameterRule reportParameter = ReportParameter.findInList(propertyName, reportParameters);
			if(reportParameter == null){
				errors.rejectValue("parameters", messageSourceService.getMessage("labmanagement.batchjob.supportedparams"));
				return;
			}

			ReportParameterValue<?> reportParameterValue = isValidReportParameter(reportParameter, property.getValue(), object, labManagementService, errors);
			if(reportParameterValue == null){
				errors.rejectValue("parameters", String.format(messageSourceService.getMessage("labmanagement.batchjob.fieldvaluenotexist"), propertyName));
				return;
			}
			object.getReportParameters().put(propertyName, reportParameterValue);
		}
		object.setPrivilegeScope(Privileges.APP_LABMANAGEMENT_REPORTS);
	}

	private ReportParameterValue<?> isValidReportParameter(ParameterRule reportParameter, Object value, BatchJobDTO batchJobDTO,
	        LabManagementService labManagementService, Errors errors) {
		ReportParameterValue<?> reportParameterValue = null;
		try {
			reportParameterValue = (ReportParameterValue<?>) reportParameter.getReportParameter().getParameterParserClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			log.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
 		try {
			reportParameterValue.setValueFromMap(value);
		}catch (Exception e){
			errors.reject(e.getMessage());
			return null;
		}

		 if(reportParameter.isRequired() && !reportParameterValue.isValueSet()){
			 return null;
		 }
		return reportParameterValue;
	}
}
