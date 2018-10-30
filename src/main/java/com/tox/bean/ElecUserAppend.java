package com.tox.bean;

import java.util.Date;

public class ElecUserAppend extends PageView<ElecUserAppend> {
    private Integer id;

    private String userPhone;

    private Integer stationId;

    private Date createDate;

    private Integer idDel;
    
    private String stationName;
    
    private String userAccount;
    
    private Date startDate;
    
    private Date endDate;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIdDel() {
        return idDel;
    }

    public void setIdDel(Integer idDel) {
        this.idDel = idDel;
    }

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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

    @Override
    public String toString() {
        return "ElecUserAppend{" +
                "id=" + id +
                ", userPhone='" + userPhone + '\'' +
                ", stationId=" + stationId +
                ", createDate=" + createDate +
                ", idDel=" + idDel +
                ", stationName='" + stationName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}