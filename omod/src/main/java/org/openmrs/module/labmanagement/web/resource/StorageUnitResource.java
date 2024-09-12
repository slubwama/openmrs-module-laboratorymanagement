package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import io.swagger.models.properties.*;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.IllegalRequestException;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/storage-unit", supportedClass = StorageUnitDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class StorageUnitResource extends ResourceBase<StorageUnitDTO> {

    @Override
    public StorageUnitDTO getByUniqueId(String uniqueId) {
        StorageSearchFilter filter = new StorageSearchFilter();
        filter.setVoided(null);
        filter.setStorageUnitUuId(uniqueId);
        Result<StorageUnitDTO> result = getLabManagementService().findStorageUnits(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(StorageUnitDTO delegate, String reason, RequestContext context) throws ResponseException {
         throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        StorageSearchFilter filter = new StorageSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("storage");
        if (!StringUtils.isBlank(param)) {
            filter.setStorageUuId(param);
        }

        param = context.getParameter("storageUnit");
        if (!StringUtils.isBlank(param)) {
            filter.setStorageUnitUuId(param);
        }

        param = context.getParameter("active");
        if (!StringUtils.isBlank(param)) {
            filter.setActive("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("assigned");
        if (!StringUtils.isBlank(param)) {
            filter.setAssigned("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        Result<StorageUnitDTO> result = getLabManagementService().findStorageUnits(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
         return doSearch(context);
    }

    @Override
    public StorageUnitDTO newDelegate() {
        return new StorageUnitDTO();
    }

    @Override
    public StorageUnitDTO save(StorageUnitDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(StorageUnitDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation){
			description.addProperty("uuid");
            description.addProperty("storageUuid");
            description.addProperty("storageName");
            description.addProperty("unitName");
            description.addProperty("description");
            description.addProperty("active");
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
		}

		if (rep instanceof DefaultRepresentation){
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}

		if (rep instanceof FullRepresentation){
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
            description.addProperty("changedBy");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("dateChanged");
			description.addSelfLink();
		}

		if(rep instanceof RefRepresentation){
			description.addProperty("uuid");
		}

        return description;
    }

@Override
	public Model getGETModel(Representation rep) {
		ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("storageId", new IntegerProperty());
            modelImpl.property("storageUuid", new StringProperty());
            modelImpl.property("storageName", new StringProperty());
            modelImpl.property("unitName", new StringProperty());
            modelImpl.property("description", new StringProperty());
            modelImpl.property("active", new BooleanProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
        }

		if (rep instanceof FullRepresentation) {
            modelImpl.property("creator", new IntegerProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("changedBy", new IntegerProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());
		}

		if(rep instanceof RefRepresentation){
			 modelImpl.property("uuid", new StringProperty());
		}

		return modelImpl;
	}




}
