package com.tox.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.Admin;
import com.tox.bean.ElecCoupon;
import com.tox.bean.ElecHoster;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecUser;
import com.tox.bean.PageView;
import com.tox.dao.AdminMapper;
import com.tox.dao.ElecCouponMapper;
import com.tox.dao.ElecHosterMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecUserMapper;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/pileHoster")
public class ElecPileHosterController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecPileHosterController.class);
	@Autowired
	private ElecHosterMapper hosterDao;
	
	//新增
	@RequestMapping(value="/create")
	public @ResponseBody Map<String,Object> create(@RequestBody ElecHoster hoster){
		logger.info(String.format("新增桩东参数：%s", hoster.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		synchronized(ElecPileHosterController.class){
			ElecHoster elecHoster = hosterDao.getHosterByPhoneAndType(hoster);
			if(null !=elecHoster){
				//手机号已存在
				map.put("result", "101");
				return map;
			}
			hoster.setCreateTime(new Date());
			int flag = hosterDao.insertSelective(hoster);
			if(flag>0){
				map.put("result", "100");
			}else{
				map.put("result", "-100");
			}
			return map;
		}
	}

}
