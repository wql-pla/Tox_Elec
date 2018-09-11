package com.tox.bean;

import java.util.Date;
import java.util.List;

public class ElecGroupCounponsF extends PageView<ElecGroupCounponsF>{
    private Integer id;

    private String name;

    private Date createTime;

    private Integer isDel;
    
    private Integer num;
    
    private List<ElecGroupCounponsZ> counponsZList;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<ElecGroupCounponsZ> getCounponsZList() {
		return counponsZList;
	}

	public void setCounponsZList(List<ElecGroupCounponsZ> counponsZList) {
		this.counponsZList = counponsZList;
	}

	@Override
	public String toString() {
		return "ElecGroupCounponsF [id=" + id + ", name=" + name + ", createTime=" + createTime + ", isDel=" + isDel
				+ ", num=" + num + ", counponsZList=" + counponsZList + "]";
	}

}