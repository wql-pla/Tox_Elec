package com.tox.bean;

import java.util.Date;

public class ElecUserCouponsRel extends PageView<ElecUserCouponsRel>{
    private Integer id;

    private Integer couponsId;//优惠卷id

    private Integer userId;//用户id

    private Integer redeemId;//兑换码id

    private Date createDate;//创建时间
    
    private Integer status;//使用状态
    
    private Integer isNew;//是否是新添优惠券
    
    private ElecCoupons coupons;//关联实体类，不做数据库字段
    
    private String users;
    
    private Integer activityId;//活动id
    

    public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRedeemId() {
        return redeemId;
    }

    public void setRedeemId(Integer redeemId) {
        this.redeemId = redeemId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ElecCoupons getCoupons() {
		return coupons;
	}

	public void setCoupons(ElecCoupons coupons) {
		this.coupons = coupons;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	@Override
	public String toString() {
		return "ElecUserCouponsRel [id=" + id + ", couponsId=" + couponsId + ", userId=" + userId + ", redeemId="
				+ redeemId + ", createDate=" + createDate + ", status=" + status + ", isNew=" + isNew + ", coupons="
				+ coupons + ", users=" + users + ", activityId=" + activityId + "]";
	}


    
}