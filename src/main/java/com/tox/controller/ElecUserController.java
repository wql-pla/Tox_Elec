package com.tox.controller;

import java.util.ArrayList;
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

import com.tox.bean.ElecRecharge;
import com.tox.bean.ElecUser;
import com.tox.bean.PageView;
import com.tox.bean.Coupon;
import com.tox.dao.CouponMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecRechargeMapper;
import com.tox.dao.ElecUserMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/user")
@Transactional
public class ElecUserController {

	private static final Logger logger = LoggerFactory.getLogger(ElecUserController.class);
	
	@Autowired
	private ElecUserMapper elecUserDao;
	@Autowired
	private ElecRechargeMapper elecRechargeDao;
	@Autowired
	private CouponMapper couponDao;
	@Autowired
	private ElecOrderMapper elecOrderDao;
	
	/**
	 * 用户列表查询
	 * @param record
	 * @return
	 */
	@RequestMapping(value="/getUsers",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> getUsers(@RequestBody ElecUser record){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		PageView<ElecUser> pageView = new PageView<ElecUser>();
		
		pageView.setPageSize(record.getPageSize());
		
		record.setPageNum(record.getPageNum() * record.getPageSize());
		
		List<ElecUser> list = elecUserDao.getUsers(record);
		int total = elecUserDao.getUserCount(record);
		
		pageView.setList(list);
		pageView.setTotal(total);
		
		map.put("result","100");
		map.put("data",pageView);
		map.put("msg","用户列表查询成功!");
		
		return map;
		
	}
	
	/**
	 * 查询用户详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/userDetail",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> userDetail(@RequestParam Integer id){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		ElecUser user = elecUserDao.userDetail(id);
		
		map.put("result", "100");
		map.put("data", user);
		map.put("msg", "用户详情查询成功!");
		
		return map;
		
	}
	
	/**
	 * 余额退款
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/returnMoney",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> returnMoney(@RequestParam Integer id){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		ElecUser user = elecUserDao.userDetail(id);
		Date date = new Date();
		
		double money = user.getBalance().doubleValue();
		
		ElecRecharge recharge = new ElecRecharge();
		
		recharge.setUserId(id);
		recharge.setRechargeDate(date);
		recharge.setType(3);
		recharge.setRechargeMoney(money);
		
		int o = elecRechargeDao.insertSelective(recharge);
		user.setBalance(0d);
		user.setGiveMoney(0d);
		if(o == 1){
			
			int i = elecUserDao.updateByPrimaryKeySelective(user);
			
			if(i == 1){
				
				map.put("result", "100");
				map.put("data", "更新个人信息成功!");
				map.put("msg", "更新个人信息成功!");
				
				
			}else{
				
				map.put("result", "-100");
				map.put("data", "更新个人信息失败!");
				map.put("msg", "更新个人信息失败!");
			
			}
		
		}else{
			
			map.put("result", "-100");
			map.put("data", "插入扣款记录失败!");
			map.put("msg", "插入扣款记录失败!");
			
		}
		
		return map;
		
	}
	/**
	 * 批量更改权限
	 * @param type
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/changeAuth",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> returnMoney(@RequestParam Integer type,Integer... ids){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> list = new ArrayList<Integer>();
		for(Integer i : ids){
			list.add(i);
		}
		//如果转为普通会员，先验证所有已选名单是否有正在进行的订单
		if(type == 2){
			int i = elecOrderDao.orderIngCount(list);
			if(i > 0){
				map.put("result", "99");
				map.put("data", "有正在进行的订单!");
				map.put("msg", "有正在进行的订单!");
				return map;
			}
		}
		try{
			if(type == 1){
				elecUserDao.changeAdmins(list);
			}else{
				elecUserDao.changeCommons(list);
			}
			map.put("result", "100");
			map.put("data", "更新成功!");
			map.put("msg", "更新成功!");
		}catch(Exception e){
			map.put("result", "-100");
			map.put("data", "更新失败!");
			map.put("msg", "更新失败!");
		}
		return map;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value="/CouponsById",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> CouponsById(@RequestParam Integer userId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Coupon> list = couponDao.getCouponsByUserId(userId);
		map.put("result", "100");
		map.put("data", list);
		map.put("msg", "优惠券查询成功!");
		return map;
	}
	
}