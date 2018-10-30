package com.tox.bean;

import java.util.Date;

public class ActivityNewOrder {
    private Integer id;

    private Integer userId;

    private Date createDate;

    private Integer isDel;

    private Integer status;

    private String openid;

    private String tNo;

    private String wNo;

    private Integer money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo == null ? null : tNo.trim();
    }

    public String getwNo() {
        return wNo;
    }

    public void setwNo(String wNo) {
        this.wNo = wNo == null ? null : wNo.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}