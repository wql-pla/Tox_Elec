package com.tox.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel(value = "activity",description = "活动实体")
public class ActivityNewInfo extends PageView<ActivityNewInfo> {
    @ApiModelProperty(name = "id",value = "活动id")
    private Integer id;
    @ApiModelProperty(name = "activityName",value = "活动名称")
    private String activityName;
    @ApiModelProperty(name = "createDate",value = "创建时间")
    private Date createDate;
    @ApiModelProperty(name = "activityCode",value = "活动代码")
    private String activityCode;
    @ApiModelProperty(name = "isDel",value = "是否删除")
    private Integer isDel;
    @ApiModelProperty(name = "isOpen",value = "是否启用")
    private Integer isOpen;
    @ApiModelProperty(name = "activityUrl",value = "活动路径")
    private String activityUrl;
    @ApiModelProperty(name = "startDate",value = "查询开始时间")
    private Date startDate;
    @ApiModelProperty(name = "endDate",value = "查询结束时间")
    private Date endDate;
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
        this.activityUrl = activityUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ActivityNewInfo{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", createDate=" + createDate +
                ", activityCode='" + activityCode + '\'' +
                ", isDel=" + isDel +
                ", isOpen=" + isOpen +
                ", activityUrl='" + activityUrl + '\'' +
                '}';
    }
}