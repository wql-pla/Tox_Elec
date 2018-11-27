	package com.tox.controller;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ActivityNewOrder;
import com.tox.bean.ActivityNewUser;
import com.tox.bean.ElecUser;
import com.tox.dao.*;
import com.tox.service.ElecActivityService;
import com.tox.sms.config.StringUtil;
import com.tox.utils.RandomUtil;
import com.tox.utils.SMS.sendSmsUtil;
import com.tox.utils.date.dateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/newActivity")
@Transactional
public class ElecNewActivityController {

	private static final String REGEX_MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
	
	private static final Logger logger = LoggerFactory.getLogger(ElecNewActivityController.class);
	
	@Autowired
	private ElecUserMapper elecUserDao;
	@Autowired
	private ActivityNewUserMapper activityNewDao;
	@Autowired
	private ElecActivityService activityService;
	@Autowired
	private ActivityNewOrderMapper activityNewOrderMapper;
	@Autowired
	private ElecOrderMapper orderDao;
    @Autowired
	private ActivityNewInfoMapper activityNewInfoDao;
	
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	 @RequestMapping(value = "/newSendCode", method = RequestMethod.POST, produces = "application/json")
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
	        synchronized (ElecNewActivityController.class) {
		        
				String code = RandomUtil.getRandomCode(6);
				
				Date date = new Date();
				
				ElecUser record = new ElecUser();
				ActivityNewUser activityNewUser= new ActivityNewUser();
				
				record.setPhone(phone);
				activityNewUser.setCreateDate(new Date());
				activityNewUser.setPhone(phone);
				activityNewUser.setIsDel(0);
				activityNewUser.setIsPay("0");
				activityNewUser.setIsSign(0);
				
	//			ElecUser elecUser = elecUserDao.selectByOpenId(openId);
				ElecUser user = elecUserDao.selectByPhone(record);
				ActivityNewUser newUser = activityNewDao.selectByPhone(activityNewUser);
				//如果98报名用户尚未存在则进行报名
				if(newUser ==null) {
					int insertSelective = activityNewDao.insertSelective(activityNewUser);
						if( insertSelective != 1){
						map.put("result", "10002");
						map.put("date", "验证码失效时间更新失败!");
						map.put("msg", "验证码失效时间更新失败!");
						return map;
					}
				}
				
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
	 @RequestMapping(value = "/newLogin", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody Map<String, Object> login(@RequestBody ElecUser user){
		 Map<String,Object> map = new HashMap<String, Object>();
		 Date date = new Date();
		 ElecUser record2 = elecUserDao.selectByPhone(user);
		 ActivityNewUser newUser = new ActivityNewUser();
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
				 ActivityNewUser record = new ActivityNewUser();
				 record.setPhone(record2.getPhone());
				 newUser = activityNewDao.selectByPhone(record);


                 ActivityNewInfo activityNewInfo = activityNewInfoDao.selectByPrimaryCode ("98ACELECTOX");

                 map.put("result", "100");
				map.put("date", newUser);
                 map.put("data-open", activityNewInfo.getIsOpen());
				map.put("msg", "登录成功!");
				 
			 }
			 
		 }
		 
		 return map;
		 
	 }
	 
	 /**
	  * 修改用户所在城市
	  * @param phone
	  * @param city
	  * @return
	  */
	 @RequestMapping(value = "/updateCity", method = RequestMethod.POST, produces = "application/json")
     public @ResponseBody Map<String, Object> updateCity(@RequestParam String phone,@RequestParam String city,@RequestParam String code) {
		 
		 logger.info(String.format("接收到的要修改的手机号为：%s",phone));
			
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 ActivityNewUser activityNewUser= new ActivityNewUser();
		 activityNewUser.setPhone(phone);
		 ActivityNewUser newUser = activityNewDao.selectByPhone(activityNewUser);
		 //判断用户是否存在
		 if(newUser!=null) {
			 //判断城市是否传输
			 if(!StringUtil.isNullOrEmpty(city)) {
				 //注册城市信息
				 newUser.setCity(city);
				 //更改报名情况
				 newUser.setIsSign(1);
                //插入报名活动代码
                 newUser.setType(code);
			 }
		 }
		 //修改用户报名信息
		 activityNewDao.updateByPrimaryKeySelective(newUser);
		 
		map.put("result", "100");
		map.put("msg", "成功!");
	    
		return map;
	 }
	 
	 /**
	  * 修改活动
	  * @param phone
	  * @return
	  */
	 @RequestMapping(value = "/updateActivityStatus", method = RequestMethod.POST, produces = "application/json")
     public @ResponseBody Map<String, Object> updateActivityStatus(@RequestParam String phone) {
		 
		 logger.info(String.format("接收到的要修改的手机号为：%s",phone));
			
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 ActivityNewUser activityNewUser= new ActivityNewUser();
		 activityNewUser.setPhone(phone);
		 
		 ActivityNewUser newUser = activityNewDao.selectByPhone(activityNewUser);
		 newUser.setIsSign(0);
		 activityNewDao.updateByPrimaryKeySelective(newUser);
		 
		map.put("result", "100");
		map.put("msg", "修改成功!");
	    
		return map;
	 }
	 
	 
	 /**
	  * 查询用户信息
	  * @param activityNewUser
	  * @return
	  */
	 @RequestMapping(value = "/findUser", method = RequestMethod.POST, produces = "application/json")
     public @ResponseBody Map<String, Object> findActivityStatus(@RequestBody ActivityNewUser activityNewUser) {
		 
		 logger.info(String.format("接收到的信息为：%s",activityNewUser));
			
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 
		 List<ActivityNewUser> newUser = activityNewDao.findNewUser(activityNewUser);
         int newUserCount = activityNewDao.findNewUserCount(activityNewUser);

         map.put("result", "100");
         map.put("total",newUserCount);
		 map.put("date", newUser);
		
		return map;
	 }
	 
