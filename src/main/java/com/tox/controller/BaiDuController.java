package com.tox.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tox.bean.BaiduStationParams;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecStation;
import com.tox.bean.EquipmentInfo;
import com.tox.bean.StationInfo;
import com.tox.bean.TookenParam;
import com.tox.service.BaiDuService;
import com.tox.utils.AESUtil;
import com.tox.utils.HMACMD5Util;
import com.tox.utils.MD5Util;
import com.tox.utils.date.dateUtil;

@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@Transactional
@RequestMapping(value="/toxStations")
public class BaiDuController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaiDuController.class);
	@Autowired
	private BaiDuService baiduService;
	
	//JWT秘钥
	public static final String SECRET= "ToxSercet";
	//组织机构代码
	private static final String OpeatorId = "800273465";
	//运营商秘钥
	private static final String OperatorSecret = "TOXSercet";
	
	@RequestMapping(value="query_token",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Map<String,Object> queryToken(@RequestBody TookenParam param){
		Map<String,Object> map = new HashMap<String,Object>();
		if(param != null){
			if(param.getOperatorID() == null || !param.getOperatorID().equals(OpeatorId)){
				map.put("OpeatorID", OpeatorId);
				map.put("SuccStat", 1);
				map.put("AccessToken", "");
				map.put("TokenAvailableTime", 7200);
				map.put("FailReason", 1); //无此运营商
				return map;
			}else if(param.getOperatorSecret() == null || !param.getOperatorSecret().equals(OperatorSecret)){
				map.put("OpeatorID", OpeatorId);
				map.put("SuccStat", 1);
				map.put("AccessToken", "");
				map.put("TokenAvailableTime", 7200);
				map.put("FailReason", 2); //运营商秘钥错误
				return map;
			}else{
				Date now = new Date();
				Calendar nowTime = Calendar.getInstance();
				//设置token失效时间两个小时
				nowTime.add(Calendar.HOUR,2);
				//nowTime.add(Calendar.MINUTE,1);
				Date expiresDate = nowTime.getTime();
				Map<String,Object> header = new HashMap<String,Object>();
				header.put("alg", "HS256");  //加密方式
				header.put("typ", "JWT");
				String token = JWT.create()
						.withHeader(header)                  						   //JWT 第一部分 header
						.withClaim("OpeatorID", param.getOperatorID())                 //JWT 第二部分 playload 负载数据
						.withClaim("OperatorSecret",param.getOperatorSecret())
						.withExpiresAt(expiresDate)									   //JWT 设置过期时间2小时
						.withIssuedAt(now)  										   //JWT 设置签发时间
						.sign(Algorithm.HMAC256(SECRET));							   //JWT HMAC256加密签名
				System.out.println(token);
				map.put("OpeatorID", OpeatorId);
				map.put("SuccStat", 0);
				map.put("AccessToken", token);
				map.put("TokenAvailableTime", 7200);
				map.put("FailReason", 0); 
				return map;
			}
		}else {
			map.put("OpeatorID", OpeatorId);
			map.put("SuccStat", 1);
			map.put("AccessToken", "");
			map.put("TokenAvailableTime", 7200);
			map.put("FailReason", 3);                                                     //参数为空
			return map;
		}
		
	}
	
	
	//百度地图查询场站接口
	@RequestMapping(value="/query_stations_info")
	public @ResponseBody Map<String,Object> queryStationsInfo(HttpServletRequest request,@RequestBody BaiduStationParams param) throws Exception{
		logger.info(String.format("百度地图查询场站参数：%s", param.toString()));
		Map<String,Object> map = new HashMap<String,Object>();
		//JWT 认证
		if(param.getToken() != null) {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			DecodedJWT jwt = null;
			try{
				jwt = verifier.verify(param.getToken());
				if(param.getcKey() != null && param.getIv() != null &&param.getSignKey() != null){
				if(param.getcKey().length() == 16 && param.getIv().length() == 16 && param.getSignKey().length() == 16){
					Map<String,Object> res = new HashMap<String,Object>();
					BaiduStationParams result = new BaiduStationParams();
					result.setPageNo(param.getPageNo());
					result.setPageSize(param.getPageSize());
					List<StationInfo> stationInfos = new ArrayList<>();
					Map<String, Object> stationsInfo = baiduService.queryStationsInfo(param);
					List<ElecStation> stations = (List<ElecStation>) stationsInfo.get("stations");
					int count = (int) stationsInfo.get("count");
					result.setItemSize((int)stationsInfo.get("itemSize"));
					result.setPageCount(count);
					if(stations.size()>0){
						for (ElecStation elecStation : stations) {
							StationInfo stationInfo = baiduService.toStationInfo(elecStation);
							List<ElecPile> piles = baiduService.queryEquipmentsByStationId(elecStation.getId());
							List<EquipmentInfo> equipmentInfos = new ArrayList<>();
							for (ElecPile elecPile : piles) {
								EquipmentInfo equipmentInfo = baiduService.toEquipmentInfo(elecPile,elecStation.getCoord());
								equipmentInfos.add(equipmentInfo);
							}
							EquipmentInfo[] arry = new EquipmentInfo[equipmentInfos.size()];
							EquipmentInfo[] array = equipmentInfos.toArray(arry);
							stationInfo.setEquipmentInfos(array);
							stationInfos.add(stationInfo);
						}
						StationInfo[] stationArray = new StationInfo[stationInfos.size()];
						StationInfo[] array = stationInfos.toArray(stationArray);
						result.setStationInfos(array);
					}
					/*String inputStr = OpeatorId +  JSONObject.toJSON(result).toString() + ;
					byte[] inputData = inputStr.getBytes();
					String signKey = param.getSignKey();*/
					res.put("OperatorID", OpeatorId);
					res.put("Data", JSONObject.toJSON(result));
					res.put("TimeStamp", dateUtil.getStrBd(new Date()));
					res.put("Seq", "0001");
					res.put("Sig", "");
					//res.put("Sig", MD5Util.encode(OpeatorId, JSONObject.toJSON(result).toString(), dateUtil.getStrBd(new Date()), "0001", param.getSignKey()));
					String AESResult = AESUtil.Encrypt(JSONObject.toJSON(res).toString(), param.getcKey(), param.getIv());
					//System.out.println(AESUtil.Decrypt(AESResult,param.getcKey(), param.getIv()));
					map.put("RET", "0");
					map.put("MSG", "请求成功!");
					map.put("DATA", JSONObject.toJSON(result));
					return map;
				}else {
					map.put("RET", "4003");
					map.put("MSG", "POST参数不合法 ，缺少必需的实例：OperatorID,sig,TimeStamp,Data,Seq五个参数!");
					map.put("DATA", null);
					return map;
				}
			}else {
				map.put("RET", "4003");
				map.put("MSG", "POST参数不合法 ，缺少必需的实例：OperatorID,sig,TimeStamp,Data,Seq五个参数!");
				map.put("DATA", null);
				return map;
			}
			}catch(Exception e) {
				//token过期或失效
				map.put("RET", "4002");
				map.put("MSG", "Token错误!");
				map.put("DATA", null);
				return map;
			}
		}else {
			//缺少token
			
			map.put("RET", "4004");
			map.put("MSG", "请求的业务参数不合法!");
			map.put("DATA", null);
			return map;
		}
	}

}