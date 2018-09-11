package com.tox.bean;

public class ElecStationNorm {
    private Integer id;

    private Integer type; 						//电费类型 1支流2交流

    private Double basicChargeAmount;			//基础电费

    private Double serviceChargeAmount;			//服务费

    private String fromDate;						//开始时间

    private String toDate;						//结束时间

    private Integer stationId;					//场站外键
    
    private Integer current; // 是否是当前时间段 0不是，1是
    
    private Integer next;//是否是下一时段 1是，0不是

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getBasicChargeAmount() {
        return basicChargeAmount;
    }

    public void setBasicChargeAmount(Double basicChargeAmount) {
        this.basicChargeAmount = basicChargeAmount;
    }

    public Double getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(Double serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	
	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "ElecStationNorm [id=" + id + ", type=" + type + ", basicChargeAmount=" + basicChargeAmount
				+ ", serviceChargeAmount=" + serviceChargeAmount + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", stationId=" + stationId + ", current=" + current + ", next=" + next + "]";
	}
}