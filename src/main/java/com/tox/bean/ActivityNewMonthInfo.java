package com.tox.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel(value = "activityNewMonthInfo",description = "月卡明细")
public class ActivityNewMonthInfo extends PageView<ActivityNewMonthInfo>{
    @ApiModelProperty(name = "id",value = "月卡id")
    private Integer id;
    @ApiModelProperty(name = "phone",value = "用户电话")
    private String phone;
    @ApiModelProperty(name = "city",value = "所在城市")
    private String city;
    @ApiModelProperty(name = "onlineDate",value = "上线时间")
    private Date onlineDate;
    @ApiModelProperty(name = "monthStartDate",value = "月卡开始时间")
    private Date monthStartDate;
    @ApiModelProperty(name = "monthEndDate",value = "月卡失效时间")
    private Date monthEndDate;
    @ApiModelProperty(name = "monthStatus",value = "月卡状态")
    private String monthStatus;
    @ApiModelProperty(name = "isDel",value = "是否删除")
    private String isDel;
    @ApiModelProperty(name = "createDate",value = "月卡创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Date getMonthStartDate() {
        return monthStartDate;
    }

    public void setMonthStartDate(Date monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public Date getMonthEndDate() {
        return monthEndDate;
    }

    public void setMonthEndDate(Date monthEndDate) {
        this.monthEndDate = monthEndDate;
    }

    public String getMonthStatus() {
        return monthStatus;
    }

    public void setMonthStatus(String monthStatus) {
        this.monthStatus = monthStatus == null ? null : monthStatus.trim();
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ActivityNewMonthInfo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", onlineDate=" + onlineDate +
                ", monthStartDate=" + monthStartDate +
                ", monthEndDate=" + monthEndDate +
                ", monthStatus='" + monthStatus + '\'' +
                ", isDel='" + isDel + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}