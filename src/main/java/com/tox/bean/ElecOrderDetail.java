package com.tox.bean;

import java.util.Date;

public class ElecOrderDetail {
    private Integer id;

    private Date fromDate;				//开始时间
    
    private Date toDate;				//结束时间

    private Double price;				//当前时间段电价

    private Double duration;			//充电时长

    private Double cost;				//该时间段充电费用

    private Integer orderId;			//订单外键
    
    private Double serviceAmount; //服务费
    
    private Double elecCount; //充电度数


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

	public Double getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(Double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public Double getElecCount() {
		return elecCount;
	}

	public void setElecCount(Double elecCount) {
		this.elecCount = elecCount;
	}

	@Override
	public String toString() {
		return "ElecOrderDetail [id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", price=" + price
				+ ", duration=" + duration + ", cost=" + cost + ", orderId=" + orderId + ", serviceAmount="
				+ serviceAmount + ", elecCount=" + elecCount + "]";
	}


}