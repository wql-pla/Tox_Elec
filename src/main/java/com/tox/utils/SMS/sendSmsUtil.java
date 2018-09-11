package com.tox.utils.SMS;

import com.alibaba.fastjson.JSONObject;
import com.tox.sms.config.AppConfig;
import com.tox.sms.config.ConfigLoader;
import com.tox.sms.config.MESSAGEXsend;
import com.tox.sms.config.MessageConfig;
import com.tox.utils.RandomUtil;


public class sendSmsUtil {
	
	private static String YZM_PROJECT = "COW4M1"; //强化短信模板
	
	/**
	 * 跳过充电警告短信
	 * @param phone
	 * @param stationName
	 * @return
	 */
	public static boolean sendCode(String phone,String YZM){
		AppConfig config = new MessageConfig();
		MESSAGEXsend submail = new MESSAGEXsend(config);
		submail.addTo(phone);
		submail.setProject(YZM_PROJECT);
		submail.addVar("code", YZM);
		String response=submail.xsend();
		JSONObject json = JSONObject.parseObject(response);
		String status = json.getString("status");
		if(status.equals("success")){
			return true;
		}
		System.out.println("接口返回数据："+response);
		return false;
	}
	
}