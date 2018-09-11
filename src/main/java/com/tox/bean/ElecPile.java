package com.tox.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElecPile extends PageView<ElecPile> {
	
    private Integer id;
    //电装编号
    private String pileNum;
    //场站id
    private Integer chargeStandardId;
    
    private ElecStation station;
    //厂商id
    private Integer firmId;

    private ElecFirm firm;
    
    private Date createTime;
    //场站名称  查询用
    private String stationName;
    //厂商名称  查询用
    private String firmName;
    //电桩上线时间
    private Date onlineDate;
    
    private Date startDate;
    
    private Date endDate;
    
    private String storeName;
    
    private Integer count;
    
    private String wxCode;
    
    private String date_Ext;
    
    private String softVersion;//电桩软件版本
    
    private String updateStatus;//软件升级结果 0电桩不在线，1升级中，2升级完成,11:非本机程序 12:升级文件校验不对 13:升级文件不能成功下载 14:其它
    
    private Integer isUsed;//电桩是否被使用：0：未使用；1：正在使用
    private Integer updateCount;//升级次数(电桩升级失败时，会做重新升级操作，最多重试三次）

    private Double allCount;//总耗电量
    
    private String serverIp;//服务器ip
    
    private String serverPort;//服务器端口号
    
    private Integer ipStatus;//修改服务器ip是否成功
    private Integer type;//电桩类型，1交流；2直流
    
    
	public Double getAllCount() {
		return allCount;
	}

	public void setAllCount(Double allCount) {
		this.allCount = allCount;
	}

	//总用电度数
    
	public String getDate_Ext() {
		return date_Ext;
	}

	public void setDate_Ext(String date_Ext) {
		this.date_Ext = date_Ext;
	}

	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}
	
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	protected List<ElecPriceRule> list;
    private Integer status;//电桩启用状态 1：启用；0：停用
    
    
    public List<ElecPriceRule> getRuleList() {
		return list;
	}

	public void setRuleList(List<ElecPriceRule> list) {
		this.list = list;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPileNum() {
        return pileNum;
    }

    public void setPileNum(String pileNum) {
        this.pileNum = pileNum == null ? null : pileNum.trim();
    }

    public Integer getChargeStandardId() {
        return chargeStandardId;
    }

    public void setChargeStandardId(Integer chargeStandardId) {
        this.chargeStandardId = chargeStandardId;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public ElecStation getStation() {
		return station;
	}

	public void setStation(ElecStation station) {
		this.station = station;
	}

	public ElecFirm getFirm() {
		return firm;
	}

	public void setFirm(ElecFirm firm) {
		this.firm = firm;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Integer getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(Integer updateCount) {
		this.updateCount = updateCount;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public Integer getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(Integer ipStatus) {
		this.ipStatus = ipStatus;
	}
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ElecPile [id=" + id + ", pileNum=" + pileNum + ", chargeStandardId=" + chargeStandardId + ", station="
				+ station + ", firmId=" + firmId + ", firm=" + firm + ", createTime=" + createTime + ", stationName="
				+ stationName + ", firmName=" + firmName + ", onlineDate=" + onlineDate + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", storeName=" + storeName + ", count=" + count + ", wxCode=" + wxCode
				+ ", date_Ext=" + date_Ext + ", softVersion=" + softVersion + ", updateStatus=" + updateStatus
				+ ", isUsed=" + isUsed + ", updateCount=" + updateCount + ", allCount=" + allCount + ", serverIp="
				+ serverIp + ", serverPort=" + serverPort + ", ipStatus=" + ipStatus + ", type=" + type + ", list="
				+ list + ", status=" + status + "]";
	}

}