package com.tox.bean;

import java.util.Date;

public class ElecOrganization extends PageView<ElecOrganization> {
    private Integer id;

    private String name; //组织机构名称

    private String region; //地区

    private Date createdate; //创建时间

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

	@Override
	public String toString() {
		return "ElecOrganization [id=" + id + ", name=" + name + ", region=" + region + ", createdate=" + createdate
				+ "]";
	}
    
    
}