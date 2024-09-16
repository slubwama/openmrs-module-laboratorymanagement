package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.impl.ApprovalUtils;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.TestRequest;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;
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

import java.util.*;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-request", supportedClass = TestRequestDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestRequestResource extends ResourceBase<TestRequestDTO> {

    @Override
    public TestRequestDTO getByUniqueId(String uniqueId) {
        TestRequestSearchFilter filter = new TestRequestSearchFilter();
        filter.setVoided(null);
        filter.setTestRequestUuid(uniqueId);
        Result<TestRequestDTO> result = getLabManagementService().findTestRequests(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(TestRequestDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        TestRequestSearchFilter filter = new TestRequestSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("testRequest");
        if (!StringUtils.isBlank(param)) {
            TestRequest testRequest = getLabManagementService().getTestRequestByUuid(param);
            if (testRequest == null) {
                return emptyResult(context);
            }
            filter.setTestRequestId(testRequest.getId());
        }

        param = context.getParameter("itemLocation");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setItemLocationId(location.getId());
        }

        param = context.getParameter("itemStatus");
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
            filter.setItemStatuses(statusIds);
        }

        param = context.getParameter("itemMatch");
        if (!StringUtils.isBlank(param)) {
            RequestItemMatchOptions opStatus = (RequestItemMatchOptions) Enum.valueOf(RequestItemMatchOptions.class, param);
            filter.setRequestItemMatch(opStatus);
        }

        param = context.getParameter("testConcept");
        if (StringUtils.isNotBlank(param)) {
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
            filter.setTestConceptIds(conceptIds);
            param = context.getParameter("testConceptTests");
            if (!StringUtils.isBlank(param)) {
                filter.setTestConceptForRequestOnly(("true".equalsIgnoreCase(param) || "1".equals(param)));
            }
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

        param = context.getParameter("referredIn");
        if (!StringUtils.isBlank(param)) {
            filter.setReferredIn("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("referredOut");
        if (!StringUtils.isBlank(param)) {
            filter.setReferredOut("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("patient");
        if (!StringUtils.isBlank(param)) {
            Patient patient = Context.getPatientService().getPatientByUuid(param);
            if (patient == null) {
                return emptyResult(context);
            }
            filter.setPatientId(patient.getPatientId());
        }

        param = context.getParameter("allTests");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeAllTests("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("sort");
        if (!StringUtils.isBlank(param)) {
            filter.setSortOrders(org.openmrs.module.labmanagement.api.utils.StringUtils.parseSortOrder(param));
        }

        filter.setIncludeTestItems(context.getRepresentation() instanceof FullRepresentation);
        if(filter.getIncludeTestItems()) {
            param = context.getParameter("includeTestItemSamples");
            if (!StringUtils.isBlank(param)) {
                filter.setIncludeTestRequestItemSamples("true".equalsIgnoreCase(param) || "1".equals(param));
            }
        }

        param = context.getParameter("samples");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestRequestSamples("true".equalsIgnoreCase(param) || "1".equals(param));
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

        param = context.getParameter("testItemSampleCriteria");
        if (!StringUtils.isBlank(param)) {
            TestItemSampleCriteria opStatus = (TestItemSampleCriteria) Enum.valueOf(TestItemSampleCriteria.class, param);
            filter.setTestItemSampleCriteria(opStatus);
        }

        param = context.getParameter("approvals");
        if (!StringUtils.isBlank(param)) {
            filter.setPendingResultApproval("true".equalsIgnoreCase(param) || "1".equals(param));
            param = context.getParameter("approvalsOnly");
            if (!StringUtils.isBlank(param)) {
                filter.setOnlyPendingResultApproval("true".equalsIgnoreCase(param) || "1".equals(param));
            }
            param = context.getParameter("approvalPerm");
            if (!StringUtils.isBlank(param)) {
                filter.setPermApproval("true".equalsIgnoreCase(param) || "1".equals(param));
            }
        }

        param = context.getParameter("includeTestItemTestResult");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestItemTestResult(("true".equalsIgnoreCase(param) || "1".equals(param)));
            param = context.getParameter("testResultApprovals");
            if (!StringUtils.isBlank(param)) {
                filter.setIncludeTestItemTestResultApprovals("true".equalsIgnoreCase(param) || "1".equals(param));
            }
        }


        param = context.getParameter("includeItemConcept");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestItemConcept(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("worksheetInfo");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestItemWorksheetInfo(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        Result<TestRequestDTO> result = getLabManagementService().findTestRequests(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public TestRequestDTO newDelegate() {
        return new TestRequestDTO();
    }

    @Override
    public TestRequestDTO save(TestRequestDTO delegate) {
        TestRequest testRequest = getLabManagementService().saveTestRequest(delegate);
        delegate.setUuid(testRequest.getUuid());
        return delegate;
    }

    @Override
    public void purge(TestRequestDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("patientUuid");
        description.addProperty("requestDate");
        description.addProperty("urgency");
        description.addProperty("careSettingUuid");
        description.addProperty("atLocationUuid");
        description.addProperty("referredIn");
        description.addProperty("referralFromFacilityUuid");
        description.addProperty("referralFromFacilityName");
        description.addProperty("referralInExternalRef");
        description.addProperty("clinicalNote");
        description.addProperty("requestReason");
        description.addProperty("providerUuid");
        description.addProperty("tests");
        description.addProperty("samples");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        throw new ResourceDoesNotSupportOperationException();
        //return getCreatableProperties();
    }


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("patientUuid");
            description.addProperty("patientIdentifier");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");
            description.addProperty("providerUuid");
            description.addProperty("providerGivenName");
            description.addProperty("providerMiddleName");
            description.addProperty("providerFamilyName");
            description.addProperty("requestDate");
            description.addProperty("requestNo");
            description.addProperty("urgency");
            description.addProperty("careSettingUuid");
            description.addProperty("careSettingName");
            description.addProperty("dateStopped");
            description.addProperty("status");
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("referredIn");
            description.addProperty("referralFromFacilityUuid");
            description.addProperty("referralFromFacilityName");
            description.addProperty("referralInExternalRef");
            description.addProperty("clinicalNote");
            description.addProperty("requestReason");
            description.addProperty("voided");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("tests",Representation.FULL);
            description.addProperty("permission");
            description.addProperty("samples",Representation.FULL);
            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
            description.addProperty("requestNo");
        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("patientUuid", new StringProperty());
            modelImpl.property("patientIdentifier", new StringProperty());
            modelImpl.property("patientGivenName", new StringProperty());
            modelImpl.property("patientMiddleName", new StringProperty());
            modelImpl.property("patientFamilyName", new StringProperty());
            modelImpl.property("providerUuid", new StringProperty());
            modelImpl.property("providerGivenName", new StringProperty());
            modelImpl.property("providerMiddleName", new StringProperty());
            modelImpl.property("providerFamilyName", new StringProperty());
            modelImpl.property("requestDate", new DateTimeProperty());
            modelImpl.property("requestNo", new StringProperty());
            modelImpl.property("urgency", new StringProperty());
            modelImpl.property("careSettingUuid", new StringProperty());
            modelImpl.property("careSettingName", new StringProperty());
            modelImpl.property("dateStopped", new DateTimeProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("referredIn", new BooleanProperty());
            modelImpl.property("referralFromFacilityUuid", new StringProperty());
            modelImpl.property("referralFromFacilityName", new StringProperty());
            modelImpl.property("referralInExternalRef", new StringProperty());
            modelImpl.property("clinicalNote", new StringProperty());
            modelImpl.property("requestReason", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());


        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("tests", new ArrayProperty());
            modelImpl.property("permission", new StringProperty());
            modelImpl.property("samples", new ArrayProperty());

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("requestNo", new StringProperty());
        }

        return modelImpl;
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(TestRequestDTO testRequest) {
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canEdit", false);
        simpleObject.add("canView", testRequest != null);
        return simpleObject;
    }

    @PropertyGetter("clinicalNote")
    public String getClinicalNote(TestRequestDTO testRequest) {
        if(testRequest == null) return null;
        if(StringUtils.isBlank(testRequest.getClinicalNote())) return testRequest.getClinicalNote();
        if(!ApprovalUtils.canViewClinicalNote(testRequest)){
            return org.openmrs.module.labmanagement.api.utils.StringUtils.MASKED_STRING;
        }
        return testRequest.getClinicalNote();
    }

    @PropertySetter("samples")
    public void setSamples(TestRequestDTO instance, ArrayList<Map<String, ?>> items) {
        if (items == null) {
            instance.setSamples(null);
            return;
        }
        if (items.isEmpty()) {
            instance.setSamples(new ArrayList<>());
            return;
        }

        List<String> allowedFields = Arrays.asList("sampleTypeUuid", "accessionNumber", "externalRef", "tests", "archive", "storageUnitUuid");
        List<TestRequestSampleDTO> itemsToUpdate = new ArrayList<>();
        for (Map<String, ?> item : items) {
            TestRequestSampleDTO itemDTO = new TestRequestSampleDTO();
            for (String prop : allowedFields) {
                if (item.containsKey(prop) && !RestConstants.PROPERTY_FOR_TYPE.equals(prop)) {
                    if(prop.equals("tests")){
                        Object value = item.get(prop);
                        if(value == null){
                            itemDTO.setTests(null);
                        }else {
                            itemDTO.setTests(getTests((ArrayList<Map<String, ?>>)value));
                        }
                    }else {
                        this.setProperty(itemDTO, prop, item.get(prop));
                    }
                }
            }
            itemsToUpdate.add(itemDTO);
        }
        instance.setSamples(itemsToUpdate);
    }

    @PropertySetter("tests")
    public void setTests(TestRequestDTO instance, ArrayList<Map<String, ?>> items) {
        instance.setTests(getTests(items));
    }

    private List<TestRequestItemDTO> getTests(ArrayList<Map<String, ?>> items){
        if (items == null) {
            return null;
        }
        if (items.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> allowedFields = Arrays.asList("testUuid", "locationUuid", "referredOut");
        List<TestRequestItemDTO> itemsToUpdate = new ArrayList<>();
        for (Map<String, ?> item : items) {
            TestRequestItemDTO itemDTO = new TestRequestItemDTO();
            for (String prop : allowedFields) {
                if (item.containsKey(prop) && !RestConstants.PROPERTY_FOR_TYPE.equals(prop)) {
                    this.setProperty(itemDTO, prop, item.get(prop));
                }
            }
            itemsToUpdate.add(itemDTO);
        }
        return itemsToUpdate;
    }

}
