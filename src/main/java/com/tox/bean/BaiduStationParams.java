package com.tox.bean;

import java.util.Arrays;

public class BaiduStationParams {

	private String lastQueryTime;//上次查询时间
	private Integer pageNo;//查询页码
	private Integer pageSize;//每页数量
	private Integer pageCount;//页码总数
	private Integer itemSize;//总记录条数
	private StationInfo[] stationInfos;//充电站信息列表
	private String token;
	private String cKey; //AES加密秘钥
	private String iv;//AES加密初始向量
	private String signKey;//签名秘钥
	public String getSignKey() {
		return signKey;
	}
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	public String getcKey() {
		return cKey;
	}
	public void setcKey(String cKey) {
		this.cKey = cKey;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLastQueryTime() {
		return lastQueryTime;
	}
	public void setLastQueryTime(String lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getItemSize() {
		return itemSize;
	}
	public void setItemSize(Integer itemSize) {
		this.itemSize = itemSize;
	}
	public StationInfo[] getStationInfos() {
		return stationInfos;
	}
	public void setStationInfos(StationInfo[] stationInfos) {
		this.stationInfos = stationInfos;
	}
	@Override
	public String toString() {
		return "BaiduStationParams [lastQueryTime=" + lastQueryTime + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", pageCount=" + pageCount + ", itemSize=" + itemSize + ", token=" + token + ", stationInfos="
				+ Arrays.toString(stationInfos) + "]";
	}
	
}
