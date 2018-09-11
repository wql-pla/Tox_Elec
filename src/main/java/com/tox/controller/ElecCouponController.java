package com.tox.controller;


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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecCoupon;
import com.tox.bean.ElecOrder;
import com.tox.dao.ElecCouponMapper;
import com.tox.dao.ElecOrderMapper;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/coupon")
public class ElecCouponController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecCouponController.class);
	@Autowired
	private ElecCouponMapper couponDao;
	@Autowired
	private ElecOrderMapper orderDao;
	
	
	@RequestMapping(value="/selectOrdersByOpenId")
	public @ResponseBody Map<String,Object> selectOrdersByOpenIdPages(@RequestBody ElecCoupon coupon){
		logger.info(String.format("查询参数：%s", coupon.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		List<ElecCoupon> list = couponDao.selectListByPrimaryKey(coupon);
		map.put("result", list.get(0));
		return map;
	}
	@RequestMapping(value="/selectCouponById")
	public @ResponseBody Map<String,Object> selectCouponByOrderId(@RequestBody ElecCoupon coupon){
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info(String.format("查询参数：%s", coupon.toString()));
		ElecCoupon cou = couponDao.selectByPrimaryKey(coupon.getId());
		map.put("code", cou.getCode());
		return map;
	}

	

}
