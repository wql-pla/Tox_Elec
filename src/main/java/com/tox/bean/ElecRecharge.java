package com.tox.bean;

import java.util.Date;

public class ElecRecharge extends PageView<ElecRecharge>{
    private Integer id;

    private Integer userId;

    private String payIde;//商户订单号

    private String orderIde;//微信订单号

    private Date rechargeDate;//充值时间

    private Double rechargeMoney;//充值金额

    private Double presentMoney;//赠送金额

    private Integer type;//类型

    private Integer isdel;
    
    private Integer orderId;
    
    private String user_phone;
    
    private Date startDate;//条件搜索开始时间
    
    private Date endDate;//条件搜索结束时间

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

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

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

    public String getPayIde() {
        return payIde;
    }

    public void setPayIde(String payIde) {
        this.payIde = payIde == null ? null : payIde.trim();
    }

    public String getOrderIde() {
        return orderIde;
    }

    public void setOrderIde(String orderIde) {
        this.orderIde = orderIde == null ? null : orderIde.trim();
    }

    public Date getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(Date rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public Double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(Double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public Double getPresentMoney() {
        return presentMoney;
    }

    public void setPresentMoney(Double presentMoney) {
        this.presentMoney = presentMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

	@Override
	public String toString() {
		return "ElecRecharge [id=" + id + ", userId=" + userId + ", payIde=" + payIde + ", orderIde=" + orderIde
				+ ", rechargeDate=" + rechargeDate + ", rechargeMoney=" + rechargeMoney + ", presentMoney="
				+ presentMoney + ", type=" + type + ", isdel=" + isdel + ", orderId=" + orderId + ", user_phone="
				+ user_phone + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
    
}