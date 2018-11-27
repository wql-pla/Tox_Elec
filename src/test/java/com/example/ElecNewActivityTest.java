package com.example;


import com.tox.Application;
import com.tox.service.ElecNewActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ElecNewActivityTest {


    @Autowired
    private ElecNewActivityService elecNewActivityService;
    /**
     *  测试98元活动获取金额BY userId
     *
     *  通过userid获取当前用户参与活动的动态金额
     */
    @Test
    public void getNewActivityTotal_fee (){

        String s = elecNewActivityService.getNewActivityTotal_fee(1);

        System.out.println(s);

    }


    /**
     * 微信回调逻辑测试
     *
     * 前端页面完成支付，微信回调验签，根据微信返回商户订单号，查找当前用户信息，
     *  判断当前用户是否上线，判断用户是否正常续租，判断用户是否过期续租，
     *  用户桩站未上线--修改当前用户的支付订单状态，保存当前用户的支付订单信息
     *  用户上线正常续租--延期用户月卡时间
     *  用户上线过期续租--修改用户月卡时间 -- 添加用户月卡记录
     *
     */
    @Test
    public void  WxinNotifyTest (){

        try {
            elecNewActivityService.weixinNotify("TOX2018112213320436300000001","4200000210201811227459100228");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
