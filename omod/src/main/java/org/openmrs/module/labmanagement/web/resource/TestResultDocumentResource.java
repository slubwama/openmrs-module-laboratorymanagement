package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.BinaryProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.model.TestResultDocument;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/testresultdocument", supportedClass = TestResultDocument.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestResultDocumentResource extends ResourceBase<TestResultDocument> {

    @Override
    public TestResultDocument getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(TestResultDocument delegate, String reason, RequestContext context) throws ResponseException {
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
    public TestResultDocument newDelegate() {
        return new TestResultDocument();
    }

    @Override
    public TestResultDocument save(TestResultDocument delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(TestResultDocument delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");
            description.addProperty("documentType");
            description.addProperty("documentName");
            description.addProperty("documentProvider");
            description.addProperty("documentProviderRef");
            description.addProperty("remarks");

        }

        if (rep instanceof DefaultRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("testResult", Representation.REF);

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("testResult", Representation.REF);

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
            modelImpl.property("documentType", new StringProperty());
            modelImpl.property("documentName", new StringProperty());
            modelImpl.property("documentProvider", new BinaryProperty().minLength(1));
            modelImpl.property("documentProviderRef", new StringProperty());
            modelImpl.property("remarks", new StringProperty());

        }
        if (rep instanceof DefaultRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("testResult", new RefProperty("#/definitions/TestResultGetRef"));

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("testResult", new RefProperty("#/definitions/TestResultGetRef"));

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }


}
