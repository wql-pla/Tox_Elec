package com.tox.bean;

import com.tox.utils.WeixinUtil;

public class WeiXinTest {


	public static void main(String[] args) {
		
		AccessToken accessToken = WeixinUtil.getAccessToken();
		
		System.out.println("票据："+accessToken.getToken());
		
		System.out.println("有效时间："+accessToken.getExpiresIn());
		
	}
}
