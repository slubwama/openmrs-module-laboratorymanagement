package org.openmrs.module.labmanagement.api.reporting;

import java.util.LinkedHashMap;

public abstract class ReportParameterValue<T> {
    private String description;
    private String display;
    private String valueDescription;
    private T value;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract Object getMapValue();

    public abstract T parseValue(Object value);

    public final void setValueFromMap(Object mapValue){
        if(mapValue == null) return;
        if(mapValue instanceof LinkedHashMap){
            LinkedHashMap<?,?> map = (LinkedHashMap<?,?>) mapValue;
            setDisplay(StringReportParameter.parse(map.getOrDefault("display", null)));
            setValueDescription(StringReportParameter.parse(map.getOrDefault("valueDescription", null)));
            setDescription(StringReportParameter.parse(map.getOrDefault("description", null)));
            setValue(parseValue(map.getOrDefault("value", null)));
        }
    }
    public abstract boolean isValueSet();

    public LinkedHashMap<String, Object> toMap(){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("display", display);
        map.put("valueDescription", valueDescription);
        map.put("description", description);
        map.put("value", getMapValue());
        return map;
    }

}
