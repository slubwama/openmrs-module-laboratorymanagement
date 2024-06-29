package org.openmrs.module.labmanagement.api.dto;


import org.apache.commons.lang.time.DateUtils;
import org.openmrs.Obs;
import org.openmrs.User;
import org.openmrs.module.labmanagement.api.model.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class TestResultDTO {
    private Integer id;
    private String uuid;
    private Integer worksheetItemId;
    private String worksheetItemUuid;
    private String worksheetNo;
    private String worksheetUuid;
    private Integer sampleId;
    private String sampleUuid;
    private String sampleProvidedRef;
    private String sampleAccessionNumber;
    private String sampleExternalRef;
    private Integer orderId;
    private String orderUuid;
    private String orderNumber;
    private Integer orderConceptId;
    private Integer testRequestItemId;
    private String testRequestItemUuid;
    private String testRequestItemSampleUuid;
    private String testUuid;
    private String testName;
    private String testShortName;
    private Integer obsId;
    private org.openmrs.Obs obs;
    private Integer resultBy;
    private String resultByUuid;
    private String resultByGivenName;
    private String resultByMiddleName;
    private String resultByFamilyName;
    private String status;
    private Date resultDate;
    private Boolean requireApproval = false;
    private Integer currentApprovalId;
    private String currentApprovalUuid;
    private Boolean additionalTestsRequired;
    private Boolean archiveSample;
    private String remarks;
    private boolean voided;
    private Integer creator;
    private String creatorUuid;
    private String creatorGivenName;
    private String creatorFamilyName;
    private Date dateCreated;
    private Integer changedBy;
    private String changedByUuid;
    private String changedByGivenName;
    private String changedByFamilyName;
    private Date dateChanged;
    private Boolean completed;
    private Boolean completedResult;
    private Date completedDate;
    private String approvalPrivilege;
    private Integer currentApprovalLevel;
    private Boolean approvalFlowLevelOneAllowOwner;
    private Boolean approvalFlowLevelTwoAllowOwner;
    private Boolean approvalFlowLevelThreeAllowOwner;
    private Boolean approvalFlowLevelFourAllowOwner;
    private Boolean approvalFlowLevelTwoAllowPrevious;
    private Boolean approvalFlowLevelThreeAllowPrevious;
    private Boolean approvalFlowLevelFourAllowPrevious;
    private Boolean canApprove;
    private Boolean canUpdate;
    private List<TestApprovalDTO> approvals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorksheetItemId() {
        return worksheetItemId;
    }

    public void setWorksheetItemId(Integer worksheetItemId) {
        this.worksheetItemId = worksheetItemId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTestRequestItemId() {
        return testRequestItemId;
    }

    public void setTestRequestItemId(Integer testRequestItemId) {
        this.testRequestItemId = testRequestItemId;
    }

    public Integer getObsId() {
        return obsId;
    }

    public void setObsId(Integer obsId) {
        this.obsId = obsId;
    }

    public Integer getResultBy() {
        return resultBy;
    }

    public void setResultBy(Integer resultBy) {
        this.resultBy = resultBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Boolean getRequireApproval() {
        return requireApproval;
    }

    public void setRequireApproval(Boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public Integer getCurrentApprovalId() {
        return currentApprovalId;
    }

    public void setCurrentApprovalId(Integer currentApprovalId) {
        this.currentApprovalId = currentApprovalId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWorksheetItemUuid() {
        return worksheetItemUuid;
    }

    public void setWorksheetItemUuid(String worksheetItemUuid) {
        this.worksheetItemUuid = worksheetItemUuid;
    }

    public String getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(String sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    public String getSampleProvidedRef() {
        return sampleProvidedRef;
    }

    public void setSampleProvidedRef(String sampleProvidedRef) {
        this.sampleProvidedRef = sampleProvidedRef;
    }

    public String getSampleAccessionNumber() {
        return sampleAccessionNumber;
    }

    public void setSampleAccessionNumber(String sampleAccessionNumber) {
        this.sampleAccessionNumber = sampleAccessionNumber;
    }

    public String getSampleExternalRef() {
        return sampleExternalRef;
    }

    public void setSampleExternalRef(String sampleExternalRef) {
        this.sampleExternalRef = sampleExternalRef;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getTestRequestItemUuid() {
        return testRequestItemUuid;
    }

    public void setTestRequestItemUuid(String testRequestItemUuid) {
        this.testRequestItemUuid = testRequestItemUuid;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestShortName() {
        return testShortName;
    }

    public void setTestShortName(String testShortName) {
        this.testShortName = testShortName;
    }

    public Obs getObs() {
        return obs;
    }

    public void setObs(Obs obs) {
        this.obs = obs;
    }

    public String getResultByUuid() {
        return resultByUuid;
    }

    public void setResultByUuid(String resultByUuid) {
        this.resultByUuid = resultByUuid;
    }

    public String getResultByGivenName() {
        return resultByGivenName;
    }

    public void setResultByGivenName(String resultByGivenName) {
        this.resultByGivenName = resultByGivenName;
    }

    public String getResultByMiddleName() {
        return resultByMiddleName;
    }

    public void setResultByMiddleName(String resultByMiddleName) {
        this.resultByMiddleName = resultByMiddleName;
    }

    public String getResultByFamilyName() {
        return resultByFamilyName;
    }

    public void setResultByFamilyName(String resultByFamilyName) {
        this.resultByFamilyName = resultByFamilyName;
    }

    public String getCurrentApprovalUuid() {
        return currentApprovalUuid;
    }

    public void setCurrentApprovalUuid(String currentApprovalUuid) {
        this.currentApprovalUuid = currentApprovalUuid;
    }

    public String getTestRequestItemSampleUuid() {
        return testRequestItemSampleUuid;
    }

    public void setTestRequestItemSampleUuid(String testRequestItemSampleUuid) {
        this.testRequestItemSampleUuid = testRequestItemSampleUuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderConceptId() {
        return orderConceptId;
    }

    public void setOrderConceptId(Integer orderConceptId) {
        this.orderConceptId = orderConceptId;
    }

    public String getTestUuid() {
        return testUuid;
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    public Boolean getAdditionalTestsRequired() {
        return additionalTestsRequired;
    }

    public void setAdditionalTestsRequired(Boolean additionalTestsRequired) {
        this.additionalTestsRequired = additionalTestsRequired;
    }

    public Boolean getArchiveSample() {
        return archiveSample;
    }

    public void setArchiveSample(Boolean archiveSample) {
        this.archiveSample = archiveSample;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean getVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public String getCreatorGivenName() {
        return creatorGivenName;
    }

    public void setCreatorGivenName(String creatorGivenName) {
        this.creatorGivenName = creatorGivenName;
    }

    public String getCreatorFamilyName() {
        return creatorFamilyName;
    }

    public void setCreatorFamilyName(String creatorFamilyName) {
        this.creatorFamilyName = creatorFamilyName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedByUuid() {
        return changedByUuid;
    }

    public void setChangedByUuid(String changedByUuid) {
        this.changedByUuid = changedByUuid;
    }

    public String getChangedByGivenName() {
        return changedByGivenName;
    }

    public void setChangedByGivenName(String changedByGivenName) {
        this.changedByGivenName = changedByGivenName;
    }

    public String getChangedByFamilyName() {
        return changedByFamilyName;
    }

    public void setChangedByFamilyName(String changedByFamilyName) {
        this.changedByFamilyName = changedByFamilyName;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompletedResult() {
        return completedResult;
    }

    public void setCompletedResult(Boolean completedResult) {
        this.completedResult = completedResult;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public static boolean allowSamePreviousApprover(TestResultDTO testResultDTO){
        switch (testResultDTO.getCurrentApprovalLevel()){
            case 1:
                return  true;

            case 2:
                return  testResultDTO.getApprovalFlowLevelTwoAllowPrevious();

            case 3:
                return  testResultDTO.getApprovalFlowLevelThreeAllowPrevious();

            case 4:
                return  testResultDTO.getApprovalFlowLevelFourAllowPrevious();
        }
        return false;
    }

    public static boolean allowSamePreviousApprover(TestResult testResultDTO){
        switch (testResultDTO.getCurrentApproval().getCurrentApprovalLevel()){
            case 1:
                return  true;

            case 2:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelTwoAllowPrevious();

            case 3:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelThreeAllowPrevious();

            case 4:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelFourAllowPrevious();
        }
        return false;
    }

    public static boolean allowOwner(TestResultDTO testResultDTO){
        switch (testResultDTO.getCurrentApprovalLevel()){
            case 1:
                return  testResultDTO.approvalFlowLevelOneAllowOwner;

            case 2:
                return  testResultDTO.approvalFlowLevelTwoAllowOwner;

            case 3:
                return  testResultDTO.approvalFlowLevelThreeAllowOwner;

            case 4:
                return  testResultDTO.approvalFlowLevelFourAllowOwner;
        }
        return false;
    }

    public static boolean allowOwner(TestResult testResultDTO){
        switch (testResultDTO.getCurrentApproval().getCurrentApprovalLevel()){
            case 1:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelOneAllowOwner();

            case 2:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelTwoAllowOwner();

            case 3:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelThreeAllowOwner();

            case 4:
                return  testResultDTO.getCurrentApproval().getApprovalFlow().getLevelFourAllowOwner();
        }
        return false;
    }

    public static boolean canApproveTestResults(TestResultDTO testResultDTO, User currentUser, List<TestApprovalDTO> previousApprovals){
        boolean canApprove =  testResultDTO != null && currentUser != null && (testResultDTO.getRequireApproval() != null &&
                testResultDTO.getRequireApproval() && !testResultDTO.getCompleted() &&
                currentUser.hasPrivilege(testResultDTO.getApprovalPrivilege()) &&
                testResultDTO.getCurrentApprovalLevel() != null
                );

        if(canApprove){
            boolean allowOwner = allowOwner(testResultDTO);
            if(!allowOwner){
                canApprove = !Objects.equals(currentUser.getUserId(), testResultDTO.resultBy);
            }
            if(canApprove) {
                boolean allowSameUserAsPreviousApprover = allowSamePreviousApprover(testResultDTO);
                if(!allowSameUserAsPreviousApprover){
                    canApprove = previousApprovals == null || previousApprovals.isEmpty() ||
                            previousApprovals.stream().noneMatch(p->
                                    !p.getId().equals(testResultDTO.getCurrentApprovalId()) &&
                                            p.getApprovedBy() != null &&
                                            p.getApprovedBy().equals(currentUser.getUserId())
                            );
                }
            }
        }
        return canApprove;
    }


    public static boolean canApproveTestResults(TestResult testResultDTO, User currentUser, List<TestApprovalDTO> previousApprovals){
        boolean canApprove =  testResultDTO != null && currentUser != null && (testResultDTO.getRequireApproval() != null &&
                testResultDTO.getRequireApproval() && !testResultDTO.getCompleted() && testResultDTO.getCurrentApproval() != null &&
                currentUser.hasPrivilege(testResultDTO.getCurrentApproval().getApprovalConfig().getPrivilege()) &&
                testResultDTO.getCurrentApproval().getCurrentApprovalLevel() != null
        );

        if(canApprove){
            boolean allowOwner = allowOwner(testResultDTO);
            if(!allowOwner){
                canApprove = !Objects.equals(currentUser.getUserId(), testResultDTO.getResultBy().getUserId());
            }
            if(canApprove) {
                boolean allowSameUserAsPreviousApprover = allowSamePreviousApprover(testResultDTO);
                if(!allowSameUserAsPreviousApprover){
                    canApprove = previousApprovals == null || previousApprovals.isEmpty() ||
                            previousApprovals.stream().noneMatch(p->
                                    !p.getId().equals(testResultDTO.getCurrentApproval().getId()) &&
                                            p.getApprovedBy() != null &&
                                            p.getApprovedBy().equals(currentUser.getUserId())
                            );
                }
            }
        }
        return canApprove;
    }

    public String getApprovalPrivilege() {
        return approvalPrivilege;
    }

    public void setApprovalPrivilege(String approvalPrivilege) {
        this.approvalPrivilege = approvalPrivilege;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public Boolean getApprovalFlowLevelOneAllowOwner() {
        return approvalFlowLevelOneAllowOwner;
    }

    public void setApprovalFlowLevelOneAllowOwner(Boolean approvalFlowLevelOneAllowOwner) {
        this.approvalFlowLevelOneAllowOwner = approvalFlowLevelOneAllowOwner;
    }

    public Boolean getApprovalFlowLevelTwoAllowOwner() {
        return approvalFlowLevelTwoAllowOwner;
    }

    public void setApprovalFlowLevelTwoAllowOwner(Boolean approvalFlowLevelTwoAllowOwner) {
        this.approvalFlowLevelTwoAllowOwner = approvalFlowLevelTwoAllowOwner;
    }

    public Boolean getApprovalFlowLevelThreeAllowOwner() {
        return approvalFlowLevelThreeAllowOwner;
    }

    public void setApprovalFlowLevelThreeAllowOwner(Boolean approvalFlowLevelThreeAllowOwner) {
        this.approvalFlowLevelThreeAllowOwner = approvalFlowLevelThreeAllowOwner;
    }

    public Boolean getApprovalFlowLevelFourAllowOwner() {
        return approvalFlowLevelFourAllowOwner;
    }

    public void setApprovalFlowLevelFourAllowOwner(Boolean approvalFlowLevelFourAllowOwner) {
        this.approvalFlowLevelFourAllowOwner = approvalFlowLevelFourAllowOwner;
    }

    public Boolean getApprovalFlowLevelTwoAllowPrevious() {
        return approvalFlowLevelTwoAllowPrevious;
    }

    public void setApprovalFlowLevelTwoAllowPrevious(Boolean approvalFlowLevelTwoAllowPrevious) {
        this.approvalFlowLevelTwoAllowPrevious = approvalFlowLevelTwoAllowPrevious;
    }

    public Boolean getApprovalFlowLevelThreeAllowPrevious() {
        return approvalFlowLevelThreeAllowPrevious;
    }

    public void setApprovalFlowLevelThreeAllowPrevious(Boolean approvalFlowLevelThreeAllowPrevious) {
        this.approvalFlowLevelThreeAllowPrevious = approvalFlowLevelThreeAllowPrevious;
    }

    public Boolean getApprovalFlowLevelFourAllowPrevious() {
        return approvalFlowLevelFourAllowPrevious;
    }

    public void setApprovalFlowLevelFourAllowPrevious(Boolean approvalFlowLevelFourAllowPrevious) {
        this.approvalFlowLevelFourAllowPrevious = approvalFlowLevelFourAllowPrevious;
    }

    public Boolean getCanApprove() {
        return canApprove;
    }

    public void setCanApprove(Boolean canApprove) {
        this.canApprove = canApprove;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public static boolean canUpdateTestResult(TestRequestItemDTO testRequestItem, int editTimeout){
        return testRequestItem != null && TestRequestItemStatus.canUpdateTestResult(testRequestItem.getStatus()) &&
                canUpdateTestResult(testRequestItem.getTestResult(), editTimeout);
    }

    public static boolean canUpdateTestResult(TestResultDTO testResult, int editTimeout){
        if(testResult == null) return true;
        if(testResult.getVoided()) return  false;
        if(testResult.getRequireApproval()){
            return !testResult.getCompleted();
        }
        else{
            Date cutoffDate = DateUtils.addSeconds(testResult.getDateCreated(), editTimeout);
            return (new Date().before(cutoffDate));
        }
    }

    public static boolean canUpdateTestResult(TestResult testResult, int editTimeout){
        if(testResult == null ||testResult.getVoided()) return  false;
        if(testResult.getRequireApproval()){
            return !testResult.getCompleted();
        }
        else{
            Date cutoffDate = DateUtils.addSeconds(testResult.getDateCreated(), editTimeout);
            return (new Date().before(cutoffDate));
        }
    }

    public String getWorksheetNo() {
        return worksheetNo;
    }

    public void setWorksheetNo(String worksheetNo) {
        this.worksheetNo = worksheetNo;
    }

    public String getWorksheetUuid() {
        return worksheetUuid;
    }

    public void setWorksheetUuid(String worksheetUuid) {
        this.worksheetUuid = worksheetUuid;
    }

    public List<TestApprovalDTO> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<TestApprovalDTO> approvals) {
        this.approvals = approvals;
    }
}
