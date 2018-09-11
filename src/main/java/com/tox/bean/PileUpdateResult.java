package com.tox.bean;

public class PileUpdateResult {
	
	private String pileNo;//电桩号
	private String softVersion;//软件版本号
	private String status;//升级结果  0 充电桩链接不可用 1 充电桩链接可用
	private String tradeTypeCode;//厂商类型(1:好易充；2:循道)
	private String msg;
	private Integer pileType;//充电桩类型 3:交流桩单枪;4:交流桩双枪;5:直流桩单枪;6:直流桩双枪
	
	public String getPileNo() {
		return pileNo;
	}

	public void setPileNo(String pileNo) {
		this.pileNo = pileNo;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeTypeCode() {
		return tradeTypeCode;
	}

	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}

	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getPileType() {
		return pileType;
	}

	public void setPileType(Integer pileType) {
		this.pileType = pileType;
	}

	@Override
	public String toString() {
		return "PileUpdateResult [pileNo=" + pileNo + ", softVersion=" + softVersion + ", status=" + status
				+ ", tradeTypeCode=" + tradeTypeCode + ", msg=" + msg + ", pileType=" + pileType + "]";
	}


}
