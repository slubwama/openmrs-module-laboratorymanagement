package org.openmrs.module.labmanagement.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestResultDocumentDTO {
    private Integer id;

    
    private Integer testResultId;

    @Size(max = 50)
    
    private String documentType;

    @Size(max = 256)
    
    private String documentName;

    
    private Byte documentProvider;

    @Size(max = 1024)
    private String documentProviderRef;

    @Size(max = 500)
    private String remarks;

    @Size(max = 38)
    
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
