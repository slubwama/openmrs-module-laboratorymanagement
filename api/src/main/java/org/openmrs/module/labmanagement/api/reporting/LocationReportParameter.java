package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;

public class LocationReportParameter extends ReportParameterValue<String> {
    private Location location;

    public Integer getLocationId() {
        return location == null ? null : location.getId();
    }

    @Override
    public boolean isValueSet() {
        return getLocationId() != null;
    }

    @Override
    public String parseValue(Object value) {
        String locationUuid = StringReportParameter.parse(value);
        if(StringUtils.isBlank(locationUuid)){
            return locationUuid;
        }
        Location locationEntity = Context.getLocationService().getLocation(locationUuid);
        if(locationEntity == null){
            throw new LabManagementException("Location with uuid not found");
        }
        location = locationEntity;
        return locationEntity.getUuid();
    }

    @Override
    public Object getMapValue() {
        if(getValue() == null) return null;
        return getValue();
    }
}
