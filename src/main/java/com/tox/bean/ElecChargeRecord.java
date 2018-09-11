package com.tox.bean;

import java.util.Date;

public class ElecChargeRecord {
    private Integer id;

    //实际应付金额
    private Double elecAmount;

    //购买度数
    private Double elecCount;

    //实际充电度数
    private Double realCount;
    
    //创建时间
    private Date createTime;

    //结束 时间
    private Date endTime;

    //订单id
    private Integer orderId;

    //我方实际服务费
    private Double serviceChargeSelf;

    //他方实际服务费
    private Double serviceChargeThird;

    //实际基础费
    private Double basicCharge;
    
    //微信支付订单编号
    private String payCord;
    
    //用户openId
    private String openId;
    
    //电桩id
    private Integer pileId;
    
    //价格规则id
    private Integer priceRuleId;
    
    //续充状态 0失败，1成功
    private Integer recordStatus;
    

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getElecAmount() {
        return elecAmount;
    }

    public void setElecAmount(Double elecAmount) {
        this.elecAmount = elecAmount;
    }

    public Double getElecCount() {
        return elecCount;
    }

    public void setElecCount(Double elecCount) {
        this.elecCount = elecCount;
    }

    public Double getRealCount() {
		return realCount;
	}

	public void setRealCount(Double realCount) {
		this.realCount = realCount;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getServiceChargeSelf() {
        return serviceChargeSelf;
    }

    public void setServiceChargeSelf(Double serviceChargeSelf) {
        this.serviceChargeSelf = serviceChargeSelf;
    }

    public Double getServiceChargeThird() {
        return serviceChargeThird;
    }

    public void setServiceChargeThird(Double serviceChargeThird) {
        this.serviceChargeThird = serviceChargeThird;
    }

    public Double getBasicCharge() {
        return basicCharge;
    }

    public void setBasicCharge(Double basicCharge) {
        this.basicCharge = basicCharge;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getPileId() {
		return pileId;
	}

	public void setPileId(Integer pileId) {
		this.pileId = pileId;
	}

	public String getPayCord() {
		return payCord;
	}

	public void setPayCord(String payCord) {
		this.payCord = payCord;
	}

	public Integer getPriceRuleId() {
		return priceRuleId;
	}

	public void setPriceRuleId(Integer priceRuleId) {
		this.priceRuleId = priceRuleId;
	}

	
	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "ElecChargeRecord [id=" + id + ", elecAmount=" + elecAmount + ", elecCount=" + elecCount + ", realCount="
				+ realCount + ", createTime=" + createTime + ", endTime=" + endTime + ", orderId=" + orderId
				+ ", serviceChargeSelf=" + serviceChargeSelf + ", serviceChargeThird=" + serviceChargeThird
				+ ", basicCharge=" + basicCharge + ", payCord=" + payCord + ", openId=" + openId + ", pileId=" + pileId
				+ ", priceRuleId=" + priceRuleId + ", recordStatus=" + recordStatus + "]";
	}
}