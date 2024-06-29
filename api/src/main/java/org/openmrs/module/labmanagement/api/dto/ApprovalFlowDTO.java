package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.module.labmanagement.api.model.ApprovalConfig;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ApprovalFlowDTO {

    private Integer id;
    private String name;
    private String levelOneUuid;
    private String levelOneApprovalTitle;

    private String levelTwoUuid;
    private String levelTwoApprovalTitle;

    private String levelThreeUuid;
    private String levelThreeApprovalTitle;
    private String levelFourUuid;
    private String levelFourApprovalTitle;
    private String uuid;
    private String systemName;
    private Boolean levelOneAllowOwner;
    private Boolean levelTwoAllowOwner;
    private Boolean levelThreeAllowOwner;
    private Boolean levelFourAllowOwner;
    private Boolean levelTwoAllowPrevious;
    private Boolean levelThreeAllowPrevious;
    private Boolean levelFourAllowPrevious;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getLevelOneUuid() {
        return levelOneUuid;
    }

    public void setLevelOneUuid(String levelOneUuid) {
        this.levelOneUuid = levelOneUuid;
    }

    public String getLevelTwoUuid() {
        return levelTwoUuid;
    }

    public void setLevelTwoUuid(String levelTwoUuid) {
        this.levelTwoUuid = levelTwoUuid;
    }

    public String getLevelThreeUuid() {
        return levelThreeUuid;
    }

    public void setLevelThreeUuid(String levelThreeUuid) {
        this.levelThreeUuid = levelThreeUuid;
    }

    public String getLevelFourUuid() {
        return levelFourUuid;
    }

    public void setLevelFourUuid(String levelFourUuid) {
        this.levelFourUuid = levelFourUuid;
    }

    public String getLevelOneApprovalTitle() {
        return levelOneApprovalTitle;
    }

    public void setLevelOneApprovalTitle(String levelOneApprovalTitle) {
        this.levelOneApprovalTitle = levelOneApprovalTitle;
    }

    public String getLevelTwoApprovalTitle() {
        return levelTwoApprovalTitle;
    }

    public void setLevelTwoApprovalTitle(String levelTwoApprovalTitle) {
        this.levelTwoApprovalTitle = levelTwoApprovalTitle;
    }

    public String getLevelThreeApprovalTitle() {
        return levelThreeApprovalTitle;
    }

    public void setLevelThreeApprovalTitle(String levelThreeApprovalTitle) {
        this.levelThreeApprovalTitle = levelThreeApprovalTitle;
    }

    public String getLevelFourApprovalTitle() {
        return levelFourApprovalTitle;
    }

    public void setLevelFourApprovalTitle(String levelFourApprovalTitle) {
        this.levelFourApprovalTitle = levelFourApprovalTitle;
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
}
