package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;
import org.openmrs.module.labmanagement.api.LabManagementService;
import org.openmrs.module.labmanagement.api.model.ReferralLocation;

public class ReferralLocationReportParameter extends ReportParameterValue<String> {
    private ReferralLocation location;

    public Integer getReferralLocationId() {
        return location == null ? null : location.getId();
    }

    @Override
    public boolean isValueSet() {
        return getReferralLocationId() != null;
    }

    @Override
    public String parseValue(Object value) {
        String locationUuid = StringReportParameter.parse(value);
        if(StringUtils.isBlank(locationUuid)){
            return locationUuid;
        }
        ReferralLocation locationEntity = Context.getService(LabManagementService.class).getReferralLocationByUuid(locationUuid);
        if(locationEntity == null){
            throw new LabManagementException("Reference location with uuid not found");
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
