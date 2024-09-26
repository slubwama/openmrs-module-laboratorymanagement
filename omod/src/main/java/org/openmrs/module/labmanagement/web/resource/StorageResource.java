package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/storage", supportedClass = StorageDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class StorageResource extends ResourceBase<StorageDTO> {

    @Override
    public StorageDTO getByUniqueId(String uniqueId) {
        StorageSearchFilter filter = new StorageSearchFilter();
        filter.setVoided(null);
        filter.setStorageUuId(uniqueId);
        Result<StorageDTO> result = getLabManagementService().findStorages(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(StorageDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteStorage(delegate.getUuid());
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

        param = context.getParameter("location");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location==null){
                return emptyResult(context);
            }
            filter.setLocationId(location.getId());
        }

        param = context.getParameter("units");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeUnits("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("active");
        if (!StringUtils.isBlank(param)) {
            filter.setActive("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        Result<StorageDTO> result = getLabManagementService().findStorages(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public StorageDTO newDelegate() {
        return new StorageDTO();
    }

    @Override
    public StorageDTO save(StorageDTO delegate) {
        Storage storage = getLabManagementService().saveStorage(delegate);
        return getByUniqueId(storage.getUuid());
    }

    @Override
    public void purge(StorageDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("name");
        description.addProperty("description");
        description.addProperty("atLocationUuid");
        description.addProperty("active");
        description.addProperty("capacity");
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
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("active");
            description.addProperty("capacity");
            description.addProperty("description");
        }

		if (rep instanceof DefaultRepresentation){
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}

		if (rep instanceof FullRepresentation){
            description.addProperty("units");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
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
            modelImpl.property("name", new StringProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("active", new BooleanProperty());
            modelImpl.property("capacity", new IntegerProperty());
            modelImpl.property("description", new StringProperty());
        }

		if (rep instanceof FullRepresentation) {
            modelImpl.property("units", new ArrayProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
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
