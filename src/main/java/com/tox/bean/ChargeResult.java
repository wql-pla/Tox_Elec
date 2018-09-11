package com.tox.bean;

import java.math.BigDecimal;

public class ChargeResult {

	private String serial;//交易流水号    BCD 码16Byte
	private String highestAllowVoltage;//充电输出电压(直 流最大输出电压)    BIN    2  精确到小数点后一位
	private String highestAllowElectricity;//充电输出电流(直 流最大输出电流)    BIN    2  单位：A，精确到小数点后二位
	private String outputRelayStatus;//输出继电器状态  BIN 码 1Byte 布尔型, 变化上传;0 断开，1:闭合
	private String switchStatus;//连接确认开关状态 BIN 码 1Byte 变化上传;0:断开， 1:连接，2:可充电， 3:故障状态
	private String activElectricalDegree;//有功总电度 BIN 码 4Byte  精确到小数点后两位
	private String pileNo;//桩编号 8位 BCD
	private String connectBattery;//是否连接电池 BIN 码 1Byte
	private String workStatus;// 工作状态 BCD 码 1Byte 0x00 离线，0x01 故障 0x02 待机，0x03 充电 04 停止充电 0x10 暂停，0x11 维护 0x12 测试
	
	/**
	 * 故障状态 Bin 码 1Byte 共 8bit
	 * 第 0bit:读卡器状态 0:正常，1 故障
	 * 第 1bit:电表状态 0: 正常，1 故障
	 * 第 2bit:急停状态 0: 正常，1 故障
	 * 第 3bit:过压状态 0: 正常，1 故障
	 * 第 4bit:欠压状态 0: 正常，1 故障
	 * 第 5it:过流状态 0: 正常，1 故障
	 * 第 6bit:充电机状态 0:正常，1 故障
	 * 第 7bit:其它状态 0: 正常，1 故障
	 */
	private String troubleStatus;
	private String chargeDuration;//充电时长 BIN 2 字节 分
	private BigDecimal currentChargeQuantity;//本次充电电量 BIN 4 字节
	private String orderNo;//订单号 ascii 32位小端
	//桩类型
	private int pileType;
	//枪号
	private int gunNo;
	//充电输出电压(直 流最大输出电压)    BIN    2  精确到小数点后一位

	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getHighestAllowVoltage() {
		return highestAllowVoltage;
	}
	public void setHighestAllowVoltage(String highestAllowVoltage) {
		this.highestAllowVoltage = highestAllowVoltage;
	}
	public String getHighestAllowElectricity() {
		return highestAllowElectricity;
	}
	public void setHighestAllowElectricity(String highestAllowElectricity) {
		this.highestAllowElectricity = highestAllowElectricity;
	}
	public String getOutputRelayStatus() {
		return outputRelayStatus;
	}
	public void setOutputRelayStatus(String outputRelayStatus) {
		this.outputRelayStatus = outputRelayStatus;
	}
	public String getSwitchStatus() {
		return switchStatus;
	}
	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}
	public String getActivElectricalDegree() {
		return activElectricalDegree;
	}
	public void setActivElectricalDegree(String activElectricalDegree) {
		this.activElectricalDegree = activElectricalDegree;
	}
	public String getPileNo() {
		return pileNo;
	}
	public void setPileNo(String pileNo) {
		this.pileNo = pileNo;
	}
	public String getConnectBattery() {
		return connectBattery;
	}
	public void setConnectBattery(String connectBattery) {
		this.connectBattery = connectBattery;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getTroubleStatus() {
		return troubleStatus;
	}
	public void setTroubleStatus(String troubleStatus) {
		this.troubleStatus = troubleStatus;
	}
	public String getChargeDuration() {
		return chargeDuration;
	}
	public void setChargeDuration(String chargeDuration) {
		this.chargeDuration = chargeDuration;
	}
	public BigDecimal getCurrentChargeQuantity() {
		return currentChargeQuantity;
	}
	public void setCurrentChargeQuantity(BigDecimal currentChargeQuantity) {
		this.currentChargeQuantity = currentChargeQuantity;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
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
	@Override
	public String toString() {
		return "ChargeResult [serial=" + serial + ", highestAllowVoltage=" + highestAllowVoltage
				+ ", highestAllowElectricity=" + highestAllowElectricity + ", outputRelayStatus=" + outputRelayStatus
				+ ", switchStatus=" + switchStatus + ", activElectricalDegree=" + activElectricalDegree + ", pileNo="
				+ pileNo + ", connectBattery=" + connectBattery + ", workStatus=" + workStatus + ", troubleStatus="
				+ troubleStatus + ", chargeDuration=" + chargeDuration + ", currentChargeQuantity="
				+ currentChargeQuantity + ", orderNo=" + orderNo + ", pileType=" + pileType + ", gunNo=" + gunNo + "]";
	}
}
