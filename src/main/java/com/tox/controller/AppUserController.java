package com.tox.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.tox.utils.Constant;
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

import com.tox.utils.date.dateUtil;
import com.tox.bean.ElecBill;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecRecharge;
import com.tox.bean.ElecUser;
import com.tox.utils.RandomUtil;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecRechargeMapper;
import com.tox.dao.ElecUserMapper;
import com.tox.service.ElecActivityService;
import com.tox.utils.SMS.sendSmsUtil;
import com.tox.utils.compare.compareUtil;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/app")
public class AppUserController {
	
//	private static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
	private static final String REGEX_MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
	
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	
	@Autowired
	private ElecUserMapper elecUserDao;
	@Autowired
	private ElecOrderMapper elecOrderDao;
	@Autowired
	private ElecRechargeMapper elecRechargeDao;
	@Autowired
	private ElecActivityService activityService;

	/**
	 * 发送验证码
	 * @param record
	 * @return
	 */
	 @RequestMapping(value = "/sendCode", method = RequestMethod.POST, produces = "application/json")
     public @ResponseBody Map<String, Object> sendCode(@RequestParam String phone) {

		 logger.info(String.format("接收到的手机号为：%s",phone));
			
			Map<String,Object> map = new HashMap<String, Object>();
			
			boolean reg = Pattern.matches(REGEX_MOBILE, phone);

	        if (!reg) {
	        	
	        	logger.info("手机号格式不正确!");
	        	
	        	map.put("result", "10001");
	            map.put("date", "手机号格式不正确!");
	            map.put("msg", "手机号格式不正确!");
	            
	            return map;
	        
	        }
	        synchronized (AppUserController.class) {
		        
				String code = RandomUtil.getRandomCode(6);
				
				Date date = new Date();
				
				ElecUser record = new ElecUser();
				
				record.setPhone(phone);
				
	//			ElecUser elecUser = elecUserDao.selectByOpenId(openId);
				ElecUser user = elecUserDao.selectByPhone(record);
				
				/*if(null !=elecUser &&user ==null){
					logger.info("用户更换了手机号登陆！");
		        	
		        	map.put("result", "10004");
		            map.put("date", "您的账号已绑定到尾号为："+elecUser.getPhone().substring(7)+"的手机号，请用该号登陆！");
		            map.put("msg", "您的账号已绑定到尾号为："+elecUser.getPhone().substring(7)+"的手机号，请用该号登陆！");
		            
		            return map;
				}*/
				boolean bool = sendSmsUtil.sendCode(record.getPhone(), code);
				
				//如果有记录，更新验证码有效时间
				if(user != null){
	//				if(user.getType()==1&&user.getOpenId()==null){
	////					user.setOpenId(openId);
	//				}
					user.setPasswordDate(dateUtil.reckonMinutes(date, 5));
					user.setPassword(code);
					
					int i = elecUserDao.updateByPrimaryKeySelective(user);
					
					if( i != 1){
						
						map.put("result", "10002");
						map.put("date", "验证码失效时间更新失败!");
						map.put("msg", "验证码失效时间更新失败!");
						
						return map;
						
					}
				
				}else{
					
					record.setPassword(code);
					record.setPasswordDate(dateUtil.reckonMinutes(date,5));
					record.setCreateDate(date);
	//				record.setOpenId(openId);
					record.setBalance(0d);
					record.setType(2);
					
					int i = elecUserDao.insertSelective(record);
					
					if(i != 1){
						
						map.put("result", "10003");
						map.put("date", "用户添加失败!");
						map.put("msg", "用户添加失败!");
						
						return map;
						
					}
				}
				
				if(bool){
					
					map.put("result", "100");
					map.put("date", "验证码发送成功!");
					map.put("msg", "验证码发送成功!");
					
				}else{
					
					logger.info("验证码发送失败，请稍后再试!");
					
					map.put("result", "-100");
					map.put("date", "验证码发送失败，请稍后再试!");
					map.put("msg", "验证码发送失败，请稍后再试!");
					
				}
				
				return map;
	        }
			
		}
	 
	 /**
	  * 登录
	  * @param user
	  * @return
	  */
	 @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody Map<String, Object> login(@RequestBody ElecUser user){
		 Map<String,Object> map = new HashMap<String, Object>();
		 Date date = new Date();
		 ElecUser record2 = elecUserDao.selectByPhone(user);
		 if(record2 == null){
			 
			map.put("result", "-100");
			map.put("date", "验证码不正确!");
			map.put("msg", "验证码不正确!");
			 
		 }else{
			 
			 if(date.after(record2.getPasswordDate())){
				 
				map.put("result", "10004");
				map.put("date", "验证码已失效!");
				map.put("msg", "验证码已失效!");
				 
			 }else{
				if(null ==record2.getOpenId()||"".equals(record2.getOpenId())){
					long currentTimeMillis = System.currentTimeMillis();
					String openId = user.getOpenId();
					record2.setOldUserId(user.getOldUserId());
					String newOpenId = openId+currentTimeMillis;
					record2.setOpenId(newOpenId);
					elecUserDao.updateByPrimaryKeySelective(record2);
					activityService.giveNewUserActivity(record2);//注册成功 送优惠
				}
				map.put("result", "100");
				map.put("date", record2);
				map.put("auth", Constant.auth);
				map.put("msg", "登录成功!");
				 
			 }
			 
		 }
		 
		 return map;
		 
	 }
	 
