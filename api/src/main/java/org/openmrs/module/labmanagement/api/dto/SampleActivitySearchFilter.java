package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.Order;
import org.openmrs.module.labmanagement.api.model.SampleStatus;
import org.openmrs.module.labmanagement.api.model.StorageStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;

import java.util.Date;
import java.util.List;

public class SampleActivitySearchFilter extends SearchFilter {
    private Integer sampleId;
    private Integer sampleActivityId;
    private String sampleActivityUuid;

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getSampleActivityId() {
        return sampleActivityId;
    }

    public void setSampleActivityId(Integer sampleActivityId) {
        this.sampleActivityId = sampleActivityId;
    }

    public String getSampleActivityUuid() {
        return sampleActivityUuid;
    }

    public void setSampleActivityUuid(String sampleActivityUuid) {
        this.sampleActivityUuid = sampleActivityUuid;
    }
}
