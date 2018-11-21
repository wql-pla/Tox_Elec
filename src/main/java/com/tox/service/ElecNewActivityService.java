package com.tox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.ElecUser;
import com.tox.dao.ElecUserMapper;


/**
 * 98元活动
 */
@Transactional
@Service
public class ElecNewActivityService {

	@Autowired
	private ElecUserMapper elecUserDao;
	
    /**
     * 获取当前用户参与的活动金额
     * @return
     */
    public String  getNewActivityTotal_fee(Integer userId){
    	
    	ElecUser user = elecUserDao.selectByPrimaryKey(userId);
    	
    return "98";
}
}
