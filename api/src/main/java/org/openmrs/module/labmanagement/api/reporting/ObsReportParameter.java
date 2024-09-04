package org.openmrs.module.labmanagement.api.reporting;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.LabManagementException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObsReportParameter extends ReportParameterValue<ObsValue> {

    @Override
    public Object getMapValue() {
        if(getValue() == null) return null;
        return getValue().toLinkedHashMap();
    }

    @Override
    public ObsValue parseValue(Object value) {
        return parse(value);
    }

    @Override
    public boolean isValueSet() {
        return getMapValue() != null;
    }

    public static ObsValue parse(Object value){
        return parseInternal(value, 2);
    }

    private static ObsValue parseInternal(Object value, int maxDepth) {
        if(maxDepth == 0){
            throw new LabManagementException("Maximum depth reached");
        }
        if(value == null) return null;
        if (ObsValue.class.isAssignableFrom(value.getClass())) {
            return (ObsValue) value;
        }

        ConceptService conceptService = Context.getConceptService();
        ObsValue obsValue = new ObsValue();
        Map<?, ?> attributes = (Map<?, ?>)value;
        String conceptUuid = StringReportParameter.parse(attributes.getOrDefault("conceptUuid", null));
        if(conceptUuid == null) {
            throw new LabManagementException("conceptUuid is required");
        }

        Concept concept = conceptService.getConceptByUuid(conceptUuid);
        if(concept == null){
            throw new LabManagementException("concept with uuid not found");
        }
        obsValue.setConceptId(concept.getId());
        obsValue.setConceptUuid(concept.getUuid());
        obsValue.setDisplay(concept.getDisplayString());
        obsValue.setNumeric(concept.isNumeric());
        obsValue.setCoded(concept.getDatatype() != null && concept.getDatatype().isCoded());
        obsValue.setNumeric(concept.getDatatype() != null && concept.getDatatype().isNumeric());
        obsValue.setText(concept.getDatatype() != null && concept.getDatatype().isText());
        obsValue.setSet(concept.getSetMembers() != null && !concept.getSetMembers().isEmpty());

        if(obsValue.isCoded() || obsValue.isNumeric() || obsValue.isText()) {
            if(obsValue.isCoded() || obsValue.isText()) {
                Object rawValue = attributes.getOrDefault("value", null);
                if (rawValue != null) {
                    if (obsValue.isText()) {
                        obsValue.valueText = StringReportParameter.parse(rawValue);
                    }else if (obsValue.isCoded) {
                        Map<?, ?> rawValueAttributes = (Map<?, ?>) rawValue;
                        String rawConceptUuid = StringReportParameter.parse(rawValueAttributes.getOrDefault("uuid", null));

                        if (StringUtils.isNotBlank(rawConceptUuid)) {
                            Concept codedConcept = conceptService.getConceptByUuid(rawConceptUuid);
                            if (codedConcept == null) {
                                throw new LabManagementException("Coded concept with given uuid not found");
                            }
                            obsValue.setValueUuid(codedConcept.getUuid());
                            obsValue.setValueConceptId(codedConcept.getConceptId());
                            Object valueDescription = attributes.getOrDefault("valueDescription", null);
                            if(valueDescription == null) {
                                    obsValue.setValueDescription(codedConcept.getDisplayString());

                            }
                        }
                    }
                }
            } else if (obsValue.isNumeric()) {
                String minValueTxt =  StringReportParameter.parse(attributes.getOrDefault("minValue", null));
                String maxValueTxt =   StringReportParameter.parse(attributes.getOrDefault("maxValue", null));
                if(StringUtils.isNotBlank(minValueTxt)) {
                    obsValue.setMinValue(BigDecimalReportParameter.parse(minValueTxt));
                }
                if(StringUtils.isNotBlank(maxValueTxt)) {
                    obsValue.setMaxValue(BigDecimalReportParameter.parse(maxValueTxt));
                }
            }
        }

        if(obsValue.isSet()){
            Object rawValue = attributes.get("groupMembers");
            if(rawValue != null){
                ArrayList<Map<?, ?>> groupMembersAttributes = (ArrayList<Map<?, ?>>)rawValue;
                obsValue.setGroupMembers(new ArrayList<>());
                for(Map<?, ?> groupMemberAttribute : groupMembersAttributes){
                    ObsValue groupMemberObsValue = parseInternal(groupMemberAttribute, maxDepth -1);
                    if(groupMemberObsValue == null) continue;
                    obsValue.getGroupMembers().add(groupMemberObsValue);
                }

            }
        }

       return obsValue;
    }
}
