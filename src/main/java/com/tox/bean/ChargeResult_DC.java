package com.tox.bean;

import java.math.BigDecimal;

public class ChargeResult_DC {

	//桩类型
	private int pileType;
	//枪号
	private int gunNo;
	//桩编号 8位 BCD
	private String pileNo;
	private int soc;//bin 1位 1%;
	//连接确认开关状态(跟交流不同) BIN 码 1Byte 变化上传;0:断开， 1:连接，2:故障状态
	private int switchStatus;
	//有功总电度 BIN 码 4Byte  精确到小数点后两位
	private BigDecimal activElectricalDegree;
	//充电输出电压(直 流最大输出电压)    BIN    2  精确到小数点后一位
	private BigDecimal dcAllowVoltage;
	//充电输出电流(直 流最大输出电流)    BIN    2  单位：A，精确到小数点后二位
	private BigDecimal dcAllowElectricity;
	//充电输出电压(直 流最大输出电压)    BIN    2  精确到小数点后一位
	private BigDecimal bmsAllowVoltage;
	//充电输出电流(直 流最大输出电流)    BIN    2  单位：A，精确到小数点后二位
	private BigDecimal bmsAllowElectricity;
	//充电模式 0恒压 1恒流 bin 2
	private int chargeType;
	//电池类型  bin 1 0x01-铅酸电池， 0x02-镍氢电池, 0x03-磷酸铁锂电池, 0x04-锰酸锂池, 0x05-钴酸锂电池, 0x06-三元次料电池, 0x07-聚合物锂离子 电池, 0x08-钛酸锂电池, 0xff-其他电池
	private int batteryType;
	//单体最高允许充电电压 bin 4Byte 精确一位
	private BigDecimal singleHighestAllowVoltage;
	//单体最高允许充电电流 bin 4Byte 精确两位
	private BigDecimal singleHighestAllowElectricity;
	//交流 A 相充电电压  BIN 码  2Byte  小端  精确一位
	private BigDecimal aCVoltageA;
	//交流 b 相充电电压  BIN 码  2Byte  小端  精确一位
	private BigDecimal aCVoltageB;
	//交流 c 相充电电压  BIN 码  2Byte  小端  精确一位
	private BigDecimal aCVoltageC;
	//交流 A 相充电电流  BIN 码  2Byte  小端  精确两位
	private BigDecimal aCElectricityA;
	//交流 B 相充电电流  BIN 码  2Byte  小端  精确两位
	private BigDecimal aCElectricityB;
	//交流 C 相充电电流  BIN 码  2Byte  小端  精确两位
	private BigDecimal aCElectricityC;
	// 工作状态(跟交流不同) BCD 码 1Byte 0x00-空闲 0x01-准备充电 0x02-充电中 0x03-充电结束 0x04-充电失败 0x05-系统故障
	private String workStatus;
	//故障状态(跟交流不一样) Bin 码 4Byte
	private int troubleStatus;
	//剩余充电时间 BIN 2 字节 分
	private int unDochargeDuration;
	//充电时长 BIN 2 字节 分
	private int chargeDuration;
	//本次充电电量 BIN 4 字节
	private BigDecimal currentChargeQuantity;
	//交易流水号    BCD 码16Byte
	private String serial;
	//订单号 ascii 32位小端
	private String orderNo;
	//充电桩最高允许充电电源  BIN    4
	private BigDecimal highestAllowVoltage;
	//充电桩最高功率  BIN    4  单位：A，
	private int highestAllowW;
	public int getPileType() {
		return pileType;
	}
	public void setPileType(int pileType) {
		this.pileType = pileType;
	}
	public int getGunNo() {
		return gunNo;
	}
	public void setGunNo(int gunNo) {
		this.gunNo = gunNo;
	}
	public String getPileNo() {
		return pileNo;
	}
	public void setPileNo(String pileNo) {
		this.pileNo = pileNo;
	}
	public int getSoc() {
		return soc;
	}
	public void setSoc(int soc) {
		this.soc = soc;
	}
	public int getSwitchStatus() {
		return switchStatus;
	}
	public void setSwitchStatus(int switchStatus) {
		this.switchStatus = switchStatus;
	}
	public BigDecimal getActivElectricalDegree() {
		return activElectricalDegree;
	}
	public void setActivElectricalDegree(BigDecimal activElectricalDegree) {
		this.activElectricalDegree = activElectricalDegree;
	}
	public BigDecimal getDcAllowVoltage() {
		return dcAllowVoltage;
	}
	public void setDcAllowVoltage(BigDecimal dcAllowVoltage) {
		this.dcAllowVoltage = dcAllowVoltage;
	}
	public BigDecimal getDcAllowElectricity() {
		return dcAllowElectricity;
	}
	public void setDcAllowElectricity(BigDecimal dcAllowElectricity) {
		this.dcAllowElectricity = dcAllowElectricity;
	}
	public BigDecimal getBmsAllowVoltage() {
		return bmsAllowVoltage;
	}
	public void setBmsAllowVoltage(BigDecimal bmsAllowVoltage) {
		this.bmsAllowVoltage = bmsAllowVoltage;
	}
	public BigDecimal getBmsAllowElectricity() {
		return bmsAllowElectricity;
	}
	public void setBmsAllowElectricity(BigDecimal bmsAllowElectricity) {
		this.bmsAllowElectricity = bmsAllowElectricity;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	public int getBatteryType() {
		return batteryType;
	}
	public void setBatteryType(int batteryType) {
		this.batteryType = batteryType;
	}
	public BigDecimal getSingleHighestAllowVoltage() {
		return singleHighestAllowVoltage;
	}
	public void setSingleHighestAllowVoltage(BigDecimal singleHighestAllowVoltage) {
		this.singleHighestAllowVoltage = singleHighestAllowVoltage;
	}
	public BigDecimal getSingleHighestAllowElectricity() {
		return singleHighestAllowElectricity;
	}
	public void setSingleHighestAllowElectricity(BigDecimal singleHighestAllowElectricity) {
		this.singleHighestAllowElectricity = singleHighestAllowElectricity;
	}
	public BigDecimal getaCVoltageA() {
		return aCVoltageA;
	}
	public void setaCVoltageA(BigDecimal aCVoltageA) {
		this.aCVoltageA = aCVoltageA;
	}
	public BigDecimal getaCVoltageB() {
		return aCVoltageB;
	}
	public void setaCVoltageB(BigDecimal aCVoltageB) {
		this.aCVoltageB = aCVoltageB;
	}
	public BigDecimal getaCVoltageC() {
		return aCVoltageC;
	}
	public void setaCVoltageC(BigDecimal aCVoltageC) {
		this.aCVoltageC = aCVoltageC;
	}
	public BigDecimal getaCElectricityA() {
		return aCElectricityA;
	}
	public void setaCElectricityA(BigDecimal aCElectricityA) {
		this.aCElectricityA = aCElectricityA;
	}
	public BigDecimal getaCElectricityB() {
		return aCElectricityB;
	}
	public void setaCElectricityB(BigDecimal aCElectricityB) {
		this.aCElectricityB = aCElectricityB;
	}
	public BigDecimal getaCElectricityC() {
		return aCElectricityC;
	}
	public void setaCElectricityC(BigDecimal aCElectricityC) {
		this.aCElectricityC = aCElectricityC;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public int getTroubleStatus() {
		return troubleStatus;
	}
	public void setTroubleStatus(int troubleStatus) {
		this.troubleStatus = troubleStatus;
	}
	public int getUnDochargeDuration() {
		return unDochargeDuration;
	}
	public void setUnDochargeDuration(int unDochargeDuration) {
		this.unDochargeDuration = unDochargeDuration;
	}
	public int getChargeDuration() {
		return chargeDuration;
	}
	public void setChargeDuration(int chargeDuration) {
		this.chargeDuration = chargeDuration;
	}
	public BigDecimal getCurrentChargeQuantity() {
		return currentChargeQuantity;
	}
	public void setCurrentChargeQuantity(BigDecimal currentChargeQuantity) {
		this.currentChargeQuantity = currentChargeQuantity;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getHighestAllowVoltage() {
		return highestAllowVoltage;
	}
	public void setHighestAllowVoltage(BigDecimal highestAllowVoltage) {
		this.highestAllowVoltage = highestAllowVoltage;
	}
	public int getHighestAllowW() {
		return highestAllowW;
	}
	public void setHighestAllowW(int highestAllowW) {
		this.highestAllowW = highestAllowW;
	}
	@Override
	public String toString() {
		return "ChargeResult_DC [pileType=" + pileType + ", gunNo=" + gunNo + ", pileNo=" + pileNo + ", soc=" + soc
				+ ", switchStatus=" + switchStatus + ", activElectricalDegree=" + activElectricalDegree
				+ ", dcAllowVoltage=" + dcAllowVoltage + ", dcAllowElectricity=" + dcAllowElectricity
				+ ", bmsAllowVoltage=" + bmsAllowVoltage + ", bmsAllowElectricity=" + bmsAllowElectricity
				+ ", chargeType=" + chargeType + ", batteryType=" + batteryType + ", singleHighestAllowVoltage="
				+ singleHighestAllowVoltage + ", singleHighestAllowElectricity=" + singleHighestAllowElectricity
				+ ", aCVoltageA=" + aCVoltageA + ", aCVoltageB=" + aCVoltageB + ", aCVoltageC=" + aCVoltageC
				+ ", aCElectricityA=" + aCElectricityA + ", aCElectricityB=" + aCElectricityB + ", aCElectricityC="
				+ aCElectricityC + ", workStatus=" + workStatus + ", troubleStatus=" + troubleStatus
				+ ", unDochargeDuration=" + unDochargeDuration + ", chargeDuration=" + chargeDuration
				+ ", currentChargeQuantity=" + currentChargeQuantity + ", serial=" + serial + ", orderNo=" + orderNo
				+ ", highestAllowVoltage=" + highestAllowVoltage + ", highestAllowW=" + highestAllowW + "]";
	}
	











}
