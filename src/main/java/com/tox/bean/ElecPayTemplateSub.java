package com.tox.bean;

public class ElecPayTemplateSub {
    private Integer id;

    private Integer masterId;

    private Double payMoney;

    private Double giveMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getGiveMoney() {
        return giveMoney;
    }

    public void setGiveMoney(Double giveMoney) {
        this.giveMoney = giveMoney;
    }

	@Override
	public String toString() {
		return "ElecPayTemplateSub [id=" + id + ", masterId=" + masterId + ", payMoney=" + payMoney + ", giveMoney="
				+ giveMoney + "]";
	}
    
}