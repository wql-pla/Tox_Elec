package com.tox.bean;

import java.util.Date;
import java.util.List;

public class ElecStation extends PageView<ElecStation> {
    private Integer id;
    
    private Double price;

    private String stationName;

    private Double serviceChargeAmount;

    private Double basicChargeAmount;

    private String personName;

    private String personPhone;
    private List<String> phones;//车位东子账号

    private boolean isMonthlyRent;

    private String personOpenid;
    
    private Integer personType;//车位东类型 2018-5-30 1个人 2普通

    private Date createTime;

    private String province;

    private String city;

    private String region;

    private String address;

    private String coord;

    private Integer status;//状态

    private Integer storeId;
    
    private Integer pilesNum;//场站中电桩数量
    
    private Integer DCNum;//预计建设直流桩数量
    
    private Integer ACNum;//预计建设交流桩数量
    
    private Date planUseTime;//预计运营时间

	//查询开始时间
    private Date startDate;
    //查询结束时间
    private Date endDate;
    
    //分润服务费
    private Double thirdServiceAmount;
    
    //个人车位东所收取的三方服务费--- 2018-5-30
    private Double personServiceAmount;
    //个人车位东所收取的基础电费
    private Double personBasicChargeAmount;
    
    private ElecPriceTemplateMaster master;//价格模板

    //是否有直流电桩
    private Integer isDirect;
    
    //是否正式
    private Integer isFormal;
    
    public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

    private Integer chargeType;								//电费类型，1全天，2分时段
    
    private List<ElecStationNorm> normList;				//电价列表
    
    public Integer getIsFormal(){
		return isFormal;
	}

	public void setIsFormal(Integer isFormal) {
		this.isFormal = isFormal;
	}

	public Integer getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(Integer isDirect) {
		this.isDirect = isDirect;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public Double getPersonServiceAmount() {
		return personServiceAmount;
	}

	public void setPersonServiceAmount(Double personServiceAmount) {
		this.personServiceAmount = personServiceAmount;
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public Double getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(Double serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public Double getBasicChargeAmount() {
        return basicChargeAmount;
    }

    public void setBasicChargeAmount(Double basicChargeAmount) {
        this.basicChargeAmount = basicChargeAmount;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone == null ? null : personPhone.trim();
    }

    public String getPersonOpenid() {
        return personOpenid;
    }

    public void setPersonOpenid(String personOpenid) {
        this.personOpenid = personOpenid == null ? null : personOpenid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord == null ? null : coord.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

	public Integer getPilesNum() {
		return pilesNum;
	}

	public void setPilesNum(Integer pilesNum) {
		this.pilesNum = pilesNum;
	}

	public ElecPriceTemplateMaster getMaster() {
		return master;
	}

	public void setMaster(ElecPriceTemplateMaster master) {
		this.master = master;
	}

	public Double getThirdServiceAmount() {
		return thirdServiceAmount;
	}

	public void setThirdServiceAmount(Double thirdServiceAmount) {
		this.thirdServiceAmount = thirdServiceAmount;
	}

	public Integer getDCNum() {
		return DCNum;
	}

	public void setDCNum(Integer dCNum) {
		DCNum = dCNum;
	}

	public Integer getACNum() {
		return ACNum;
	}

	public void setACNum(Integer aCNum) {
		ACNum = aCNum;
	}

	public Date getPlanUseTime() {
		return planUseTime;
	}

	public void setPlanUseTime(Date planUseTime) {
		this.planUseTime = planUseTime;
	}
	
	public List<ElecStationNorm> getNormList() {
		return normList;
	}

	public void setNormList(List<ElecStationNorm> normList) {
		this.normList = normList;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
    public Double getPersonBasicChargeAmount() {
        return personBasicChargeAmount;
    }

    public void setPersonBasicChargeAmount(Double personBasicChargeAmount) {
        this.personBasicChargeAmount = personBasicChargeAmount;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public boolean isMonthlyRent() {
        return isMonthlyRent;
    }

    public void setMonthlyRent(boolean monthlyRent) {
        isMonthlyRent = monthlyRent;
    }

    @Override
    public String toString() {
        return "ElecStation{" +
                "id=" + id +
                ", price=" + price +
                ", stationName='" + stationName + '\'' +
                ", serviceChargeAmount=" + serviceChargeAmount +
                ", basicChargeAmount=" + basicChargeAmount +
                ", personName='" + personName + '\'' +
                ", personPhone='" + personPhone + '\'' +
                ", phones=" + phones +
                ", personOpenid='" + personOpenid + '\'' +
                ", personType=" + personType +
                ", createTime=" + createTime +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                ", coord='" + coord + '\'' +
                ", status=" + status +
                ", storeId=" + storeId +
                ", pilesNum=" + pilesNum +
                ", DCNum=" + DCNum +
                ", ACNum=" + ACNum +
                ", planUseTime=" + planUseTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", thirdServiceAmount=" + thirdServiceAmount +
                ", personServiceAmount=" + personServiceAmount +
                ", personBasicChargeAmount=" + personBasicChargeAmount +
                ", master=" + master +
                ", isDirect=" + isDirect +
                ", isFormal=" + isFormal +
                ", chargeType=" + chargeType +
                ", normList=" + normList +
                '}';
    }
}