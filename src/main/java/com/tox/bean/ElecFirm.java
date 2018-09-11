package com.tox.bean;

public class ElecFirm extends PageView<ElecFirm>{
    private Integer id;
    //厂商名称
    private String firmName;
    //负责人姓名
    private String personName;
    //负责人电话
    private String personPhone;
    //负责人openid
    private String personOpenid;
    //服务费付款比例
    private Double payRatio;
    //供应商地址
    private String address;

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName == null ? null : firmName.trim();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone == null ? null : personPhone.trim();
    }

    public String getPersonOpenid() {
        return personOpenid;
    }

    public void setPersonOpenid(String personOpenid) {
        this.personOpenid = personOpenid == null ? null : personOpenid.trim();
    }

    public Double getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(Double payRatio) {
        this.payRatio = payRatio;
    }

	@Override
	public String toString() {
		return "ElecFirm [id=" + id + ", firmName=" + firmName + ", personName=" + personName + ", personPhone="
				+ personPhone + ", personOpenid=" + personOpenid + ", payRatio=" + payRatio + "]";
	}
    
}