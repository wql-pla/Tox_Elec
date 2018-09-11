package com.tox.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.ElecActivity2;
import com.tox.bean.ElecGroupCounponsZ;
import com.tox.bean.ElecUser;
import com.tox.bean.ElecUserCouponsRel;
import com.tox.dao.ElecActivity2Mapper;
import com.tox.dao.ElecGroupCounponsZMapper;
import com.tox.dao.ElecUserCouponsRelMapper;
@Transactional
@Service
public class ElecActivityService {

	private static final Logger logger = LoggerFactory.getLogger(ElecActivityService.class);
	@Autowired
	private ElecActivity2Mapper activity2Dao;
	@Autowired
	private ElecUserCouponsRelMapper ucrDao;
	@Autowired
	private ElecGroupCounponsZMapper couponsZDao;
	
	
	
	
	public List<ElecActivity2> getEnableActivity(){
		List<ElecActivity2> activitys = activity2Dao.getEnableActivity();
		return activitys;
	}
	//注册成功发放优惠券
	public void giveNewUserActivity(ElecUser user){
		logger.info("注册成功送优惠活动 :"+user.toString());
		List<ElecActivity2> activitys = getEnableActivity();
		for (ElecActivity2 elecActivity2 : activitys) {
			if(elecActivity2.getType()==1 &&elecActivity2.getGiveRule()==1){//新注册活动并且是注册成就发优惠券
				if(elecActivity2.getNewCouponType()==1){//普通优惠券
					ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
					logger.info("新用户注册活动 发放普通优惠券 "+ucr.toString());
				}else if(elecActivity2.getNewCouponType()==2){//组合优惠券
					giveNewUserGroupCoupons(user, elecActivity2);
				}
				
			}else if(elecActivity2.getType()==2 && elecActivity2.getGiveRule()==1 && user.getOldUserId()!=null){//老带新活动，并且发放条件是注册成功
				//给新用户优惠活动
				if(null !=elecActivity2.getNewPersonCoupon()){
					if(elecActivity2.getNewCouponType()==1){//普通优惠券
						ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
						logger.info("老带新活动 注册成功 给新用户发放普通优惠券 " +ucr.toString());
					}else if(elecActivity2.getNewCouponType()==2){//组合优惠券
						giveNewUserGroupCoupons(user, elecActivity2);
					}
				}
				//给老用户优惠活动
				if(null !=elecActivity2.getOldPersonCoupon()){
					if(elecActivity2.getOldCouponType()==1){//普通优惠
						ElecUserCouponsRel ucr = giveOldUserCoupons(user, elecActivity2);
						logger.info("老带新活动 注册成功 给老用户发放普通优惠券 " +ucr.toString());
					}else if(elecActivity2.getOldCouponType()==2){// 组合优惠
						giveOldUserGroupCoupons(user, elecActivity2);
					}
					
				}
			}
		}
	}
	//有充值订单发放优惠券
	public void giveRechargeActivity(ElecUser user){
		List<ElecActivity2> activitys = getEnableActivity();
		for (ElecActivity2 elecActivity2 : activitys) {
			if(elecActivity2.getGiveRule()==2){//有充值订单发放优惠券
				if(elecActivity2.getType()==1){
					//新用户逻辑
					if(null!=elecActivity2.getNewPersonCoupon()){
						if(elecActivity2.getNewCouponType()==1){//给新用户发放普通优惠
							ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
							logger.info("有充值订单给新用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getNewCouponType()==2){//给新用户发放组合优惠
							giveNewUserGroupCoupons(user, elecActivity2);
						}
					}
					
				}else if(elecActivity2.getType()==2&&user.getOldUserId()!=null){
					//新用户逻辑
					if(null!=elecActivity2.getNewPersonCoupon()){
						if(elecActivity2.getNewCouponType()==1){//给新用户发放普通优惠
							ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
							logger.info("有充值订单给新用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getNewCouponType()==2){//给新用户发放组合优惠
							giveNewUserGroupCoupons(user, elecActivity2);
						}
					}
					//老用户逻辑
					if(null!=elecActivity2.getOldPersonCoupon()){
						if(elecActivity2.getOldCouponType()==1){//给老用户发放普通优惠
							ElecUserCouponsRel ucr = giveOldUserCoupons(user, elecActivity2);
							logger.info("有充值订单 给老用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getOldCouponType()==2){//给老用户发放组合优惠
							giveOldUserGroupCoupons(user, elecActivity2);
						}
					}
				}
				
			}
		}
	}
	//有充电订单发放优惠券
	public void giveOrderActivity(ElecUser user){
		List<ElecActivity2> activitys = getEnableActivity();
		for (ElecActivity2 elecActivity2 : activitys) {
			
			if(elecActivity2.getGiveRule()==3){//有充电订单发放优惠券
				if(elecActivity2.getType()==1){
					//新用户逻辑
					if(null!=elecActivity2.getNewPersonCoupon()){
						if(elecActivity2.getNewCouponType()==1){//给新用户发放普通优惠
							ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
							logger.info("有充值订单给新用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getNewCouponType()==2){//给新用户发放组合优惠
							giveNewUserGroupCoupons(user, elecActivity2);
						}
					}
				}else if(elecActivity2.getType()==2 &&user.getOldUserId()!=null){
					//新用户逻辑
					if(null!=elecActivity2.getNewPersonCoupon()){
						if(elecActivity2.getNewCouponType()==1){//给新用户发放普通优惠
							ElecUserCouponsRel ucr = giveNewUserCoupons(user, elecActivity2);
							logger.info("有充值订单给新用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getNewCouponType()==2){//给新用户发放组合优惠
							giveNewUserGroupCoupons(user, elecActivity2);
						}
					}
					//老用户逻辑
					if(null!=elecActivity2.getOldPersonCoupon()){
						if(elecActivity2.getOldCouponType()==1){//给老用户发放普通优惠
							ElecUserCouponsRel ucr = giveOldUserCoupons(user, elecActivity2);
							logger.info("有充值订单 给老用户发放普通优惠券 " +ucr.toString());
						}else if(elecActivity2.getOldCouponType()==2){//给老用户发放组合优惠
							giveOldUserGroupCoupons(user, elecActivity2);
						}
					}
				}
				
				
				
				
			}
		}
	}
	
	private ElecUserCouponsRel giveNewUserCoupons(ElecUser user, ElecActivity2 elecActivity2){
		ElecUserCouponsRel ucr =  new ElecUserCouponsRel();
		ucr.setCouponsId(elecActivity2.getNewPersonCoupon());
		ucr.setCreateDate(new Date());
		ucr.setStatus(0);
		ucr.setUserId(user.getId());
		ucr.setActivityId(elecActivity2.getId());
		ucrDao.insertSelective(ucr);
		return ucr;
	}
	private ElecUserCouponsRel giveOldUserCoupons(ElecUser user, ElecActivity2 elecActivity2){
		ElecUserCouponsRel ucr =  new ElecUserCouponsRel();
		ucr.setCouponsId(elecActivity2.getOldPersonCoupon());
		ucr.setCreateDate(new Date());
		ucr.setStatus(0);
		ucr.setUserId(user.getOldUserId());
		ucr.setActivityId(elecActivity2.getId());
		ucrDao.insertSelective(ucr);
		return ucr;
	}
	private void giveNewUserGroupCoupons(ElecUser user, ElecActivity2 elecActivity2){
		List<ElecGroupCounponsZ> counponsZList = couponsZDao.selectByCounponsFId(elecActivity2.getNewPersonCoupon());
		for (ElecGroupCounponsZ elecGroupCounponsZ : counponsZList) {
			ElecUserCouponsRel ucr =  new ElecUserCouponsRel();
			ucr.setCouponsId(elecGroupCounponsZ.getCounponsId());
			ucr.setCreateDate(new Date());
			ucr.setStatus(0);
			ucr.setUserId(user.getId());
			ucr.setActivityId(elecActivity2.getId());
			ucrDao.insertSelective(ucr);
			logger.info("有充值订单给新用户发放组合优惠券 " +ucr.toString());
		}
	}
	private void giveOldUserGroupCoupons(ElecUser user, ElecActivity2 elecActivity2){
		List<ElecGroupCounponsZ> counponsZList = couponsZDao.selectByCounponsFId(elecActivity2.getOldPersonCoupon());
		for (ElecGroupCounponsZ elecGroupCounponsZ : counponsZList) {
			ElecUserCouponsRel ucr =  new ElecUserCouponsRel();
			ucr.setCouponsId(elecGroupCounponsZ.getCounponsId());
			ucr.setCreateDate(new Date());
			ucr.setStatus(0);
			ucr.setUserId(user.getOldUserId());
			ucr.setActivityId(elecActivity2.getId());
			ucrDao.insertSelective(ucr);
			logger.info("有充值订单给老用户发放组合优惠券 " +ucr.toString());
		}
	}
	
}
