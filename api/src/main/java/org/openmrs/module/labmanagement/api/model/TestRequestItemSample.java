package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseOpenmrsData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity(name = "labmanagement.TestRequestItemSample")
@Table(name = "labmgmt_test_request_item_sample")
public class TestRequestItemSample extends BaseOpenmrsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_request_item_sample_id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_request_item_id", nullable = false)
    private TestRequestItem testRequestItem;

    @OneToMany(mappedBy = "testRequestItemSample", cascade = CascadeType.ALL)
    private Set<WorksheetItem> worksheetItems;

    @OneToMany(mappedBy = "testRequestItemSample", cascade = CascadeType.ALL)
    private Set<TestResult> testResults;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sample_id", nullable = false)
    private Sample sample;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TestRequestItem getTestRequestItem() {
        return testRequestItem;
    }

    public void setTestRequestItem(TestRequestItem testRequestItem) {
        this.testRequestItem = testRequestItem;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Set<WorksheetItem> getWorksheetItems() {
        return worksheetItems;
    }

    public void setWorksheetItems(Set<WorksheetItem> worksheetItems) {
        this.worksheetItems = worksheetItems;
    }

    public Set<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(Set<TestResult> testResults) {
        this.testResults = testResults;
    }
}