	 /**
	  * 查询资金管理
	  * @param activityNewOrder
	  * @return
	  */
	 @RequestMapping(value = "/findMoneyManager", method = RequestMethod.POST, produces = "application/json")
     public @ResponseBody Map<String, Object> findMoneyManager(@RequestBody ActivityNewOrder activityNewOrder) {
		 
		 logger.info(String.format("接收到的信息为：%s",activityNewOrder));
			
		 Map<String,Object> map = new HashMap<String, Object>();
		 
		 List<ActivityNewOrder> orders = activityNewOrderMapper.findNewOrder(activityNewOrder);
         int newOrderCount = activityNewOrderMapper.findNewOrderCount(activityNewOrder);

         map.put("result", "100");
         map.put("date", orders);
		map.put("total",newOrderCount);
		return map;
	 }
	 
	 
	 
	 public void findMoneyManager(){
		 logger.info("查询活动信息");
	 }
	 
//	 @RequestMapping(value = "/findMonthInfo", method = RequestMethod.POST, produces = "application/json")
//     public @ResponseBody Map<String, Object> findMonthInfo(@RequestBody ActivityNewOrder activityNewOrder) {
//		 
//	 }
	
	 
	 
	 
//	 @RequestMapping(value = "/findMoneyManager", method = RequestMethod.POST, produces = "application/json")
//     public @ResponseBody Map<String, Object> findMonthManager(@RequestBody ActivityNewUser activityNewUser) 
//	 {
//		 
//	 }
	 //生成假用户
//	 @ApiOperation(value = "创建用户", notes = "创建用户")
//	 @RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = "application/json")
//	 public void createFalseUser(@RequestParam Integer userNum) {
//		 ElecUser elecUser= new ElecUser();
//		 Random random=new Random(300);
//		 Random random1=new Random(100);
//		 for(int i=0;i<userNum;i++) {
//			 elecUser.setPhone(createPhone());
//			 elecUser.setCreateDate(randomDate("2017-12-01","2018-11-05"));
//			 elecUser.setOpenId(UUID.randomUUID().toString());
//			 elecUser.setBalance(random.nextDouble());
//			 elecUser.setGiveMoney(random1.nextDouble());
//			 elecUser.setIsdel(0);
//			 elecUser.setType(2);
//		 }
//	 }
//	 
//	 
//	 public void createFalseOrder(Integer orderNum) {
//		 Random random=new Random(8914);
//		 ElecUser user=null;
//		 while(1==1) {
//			 user = elecUserDao.selectByPrimaryKey(random.nextInt());
//			 if(user!=null) {
//				break; 
//			 }
//		 }
//		 ElecOrder order= new ElecOrder();
//		// orderDao.insert(record);
//		 
//	 }
//	 
//	 public String createPhone() {
//		 String[]lt = new String[]{"130","131","132","133","134","135","136","137","138","139","188","181","182","183","184","185","186","187","188","189"};
//			StringBuilder str=new StringBuilder();
//			Random random=new Random();
//			for(int i=0;i<8;i++){
//			    str.append(random.nextInt(10));
//			}
//			//将字符串转换为数字并输出
//			String phoneEnd=str.toString();
//			Integer nextInt=random.nextInt(19);
//			String phoneStart=lt[nextInt];
//			String phone=phoneStart+phoneEnd;
//			return phone;
//	 }
//	 
//	 public void createTime() {
//		 
//		 Random random = new Random();
//		 Calendar can = Calendar.getInstance();
//		 for (int i = 0; i < 10; i++) {
//			 can.setTimeInMillis(random.nextLong());
//			 System.out.println(can.getTime());
//		 }
//	 }
//	 
//	 
//	 
//	 
//	 private static Date randomDate(String beginDate,String  endDate ){  
//		 
//		 try {  
//		  
//			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
//			  
//			 Date start = format.parse(beginDate);//构造开始日期  
//			  
//			 Date end = format.parse(endDate);//构造结束日期  
//			  
//			  
//			 if(start.getTime() >= end.getTime()){  
//			  
//			 return null;  
//			  
//			 }  
//			  
//			 long date = random(start.getTime(),end.getTime());  
//			  
//			 return new Date(date);  
//			  
//			 } catch (Exception e) {  
//			  
//			 e.printStackTrace();  
//			  
//			 }  
//			  
//			 return null;  
//		  
//		 }  
//		  
//		 private static long random(long begin,long end){  
//		  
//			 long rtn = begin + (long)(Math.random() * (end - begin));  
//			  
//			 //如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
//			  
//			 if(rtn == begin || rtn == end){  
//			  
//			 return random(begin,end);  
//			  
//			 }  
//			  
//			 return rtn;  
//		  
//		 }

}
