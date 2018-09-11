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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tox.bean.ElecActivity;
import com.tox.bean.ElecActivity2;
import com.tox.bean.ElecCoupons;
import com.tox.bean.ElecGroupCounponsF;
import com.tox.bean.PageView;
import com.tox.dao.ElecActivity2Mapper;
import com.tox.dao.ElecCouponsMapper;
import com.tox.dao.ElecGroupCounponsFMapper;
import com.tox.dao.ElecRedeemMapper;
import com.tox.dao.ElecUserCouponsRelMapper;

/**
 * 活动管理----生成兑换码
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/activity2")
public class ElecActivityController2 {

	//日志对象
	private static final Logger logger = LoggerFactory.getLogger(ElecActivityController2.class);
	
	@Autowired
	private ElecActivity2Mapper activityDao;
	@Autowired
	private ElecRedeemMapper redeemDao;
	@Autowired
	private ElecUserCouponsRelMapper ucrDao;
	@Autowired
	private ElecCouponsMapper couponsDao;
	@Autowired
	private ElecGroupCounponsFMapper couponsFDao;
	
	/**
	 * 添加活动信息
	 * @param activity
	 * @return
	 */

    @RequestMapping(value = "/insertActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertActivity(@RequestBody ElecActivity2 activity) {
        
    	logger.info(String.format("新增参数：%s", activity.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        activity.setCreateTime(new Date());
        
        List<ElecActivity2> activitys = activityDao.getActivityByTypeAndRule(activity);
        for (ElecActivity2 old : activitys) {
			if((activity.getEndTime().before(old.getEndTime()) && activity.getEndTime().after(old.getStartTime()))||(activity.getStartTime().after(old.getStartTime()) &&activity.getStartTime().before(old.getEndTime()))){
				map.put("result", 101);
				map.put("msg", "活动时间有重合");
				return map;
			}
		}
        
        //-----------------插入信息-------------
        
        int flag = activityDao.insertSelective(activity);
       if(flag==1){
    	   map.put("result", 100);
       }else{
    	   map.put("result", -100);
       }
        
        return map;
    }
	/**
	 * 活动列表查询
	 * @param activity
	 * @return
	 */
    @RequestMapping(value = "/selectActivitys",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivitys(@RequestBody ElecActivity2 activity) {
        logger.info(String.format("查询参数：%s", activity.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecActivity2> pageView = new PageView<ElecActivity2>();

		pageView.setPageSize(activity.getPageSize());

		activity.setPageNum(activity.getPageNum() * activity.getPageSize());
		
        List<ElecActivity2> activitys = activityDao.selectActivitys(activity);
        
        int count = activityDao.selectActivitysCount(activity);
        
        pageView.setList(activitys);
//
		pageView.setTotal(count);
		
		map.put("data", pageView);
		
        return map;
    }
    
    /**
     * 查询活动详情
     * @param activity
     * @return
     */
    @RequestMapping(value = "/selectActivityById",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectActivityById(Integer activityId) {
       
    	logger.info(String.format("查询参数：%s", activityId.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        ElecActivity2 activityInfo = activityDao.selectByPrimaryKey(activityId);
        if(null!=activityInfo.getNewCouponType() &&activityInfo.getNewCouponType()==1){//新用户普通优惠券
        	ElecCoupons elecCoupons = couponsDao.selectByPrimaryKey(activityInfo.getNewPersonCoupon());
        	activityInfo.setNewCoupons(elecCoupons);
        }else if(null!=activityInfo.getNewCouponType() &&activityInfo.getNewCouponType()==2){//新用户组合优惠券
        	ElecGroupCounponsF groupCounponsF = couponsFDao.selectByPrimaryKey(activityInfo.getNewPersonCoupon());
        	activityInfo.setNewGroupCoupons(groupCounponsF);
        }
        if(null!=activityInfo.getOldCouponType() &&activityInfo.getOldCouponType()==1){//老用户普通优惠券
        	ElecCoupons elecCoupons = couponsDao.selectByPrimaryKey(activityInfo.getOldPersonCoupon());
        	activityInfo.setOldCoupons(elecCoupons);
        }else if(null!=activityInfo.getOldCouponType() &&activityInfo.getOldCouponType()==2){//老用户组合优惠券
        	ElecGroupCounponsF groupCounponsF = couponsFDao.selectByPrimaryKey(activityInfo.getOldPersonCoupon());
        	activityInfo.setOldGroupCoupons(groupCounponsF);
        }
        
        map.put("data",activityInfo);
        
        map.put("result",100);
       
        map.put("msg", "优惠卷查询成功!");
        
        return map;
    }
	
    /**
     * 修改活动信息
     * @param activity
     * @return
     */
    @RequestMapping(value = "/editActivity",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> editActivity(@RequestBody ElecActivity2 activity) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ElecActivity2> activitys = activityDao.getActivityByTypeAndRule(activity);
    	if(null!=activity.getStatus() &&activity.getStatus()==0){
    		
    	}else{
    		for (ElecActivity2 old : activitys) {
    			if((activity.getEndTime().before(old.getEndTime()) && activity.getEndTime().after(old.getStartTime()))||(activity.getStartTime().after(old.getStartTime()) &&activity.getStartTime().before(old.getEndTime()))){
    				map.put("result", 101);
    				map.put("msg", "该活动时间与"+old.getId()+"号活动时间重合");
    				return map;
    			}
    		}
    	}
    	 
    	Boolean flag = activityDao.updateByPrimaryKeySelective(activity)>0?true:false;
    	if(flag){
    		map.put("result", 100);
    	}else{
    		map.put("result", -100);
    	}
    	
    	return map;
    }
}
