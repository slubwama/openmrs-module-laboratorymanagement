package org.openmrs.module.labmanagement.api.model;

import org.openmrs.BaseChangeableOpenmrsData;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "labmanagement.TestResultDocument")
@Table(name = "labmgmt_test_result_document")
public class TestResultDocument extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_document_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id", nullable = false)
    private TestResult testResult;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType;

    @Column(name = "document_name", nullable = false, length = 256)
    private String documentName;

    @Column(name = "document_provider", nullable = false)
    private Byte documentProvider;

    @Column(name = "document_provider_ref", length = 1024)
    private String documentProviderRef;

    @Column(name = "remarks", length = 500)
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Byte getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(Byte documentProvider) {
        this.documentProvider = documentProvider;
    }

    public String getDocumentProviderRef() {
        return documentProviderRef;
    }

    public void setDocumentProviderRef(String documentProviderRef) {
        this.documentProviderRef = documentProviderRef;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
