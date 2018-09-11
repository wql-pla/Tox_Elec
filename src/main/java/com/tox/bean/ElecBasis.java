package com.tox.bean;

import java.util.List;

public class ElecBasis {
    private Integer id;

    private Integer faid; //父级目录外键

    private String name; //权限名称

    private String url; //权限地址

    private Integer tops; //排序
    
    private List<ElecBasis> bason; //子级列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaid() {
        return faid;
    }

    public void setFaid(Integer faid) {
        this.faid = faid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getTops() {
        return tops;
    }

    public void setTops(Integer tops) {
        this.tops = tops;
    }

	public List<ElecBasis> getBason() {
		return bason;
	}

	public void setBason(List<ElecBasis> bason) {
		this.bason = bason;
	}
	
}