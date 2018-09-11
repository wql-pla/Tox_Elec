package com.tox.bean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ElecOrder extends PageView<ElecOrder> {
    private Integer id;
    //订单状态 
    private String status;
    //付款总金额
    private Double elecTotalAmount;
    //充电总度数
    private Double elecTotalCount;
    private Double elecPrice;//电价
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //结束时间
//    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    //充电用户openid
    private String openId;
    //兑换码id
    private Integer couponId;
    //兑换码状态 1已绑定未兑换，2已兑换，3已过期
    private String couponStatus;
    
    private ElecCoupons coupon;
    
    //电桩id
    private Integer pileId;
    //电桩pojo
    private ElecPile pile;
    //电桩编号
    private String pileNum;
    //我方实际服务费
    private Double serviceChargeTotalSelf;
    //他方实际服务费
    private Double serviceChargeTotalThird;
    //实际电费
    private Double basicChargeTotal;
    //订单来源
    private String orderSource;
    //优惠前充电金额
    private Double orderFee;
    //实际充电金额
    private Double realAmount;
    //实际充电度数
    private Double realCount;
    //服务费打款状态
    private String servicePayStatus;
    //电费打款状态
    private String basicPayStatus;
    //条件查询时用
    private String stationName;
    //条件查询时用
    private Integer stationId;
    
    private String firmName;
    //电桩位置
    private String pilePosition;
    //电桩发布时间
    private Date onLineDate;
    //查询开始时间
    private Date startDate;
    //查询结束时间
    private Date endDate;

	//用户手机号
    private String phone;
    //所属门店
    private String storeName;
    //庄站所属城市
    private String city;
    //订单类型 1：内部人员充电订单；2：普通用户充电订单
    private Integer type;
    
    private Double sumMoney;
    
    private List<ElecRecharge> recharges;
    
    private Integer gunNo;//枪号
    
    private String useOpenid;
    private Integer chargeType;//是否跨时段 1全时 2 分时
    
    private List<ElecOrderDetail> details;
    

	public List<ElecOrderDetail> getDetails() {
		return details;
	}
    private List<ElecOrderDetail> pageDetails;
    
    public List<ElecOrderDetail> getPageDetails() {
		return pageDetails;
	}

	public void setPageDetails(List<ElecOrderDetail> pageDetails) {
		this.pageDetails = pageDetails;
	}


	public void setDetails(List<ElecOrderDetail> details) {
		this.details = details;
	}

	public String getUseOpenid() {
		return useOpenid;
	}

	public void setUseOpenid(String useOpenid) {
		this.useOpenid = useOpenid;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
    
    public List<ElecRecharge> getRecharges() {
		return recharges;
	}

	public void setRecharges(List<ElecRecharge> recharges) {
		this.recharges = recharges;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	private Long timeDiff;
    
    public Long getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(Long timeDiff) {
		this.timeDiff = timeDiff;
	}

	public String getPilePosition() {
		return pilePosition;
	}

	public void setPilePosition(String pilePosition) {
		this.pilePosition = pilePosition;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Double getElecTotalAmount() {
        return elecTotalAmount;
    }

    public void setElecTotalAmount(Double elecTotalAmount) {
        this.elecTotalAmount = elecTotalAmount;
    }

    public Double getElecTotalCount() {
        return elecTotalCount;
    }

    public void setElecTotalCount(Double elecTotalCount) {
        this.elecTotalCount = elecTotalCount;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus == null ? null : couponStatus.trim();
    }

    public String getPileNum() {
		return pileNum;
	}

	public void setPileNum(String pileNum) {
		this.pileNum = pileNum;
	}

	public Integer getPileId() {
        return pileId;
    }

    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }

    public ElecPile getPile() {
		return pile;
	}

	public void setPile(ElecPile pile) {
		this.pile = pile;
	}

	public Double getServiceChargeTotalSelf() {
        return serviceChargeTotalSelf;
    }

    public void setServiceChargeTotalSelf(Double serviceChargeTotalSelf) {
        this.serviceChargeTotalSelf = serviceChargeTotalSelf;
    }

    public Double getServiceChargeTotalThird() {
        return serviceChargeTotalThird;
    }

    public void setServiceChargeTotalThird(Double serviceChargeTotalThird) {
        this.serviceChargeTotalThird = serviceChargeTotalThird;
    }

    public Double getBasicChargeTotal() {
        return basicChargeTotal;
    }

    public void setBasicChargeTotal(Double basicChargeTotal) {
        this.basicChargeTotal = basicChargeTotal;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource == null ? null : orderSource.trim();
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Double getRealCount() {
        return realCount;
    }

    public void setRealCount(Double realCount) {
        this.realCount = realCount;
    }

    public String getServicePayStatus() {
        return servicePayStatus;
    }

    public void setServicePayStatus(String servicePayStatus) {
        this.servicePayStatus = servicePayStatus == null ? null : servicePayStatus.trim();
    }

    public String getBasicPayStatus() {
        return basicPayStatus;
    }

    public void setBasicPayStatus(String basicPayStatus) {
        this.basicPayStatus = basicPayStatus == null ? null : basicPayStatus.trim();
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
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ElecCoupons getCoupon() {
		return coupon;
	}

	public void setCoupon(ElecCoupons coupon) {
		this.coupon = coupon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getGunNo() {
		return gunNo;
	}

	public void setGunNo(Integer gunNo) {
		this.gunNo = gunNo;
	}

	public Date getOnLineDate() {
		return onLineDate;
	}

	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}
	

	public Double getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(Double orderFee) {
		this.orderFee = orderFee;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Double getElecPrice() {
		return elecPrice;
	}

	public void setElecPrice(Double elecPrice) {
		this.elecPrice = elecPrice;
	}
	

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	@Override
	public String toString() {
		return "ElecOrder [id=" + id + ", status=" + status + ", elecTotalAmount=" + elecTotalAmount
				+ ", elecTotalCount=" + elecTotalCount + ", elecPrice=" + elecPrice + ", createTime=" + createTime
				+ ", endTime=" + endTime + ", openId=" + openId + ", couponId=" + couponId + ", couponStatus="
				+ couponStatus + ", coupon=" + coupon + ", pileId=" + pileId + ", pile=" + pile + ", pileNum=" + pileNum
				+ ", serviceChargeTotalSelf=" + serviceChargeTotalSelf + ", serviceChargeTotalThird="
				+ serviceChargeTotalThird + ", basicChargeTotal=" + basicChargeTotal + ", orderSource=" + orderSource
				+ ", orderFee=" + orderFee + ", realAmount=" + realAmount + ", realCount=" + realCount
				+ ", servicePayStatus=" + servicePayStatus + ", basicPayStatus=" + basicPayStatus + ", stationName="
				+ stationName + ", stationId=" + stationId + ", firmName=" + firmName + ", pilePosition=" + pilePosition
				+ ", onLineDate=" + onLineDate + ", startDate=" + startDate + ", endDate=" + endDate + ", phone="
				+ phone + ", storeName=" + storeName + ", city=" + city + ", type=" + type + ", sumMoney=" + sumMoney
				+ ", recharges=" + recharges + ", gunNo=" + gunNo + ", useOpenid=" + useOpenid + ", chargeType="
				+ chargeType + ", details=" + details + ", pageDetails=" + pageDetails + ", timeDiff=" + timeDiff + "]";
	}

}