package com.tox.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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

import com.tox.bean.ElecCoupons;
import com.tox.bean.ElecGroupCounponsF;
import com.tox.bean.ElecGroupCounponsZ;
import com.tox.bean.ElecUserCouponsRel;
import com.tox.bean.PageView;
import com.tox.dao.ElecCouponsMapper;
import com.tox.dao.ElecGroupCounponsFMapper;
import com.tox.dao.ElecGroupCounponsZMapper;
import com.tox.dao.ElecUserCouponsRelMapper;
import com.tox.dao.ElecUserMapper;
import com.tox.service.ElecOrderService;

/**
 * 优惠卷
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/coupons")
public class ElecCouponsController {
	
	  private static final Logger logger = LoggerFactory.getLogger(ElecCouponsController.class);

	  @Autowired
	  private ElecCouponsMapper couponsDao;
	  @Autowired
	  private ElecUserMapper elecUserMapper;
	  @Autowired
	  private ElecUserCouponsRelMapper userCouponRelDao;
	  @Autowired
	  private ElecOrderService orderService;
	  @Autowired
	  private ElecGroupCounponsFMapper counponsFDao;
	  @Autowired
	  private ElecGroupCounponsZMapper counponsZDao;
	  
	 /**
	  * 添加优惠卷信息 
	  * @param coupon
	  * @return
	  */
    @RequestMapping(value = "/insertCoupons",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertCoupons(@RequestBody ElecCoupons coupon) {
        
    	logger.info(String.format("新增参数：%s", coupon.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        //-------------基础信息赋值------------------
        
        coupon.setCreateDate(new Date());
        
        //-------------信息插入-----------------------
        
        boolean flag = couponsDao.insertSelective(coupon)>0?true:false;
      
        map.put("result", flag);
        
        return map;
    }
    /**
     * 添加组合优惠卷信息 
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/insertGroupCoupons",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertGroupCoupons(@RequestBody Map<Object, Object> param) {
    	logger.info(String.format("添加组合优惠卷信息 ：%s", param.toString()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	String name = (String) param.get("name");
    	ElecGroupCounponsF f= new ElecGroupCounponsF();
    	f.setCreateTime(new Date());
    	f.setIsDel(0);
    	f.setName(name);
    	int flagF = counponsFDao.insertSelective(f);
    	if(flagF==1){
    		List<Map<String, Object>> listData = (List<Map<String, Object>>) param.get("data");
    		for (Map<String, Object> mapZ : listData) {
    		ElecGroupCounponsZ z = new ElecGroupCounponsZ();
			String id = (String) mapZ.get("counponsId");//优惠券id
			Integer counponsId = Integer.valueOf(id);
			String numStr = (String) mapZ.get("num");
			Integer num = Integer.valueOf(numStr);
			z.setCounponsId(counponsId);
			z.setNum(num);
			z.setFatherId(f.getId());
			counponsZDao.insertSelective(z);
			}
    	}else{
    		map.put("result", -100);
    		return map;
    	}
    	map.put("result", 100);
    	
    	return map;
    }
    
    /**
     * 优惠卷列表查询
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/selectCoupons",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectCoupons(@RequestBody ElecCoupons coupon) {
        logger.info(String.format("查询参数：%s", coupon.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        PageView<ElecCoupons> pageView = new PageView<ElecCoupons>();

		pageView.setPageSize(coupon.getPageSize());

		coupon.setPageNum(coupon.getPageNum() * coupon.getPageSize());
		
        List<ElecCoupons> coupons = couponsDao.selectCoupons(coupon);
        
        int count = couponsDao.selectCouponsCount(coupon);
        
        pageView.setList(coupons);

		pageView.setTotal(count);
		
		map.put("data", pageView);
		
        return map;
    }
    /**
     * 组合优惠卷列表查询
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/selectGroupCoupons",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectGroupCoupons(@RequestBody ElecGroupCounponsF couponsF) {
    	logger.info(String.format("查询参数：%s", couponsF.toString()));
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	PageView<ElecGroupCounponsF> pageView = new PageView<ElecGroupCounponsF>();
    	
    	pageView.setPageSize(couponsF.getPageSize());
    	
    	couponsF.setPageNum(couponsF.getPageNum() * couponsF.getPageSize());
    	
//    	List<ElecCoupons> coupons = couponsDao.selectCoupons(coupon);
    	List<ElecGroupCounponsF> listF = counponsFDao.getGroupCounpons(couponsF);
    	
    	int count = counponsFDao.getGroupCounponsCount(couponsF);
    	
    	pageView.setList(listF);
    	
    	pageView.setTotal(count);
    	
    	map.put("data", pageView);
    	
    	return map;
    }
    
    /**
     * 添加用户优惠卷，平台发放
     * @param users
     * @param couponsId
     * @return
     */
    @RequestMapping(value = "/insertUserCouponsREL",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertUserCouponsREL(@RequestBody ElecUserCouponsRel cur) {
 
        Map<String, Object> map = new HashMap<String, Object>();
        
        //-----------------关系表数据赋值------------------------- 
        
        ElecUserCouponsRel eucr = new ElecUserCouponsRel();
        
        eucr.setCreateDate(new Date());
        
        eucr.setCouponsId(cur.getCouponsId());
        
        String[] ids = cur.getUsers().split(",");
        
        for(int i = 0;i<ids.length;i++){
        	
        	//-----------------插入相应信息
        	
        	eucr.setUserId(Integer.valueOf(ids[i]));
        	
        	userCouponRelDao.insertSelective(eucr);
        	
        }

        map.put("result", true);
        
        return map;
    }
    /**
     * 添加用户优惠卷，平台发放
     * @param users
     * @param couponsId
     * @return
     */
    @RequestMapping(value = "/insertUserGroupCouponsREL",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> insertUserGroupCouponsREL(@RequestBody ElecUserCouponsRel cur) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	//-----------------关系表数据赋值------------------------- 
    	
    	List<ElecGroupCounponsZ> counponsZList = counponsZDao.selectByCounponsFId(cur.getCouponsId());
    	String[] ids = cur.getUsers().split(",");
		for(int i = 0;i<ids.length;i++){
			for (ElecGroupCounponsZ elecGroupCounponsZ : counponsZList) {
				ElecUserCouponsRel eucr = new ElecUserCouponsRel();
				eucr.setCreateDate(new Date());
				eucr.setCouponsId(elecGroupCounponsZ.getCounponsId());
				eucr.setUserId(Integer.valueOf(ids[i]));
				eucr.setStatus(0);
				userCouponRelDao.insertSelective(eucr);
			}
		    		
    	}
    	map.put("result", true);
    	
    	return map;
    }
    
    /**
     * 检索优惠卷详情
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/selectCouponsById",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectCouponsById(@RequestBody ElecCoupons coupons) {
       
    	logger.info(String.format("查询参数：%s", coupons.toString()));
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        ElecCoupons coupon = couponsDao.selectByPrimaryKeys(coupons.getId());
        
        map.put("data",coupon);
        
        map.put("result",100);
       
        map.put("msg", "优惠卷查询成功!");
        
        return map;
    }
    /**
     * 查看组合优惠卷详情
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/selectGroupCouponsById",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> selectGroupCouponsById(Integer counponsFId) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	ElecGroupCounponsF counponsF = counponsFDao.selectByPrimaryKey(counponsFId);
    	List<ElecGroupCounponsZ>  counponsZ= counponsZDao.selectByCounponsFId(counponsFId);
    	counponsF.setCounponsZList(counponsZ);
    	map.put("data",counponsF);
    	
    	map.put("result",100);
    	
    	map.put("msg", "优惠卷查询成功!");
    	
    	return map;
    }
    
    /**
     * 修改优惠卷信息
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/editCoupons",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> editCoupons(@RequestBody ElecCoupons coupons) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
    	Boolean flag = couponsDao.updateByPrimaryKeySelective(coupons)>0?true:false;
    	  
    	map.put("result", flag);
    	
    	return map;
    }
    /**
     * 根据用户Id获取优惠卷列表信息
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/getCouponsByUserId",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> getCouponsByUserId(@RequestBody ElecUserCouponsRel record) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	PageView<ElecUserCouponsRel> pageView = new PageView<ElecUserCouponsRel>();
		pageView.setPageSize(record.getPageSize());

		record.setPageNum(record.getPageNum() * record.getPageSize());
    	
    	List<ElecUserCouponsRel> list =userCouponRelDao.getCouponsByUserId(record);
    	int count = userCouponRelDao.getCouponsCountByUserId(record);
    	
    	for (ElecUserCouponsRel elecUserCouponsRel : list) {
			if(1==elecUserCouponsRel.getIsNew()){
				ElecUserCouponsRel ucr = new ElecUserCouponsRel();
				try {
					BeanUtils.copyProperties(ucr, elecUserCouponsRel);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ucr.setIsNew(0);
				userCouponRelDao.updateByPrimaryKeySelective(ucr);
			}
		}
    	
    	pageView.setList(list);

 		pageView.setTotal(count);
 		
 		map.put("data", pageView);
 		
    	map.put("result", "100");
    	
    	return map;
    }
    /**
     * 根据用户Id获取可用优惠卷数量
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/getCouponsNumByUserId",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> getCouponsNumByUserId(Integer userId) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	List<ElecUserCouponsRel> list =userCouponRelDao.getCouponsNumByUserId(userId);
    	for (ElecUserCouponsRel elecUserCouponsRel : list) {
    		if(1==elecUserCouponsRel.getIsNew()){
    			map.put("isNew", true);
    			break;
    		}else{
    			map.put("isNew", false);
    		}
		}
    	map.put("data", list);
    	map.put("result", "100");
    	
    	return map;
    }
    /**
     * 首单免费优惠券查询
     * @return
     */
    @RequestMapping(value = "/getFreeCoupon",method=RequestMethod.POST)
    public boolean  getFreeCoupon(String openId) {
    	Map<String, Object> map = orderService.getFreeCoupon(openId);
    	return (boolean) map.get("result");
    }
    
    /**
     * 查询分享次数
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getFreeCounts",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody Map<String, Object> getFreeCounts(Integer userId) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	Integer userNum = elecUserMapper.selectByPrimaryKeyByFreeCounts(userId);
    	List<ElecUserCouponsRel> list =userCouponRelDao.getCouponsNumByUserId(userId);
    	map.put("usernum", userNum);
    	if(list !=null && list.size()>0){
    		map.put("coupons", list.size());
    	}else{
    		map.put("coupons", "0");
    	}
    	return map;
    }
    
	
}
