package com.tox.bean;

import java.util.List;

public class ElecPayTemplateMaster extends PageView<ElecPayTemplateMaster>{
    private Integer id;

    private String name;

    private Integer status;
    
    private List<ElecPayTemplateSub> subs;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<ElecPayTemplateSub> getSubs() {
		return subs;
	}

	public void setSubs(List<ElecPayTemplateSub> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "ElecPayTemplateMaster [id=" + id + ", name=" + name + ", status=" + status + ", subs=" + subs + "]";
	}
    
}