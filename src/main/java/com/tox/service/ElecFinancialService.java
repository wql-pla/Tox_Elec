package com.tox.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.ElecRecharge;
import com.tox.bean.ElecUser;
import com.tox.dao.ElecRechargeMapper;
import com.tox.dao.ElecUserMapper;

@Transactional(propagation= Propagation.REQUIRES_NEW)
@Service
public class ElecFinancialService {

	@Autowired
	private ElecRechargeMapper recgargeDao;
	@Autowired
	private ElecUserMapper elecUserDao;
	@Autowired
	private ElecActivityService activityService;
	
	//添加充值订单
	public int createRecharge(ElecRecharge record){
		record.setRechargeDate(new Date());
		record.setIsdel(1);//预付款订单不需要查询
		int state = recgargeDao.insertSelective(record);
		return state;
	}
	
	//修改订单状态
	public boolean editRecharge(ElecRecharge record) {
		boolean state = true;
		List<ElecRecharge> recinfolist =recgargeDao.selectByPayIDE(record.getPayIde());
		ElecRecharge recinfo = recinfolist.get(0);
		if (recinfo!=null&&recinfo.getIsdel()==1) {
		 ElecUser user = elecUserDao.selectByPrimaryKey(recinfo.getUserId());
		 user.setBalance((user.getBalance()==null?0:user.getBalance())+recinfo.getRechargeMoney());
		 user.setGiveMoney((user.getGiveMoney()==null?0:user.getGiveMoney())+recinfo.getPresentMoney());
		if (elecUserDao.updateByPrimaryKeySelective(user)<=0) {
			return false;
		}
			record.setIsdel(0);
			record.setId(recinfo.getId());
			record.setUserId(user.getId());
			state = recgargeDao.updateByPrimaryKeySelective(record)>0?true:false;
		}
		return state;
	}

	public void giveUserCoupons(Integer userId) {
		List<ElecRecharge> recharges = recgargeDao.getRechargesByUserId(userId);
		if(recharges.size()==1){
			ElecUser user = elecUserDao.selectByPrimaryKey(userId);
			activityService.giveRechargeActivity(user);
		}
	}
}
