package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.Worksheet;
import org.openmrs.module.labmanagement.api.model.WorksheetItem;
import org.openmrs.module.labmanagement.api.model.WorksheetItemStatus;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.util.ReflectionUtil;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.*;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingConverter;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/worksheet-item", supportedClass = WorksheetItemDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class WorksheetItemResource extends ResourceBase<WorksheetItemDTO> {


    @Override
    public WorksheetItemDTO getByUniqueId(String uniqueId) {

        WorksheetItemSearchFilter filter = new WorksheetItemSearchFilter();
        filter.setVoided(null);
        filter.setWorksheetItemUuid(uniqueId);
        Result<WorksheetItemDTO> result = getLabManagementService().findWorksheetItems(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(WorksheetItemDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteWorksheetItem(delegate.getWorksheetId(), reason);
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        WorksheetItemSearchFilter filter = new WorksheetItemSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("worksheetItem");
        if (!StringUtils.isBlank(param)) {
            WorksheetItem worksheetItem = getLabManagementService().getWorksheetItemByUuid(param);
            if (worksheetItem == null) {
                return emptyResult(context);
            }
            filter.setWorksheetItemId(worksheetItem.getId());
        }

        param = context.getParameter("itemLocation");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setItemLocationId(location.getId());
        }

        param = context.getParameter("worksheet");
        if (StringUtils.isNotBlank(param)) {
            String[] params = param.split(",", 10);
            List<Integer> worksheetIds = new ArrayList<>();
            for (String conceptUuid : params) {
                Worksheet conworksheetept = getLabManagementService().getWorksheetByUuid(conceptUuid);
                if (conworksheetept != null) {
                    worksheetIds.add(conworksheetept.getId());
                }
            }
            if (worksheetIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setWorksheetIds(worksheetIds);
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

        param = context.getParameter("status");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<WorksheetItemStatus> statusIds = new ArrayList<>();
            for (String status : params) {
                WorksheetItemStatus opStatus = (WorksheetItemStatus) Enum.valueOf(WorksheetItemStatus.class, status);
                statusIds.add(opStatus);
            }
            if (statusIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setWorksheetItemStatuses(statusIds);
        }

        param = context.getParameter("sampleRef");
        if (!StringUtils.isBlank(param)) {
            filter.setSampleRef(param);
        }

        param = context.getParameter("urgency");
        if (!StringUtils.isBlank(param)) {
            filter.setUrgency((Order.Urgency) Enum.valueOf(Order.Urgency.class, param));
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

        Result<WorksheetItemDTO> result = getLabManagementService().findWorksheetItems(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public WorksheetItemDTO newDelegate() {
        return new WorksheetItemDTO();
    }

    @Override
    public WorksheetItemDTO save(WorksheetItemDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(WorksheetItemDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation){
            description.addProperty("uuid");
            description.addProperty("worksheetId");
            description.addProperty("sampleUuid");
            description.addProperty("testRequestItemSampleUuid");
            description.addProperty("sampleProvidedRef");
            description.addProperty("sampleAccessionNumber");
            description.addProperty("sampleExternalRef");
            description.addProperty("status");
            description.addProperty("completedDate");
            description.addProperty("cancelledDate");
            description.addProperty("cancellationRemarks");
            description.addProperty("testRequestItemUuid");
            description.addProperty("orderUuid");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("testRequestNo");
            description.addProperty("orderNumber");

            description.addProperty("urgency");
            description.addProperty("patientUuid");
            description.addProperty("patientIdentifier");
            description.addProperty("patientGivenName");
            description.addProperty("patientMiddleName");
            description.addProperty("patientFamilyName");
            description.addProperty("referralFromFacilityUuid");
            description.addProperty("referralFromFacilityName");
            description.addProperty("referralInExternalRef");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("dateChanged");

        }

        if (rep instanceof DefaultRepresentation){

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation){
            description.addProperty("sampleTypeUuid");
            description.addProperty("sampleTypeName");
            description.addProperty("toLocationUuid");
            description.addProperty("toLocationName");
            description.addProperty("sampleContainerTypeUuid");
            description.addProperty("sampleContainerTypeName");
            description.addProperty("sampleVolume");
            description.addProperty("sampleVolumeUnitUuid");
            description.addProperty("sampleVolumeUnitName");
            description.addProperty("sampleCollectedByUuid");
            description.addProperty("sampleCollectedByGivenName");
            description.addProperty("sampleCollectedByMiddleName");
            description.addProperty("sampleCollectedByFamilyName");
            description.addProperty("sampleCollectionDate");
            description.addProperty("sampleContainerCount");
            description.addProperty("testRequestItemSampleUuid");


            description.addProperty("testResult", Representation.FULL);
            description.addProperty("testConcept", new NamedRepresentation("fullchildren"));
            description.addProperty("permission");

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
            modelImpl.property("testRequestItemSampleUuid", new StringProperty());
            modelImpl.property("sampleUuid", new StringProperty());
            modelImpl.property("sampleProvidedRef", new StringProperty());
            modelImpl.property("sampleAccessionNumber", new StringProperty());
            modelImpl.property("sampleExternalRef", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("completedDate", new DateTimeProperty());
            modelImpl.property("cancelledDate", new DateTimeProperty());
            modelImpl.property("cancellationRemarks", new StringProperty());
            modelImpl.property("testRequestItemUuid", new StringProperty());
            modelImpl.property("orderUuid", new StringProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());

            modelImpl.property("urgency", new StringProperty());
            modelImpl.property("patientUuid", new StringProperty());
            modelImpl.property("patientIdentifier", new StringProperty());
            modelImpl.property("patientGivenName", new StringProperty());
            modelImpl.property("patientMiddleName", new StringProperty());
            modelImpl.property("patientFamilyName", new StringProperty());

            modelImpl.property("referralFromFacilityUuid", new StringProperty());
            modelImpl.property("referralInExternalRef", new StringProperty());
            modelImpl.property("referralFromFacilityName", new StringProperty());
            modelImpl.property("permission", new StringProperty());
            modelImpl.property("testRequestNo", new StringProperty());
            modelImpl.property("orderNumber", new StringProperty());

        }
        if (rep instanceof DefaultRepresentation) {

        }

        if (rep instanceof FullRepresentation) {

        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(WorksheetItemDTO worksheetItem) {
        Boolean canViewTestResults = (Boolean) worksheetItem.getRequestContextItems().getOrDefault(Privileges.APP_LABMANAGEMENT_TESTRESULTS,null);
        Boolean canEditTestResults = (Boolean) worksheetItem.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE,null);
        int editResultTimeout = (int) worksheetItem.getRequestContextItems().getOrDefault("TestResultEditTimeout", 0);
        if(canEditTestResults == null){
            User user = Context.getAuthenticatedUser();
            canEditTestResults = setRequestContextValue(worksheetItem.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE,user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_TESTRESULTS_MUTATE));
            canViewTestResults = setRequestContextValue(worksheetItem.getRequestContextItems(), Privileges.APP_LABMANAGEMENT_TESTRESULTS,user.hasPrivilege(Privileges.APP_LABMANAGEMENT_TESTRESULTS));
            editResultTimeout = setRequestContextValue(worksheetItem.getRequestContextItems(), "TestResultEditTimeout" ,GlobalProperties.getTestResultEditTimeout());
        }
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canViewTestResults", canViewTestResults);
        simpleObject.add("canEditTestResults", canEditTestResults &&
                TestResultDTO.canUpdateTestResult(worksheetItem.getTestResult(), editResultTimeout));
        if(!canViewTestResults && !canEditTestResults){
            worksheetItem.setTestResult(null);
        }
        return simpleObject;
    }
}
