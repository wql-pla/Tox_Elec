package com.tox.bean;

public class ConnectorStatusInfo {

	private Integer connectorId;
	
	private Integer status;
	
	private Integer parkStatus;
	
	private Integer lockStatus;

	public Integer getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParkStatus() {
		return parkStatus;
	}

	public void setParkStatus(Integer parkStatus) {
		this.parkStatus = parkStatus;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	
}
