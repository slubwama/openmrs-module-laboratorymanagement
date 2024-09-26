package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;

public class UserReportParameter extends ReportParameterValue<String> {
    private User user;

    public Integer getUserId() {
        return user == null ? null : user.getUserId();
    }

    @Override
    public boolean isValueSet() {
        return getUserId() != null;
    }

    @Override
    public String parseValue(Object value) {
        String userUuid = StringReportParameter.parse(value);
        if(StringUtils.isBlank(userUuid)){
            return userUuid;
        }
        User userEntity = Context.getUserService().getUserByUuid(userUuid);
        if(userEntity == null){
            throw new LabManagementException("User with uuid not found");
        }
        user = userEntity;
        return userEntity.getUuid();
    }

    @Override
    public Object getMapValue() {
        if(getValue() == null) return null;
        return getValue();
    }

}
