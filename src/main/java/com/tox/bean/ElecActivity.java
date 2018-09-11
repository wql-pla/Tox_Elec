package com.tox.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElecActivity extends PageView<ElecActivity> {
    private Integer id;

    private String name;//名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date toDate;

    private Integer status;//状态

    private Integer number;//数量

    private Integer isMore;//是否多次使用

    private Integer couponsId;//优惠卷id

    private Date createDate;//创建时间

    private Integer isDel;//逻辑删除
    
    private Integer allNum;//兑换数量
    
    private List<ElecRedeem> redeems;
    
    private List<ElecUser> users;//兑换人手机号
    
    private String couponName;
    
    private Integer couponsType;//优惠券类型 1普通优惠券  2组合优惠券

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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getIsMore() {
        return isMore;
    }

    public void setIsMore(Integer isMore) {
        this.isMore = isMore;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
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
    
	public List<ElecRedeem> getRedeems() {
		return redeems;
	}

	public void setRedeems(List<ElecRedeem> redeems) {
		this.redeems = redeems;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Integer getCouponsType() {
		return couponsType;
	}

	public void setCouponsType(Integer couponsType) {
		this.couponsType = couponsType;
	}

	public List<ElecUser> getUsers() {
		return users;
	}

	public void setUsers(List<ElecUser> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "ElecActivity [id=" + id + ", name=" + name + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", status=" + status + ", number=" + number + ", isMore=" + isMore + ", couponsId=" + couponsId
				+ ", createDate=" + createDate + ", isDel=" + isDel + ", allNum=" + allNum + ", redeems=" + redeems
				+ ", users=" + users + ", couponName=" + couponName + ", couponsType=" + couponsType + "]";
	}
}