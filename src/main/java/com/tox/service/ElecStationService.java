package com.tox.service;

import com.tox.bean.ActivityNewMonthInfo;
import com.tox.bean.ActivityNewUser;
import com.tox.dao.ActivityNewMonthInfoMapper;
import com.tox.dao.ActivityNewOrderMapper;
import com.tox.dao.ActivityNewUserMapper;
import com.tox.utils.date.dateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class ElecStationService {

    @Autowired
    ActivityNewUserMapper activityNewUserMapper;
    @Autowired

    ActivityNewOrderMapper activityNewOrderMapper;
    @Autowired

    ActivityNewMonthInfoMapper activityNewMonthInfoMapper;


    public void updateActiveUser(String phone, Date online){
        ActivityNewUser u=new ActivityNewUser();
        u.setPhone(phone);
        u=  activityNewUserMapper.selectByPhone(u);
        if(u==null){
            return;
        }
        if("1".equals(u.getIsPay())){
            Integer count= activityNewOrderMapper.getCountByUserId(u.getId());
            if(count>0){
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date online=null;
//                try{
//                    online= sdf.parse(onlineDate);
//
//                }catch (Exception e){
//                    throw  new Exception(phone+"online日期格式有误!");
//                }
                Date endDate= dateUtil.reckonMonths(online, count);
                u.setFromDate(online);
                u.setToDate(endDate);
                u.setFirstOnlineDate(online);
                u.setIsPay("2");
                activityNewUserMapper.updateByPrimaryKeySelective(u);
                ActivityNewMonthInfo info=new ActivityNewMonthInfo();
                info.setCity(u.getCity());
                info.setCreateDate(new Date());
                info.setIsDel("0");
                info.setMonthEndDate(endDate);
                info.setMonthStartDate(online);
                info.setPhone(phone);
               // info.setOnlineDate(online);
                activityNewMonthInfoMapper.insertSelective(info);





            }
        }



    }
}
