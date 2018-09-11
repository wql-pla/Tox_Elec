package com.tox.bean;

import java.util.Arrays;

public class StationInfo {
	private String stationId;//充电桩id
	private String operatorId;//运营商id
	private String equipmentOwnerId;//设备所属运营平台组织机构代码
	private String stationName;//充电站名称
	private String countryCode;//充电站国家代码
	private String areaCode;//充电站省市辖区编码
	private String address;//详细地址
	private String stationTel;//站点电话
	private String serviceTel;//服务电话
	private Integer stationType;//站点 类型
	private Integer stationStatus;//站点状态
	private Integer parkNums;//车位数量
	private float stationLng;//经度
	private float stationLat;//维度
	private String siteGuide;//站点引导
	private Integer construction;//建设场所
	private String[] pictures;//站点照片
	private String coord;
	
	private String matchCars;//使用车型描述
	private String parkInfo;//车位楼层及数量描述
	private String busineHours;//营业时间
	private String electricityFee;//充电电费率
	private String serviceFee;//服务费率
	private String parkFee;//停车费
	private String payment;//支付方式
	private Integer supportOrder;//是否支持预约
	private String remark;//备注
	private EquipmentInfo [] equipmentInfos;//充电设备信息列表
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getEquipmentOwnerId() {
		return equipmentOwnerId;
	}
	public void setEquipmentOwnerId(String equipmentOwnerId) {
		this.equipmentOwnerId = equipmentOwnerId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStationTel() {
		return stationTel;
	}
	public void setStationTel(String stationTel) {
		this.stationTel = stationTel;
	}
	public String getServiceTel() {
		return serviceTel;
	}
	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}
	public Integer getStationType() {
		return stationType;
	}
	public void setStationType(Integer stationType) {
		this.stationType = stationType;
	}
	public Integer getStationStatus() {
		return stationStatus;
	}
	public void setStationStatus(Integer stationStatus) {
		this.stationStatus = stationStatus;
	}
	public Integer getParkNums() {
		return parkNums;
	}
	public void setParkNums(Integer parkNums) {
		this.parkNums = parkNums;
	}
	public float getStationLng() {
		return stationLng;
	}
	public void setStationLng(float stationLng) {
		this.stationLng = stationLng;
	}
	public float getStationLat() {
		return stationLat;
	}
	public void setStationLat(float stationLat) {
		this.stationLat = stationLat;
	}
	public String getSiteGuide() {
		return siteGuide;
	}
	public void setSiteGuide(String siteGuide) {
		this.siteGuide = siteGuide;
	}
	public Integer getConstruction() {
		return construction;
	}
	public void setConstruction(Integer construction) {
		this.construction = construction;
	}
	public String[] getPictures() {
		return pictures;
	}
	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}
	public String getCoord() {
		return coord;
	}
	public void setCoord(String coord) {
		this.coord = coord;
	}
	public String getMatchCars() {
		return matchCars;
	}
	public void setMatchCars(String matchCars) {
		this.matchCars = matchCars;
	}
	public String getParkInfo() {
		return parkInfo;
	}
	public void setParkInfo(String parkInfo) {
		this.parkInfo = parkInfo;
	}
	public String getBusineHours() {
		return busineHours;
	}
	public void setBusineHours(String busineHours) {
		this.busineHours = busineHours;
	}
	public String getElectricityFee() {
		return electricityFee;
	}
	public void setElectricityFee(String electricityFee) {
		this.electricityFee = electricityFee;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getParkFee() {
		return parkFee;
	}
	public void setParkFee(String parkFee) {
		this.parkFee = parkFee;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getSupportOrder() {
		return supportOrder;
	}
	public void setSupportOrder(Integer supportOrder) {
		this.supportOrder = supportOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public EquipmentInfo[] getEquipmentInfos() {
		return equipmentInfos;
	}
	public void setEquipmentInfos(EquipmentInfo[] equipmentInfos) {
		this.equipmentInfos = equipmentInfos;
	}
	@Override
	public String toString() {
		return "StationInfo [stationId=" + stationId + ", operatorId=" + operatorId + ", equipmentOwnerId="
				+ equipmentOwnerId + ", stationName=" + stationName + ", countryCode=" + countryCode + ", areaCode="
				+ areaCode + ", address=" + address + ", stationTel=" + stationTel + ", serviceTel=" + serviceTel
				+ ", stationType=" + stationType + ", stationStatus=" + stationStatus + ", parkNums=" + parkNums
				+ ", stationLng=" + stationLng + ", stationLat=" + stationLat + ", siteGuide=" + siteGuide
				+ ", construction=" + construction + ", pictures=" + Arrays.toString(pictures) + ", matchCars="
				+ matchCars + ", parkInfo=" + parkInfo + ", busineHours=" + busineHours + ", electricityFee="
				+ electricityFee + ", serviceFee=" + serviceFee + ", parkFee=" + parkFee + ", payment=" + payment
				+ ", supportOrder=" + supportOrder + ", remark=" + remark + ", equipmentInfos="
				+ Arrays.toString(equipmentInfos) + "]";
	}
	
}
