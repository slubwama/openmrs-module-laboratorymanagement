package org.openmrs.module.labmanagement.api.dto;

public class ObsDto {
    Integer obsId;
    Integer orderId;
    Integer obsGroupId;
    Integer conceptId;
    String conceptName;
    Double valueNumeric;
    String valueText;
    Integer valueCoded;
    String valueCodedName;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getConceptId() {
        return conceptId;
    }

    public void setConceptId(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public Double getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getValueCodedName() {
        return valueCodedName;
    }

    public void setValueCodedName(String valueCodedName) {
        this.valueCodedName = valueCodedName;
    }

    public Integer getObsGroupId() {
        return obsGroupId;
    }

    public void setObsGroupId(Integer obsGroupId) {
        this.obsGroupId = obsGroupId;
    }

    public Integer getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(Integer valueCoded) {
        this.valueCoded = valueCoded;
    }

    public Integer getObsId() {
        return obsId;
    }

    public void setObsId(Integer obsId) {
        this.obsId = obsId;
    }
}
