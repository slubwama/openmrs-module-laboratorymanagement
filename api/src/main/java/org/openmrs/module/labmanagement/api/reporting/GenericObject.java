package org.openmrs.module.labmanagement.api.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmrs.module.labmanagement.api.LabManagementException;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenericObject extends LinkedHashMap<String, Object> {

    public static String toJson(Map<?,?> hashmap) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(hashmap);
        } catch (JsonProcessingException e) {
            throw new LabManagementException(e);
        }
    }

    public static GenericObject parseJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, GenericObject.class);
        } catch (JsonProcessingException e) {
            throw new LabManagementException(e);
        }
    }


}
