package com.tox.service;

import com.tox.bean.*;
import com.tox.dao.*;
import com.tox.utils.ElecUtil;
import com.tox.utils.SystemConstant;
import com.tox.utils.dateUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

//import com.tox.utils.RabbitMQ;

@Service
@Transactional
public class ElecOrderService {
	private static final Logger logger = LoggerFactory.getLogger(ElecOrderService.class);
	private static AtomicLong at = new AtomicLong();
	@Autowired
	private ElecOrderMapper orderDao;
	@Autowired
	private ElecPileMapper pileDao;
	@Autowired
	private ElecFirmMapper firmDao;
	@Autowired
	private ElecCouponsMapper couponsDao;
	@Autowired
	private ElecUserCouponsRelMapper ucrDao;
	@Autowired
	private ElecChargeRecordMapper recordDao;
	@Autowired
	private ElecOrderDetailMapper detailMapper;
    @Autowired
    private ElecStationNormMapper stationNormDao;
    @Autowired
    private ElecStationMapper stationDao;
    @Autowired
    private ElecUserAppendMapper appendDao;
	
	private String callBackQueue = "carportQueue";
	
	public boolean openCharging(ElecOrder order,ElecPile pile){
		ElecFirm firm = firmDao.selectByPrimaryKey(pile.getFirmId());
		String tradeTypeCode ="1";
		if(firm.getFirmName().contains("循道")){
			tradeTypeCode= "2";
		}else if(firm.getFirmName().contains("科士达")){
			tradeTypeCode= "3";
		}
		//开启充电
		logger.info("开启充电桩===========");
		//如果开启充电失败，最多会请求2次
		String result = "";
		for(int i =1;i<=2;i++){
			logger.info(String.format("开启电桩第：%s次请求", i));
			if(order.getElecPrice()==0){
				result = ElecUtil.sendPost(pile.getPileNum(), order.getGunNo(),"1", 0, order.getId(),tradeTypeCode,pile.getType());
			}else{
				result = ElecUtil.sendPost(pile.getPileNum(),order.getGunNo(), "4", order.getElecTotalCount(), order.getId(),tradeTypeCode,pile.getType());
			}
			logger.info(String.format("充电桩第：%s 次返回结果：%s", i,result));
			if("SUCCESS".equals(result)){
				order.setStatus("1");//开启充电桩 成功
				if(3==pile.getType()||5==pile.getType()){
					//单枪电桩被占用
					pile.setIsUsed(1);
				}else{
					if(0==pile.getIsUsed()){
						if(0==order.getGunNo()){
							pile.setIsUsed(1);//A枪被占用
						}else if(1==order.getGunNo()){
							pile.setIsUsed(2);//B枪被占用
						}
					}else if(1==pile.getIsUsed()||2==pile.getIsUsed()){
						pile.setIsUsed(3);//枪都被占用
					}
				}
//				orderDao.updateByPrimaryKeySelective(order);
//				pileDao.updateByPrimaryKeySelective(pile);
				return true;
			}else{
				order.setStatus("2");//开启充电桩失败
//				orderDao.updateByPrimaryKeySelective(order);
			}
		}
		return false;
	}
	//是否满足首单免费活动
	public Integer getFreeCoupon(ElecUser user){
		List<ElecCoupons> list = couponsDao.getFreeCoupon();
    	if(null != list& list.size()>0){
    		return list.get(0).getId();
    	}
    	return null;
	}
	//是否满足首单免费活动 for app
	public @ResponseBody Map<String, Object> getFreeCoupon(String openId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ElecCoupons> list = couponsDao.getFreeCoupon();
    	if(null != list& list.size()>0){
    		List<ElecOrder> orders = orderDao.getOrderListByOpenid(openId);
    		if(null ==orders || orders.size()<1){
    			map.put("couponId", list.get(0).getId());
    			map.put("result", true);
    			return map;
    		}
    	}
    	map.put("result", false);
    	return map;
	}
	//绑定首单免费活动
	public Integer bindFreeOrder(Integer couponId,Integer userId){
		logger.info(String.format("绑定首单免费活动 couponId：%s,userId:%s", couponId,userId));
		ElecUserCouponsRel ucr = new ElecUserCouponsRel();
		ucr.setCouponsId(couponId);
		ucr.setCreateDate(new Date());
		ucr.setStatus(1);
		ucr.setUserId(userId);
		ucrDao.insertSelective(ucr);
		return  ucr.getId();
		
	}
	//组装订单费用信息
	public void setOrderMoney(ElecPile pile,ElecUser user,ElecOrder order,Double balance) throws ParseException {
		order.setPileId(pile.getId());
		ElecStation station = pile.getStation();


		DirectStation directStation = stationDao.selectDirectStationByPrimaryKey(pile.getChargeStandardId());
		Integer chargeType = station.getChargeType();
		Double basicChargeAmount=0D;
		Double serviceChargeAmount =0D;
		
		BigDecimal serviceAmount = BigDecimal.ZERO;
		List<String> phones = new ArrayList<String>();
		if(null!=station.getPersonType()&& 1==station.getPersonType()){
			ElecUserAppend append = new ElecUserAppend();
			append.setUserAccount(station.getPersonPhone());
			List<ElecUserAppend> elecUserAppends = appendDao.selectStationAndAppent(append);
			phones.add(station.getPersonPhone());
			for (ElecUserAppend elecUserAppend : elecUserAppends) {
				phones.add(String.valueOf(elecUserAppend.getUserPhone()));
			}
		}
		if(null!=station.getPersonType()&& 1==station.getPersonType()&&phones.contains(user.getPhone())){
			logger.info("桩东充电============");
			serviceAmount=BigDecimal.valueOf(station.getPersonServiceAmount());
			basicChargeAmount =station.getPersonBasicChargeAmount();
		}else{
			Date now = new Date();
			//双十一活动免普通桩东服务费
			if(chargeType==1&& (pile.getType()==3 ||pile.getType()==4)){//全时 交流价格
				basicChargeAmount = station.getBasicChargeAmount();
				serviceChargeAmount = station.getServiceChargeAmount();
			}else if(chargeType==1 &&(pile.getType()==5 ||pile.getType()==6)){//全时 直流价格
				basicChargeAmount = directStation.getDirectBasicChargeAmout();
				serviceChargeAmount = directStation.getDirectServiceChargeAmount();
			}else if(chargeType==2 &&(pile.getType()==3 || pile.getType()==4)){//分时 交流价格
				List<ElecStationNorm> normList = stationNormDao.selectMaxAmountByStationIdandType(pile.getChargeStandardId(), 2);
				basicChargeAmount=normList.get(0).getBasicChargeAmount();
				serviceChargeAmount=normList.get(0).getServiceChargeAmount();
			}else if(chargeType==2&&(pile.getType()==5||pile.getType()==6)){//分时 直流价格
				List<ElecStationNorm> normList = stationNormDao.selectMaxAmountByStationIdandType(station.getId(), 1);
				basicChargeAmount=normList.get(0).getBasicChargeAmount();
				serviceChargeAmount=normList.get(0).getServiceChargeAmount();
			}
			if(now.after(dateUtil.getDateHms(SystemConstant.startDate11))&&now.before(dateUtil.getDateHms(SystemConstant.endDate11))){
				serviceChargeAmount=0D;

			}

			serviceAmount= BigDecimal.valueOf(serviceChargeAmount);
		}
		BigDecimal basicAmount = BigDecimal.valueOf(basicChargeAmount);
//		serviceAmount= BigDecimal.valueOf(serviceChargeAmount);
		BigDecimal balanceAmount = BigDecimal.valueOf(balance);
		if(basicAmount.add(serviceAmount).doubleValue()!=0){
			BigDecimal setScale = balanceAmount.divide((basicAmount.add(serviceAmount)),2,BigDecimal.ROUND_DOWN);
			BigDecimal divide = setScale.divide(BigDecimal.valueOf(1.05),2,BigDecimal.ROUND_DOWN);//增加5%的收费
			order.setElecTotalAmount(balance);
			order.setElecTotalCount(divide.doubleValue());
		}else{
			order.setElecTotalAmount(0.00);
			order.setElecTotalCount(0.00);
		}
		order.setElecPrice(basicAmount.add(serviceAmount).doubleValue());
	}
	public void updateOrderAndPile(ElecOrder order, ElecPile pile) {
		orderDao.updateByPrimaryKeySelective(order);
		pileDao.updateByPrimaryKeySelective(pile);
	}
	public void endOrder(ElecOrder elecOrder,ElecUser user, ElecPile pile,ElecStation station,ResultXYDF bean,Double basicAmount,Double serviceAmount,Double totalAmout,List<String>phones) throws NumberFormatException, ParseException {
		if(null!=station.getPersonType()&& 1==station.getPersonType()&&phones.contains(user.getPhone())){
			logger.info("桩东结束充电============");
			if("99".equals(bean.getEndReason())){//充满,但是订单未结束
				//上报的充电电量大于上次的电量才会修改充电信息，否则不修改
				if(Double.valueOf(bean.getTotalAmmeterDegree())==0 || Double.valueOf(bean.getTotalAmmeterDegree())>(elecOrder.getRealCount() ==null?0:elecOrder.getRealCount())){
					BigDecimal multiply = BigDecimal.valueOf(Double.valueOf(bean.getTotalAmmeterDegree())).multiply(BigDecimal.valueOf(1.05D)).setScale(2, BigDecimal.ROUND_UP);
					elecOrder.setRealCount(multiply.doubleValue());
					if(elecOrder.getElecTotalCount()==elecOrder.getRealCount()){
						elecOrder.setOrderFee(elecOrder.getElecTotalAmount());
						elecOrder.setRealAmount(elecOrder.getElecTotalAmount());
					}else{
						elecOrder.setOrderFee(elecOrder.getRealCount()*totalAmout);
						elecOrder.setRealAmount(elecOrder.getRealCount()*totalAmout);
					}
					elecOrder.setBasicChargeTotal(basicAmount*elecOrder.getRealCount());
					elecOrder.setServiceChargeTotalSelf(serviceAmount*elecOrder.getRealCount());
					elecOrder.setEndTime(new Date());
					elecOrder.setBasicPayStatus("0");
					elecOrder.setServicePayStatus("0");
					elecOrder.setServiceChargeTotalThird(0.00);
					orderDao.updateByPrimaryKeySelective(elecOrder);
				}
			}else{//结束订单
				BigDecimal multiply = BigDecimal.valueOf(Double.valueOf(bean.getTotalAmmeterDegree())).multiply(BigDecimal.valueOf(1.05D)).setScale(2, BigDecimal.ROUND_UP);
				//每次充电电量累积到电桩上
				if(("0".equals(elecOrder.getStatus())&&multiply.doubleValue()>elecOrder.getRealCount())){
					pile.setAllCount((pile.getAllCount()==null?0:pile.getAllCount())+(multiply.doubleValue()-elecOrder.getRealCount()));
				}else if("1".equals(elecOrder.getStatus())){
					pile.setAllCount((pile.getAllCount()==null?0:pile.getAllCount())+multiply.doubleValue());
				}
				elecOrder.setRealCount(multiply.doubleValue());
				if(elecOrder.getElecTotalCount()<=Double.valueOf(bean.getTotalAmmeterDegree())){
					elecOrder.setOrderFee(elecOrder.getElecTotalAmount());
					elecOrder.setRealAmount(elecOrder.getElecTotalAmount());
				}else{
					elecOrder.setOrderFee(elecOrder.getRealCount()*totalAmout);
					BigDecimal realAmountB = BigDecimal.valueOf(elecOrder.getRealCount()).multiply(BigDecimal.valueOf(totalAmout));
					elecOrder.setRealAmount(realAmountB.doubleValue());
				}
				elecOrder.setBasicChargeTotal(basicAmount*elecOrder.getRealCount());
				elecOrder.setServiceChargeTotalSelf(serviceAmount*elecOrder.getRealCount());
				if(null==elecOrder.getEndTime()){
					elecOrder.setEndTime(new Date());
				}
				elecOrder.setBasicPayStatus("0");
				elecOrder.setServicePayStatus("0");
				elecOrder.setServiceChargeTotalThird(0.00);
				editGunStatusAndCouponsStatus(elecOrder,pile,bean);
				pileDao.updateByPrimaryKeySelective(pile);
				if(elecOrder.getRealCount()==0){
					elecOrder.setStatus("2");
				}else{
					elecOrder.setStatus("0");
				}
				orderDao.updateByPrimaryKeySelective(elecOrder);
				if(!BigDecimal.ZERO.equals(BigDecimal.valueOf(station.getPersonBasicChargeAmount()))){
				    //分润
				    sendServiceMoney(elecOrder.getId(),station.getPersonPhone(),elecOrder.getRealCount(),station.getPersonBasicChargeAmount(),0D);
				}
			//}
			}
			
		}else{
			HashMap countOrderElec = countOrderElec(elecOrder.getId(), Double.valueOf(bean.getTotalAmmeterDegree()));
			
			Double elecSum = (Double) countOrderElec.get("elec");
			Double amountCostSum = (Double) countOrderElec.get("costAmount");
			Double serivceAmountSum = (Double) countOrderElec.get("serivceAmount");
			Date now = new Date();
			//双十一活动免普通桩东服务费
			if(now.after(dateUtil.getDateHms(SystemConstant.startDate11))&&now.before(dateUtil.getDateHms(SystemConstant.endDate11))){
				serivceAmountSum=0D;
			}
			amountCostSum+=serivceAmountSum;
			if("99".equals(bean.getEndReason())){//充满,但是订单未结束
				//上报的充电电量大于上次的电量才会修改充电信息，否则不修改
				if(Double.valueOf(bean.getTotalAmmeterDegree())==0 || Double.valueOf(bean.getTotalAmmeterDegree())>(elecOrder.getRealCount() ==null?0:elecOrder.getRealCount())){
//					BigDecimal multiply = BigDecimal.valueOf(Double.valueOf(bean.getTotalAmmeterDegree())).multiply(BigDecimal.valueOf(1.05D)).setScale(2, BigDecimal.ROUND_UP);
					elecOrder.setRealCount(elecSum);
					if(elecOrder.getElecTotalCount()<=Double.valueOf(bean.getTotalAmmeterDegree())){
						elecOrder.setOrderFee(elecOrder.getElecTotalAmount());
						elecOrder.setRealAmount(elecOrder.getElecTotalAmount());
					}else{
						elecOrder.setOrderFee(amountCostSum);
						elecOrder.setRealAmount(amountCostSum);
					}
//					elecOrder.setBasicChargeTotal(basicAmount*elecOrder.getRealCount());
//					elecOrder.setServiceChargeTotalSelf(serviceAmount*elecOrder.getRealCount());
					elecOrder.setEndTime(new Date());
					elecOrder.setBasicPayStatus("0");
					elecOrder.setServicePayStatus("0");
					elecOrder.setServiceChargeTotalThird(0.00);
					orderDao.updateByPrimaryKeySelective(elecOrder);
				}
			}else{//结束订单
//				BigDecimal multiply = BigDecimal.valueOf(Double.valueOf(bean.getTotalAmmeterDegree())).multiply(BigDecimal.valueOf(1.05D)).setScale(2, BigDecimal.ROUND_UP);
				//每次充电电量累积到电桩上
				if(("0".equals(elecOrder.getStatus())&&elecSum>elecOrder.getRealCount())){
					pile.setAllCount((pile.getAllCount()==null?0:pile.getAllCount())+(elecSum-elecOrder.getRealCount()));
				}else if("1".equals(elecOrder.getStatus())){
					pile.setAllCount((pile.getAllCount()==null?0:pile.getAllCount())+elecSum);
				}
				elecOrder.setRealCount(elecSum);
				if(elecOrder.getElecTotalCount()<=Double.valueOf(bean.getTotalAmmeterDegree())){
					elecOrder.setOrderFee(elecOrder.getElecTotalAmount());
					elecOrder.setRealAmount(elecOrder.getElecTotalAmount());
				}else{
					elecOrder.setOrderFee(amountCostSum);
//					BigDecimal realAmountB = BigDecimal.valueOf(elecOrder.getRealCount()).multiply(BigDecimal.valueOf(totalAmout));
					elecOrder.setRealAmount(amountCostSum);
				}
//				elecOrder.setBasicChargeTotal(basicAmount*elecOrder.getRealCount());
//				elecOrder.setServiceChargeTotalSelf(serviceAmount*elecOrder.getRealCount());
				if(null==elecOrder.getEndTime()){
					elecOrder.setEndTime(new Date());
				}
				elecOrder.setBasicPayStatus("0");
				elecOrder.setServicePayStatus("0");
				elecOrder.setServiceChargeTotalThird(0.00);
				editGunStatusAndCouponsStatus(elecOrder,pile,bean);
				pileDao.updateByPrimaryKeySelective(pile);
				if(elecOrder.getRealCount()==0){
					elecOrder.setStatus("2");
				}else{
					elecOrder.setStatus("0");
				}
				orderDao.updateByPrimaryKeySelective(elecOrder);
				//分润
				sendServiceMoney(elecOrder.getId(),station.getPersonPhone(),elecOrder.getRealCount(),station.getBasicChargeAmount(),station.getThirdServiceAmount() * station.getServiceChargeAmount());
			}
		}
		
	
	}
	public boolean appentXY(ElecChargeRecord record,ElecOrder order,ElecUser user){
		ElecPile pile = pileDao.findChargeInfoByPileNum2(order.getPileNum());
		ElecFirm firm = firmDao.selectByPrimaryKey(pile.getFirmId());
		String type ="1";
		if(firm.getFirmName().contains("循道")){
			type= "2";
		}else if(firm.getFirmName().contains("科士达")){
			type= "3";
		}
		ElecStation station = pile.getStation();
		Double basicChargeAmount = station.getBasicChargeAmount();
		Double serviceChargeAmount = station.getServiceChargeAmount();
		BigDecimal basicAmount = BigDecimal.valueOf(basicChargeAmount);
		BigDecimal serviceAmount = BigDecimal.ZERO;
		if(null!=station.getPersonType()&& 1==station.getPersonType()&&user.getPhone().equals(station.getPersonPhone())){
			logger.info("桩东充电============");
			serviceAmount=BigDecimal.valueOf(station.getPersonServiceAmount());
		}else{
			serviceAmount= BigDecimal.valueOf(serviceChargeAmount);
		}
		BigDecimal balanceAmount = BigDecimal.valueOf(record.getElecAmount());
		BigDecimal setScale = balanceAmount.divide((basicAmount.add(serviceAmount)),2,BigDecimal.ROUND_DOWN);
		BigDecimal divide = setScale.divide(BigDecimal.valueOf(1.05),2,BigDecimal.ROUND_DOWN);//增加5%的收费
		record.setElecCount(divide.doubleValue());
		record.setRecordStatus(1);
		order.setElecTotalAmount(order.getElecTotalAmount()+record.getElecAmount());
		order.setElecTotalCount(BigDecimal.valueOf(order.getElecTotalCount()).add(divide).doubleValue());
	    int recordFlag = recordDao.insertSelective(record);
	    if(recordFlag>0){
	    	String result = ElecUtil.appentXY(pile.getPileNum(), "4", order.getElecTotalCount(),order.getId(),type,order.getGunNo(),pile.getType() );
	    	if("SUCCESS".equals(result)){
	    		orderDao.updateByPrimaryKeySelective(order);
	    		return true;
			}
	    }
		return false;
	}
	
	
	/**
	 * 订单结束时根据订单传送过来的信息进行返回订单信息
	 * @param orderId
	 * @param pileChargeCounts
	 * @return
	 * @throws ParseException
	 */
	public HashMap countOrderElec(Integer orderId,Double pileChargeCounts) throws ParseException{
   	 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
   	//根据订单ID查询订单
   	ElecOrder order = orderDao.selectByPrimaryKey(orderId);
   	
   	Calendar calendar = Calendar.getInstance();//日历对象
       calendar.setTime(new Date());//设置当前日期
       //小时
       int hour = calendar.get(Calendar.HOUR_OF_DAY);
       //时间
       int min = calendar.get(Calendar.MINUTE);
     //年
       int year = calendar.get(Calendar.YEAR);
     //月
       int month = calendar.get(Calendar.MONTH);
     //日
       int day = calendar.get(Calendar.DAY_OF_MONTH);
     //根据该场站时间段
       List<ElecStationNorm> elecStationNorms = stationNormDao.selectByStationId(order.getPile().getChargeStandardId());
       
       ElecStationNorm norms=null;
       
       int timeSum=0;
       
       for(ElecStationNorm norm:elecStationNorms){
    	   //结束时间
    	   String[] toDates = norm.getToDate().split(":");
    	   //开始时间
    	   String[] fromDates = norm.getFromDate().split(":");
    	   
    	   //开始时间-小时
    	   Integer fromHour = Integer.valueOf(fromDates[0]);
    	   
    	   //结束时间-小时
    	   Integer toHour = Integer.valueOf(toDates[0]);
    	   Integer toMin = Integer.valueOf(toDates[1]);
    	   
    	   //订单结束时间大于时间段开始时间
    	   if(hour>=fromHour){
    		   //订单结束时间等于当前时间段结束时间
    		   if(hour==toHour){
    			   //需要结束时间分钟数大于等于当前结束时间分钟
    			   if(toMin>=min){
    				   norms= norm;
    			   }
    			   //如果结束时间小时小于结束时间小时
    		   }else if(hour<toHour){
    			   norms= norm;
    		   }
    		   }
    	   }
    	   
    	   
       
     //查询当前订单是否有过明细
       Double  orderElecCount= detailMapper.selectCountByOrderId(order.getId());
       //如果有过明细则减去之前度数
       if(orderElecCount!=null && orderElecCount>0){
       	Double endElecCount= pileChargeCounts-orderElecCount;
       	if(endElecCount>0){
       		endElecCount=endElecCount*1.05;
   			ElecOrderDetail elecOrderDetail = new ElecOrderDetail();
   			String time1=year+"-"+month+"-"+day+" "+norms.getFromDate();
   			// 创建订单明细
       		elecOrderDetail.setFromDate(format.parse(time1));
       		elecOrderDetail.setToDate(new Date());
       		elecOrderDetail.setPrice(norms.getBasicChargeAmount()+norms.getServiceChargeAmount());
       		elecOrderDetail.setServiceAmount(norms.getServiceChargeAmount()*endElecCount);
       		elecOrderDetail.setCost(norms.getBasicChargeAmount()*endElecCount);
       		elecOrderDetail.setOrderId(order.getId());
       		elecOrderDetail.setElecCount(endElecCount);
       		elecOrderDetail.setDuration(Double.valueOf(timeSum));
       		detailMapper.insertSelective(elecOrderDetail);
       	}
       }else{
   			double endElecCount=pileChargeCounts*1.05;
  			ElecOrderDetail elecOrderDetail = new ElecOrderDetail();
  			String time1=year+"-"+month+"-"+day+" "+norms.getFromDate();
  			// 创建订单明细
      		elecOrderDetail.setFromDate(format.parse(time1));
      		elecOrderDetail.setToDate(new Date());
      		elecOrderDetail.setPrice(norms.getBasicChargeAmount()+norms.getServiceChargeAmount());
      		elecOrderDetail.setServiceAmount(norms.getServiceChargeAmount()*endElecCount);
      		elecOrderDetail.setCost(norms.getBasicChargeAmount()*endElecCount);
      		elecOrderDetail.setOrderId(order.getId());
      		elecOrderDetail.setElecCount(endElecCount);
      		detailMapper.insertSelective(elecOrderDetail);
      		
       }
       HashMap map= new HashMap();
       Double elecSum=0.0;
       Double amountCostSum=0.0;
       Double amountServiceSum=0.0;
       List<ElecOrderDetail> selectByOrderId = detailMapper.selectByOrderId(orderId);
       for(ElecOrderDetail detail:selectByOrderId){
       	elecSum+=detail.getElecCount();
       	amountCostSum+=detail.getCost();
       	amountServiceSum+=detail.getServiceAmount();
       }
       map.put("elec", elecSum);
       map.put("serivceAmount", amountServiceSum);
       map.put("costAmount", amountCostSum);
       return map;
   }

