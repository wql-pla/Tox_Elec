package com.tox.bean;

public class TookenParam {

	private String operatorID;
	
	private String operatorSecret;

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		if(operatorID == null){
			this.operatorID = "";
		}else{
			this.operatorID = operatorID;
		}
	}

	public String getOperatorSecret() {
		return operatorSecret;
	}

	public void setOperatorSecret(String operatorSecret) {
		if(operatorSecret == null){
			this.operatorSecret = "";
		}else {
			this.operatorSecret = operatorSecret;
		}
	}
	
	
	
}
