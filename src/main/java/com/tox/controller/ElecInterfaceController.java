package com.tox.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tox.utils.SystemUrlLinkUtil;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/elecController")  
public class ElecInterfaceController {
	String urlopen="http://60.205.159.75:8080/ECharge/";
	
	
	/**
	 * 开启充电桩
	 * @param cpip 电装编号
	 * @param uid 用户openid
	 * @return
	 * @throws IOException
	 */
	
    	@RequestMapping("/open")
        public  String open(String cpip,String uid) throws IOException {
    		
    		String linkUrl = SystemUrlLinkUtil.linkUrl(urlopen+"Control/StartCharge?CPIP="+cpip+"&UID="+uid);
    		
            if(linkUrl.equals("0")){
            	String result = SystemUrlLinkUtil.linkUrl(urlopen+"Control/StartChargeResult");
            	if("000000".equals(result)){
            			return "SUCCESS";
            	}else if("ff0201".equals(result)){
            		  return "未连接充电枪";
            	}else if("ffffff".equals(result)){
          		  return "电桩异常";
            	}else if("-1".equals(result)){
            		  return "找不到开启充电的桩";
              	}else if("f".equals(result)){
              	 SystemUrlLinkUtil.linkUrl(urlopen+"Control/StartChargeResult");
              	 return "SUCCESS";
              	}
            	else{
              		return "电桩异常请您稍后再试";
              	}
            	
            }else if(linkUrl.equals("-1")){
            	return "电桩启动失败";
            }else if(linkUrl.equals("fff")){
            	return "该充电桩不是共享桩";
            }else if(linkUrl.equals("ff0201")){
            	return "未连接充电枪";
            }else if(linkUrl.equals("ff0202")){
            	return "充电桩离线";
            }else if(linkUrl.equals("ff0203")){
            	return "充电桩不空闲";
            }else if(linkUrl.equals("ff0203")){
            	return "找不到该编号的充电桩";
            }else{
            	return "电桩异常请您稍后再试";
            }
    		
        }
    	
    	/**
    	 * 关闭充电桩 
    	 * @param cpip 电装编号
    	 * @param uid 用户openid
    	 * @return
    	 * @throws IOException
    	 */
    	@RequestMapping("/close")
        public  String close(String cpip,String uid) throws IOException {
    		
    		String linkUrl = SystemUrlLinkUtil.linkUrl(urlopen+"Control/StopCharge?CPIP="+cpip+"&UID="+uid);
    		
            if(linkUrl.equals("0")){
            	String result = SystemUrlLinkUtil.linkUrl(urlopen+"Control/StopChargeResult");
            	if("000000".equals(result)){
            			return "SUCCESS";
            	}else{
            		  return "FAIL";
            	}
            }else{
            	return "FAIL"; 
            }
    		
        }
    	
    	
    	/**
    	 * 获取电桩心跳信息
    	 * @param cpip 电桩编号
    	 * @return
    	 * @throws IOException
    	 */
    	@RequestMapping("/getHeart")
        public  String getHeartMsg(String cpip) throws IOException {
    		String result = SystemUrlLinkUtil.linkUrl(urlopen+"Control/GetHeartMsg?CPIP=1710090101020203");
    		return result;
    	}
    	
    	/**
    	 * 获取用户最新一条充电记录
    	 * @param uid
    	 * @return
    	 * @throws IOException
    	 */
    	@RequestMapping("/getUserChgLog")
        public  String getUserChgLog(String uid) throws IOException {
    		String result = SystemUrlLinkUtil.linkUrl(urlopen+"Control/GetUserChgLog?UID=1710090101020203");
    		return result;
    	}
    		
}