	 /**
	  * 查询我的订单列表(完成和正在进行中)
	  * @param user
	  * @return
	 * @throws ParseException 
	  */
	 @RequestMapping(value = "/myOrder", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody Map<String, Object> myOrder(String openId,Integer pageSize,Integer pageNum) throws ParseException{
		 logger.info("查询我的订单列表请求参数:openId="+openId+"pageSize="+pageSize+"pageNum="+pageNum);
		 Map<String,Object> map = new HashMap<String, Object>();
		 pageNum=pageSize*pageNum;
		 //List<ElecOrder> list = elecOrderDao.getOrderListByOpenidPages(openId,pageSize,pageNum);
		 List<ElecOrder> list = elecOrderDao.getPageOrderListByOpenidPages(openId,pageSize,pageNum);
		 int count = elecOrderDao.getOrderListByOpenidPagesCount(openId);
		 long l = 0l;
		 
		 for(ElecOrder order:list){
			 if(null != order.getEndTime()){
				 l = dateUtil.getSeconds(order.getCreateTime(),order.getEndTime());
			 }
			 
			 order.setTimeDiff(l);
			 
		 }
		 
		 map.put("result", "100");
		 map.put("date", list);
		 map.put("total", count);
		 map.put("msg", "查询成功!");
		 
		 return map;
		 
	 }
	 	
	 /**
	  * 账单列表
	  * @param openId
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	@RequestMapping(value = "/billList", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody Map<String, Object> billList(@RequestParam Integer id,String openId){
		 
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 List<ElecRecharge> recharges = elecRechargeDao.rechargeList(id);
		 
		 List<ElecOrder> orders = elecOrderDao.getOverOrderListByOpenid(openId);
		 
		 List<ElecBill> list = new ArrayList<>();
		 
		 for(ElecRecharge re : recharges){
			 
			 ElecBill bill = new ElecBill();
			 
			 int i = re.getType().intValue();
			 
			 /*if(i == 1){
				 bill.setType(1);
			 }else if(i == 3){
				 bill.setType(3);
			 }*/
			 bill.setType(re.getType());//type =1 充值金额，type=3 申请退款金额
			 bill.setMoney(re.getRechargeMoney());
			 bill.setDate(re.getRechargeDate());
			 
			 list.add(bill);
		 }
		 
		 for(ElecOrder or : orders){
			 
			 ElecBill bill = new ElecBill();
			 bill.setType(2);
			 bill.setMoney(or.getRealAmount());
			 bill.setDate(or.getCreateTime());
			 
			 list.add(bill);
		 }
		 
		 compareUtil sort = new compareUtil(); 
		 Collections.sort(list,sort);
		 
		 map.put("result","100");
		 map.put("date", list);
		 map.put("msg", "订单查询成功!");
		 
		 return map;
		 
	 }
	 
	 /**
	  * 个人中心
	  * @param id
	  * @param openId
	  * @return
	  */
	 @RequestMapping(value = "/center", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody Map<String, Object> center(@RequestParam Integer id){
		 
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 ElecUser record = elecUserDao.selectByPrimaryKey(id);
		 if(null == record){
			 map.put("result","-100");
			 map.put("msg", "获取个人信息失败!");
			 return map;
		 }
		 /*if(record.getType()==2){
			 String openId = record.getOpenId();
			 ElecOrder order = elecOrderDao.getOrderByOpenid(openId);
			 if(null !=order){
				 Double balance = record.getBalance();
				 Double giveMoney = record.getGiveMoney();
				 Double elecTotalAmount = order.getElecTotalAmount();
				 if(balance>=elecTotalAmount){
					 record.setBalance(balance-elecTotalAmount);
				 }else{
					 record.setBalance(0.00);
					 BigDecimal sum = BigDecimal.valueOf(balance).add(BigDecimal.valueOf(giveMoney));
					 BigDecimal sub = sum.subtract(BigDecimal.valueOf(elecTotalAmount));
					 record.setGiveMoney(sub.doubleValue());
				 }
			 }
		 }*/
		 map.put("result","100");
		 map.put("date", record);
		 map.put("msg", "获取个人信息成功!");
		 
		 return map;
		 
	 }
	 
}
