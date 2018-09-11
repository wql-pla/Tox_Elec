package com.tox.bean;

public class QRCodeParams {
	
	private String pileNo;//充电桩编号
	private String tradeTypeCode;//厂商类型
	private Integer pileType;//充电桩类型 3:交流桩单枪;4:交流桩双枪;5:直流桩单枪;6:直流桩双枪
	public String getPileNo() {
		return pileNo;
	}
	public void setPileNo(String pileNo) {
		this.pileNo = pileNo;
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
	@Override
	public String toString() {
		return "QRCodeParams [pileNo=" + pileNo + ", tradeTypeCode=" + tradeTypeCode + ", pileType=" + pileType + "]";
	}
	

}
