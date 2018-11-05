	package com.tox.controller;

import com.tox.bean.*;
import com.tox.dao.*;
import com.tox.service.ElecActivityService;
import com.tox.service.ElecOrderService;
import com.tox.utils.ElecUtil;
import com.tox.utils.date.dateUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

	@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/order")
@Transactional
public class ElecOrderController {

	private static final Logger logger = LoggerFactory.getLogger(ElecOrderController.class);
	@Autowired
	private ElecOrderMapper orderDao;
//	@Autowired
//	private ElecChargeRecordMapper recordDao;
//	@Autowired
//	private ElecCouponMapper couponDao;
	@Autowired
	private ElecStationMapper stationDao;
	@Autowired
	private ElecOrderService orderService;
	@Autowired
	private ElecPileMapper pileDao;
	@Autowired
	private ElecUserMapper userDao;
	@Autowired
	private ElecRechargeMapper rechargeDao;
	@Autowired
	private ElecFirmMapper firmDao;
	@Autowired
	private ElecUserCouponsRelMapper ucrDao;
//	@Autowired
//	private ElecCouponsMapper couponsDao;
	@Autowired
	private ElecActivityService activityService;
	@Autowired
	private ElecRoleMapper roleDao;
	@Autowired
	private ElecOrderDetailMapper orderDetail;
	@Autowired
	private ElecUserAppendMapper appendDao;
	
//	private static AtomicLong at = new AtomicLong();
//
//	private static Integer i = 1;

	/**
	 * 订单详情 -- 后台正在用
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getOrderDetail")
	public @ResponseBody Map<String,Object> getOrderDetail(@RequestParam Integer id){
		
		Map<String, Object> map = new HashMap<String, Object>();
		//获取订单详情
		ElecOrder detail = orderDao.selectDetailById(id);
		//获取订单时段详情
		List<ElecOrderDetail> detailist = orderDetail.selectByOrderId(id);
		
		map.put("result", "100");
		
		map.put("detail", detail);
		
		map.put("detailist", detailist);
		
		map.put("msg", "订单详情查询成功!");
		
		return map;
		
	}
	
	/**
	 * 根据openid 分页查询用户订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/selectOrdersByOpenId")
	public @ResponseBody Map<String, Object> selectOrdersByOpenIdPages(@RequestBody ElecOrder order) {
		logger.info(String.format("查询参数：%s", order.toString()));
		Map<String, Object> map = new HashMap<String, Object>();

		PageView<ElecOrder> pageView = new PageView<ElecOrder>();

		pageView.setPageSize(order.getPageSize());

		order.setPageNum(order.getPageNum() * order.getPageSize());
		List<ElecOrder> orders = orderDao.selectOrdersByOpenIdPages(order);
		logger.info(String.format("查询结果：%s", orders.toString()));
		int count = orderDao.selectOrdersByOpenIdPagesCount(order);
		logger.info(String.format("总记录数：%s", Integer.valueOf(count).toString()));

		pageView.setList(orders);

		pageView.setTotal(count);
		map.put("data", pageView);
		return map;
	}

	/**
	 * 分页查询所有订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/selectOrdersPages")
	public @ResponseBody Map<String, Object> selectOrdersPages(@RequestBody ElecOrder order,HttpServletRequest req) {
		logger.info(String.format("查询参数：%s", order.toString()));
		String header = req.getHeader("token");
		Map<String, Object> map = new HashMap<String, Object>();
		if("".equals(header)||null ==header){
			map.put("result", -1);
			return map;
		}
		String split = header.split(";")[1];
		ElecRole elecRole = roleDao.selectByPrimaryKey(Integer.valueOf(split));
		String region = elecRole.getRegion();
		if(!region.equals("所有城市")){
			order.setCity(region);
		}
		PageView<ElecOrder> pageView = new PageView<ElecOrder>();

		pageView.setPageSize(order.getPageSize());

		order.setPageNum(order.getPageNum() * order.getPageSize());
		//List<Map<String,Object>> list = orderDao.selectOrdersPages(order);
		List<ElecOrder> list = orderDao.selectOrders(order);
		/*if(null !=order.getId() ){
			List<ElecChargeRecord> records = recordDao.selectByOrderId(order.getId());
			if (null != records && records.size() > 1) {
				list.get(0).put("isAgain", true);
			} else {
				list.get(0).put("isAgain", false);
			}
		}*/
		logger.info(String.format("查询结果：%s", list.toString()));
		//int count = orderDao.selectOrdersPagesCount(order);
		int count = orderDao.selectOrderCount(order);
		logger.info(String.format("总记录数：%s", Integer.valueOf(count).toString()));
		double totalMoney = orderDao.selectSumAmount(order);
		double totalElec = orderDao.selectSumCount(order);
		pageView.setList(list);
		pageView.setTotal(count);
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put("pageView",pageView);
		data.put("sum", totalMoney);
		data.put("elec", totalElec);
		
