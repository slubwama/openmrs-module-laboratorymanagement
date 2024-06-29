package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.model.BatchJobOwner;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/batchjobowner", supportedClass = BatchJobOwner.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class BatchJobOwnerResource extends ResourceBase<BatchJobOwner> {

    @Override
    public BatchJobOwner getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(BatchJobOwner delegate, String reason, RequestContext context) throws ResponseException {
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
    public BatchJobOwner newDelegate() {
        return new BatchJobOwner();
    }

    @Override
    public BatchJobOwner save(BatchJobOwner delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(BatchJobOwner delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("dateCreated");

        }

        if (rep instanceof DefaultRepresentation) {
            description.addProperty("batchJob", Representation.REF);
            description.addProperty("owner", Representation.REF);

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("batchJob", Representation.REF);
            description.addProperty("owner", Representation.REF);

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

        }
        if (rep instanceof DefaultRepresentation) {
            modelImpl.property("batchJob", new RefProperty("#/definitions/BatchJobGetRef"));
            modelImpl.property("owner", new RefProperty("#/definitions/UserGetRef"));

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("batchJob", new RefProperty("#/definitions/BatchJobGetRef"));
            modelImpl.property("owner", new RefProperty("#/definitions/UserGetRef"));

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }


}
