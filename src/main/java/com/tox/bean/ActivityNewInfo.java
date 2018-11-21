package com.tox.bean;

import java.util.Date;

public class ActivityNewInfo {
    private Integer id;

    private String activityName;

    private Date createDate;

    private String activityCode;

    private Integer isDel;

    private Integer isOpen;

    private String activityUrl;

    private Double monthAmount;

    private Date startMonthDate;

    private Date endMonthDate;

    private Date activityDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode == null ? null : activityCode.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl == null ? null : activityUrl.trim();
    }

    public Double getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
    }

    public Date getStartMonthDate() {
        return startMonthDate;
    }

    public void setStartMonthDate(Date startMonthDate) {
        this.startMonthDate = startMonthDate;
    }

    public Date getEndMonthDate() {
        return endMonthDate;
    }

    public void setEndMonthDate(Date endMonthDate) {
        this.endMonthDate = endMonthDate;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }
}