		map.put("result", 100);
		map.put("data",data);
		map.put("msg","列表查询成功!");
		return map;
	}

	/**
	 * 根据orderid 查询订单详情
	 * 
	 * @param order
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/selectOrderById")
	public @ResponseBody Map<String, Object> selectOrderById(Integer orderId) throws ParseException {
		logger.info(String.format("查询参数：%s", orderId.toString()));
		Map<String, Object> map = new HashMap<String, Object>();

		ElecOrder elecOrder = orderDao.getOrderAndCouponByOrderId(orderId);
		if(elecOrder.getCreateTime()!= null && elecOrder.getEndTime() !=null){
			long seconds = dateUtil.getSeconds(elecOrder.getCreateTime(),elecOrder.getEndTime());
			elecOrder.setTimeDiff(seconds);
		}
		logger.info(String.format("查询结果：%s", elecOrder.toString()));

		map.put("order", elecOrder);
		return map;
	}

	/**
	 * 管理员开启充电接口
	 * 
	 * @param order
	 * @return
	 */
	/*
	@RequestMapping(value = "/adminOpenCharging")
	public Map<String, Object> adminOpenCharging(Integer orderId) {
		logger.info(String.format("要开启充电的订单信息id：%s", orderId.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		ElecOrder order = orderDao.selectByPrimaryKey(orderId);
		
		if (null != order && "2".equals(order.getStatus())) {
			logger.info("开启充电桩=====管理员操作======");
			// 如果开启充电失败，最多会请求2次
			for (int i = 1; i <= 2; i++) {
				logger.info(String.format("开启电桩第：%s次请求", i));
				boolean flag = orderService.openCharging(order,pile);
				// result = ElecUtil.sendPost(pile.getPileNum(), "4",
				// order.getElecTotalCount(), order.getId(),order.getId());
				// logger.info(String.format("充电桩返回结果：%s", result));
				// System.out.println("充电桩返回结果："+result);
				if (flag) {
					map.put("result", true);
					map.put("orderId", order.getId());
					break;
				} else {
					map.put("result", false);
				}
			}

		} else {
			map.put("result", false);
		}
		return map;
	}*/
	/**
	 * 内部人员开启充电接口
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/createOrderByAdmin")
	public Map<String, Object> createOrderByAdmin(String pileNum,String openId,Integer gunNo,String useOpenid) {
		Map<String, Object> map = new HashMap<String, Object>();
		ElecUser selectByOpenId = userDao.selectByOpenId(openId);
		if(selectByOpenId.getType()==1){
		logger.info(String.format("内部人员开启电桩接口====pileNum:%s,openId:%s", pileNum,openId));
		logger.info("======获取电桩信息======");
		ElecPile elecPileInfo = pileDao.findChargeInfoByPileNum(pileNum);
		if(null== elecPileInfo){
			logger.info("获取到的电桩可能没上线");
			map.put("result", "111");
			map.put("msg", "电桩暂不可用");
			return map;
		}
		logger.info(String.format("电桩信息：%s", elecPileInfo.toString()));
		ElecFirm firm = firmDao.selectByPrimaryKey(elecPileInfo.getFirmId());
		String tradeTypeCode ="1";
		if(firm.getFirmName().contains("循道")){
			tradeTypeCode= "2";
		}else if(firm.getFirmName().contains("科士达")){
			tradeTypeCode= "3";
		}
		ElecOrder order = new ElecOrder();
		order.setCreateTime(new Date());
		order.setStatus("2");
		order.setOpenId(openId);
		order.setPileId(elecPileInfo.getId());
		order.setGunNo(gunNo);
		order.setUseOpenid(useOpenid);
		int flag = orderDao.insertSelective(order);
		if(1==flag){
		    logger.info(String.format("开启电桩"));
			String result = ElecUtil.sendPost(elecPileInfo.getPileNum(), gunNo,"1", 0, order.getId(),tradeTypeCode,elecPileInfo.getType());
			if("SUCCESS".equals(result)){
				order.setStatus("1");//开启充电桩 成功
				if(3==elecPileInfo.getType()||5==elecPileInfo.getType()){
					//单枪电桩被占用
					elecPileInfo.setIsUsed(1);
				}else{
					if(0==elecPileInfo.getIsUsed()){
						if(0==order.getGunNo()){
							elecPileInfo.setIsUsed(1);//A枪被占用
						}else if(1==order.getGunNo()){
							elecPileInfo.setIsUsed(2);//B枪被占用
						}
					}else if(1==elecPileInfo.getIsUsed()||2==elecPileInfo.getIsUsed()){
						elecPileInfo.setIsUsed(3);//枪都被占用
					}
				}
//				elecPileInfo.setIsUsed(1);
				pileDao.updateByPrimaryKeySelective(elecPileInfo);
				orderDao.updateByPrimaryKeySelective(order);
				map.put("result", 100);
				map.put("msg", "充电成功");
			}else{
				map.put("result", -100);
				map.put("msg", "充电失败");
			}
		}else{
			map.put("result", -100);
			map.put("msg", "充电失败");
		}
		}else{
			logger.info(String.format("系统异常非内部人员尝试开启电桩接口====phone:%s,openId:%s", selectByOpenId.getPhone(),openId));
			map.put("result", -100);
			map.put("msg", "充电失败");
		}
		return map;
	}

	/**
	 * 新增订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/createOrder")
	public Map<String, Object> createOrder(@RequestBody ElecOrder order) throws ParseException {
		logger.info(String.format("要插入的订单信息：%s", order.toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		order.setCreateTime(new Date());
//		order.setStatus("2");// 开启充电桩失败
		//获取用户钱包
		ElecUser user = userDao.selectByOpenId(order.getOpenId());
		if(null == user){
			logger.info("获取用户信息失败");
			map.put("result", "300");
			map.put("msg", "获取用户信息失败");
			return map;
		}
		Double balance = (user.getBalance()==null ?0:user.getBalance())+(user.getGiveMoney()==null? 0:user.getGiveMoney());
		/*if(balance<5){
			logger.info("余额不足5元，请先充值");
			map.put("result", "200");
			map.put("msg", "余额不足5元，请先充值");
			return map;
		}*/
		ElecOrder elecOrder = orderDao.getOrderByOpenid(order.getOpenId());
		//有正在充电订单时，不能开启新充电订单
		if(null != elecOrder){
			map.put("result", "-100");
			map.put("msg", "开启电桩失败");
			return map;
		}
		ElecPile pile =null;
		//根据电桩编号获取电桩id
		if(order.getPileNum().contains("http")){
			pile = pileDao.findChargeInfoByPileNum(order.getPileNum());
			}else{
				pile = pileDao.findChargeInfoByPileNum2(order.getPileNum());
			}
		if(null ==pile){
			logger.info("===========电桩已停用，获取电桩信息失败==============");
			map.put("result", "-100");
			map.put("msg", "电桩已停用");
			return map;
		}


		orderService.setOrderMoney(pile, user, order, balance);
		int status = orderDao.insertSelective(order);
		if (1 == status) {
			boolean flag = orderService.openCharging(order,pile);
			if(flag){
				Integer couponId = order.getCouponId();
				if(null != couponId){
					//选择优惠券的情况下，开启充电成功，把优惠券置为已使用
					ElecUserCouponsRel userCouponsRel = ucrDao.selectByPrimaryKey(couponId);
					userCouponsRel.setStatus(1);
					ucrDao.updateByPrimaryKeySelective(userCouponsRel);
					
				}else{
					//首单免费
					Integer freeCouponId = orderService.getFreeCoupon(user);
					if(null !=freeCouponId){
						List<ElecOrder> orders = orderDao.getOrderListByOpenid(user.getOpenId());
			    		if(null ==orders || orders.size()<1){
			    			Integer ucrId = orderService.bindFreeOrder(freeCouponId, user.getId());
							order.setCouponId(ucrId);
			    		}
					}
					
				}
				map.put("result", "100");
				map.put("orderId", order.getId());
			}else{
				map.put("result", "-100");
				map.put("msg", "开启电桩失败");
			}
			orderService.updateOrderAndPile(order,pile);
		} else {
			map.put("result", "-100");
			map.put("msg", "开启电桩失败");
		}
		return map;
	}

	/**
	 * 修改订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/editOrder")
	public @ResponseBody Map<String, Object> editOrder(@RequestBody ElecOrder order) {
		logger.info(String.format("修改信息为：%s", order.toString()));
		// 查询订单来源
		ElecOrder elecOrder = orderDao.selectByPrimaryKey(order.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * Double totalAmount = 0.00; Double totalCount = 0.00;
		 * List<ElecChargeRecord> records =
		 * recordDao.selectByOrderId(order.getId()); if(null !=records &&
		 * records.size()>0){ for (ElecChargeRecord record : records) {
		 * totalAmount += record.getElecAmount(); totalCount +=
		 * record.getElecCount(); } }
		 */
		// elecOrder.setElecTotalAmount(totalAmount);
		// elecOrder.setElecTotalCount(totalCount);
		elecOrder.setRealCount(order.getRealCount());
		elecOrder.setEndTime(order.getEndTime());
		elecOrder.setBasicPayStatus("0");
		elecOrder.setServicePayStatus("0");
		elecOrder.setStatus("0");

		// 根据电桩id获取厂商抽成信息
		// ElecFirm elecFirm
		// =firmDao.selectByPrimaryPileId(elecOrder.getPileId());
		// 根据电桩id获取场站信息
		ElecStation elecStation = stationDao.selectByPrimaryPileId(elecOrder.getPileId());

		// 实际基础费用
		elecOrder.setBasicChargeTotal(elecOrder.getRealCount() * elecStation.getBasicChargeAmount());

		// 我方实际服务费
		elecOrder.setServiceChargeTotalSelf(elecOrder.getRealCount() * elecStation.getServiceChargeAmount());
		// 第三方实际服务费
		elecOrder.setServiceChargeTotalThird(0.00);

		int status = orderDao.updateByPrimaryKeySelective(elecOrder);
		if (1 == status) {
			map.put("result", true);

		} else {
			map.put("result", false);
		}
		return map;
	}
	/**
	 * 停止充电
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/stopCharge")
	public @ResponseBody Map<String, Object> stopCharge(Integer orderId,String pileNo) {
		logger.info(String.format("停止充电订单Id：%s,电桩编号：%s", orderId,pileNo));
		// 查询订单来源
		ElecOrder elecOrder = orderDao.selectByPrimaryKey(orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=elecOrder){
		ElecPile elecPile = pileDao.findChargeInfoByPileNum2(pileNo);
		ElecFirm elecFirm = firmDao.selectByPrimaryKey(elecPile.getFirmId());
		Integer tradeTypeCode =1;
		if(elecFirm.getFirmName().contains("循道")){
			tradeTypeCode= 2;
		}else if(elecFirm.getFirmName().contains("科士达")){
			tradeTypeCode= 3;
		}
			String stopCharge = ElecUtil.stopCharge(pileNo, elecOrder.getGunNo(), orderId, tradeTypeCode, elecPile.getType());
			logger.info("停止充电返回结果："+stopCharge);
			map.put("data", stopCharge);
		}else{
			map.put("code", 1001);
			map.put("msg", "订单不存在");
		}
		
		return map;
	}
	/**
	 * 结束订单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/closeOrder")
	public @ResponseBody Map<String, Object> editOrder(Integer orderId) {
		logger.info(String.format("结束订单Id：%s", orderId));
		// 查询订单来源
		ElecOrder elecOrder = orderDao.selectByPrimaryKey(orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=elecOrder){
			if(null!=elecOrder.getRealCount()&&elecOrder.getRealCount()>0){
				ElecPile elecPile = pileDao.selectByPrimaryKey(elecOrder.getPileId());
				elecPile.setAllCount((elecPile.getAllCount()==null?0:elecPile.getAllCount())+elecOrder.getRealCount());
				pileDao.updateByPrimaryKeySelective(elecPile);
			}
			elecOrder.setStatus("0");
			int status = orderDao.updateByPrimaryKeySelective(elecOrder);
			if (1 == status) {
				map.put("result", "1000");
				map.put("msg", "成功");
				
			} else {
				map.put("result", "-1000");
				map.put("msg", "失败");
			}
		}else{
			map.put("result", "1001");
			map.put("msg", "订单不存在");
		}
		
		return map;
	}

	/**
	 * 充电结束调用接口（包含拔枪断电和正常结束） 目前 只提供给 新亚东方
	 * 
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/powerEnd")
	public @ResponseBody Map<String, Object> powerEnd(String args,@RequestBody String kstar) throws Exception{
		Map<String, Object> map = new HashMap<>();
		JSONObject fromObject;
		if(null == args){
			logger.info(String.format("科士达结束充电信息：%s", kstar));
			fromObject = JSONObject.fromObject(kstar);
		}else{
			logger.info(String.format("循道结束充电信息：%s", args));
			fromObject = JSONObject.fromObject(args);
		}
		ResultXYDF bean = (ResultXYDF) JSONObject.toBean(fromObject, ResultXYDF.class);
		if ("".equals(bean.getOrderNo())) {
			logger.info(String.format("订单号：%s,不存在,程序直接结束!!!!!!", bean.getOrderNo()));
			map.put("result", "success");
			return map;
		}
			ElecOrder elecOrder = orderDao.selectByPrimaryKey(Integer.valueOf(bean.getOrderNo()));
			if (null != elecOrder) {
				
				ElecUser user = userDao.selectByOpenId(elecOrder.getOpenId());
				if(null ==elecOrder.getPileNum()){
					throw new Exception("订单："+elecOrder.getId()+"对应桩号不存在！！！！！！！");
				}
				ElecPile pile = pileDao.selectCSByPrimaryKey(elecOrder.getPileNum());
				ElecStation station = pile.getStation();
				Double basicAmount = station.getBasicChargeAmount();
				Double serviceAmount=0D;
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
					logger.info("桩东结束充电============");
					serviceAmount=station.getPersonServiceAmount();
					basicAmount=station.getPersonBasicChargeAmount();
				}else{
					serviceAmount= station.getServiceChargeAmount();
				}
				Double totalAmout = basicAmount+serviceAmount;
				//上报的充电电量大于上次的电量才会修改充电信息，否则不修改
				orderService.endOrder(elecOrder,user, pile, station, bean, basicAmount, serviceAmount, totalAmout,phones);
				if(user.getType()==1){
					map.put("result", "success");
					return map;
				}else if(null ==elecOrder.getElecTotalAmount()||elecOrder.getElecTotalAmount()==0){
					map.put("result", "success");
					return map;
				}
				List<ElecRecharge> elecRecharges = rechargeDao.getElecRechargByOrderId(elecOrder.getId());
				//只允许有一次退款
				if(elecRecharges ==null || elecRecharges.size()<1){
					//实充 退款
					if(!bean.getEndReason().equals("99")){
						ElecRecharge recharge = new ElecRecharge();
						recharge.setOrderId(elecOrder.getId());
						recharge.setType(2);
						recharge.setRechargeDate(new Date());
						recharge.setRechargeMoney(elecOrder.getElecTotalAmount()-elecOrder.getRealAmount());
						recharge.setUserId(user.getId());
						if(user.getBalance()>=elecOrder.getRealAmount()){
							user.setBalance(user.getBalance()-elecOrder.getRealAmount());
						}else if(user.getBalance()+user.getGiveMoney()-elecOrder.getRealAmount()>=0){
							user.setGiveMoney(user.getBalance()+user.getGiveMoney()-elecOrder.getRealAmount());
							user.setBalance(0.00);
						}else{
							user.setGiveMoney(0.00);
							user.setBalance(user.getBalance()+user.getGiveMoney()-elecOrder.getRealAmount());
						}
						userDao.updateByPrimaryKeySelective(user);
						rechargeDao.insertSelective(recharge);
						//有充电订单送优惠活动
						List<ElecOrder> list = orderDao.getOverOrderListByOpenid(user.getOpenId());
						if(list.size()==1){
							activityService.giveOrderActivity(user);
						}
					}
				}
				
			}
			map.put("result", "success");
			return map;

	}

	/**
	 * 根据openid获取当前用户充电订单
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/getOrderByOpenid")
	public @ResponseBody Map<String, Object> getOrderByOpenid(String openid) {
		Map<String, Object> map = new HashMap<>();
		ElecOrder elecOrder = orderDao.getOrderByOpenid(openid);
		System.out.println(1232);
		if (elecOrder != null) {
			map.put("result", "100");// 有正在充电的订单
			map.put("data", elecOrder);
		} else {
			map.put("result", "101");// 当前用户没有充电订单
		}
		return map;
	}

	/**
	 * 兑换优惠券
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	/*@RequestMapping(value = "/exchangeCoupon")
	public Map<String, Object> exchangeCoupon(Integer orderId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("=======调用兑换优惠券接口======");
		ElecOrder elecOrder = orderDao.selectByPrimaryKey(orderId);
		if (elecOrder != null && null != elecOrder.getCouponId()) {
			System.out.println("要兑换优惠券的订单信息===" + elecOrder.toString());
			ElecCoupon elecCoupon = couponDao.selectByPrimaryKey(elecOrder.getCouponId());
			if (null != elecCoupon) {
				System.out.println("订单关联的兑换码信息===" + elecCoupon.toString());
				elecCoupon.setStatus("2");
				elecOrder.setCouponStatus("2");
				orderDao.updateByPrimaryKeySelective(elecOrder);
				couponDao.updateByPrimaryKeySelective(elecCoupon);
				System.out.println("兑换成功");
			}
		}
		map.put("result", "100");// 兑换成功，无论这个方法成功或失败，以领航员兑换接口为准
		return map;
	}*/

}
