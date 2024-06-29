package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class SearchFilter {
    private String searchText;
    private Integer startIndex;
    private Integer limit;
    private Boolean voided;
    private List<SortField> sortFields;

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<SortField> getSortOrders() {
        return sortFields;
    }

    public void setSortOrders(List<SortField> sortFields) {
        this.sortFields = sortFields;
    }
}
