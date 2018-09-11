package com.tox.service;

import com.tox.bean.ChargeResult;
import com.tox.bean.ChargeResult_DC;
import com.tox.bean.ElecOrder;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecStationNorm;
import com.tox.bean.ElecStore;
import com.tox.bean.ElecUser;
import com.tox.bean.PageResponse;
import com.tox.dao.ElecOrderMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.dao.ElecStationNormMapper;
import com.tox.dao.ElecStoreMapper;
import com.tox.dao.ElecUserMapper;
import com.tox.utils.ElecUtil;
import com.tox.utils.date.dateUtil;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.font.CreatedFontTracker;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ElecWebService {
    @Autowired
    private ElecUserMapper userDao;
    @Autowired
    private ElecOrderMapper orderDao;
    @Autowired
    private ElecPileMapper pileDao;
    @Autowired
    private ElecStationNormMapper stationNormDao;

    public Map<String,Object>  getOrderPileUserInfo(Integer orderId,Integer userId) throws ParseException {
    	Map<String, Object> map = new HashMap<String, Object>();
    	ElecUser elecUser = userDao.selectByPrimaryKey(userId);
    	map.put("user", elecUser);
    	ElecOrder elecOrder = orderDao.getOrderAndCouponByOrderId(orderId);
    	if(elecOrder.getCreateTime()!= null && elecOrder.getEndTime() !=null){
			long seconds = dateUtil.getSeconds(elecOrder.getCreateTime(),elecOrder.getEndTime());
			elecOrder.setTimeDiff(seconds);
		}
    	map.put("order", elecOrder);
    	ElecPile pile = pileDao.getPileInfoByPileNumOrWxCode(elecOrder.getPileNum());
    	Integer piletype=1;
 		//添加场站电价信息
 		if(pile.getType()==3||pile.getType()==4){
 			piletype=2;
 		}
    	//添加桩站电价
 		List<ElecStationNorm> normList = stationNormDao.selectByStationIdandType(pile.getChargeStandardId(), piletype);
 		SimpleDateFormat sdf_input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//输入格式
    	Calendar calendar = Calendar.getInstance();//日历对象
    	calendar.setTime(new Date());//设置当前日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
    	for (int i=0;i<normList.size();i++) {
    		String fromDate = normList.get(i).getFromDate();
    		String toDate = normList.get(i).getToDate();
    		try {
				Date parseFrom = sdf_input.parse(year+"-"+month+"-"+day+" "+fromDate);
				Date parseTo = sdf_input.parse(year+"-"+month+"-"+day+" "+toDate);
				if(parseFrom.getTime()<=new Date().getTime()&& parseTo.getTime()>=new Date().getTime()){
					normList.get(i).setCurrent(1);
					if(i+1==normList.size()){
						normList.get(0).setNext(1);
					}else{
						normList.get(i+1).setNext(1);
					}
					
					break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    	pile.getStation().setNormList(normList);
    	map.put("pile", pile);
		String firmName = pile.getFirm().getFirmName();
		String tradeTypeCode = "1";
		if(firmName.contains("循道")){
			tradeTypeCode= "2";
		}else if(firmName.contains("科士达")){
			tradeTypeCode= "3";
		}
		String result = ElecUtil.getPileChargeStatus(pile.getPileNum(), tradeTypeCode,pile.getType(),elecOrder.getGunNo());
		JSONObject fromObject = JSONObject.fromObject(result);
		Integer num = Integer.valueOf(fromObject.get("status").toString());
		if (num == 200) {
			Integer type = Integer.valueOf(fromObject.get("type").toString());
			JSONObject fromObject2 = JSONObject.fromObject(fromObject.get("data"));
			if(1==type){
				ChargeResult bean = (ChargeResult) JSONObject.toBean(fromObject2, ChargeResult.class);
				BigDecimal degree = bean.getCurrentChargeQuantity().multiply(BigDecimal.valueOf(1.05));
				bean.setCurrentChargeQuantity(degree);
				map.put("charging", bean);
			}else if(2==type){
				ChargeResult_DC bean = (ChargeResult_DC) JSONObject.toBean(fromObject2, ChargeResult_DC.class);
				BigDecimal degree = bean.getCurrentChargeQuantity().multiply(BigDecimal.valueOf(1.05));
				bean.setCurrentChargeQuantity(degree);
				map.put("charging", bean);
			}
		}else{
			map.put("charging", null);
		}
    	
    	return map;
    }

}
