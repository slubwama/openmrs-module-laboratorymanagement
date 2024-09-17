package org.openmrs.module.labmanagement.web.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.ImportResult;
import org.openmrs.module.labmanagement.api.dto.TestResultDTO;
import org.openmrs.module.labmanagement.api.model.Document;
import org.openmrs.module.labmanagement.api.model.TestResult;
import org.openmrs.module.labmanagement.api.model.Worksheet;
import org.openmrs.module.labmanagement.api.utils.FileUtil;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.openmrs.module.labmanagement.api.model.Document.TEST_RESULT_DOC_GROUP;
import static org.openmrs.module.labmanagement.api.model.Document.WORKSHEET_TEST_RESULT_DOC_GROUP;

@Controller("${rootrootArtifactId}.TestResultAttachmentController")
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-result-attachment")
public class TestResultAttachmentController {

	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ImportResult upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        boolean authenticated = Context.isAuthenticated();
        if (!authenticated) {
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add(Context.getMessageSourceService().getMessage("labmanagement.authrequired"));
            return importResult;
        }

        if (file == null) {
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add("File not specified");
            return importResult;
        }

        if (file.getSize() > (GlobalProperties.getTestResultsMaxUploadSize() * 1024 * 1024)) {
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add(
                    String.format(
                            Context.getMessageSourceService().getMessage("labmanagement.importmaxfilesizeexceeded"),
                            GlobalProperties.getTestConfigsMaxUploadSize()));
            return importResult;
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension != null && !extension.isEmpty() && !extension.startsWith(".")){
            extension = "." + extension;
        }
        if(FileUtil.isBlockedExtension(extension)){
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add(Context.getMessageSourceService().getMessage("labmanagement.importcontenttypenotsupported"));
            return importResult;
        }

        String testResultUuid = request.getParameter("testResultUuid");
        String worksheetUuid = request.getParameter("worksheetUuid");
        if(StringUtils.isBlank(testResultUuid) && StringUtils.isBlank(worksheetUuid)){
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add("Specify worksheet or test result");
            return importResult;
        }

        Integer testResultId = null;
        List<Integer> worksheetTestResultIds = null;
        LabManagementService labManagementService = Context.getService(LabManagementService.class);
        if(StringUtils.isNotBlank(testResultUuid)){
            TestResult testResult = labManagementService.getTestResultByUuid(testResultUuid);
            if(testResult == null){
                ImportResult importResult = new ImportResult();
                importResult.setSuccess(false);
                importResult.setErrors(new ArrayList<>());
                importResult.getErrors().add("Test result not found");
                return importResult;
            }
            if(testResult.getDocumentId() != null){
                if(!TestResultDTO.canUpdateTestResult(testResult, GlobalProperties.getTestResultEditTimeout())) {
                    ImportResult importResult = new ImportResult();
                    importResult.setSuccess(false);
                    importResult.setErrors(new ArrayList<>());
                    importResult.getErrors().add("Test result modification not allowed");
                    return importResult;
                }
            }
            testResultId = testResult.getId();
        }else{
            Worksheet worksheet = labManagementService.getWorksheetByUuid(worksheetUuid);
            if(worksheet == null){
                ImportResult importResult = new ImportResult();
                importResult.setSuccess(false);
                importResult.setErrors(new ArrayList<>());
                importResult.getErrors().add("Worksheet not found");
                return importResult;
            }
            worksheetTestResultIds = labManagementService.getWorksheetTestResultIdsForAttachmentUpdate(worksheet.getId());
            if(worksheetTestResultIds.isEmpty()){
                ImportResult importResult = new ImportResult();
                importResult.setSuccess(false);
                importResult.setErrors(new ArrayList<>());
                importResult.getErrors().add("Worksheet test result modification not allowed");
                return importResult;
            }
        }

        Document document = new Document();
        document.setFileExtension(extension);
        document.setContentType( file.getContentType());
        document.setContentSize(file.getSize());
        document.setContentTitle("Test Result Attachment");
        document.setFileName(file.getOriginalFilename());
        document.setDocumentGroup(StringUtils.isBlank(testResultUuid) ? WORKSHEET_TEST_RESULT_DOC_GROUP : TEST_RESULT_DOC_GROUP);
        document.setDocumentGroupItemRef(StringUtils.isBlank(testResultUuid) ? worksheetUuid : testResultUuid);
        document.setContentData(file.getBytes());
        document.setCreator(Context.getAuthenticatedUser());
        document.setDateCreated(new Date());

        int updateCount;
        if(testResultId != null){
            updateCount = 1;
            document.setRefCount(1);
            document.setRefUpdate(new Date());
            labManagementService.saveTestResultAttachment(Collections.singletonList(testResultId), document);
        }else{
            updateCount = worksheetTestResultIds.size();
            document.setRefCount(worksheetTestResultIds.size());
            document.setRefUpdate(new Date());
            labManagementService.saveTestResultAttachment(worksheetTestResultIds, document);
        }
        ImportResult importResult = new ImportResult();
        importResult.setSuccess(true);
        importResult.setUpdatedCount(updateCount);

        return importResult;
    }

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object getDocument(@RequestParam(value = "id") String testResultUuid, HttpServletResponse response) {
		boolean authenticated = Context.isAuthenticated();
		if (!authenticated) {
			return Context.getMessageSourceService().getMessage("labmanagement.authrequired");
		}

        LabManagementService labManagementService = Context.getService(LabManagementService.class);
        TestResult testResult = labManagementService.getTestResultByUuid(testResultUuid);
        if(testResult == null){
            response.setStatus(404);
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add("Test result not found");
            return importResult;
        }

        if(testResult.getDocumentId() == null){
            response.setStatus(404);
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add("Test result has no attachment found");
            return importResult;
        }

        Document document = labManagementService.getDocumentById(testResult.getDocumentId());
        if(document == null){
            response.setStatus(404);
            ImportResult importResult = new ImportResult();
            importResult.setSuccess(false);
            importResult.setErrors(new ArrayList<>());
            importResult.getErrors().add("Attachment not found");
            return importResult;
        }

        String fileName = document.getFileName();
        if(!StringUtils.isBlank(fileName)){
            fileName = fileName.replace("/","").replace("\\","").replace("\"","");
        }
        if(StringUtils.isBlank(fileName)){
            fileName = "result-file-" + testResultUuid ;
        }

		response.setContentType(StringUtils.isBlank(document.getContentType()) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : document.getContentType());
        response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		return document.getContentData();
	}
}
