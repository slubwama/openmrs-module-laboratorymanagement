package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.BaseFormRecordableOpenmrsData;
import org.openmrs.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "labmanagement.Storage")
@Table(name = "labmgmt_storage")
public class Storage extends BaseChangeableOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "at_location_id", nullable = false)
    private Location atLocation;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private Set<StorageUnit> storageUnits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getAtLocation() {
        return atLocation;
    }

    public void setAtLocation(Location atLocation) {
        this.atLocation = atLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<StorageUnit> getStorageUnits() {
        return storageUnits;
    }

    public void setStorageUnits(Set<StorageUnit> storageUnits) {
        this.storageUnits = storageUnits;
    }

    public StorageUnit addStorageUnit(StorageUnit storageUnit) {
        if(getStorageUnits() == null){
            setStorageUnits(new HashSet<>());
        }
        getStorageUnits().add(storageUnit);
        storageUnit.setStorage(this);
        return storageUnit;
    }

    public StorageUnit removeStorageUnit(StorageUnit storageUnit) {
        if(getStorageUnits() == null){
            setStorageUnits(new HashSet<>());
        }
        getStorageUnits().remove(storageUnit);
        storageUnit.setStorage(null);
        return storageUnit;
    }
}
