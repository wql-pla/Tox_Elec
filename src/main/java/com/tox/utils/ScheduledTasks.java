package com.tox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tox.bean.ElecOrder;
import com.tox.controller.ElecCouponController;
import com.tox.dao.ElecCouponMapper;
import com.tox.dao.ElecOrderMapper;

@Component
public class ScheduledTasks {

	@Autowired
	private ElecOrderMapper orderDao;
	
	@Autowired 
	private ElecCouponMapper couponDao;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//@Scheduled(fixedRate = 5000)
	@Scheduled(cron ="0 0 0 * * ?")  //每天0点执行任务
    public void reportCurrentTime() {
    	
        System.out.println("现在时间：" + dateFormat.format(new Date()));
        
		List<ElecOrder> ElecOrders = orderDao.selectOrdersByStatus("1");
		
		Date now = new Date();
		
		long seconds = 86400l;
		
		for(ElecOrder record : ElecOrders){
			
			try {
				
				Date thisTime = dateFormat.parse(dateFormat.format(now));
				Date recordTime = dateFormat.parse(dateFormat.format(record.getCreateTime()));
				
				long diff = (thisTime.getTime() - recordTime.getTime())/1000;  
				
				if(diff > seconds){
					
					orderDao.updateOrderStatus(record.getId());
					
					couponDao.updateStatus(record.getCouponId());
					
					orderDao.updateOrderCid(-1*record.getCouponId(),record.getId());
					
				}
				
			} catch (ParseException e) {
				
				e.printStackTrace();
				
			}
			
		}
        
	}
	
}
