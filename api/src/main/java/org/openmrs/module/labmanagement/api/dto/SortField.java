package org.openmrs.module.labmanagement.api.dto;

public class SortField {
    private boolean ascending;
    private String field;


    public SortField(String field, boolean ascending){
        this.field = field;
        this.ascending = ascending;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
