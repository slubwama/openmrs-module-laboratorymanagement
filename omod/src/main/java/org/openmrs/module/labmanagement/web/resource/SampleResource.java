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
import org.openmrs.module.labmanagement.api.model.Sample;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.TestRequest;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.Pair;
import org.openmrs.module.labmanagement.web.SampleTestRequestItemRepresentation;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/sample", supportedClass = SampleDTO.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class SampleResource extends ResourceBase<SampleDTO> {

    @Override
    public SampleDTO getByUniqueId(String uniqueId) {

        SampleSearchFilter filter = new SampleSearchFilter();
        filter.setVoided(null);
        filter.setSampleUuid(uniqueId);
        Result<SampleDTO> result = getLabManagementService().findSamples(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(SampleDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteSampleByUuid(delegate.getUuid());
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        SampleSearchFilter filter = new SampleSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("sample");
        if (!StringUtils.isBlank(param)) {
            filter.setSampleUuid(param);
        }

        param = context.getParameter("tests");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTests("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("patient");
        if (!StringUtils.isBlank(param)) {
            Patient patient = Context.getPatientService().getPatientByUuid(param);
            if (patient == null) {
                return emptyResult(context);
            }
            filter.setPatientId(patient.getPatientId());
        }

        param = context.getParameter("sampleType");
        if (!StringUtils.isBlank(param)) {
            Concept concept = Context.getConceptService().getConceptByUuid(param);
            if (concept == null) {
                return emptyResult(context);
            }
            filter.setSampleTypeId(concept.getConceptId());
        }

        param = context.getParameter("minActivatedDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            filter.setMinActivatedDate(date);
        }

        param = context.getParameter("maxActivatedDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            filter.setMaxActivatedDate(date);
        }

        param = context.getParameter("minCollectionDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            filter.setMinCollectionDate(date);
        }

        param = context.getParameter("maxCollectionDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            filter.setMaxCollectionDate(date);
        }

        param = context.getParameter("sampleStatus");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<SampleStatus> statusIds = new ArrayList<>();
            for (String status : params) {
                SampleStatus opStatus = (SampleStatus) Enum.valueOf(SampleStatus.class, status);
                statusIds.add(opStatus);
            }
            if (statusIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setSampleStatuses(statusIds);
        }
        param = context.getParameter("testRequestItemStatuses");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<TestRequestItemStatus> statusIds = new ArrayList<>();
            for (String status : params) {
                TestRequestItemStatus opStatus = (TestRequestItemStatus) Enum.valueOf(TestRequestItemStatus.class, status);
                statusIds.add(opStatus);
            }
            if (statusIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setTestRequestItemStatuses(statusIds);
        }


        param = context.getParameter("testRequest");
        if (!StringUtils.isBlank(param)) {
            TestRequest testRequest = getLabManagementService().getTestRequestByUuid(param);
            if(testRequest == null){
                return emptyResult(context);
            }
            filter.setTestRequestId(testRequest.getId());
        }

        param = context.getParameter("testConcept");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> conceptIds = new ArrayList<>();
            for (String conceptUuid : params) {
                Concept concept = Context.getConceptService().getConceptByUuid(conceptUuid);
                if (concept != null) {
                    conceptIds.add(concept.getId());
                }
            }
            if (conceptIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setTestRequestItemConceptIds(conceptIds);
        }

        param = context.getParameter("reference");
        if (!StringUtils.isBlank(param)) {
            filter.setReference(param);
        }

        param = context.getParameter("storage");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeSamplesInStorage("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("forWorksheet");
        if (!StringUtils.isBlank(param)) {
            filter.setForWorksheet("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("referenceOrForWorksheet");
        if (!StringUtils.isBlank(param)) {
            filter.setReferenceOrForWorksheet("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("allTests");
        if (!StringUtils.isBlank(param)) {
            filter.setAllTests("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("location");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setLocationId(location.getId());
        }

        param = context.getParameter("testItemlocation");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setTestItemlocationId(location.getId());
        }

        param = context.getParameter("sort");
        if (!StringUtils.isBlank(param)) {
            filter.setSortOrders(org.openmrs.module.labmanagement.api.utils.StringUtils.parseSortOrder(param));
        }

        param = context.getParameter("urgency");
        if (!StringUtils.isBlank(param)) {
            Order.Urgency urgency = (Order.Urgency) Enum.valueOf(Order.Urgency.class, param);
            filter.setUrgency(urgency);
        }

        Result<SampleDTO> result = getLabManagementService().findSamples(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public SampleDTO newDelegate() {
        return new SampleDTO();
    }

    @Override
    public SampleDTO save(SampleDTO delegate) {
        Pair<Sample, Map<Integer, String>> sample = getLabManagementService().saveSample(delegate);
        if(sample.getValue2() != null){
            getLabManagementService().updateOrderInstructions(sample.getValue2());
        }
        return getByUniqueId(sample.getValue1().getUuid());
    }

    @Override
    public void purge(SampleDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("sampleTypeUuid");
        description.addProperty("accessionNumber");
        description.addProperty("providedRef");
        description.addProperty("externalRef");
        description.addProperty("tests");
        description.addProperty("atLocationUuid");
        description.addProperty("containerTypeUuid");
        description.addProperty("containerCount");
        description.addProperty("volume");
        description.addProperty("volumeUnitUuid");
        description.addProperty("referredOut");
        description.addProperty("testRequestUuid");
        description.addProperty("referralToFacilityUuid");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("sampleTypeUuid");
        description.addProperty("accessionNumber");
        description.addProperty("providedRef");
        description.addProperty("externalRef");
        description.addProperty("tests");
        description.addProperty("containerTypeUuid");
        description.addProperty("containerCount");
        description.addProperty("volume");
        description.addProperty("volumeUnitUuid");
        description.addProperty("referredOut");
        description.addProperty("referralToFacilityUuid");
        return description;
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation){
            description.addProperty("sampleTypeUuid");
            description.addProperty("accessionNumber");
            description.addProperty("externalRef");
            description.addProperty("parentSampleUuid");
            description.addProperty("sampleTypeName");
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("containerTypeUuid");
            description.addProperty("containerTypeName");
            description.addProperty("volume");
            description.addProperty("volumeUnitUuid");
            description.addProperty("volumeUnitName");
            description.addProperty("collectedByUuid");
            description.addProperty("collectedByGivenName");
            description.addProperty("collectedByMiddleName");
            description.addProperty("collectedByFamilyName");
            description.addProperty("collectionDate");
            description.addProperty("containerCount");
            description.addProperty("providedRef");
            description.addProperty("referredOut");
            description.addProperty("referralOutOrigin");
            description.addProperty("referralOutBy");
            description.addProperty("referralOutByUuid");
            description.addProperty("referralOutByGivenName");
            description.addProperty("referralOutByMiddleName");
            description.addProperty("referralOutByFamilyName");
            description.addProperty("referralOutDate");
            description.addProperty("referralToFacilityUuid");
            description.addProperty("referralToFacilityName");
            description.addProperty("currentSampleActivityUuid");
            description.addProperty("status");
            description.addProperty("encounterUuid");
            description.addProperty("testRequestUuid");
            description.addProperty("uuid");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");
            description.addProperty("patientUuid");
            description.addProperty("patientIdentifier");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");
            description.addProperty("referralFromFacilityUuid");
            description.addProperty("referralFromFacilityName");
            description.addProperty("referralInExternalRef");
            description.addProperty("permission");
            description.addProperty("testRequestNo");
            description.addProperty("storageUnitUuid");
            description.addProperty("storageUnitName");
            description.addProperty("storageUuid");
            description.addProperty("storageName");
        }

		if (rep instanceof DefaultRepresentation){
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

		if (rep instanceof FullRepresentation){
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("testRequestItemSampleUuid");
            description.addProperty("tests", SampleTestRequestItemRepresentation.Instance);
            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
            description.addProperty("accessionNumber");
            description.addProperty("providedRef");
            description.addProperty("externalRef");
            description.addProperty("status");
            description.addProperty("testRequestItemSampleUuid");
        }

        return description;
    }

@Override
	public Model getGETModel(Representation rep) {
		ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("sampleTypeUuid", new StringProperty());
            modelImpl.property("accessionNumber", new StringProperty());
            modelImpl.property("externalRef", new StringProperty());
            modelImpl.property("parentSampleUuid", new StringProperty());
            modelImpl.property("sampleTypeName", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("containerTypeUuid", new StringProperty());
            modelImpl.property("containerTypeName", new StringProperty());
            modelImpl.property("volume", new DecimalProperty());
            modelImpl.property("volumeUnitUuid", new StringProperty());
            modelImpl.property("volumeUnitName", new StringProperty());
            modelImpl.property("collectedByUuid", new IntegerProperty());
            modelImpl.property("collectedByGivenName", new StringProperty());
            modelImpl.property("collectedByMiddleName", new StringProperty());
            modelImpl.property("collectedByFamilyName", new StringProperty());
            modelImpl.property("collectionDate", new DateTimeProperty());
            modelImpl.property("containerCount", new IntegerProperty());
            modelImpl.property("providedRef", new StringProperty());
            modelImpl.property("referredOut", new BooleanProperty());
            modelImpl.property("referralOutOrigin", new StringProperty());
            modelImpl.property("referralOutByUuid", new StringProperty());
            modelImpl.property("referralOutByGivenName", new StringProperty());
            modelImpl.property("referralOutByMiddleName", new StringProperty());
            modelImpl.property("referralOutByFamilyName", new StringProperty());
            modelImpl.property("referralOutDate", new DateTimeProperty());
            modelImpl.property("referralToFacilityUuid", new StringProperty());
            modelImpl.property("referralToFacilityName", new StringProperty());
            modelImpl.property("currentSampleActivityUuid", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("encounterUuid", new StringProperty());
            modelImpl.property("testRequestUuid", new StringProperty());
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());
            modelImpl.property("patientUuid", new StringProperty());
            modelImpl.property("patientIdentifier", new StringProperty());
            modelImpl.property("patientGivenName", new StringProperty());
            modelImpl.property("patientMiddleName", new StringProperty());
            modelImpl.property("patientFamilyName", new StringProperty());
            modelImpl.property("referralFromFacilityUuid", new StringProperty());
            modelImpl.property("referralInExternalRef", new StringProperty());
            modelImpl.property("referralFromFacilityName", new StringProperty());
            modelImpl.property("testRequestNo", new StringProperty());
            modelImpl.property("storageUnitUuid", new StringProperty());
            modelImpl.property("storageUnitName", new StringProperty());
            modelImpl.property("storageUuid", new StringProperty());
            modelImpl.property("storageName", new StringProperty());
		}

		if (rep instanceof FullRepresentation) {
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("testRequestItemSampleUuid", new StringProperty());
            modelImpl.property("tests", new ArrayProperty());
        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("accessionNumber", new StringProperty());
            modelImpl.property("providedRef", new StringProperty());
            modelImpl.property("externalRef", new StringProperty());
            modelImpl.property("testRequestItemSampleUuid", new StringProperty());
        }

        return modelImpl;
    }

    @PropertySetter("volume")
    public void setVolume(SampleDTO instance, Double value) {
        if (value == null) {
            instance.setVolume(null);
        } else {
            instance.setVolume(BigDecimal.valueOf(value));
        }
    }

    @PropertySetter("tests")
    public void setSamplesTestItems(SampleDTO instance, ArrayList<String> items) {
        if (items == null) {
            instance.setSampleTestItemUuids(null);
            return;
        }
        if (items.isEmpty()) {
            instance.setSampleTestItemUuids(new HashSet<>());
            return;
        }
        instance.setSampleTestItemUuids(new HashSet<>(items));
    }

    @PropertyGetter("tests")
    public List<SimpleObject> getTestItems(SampleDTO sample) {
        if(sample.getTests() == null) return null;
        if(sample.getTests().isEmpty()) return new ArrayList<>();
        return sample.getTests().stream().map(p->{
            SimpleObject simpleObject = new SimpleObject();
            simpleObject.add("uuid", p.getUuid());
            simpleObject.add("testName",p.getTestName());
            simpleObject.add("testShortName",p.getTestShortName());
            simpleObject.add("status", p.getStatus());
            simpleObject.add("urgency", p.getUrgency());
            simpleObject.add("orderNumber", p.getOrderNumber());
            simpleObject.add("patientUuid", p.getPatientUuid());
            simpleObject.add("patientIdentifier", p.getPatientIdentifier());
            simpleObject.add("patientGivenName", p.getPatientGivenName());
            simpleObject.add("patientMiddleName", p.getPatientMiddleName());
            simpleObject.add("patientFamilyName", p.getPatientFamilyName());
            simpleObject.add("toLocationUuid", p.getToLocationUuid());
            simpleObject.add("toLocationName", p.getToLocationName());
            simpleObject.add("testRequestItemSampleUuid", p.getTestRequestItemSampleUuid());

            return simpleObject;
        }).collect(Collectors.toList());
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(SampleDTO sampleDTO) {
        Boolean hasEditPermission = (Boolean) sampleDTO.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_SAMPLES_MUTATE,null);
        Boolean hasCollectionPermission = (Boolean) sampleDTO.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT,null);
        if(hasEditPermission == null){
            User user = Context.getAuthenticatedUser();
            hasEditPermission = setRequestContextValue(sampleDTO.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_SAMPLES_MUTATE,  user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_SAMPLES_MUTATE));
            hasCollectionPermission = setRequestContextValue(sampleDTO.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT,  user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT));
        }

        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canEdit", hasEditPermission && SampleStatus.canEdit(sampleDTO));
        simpleObject.add("canDelete", hasEditPermission && SampleStatus.canDeleteSampleWithStatus(sampleDTO.getStatus()));
        simpleObject.add("canReleaseForTesting", hasCollectionPermission && SampleStatus.canReleaseForTesting(sampleDTO));
        simpleObject.add("canView", sampleDTO != null);
        return simpleObject;
    }
}
