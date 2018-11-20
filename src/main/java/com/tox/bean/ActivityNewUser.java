package com.tox.bean;

import java.util.Date;

public class ActivityNewUser extends PageView<ActivityNewUser> {
    private Integer id;

    private String phone;

    private Date createDate;

    private Integer isDel;

    private String isPay;

    private Date fromDate;

    private Date toDate;

    private String type;

    private String city;
    
    private Integer isSign;
    //---------------------------------查询条件非映射------------------------------------------------

    //月卡状态
    private Integer monthStatus;
    //开始时间-针对用户管理报名日期
    //
    private Date startDate;

    private Date endDate;
    
    private Date firstOnlineDate;
    
    private Integer monthDay;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay == null ? null : isPay.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getMonthStatus() {
		return monthStatus;
	}

	public void setMonthStatus(Integer monthStatus) {
		this.monthStatus = monthStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "ActivityNewUser [id=" + id + ", phone=" + phone + ", createDate=" + createDate + ", isDel=" + isDel
				+ ", isPay=" + isPay + ", fromDate=" + fromDate + ", toDate=" + toDate + ", type=" + type + ", city="
				+ city + ", isSign=" + isSign + ", monthStatus=" + monthStatus + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", firstOnlineDate=" + firstOnlineDate + "]";
	}

	public Date getEndDate() {
		return endDate;
	}
	

	public Date getFirstOnlineDate() {
		return firstOnlineDate;
	}

	public void setFirstOnlineDate(Date firstOnlineDate) {
		this.firstOnlineDate = firstOnlineDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(Integer monthDay) {
		this.monthDay = monthDay;
	}
	

	
    
}