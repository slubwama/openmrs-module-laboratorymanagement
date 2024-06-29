package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.ApprovalFlowDTO;
import org.openmrs.module.labmanagement.api.dto.ApprovalFlowSearchFilter;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.labmanagement.api.model.ApprovalFlow;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/approval-flow", supportedClass = ApprovalFlowDTO.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class ApprovalFlowResource extends ResourceBase<ApprovalFlowDTO> {

    @Override
    public ApprovalFlowDTO getByUniqueId(String uniqueId) {
        ApprovalFlowSearchFilter filter = new ApprovalFlowSearchFilter();
        filter.setVoided(null);
        filter.setApprovalFlowUuid(uniqueId);
        Result<ApprovalFlowDTO> result = getLabManagementService().findApprovalFlows(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(ApprovalFlowDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteApprovalFlow(delegate.getUuid());
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        ApprovalFlowSearchFilter filter = new ApprovalFlowSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("name");
        if (!StringUtils.isBlank(param)) {
            filter.setNameOrSystemName(param);
        }

        param = context.getParameter("approvalConfig");
        if (!StringUtils.isBlank(param)) {
            filter.setApprovalConfigUuid(param);
        }

        Result<ApprovalFlowDTO> result = getLabManagementService().findApprovalFlows(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public ApprovalFlowDTO newDelegate() {
        return new ApprovalFlowDTO();
    }

    @Override
    public ApprovalFlowDTO save(ApprovalFlowDTO delegate) {
        ApprovalFlow approvalFlow = getLabManagementService().saveApprovalFlow(delegate);
        return getByUniqueId(approvalFlow.getUuid());
    }

    @Override
    public void purge(ApprovalFlowDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("name");
        description.addProperty("systemName");
        description.addProperty("levelOneUuid");
        description.addProperty("levelOneAllowOwner");
        description.addProperty("levelTwoUuid");
        description.addProperty("levelTwoAllowOwner");
        description.addProperty("levelThreeUuid");
        description.addProperty("levelThreeAllowOwner");
        description.addProperty("levelFourUuid");
        description.addProperty("levelFourAllowOwner");
        description.addProperty("levelTwoAllowPrevious");
        description.addProperty("levelThreeAllowPrevious");
        description.addProperty("levelFourAllowPrevious");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
       return getCreatableProperties();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("name");
            description.addProperty("systemName");
            description.addProperty("levelOneUuid");
            description.addProperty("levelOneApprovalTitle");
            description.addProperty("levelOneAllowOwner");
            description.addProperty("levelTwoUuid");
            description.addProperty("levelTwoApprovalTitle");
            description.addProperty("levelTwoAllowOwner");
            description.addProperty("levelThreeUuid");
            description.addProperty("levelThreeApprovalTitle");
            description.addProperty("levelThreeAllowOwner");
            description.addProperty("levelFourUuid");
            description.addProperty("levelFourApprovalTitle");
            description.addProperty("levelFourAllowOwner");
            description.addProperty("levelTwoAllowPrevious");
            description.addProperty("levelThreeAllowPrevious");
            description.addProperty("levelFourAllowPrevious");
            changeTrackingFields(description);

        }

        if (rep instanceof DefaultRepresentation) {
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
            description.addProperty("name");
        }

        return description;
    }


    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("name", new StringProperty());
            modelImpl.property("systemName", new StringProperty());
            modelImpl.property("levelOneUuid", new StringProperty());
            modelImpl.property("levelOneApprovalTitle", new StringProperty());
            modelImpl.property("levelTwoUuid", new StringProperty());
            modelImpl.property("levelTwoApprovalTitle", new StringProperty());
            modelImpl.property("levelThreeUuid", new StringProperty());
            modelImpl.property("levelThreeApprovalTitle", new StringProperty());
            modelImpl.property("levelFourUuid", new StringProperty());
            modelImpl.property("levelFourApprovalTitle", new StringProperty());
            modelImpl.property("levelOneAllowOwner", new BooleanProperty());
            modelImpl.property("levelTwoAllowOwner", new BooleanProperty());
            modelImpl.property("levelThreeAllowOwner", new BooleanProperty());
            modelImpl.property("levelFourAllowOwner", new BooleanProperty());
            modelImpl.property("levelTwoAllowPrevious", new BooleanProperty());
            modelImpl.property("levelThreeAllowPrevious", new BooleanProperty());
            modelImpl.property("levelFourAllowPrevious", new BooleanProperty());
            changeTrackingFields(modelImpl);
        }


        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("name", new StringProperty());
        }

        return modelImpl;
    }
}
