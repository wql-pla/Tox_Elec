package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ChargeResult;
import com.tox.bean.ChargeResult_DC;
import com.tox.bean.ElecFirm;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecPile;
import com.tox.dao.ElecFirmMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.utils.ElecUtil;

import net.sf.json.JSONObject;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/admin")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private ElecOrderMapper orderDao;
	@Autowired
	private ElecPileMapper pileDao;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ElecFirmMapper firmDao;

	// 管理员登录
	@RequestMapping(value = "/open")
	public Map<String, Object> login(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * StringBuffer elecNum=new StringBuffer("000000008000");
		 * if(code.length()==1){ elecNum.append("000"+code); }else
		 * if(code.length()==2){ elecNum.append("00"+code); }else
		 * if(code.length()==3){ elecNum.append("0"+code); }else
		 * if(code.length()==4){ elecNum.append(code); }
		 */
		ElecPile pile = pileDao.selectCSByPrimaryKey(code);
		if (null == pile) {
			map.put("result", "-100");
			map.put("msg", "电桩不在线或者电桩号不正确");
			return map;
		}
		ElecOrder order = new ElecOrder();
		order.setCreateTime(new Date());
		order.setStatus("2");
		order.setPileId(pile.getId());
		order.setOpenId("2");
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		int flag = orderDao.insertSelective(order);
		transactionManager.commit(status);
		if (1 == flag) {
			String result = ElecUtil.sendPostTest(code, order.getId());
			if ("SUCCESS".equals(result)) {
				order.setStatus("1");
				orderDao.updateByPrimaryKeySelective(order);
				map.put("result", "100");
				map.put("msg", "充电成功");
			} else {
				map.put("result", "-100");
				map.put("msg", "充电失败");
			}
		} else {
			map.put("result", "-100");
			map.put("msg", "充电失败");
		}
		return map;
	}

	// 查看电桩状态
	@RequestMapping(value = "/checkPileStatus")
	public Map<String, Object> checkPileStatus(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		ElecPile pile = pileDao.selectCSByPrimaryKey(code);
		if (null == pile) {
			map.put("result", "-101");
			map.put("msg", "电桩不在线或者电桩号不正确");
			System.out.println(map);
			return map;
		}
		ElecFirm firm = firmDao.selectByPrimaryKey(pile.getFirmId());
		String tradeTypeCode = "1";
		if(firm.getFirmName().contains("循道")){
			tradeTypeCode= "2";
		}else if(firm.getFirmName().contains("科士达")){
			tradeTypeCode= "3";
		}
		String result = ElecUtil.checkPileStatus(code, tradeTypeCode,pile.getType());
		if ("SUCCESS".equals(result)) {
			map.put("result", "100");
			map.put("msg", "在线");
		} else {
			map.put("result", "-100");
			map.put("msg", "不在线");
		}
		System.out.println(map);
		return map;
	}
	// 查看电桩插枪状态
	@RequestMapping(value = "/checkPileGunStatus")
	public String checkPileGunStatus(String code,Integer gunNo) {
		logger.info("=============检查电枪链接状态=============");
		ElecPile pile = pileDao.getPileInfoByPileNumOrWxCode(code);
		if (null == pile) {
			return "false";
		}
		String firmName = pile.getFirm().getFirmName();
		String tradeTypeCode = "1";
		if(firmName.contains("循道")){
			tradeTypeCode= "2";
		}else if(firmName.contains("科士达")){
			tradeTypeCode= "3";
		}
		String result = ElecUtil.checkPileGunStatus(pile.getPileNum(), tradeTypeCode,pile.getType(),gunNo);
		return result;
	}

	// 查看电桩充电进度
		@RequestMapping(value = "/getPileChargeStatus")
		public Map<String,Object> getPileChargeStatus(Integer orderId) {
			Map<String,Object> map = new HashMap<>();
	        ElecOrder elecOrder = orderDao.selectByPrimaryKey(orderId);
	        ElecPile pile = pileDao.getPileInfoByPileNumOrWxCode(elecOrder.getPileNum());
			String firmName = pile.getFirm().getFirmName();
			String tradeTypeCode = "1";
			if(firmName.contains("循道")){
				tradeTypeCode= "2";
			}else if(firmName.contains("科士达")){
				tradeTypeCode= "3";
			}
			String result = ElecUtil.getPileChargeStatus(pile.getPileNum(), tradeTypeCode,pile.getType(),elecOrder.getGunNo());
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				Integer type = Integer.valueOf(fromObject.get("type").toString());
				JSONObject fromObject2 = JSONObject.fromObject(fromObject.get("data"));
				if(1==type){
					ChargeResult bean = (ChargeResult) JSONObject.toBean(fromObject2, ChargeResult.class);
					map.put("data", bean);
				}else if(2==type){
					ChargeResult_DC bean = (ChargeResult_DC) JSONObject.toBean(fromObject2, ChargeResult_DC.class);
					map.put("data", bean);
				}
			}else{
				map.put("data", null);
			}
			return map;
		}
}
