package com.tox.bean;


/**
 * Controller返回对象基类.包含返回code和msg.Controller返回的所有消息都应该是该类型或者该类型的子类
 */
public class Message <T>{
    /**
     * 返回标识code;
     */
    int code;
    /**
     *msg 返回消息
     */
    String msg;

    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "Message [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}


}
