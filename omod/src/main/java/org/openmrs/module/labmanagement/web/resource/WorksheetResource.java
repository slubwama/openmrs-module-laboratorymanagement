package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.Worksheet;
import org.openmrs.module.labmanagement.api.model.WorksheetItemStatus;
import org.openmrs.module.labmanagement.api.model.WorksheetStatus;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/worksheet", supportedClass = WorksheetDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class WorksheetResource extends ResourceBase<WorksheetDTO> {



    @Override
    public WorksheetDTO getByUniqueId(String uniqueId) {
        WorksheetSearchFilter filter = new WorksheetSearchFilter();
        filter.setVoided(null);
        filter.setWorksheetUuid(uniqueId);
        filter.setAllItems(true);
        Result<WorksheetDTO> result = getLabManagementService().findWorksheets(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(WorksheetDTO delegate, String reason, RequestContext context) throws ResponseException {
        getLabManagementService().deleteWorksheet(delegate.getId(), reason);
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        WorksheetSearchFilter filter = new WorksheetSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        filter.setStartIndex(context.getStartIndex());
        filter.setLimit(context.getLimit());
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);


        param = context.getParameter("allItems");
        if (!StringUtils.isBlank(param)) {
            filter.setAllItems(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("includeWorksheetItemTestResult");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeWorksheetItemTestResult(("true".equalsIgnoreCase(param) || "1".equals(param)));

            param = context.getParameter("testResultApprovals");
            if (!StringUtils.isBlank(param)) {
                filter.setIncludeTestItemTestResultApprovals("true".equalsIgnoreCase(param) || "1".equals(param));
            }
        }

        param = context.getParameter("includeWorksheetItemConcept");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeWorksheetItemConcept(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("includeTestResultId");
        if (!StringUtils.isBlank(param)) {
            filter.setIncludeTestResultIds(("true".equalsIgnoreCase(param) || "1".equals(param)));
        }

        param = context.getParameter("worksheet");
        if (!StringUtils.isBlank(param)) {
            Worksheet worksheet = getLabManagementService().getWorksheetByUuid(param);
            if (worksheet == null) {
                return emptyResult(context);
            }
            filter.setWorksheetId(worksheet.getId());
        }

        param = context.getParameter("itemLocation");
        if (!StringUtils.isBlank(param)) {
            Location location = Context.getLocationService().getLocationByUuid(param);
            if(location == null){
                return emptyResult(context);
            }
            filter.setAtLocationId(location.getId());
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
                filter.setTestConceptForWorksheetOnly(("true".equalsIgnoreCase(param) || "1".equals(param)));
            }
        }

        param = context.getParameter("status");
        if (!StringUtils.isBlank(param)) {
            String[] params = param.split(",", 10);
            List<WorksheetStatus> statusIds = new ArrayList<>();
            for (String status : params) {
                WorksheetStatus opStatus = (WorksheetStatus) Enum.valueOf(WorksheetStatus.class, status);
                statusIds.add(opStatus);
            }
            if (statusIds.isEmpty()) {
                return emptyResult(context);
            }
            filter.setWorksheetStatuses(statusIds);
        }

        param = context.getParameter("itemStatus");
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

        param = context.getParameter("responsiblePersonUserId");
        if (!StringUtils.isBlank(param)) {
            User user = Context.getUserService().getUserByUuid(param);
            if (user == null) {
                return emptyResult(context);
            }
            filter.setPatientId(user.getId());
        }

        param = context.getParameter("sort");
        if (!StringUtils.isBlank(param)) {
            filter.setSortOrders(org.openmrs.module.labmanagement.api.utils.StringUtils.parseSortOrder(param));
        }

        filter.setIncludeWorksheetItems(context.getRepresentation() instanceof FullRepresentation);
        if(!filter.getIncludeWorksheetItems()){
            param = context.getParameter("tests");
            if (!StringUtils.isBlank(param)) {
                filter.setTestConceptForWorksheetOnly(("true".equalsIgnoreCase(param) || "1".equals(param)));
            }
        }
        Result<WorksheetDTO> result = getLabManagementService().findWorksheets(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
       return doSearch(context);
    }

    @Override
    public WorksheetDTO newDelegate() {
        return new WorksheetDTO();
    }

    @Override
    public WorksheetDTO save(WorksheetDTO delegate) {
        Worksheet worksheet = getLabManagementService().saveWorksheet(delegate);
        return getByUniqueId(worksheet.getUuid());
    }

    @Override
    public void purge(WorksheetDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("atLocationUuid");
        description.addProperty("worksheetDate");
        description.addProperty("remarks");
        description.addProperty("testUuid");
        description.addProperty("diagnosisTypeUuid");
        description.addProperty("responsiblePersonUuid");
        description.addProperty("responsiblePersonOther");
        description.addProperty("worksheetItems");
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
            description.addProperty("atLocationUuid");
            description.addProperty("atLocationName");
            description.addProperty("worksheetDate");
            description.addProperty("worksheetNo");
            description.addProperty("remarks");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("diagnosisTypeUuid");
            description.addProperty("diagnosisTypeName");
            description.addProperty("status");
            description.addProperty("responsiblePersonUuid");
            description.addProperty("responsiblePersonGivenName");
            description.addProperty("responsiblePersonMiddleName");
            description.addProperty("responsiblePersonFamilyName");
            description.addProperty("responsiblePersonOther");
            description.addProperty("creatorUuid");
            description.addProperty("creatorGivenName");
            description.addProperty("creatorFamilyName");
            description.addProperty("dateCreated");
            description.addProperty("changedByUuid");
            description.addProperty("changedByGivenName");
            description.addProperty("changedByFamilyName");
            description.addProperty("dateChanged");
            description.addProperty("permission");

        }

        if (rep instanceof DefaultRepresentation){
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation){
            description.addProperty("worksheetItems", Representation.FULL);
            description.addSelfLink();
        }

        if(rep instanceof RefRepresentation){
            description.addProperty("uuid");
            description.addProperty("worksheetNo");
        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("atLocationUuid", new StringProperty());
            modelImpl.property("atLocationName", new StringProperty());
            modelImpl.property("worksheetDate", new DateTimeProperty());
            modelImpl.property("worksheetNo", new StringProperty());
            modelImpl.property("remarks", new StringProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("diagnosisTypeUuid", new StringProperty());
            modelImpl.property("diagnosisTypeName", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("responsiblePersonUuid", new StringProperty());
            modelImpl.property("responsiblePersonGivenName", new StringProperty());
            modelImpl.property("responsiblePersonMiddleName", new StringProperty());
            modelImpl.property("responsiblePersonFamilyName", new StringProperty());
            modelImpl.property("responsiblePersonOther", new StringProperty());
            modelImpl.property("creatorUuid", new StringProperty());
            modelImpl.property("creatorGivenName", new StringProperty());
            modelImpl.property("creatorFamilyName", new StringProperty());
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("changedByUuid", new StringProperty());
            modelImpl.property("changedByGivenName", new StringProperty());
            modelImpl.property("changedByFamilyName", new StringProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());
            modelImpl.property("permission", new StringProperty());

        }
        if (rep instanceof DefaultRepresentation) {

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("worksheetItems", new ArrayProperty());
        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("worksheetNo", new StringProperty());

        }

        return modelImpl;
    }

    @PropertySetter("worksheetItems")
    public void setItems(WorksheetDTO instance, ArrayList<Map<String, ?>> items) {
        instance.setWorksheetItems(getWorksheetItems(items));
    }

    private List<WorksheetItemDTO> getWorksheetItems(ArrayList<Map<String, ?>> items){
        if (items == null) {
            return null;
        }
        if (items.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> allowedFields = Arrays.asList("testRequestItemSampleUuid");
        List<WorksheetItemDTO> itemsToUpdate = new ArrayList<>();
        for (Map<String, ?> item : items) {
            WorksheetItemDTO itemDTO = new WorksheetItemDTO();
            for (String prop : allowedFields) {
                if (item.containsKey(prop) && !RestConstants.PROPERTY_FOR_TYPE.equals(prop)) {
                    this.setProperty(itemDTO, prop, item.get(prop));
                }
            }
            itemsToUpdate.add(itemDTO);
        }
        return itemsToUpdate;
    }

    @PropertyGetter("permission")
    public SimpleObject getPermission(WorksheetDTO worksheet) {
        Boolean canEditWorksheet = (Boolean) worksheet.getRequestContextItems().getOrDefault(Privileges.TASK_LABMANAGEMENT_WORKSHEETS_MUTATE,null);
        if(canEditWorksheet == null){
            User user = Context.getAuthenticatedUser();
            canEditWorksheet = setRequestContextValue(worksheet.getRequestContextItems(), Privileges.TASK_LABMANAGEMENT_WORKSHEETS_MUTATE,user.hasPrivilege(Privileges.TASK_LABMANAGEMENT_WORKSHEETS_MUTATE));
        }
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.add("canEdit", canEditWorksheet && WorksheetStatus.canEditWorksheet(worksheet.getStatus()));
        return simpleObject;
    }
}
