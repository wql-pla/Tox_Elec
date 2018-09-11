package com.tox.bean;

import java.util.Date;

public class ElecZJ extends PageView<ElecZJ>{

	private String phone;
	
	private Double czje;
	
	private Double sjzfje;
	
	private Double zftkje;
	
	private Double yetkje;
	
	private Double czjlje;
	
	private Double sjsy;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer type;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getCzje() {
		return czje;
	}

	public void setCzje(Double czje) {
		this.czje = czje;
	}

	public Double getSjzfje() {
		return sjzfje;
	}

	public void setSjzfje(Double sjzfje) {
		this.sjzfje = sjzfje;
	}

	public Double getZftkje() {
		return zftkje;
	}

	public void setZftkje(Double zftkje) {
		this.zftkje = zftkje;
	}

	public Double getYetkje() {
		return yetkje;
	}

	public void setYetkje(Double yetkje) {
		this.yetkje = yetkje;
	}

	public Double getCzjlje() {
		return czjlje;
	}

	public void setCzjlje(Double czjlje) {
		this.czjlje = czjlje;
	}

	public Double getSjsy() {
		return sjsy;
	}

	public void setSjsy(Double sjsy) {
		this.sjsy = sjsy;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ElecZJ [phone=" + phone + ", czje=" + czje + ", sjzfje=" + sjzfje + ", zftkje=" + zftkje + ", yetkje="
				+ yetkje + ", czjlje=" + czjlje + ", sjsy=" + sjsy + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", type=" + type + "]";
	}
	
	
}
