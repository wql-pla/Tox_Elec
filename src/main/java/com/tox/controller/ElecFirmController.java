package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleToLongFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecChargeRecord;
import com.tox.bean.ElecCoupon;
import com.tox.bean.ElecFirm;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecStation;
import com.tox.bean.PageView;
import com.tox.dao.ElecChargeRecordMapper;
import com.tox.dao.ElecCouponMapper;
import com.tox.dao.ElecFirmMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecStationMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/firm")
@Transactional
public class ElecFirmController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecFirmController.class);
	@Autowired
	private ElecFirmMapper firmDao;
	
	/**
	 * 带条件的分页查询
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/selectFirms")
	public @ResponseBody Map<String,Object> selectOrdersByOpenIdPages(@RequestBody ElecFirm firm){
		logger.info(String.format("查询参数：%s", firm.toString()));
		Map<String, Object> map = new HashMap<String, Object>();

		PageView<ElecFirm> pageView = new PageView<ElecFirm>();

		pageView.setPageSize(firm.getPageSize());

		firm.setPageNum(firm.getPageNum() * firm.getPageSize());
		List<ElecFirm> firms = firmDao.selectFirmsPages(firm);
		logger.info(String.format("查询结果：%s", firms.toString()));
		int count = firmDao.selectFirmsPagesPagesCount(firm);
		logger.info(String.format("总记录数：%s", Integer.valueOf(count).toString()));
		
		pageView.setList(firms);

		pageView.setTotal(count);
		map.put("data", pageView);
		return map;
	}
	
	/**
	 * 根据厂商id查详情
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/selectFirmById")
	public @ResponseBody Map<String,Object> selectFirmById(@RequestBody ElecFirm firm){
		logger.info(String.format("查询参数：%s", firm.toString()));
		Map<String, Object> map = new HashMap<String, Object>();

		ElecFirm elecFirm = firmDao.selectByPrimaryKey(firm.getId());
		logger.info(String.format("查询结果：%s", elecFirm.toString()));
		map.put("firm", elecFirm);
		return map;
	}
	
	
	/**
	 * 新增厂商
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/createFirm")
	public @ResponseBody Map<String,Object> createOrder(@RequestBody ElecFirm firm){
		logger.info(String.format("要插入的订单信息：%s", firm.toString()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int status = firmDao.insertSelective(firm);
		if (1 == status) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}
	
	/**
	 * 修改厂商
	 * @param
	 * @return
	 */
	@RequestMapping(value="/editFirm")
	public @ResponseBody Map<String, Object> editOrder(@RequestBody ElecFirm firm) {
		logger.info(String.format("修改信息为：%s", firm.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		int status = firmDao.updateByPrimaryKeySelective(firm);
		if (1 == status) {
			map.put("result", true);

		} else {
			map.put("result", false);
		}
		return map;
	}
	
	/**
	 * 获取所有厂商名
	 * @param firm
	 * @return
	 */
	@RequestMapping(value="/getFirmNames")
	public @ResponseBody Map<String, Object> getFirmNames(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<ElecFirm> firms = firmDao.selectFirmName();
		
		map.put("firms", firms);
		
		return map;
		
	}
	
}
