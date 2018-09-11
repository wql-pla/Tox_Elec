package com.tox.bean;

public class ElecPriceTemplateSub {
    private Integer id;

    private Integer masterId;

    private Double amount;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

	@Override
	public String toString() {
		return "ElecPriceTemplateSub [id=" + id + ", masterId=" + masterId + ", amount=" + amount + "]";
	}
    
}