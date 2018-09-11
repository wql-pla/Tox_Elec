package com.tox.bean;

import java.util.Date;

public class ElecActivity2 extends PageView<ElecActivity2>{
    private Integer id;

    private String name;//活动名称

    private Integer type;//活动类型 1新注册 2 老带新

    private Date startTime;//活动开始时间

    private Date endTime;//活动结束时间

    private Integer status;//启用状态 1启用，0停用

    private Integer giveRule;//发放条件 1注册成功 2有充值订单 3有充电订单

    private Integer oldPersonCoupon;//给老用户发放的优惠券id
    
    private ElecCoupons oldCoupons;//老用户普通优惠券
    
    private ElecGroupCounponsF oldGroupCoupons;//老用户组合优惠券

    private Integer newPersonCoupon;//给新用户发放的优惠券id
    
    private ElecCoupons newCoupons;//新用户优惠券
    
    private ElecGroupCounponsF newGroupCoupons;//新用户组合优惠券

    private Date createTime;//

    private Integer isDel;//

    private Integer oldCouponType;//老用户发放的优惠券类型（1普通优惠，2组合优惠）
    private Integer newCouponType;//新用户发放的优惠券类型（1普通优惠，2组合优惠）

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGiveRule() {
        return giveRule;
    }

    public void setGiveRule(Integer giveRule) {
        this.giveRule = giveRule;
    }

    public Integer getOldPersonCoupon() {
        return oldPersonCoupon;
    }

    public void setOldPersonCoupon(Integer oldPersonCoupon) {
        this.oldPersonCoupon = oldPersonCoupon;
    }

    public Integer getNewPersonCoupon() {
        return newPersonCoupon;
    }

    public void setNewPersonCoupon(Integer newPersonCoupon) {
        this.newPersonCoupon = newPersonCoupon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getOldCouponType() {
		return oldCouponType;
	}

	public void setOldCouponType(Integer oldCouponType) {
		this.oldCouponType = oldCouponType;
	}

	public Integer getNewCouponType() {
		return newCouponType;
	}

	public void setNewCouponType(Integer newCouponType) {
		this.newCouponType = newCouponType;
	}

	public ElecCoupons getOldCoupons() {
		return oldCoupons;
	}

	public void setOldCoupons(ElecCoupons oldCoupons) {
		this.oldCoupons = oldCoupons;
	}

	public ElecGroupCounponsF getOldGroupCoupons() {
		return oldGroupCoupons;
	}

	public void setOldGroupCoupons(ElecGroupCounponsF oldGroupCoupons) {
		this.oldGroupCoupons = oldGroupCoupons;
	}

	public ElecCoupons getNewCoupons() {
		return newCoupons;
	}

	public void setNewCoupons(ElecCoupons newCoupons) {
		this.newCoupons = newCoupons;
	}

	public ElecGroupCounponsF getNewGroupCoupons() {
		return newGroupCoupons;
	}

	public void setNewGroupCoupons(ElecGroupCounponsF newGroupCoupons) {
		this.newGroupCoupons = newGroupCoupons;
	}

	@Override
	public String toString() {
		return "ElecActivity2 [id=" + id + ", name=" + name + ", type=" + type + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + ", giveRule=" + giveRule + ", oldPersonCoupon="
				+ oldPersonCoupon + ", oldCoupons=" + oldCoupons + ", oldGroupCoupons=" + oldGroupCoupons
				+ ", newPersonCoupon=" + newPersonCoupon + ", newCoupons=" + newCoupons + ", newGroupCoupons="
				+ newGroupCoupons + ", createTime=" + createTime + ", isDel=" + isDel + ", oldCouponType="
				+ oldCouponType + ", newCouponType=" + newCouponType + "]";
	}

}