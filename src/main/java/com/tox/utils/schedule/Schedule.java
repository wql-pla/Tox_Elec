package com.tox.utils.schedule;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tox.bean.ChargeResult;
import com.tox.bean.ChargeResult_DC;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecOrderDetail;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecStationNorm;
import com.tox.dao.ElecOrderDetailMapper;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.dao.ElecStationMapper;
import com.tox.dao.ElecStationNormMapper;
import com.tox.utils.ElecUtil;

import net.sf.json.JSONObject;
@Component
public class Schedule {

	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
	@Autowired
	private ElecOrderMapper orderDao;
    @Autowired
    private ElecStationNormMapper stationNormDao;
    @Autowired
    private ElecOrderDetailMapper detailMapper;
	@Autowired
	private ElecPileMapper pileDao;
	 /*每天15:39:00时执行*/
	 @Scheduled(cron = "0 30,0 * * * ? ")
	 public void timerCron() throws ParseException {
		 DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    	Calendar calendar = Calendar.getInstance();//日历对象
		        calendar.setTime(new Date());//设置当前日期
		        //小时
		        int hour = calendar.get(Calendar.HOUR_OF_DAY);
		        //时间
		        int min = calendar.get(Calendar.MINUTE);
		      //时间
		        int year = calendar.get(Calendar.YEAR);
		      //时间
		        int month = calendar.get(Calendar.MONTH);
		      //时间
		        int day = calendar.get(Calendar.DAY_OF_MONTH);
		        //组合条件
		        String time="";
		        //根据当前时间组合查询条件 
		        int i=0;
		        if(min>0){
		        	time=hour+":30:00";
		        }else{
		        	time=hour+":00:00";
		        	i=1;
		        }
		        
		        if(hour==0 && min<=1){
		        	time="23:59:00";
		        }
		        //查询所有符合时间段的桩站
		        List<ElecStationNorm> elecStationNorms = stationNormDao.selectByEndTime(time);
		        //通过桩站ID查询所有该桩站下所有开启订单
		        for(ElecStationNorm station: elecStationNorms){
		        	List<ElecOrder> orders = orderDao.selectOrdersByStationId(station.getStationId());
		        	for(ElecOrder order:orders){
		        		//获得当前订单充电量
		        		 Map<String, Object> pileChargeStatus = getPileChargeStatus(order.getId());
		        		 if(pileChargeStatus!=null){
		        		 ChargeResult bean = (ChargeResult) pileChargeStatus.get("data");
		        		 if(bean!=null){
		        		 BigDecimal pileChargeCountss=bean.getCurrentChargeQuantity();
		        		 Double pileChargeCount= Double.valueOf(String.valueOf(pileChargeCountss));
		        		 //当前充电进度大于0
		        	if(pileChargeCount>0){
		        		ElecOrderDetail elecOrderDetail = new ElecOrderDetail();
		        		//查询当前订单是否有过明细
		        		Double selectCountByOrderId = detailMapper.selectCountByOrderId(order.getId());
		        		if(selectCountByOrderId!=null && selectCountByOrderId>0){
		        			pileChargeCount=pileChargeCount-selectCountByOrderId;
		        			pileChargeCount=pileChargeCount*1.05;
		        		}
		        		// 创建订单明细
		        		String time1=year+"-"+month+1+"-"+day+" "+station.getFromDate();
		        		
		        		Date parse = format1.parse(time1);
		        		elecOrderDetail.setFromDate(parse);
		        		elecOrderDetail.setToDate(new Date());
		        		elecOrderDetail.setPrice(station.getBasicChargeAmount()+station.getServiceChargeAmount());
		        		System.out.println("基础电价"+station.getBasicChargeAmount());
		        		System.out.println("服务费电价"+station.getServiceChargeAmount());
		        		System.out.println("电度"+pileChargeCount);
		        		elecOrderDetail.setServiceAmount(station.getServiceChargeAmount()*pileChargeCount);
		        		elecOrderDetail.setCost(station.getBasicChargeAmount()*pileChargeCount);
		        		elecOrderDetail.setOrderId(order.getId());
		        		elecOrderDetail.setElecCount(pileChargeCount);
		        		
		        		detailMapper.insertSelective(elecOrderDetail);
		        	}
		        		 }
		        	}
		        	}
		        
			}
	 }
	 
	 
		public Map<String,Object> getPileChargeStatus(Integer orderId) {
			Map<String,Object> map = new HashMap<>();
	        ElecOrder elecOrder = orderDao.selectByPrimaryKey(orderId);
	        ElecPile pile = pileDao.getPileInfoByPileNumOrWxCode(elecOrder.getPileNum());
			String firmName = pile.getFirm().getFirmName();
			String tradeTypeCode = "1";
			if (!firmName.contains("新亚")) {
				tradeTypeCode = "2";
			}
			String result = ElecUtil.getPileChargeStatus(pile.getPileNum(), tradeTypeCode,pile.getType(),elecOrder.getGunNo());
			JSONObject fromObject = JSONObject.fromObject(result);
			Integer num = Integer.valueOf(fromObject.get("status").toString());
			if (num == 200) {
				Integer type = Integer.valueOf(fromObject.get("type").toString());
				JSONObject fromObject2 = JSONObject.fromObject(fromObject.get("data"));
				if(1==type){
					ChargeResult bean = (ChargeResult) JSONObject.toBean(fromObject2, ChargeResult.class);
					map.put("data", bean);
				}else if(2==type){
					ChargeResult_DC bean = (ChargeResult_DC) JSONObject.toBean(fromObject2, ChargeResult_DC.class);
					map.put("data", bean);
				}
			}else{
				map.put("data", null);
			}
			return map;
		}
	 
/*	 每天15:39:00时执行
	 @Scheduled(cron = "0 0/1 * * * ? ")
	 public void timerCron1() {
	  System.out.println("current time : "+ dateFormat.format(new Date()));
	 }*/
}
