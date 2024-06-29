package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.labmanagement.api.model.TestResult;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/worksheet-test-result", supportedClass = WorksheetTestResultDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class WorksheetTestResultResource extends ResourceBase<WorksheetTestResultDTO> {

    @Override
    public WorksheetTestResultDTO getByUniqueId(String uniqueId) {
        return null;
    }

    @Override
    protected void delete(WorksheetTestResultDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public WorksheetTestResultDTO newDelegate() {
        return new WorksheetTestResultDTO();
    }

    @Override
    public WorksheetTestResultDTO save(WorksheetTestResultDTO delegate) {
        getLabManagementService().saveWorksheetTestResults(delegate);
        WorksheetTestResultDTO resultDTO = new WorksheetTestResultDTO();
        resultDTO.setWorksheetUuid(delegate.getWorksheetUuid());
        resultDTO.setUuid(delegate.getWorksheetUuid());
        return resultDTO;
    }

    @Override
    public void purge(WorksheetTestResultDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }


    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("testResults");
        description.addProperty("worksheetUuid");
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
        }

        if (rep instanceof DefaultRepresentation){

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation){

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
        }

        if(rep instanceof RefRepresentation){
            modelImpl.property("uuid", new StringProperty());
        }

        return modelImpl;
    }

    @PropertySetter("testResults")
    public void setTestResults(WorksheetTestResultDTO instance, ArrayList<Map<String, ?>> items) {
        if (items == null) {
            instance.setTestResults(null);
            return;
        }
        if (items.isEmpty()) {
            instance.setTestResults(new ArrayList<>());
            return;
        }

        TestResultResource handler = new TestResultResource();
        DelegatingResourceDescription creatableProperties = handler.getCreatableProperties();

        List<TestResultDTO> itemsToUpdate = new ArrayList<>();

        // Some resources do not have delegating resource description
        /*LabManagementService labManagementService = getLabManagementService();*/
        for (Map<String, ?> item : items) {
            TestResultDTO itemDTO = new TestResultDTO();
            /*String obsUuid = null;
            TestResult testResult = null;
            if(item.containsKey("worksheetItemUuid")){
                Object object = item.get("worksheetItemUuid");
                if(object != null && StringUtils.isNotBlank((String) object)){
                    List<TestResult> testResults = labManagementService.getTestResultByWorksheetItem((String)object);
                    if(!testResults.isEmpty()){
                        testResult = testResults.get(0);
                        obsUuid = testResult.getObs().getUuid();
                    }
                }
            }
            if(itemDTO.getObs() == null){
                if(item.containsKey("testRequestItemSampleUuid")){
                    Object object = item.get("testRequestItemSampleUuid");
                    if(object != null && StringUtils.isNotBlank((String) object)){
                        List<TestResult> testResults = labManagementService.getTestResultByTestRequestItemSample((String)object);
                        if(!testResults.isEmpty()){
                            testResult = testResults.get(0);
                            obsUuid = testResult.getObs().getUuid();
                        }
                    }
                }
            }*/
            for (Map.Entry<String, DelegatingResourceDescription.Property> prop : creatableProperties.getProperties().entrySet()) {
                if (item.containsKey(prop.getKey()) && !RestConstants.PROPERTY_FOR_TYPE.equals(prop.getKey())) {
                    Object object = item.get(prop.getKey());
                    /*if(prop.getKey().equals("obs") && object != null && obsUuid != null){
                        try {
                            Map<String, Object> objRecord = (Map<String, Object>) object;
                            objRecord.put("uuid", obsUuid);
                            if(testResult != null){
                                handler.setProperty(testResult, prop.getKey(), object);
                                itemDTO.setObs(testResult.getObs());
                            }else{
                                handler.setProperty(itemDTO, prop.getKey(), object);
                            }
                        }catch (Exception exception){
                            handler.setProperty(itemDTO, prop.getKey(), object);
                            log.error(exception);
                        }
                    }else{*/
                        handler.setProperty(itemDTO, prop.getKey(), object);
                    /*}*/

                }
            }
            itemsToUpdate.add(itemDTO);
        }
        instance.setTestResults(itemsToUpdate);
    }

}
