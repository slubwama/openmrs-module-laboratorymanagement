package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.*;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/dashboard-metrics", supportedClass = DashboardMetricsDTO.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class DashboardMetricsResource extends ResourceBase<DashboardMetricsDTO> {

    @Override
    public DashboardMetricsDTO getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(DashboardMetricsDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        Date minActivatedDate = null;
        Date maxActivatedDate = null;
        String param = context.getParameter("minActivatedDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            minActivatedDate = date;
        }

        param = context.getParameter("maxActivatedDate");
        if (StringUtils.isNotBlank(param)) {
            Date date = (Date) ConversionUtil.convert(param, Date.class);
            if (date == null) {
                return emptyResult(context);
            }
            maxActivatedDate = date;
        }
        if(minActivatedDate == null || maxActivatedDate == null) {
            return emptyResult(context);
        }
        Result<DashboardMetricsDTO> result=new Result<>();
        result.setData(Collections.singletonList(getLabManagementService().getDashboardMetrics(minActivatedDate, maxActivatedDate)));
        result.setTotalRecordCount(1L);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return doSearch(context);
    }

    @Override
    public DashboardMetricsDTO newDelegate() {
        return new DashboardMetricsDTO();
    }

    @Override
    public DashboardMetricsDTO save(DashboardMetricsDTO delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(DashboardMetricsDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("testsOrdered");
            description.addProperty("testsToAccept");
            description.addProperty("testsForSampleCollection");
            description.addProperty("testsInProgress");
            description.addProperty("testsOnWorksheet");
            description.addProperty("testResultsRejected");
            description.addProperty("testsPendingApproval");
            description.addProperty("testsCompleted");
            description.addProperty("testsRejected");
            description.addProperty("testsReferredOut");
            description.addProperty("testsReferredOutLab");
            description.addProperty("testsReferredOutLabResulted");
            description.addProperty("testsReferredOutProvider");

        }

        if (rep instanceof DefaultRepresentation) {

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {

            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {

        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("testsOrdered", new IntegerProperty());
            modelImpl.property("testsToAccept", new IntegerProperty());
            modelImpl.property("testsForSampleCollection", new IntegerProperty());
            modelImpl.property("testsInProgress", new IntegerProperty());
            modelImpl.property("testsOnWorksheet", new IntegerProperty());
            modelImpl.property("testResultsRejected", new IntegerProperty());
            modelImpl.property("testsPendingApproval", new IntegerProperty());
            modelImpl.property("testsCompleted", new IntegerProperty());
            modelImpl.property("testsRejected", new IntegerProperty());
            modelImpl.property("testsReferredOut", new IntegerProperty());
            modelImpl.property("testsReferredOutLab", new IntegerProperty());
            modelImpl.property("testsReferredOutLabResulted", new IntegerProperty());
            modelImpl.property("testsReferredOutProvider", new IntegerProperty());

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
        }

        return modelImpl;
    }

}
