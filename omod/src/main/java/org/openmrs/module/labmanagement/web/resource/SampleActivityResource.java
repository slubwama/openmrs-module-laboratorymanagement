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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/sample-activity", supportedClass = SampleActivityDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class SampleActivityResource extends ResourceBase<SampleActivityDTO> {

    @Override
    public SampleActivityDTO getByUniqueId(String uniqueId) {
        SampleActivitySearchFilter filter = new SampleActivitySearchFilter();
        filter.setVoided(null);
        filter.setSampleActivityUuid(uniqueId);
        Result<SampleActivityDTO> result = getLabManagementService().findSampleActivities(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(SampleActivityDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        SampleActivitySearchFilter filter = new SampleActivitySearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("sample");
        if (!StringUtils.isBlank(param)) {
            Sample sample = getLabManagementService().getSampleByUuid(param);
            if (sample == null) {
                return emptyResult(context);
            }
            filter.setSampleId(sample.getId());
        } else {
            return emptyResult(context);
        }

        Result<SampleActivityDTO> result = getLabManagementService().findSampleActivities(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public SampleActivityDTO newDelegate() {
        return new SampleActivityDTO();
    }

    @Override
    public SampleActivityDTO save(SampleActivityDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(SampleActivityDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("sampleUuid");
            description.addProperty("activityType");
            description.addProperty("sourceUuid");
            description.addProperty("sourceName");
            description.addProperty("destinationUuid");
            description.addProperty("destinationName");
            description.addProperty("sourceState");
            description.addProperty("destinationState");
            description.addProperty("activityByUuid");
            description.addProperty("activityByGivenName");
            description.addProperty("activityByMiddleName");
            description.addProperty("activityByFamilyName");
            description.addProperty("remarks");
            description.addProperty("status");
            description.addProperty("volume");
            description.addProperty("volumeUnitUuid");
            description.addProperty("volumeUnitName");
            description.addProperty("thawCycles");
            description.addProperty("storageUnitUuid");
            description.addProperty("storageUnitName");
            description.addProperty("storageUuid");
            description.addProperty("storageName");
            description.addProperty("activityDate");
            description.addProperty("responsiblePersonUuid");
            description.addProperty("responsiblePersonGivenName");
            description.addProperty("responsiblePersonMiddleName");
            description.addProperty("responsiblePersonFamilyName");
            description.addProperty("responsiblePersonOther");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorMiddleName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
        }

        if (rep instanceof DefaultRepresentation) {
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("sampleUuid", new StringProperty());
            modelImpl.property("activityType", new StringProperty());
            modelImpl.property("sourceUuid", new StringProperty());
            modelImpl.property("sourceName", new StringProperty());
            modelImpl.property("destinationUuid", new StringProperty());
            modelImpl.property("destinationName", new StringProperty());
            modelImpl.property("sourceState", new StringProperty());
            modelImpl.property("destinationState", new StringProperty());
            modelImpl.property("activityByUuid", new StringProperty());
            modelImpl.property("activityByGivenName", new StringProperty());
            modelImpl.property("activityByMiddleName", new StringProperty());
            modelImpl.property("activityByFamilyName", new StringProperty());
            modelImpl.property("remarks", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("volume", new DecimalProperty());
            modelImpl.property("volumeUnitUuid", new StringProperty());
            modelImpl.property("volumeUnitName", new StringProperty());
            modelImpl.property("thawCycles", new IntegerProperty());
            modelImpl.property("storageUnitUuid", new StringProperty());
            modelImpl.property("storageUnitName", new StringProperty());
            modelImpl.property("storageUuid", new StringProperty());
            modelImpl.property("storageName", new StringProperty());
            modelImpl.property("activityDate", new DateTimeProperty());
            modelImpl.property("responsiblePersonUuid", new StringProperty());
            modelImpl.property("responsiblePersonGivenName", new StringProperty());
            modelImpl.property("responsiblePersonMiddleName", new StringProperty());
            modelImpl.property("responsiblePersonFamilyName", new StringProperty());
            modelImpl.property("responsiblePersonOther", new StringProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorMiddleName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
        }

        return modelImpl;
    }

}
