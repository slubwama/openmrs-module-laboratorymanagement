package org.openmrs.module.labmanagement.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.openmrs.BaseChangeableOpenmrsData;
import org.openmrs.api.context.Context;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "labmanagement.ApprovalFlow")
@Table(name = "labmgmt_approval_flow")
public class ApprovalFlow extends BaseChangeableOpenmrsData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_flow_id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_one")
    private ApprovalConfig levelOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_two")
    private ApprovalConfig levelTwo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_three")
    private ApprovalConfig levelThree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_four")
    private ApprovalConfig levelFour;

    @Column(name = "system_name", length = 50)
    private String systemName;

    @Column(name = "level_one_allow_owner")
    private Boolean levelOneAllowOwner;

    @Column(name = "level_two_allow_owner")
    private Boolean levelTwoAllowOwner;

    @Column(name = "level_three_allow_owner")
    private Boolean levelThreeAllowOwner;

    @Column(name = "level_four_allow_owner")
    private Boolean levelFourAllowOwner;

    @Column(name = "level_two_allow_previous")
    private Boolean levelTwoAllowPrevious;

    @Column(name = "level_three_allow_previous")
    private Boolean levelThreeAllowPrevious;

    @Column(name = "level_four_allow_previous")
    private Boolean levelFourAllowPrevious;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApprovalConfig getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(ApprovalConfig levelOne) {
        this.levelOne = levelOne;
    }

    public ApprovalConfig getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(ApprovalConfig levelTwo) {
        this.levelTwo = levelTwo;
    }

    public ApprovalConfig getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(ApprovalConfig levelThree) {
        this.levelThree = levelThree;
    }

    public ApprovalConfig getLevelFour() {
        return levelFour;
    }

    public void setLevelFour(ApprovalConfig levelFour) {
        this.levelFour = levelFour;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Boolean getLevelOneAllowOwner() {
        return levelOneAllowOwner;
    }

    public void setLevelOneAllowOwner(Boolean levelOneAllowOwner) {
        this.levelOneAllowOwner = levelOneAllowOwner;
    }

    public Boolean getLevelTwoAllowOwner() {
        return levelTwoAllowOwner;
    }

    public void setLevelTwoAllowOwner(Boolean levelTwoAllowOwner) {
        this.levelTwoAllowOwner = levelTwoAllowOwner;
    }

    public Boolean getLevelThreeAllowOwner() {
        return levelThreeAllowOwner;
    }

    public void setLevelThreeAllowOwner(Boolean levelThreeAllowOwner) {
        this.levelThreeAllowOwner = levelThreeAllowOwner;
    }

    public Boolean getLevelFourAllowOwner() {
        return levelFourAllowOwner;
    }

    public void setLevelFourAllowOwner(Boolean levelFourAllowOwner) {
        this.levelFourAllowOwner = levelFourAllowOwner;
    }

    public Boolean getLevelTwoAllowPrevious() {
        return levelTwoAllowPrevious;
    }

    public void setLevelTwoAllowPrevious(Boolean levelTwoAllowPrevious) {
        this.levelTwoAllowPrevious = levelTwoAllowPrevious;
    }

    public Boolean getLevelThreeAllowPrevious() {
        return levelThreeAllowPrevious;
    }

    public void setLevelThreeAllowPrevious(Boolean levelThreeAllowPrevious) {
        this.levelThreeAllowPrevious = levelThreeAllowPrevious;
    }

    public Boolean getLevelFourAllowPrevious() {
        return levelFourAllowPrevious;
    }

    public void setLevelFourAllowPrevious(Boolean levelFourAllowPrevious) {
        this.levelFourAllowPrevious = levelFourAllowPrevious;
    }

    public int getNumberOfApprovalLevels(){
        return (levelOne != null ? 0 : 1) +
                (levelTwo != null ? 0 : 1) +
                (levelThree != null ? 0 : 1) +
                (levelFour != null ? 0 : 1);
    }

    public int getRemainingLevels(int currentApprovalLevel){
        if(currentApprovalLevel > 4) return 0;
        if(currentApprovalLevel < 1) currentApprovalLevel = 1;
        int remainingLevels = 0;
        switch (currentApprovalLevel){
            case 1:
                remainingLevels = remainingLevels + (levelTwo != null ? 0 : 1);
            case 2:
                remainingLevels = remainingLevels + (levelThree != null ? 0 : 1);
            case 3:
                remainingLevels = remainingLevels + (levelFour != null ? 0 : 1);
        }
        return remainingLevels;
    }

    private void setNextTestApproval(TestResult testResult,TestApproval currentApproval,ApprovalConfig approvalConfig, int level){
        TestApproval testApproval = new TestApproval();
        testApproval.setTestResult(testResult);
        testApproval.setActivatedDate(new Date());
        testApproval.setApprovalFlow(this);
        testApproval.setApprovalConfig(approvalConfig);
        testApproval.setNextApproval(null);
        testApproval.setCurrentApprovalLevel(level);
        testApproval.setCreator(Context.getAuthenticatedUser());
        testApproval.setDateCreated(new Date());
        testResult.setCurrentApproval(testApproval);
        testResult.setStatus(approvalConfig.getPendingStatus());
        if(currentApproval != null) {
            currentApproval.setNextApproval(testApproval);
        }
    }

    public void setNextTestApproval(TestResult testResult, TestApproval currentApproval, Integer currentApprovalLevel) {
        if(currentApprovalLevel <= 0 ){
            if(levelOne == null){
                testResult.completeTestResult();
            }else{
                setNextTestApproval(testResult,currentApproval, getLevelOne(),1);
            }
        }else if(currentApprovalLevel <= 1 ){
            if(levelTwo == null){
                testResult.completeTestResult();
            }else{
                setNextTestApproval(testResult,currentApproval, getLevelTwo(),2);
            }
        }else if(currentApprovalLevel <= 2){
            if(levelThree == null){
                testResult.completeTestResult();
            }else{
                setNextTestApproval(testResult,currentApproval, getLevelThree(),3);
            }
        }
        else if(currentApprovalLevel <= 3){
            if(levelFour == null){
                testResult.completeTestResult();
            }else{
                setNextTestApproval(testResult,currentApproval, getLevelFour(),4);
            }
        }else{
            testResult.completeTestResult();
        }
    }
}
