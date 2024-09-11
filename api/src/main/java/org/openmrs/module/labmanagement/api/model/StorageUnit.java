package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.BaseFormRecordableOpenmrsData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity(name = "labmanagement.StorageUnit")
@Table(name = "labmgmt_storage_unit")
public class StorageUnit extends BaseChangeableOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_unit_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @Size(max = 255)
    @NotNull
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "storageUnit", cascade = CascadeType.ALL)
    private Set<Sample> samples;

    @OneToMany(mappedBy = "storageUnit", cascade = CascadeType.ALL)
    private Set<SampleActivity> sampleActivities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Sample> getSamples() {
        return samples;
    }

    public void setSamples(Set<Sample> samples) {
        this.samples = samples;
    }

    public Set<SampleActivity> getSampleActivities() {
        return sampleActivities;
    }

    public void setSampleActivities(Set<SampleActivity> sampleActivities) {
        this.sampleActivities = sampleActivities;
    }
}
