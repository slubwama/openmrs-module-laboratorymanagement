package org.openmrs.module.labmanagement.web.resource;

import io.swagger.models.ModelImpl;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.dto.Result;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ResourceBase<T> extends DelegatingCrudResource<T> {

	private LabManagementService labManagementService;

	protected LabManagementService getLabManagementService() {
		if (labManagementService == null) {
			labManagementService = Context.getService(LabManagementService.class);
		}
		return labManagementService;
	}

	protected void forbidden() {
		throw new RestClientException("403: Forbidden");
	}

	protected void notFound() {
		throw new RestClientException("404: Forbidden");
	}

	protected void invalidRequest(String messageKey, Object... args) {
		if (args != null && args.length > 0)
			throw new RestClientException(String.format(Context.getMessageSourceService().getMessage(messageKey), args));
		else
			throw new RestClientException(Context.getMessageSourceService().getMessage(messageKey));
	}

	protected void requirePriviledge(String priviledge) {
		UserContext userContext = Context.getUserContext();
		if (!userContext.hasPrivilege(priviledge))
			forbidden();
	}

	protected String nullIfEmpty(String string) {
		if (string == null)
			return null;
		string = string.trim();
		if (string.isEmpty())
			return null;
		return string;
	}

	protected <E> AlreadyPaged<E> emptyResult(RequestContext context) {
		return new AlreadyPaged<E>(context, new ArrayList<E>(), false);
	}

	protected <E> AlreadyPaged<E> toAlreadyPaged(Result<E> result, RequestContext context) {
		return new AlreadyPaged<E>(context, result.getData(), result.hasMoreResults(), result.getTotalRecordCount());
	}

	protected <E> AlreadyPaged<E> toAlreadyPaged(List<E> result, RequestContext context) {
		return new AlreadyPaged<E>(context, result, false, (long) result.size());
	}

	protected void changeTrackingFields(DelegatingResourceDescription description) {
		description.addProperty("creatorUuid");
		description.addProperty("dateCreated");
		description.addProperty("creatorGivenName");
		description.addProperty("creatorFamilyName");
		description.addProperty("changedByUuid");
		description.addProperty("dateChanged");
		description.addProperty("changedByGivenName");
		description.addProperty("changedByFamilyName");
		description.addProperty("voided");
	}

	protected void changeTrackingFields(ModelImpl modelImpl) {
		modelImpl.property("creatorUuid", new StringProperty());
		modelImpl.property("dateCreated", new DateTimeProperty());
		modelImpl.property("creatorGivenName", new StringProperty());
		modelImpl.property("creatorFamilyName", new StringProperty());
		modelImpl.property("changedByUuid", new StringProperty());
		modelImpl.property("dateChanged", new DateTimeProperty());
		modelImpl.property("changedByGivenName", new StringProperty());
		modelImpl.property("changedByFamilyName", new StringProperty());
		modelImpl.property("voided", new BooleanProperty());
	}

	protected Boolean setRequestContextValue(Map<String, Object> requestContextItems, String key, Boolean value){
		requestContextItems.put(key, value);
		return value;
	}

	protected Integer setRequestContextValue(Map<String, Object> requestContextItems, String key, Integer value){
		requestContextItems.put(key, value);
		return value;
	}
}
