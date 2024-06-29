package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.StringProperty;
import org.openmrs.logic.op.In;
import org.openmrs.module.labmanagement.api.LabManagementException;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.TestRequestAction;
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

import java.util.Map;

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-request-action", supportedClass = TestRequestAction.class, supportedOpenmrsVersions = {
        "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestRequestActionResource extends ResourceBase<TestRequestAction> {

    @Override
    public TestRequestAction getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(TestRequestAction delegate, String reason, RequestContext context) throws ResponseException {
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
    public TestRequestAction newDelegate() {
        return new TestRequestAction();
    }

    @Override
    public TestRequestAction save(TestRequestAction delegate) {
        if(delegate.getActionType() == null){
            throw new LabManagementException("Action type required");
        }
        switch (delegate.getActionType()){
            case TEST_REQUEST_APPROVAL:
            {
                getLabManagementService().approveTestRequestItem(delegate);
                delegate=new TestRequestAction();
                delegate.setRemarks("Approval action successfull");
                delegate.setUuid("not-supported");
                return delegate;
            }
            case SAMPLE_RELEASE_FOR_TESTING:
            {
                Map<Integer, String> result = getLabManagementService()
                        .releaseSamplesForTesting(delegate.getTestRequestUuid(), delegate.getRecords());
                if(result != null){
                    getLabManagementService().updateOrderInstructions(result);
                }
                delegate=new TestRequestAction();
                delegate.setRemarks("Release for testing successfull");
                delegate.setUuid("not-supported");
                return delegate;
            }
            case TEST_RESULT_APPROVAL:
            {
                getLabManagementService().approveTestResultItem(delegate);
                delegate=new TestRequestAction();
                delegate.setRemarks("Approval action successfull");
                delegate.setUuid("not-supported");
                return delegate;
            }
        }
        throw new ResourceDoesNotSupportOperationException("Action type does not support operation");
    }

    @Override
    public void purge(TestRequestAction delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("actionType");
        description.addProperty("action");
        description.addProperty("remarks");
        description.addProperty("testRequestUuid");
        description.addProperty("records");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation || rep instanceof RefRepresentation){
            description.addProperty("uuid");
            description.addProperty("remarks");
		}

		if (rep instanceof DefaultRepresentation){

			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		}

		if (rep instanceof FullRepresentation){

			description.addSelfLink();
		}
        return description;
    }

    @Override
	public Model getGETModel(Representation rep) {
		ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("remarks", new StringProperty());
		}
		return modelImpl;
	}
}
