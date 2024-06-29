package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.model.BatchJob;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/batchjob", supportedClass = BatchJob.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class BatchJobResource extends ResourceBase<BatchJob> {

    @Override
    public BatchJob getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(BatchJob delegate, String reason, RequestContext context) throws ResponseException {
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
    public BatchJob newDelegate() {
        return new BatchJob();
    }

    @Override
    public BatchJob save(BatchJob delegate) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(BatchJob delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("dateCreated");
            description.addProperty("dateChanged");
            description.addProperty("batchJobType");
            description.addProperty("status");
            description.addProperty("description");
            description.addProperty("startTime");
            description.addProperty("endTime");
            description.addProperty("expiration");
            description.addProperty("parameters");
            description.addProperty("privilegeScope");
            description.addProperty("executionState");
            description.addProperty("cancelReason");
            description.addProperty("cancelledDate");
            description.addProperty("exitMessage");
            description.addProperty("completedDate");
            description.addProperty("outputArtifactSize");
            description.addProperty("outputArtifactFileExt");
            description.addProperty("outputArtifactViewable");

        }

        if (rep instanceof DefaultRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("locationScope", Representation.REF);
            description.addProperty("cancelledBy", Representation.REF);

            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addProperty("creator", Representation.REF);
            description.addProperty("changedBy", Representation.REF);
            description.addProperty("locationScope", Representation.REF);
            description.addProperty("cancelledBy", Representation.REF);

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
            modelImpl.property("batchJobType", new StringProperty());
            modelImpl.property("status", new StringProperty());
            modelImpl.property("description", new StringProperty());
            modelImpl.property("startTime", new DateTimeProperty());
            modelImpl.property("endTime", new DateTimeProperty());
            modelImpl.property("expiration", new DateTimeProperty());
            modelImpl.property("parameters", new StringProperty());
            modelImpl.property("privilegeScope", new StringProperty());
            modelImpl.property("executionState", new StringProperty());
            modelImpl.property("cancelReason", new StringProperty());
            modelImpl.property("cancelledDate", new DateTimeProperty());
            modelImpl.property("exitMessage", new StringProperty());
            modelImpl.property("completedDate", new DateTimeProperty());
            modelImpl.property("outputArtifactSize", new LongProperty());
            modelImpl.property("outputArtifactFileExt", new StringProperty());
            modelImpl.property("outputArtifactViewable", new BooleanProperty());

        }
        if (rep instanceof DefaultRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("locationScope", new RefProperty("#/definitions/LocationGetRef"));
            modelImpl.property("cancelledBy", new RefProperty("#/definitions/UserGetRef"));

        }

        if (rep instanceof FullRepresentation) {
            modelImpl.property("creator", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("changedBy", new RefProperty("#/definitions/UserGetRef"));
            modelImpl.property("locationScope", new RefProperty("#/definitions/LocationGetRef"));
            modelImpl.property("cancelledBy", new RefProperty("#/definitions/UserGetRef"));

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());

        }

        return modelImpl;
    }


}
