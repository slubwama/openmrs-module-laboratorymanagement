package org.openmrs.module.labmanagement.api.reporting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ObsValue {
    String conceptUuid;
    Integer conceptId;
    String display;
    String valueDescription;
    String valueText;
    String valueUuid;
    Integer valueConceptId;
    BigDecimal minValue;
    BigDecimal maxValue;
    List<ObsValue> groupMembers;
    boolean isNumeric;
    boolean isText;
    boolean isCoded;
    boolean isSet;

    public String getConceptUuid() {
        return conceptUuid;
    }

    public void setConceptUuid(String conceptUuid) {
        this.conceptUuid = conceptUuid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValueDescription() {
        return valueDescription;
    }

    public void setValueDescription(String valueDescription) {
        this.valueDescription = valueDescription;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public List<ObsValue> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<ObsValue> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean numeric) {
        isNumeric = numeric;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public boolean isCoded() {
        return isCoded;
    }

    public void setCoded(boolean coded) {
        isCoded = coded;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getValueUuid() {
        return valueUuid;
    }

    public void setValueUuid(String valueUuid) {
        this.valueUuid = valueUuid;
    }

    public Integer getValueConceptId() {
        return valueConceptId;
    }

    public void setValueConceptId(Integer valueConceptId) {
        this.valueConceptId = valueConceptId;
    }

    public Integer getConceptId() {
        return conceptId;
    }

    public void setConceptId(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public LinkedHashMap<String, Object> toLinkedHashMap() {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("conceptUuid", conceptUuid);
        linkedHashMap.put("display", display);
        linkedHashMap.put("valueDescription", valueDescription);
        if(isText()){
            linkedHashMap.put("value", valueText);
        }else if(isCoded() && getValueUuid() != null){
            LinkedHashMap<String, Object> uuid=new LinkedHashMap<>();
            uuid.put("uuid", valueUuid);
            linkedHashMap.put("value", uuid);
        }
        else if(isNumeric()){
            if(minValue != null){
                linkedHashMap.put("minValue", minValue);
            }
            if(maxValue != null){
                linkedHashMap.put("maxValue", maxValue);
            }
        }

        if(isSet() && getGroupMembers() != null){
            ArrayList<LinkedHashMap<String, Object>> setMembers = new ArrayList<>();
            for(ObsValue obsValue : groupMembers){
                setMembers.add(obsValue.toLinkedHashMap());
            }
            linkedHashMap.put("groupMembers", setMembers);
        }
        return linkedHashMap;
    }
}
