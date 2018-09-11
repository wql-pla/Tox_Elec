package com.tox.bean;

import java.util.Date;

public class ElecRedeem extends PageView<ElecRedeem> {
    private Integer id;

    private String code;

    private Date createDate;

    private Integer isDel;

    private Integer isUse;

    private Integer activityId;
    
    private String userPhone;//兑换人手机号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Override
	public String toString() {
		return "ElecRedeem [id=" + id + ", code=" + code + ", createDate=" + createDate + ", isDel=" + isDel
				+ ", isUse=" + isUse + ", activityId=" + activityId + ", userPhone=" + userPhone + "]";
	}
    
}