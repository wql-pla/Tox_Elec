package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecChargeRecord;
import com.tox.bean.ElecFirm;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecPriceRule;
import com.tox.bean.ElecStation;
import com.tox.bean.ElecUser;
import com.tox.dao.ElecChargeRecordMapper;
import com.tox.dao.ElecFirmMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.dao.ElecPriceRuleMapper;
import com.tox.dao.ElecStationMapper;
import com.tox.dao.ElecUserMapper;
import com.tox.service.ElecOrderService;
import com.tox.utils.ElecUtil;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional()
@RequestMapping(value="/record")
public class ElecRecordController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecRecordController.class);
	@Autowired
	private ElecChargeRecordMapper recordDao;
	@Autowired
	private ElecOrderMapper orderDao;
	@Autowired
	private ElecOrderService orderService;
	@Autowired
	private ElecUserMapper userDao;
	/**
	 * 
	* @Title: createRecord 
	* @Description: (续充插入记录) 
	* @param @param record
	* @param @return    设定文件 
	* @return Map<String,Object>    
	* @author WYT  
	* @date 2017年10月13日 上午10:02:41
	* @throws
	 */
	
	@RequestMapping(value="/createRecord")
	public @ResponseBody Map<String,Object> createRecord(ElecChargeRecord record){
		logger.info(String.format("要续充的订单信息：%s", record.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecOrder order = orderDao.selectByPrimaryKey(record.getOrderId());
		ElecUser user = userDao.selectByOpenId(order.getOpenId());
		record.setCreateTime(new Date());
		boolean flag = orderService.appentXY(record,order,user);
		if(flag){
			map.put("orderId", order.getId());
			map.put("result", "100");
		}else{
			record.setRecordStatus(0);
			recordDao.updateByPrimaryKeySelective(record);
			map.put("result", "-100");
			map.put("msg", "续充失败");
		}
		return map;
	}
	
	
	/**
	 * 
	* @Title: editOrder 
	* @Description: (更新插入记录) 
	* @param @param record
	* @param @return    设定文件 
	* @return Map<String,Object>    
	* @author WYT  
	* @date 2017年10月13日 上午11:34:49
	* @throws
	 */
	@RequestMapping(value="/editRecord",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String, Object> editOrder(ElecChargeRecord record) {
		logger.info(String.format("修改信息为：%s", record.toString()));
		//根据电桩id获取厂商抽成信息
		/*ElecFirm elecFirm =elecFirmDao.selectByPrimaryPileId(record.getPileId());
		//根据电桩id获取场站信息
		ElecStation elecStation = elecStationDao.selectByPrimaryPileId(record.getPileId());
		//查询订单来源
		ElecOrder elecOrder = orderDao.selectByPrimaryKey(record.getOrderId());
		
		record.setCreateTime(new Date());
		//判断来源weixin
		if (elecOrder.getOrderSource().equals("weixin")) {
			
			//实际基础费用
			record.setBasicCharge(record.getRealCount()*elecStation.getBasicChargeAmount());
			//我方实际服务费
			record.setServiceChargeSelf(record.getRealCount()*elecStation.getServiceChargeAmount());
			//第三方实际服务费
			record.setServiceChargeThird(0.00); 
		}else {
			
			//实际基础费用
			record.setBasicCharge(record.getRealCount()*elecStation.getBasicChargeAmount());
			//我方实际服务费
			record.setServiceChargeSelf(record.getRealCount()*elecStation.getServiceChargeAmount()*(1-elecFirm.getPayRatio()));
			//第三方实际服务费
			record.setServiceChargeThird(record.getRealCount()*elecStation.getServiceChargeAmount()*elecFirm.getPayRatio());
		}*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		record.setEndTime(new Date());
		map.put("result", recordDao.updateByPrimaryKeySelective(record)>0?true:false);
		return map;
	}
	

}
