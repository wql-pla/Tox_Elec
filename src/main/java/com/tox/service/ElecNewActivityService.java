package com.tox.service;

import com.tox.bean.ActivityNewInfo;
import com.tox.bean.ActivityNewMonthInfo;
import com.tox.bean.ActivityNewOrder;
import com.tox.bean.ActivityNewUser;
import com.tox.dao.ActivityNewInfoMapper;
import com.tox.dao.ActivityNewMonthInfoMapper;
import com.tox.dao.ActivityNewOrderMapper;
import com.tox.dao.ActivityNewUserMapper;
import com.tox.utils.date.dateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    @Autowired
    private ActivityNewOrderMapper acOrderDao;
    @Autowired
    private ActivityNewUserMapper acUserDao;
    @Autowired
    private ActivityNewMonthInfoMapper activityNewMonthInfoDao;
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
    public Boolean weixinNotify (String tno, String wno) throws Exception {

        //---------------修改当前订单状态start--------------
        ActivityNewOrder record = acOrderDao.selectByTno(tno);

        if (record.getwNo()!=null) return true;
        record.settNo(tno);
        record.setwNo(wno);
        record.setIsDel(0);
        acOrderDao.updateByPrimaryKeySelective(record);
        //---------------修改当前订单状态end--------------

        //---------------获取当前用户对象------------------
        ActivityNewUser activityNewUser = acUserDao.selectByPrimaryKey(record.getUserId());

        //---------------判断当前用户桩站是否上线------------
        if ( activityNewUser.getFromDate() == null && activityNewUser.getToDate()== null ){

            //修改支付状态---暂不处理
            if (activityNewUser.getIsPay().equals("0")) {
                activityNewUser.setIsPay("1");  //已经支付
                acUserDao.updateByPrimaryKeySelective(activityNewUser);
            }
        //---上线---正常续租/过期续租-----------------
        }else{

            ActivityNewMonthInfo monthInfo = new ActivityNewMonthInfo();
            //--正常续租/开始时间不用变--正常续期
            if ( dateUtil.getDay(new Date(),activityNewUser.getToDate()) >= 0 ){

                //月卡到期时间添加一个自然月
                monthInfo.setPhone(activityNewUser.getPhone());
                monthInfo.setMonthStartBeginDate(activityNewUser.getFromDate());
                monthInfo.setMonthStartEndDate(activityNewUser.getFromDate());
                monthInfo.setMonthEndbeginDate(activityNewUser.getToDate());
                monthInfo.setMonthEndFinishDate(activityNewUser.getToDate());
                List<ActivityNewMonthInfo> activityNewMonthInfos = activityNewMonthInfoDao.selectMonthInfos(monthInfo);

                //到期时间添加一个自然月
                activityNewUser.setToDate(dateUtil.reckonMonths(activityNewUser.getToDate(), 1));
                acUserDao.updateByPrimaryKeySelective(activityNewUser);
                //月卡记录到期时间添加一个自然月
                if (activityNewMonthInfos.size() == 1){
                    activityNewMonthInfos.get(0).setMonthEndDate(activityNewUser.getToDate());
                    activityNewMonthInfoDao.updateByPrimaryKeySelective(activityNewMonthInfos.get(0));
                }

                //--过期续租----
            }else{

                //获取当前活动
                ActivityNewInfo activityNewInfo = activityNewInfoDao.selectByPrimaryCode(activityNewUser.getType());
                activityNewUser.setFromDate(new Date());
                if (activityNewInfo.getActivityDate()>(dateUtil.getDay(getFristDay(activityNewUser.getFromDate()),activityNewUser.getFromDate()))+1){
                    activityNewUser.setToDate(getLastDay(activityNewUser.getFromDate()));//当月最后一天
                }else{
                    activityNewUser.setToDate(getLastDay(dateUtil.reckonMonths(activityNewUser.getFromDate(),1)));//下个月最后一天
                }
                //---用户月卡状态设置---
                acUserDao.updateByPrimaryKeySelective(activityNewUser);
                //--月卡记录直接添加记录
                monthInfo.setCity(activityNewUser.getCity());
                monthInfo.setCreateDate(new Date());
                monthInfo.setMonthStartDate(activityNewUser.getFromDate());
                monthInfo.setMonthEndDate(activityNewUser.getToDate());
                monthInfo.setPhone(activityNewUser.getPhone());
                monthInfo.setIsDel("0");
                activityNewMonthInfoDao.insertSelective(monthInfo);

            }

        }
        return true;
    }

    public static Date  getFristDay(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天

    return c.getTime();
}

    public static Date getLastDay(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
    return ca.getTime();
}

    public static void main(String[] args) {
        System.out.println("ddd"+getLastDay(dateUtil.reckonMonths(new Date(),1)));
    }

}
