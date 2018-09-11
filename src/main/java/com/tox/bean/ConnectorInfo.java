package com.tox.bean;

public class ConnectorInfo {
	private String connectorId;//充电设备接口编码
	private String connectorName;//充电设备接口名称
	private Integer connectorType;//充电设备接口类型
	private Integer voltageUpperLimits;//额定电压上限
	private Integer voltageLowerLimits;//额定电压下限
	private Integer current;//额定电流
	private float power;//额定功率
	private String parkNo;//车位号
	private Integer nationalStandard;//国家标准
	public String getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}
	public String getConnectorName() {
		return connectorName;
	}
	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}
	public Integer getConnectorType() {
		return connectorType;
	}
	public void setConnectorType(Integer connectorType) {
		this.connectorType = connectorType;
	}
	public Integer getVoltageUpperLimits() {
		return voltageUpperLimits;
	}
	public void setVoltageUpperLimits(Integer voltageUpperLimits) {
		this.voltageUpperLimits = voltageUpperLimits;
	}
	public Integer getVoltageLowerLimits() {
		return voltageLowerLimits;
	}
	public void setVoltageLowerLimits(Integer voltageLowerLimits) {
		this.voltageLowerLimits = voltageLowerLimits;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	public Integer getNationalStandard() {
		return nationalStandard;
	}
	public void setNationalStandard(Integer nationalStandard) {
		this.nationalStandard = nationalStandard;
	}
	@Override
	public String toString() {
		return "ConnectorInfo [connectorId=" + connectorId + ", connectorName=" + connectorName + ", connectorType="
				+ connectorType + ", voltageUpperLimits=" + voltageUpperLimits + ", voltageLowerLimits="
				+ voltageLowerLimits + ", current=" + current + ", power=" + power + ", parkNo=" + parkNo
				+ ", nationalStandard=" + nationalStandard + "]";
	}
	
}
