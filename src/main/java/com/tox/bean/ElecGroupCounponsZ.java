package com.tox.bean;

public class ElecGroupCounponsZ {
    private Integer id;

    private Integer fatherId;

    private Integer counponsId;

    private Integer num;
    
    private ElecCoupons coupon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public Integer getCounponsId() {
        return counponsId;
    }

    public void setCounponsId(Integer counponsId) {
        this.counponsId = counponsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

	public ElecCoupons getCoupon() {
		return coupon;
	}

	public void setCoupon(ElecCoupons coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "ElecGroupCounponsZ [id=" + id + ", fatherId=" + fatherId + ", counponsId=" + counponsId + ", num=" + num
				+ ", coupon=" + coupon + "]";
	}
    
}