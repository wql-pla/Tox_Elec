package com.tox.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tox.bean.AccessToken;

//@Component
public class TokenTask {

	private static AccessToken token =null;
	
	public static AccessToken getToken(){
		if(null ==token){
			AccessToken accessToken = WeixinUtil.getAccessToken();
			token = accessToken;
			return accessToken;
		}
		return token;
	}
	//每一小时获取一次微信AccessToken
//	@Scheduled(cron="0 0 0/1 * * ?") 
	private void tokenTimerTask(){
		System.out.println("获取token 定时任务开始。。。。。。。");
		AccessToken accessToken = WeixinUtil.getAccessToken();
		System.out.println("获取token 定时任务结束。。。。。。。");
		System.out.println("token====="+accessToken);
		this.token =accessToken;
	}
}
