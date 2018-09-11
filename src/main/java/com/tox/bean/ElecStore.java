package com.tox.bean;

public class ElecStore extends  PageRequest{
    private Integer id;

    private String name;

    private String address;

    private String tel;

    private String linkman;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

	@Override
	public String toString() {
		return "ElecStore [id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", linkman="
				+ linkman + "]";
	}
    
}