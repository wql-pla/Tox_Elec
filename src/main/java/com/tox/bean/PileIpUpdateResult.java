package com.tox.bean;

public class PileIpUpdateResult {
	
	private String pileNo;//电桩号
	private Integer status;//升级结果  0 充电桩链接不可用 1 充电桩链接可用
	private String msg;
	private String tradeTypeCode;
	private String addr;
	private String port;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "PileIpUpdateResult [pileNo=" + pileNo + ", status=" + status + ", msg=" + msg + ", tradeTypeCode="
				+ tradeTypeCode + ", addr=" + addr + ", port=" + port + ", pileType=" + pileType + "]";
	}




}
