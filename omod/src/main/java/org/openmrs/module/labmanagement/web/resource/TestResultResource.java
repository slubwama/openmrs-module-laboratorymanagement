package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.ArrayList;
import java.util.List;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-result", supportedClass = TestResultDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestResultResource extends ResourceBase<TestResultDTO> {

    @Override
    public TestResultDTO getByUniqueId(String uniqueId) {
        TestResultSearchFilter filter = new TestResultSearchFilter();
        filter.setVoided(null);
        filter.setTestResultUuid(uniqueId);
        Result<TestResultDTO> result = getLabManagementService().findTestResults(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(TestResultDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        TestResultSearchFilter filter = new TestResultSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("testResult");
        if (!StringUtils.isBlank(param)) {
            TestResult testResult = getLabManagementService().getTestResultByUuid(param);
            if (testResult == null) {
                return emptyResult(context);
            }
            filter.setTestResultId(testResult.getId());
        }

        param = context.getParameter("testRequestItem");
        if (StringUtils.isNotBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> testRequestItemIds = new ArrayList<>();
            for (String conceptUuid : params) {
                TestRequestItem testRequestItem = getLabManagementService().getTestRequestItemByUuid(conceptUuid);
                if (testRequestItem != null) {
                    testRequestItemIds.add(testRequestItem.getId());
                }
            }
            if (testRequestItemIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setTestRequestItemIds(testRequestItemIds);
        }

        param = context.getParameter("worksheetItem");
        if (StringUtils.isNotBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> worksheetItemIds = new ArrayList<>();
            for (String conceptUuid : params) {
                WorksheetItem worksheetItem = getLabManagementService().getWorksheetItemByUuid(conceptUuid);
                if (worksheetItem != null) {
                    worksheetItemIds.add(worksheetItem.getId());
                }
            }
            if (worksheetItemIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setWorksheetItemIds(worksheetItemIds);
        }

        param = context.getParameter("patient");
        if (!StringUtils.isBlank(param)) {
            Patient patient = Context.getPatientService().getPatientByUuid(param);
            if (patient == null) {
                return emptyResult(context);
            }
            filter.setPatientId(patient.getPatientId());
        }

        param = context.getParameter("sort");
        if (!StringUtils.isBlank(param)) {
            filter.setSortOrders(org.openmrs.module.labmanagement.api.utils.StringUtils.parseSortOrder(param));
        }

        param = context.getParameter("requireApproval");
        if (!StringUtils.isBlank(param)) {
            filter.setRequireApproval(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("completed");
        if (!StringUtils.isBlank(param)) {
            filter.setCompleted(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("completedResult");
        if (!StringUtils.isBlank(param)) {
            filter.setCompletedResult(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("approvalPerm");
        if (!StringUtils.isBlank(param)) {
            filter.setPermApproval("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("testResultApprovals");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeApprovals("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        Result<TestResultDTO> result = getLabManagementService().findTestResults(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public TestResultDTO newDelegate() {
        return new TestResultDTO();
    }

    @Override
    public TestResultDTO save(TestResultDTO delegate) {
        TestResult testResult = getLabManagementService().saveTestResult(delegate);
        return getByUniqueId(testResult.getUuid());
    }

    @Override
    public void purge(TestResultDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("worksheetItemUuid");
        description.addProperty("testRequestItemSampleUuid");
        description.addProperty("obs");
        description.addProperty("remarks");
        description.addProperty("atLocationUuid");
        description.addProperty("additionalTestsRequired");
        description.addProperty("archiveSample");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        return getCreatableProperties();
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation){
            description.addProperty("uuid");
            description.addProperty("worksheetItemUuid");
            description.addProperty("sampleUuid");
            description.addProperty("sampleProvidedRef");
            description.addProperty("sampleAccessionNumber");
            description.addProperty("sampleExternalRef");
            description.addProperty("orderUuid");
            description.addProperty("orderNumber");
            description.addProperty("testRequestItemUuid");
            description.addProperty("testRequestItemSampleUuid");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("resultBy");
            description.addProperty("resultByUuid");
            description.addProperty("resultByGivenName");
            description.addProperty("resultByMiddleName");
            description.addProperty("resultByFamilyName");
            description.addProperty("status");
            description.addProperty("resultDate");
            description.addProperty("requireApproval");
            description.addProperty("currentApprovalUuid");
            description.addProperty("additionalTestsRequired");
            description.addProperty("archiveSample");
            description.addProperty("remarks");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("dateChanged");
            description.addProperty("completed");
            description.addProperty("completedResult");
            description.addProperty("hasAttachment");
            description.addProperty("completedDate");
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("obs");
            description.addProperty("approvalPrivilege");
            description.addProperty("currentApprovalLevel");
            description.addProperty("approvalFlowLevelOneAllowOwner");
            description.addProperty("approvalFlowLevelTwoAllowOwner");
            description.addProperty("approvalFlowLevelThreeAllowOwner");
            description.addProperty("approvalFlowLevelFourAllowOwner");
            description.addProperty("approvalFlowLevelTwoAllowPrevious");
            description.addProperty("approvalFlowLevelThreeAllowPrevious");
            description.addProperty("approvalFlowLevelFourAllowPrevious");
            description.addProperty("permission");
        }

        if (rep instanceof DefaultRepresentation){

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation){
            description.addProperty("approvals", Representation.FULL);
            description.addSelfLink();
        }

        if(rep instanceof RefRepresentation){
            description.addProperty("uuid");
            description.addProperty("sampleProvidedRef");
            description.addProperty("sampleAccessionNumber");
            description.addProperty("sampleExternalRef");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");

        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("worksheetItemUuid", new StringProperty());
            modelImpl.property("sampleUuid", new StringProperty());
            modelImpl.property("sampleProvidedRef", new StringProperty());
            modelImpl.property("sampleAccessionNumber", new StringProperty());
            modelImpl.property("sampleExternalRef", new StringProperty());
            modelImpl.property("orderId", new IntegerProperty());
            modelImpl.property("orderUuid", new StringProperty());
            modelImpl.property("orderNumber", new StringProperty());
            modelImpl.property("testRequestItemUuid", new StringProperty());
            modelImpl.property("testRequestItemSampleUuid", new StringProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("resultByUuid", new StringProperty());
            modelImpl.property("resultByGivenName", new StringProperty());
            modelImpl.property("resultByMiddleName", new StringProperty());
            modelImpl.property("resultByFamilyName", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("resultDate", new DateTimeProperty());
            modelImpl.property("requireApproval", new BooleanProperty());
            modelImpl.property("currentApprovalUuid", new StringProperty());
            modelImpl.property("additionalTestsRequired", new BooleanProperty());
            modelImpl.property("archiveSample", new BooleanProperty());
            modelImpl.property("remarks", new StringProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());
            modelImpl.property("completed", new BooleanProperty());
            modelImpl.property("completedResult", new BooleanProperty());
            modelImpl.property("completedDate", new DateTimeProperty());
            modelImpl.property("obs", new RefProperty());
            modelImpl.property("approvalPrivilege", new StringProperty());
            modelImpl.property("currentApprovalLevel", new IntegerProperty());
            modelImpl.property("approvalFlowLevelOneAllowOwner", new BooleanProperty());
            modelImpl.property("approvalFlowLevelTwoAllowOwner", new BooleanProperty());
            modelImpl.property("approvalFlowLevelThreeAllowOwner", new BooleanProperty());
            modelImpl.property("approvalFlowLevelFourAllowOwner", new BooleanProperty());
            modelImpl.property("approvalFlowLevelTwoAllowPrevious", new BooleanProperty());
            modelImpl.property("approvalFlowLevelThreeAllowPrevious", new BooleanProperty());
            modelImpl.property("approvalFlowLevelFourAllowPrevious", new BooleanProperty());

        }
        if (rep instanceof DefaultRepresentation) {

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("approvals", new ArrayProperty());
        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(TestResultDTO testResultDTO) {
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canApprove", testResultDTO.getCanApprove());
        simpleObject.add("canUpdate", testResultDTO.getCanUpdate());
        return simpleObject;
    }

    @PropertyGetter("hasAttachment")
    public Boolean getHasAttachment(TestResultDTO testResultDTO) {
        return testResultDTO.getDocumentId() != null;
    }


}
