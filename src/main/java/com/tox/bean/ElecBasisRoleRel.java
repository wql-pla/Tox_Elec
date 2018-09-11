package com.tox.bean;

public class ElecBasisRoleRel {
    private Integer id;

    private Integer roleid; //角色外键

    private Integer basisid; //权限外键
    
    private Integer faid; //权限外键

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getBasisid() {
        return basisid;
    }

    public void setBasisid(Integer basisid) {
        this.basisid = basisid;
    }

	public Integer getFaid() {
		return faid;
	}

	public void setFaid(Integer faid) {
		this.faid = faid;
	}
    
}