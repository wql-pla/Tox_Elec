package com.tox.bean;

import java.io.Serializable;

public class DirectStation extends ElecStation{
	
	private Integer directPileNum;
	
	private Integer pileNum;

	//直流桩服务费
	private Double directServiceChargeAmount;
	
	//直流桩基础费
	private Double directBasicChargeAmount;
	
	//直流桩个人服务费
	private Double directPersonServiceAmount;
	
	//直流桩分润比
	private Double directThirdServiceAmount;
	
	public Integer getDirectPileNum() {
		return directPileNum;
	}

	public void setDirectPileNum(Integer directPileNum) {
		this.directPileNum = directPileNum;
	}

	public Integer getPileNum() {
		return pileNum;
	}

	public void setPileNum(Integer pileNum) {
		this.pileNum = pileNum;
	}

	public Double getDirectServiceChargeAmount() {
		return directServiceChargeAmount;
	}
	
	public void setDirectServiceChargeAmount(Double directServiceChargeAmount) {
		this.directServiceChargeAmount = directServiceChargeAmount;
	}

	public Double getDirectBasicChargeAmout() {
		return directBasicChargeAmount;
	}

	public void setDirectBasicChargeAmout(Double directBasicChargeAmount) {
		this.directBasicChargeAmount = directBasicChargeAmount;
	}

	public Double getDirectPersonServiceAmout() {
		return directPersonServiceAmount;
	}

	public void setDirectPersonServiceAmout(Double directPersonServiceAmount) {
		this.directPersonServiceAmount = directPersonServiceAmount;
	}

	public Double getDirectThirdServiceAmount() {
		return directThirdServiceAmount;
	}

	public void setDirectThirdServiceAmount(Double directThirdServiceAmount) {
		this.directThirdServiceAmount = directThirdServiceAmount;
	}

	@Override
	public String toString() {
		return "DirectStation [directServiceChargeAmount=" + directServiceChargeAmount + ", directBasicChargeAmout="
				+ directBasicChargeAmount + ", directPersonServiceAmout=" + directPersonServiceAmount
				+ ", directThirdServiceAmount=" + directThirdServiceAmount + ", getIsDirect()=" + getIsDirect()
				+ ", getPersonType()=" + getPersonType() + ", getPersonServiceAmount()=" + getPersonServiceAmount()
				+ ", getStartDate()=" + getStartDate() + ", getEndDate()=" + getEndDate() + ", getId()=" + getId()
				+ ", getStationName()=" + getStationName() + ", getServiceChargeAmount()=" + getServiceChargeAmount()
				+ ", getBasicChargeAmount()=" + getBasicChargeAmount() + ", getPersonName()=" + getPersonName()
				+ ", getPersonPhone()=" + getPersonPhone() + ", getPersonOpenid()=" + getPersonOpenid()
				+ ", getCreateTime()=" + getCreateTime() + ", getProvince()=" + getProvince() + ", getCity()="
				+ getCity() + ", getRegion()=" + getRegion() + ", getAddress()=" + getAddress() + ", getCoord()="
				+ getCoord() + ", getStatus()=" + getStatus() + ", getStoreId()=" + getStoreId() + ", getPilesNum()="
				+ getPilesNum() + ", getMaster()=" + getMaster() + ", getThirdServiceAmount()="
				+ getThirdServiceAmount() + ", getDCNum()=" + getDCNum() + ", getACNum()=" + getACNum()
				+ ", getPlanUseTime()=" + getPlanUseTime() + ", toString()=" + super.toString() + ", getPageNum()="
				+ getPageNum() + ", getPageSize()=" + getPageSize() + ", getTotal()=" + getTotal() + ", getList()="
				+ getList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
