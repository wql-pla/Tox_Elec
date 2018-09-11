package com.tox.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecActivity;
import com.tox.bean.ElecCoupons;
import com.tox.bean.ElecGroupCounponsZ;
import com.tox.bean.ElecRedeem;
import com.tox.bean.ElecUserCouponsRel;
import com.tox.bean.PageView;
import com.tox.dao.ElecActivityMapper;
import com.tox.dao.ElecCouponsMapper;
import com.tox.dao.ElecGroupCounponsZMapper;
import com.tox.dao.ElecRedeemMapper;
import com.tox.dao.ElecUserCouponsRelMapper;
import com.tox.utils.RandomUtil;

/**
 * 兑换码管理----生成兑换码
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/activity")
public class ElecActivityController {

	//日志对象
	private static final Logger logger = LoggerFactory.getLogger(ElecActivityController.class);
	
	@Autowired
	private ElecActivityMapper activityDao;
	@Autowired
	private ElecRedeemMapper redeemDao;
	@Autowired
	private ElecUserCouponsRelMapper ucrDao;
	@Autowired
	private ElecCouponsMapper couponsDao;
	@Autowired
	private ElecGroupCounponsZMapper couponsZDao;
	
	/**
	 * 添加活动信息---生成优惠卷
	 * @param activity
	 * @return
	 */

	@Transactional(propagation=Propagation.REQUIRED)
    @RequestMapping(value = "/insertActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertActivity(@RequestBody ElecActivity activity) {
        
    	logger.info(String.format("新增参数：%s", activity.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        
//        Integer couponsId = activity.getCouponsId();
        
//        ElecCoupons elecCoupons = couponsDao.selectByPrimaryKey(couponsId);
//        if(activity.getToDate().after(elecCoupons.getToDate())){
//        	map.put("result", 1003);
//			map.put("msg", "活动有效期不能超过优惠券有效期");
//			return map;
//        }
        
        
        activity.setCreateDate(new Date());
        
        //-----------------插入信息-------------
        
        int flag = activityDao.insertSelective(activity);
       
        if (flag>0) {
        	
        	ElecRedeem redeem = new ElecRedeem();
        	
        	redeem.setActivityId(activity.getId());
        	
        	redeem.setCreateDate(new Date());
        	
        	redeem.setIsDel(0);
        	
        	RandomUtil random = new RandomUtil();
        	
        	//------------------生成兑换码(每次查重十次有一次超过十次直接回滚，提示错误1002)-------------------
        	
        	int suNum = 0;
        	
        	if (activity.getIsMore()==0) {
        		
        		for(int i = 0 ; i < activity.getNumber(); i++){
        			
        			suNum++;
        			
        			try {
        				redeem.setCode(random.getRandomString(6));
        				
        				redeemDao.insertSelective(redeem);
        				
        				System.out.println(suNum);
        				
        				suNum = 0;
        				
					} catch (Exception e) {
						
						//e.printStackTrace();
						
						i--;
						
						if (suNum==10) {
							
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
							
							map.put("result", 1002);
							
							 return map; 
						}
						
					}
        			
            	}
        		
			}else{
				
				for(int i = 0 ; i < 1; i++){
					
					suNum++;
					
					try {
						
						redeem.setCode(random.getRandomString(6));
						
						redeemDao.insertSelective(redeem);
						
					} catch (Exception e) {

						e.printStackTrace();
						
						i--;
						if (suNum==10) {
							
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
							
							map.put("result", 1002);
							
							 return map; 
						}
					}
				}
					
			}
        	
        	map.put("result", 100);
			
			map.put("msg", "添加成功！");
			
		}else{
		
			map.put("result", 1001);
			
			map.put("msg", "活动插入失败！");
			
		}
        
        return map;
    }
	/**
	 * 活动列表查询
	 * @param activity
	 * @return
	 */
    @RequestMapping(value = "/selectActivitys",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivitys(@RequestBody ElecActivity activity) {
        logger.info(String.format("查询参数：%s", activity.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecActivity> pageView = new PageView<ElecActivity>();

		pageView.setPageSize(activity.getPageSize());

		activity.setPageNum(activity.getPageNum() * activity.getPageSize());
		
		if(activity.getIsMore()==1){
			List<ElecActivity> activitys = activityDao.selectActivityMore(activity);
			int count = activityDao.selectActivityMoreCount(activity);
			pageView.setList(activitys);

			pageView.setTotal(count);
			
			map.put("data", pageView);
			
	        return map;
		}else{
			List<ElecActivity> activitys = activityDao.selectActivity(activity);
	        
	        int count = activityDao.selectActivityCount(activity);
	        
	        pageView.setList(activitys);

			pageView.setTotal(count);
			
			map.put("data", pageView);
			
	        return map;
		}
        
    }
    
    /**
     * 查询活动详情
     * @param activity
     * @return
     */
    @RequestMapping(value = "/selectActivityById",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivityById(@RequestBody ElecActivity activity) {
       
    	logger.info(String.format("查询参数：%s", activity.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        if(activity.getIsMore()==1){
        	ElecActivity activityInfo = activityDao.selectMoreActivityById(activity.getId());
        	map.put("data",activityInfo);
        	map.put("result",100);
        }else{
        	ElecActivity activityInfo = activityDao.selectActivityById(activity.getId());
        	map.put("data",activityInfo);
        	map.put("result",100);
        }
        
        return map;
    }
	
    /**
     * 修改活动信息
     * @param activity
     * @return
     */
    @RequestMapping(value = "/editActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> editActivity(@RequestBody ElecActivity activity) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	Boolean flag = activityDao.updateByPrimaryKeySelective(activity)>0?true:false;
    	  
    	map.put("result", flag);
    	
    	return map;
    }
    /**
     * 兑换优惠券
     * @param redeem 兑换码
     * @return
     */
    @RequestMapping(value = "/redeemExchangeCoupon",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> redeemExchangeCoupon(String redeem,Integer userId) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	synchronized(ElecActivityController.class){
    	ElecRedeem elecRedeem = redeemDao.getRedeemByCode(redeem);
    	if(null == elecRedeem){
    		logger.info(String.format("%s:兑换码不正确", redeem));
    		map.put("result", "-1001");
    		map.put("msg", "兑换码不正确");
    		return map;
    	}
    	ElecActivity elecActivity = activityDao.selectByPrimaryKey(elecRedeem.getActivityId());
    	if(0==elecActivity.getStatus()||1==elecActivity.getIsDel()){
    		map.put("result", "-1002");
    		map.put("msg", "兑换活动还未开始");
    		return map;
    	}
    	if(elecActivity.getIsMore()==0){
    		if(elecRedeem.getIsUse()==1){
    			map.put("result", "-1003");
        		map.put("msg", "兑换码已被兑换");
        		return map;
    		}
    	}
    	Date now = new Date();
    	if(now.after(elecActivity.getToDate())){
    		map.put("result", "-1004");
    		map.put("msg", "兑换码过期了哦！");
    		return map;
    	}
    	List<ElecUserCouponsRel> records  =ucrDao.getUserCouponsRelByUserIdAndRedeemId(userId,elecRedeem.getId());
    	if(records.size()>0){
    		map.put("result", "-1005");
    		map.put("msg", "不能重复兑换");
    		return map;
    	}
    	if(elecActivity.getIsMore()==1 && null != elecActivity.getNumber()){
    		int count =ucrDao.getCouponsNumByRedeemId(elecRedeem.getId());
    		if(elecActivity.getNumber()<=count){
    			map.put("result", "-1006");
        		map.put("msg", "兑换码失效");
        		return map;
    		}
    	}
    	int couponsNum =0;
    	Integer couponsId = elecActivity.getCouponsId();
    	if(null ==elecActivity.getCouponsType() ||elecActivity.getCouponsType()==1){
    		ElecUserCouponsRel ucr = new ElecUserCouponsRel();
        	ucr.setCouponsId(couponsId);
        	ucr.setCreateDate(now);
        	ucr.setRedeemId(elecRedeem.getId());
        	ucr.setStatus(0);
        	ucr.setUserId(userId);
        	int flag = ucrDao.insertSelective(ucr);
        	if(flag>0){
        		couponsNum=1;
        		elecRedeem.setIsUse(1);
        		redeemDao.updateByPrimaryKeySelective(elecRedeem);
        	}
        	
    	}else if(elecActivity.getCouponsType()==2){
    		List<ElecGroupCounponsZ> counponsZList= couponsZDao.selectByCounponsFId(couponsId);
    		int result =1;
    		for (ElecGroupCounponsZ elecGroupCounponsZ : counponsZList) {
    			ElecUserCouponsRel ucr = new ElecUserCouponsRel();
            	ucr.setCouponsId(elecGroupCounponsZ.getCounponsId());
            	ucr.setCreateDate(now);
            	ucr.setRedeemId(elecRedeem.getId());
            	ucr.setStatus(0);
            	ucr.setUserId(userId);
            	int flag = ucrDao.insertSelective(ucr);
            	if(flag!=1){
            		result=0;
            	}
			}
    		if(result==1){
    			couponsNum=counponsZList.size();
    			elecRedeem.setIsUse(1);
    			redeemDao.updateByPrimaryKeySelective(elecRedeem);
    		}
    	}
    	
    	map.put("result", "100");
    	map.put("couponsNum", couponsNum);
		map.put("msg", "兑换成功");
    	return map;
    }
    }
    
    
}
