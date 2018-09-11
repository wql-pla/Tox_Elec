package com.tox.bean;

public class ElecPriceRule {
    private Integer id;
    //场站id
    private Integer stationId;
    //设置金额
    private Double setAmount;
    //优惠比例
    private Double discRatio;
    //购买用电度数
    private Double releCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Double getSetAmount() {
        return setAmount;
    }

    public void setSetAmount(Double setAmount) {
        this.setAmount = setAmount;
    }

    public Double getDiscRatio() {
        return discRatio;
    }

    public void setDiscRatio(Double discRatio) {
        this.discRatio = discRatio;
    }

    public Double getReleCount() {
        return releCount;
    }

    public void setReleCount(Double releCount) {
        this.releCount = releCount;
    }

	@Override
	public String toString() {
		return "ElecPriceRule [id=" + id + ", stationId=" + stationId + ", setAmount=" + setAmount + ", discRatio="
				+ discRatio + ", releCount=" + releCount + "]";
	}
    
}