package com.tox.bean;

public class SnElecVo {
	private String currentChargeState;
	private String chargeQuantity;
	private String state;
	private String pileState;
	private String reserve2;
	private String reserve1;
	private String isOpen;;
	private String time;
	private String price;
	private String interState;
	private String remainTime;
	private String voltage;
	private String power;
	private String electricity;
	private String faultCode;
	private String partState;
	public String getCurrentChargeState() {
		return currentChargeState;
	}
	public void setCurrentChargeState(String currentChargeState) {
		this.currentChargeState = currentChargeState;
	}
	public String getChargeQuantity() {
		return chargeQuantity;
	}
	public void setChargeQuantity(String chargeQuantity) {
		this.chargeQuantity = chargeQuantity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPileState() {
		return pileState;
	}
	public void setPileState(String pileState) {
		this.pileState = pileState;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getInterState() {
		return interState;
	}
	public void setInterState(String interState) {
		this.interState = interState;
	}
	public String getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getElectricity() {
		return electricity;
	}
	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getPartState() {
		return partState;
	}
	public void setPartState(String partState) {
		this.partState = partState;
	}
	@Override
	public String toString() {
		return "SnElecVo [currentChargeState=" + currentChargeState + ", chargeQuantity=" + chargeQuantity + ", state="
				+ state + ", pileState=" + pileState + ", reserve2=" + reserve2 + ", reserve1=" + reserve1 + ", isOpen="
				+ isOpen + ", time=" + time + ", price=" + price + ", interState=" + interState + ", remainTime="
				+ remainTime + ", voltage=" + voltage + ", power=" + power + ", electricity=" + electricity
				+ ", faultCode=" + faultCode + ", partState=" + partState + "]";
	}
	
	
	
}
