package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.ModuleConstants;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.labmanagement.api.dto.TestConfigDTO;
import org.openmrs.module.labmanagement.api.dto.TestConfigSearchFilter;
import org.openmrs.module.labmanagement.api.model.TestConfig;
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

@Resource(name = RestConstants.VERSION_1 + "/" + ModuleConstants.MODULE_ID + "/test-config", supportedClass = TestConfigDTO.class, supportedOpenmrsVersions = {"1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.*"})
public class TestConfigResource extends ResourceBase<TestConfigDTO> {

    @Override
    public TestConfigDTO getByUniqueId(String uniqueId) {
        TestConfigSearchFilter filter = new TestConfigSearchFilter();
        filter.setVoided(null);
        filter.setTestConfigUuid(uniqueId);
        Result<TestConfigDTO> result = getLabManagementService().findTestConfigurations(filter);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

    @Override
    protected void delete(TestConfigDTO delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        return searchTestConfiguations(context, false);
    }

    private PageableResult searchTestConfiguations(RequestContext context, boolean getAll){
        TestConfigSearchFilter filter = new TestConfigSearchFilter();
        filter.setVoided(context.getIncludeAll() ? null : false);
        if(!getAll) {
            filter.setStartIndex(context.getStartIndex());
            filter.setLimit(context.getLimit());
        }
        String param = context.getParameter("q");
        if (StringUtils.isNotBlank(param))
            filter.setSearchText(param);

        param = context.getParameter("active");
        if (!StringUtils.isBlank(param)) {
            filter.setActive("true".equalsIgnoreCase(param) || "1".equals(param));
        }

        param = context.getParameter("testGroup");

        if (!StringUtils.isBlank(param)) {
            Concept concept = Context.getConceptService().getConcept(param);
            if (concept == null) {
                return emptyResult(context);
            }
            filter.setTestGroupId(concept.getConceptId());
        }
        Result<TestConfigDTO> result = getLabManagementService().findTestConfigurations(filter);
        return toAlreadyPaged(result, context);
    }

    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        return searchTestConfiguations(context, true);
    }

    @Override
    public TestConfigDTO newDelegate() {
        return new TestConfigDTO();
    }

    @Override
    public TestConfigDTO save(TestConfigDTO delegate) {
        TestConfig testConfig = getLabManagementService().saveTestConfig(delegate);
        return getByUniqueId(testConfig.getUuid());
    }

    @Override
    public void purge(TestConfigDTO delegate, RequestContext context) throws ResponseException {
        delete(delegate, null, context);
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("requireApproval");
        description.addProperty("enabled");
        description.addProperty("testUuid");
        description.addProperty("testGroupUuid");
        description.addProperty("approvalFlowUuid");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("requireApproval");
        description.addProperty("enabled");
        description.addProperty("testGroupUuid");
        description.addProperty("approvalFlowUuid");
        return description;
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            description.addProperty("uuid");
            description.addProperty("requireApproval");
            description.addProperty("enabled");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("testGroupUuid");
            description.addProperty("testGroupName");
            description.addProperty("approvalFlowUuid");
            description.addProperty("approvalFlowName");
            changeTrackingFields(description);
        }

        if (rep instanceof DefaultRepresentation) {
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
        }

        if (rep instanceof FullRepresentation) {
            description.addSelfLink();
        }

        if (rep instanceof RefRepresentation) {
            description.addProperty("uuid");
            description.addProperty("testUuid");
            description.addProperty("testName");
            description.addProperty("testShortName");
            description.addProperty("testGroupUuid");
            description.addProperty("testGroupName");
        }

        return description;
    }

    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl modelImpl = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("testGroupUuid", new StringProperty());
            modelImpl.property("testGroupName", new StringProperty());
            modelImpl.property("requireApproval", new BooleanProperty());
            modelImpl.property("approvalFlowUuid", new StringProperty());
            modelImpl.property("approvalFlowName", new StringProperty());
            modelImpl.property("enabled", new BooleanProperty());
            changeTrackingFields(modelImpl);

        }

        if (rep instanceof RefRepresentation) {
            modelImpl.property("uuid", new StringProperty());
            modelImpl.property("testUuid", new StringProperty());
            modelImpl.property("testName", new StringProperty());
            modelImpl.property("testShortName", new StringProperty());
            modelImpl.property("testGroupUuid", new StringProperty());
            modelImpl.property("testGroupName", new StringProperty());
        }

        return modelImpl;
    }


}
