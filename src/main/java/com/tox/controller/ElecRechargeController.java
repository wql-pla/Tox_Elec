package com.tox.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecRecharge;
import com.tox.bean.ElecStation;
import com.tox.bean.ElecZJ;
import com.tox.bean.PageView;
import com.tox.dao.ElecRechargeMapper;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional(propagation=Propagation.REQUIRES_NEW)
@RequestMapping(value="/recharge")
public class ElecRechargeController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElecRechargeController.class);
	 
	@Autowired
	private ElecRechargeMapper elecRechargeDao;
	
	/**
	 * 按条件查询充值流水信息、余额退款列表信息(充值:type=1，余额退款：type=3    订单退款:type=2)
	 * @param record
	 * @return
	 */
	@RequestMapping(value="/rechargeList",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> rechargeList(@RequestBody ElecRecharge record){
		
        Map<String, Object> map = new HashMap<String, Object>();
        PageView<ElecRecharge> pageView = new PageView<ElecRecharge>();

		pageView.setPageSize(record.getPageSize());

		record.setPageNum(record.getPageNum() * record.getPageSize());
        List<ElecRecharge> recharges = elecRechargeDao.recharges(record);
        int count = elecRechargeDao.rechargesCount(record);
        double all = elecRechargeDao.sumRecharge(record);
        logger.info(String.format("总记录数：%s", Integer.valueOf(count).toString()));
        pageView.setList(recharges);
		pageView.setTotal(count);
		
		map.put("result", "100");
		map.put("data", pageView);
		map.put("all", all);
		map.put("msg", "充值列表查询成功!");
		
        return map;
		
	}
	
	/**
	 * 用户资金明细
	 * @param record
	 * @return
	 */
	@RequestMapping(value="/details",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> details(@RequestBody ElecZJ record){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		PageView<ElecZJ> pageView = new PageView<ElecZJ>();

		pageView.setPageSize(record.getPageSize());

		record.setPageNum(record.getPageNum() * record.getPageSize());
		
		List<ElecZJ> list = elecRechargeDao.details(record);
		
		for(ElecZJ zj : list){
			
			if(zj.getCzje().doubleValue() - zj.getSjzfje().doubleValue() > 0){
				zj.setSjsy(zj.getSjzfje());
			}else{
				zj.setSjsy(zj.getCzje());
			}
			
		}
		
		int total = elecRechargeDao.detailsCount(record);
		
		pageView.setList(list);
		pageView.setTotal(total);
		
		map.put("result", "100");
		map.put("data", pageView);
		map.put("msg","用户资金列表查询成功!");
		
		return map;
		
	}
	
}
