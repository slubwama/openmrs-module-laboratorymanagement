package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.ApprovalConfigDTO;
import org.openmrs.module.labmanagement.api.dto.ApprovalConfigSearchFilter;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.labmanagement.api.model.ApprovalConfig;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/approval-config", supportedClass = ApprovalConfigDTO.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class ApprovalConfigResource extends ResourceBase<ApprovalConfigDTO> {

    @Override
    public ApprovalConfigDTO getByUniqueId(String uniqueId) {
        ApprovalConfigSearchFilter filter = new ApprovalConfigSearchFilter();
        filter.setVoided(null);
        filter.setApprovalConfigUuid(uniqueId);
        Result<ApprovalConfigDTO> result = getLabManagementService().findApprovalConfigurations(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(ApprovalConfigDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteApprovalConfig(delegate.getUuid());
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        ApprovalConfigSearchFilter filter = new ApprovalConfigSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("approvalTitle");
        if (!StringUtils.isBlank(param)) {
            filter.setApprovalTitle(param);
        }

        Result<ApprovalConfigDTO> result = getLabManagementService().findApprovalConfigurations(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public ApprovalConfigDTO newDelegate() {
        return new ApprovalConfigDTO();
    }

    @Override
    public ApprovalConfigDTO save(ApprovalConfigDTO delegate) {
        ApprovalConfig approvalConfig = getLabManagementService().saveApprovalConfig(delegate);
        return getByUniqueId(approvalConfig.getUuid());
    }

    @Override
    public void purge(ApprovalConfigDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("approvalTitle");
        description.addProperty("privilege");
        description.addProperty("pendingStatus");
        description.addProperty("returnedStatus");
        description.addProperty("rejectedStatus");
        description.addProperty("approvedStatus");
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
            description.addProperty("approvalTitle");
            description.addProperty("privilege");
            description.addProperty("pendingStatus");
            description.addProperty("returnedStatus");
            description.addProperty("rejectedStatus");
            description.addProperty("approvedStatus");
            changeTrackingFields(description);

        }

        if (rep instanceof DefaultRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);

            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
            description.addProperty("approvalTitle");

        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("approvalTitle", new StringProperty());
            modelImpl.property("privilege", new StringProperty());
            modelImpl.property("pendingStatus", new StringProperty());
            modelImpl.property("returnedStatus", new StringProperty());
            modelImpl.property("rejectedStatus", new StringProperty());
            modelImpl.property("approvedStatus", new StringProperty());
            changeTrackingFields(modelImpl);

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("approvalTitle", new StringProperty());
        }

        return modelImpl;
    }


}
