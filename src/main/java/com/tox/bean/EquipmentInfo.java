package com.tox.bean;

import java.util.Arrays;

public class EquipmentInfo {
	private String equipmentId;//设备编码
	private String manufacturerId;//设备生产商组织机构代码
	private String manufacturerName;//设备生产商名称
	private String equipmentModel;//设备型号
	private String productionDate;//设备生产日期
	private Integer equipmentType;//设备类型
	private ConnectorInfo[] connectorInfos;//充电设备接口信息列表
	private float equipmentLng;//充电设备经度
	private float equipmentLat;//充电设备维度
	private float power;//充电设备总功率
	private String equipmentName;//充电设备名称
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getEquipmentModel() {
		return equipmentModel;
	}
	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public ConnectorInfo[] getConnectorInfos() {
		return connectorInfos;
	}
	public void setConnectorInfos(ConnectorInfo[] connectorInfos) {
		this.connectorInfos = connectorInfos;
	}
	public float getEquipmentLng() {
		return equipmentLng;
	}
	public void setEquipmentLng(float equipmentLng) {
		this.equipmentLng = equipmentLng;
	}
	public float getEquipmentLat() {
		return equipmentLat;
	}
	public void setEquipmentLat(float equipmentLat) {
		this.equipmentLat = equipmentLat;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	@Override
	public String toString() {
		return "EquipmentInfo [equipmentId=" + equipmentId + ", manufacturerId=" + manufacturerId
				+ ", manufacturerName=" + manufacturerName + ", equipmentModel=" + equipmentModel + ", productionDate="
				+ productionDate + ", equipmentType=" + equipmentType + ", connectorInfos="
				+ Arrays.toString(connectorInfos) + ", equipmentLng=" + equipmentLng + ", equipmentLat=" + equipmentLat
				+ ", power=" + power + ", equipmentName=" + equipmentName + "]";
	}
		
}
