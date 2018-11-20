package com.tox.bean;

import java.util.Date;

public class ActivityNewMonthInfo {
    private Integer id;

    private String phone;

    private String city;

    private Date onlineDate;

    private Date monthStartDate;

    private Date monthEndDate;

    private String monthStatus;

    private String isDel;

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
}