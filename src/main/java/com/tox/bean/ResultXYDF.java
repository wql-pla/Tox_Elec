package com.tox.bean;

public class ResultXYDF {
	
	private String orderNo;//订单号
	private String serial;//流水号
	private String endReason;//充电状态（也是结束原因）
	private String pileNo;//充电桩编号
	private String totalAmmeterDegree;//总充电度数
	private String tradeTypeCode;//厂商类型
	private Integer pileType;//充电桩类型 3:交流桩单枪;4:交流桩双枪;5:直流桩单枪;6:直流桩双枪
	private Integer gunNo;//充电枪枪号,单枪为 0，如果是双枪: A枪为0, B枪为1 
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getEndReason() {
		return endReason;
	}
	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}
	public String getPileNo() {
		return pileNo;
	}
	public void setPileNo(String pileNo) {
		this.pileNo = pileNo;
	}
	public String getTotalAmmeterDegree() {
		return totalAmmeterDegree;
	}
	public void setTotalAmmeterDegree(String totalAmmeterDegree) {
		this.totalAmmeterDegree = totalAmmeterDegree;
	}
	
	public String getTradeTypeCode() {
		return tradeTypeCode;
	}
	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}
	public Integer getPileType() {
		return pileType;
	}
	public void setPileType(Integer pileType) {
		this.pileType = pileType;
	}
	public Integer getGunNo() {
		return gunNo;
	}
	public void setGunNo(Integer gunNo) {
		this.gunNo = gunNo;
	}
	@Override
	public String toString() {
		return "ResultXYDF [orderNo=" + orderNo + ", serial=" + serial + ", endReason=" + endReason + ", pileNo="
				+ pileNo + ", totalAmmeterDegree=" + totalAmmeterDegree + ", tradeTypeCode=" + tradeTypeCode
				+ ", pileType=" + pileType + ", gunNo=" + gunNo + "]";
	}
	

}
