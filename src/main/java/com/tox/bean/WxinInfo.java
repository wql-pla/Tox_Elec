package com.tox.bean;

public class WxinInfo {

    private String body; //订单描述
    private String out_trade_no;//商户订单号
    private String device_info; //设备信息
    private String fee_type; //货币类型
    private String total_fee; //支付金额
    private String spbill_create_ip; //IP地址
    private String nonce_str;//随机字符串
    private String notify_url; //回调地址
    private String trade_type; //支付类型App
    private String product_id; //产品id
    private String timestamp; //时间戳
    private String openid;//微信openid
    private String attach;//规则id
    private Integer userId;
    private String JSCODE;//小程序登陆code
    
    public String getJSCODE() {
		return JSCODE;
	}

	public void setJSCODE(String jSCODE) {
		JSCODE = jSCODE;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

	@Override
	public String toString() {
		return "WxinInfo [body=" + body + ", out_trade_no=" + out_trade_no + ", device_info=" + device_info
				+ ", fee_type=" + fee_type + ", total_fee=" + total_fee + ", spbill_create_ip=" + spbill_create_ip
				+ ", nonce_str=" + nonce_str + ", notify_url=" + notify_url + ", trade_type=" + trade_type
				+ ", product_id=" + product_id + ", timestamp=" + timestamp + ", openid=" + openid + ", attach="
				+ attach + ", userId=" + userId + "]";
	}

	


   
}
