package com.tox.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElecBill {

	//1:充值   2:充电   3:充电退款
	private Integer type;
	
	private Date date;
	
	private Double money;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
}
