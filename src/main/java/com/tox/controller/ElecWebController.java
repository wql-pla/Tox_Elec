package com.tox.controller;


import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.service.ElecWebService;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@RequestMapping(value="/web")
public class ElecWebController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecWebController.class);
	@Autowired
	private ElecWebService webService;
	
	//正在充电页面
	@RequestMapping(value="/charging")
	public @ResponseBody Map<String,Object> login(Integer orderId,Integer userId) throws ParseException{
		logger.info("充电页请求参数：orderId="+orderId+"userId="+userId);
		Map<String, Object> map = webService.getOrderPileUserInfo(orderId, userId);
		logger.info("充电页面查询结果："+map.toString());
		return map;
	}

}
