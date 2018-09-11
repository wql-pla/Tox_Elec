package com.tox.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElecUser extends PageView<ElecUser>{
	
    private Integer id;

    private String phone;

    private String password;

    private Date passwordDate;

    private Date createDate;

    private String openId;

    private Double balance;
    
    private Double giveMoney;

    private Integer isdel;
    
    private Integer type;
    
    private Date startDate;
    
    private Integer couponsCount;
    
    private Integer oldUserId;
    
   /* private List<Coupon> coupons;
    
    public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}*/

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

	private Date endDate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getPasswordDate() {
        return passwordDate;
    }

    public void setPasswordDate(Date passwordDate) {
        this.passwordDate = passwordDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

	public Double getGiveMoney() {
		return giveMoney;
	}

	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}

	public Integer getCouponsCount() {
		return couponsCount;
	}

	public void setCouponsCount(Integer couponsCount) {
		this.couponsCount = couponsCount;
	}

	public Integer getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(Integer oldUserId) {
		this.oldUserId = oldUserId;
	}

	@Override
	public String toString() {
		return "ElecUser [id=" + id + ", phone=" + phone + ", password=" + password + ", passwordDate=" + passwordDate
				+ ", createDate=" + createDate + ", openId=" + openId + ", balance=" + balance + ", giveMoney="
				+ giveMoney + ", isdel=" + isdel + ", type=" + type + ", startDate=" + startDate + ", couponsCount="
				+ couponsCount + ", oldUserId=" + oldUserId + ", endDate=" + endDate + "]";
	}

}