package com.tox.bean;

import java.util.Date;

public class ElecHoster {
    private Integer id;

    private String phone;

    private Integer type;
    private Date createTime;

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


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ElecHoster [id=" + id + ", phone=" + phone + ", type=" + type + ", createTime=" + createTime + "]";
	}
    
}