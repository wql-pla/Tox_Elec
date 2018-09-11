package com.tox.bean;

import java.util.Date;
import java.util.List;

public class ElecRole extends PageView<ElecRole>{
    private Integer id;

    private String rolename; //角色名称

    private Date createdate; //创建时间

    private Integer organid; //组织机构id
    
    private String organName; //组织机构名称
    
    private String region; //地区

    private String department; //部门名称--所属部门
    
    private List<ElecBasisRoleRel> basisList;
    
    public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getOrganid() {
        return organid;
    }

    public void setOrganid(Integer organid) {
        this.organid = organid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }
    
	public List<ElecBasisRoleRel> getBasisList() {
		return basisList;
	}

	public void setBasisList(List<ElecBasisRoleRel> basisList) {
		this.basisList = basisList;
	}
	
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Override
	public String toString() {
		return "ElecRole [id=" + id + ", rolename=" + rolename + ", createdate=" + createdate + ", organid=" + organid
				+ ", department=" + department + ", basisList=" + basisList + "]";
	}
    
}