    /**
     * 给桩东分润
     * @param orderId
     * @param phone
     * @param count
     * @param basicPrice
     * @param serviceMoney
     */
   private void sendServiceMoney(Integer orderId,String phone,Double count,Double basicPrice,Double serviceMoney){
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               JSONObject json = new JSONObject();
               json.element("ordernum", orderId);
               json.element("phone", phone);
               json.element("eleccount", count);
               json.element("elecPrice", basicPrice);
               json.element("money", serviceMoney);
               logger.info(String.format("服务费同步至车位东 请求参数：%s", json.toString()));
               String result = ElecUtil.sendCarPort(json.toString());
               logger.info(String.format("服务费同步至车位东返回结果：%s", result));
           }
       });
       thread.start();
   }

    /**
     * 修改枪状态和优惠券使用状态
     * @param elecOrder
     * @param pile
     * @param bean
     */
   private void editGunStatusAndCouponsStatus(ElecOrder elecOrder,ElecPile pile,ResultXYDF bean){
       //判断有没有使用优惠券
       Integer couponId = elecOrder.getCouponId();
       if(null != couponId && 0 != couponId){
           ElecUserCouponsRel couponsRel = ucrDao.selectByPrimaryKey(couponId);
           ElecCoupons elecCoupons = couponsDao.selectByPrimaryKey(couponsRel.getCouponsId());
           Integer status = elecCoupons.getStatus();
           //充电金额为零时，退还使用的优惠券,如果是首单免费优惠券删除该优惠券
           if(!BigDecimal.ZERO.equals(BigDecimal.valueOf(elecOrder.getRealAmount()))){
               //直减优惠券
               if(1==status){
                   //优惠券抵扣（充电金额大于优惠券金额时用优惠券和余额一起支付，充电金额小于优惠券金额时，直接用优惠券支付，优惠金额多余部分不予退还）
                   if(elecOrder.getRealAmount()>elecCoupons.getAmount()){
                       elecOrder.setRealAmount(elecOrder.getRealAmount()-elecCoupons.getAmount());
                   }else{
                       elecOrder.setRealAmount(0.00);
                   }
                   //打折优惠券
               }else if(2==status){
                   elecOrder.setRealAmount(elecOrder.getRealAmount()*elecCoupons.getAmount());
               }else if(3==status||5==status){
                   elecOrder.setRealAmount(0.00);
               }else if(4==status){
                   if(elecOrder.getRealAmount()>=elecCoupons.getReach()){
                       elecOrder.setRealAmount(elecOrder.getRealAmount()-elecCoupons.getAmount());
                   }else{
                       couponsRel.setStatus(0);
                       elecOrder.setCouponId(0);
                       ucrDao.updateByPrimaryKeySelective(couponsRel);
                   }
               }
           }else{
               if(3==status){
                   elecOrder.setCouponId(0);
                   ucrDao.deleteByPrimaryKey(couponsRel.getId());
               }else{
                   couponsRel.setStatus(0);
                   elecOrder.setCouponId(0);
                   ucrDao.updateByPrimaryKeySelective(couponsRel);
               }
           }

       }
       if(3==pile.getType()||5==pile.getType()){//单枪电桩
           pile.setIsUsed(0);
       }else{//双枪电桩
           if(3==pile.getIsUsed()){
               if(0==bean.getGunNo()){
                   pile.setIsUsed(2);//B枪被占用
               }else if(1==bean.getGunNo()){
                   pile.setIsUsed(1);//A枪被占用
               }
           }else if(1==pile.getIsUsed()||2==pile.getIsUsed()){
               pile.setIsUsed(0);
           }
       }

   }
}
