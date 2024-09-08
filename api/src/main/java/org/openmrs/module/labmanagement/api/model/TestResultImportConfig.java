package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.Concept;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "labmanagement.TestResultImportConfig")
@Table(name = "labmgmt_test_result_import_config")
public class TestResultImportConfig extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_import_config_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Concept test;

    @Column(name = "header_hash", nullable = false, length = 500)
    private String headerHash;

    @Column(name = "field_mapping", nullable = false, length = 65535, columnDefinition="TEXT")
    private String fieldMapping;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Concept getTest() {
        return test;
    }

    public void setTest(Concept test) {
        this.test = test;
    }

    public String getHeaderHash() {
        return headerHash;
    }

    public void setHeaderHash(String headerHash) {
        this.headerHash = headerHash;
    }

    public String getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(String fieldMapping) {
        this.fieldMapping = fieldMapping;
    }
}
