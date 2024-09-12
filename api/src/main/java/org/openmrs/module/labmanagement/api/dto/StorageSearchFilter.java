package org.openmrs.module.labmanagement.api.dto;

import java.util.List;

public class StorageSearchFilter extends SearchFilter {
    private Integer storageId;
    private Integer storageUnitId;
    private String storageUuId;
    private String storageUnitUuId;
    private String storageName;
    private Integer locationId;
    private Boolean includeUnits;
    private List<Integer> storageIds;
    private Boolean active;
    private Boolean assigned;

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public Integer getStorageUnitId() {
        return storageUnitId;
    }

    public void setStorageUnitId(Integer storageUnitId) {
        this.storageUnitId = storageUnitId;
    }

    public String getStorageUuId() {
        return storageUuId;
    }

    public void setStorageUuId(String storageUuId) {
        this.storageUuId = storageUuId;
    }

    public String getStorageUnitUuId() {
        return storageUnitUuId;
    }

    public void setStorageUnitUuId(String storageUnitUuId) {
        this.storageUnitUuId = storageUnitUuId;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Boolean getIncludeUnits() {
        return includeUnits;
    }

    public void setIncludeUnits(Boolean includeUnits) {
        this.includeUnits = includeUnits;
    }

    public List<Integer> getStorageIds() {
        return storageIds;
    }

    public void setStorageIds(List<Integer> storageIds) {
        this.storageIds = storageIds;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }
}

