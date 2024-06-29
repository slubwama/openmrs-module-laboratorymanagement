package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.model.TestRequestItemSample;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-request-item-sample", supportedClass = TestRequestItemSample.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestRequestItemSampleResource extends ResourceBase<TestRequestItemSample> {

    @Override
    public TestRequestItemSample getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(TestRequestItemSample delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public TestRequestItemSample newDelegate() {
        return new TestRequestItemSample();
    }

    @Override
    public TestRequestItemSample save(TestRequestItemSample delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(TestRequestItemSample delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");

        }

        if (rep instanceof DefaultRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("testRequestItem", Representation.REF);
            description.addProperty("sample", Representation.REF);

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("testRequestItem", Representation.REF);
            description.addProperty("sample", Representation.REF);

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
            modelImpl.property("dateCreated", new DateTimeProperty());
            modelImpl.property("dateChanged", new DateTimeProperty());

        }
        if (rep instanceof DefaultRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("testRequestItem", new RefProperty("#/definitions/TestRequestItemGetRef"));
            modelImpl.property("sample", new RefProperty("#/definitions/SampleGetRef"));

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("testRequestItem", new RefProperty("#/definitions/TestRequestItemGetRef"));
            modelImpl.property("sample", new RefProperty("#/definitions/SampleGetRef"));

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }


}
