package com.tox.bean;

public class ElecCoupon {
    private Integer id;
    //兑换码
    private String code;
    //兑换码状态 0未使用，1已占用，2已兑换
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	@Override
	public String toString() {
		return "ElecCoupon [id=" + id + ", code=" + code + ", status=" + status + "]";
	}
    
    
}