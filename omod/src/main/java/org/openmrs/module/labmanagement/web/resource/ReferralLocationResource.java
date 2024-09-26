package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Patient;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/referral-location", supportedClass = ReferralLocationDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class ReferralLocationResource extends ResourceBase<ReferralLocationDTO> {

    @Override
    public ReferralLocationDTO getByUniqueId(String uniqueId) {
        ReferralLocationSearchFilter filter = new ReferralLocationSearchFilter();
        filter.setVoided(null);
        filter.setReferralLocationUuid(uniqueId);
        Result<ReferralLocationDTO> result = getLabManagementService().findReferralLocations(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(ReferralLocationDTO delegate, String reason, RequestContext context) throws ResponseException {
         throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        return searchReferralLocations(context, false);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return searchReferralLocations(context, true);
    }

    private PageableResult searchReferralLocations(RequestContext context, boolean getAll){
        ReferralLocationSearchFilter filter = new ReferralLocationSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        if(!getAll) {
            filter.setStartIndex(context.getStartIndex());
            filter.setLimit(context.getLimit());
        }
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("active");
        if (!StringUtils.isBlank(param)) {
            filter.setActive("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("referrerIn");
        if (!StringUtils.isBlank(param)) {
            filter.setReferrerIn("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("referrerOut");
        if (!StringUtils.isBlank(param)) {
            filter.setReferrerOut("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("concept");
        if (!StringUtils.isBlank(param)) {
            Concept concept = Context.getConceptService().getConcept(param);
            if (concept == null) {
                return emptyResult(context);
            }
            filter.setConceptId(concept.getConceptId());
        }

        param = context.getParameter("patient");
        if (!StringUtils.isBlank(param)) {
            Patient patient = Context.getPatientService().getPatientByUuid(param);
            if (patient == null) {
                return emptyResult(context);
            }
            filter.setPatientId(patient.getPatientId());
        }

        Result<ReferralLocationDTO> result = getLabManagementService().findReferralLocations(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    public ReferralLocationDTO newDelegate() {
        return new ReferralLocationDTO();
    }

    @Override
    public ReferralLocationDTO save(ReferralLocationDTO delegate) {
        ReferralLocation referralLocationDTO = getLabManagementService().saveReferralLocation(delegate);
        return getByUniqueId(referralLocationDTO.getUuid());
    }

    @Override
    public void purge(ReferralLocationDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("uuid");
        description.addProperty("name");
        description.addProperty("acronym");
        description.addProperty("conceptUuid");
        description.addProperty("referrerIn");
        description.addProperty("referrerOut");
        description.addProperty("enabled");
        description.addProperty("system");
        description.addProperty("patientUuid");
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
            description.addProperty("name");
            description.addProperty("acronym");
            description.addProperty("conceptUuid");
            description.addProperty("conceptName");
            description.addProperty("referrerIn");
            description.addProperty("referrerOut");
            description.addProperty("enabled");
            description.addProperty("system");
            description.addProperty("patientUuid");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");
		}

		if (rep instanceof DefaultRepresentation){
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}

		if (rep instanceof FullRepresentation){
			description.addSelfLink();
		}

		if(rep instanceof RefRepresentation){
			description.addProperty("uuid");
            description.addProperty("name");
            description.addProperty("acronym");
            description.addProperty("conceptUuid");
            description.addProperty("conceptName");
            description.addProperty("patientUuid");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");

		}

        return description;
    }

@Override
	public Model getGETModel(Representation rep) {
		ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			modelImpl.property("uuid", new StringProperty());
            modelImpl.property("name", new StringProperty());
            modelImpl.property("acronym", new StringProperty());
            modelImpl.property("conceptUuid", new StringProperty());
            modelImpl.property("conceptName", new StringProperty());
            modelImpl.property("patientUuid", new StringProperty());
            modelImpl.property("patientGivenName", new StringProperty());
            modelImpl.property("patientMiddleName", new StringProperty());
            modelImpl.property("patientFamilyName", new StringProperty());
            modelImpl.property("referrerIn", new BooleanProperty());
            modelImpl.property("referrerOut", new BooleanProperty());
            modelImpl.property("enabled", new BooleanProperty());
            modelImpl.property("system", new BooleanProperty());
		}

		if(rep instanceof RefRepresentation){
			modelImpl.property("uuid", new StringProperty());
            modelImpl.property("name", new StringProperty());
            modelImpl.property("acronym", new StringProperty());
            modelImpl.property("conceptUuid", new StringProperty());
            modelImpl.property("conceptName", new StringProperty());
            modelImpl.property("patientUuid", new StringProperty());
            modelImpl.property("patientGivenName", new StringProperty());
            modelImpl.property("patientMiddleName", new StringProperty());
		}

		return modelImpl;
	}




}
