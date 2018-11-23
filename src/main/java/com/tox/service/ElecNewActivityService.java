package com.tox.service;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ActivityNewUser;
import com.tox.dao.ActivityNewInfoMapper;
import com.tox.dao.ActivityNewUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 98元活动
 */
@Transactional
@Service
public class ElecNewActivityService {

	@Autowired
	private ActivityNewUserMapper activityNewDao;
	@Autowired
    private ActivityNewInfoMapper activityNewInfoDao;
	
    /**
     * 获取当前用户参与的活动金额
     * @return
     */
    public String  getNewActivityTotal_fee(Integer userId){

        //获取当前用户参加活动码
        ActivityNewUser activityNewUser = activityNewDao.selectByPrimaryKey(userId);
        //获取当前活动金额By CODE
        ActivityNewInfo activityNewInfo = activityNewInfoDao.selectByPrimaryCode(activityNewUser.getType());
        //返回相应金额
        return activityNewInfo.getMonthAmount().toString();
        }


    /**
     * 微信回调各种校验
     */




}
