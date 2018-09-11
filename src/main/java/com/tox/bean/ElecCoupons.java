package com.tox.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElecCoupons extends PageView<ElecCoupons> {
    private Integer id;

    private String name;//名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date fromDate;//开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date toDate;//结束时间

    private Double amount;//优惠金额

    private Integer status;//优惠券使用类型 1直减，2打折，3免单 ,4满减

    private Integer type;//发放方式1兑换码2全网主动发放

    private Date createDate;//创建时间

    private Integer isDel;//逻辑删除
    
    private Integer useNum;//主动发放使用数量
    
    private Integer allNum;//主动发放总数量
    
    private Integer indateType;//有效期类型 1固定天数，2 具体日期
    
    private double reach;//满减额度
    
    private Integer days;//有效期天数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    
	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}

	public Integer getIndateType() {
		return indateType;
	}

	public void setIndateType(Integer indateType) {
		this.indateType = indateType;
	}

	public double getReach() {
		return reach;
	}

	public void setReach(double reach) {
		this.reach = reach;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "ElecCoupons [id=" + id + ", name=" + name + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", amount=" + amount + ", status=" + status + ", type=" + type + ", createDate=" + createDate
				+ ", isDel=" + isDel + ", useNum=" + useNum + ", allNum=" + allNum + ", indateType=" + indateType
				+ ", reach=" + reach + ", days=" + days + "]";
	}


    
}