package org.openmrs.module.labmanagement.web.resource;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.*;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.web.SampleTestRequestItemRepresentation;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.*;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-request-item", supportedClass = TestRequestItemDTO.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestRequestItemResource extends ResourceBase<TestRequestItemDTO> {



    @Override
    public TestRequestItemDTO getByUniqueId(String uniqueId) {
        TestRequestItemSearchFilter filter = new TestRequestItemSearchFilter();
        filter.setVoided(null);
        filter.setTestRequestItemUuid(uniqueId);
        Result<TestRequestItemDTO> result = getLabManagementService().findTestRequestItems(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(TestRequestItemDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        TestRequestItemSearchFilter filter = new TestRequestItemSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("itemLocation");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setItemLocationId(location.getId());
        }

        param = context.getParameter("testRequest");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> testRequests = new ArrayList<>();
            for (String testRequestUuid : params) {
                TestRequest testRequest = getLabManagementService().getTestRequestByUuid(testRequestUuid);
                if (testRequest != null) {
                    testRequests.add(testRequest.getId());
                }
            }
            if (testRequests.isEmpty()) {
                return emptyResult(context);
            }
            filter.setTestRequestIds(testRequests);
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

        param = context.getParameter("sort");
        if (!StringUtils.isBlank(param)) {
            filter.setSortOrders(org.openmrs.module.labmanagement.api.utils.StringUtils.parseSortOrder(param));
        }

        param = context.getParameter("includeTestSamples");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestSamples("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("worksheetInfo");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestWorksheetInfo("true".equalsIgnoreCase(param) || "1".equals(param));
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

        param = context.getParameter("includeTestResult");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestResult(("true".equalsIgnoreCase(param) || "1".equals(param)));
            param = context.getParameter("testResultApprovals");
            if (!StringUtils.isBlank(param)) {
                filter.setIncludeTestResultApprovals("true".equalsIgnoreCase(param) || "1".equals(param));
            }
        }

        param = context.getParameter("itemMatch");
        if (!StringUtils.isBlank(param)) {
            RequestItemMatchOptions opStatus = (RequestItemMatchOptions) Enum.valueOf(RequestItemMatchOptions.class, param);
            filter.setItemMatch(opStatus);
        }

        param = context.getParameter("sample");
        if (!StringUtils.isBlank(param)) {
            Sample sample = getLabManagementService().getSampleByUuid(param);
            if(sample == null){
                return emptyResult(context);
            }
            filter.setSampleId(sample.getId());
        }

        //filter.setIncludeTestItems(context.getRepresentation() instanceof FullRepresentation);
        Result<TestRequestItemDTO> result = getLabManagementService().findTestRequestItems(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public TestRequestItemDTO newDelegate() {
        return new TestRequestItemDTO();
    }

    @Override
    public TestRequestItemDTO save(TestRequestItemDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(TestRequestItemDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("testUuid");
        description.addProperty("locationUuid");
        description.addProperty("referredOut");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        return getCreatableProperties();
    }

    @Override
    public List<Representation> getAvailableRepresentations() {
        return Arrays.asList(Representation.DEFAULT, Representation.FULL, Representation.REF, SampleTestRequestItemRepresentation.Instance);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("testUuid");
            description.addProperty("locationUuid");
            description.addProperty("referredOut");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("orderUuid");
            description.addProperty("orderNumber");
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("toLocationUuid");
            description.addProperty("toLocationName");
            description.addProperty("referredOut");
            description.addProperty("status");
            description.addProperty("referralOutOrigin");
            description.addProperty("referralOutBy");
            description.addProperty("referralOutByUuid");
            description.addProperty("referralOutByGivenName");
            description.addProperty("referralOutByMiddleName");
            description.addProperty("referralOutByFamilyName");
            description.addProperty("referralOutDate");
            description.addProperty("referralToFacilityUuid");
            description.addProperty("referralToFacilityName");
            description.addProperty("requireRequestApproval");
            description.addProperty("requestApprovalResult");
            description.addProperty("requestApprovalByUuid");
            description.addProperty("requestApprovalGivenName");
            description.addProperty("requestApprovalMiddleName");
            description.addProperty("requestApprovalFamilyName");
            description.addProperty("requestApprovalDate");
            description.addProperty("requestApprovalRemarks");
            description.addProperty("uuid");
            description.addProperty("encounterUuid");
            description.addProperty("referralOutSampleUuid");
            description.addProperty("returnCount");
            description.addProperty("completed");
            description.addProperty("completedDate");
            description.addProperty("resultDate");
            description.addProperty("testRequestUuid");
            description.addProperty("testRequestNo");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");
            description.addProperty("worksheetNo");
            description.addProperty("worksheetUuid");
            description.addProperty("permission");
        }

        if (rep instanceof DefaultRepresentation) {
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("creator");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("changedBy");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("testResult", Representation.FULL);
            description.addProperty("samples", Representation.REF);
            description.addProperty("testConcept", new NamedRepresentation("fullchildren"));
			description.addSelfLink();
		}

        if (rep instanceof RefRepresentation || rep instanceof SampleTestRequestItemRepresentation) {
            description.addProperty("uuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("status");
            description.addProperty("permission");
        }

        if(rep instanceof SampleTestRequestItemRepresentation){
            description.addProperty("urgency");
            description.addProperty("patientUuid");
            description.addProperty("patientIdentifier");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");
            description.addProperty("toLocationUuid");
            description.addProperty("toLocationName");
            description.addProperty("testRequestItemSampleUuid");

        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("locationUuid", new StringProperty());
            modelImpl.property("referredOut", new BooleanProperty());
            modelImpl.property("orderId", new IntegerProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("orderUuid", new StringProperty());
            modelImpl.property("orderNumber", new StringProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("toLocationName", new StringProperty());
            modelImpl.property("referredOut", new BooleanProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("referralOutOrigin", new StringProperty());
            modelImpl.property("referralOutByUuid", new StringProperty());
            modelImpl.property("referralOutByGivenName", new StringProperty());
            modelImpl.property("referralOutByMiddleName", new StringProperty());
            modelImpl.property("referralOutByFamilyName", new StringProperty());
            modelImpl.property("referralOutDate", new DateTimeProperty());
            modelImpl.property("referralToFacilityUuid", new StringProperty());
            modelImpl.property("referralToFacilityName", new StringProperty());
            modelImpl.property("requireRequestApproval", new BooleanProperty());
            modelImpl.property("requestApprovalResult", new StringProperty());
            modelImpl.property("requestApprovalByUuid", new StringProperty());
            modelImpl.property("requestApprovalBy", new IntegerProperty());
            modelImpl.property("requestApprovalGivenName", new StringProperty());
            modelImpl.property("requestApprovalMiddleName", new StringProperty());
            modelImpl.property("requestApprovalFamilyName", new StringProperty());
            modelImpl.property("requestApprovalDate", new DateTimeProperty());
            modelImpl.property("requestApprovalRemarks", new StringProperty());
            modelImpl.property("initialSampleUuid", new StringProperty());
            modelImpl.property("finalResultUuid", new StringProperty());
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("encounterUuid", new StringProperty());
            modelImpl.property("referralOutSampleUuid", new StringProperty());
            modelImpl.property("returnCount", new IntegerProperty());
            modelImpl.property("completed", new BooleanProperty());
            modelImpl.property("completedDate", new DateTimeProperty());
            modelImpl.property("resultDate", new DateTimeProperty());
            modelImpl.property("testRequestUuid", new StringProperty());
            modelImpl.property("testRequestNo", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());
            modelImpl.property("permission", new StringProperty());

        }
        if (rep instanceof FullRepresentation) {
            modelImpl.property("creator", new IntegerProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("changedBy", new IntegerProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("samples", new ArrayProperty(new RefProperty("#/definitions/SampleGetRef")));
        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("permission", new StringProperty());
        }

        return modelImpl;
    }

    @PropertyGetter("samples")
    public List<SimpleObject> getSamples(TestRequestItemDTO test) {
        if(test.getSamples() == null) return null;
        if(test.getSamples().isEmpty()) return new ArrayList<>();
        return test.getSamples().stream().map(p->{
            SimpleObject simpleObject = new SimpleObject();
            simpleObject.add("uuid", p.getUuid());
            simpleObject.add("accessionNumber",p.getAccessionNumber());
            simpleObject.add("providedRef",p.getProvidedRef());
            simpleObject.add("externalRef",p.getExternalRef());
            simpleObject.add("collectionDate", p.getCollectionDate());
            simpleObject.add("testRequestItemSampleUuid",p.getTestRequestItemSampleUuid());
            return simpleObject;
        }).collect(Collectors.toList());
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(TestRequestItemDTO testRequestItemDTO) {
        Boolean hasCollectionPermission = (Boolean) testRequestItemDTO.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT,null);
        Boolean canViewTestResults = (Boolean) testRequestItemDTO.getRequestContextItems().getOrDefault(Privileges.APP_LABMANAGEMENT_TESTRESULTS,null);
        Boolean canEditTestResults = (Boolean) testRequestItemDTO.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE,null);
        int editResultTimeout = (int) testRequestItemDTO.getRequestContextItems().getOrDefault("TestResultEditTimeout", 0);;
        Boolean hasApprovePermission = (Boolean) testRequestItemDTO.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE,null);
        if(hasApprovePermission == null){
            User user = Context.getAuthenticatedUser();
            hasApprovePermission =  setRequestContextValue(testRequestItemDTO.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE,  user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE));
            hasCollectionPermission = setRequestContextValue(testRequestItemDTO.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT,user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_SAMPLES_COLLECT));
            canEditTestResults = setRequestContextValue(testRequestItemDTO.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE,user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE));
            canViewTestResults = setRequestContextValue(testRequestItemDTO.getRequestContextItems(), Privileges.APP_LABMANAGEMENT_TESTRESULTS,user.hasPrivilege(Privileges.APP_LABMANAGEMENT_TESTRESULTS));
            editResultTimeout = setRequestContextValue(testRequestItemDTO.getRequestContextItems(), "TestResultEditTimeout" ,GlobalProperties.getTestResultEditTimeout());
        }
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canEdit", false);
        simpleObject.add("canApprove", hasApprovePermission && TestRequestItemStatus.canApprove(testRequestItemDTO.getStatus()));
        simpleObject.add("canReject", hasApprovePermission && TestRequestItemStatus.canReject(testRequestItemDTO.getStatus()));
        simpleObject.add("canDoSampleCollection", hasCollectionPermission && TestRequestItemStatus.canDoSampleCollection(testRequestItemDTO.getStatus()));
        simpleObject.add("canView", true);
        simpleObject.add("canViewTestResults", canViewTestResults);
        simpleObject.add("canEditTestResults", canEditTestResults &&
                TestResultDTO.canUpdateTestResult(testRequestItemDTO, editResultTimeout));
        if(!canViewTestResults && !canEditTestResults){
            testRequestItemDTO.setTestResult(null);
        }
        return simpleObject;
    }
}
