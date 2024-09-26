package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-approvals", supportedClass = TestApprovalDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestApprovalResource extends ResourceBase<TestApprovalDTO> {

    @Override
    public TestApprovalDTO getByUniqueId(String uniqueId) {
        TestApprovalSearchFilter filter = new TestApprovalSearchFilter();
        filter.setVoided(null);
        filter.setTestApprovalUuid(uniqueId);
        Result<TestApprovalDTO> result = getLabManagementService().findTestApprovals(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(TestApprovalDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        TestApprovalSearchFilter filter = new TestApprovalSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());

        LabManagementService labManagementService = getLabManagementService();
        String param = context.getParameter("testResult");
        if (StringUtils.isNotBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> testResultIds = new ArrayList<>();
            for (String conceptUuid : params) {
                TestResult testResult = labManagementService.getTestResultByUuid(conceptUuid);
                if (testResult != null) {
                    testResultIds.add(testResult.getId());
                }
            }
            if (testResultIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setTestResultIds(testResultIds);
        }

        Result<TestApprovalDTO> result = labManagementService.findTestApprovals(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return  doSearch(context);
    }

    @Override
    public TestApprovalDTO newDelegate() {
        return new TestApprovalDTO();
    }

    @Override
    public TestApprovalDTO save(TestApprovalDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(TestApprovalDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation){
            description.addProperty("uuid");
            description.addProperty("approvedByGivenName");
            description.addProperty("approvedByMiddleName");
            description.addProperty("approvedByFamilyName");
            description.addProperty("approvedByUuid");
            description.addProperty("testResultUuid");
            description.addProperty("approvalTitle");
            description.addProperty("approvalResult");
            description.addProperty("remarks");
            description.addProperty("activatedDate");
            description.addProperty("approvalDate");
            description.addProperty("currentApprovalLevel");
        }

        if (rep instanceof DefaultRepresentation){
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation){
            description.addSelfLink();
        }

        if(rep instanceof RefRepresentation){
            description.addProperty("uuid");
            description.addProperty("approvalTitle");
            description.addProperty("currentApprovalLevel");
        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("approvedByGivenName", new StringProperty());
            modelImpl.property("approvedByMiddleName", new StringProperty());
            modelImpl.property("approvedByFamilyName", new StringProperty());
            modelImpl.property("approvedByUuid", new StringProperty());
            modelImpl.property("testResultUuid", new StringProperty());
            modelImpl.property("approvalTitle", new StringProperty());
            modelImpl.property("approvalResult", new StringProperty());
            modelImpl.property("remarks", new StringProperty());
            modelImpl.property("activatedDate", new DateTimeProperty());
            modelImpl.property("approvalDate", new DateTimeProperty());
            modelImpl.property("currentApprovalLevel", new IntegerProperty());
        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("approvalTitle", new StringProperty());
            modelImpl.property("currentApprovalLevel", new IntegerProperty());
        }

        return modelImpl;
    }

}
