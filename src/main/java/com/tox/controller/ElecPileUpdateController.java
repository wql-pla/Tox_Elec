package com.tox.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.tox.bean.ElecFirm;
import com.tox.bean.ElecPile;
import com.tox.bean.PileIpUpdateResult;
import com.tox.bean.PileUpdateResult;
import com.tox.bean.QRCodeParams;
import com.tox.dao.ElecFirmMapper;
import com.tox.dao.ElecPileMapper;
import com.tox.utils.ElecUtil;

import net.sf.json.JSONObject;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping(value = "/pileUpdate")
public class ElecPileUpdateController {
    private static final Logger logger = LoggerFactory.getLogger(ElecPileUpdateController.class);
    
    private static AtomicLong at = new AtomicLong();
	
	@Autowired
	private ElecPileMapper elecPileDao;
	@Autowired
	private ElecFirmMapper elecFirmDao;
	
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	
	/**
	 * 
	 * @param result
	 */
	@RequestMapping(value="/getQrCode")
	public @ResponseBody Map<String, Object> getQrCode(String args){
		Map<String, Object> map = new HashMap<>();
		logger.info(String.format("获取微信码参数：%s", args));
		if("".equals(args)||null==args){
			map.put("code", 1);
			map.put("msg", "获取微信码请求参数不能为空");
			return map;
		}
		JSONObject jsonObject = JSONObject.fromObject(args);
		QRCodeParams bean = (QRCodeParams) JSONObject.toBean(jsonObject, QRCodeParams.class);
		if(null != bean&&bean.getPileNo()!=null&&!bean.getPileNo().equals("")){
			ElecPile pile = elecPileDao.getQrCode(bean.getPileNo());
			if(pile ==null){
				map.put("code", 1);
				map.put("msg", "电桩号不存在");
			}else{
				map.put("code", 0);
				map.put("pileNo", pile.getPileNum());
				map.put("qrCode", pile.getWxCode());
			}
		}else{
			map.put("code", 1);
			map.put("msg","桩号不能为空");
		}
		logger.info("获取微信码返回结果："+map.toString());
		return map;
	}
	/**
	 * 0:升级成功(非 0 为失败) 1:非本机程序 2:升级文件校验不对 3:升级文件不能成功下载 4:其它
	 * @param result
	 */
 	@RequestMapping(value="/updateResult")
 	public @ResponseBody Map<String, Object> updateResult(String result){
 		Map<String, Object> map = new HashMap<>();
 		logger.info(String.format("电桩升级结果信息：%s", result));
 		JSONObject jsonObject = JSONObject.fromObject(result);
 		PileUpdateResult bean = (PileUpdateResult) JSONObject.toBean(jsonObject, PileUpdateResult.class);
 		ElecPile pile = elecPileDao.selectCSByPrimaryKey(bean.getPileNo());
 		if(null ==pile){
 			map.put("result", "success");
 			return map;
 		}
 		if("0".equals(bean.getStatus())){
 			pile.setUpdateStatus("2");
 			pile.setSoftVersion(bean.getSoftVersion());
 			elecPileDao.updateByPrimaryKeySelective(pile);
 		}else{
 			int count = pile.getUpdateCount()==null?0:pile.getUpdateCount();
 			if(count<3){
 				String tradeTypeCode="2";
 				ElecFirm firm = elecFirmDao.selectByPrimaryKey(pile.getFirmId());
 				if(firm.getFirmName().contains("新亚")){
 					tradeTypeCode="1";
 				}
 				updatePiles(bean);
 				count++;
 				pile.setUpdateCount(count);
 			}else{
 				pile.setUpdateStatus("1"+bean.getStatus());
 			}
 			elecPileDao.updateByPrimaryKeySelective(pile);
 		}
 		map.put("result", "success");
		return map;
 	}
 	
 	/**
 	 * 
 	 * @param tradeTypeCode 厂商类型，1新亚，2循道
 	 * @param pileNo 电桩号
 	 * @return
 	 */
 	@RequestMapping(value="/updatePiles",produces="application/json",method=RequestMethod.POST)
 	public Map<String,Object> updatePiles(@RequestBody PileUpdateResult params){
 		Map<String, Object> map = new HashMap<String,Object>();
 		
 		if(null == params.getSoftVersion() || null ==params.getTradeTypeCode()){
 			map.put("msg", "版本号或厂商类型不能为空");
 			return map;
 		}
 		String typeCode = null;
 		String pileNos = "";
	    if("1".equals(params.getTradeTypeCode())){
			typeCode = "新亚";
		}else if("2".equals(params.getTradeTypeCode())){
			typeCode = "循道";
		}else if("3".equals(params.getTradeTypeCode())){
			typeCode = "科士达";
		}else{
			map.put("result", "101");
			map.put("msg", "厂商类型不正确");
			return map;
		}
 		if(null == params.getPileNo()){
 			List<ElecPile> piles = elecPileDao.getPilesByTradeTypeCode(typeCode);
			for (int i = 0; i < piles.size(); i++) {
				pileNos += piles.get(i).getPileNum();
				if(i<(piles.size()-1)){
					pileNos +=",";
				}
			}
			String result = ElecUtil.updatePiles(pileNos, params.getTradeTypeCode(), params.getSoftVersion(),params.getPileType());
			if(!"".equals(result)){
				List<PileUpdateResult> list = JSONArray.parseArray(result, PileUpdateResult.class);
				for (PileUpdateResult pileUpdateResult : list) {
					ElecPile pile = elecPileDao.selectCSByPrimaryKey(pileUpdateResult.getPileNo());
					pile.setSoftVersion(params.getSoftVersion());
					pile.setUpdateStatus(pileUpdateResult.getStatus());
					elecPileDao.updateByPrimaryKeySelective(pile);
				}
				map.put("result", "100");
				map.put("msg", "success");
			}else{
				map.put("msg", "fail");
				map.put("result", "102");
			}
			return map;
 		}else{
 			pileNos = params.getPileNo();
 			String result = ElecUtil.updatePiles(pileNos, params.getTradeTypeCode(), params.getSoftVersion(),params.getPileType());
 			if(!"".equals(result)){
				List<PileUpdateResult> list = JSONArray.parseArray(result, PileUpdateResult.class);
				for (PileUpdateResult pileUpdateResult : list) {
					ElecPile pile = elecPileDao.selectCSByPrimaryKey(pileUpdateResult.getPileNo());
					pile.setSoftVersion(params.getSoftVersion());
					pile.setUpdateStatus(pileUpdateResult.getStatus());
					elecPileDao.updateByPrimaryKeySelective(pile);
				}
				map.put("result", "100");
				map.put("msg", "success");
			}else{
				map.put("msg", "fail");
				map.put("result", "102");
			}
			return map;
 		}
 	}
 	/**
 	 * 修改电桩ip
 	 * @param tradeTypeCode 厂商类型，1新亚，2循道
 	 * @param pileNo 电桩号
 	 * @return
 	 */
 	@RequestMapping(value="/editPilesIp")
 	public Map<String,Object> editPilesIp(String tradeTypeCode,String pileNo,String ip,String port,Integer pileType){
 		Map<String, Object> map = new HashMap<String,Object>();
 		
 		if(null == ip || null ==tradeTypeCode|| null==port){
 			map.put("msg", "ip地址或厂商类型或端口号不能为空");
 			return map;
 		}
 		String typeCode = null;
 		String pileNos = "";
 		if("1".equals(tradeTypeCode)){
 			typeCode = "新亚";
 		}else if("2".equals(tradeTypeCode)){
 			typeCode = "循道";
 		}else if("3".equals(tradeTypeCode)){
 			typeCode = "科士达";
 		}else{
 			map.put("result", "101");
 			map.put("msg", "厂商类型不正确");
 			return map;
 		}
 		if(null == pileNo){
 			List<ElecPile> piles = elecPileDao.getPilesByTradeTypeCode(typeCode);
 			for (int i = 0; i < piles.size(); i++) {
 				pileNos += piles.get(i).getPileNum();
 				if(i<(piles.size()-1)){
 					pileNos +=",";
 				}
 			}
 			String result = ElecUtil.editPilesIp(pileNos, tradeTypeCode,ip,port,pileType);
 			if(!"".equals(result)){
 				List<PileIpUpdateResult> list = JSONArray.parseArray(result, PileIpUpdateResult.class);
 				for (PileIpUpdateResult pileIpUpdateResult : list) {
 					ElecPile pile = elecPileDao.selectCSByPrimaryKey(pileIpUpdateResult.getPileNo());
 					pile.setIpStatus(pileIpUpdateResult.getStatus());
 					pile.setServerIp(ip);
 					pile.setServerPort(port);
 					elecPileDao.updateByPrimaryKeySelective(pile);
 				}
 				map.put("result", "100");
 				map.put("msg", "success");
 			}else{
 				map.put("msg", "fail");
 				map.put("result", "102");
 			}
 			return map;
 		}else{
 			pileNos = pileNo;
 			String result = ElecUtil.editPilesIp(pileNos, tradeTypeCode,ip,port,pileType);
 			if(!"".equals(result)){
 				List<PileIpUpdateResult> list = JSONArray.parseArray(result, PileIpUpdateResult.class);
 				for (PileIpUpdateResult pileIpUpdateResult : list) {
 					ElecPile pile = elecPileDao.selectCSByPrimaryKey(pileIpUpdateResult.getPileNo());
 					pile.setIpStatus(pileIpUpdateResult.getStatus());
 					pile.setServerIp(ip);
 					pile.setServerPort(port);
 					elecPileDao.updateByPrimaryKeySelective(pile);
 				}
 				map.put("result", "100");
 				map.put("msg", "success");
 			}else{
 				map.put("msg", "fail");
 				map.put("result", "102");
 			}
 			return map;
 		}
 	}
 	
 	/**
	 * 0:电桩不在线(0 为失败) 1:修改中 2:成功
	 * @param result
	 */
 	@RequestMapping(value="/ipUpdateResult")
 	public @ResponseBody Map<String, Object> ipUpdateResult(String args){
 		Map<String, Object> map = new HashMap<>();
 		logger.info(String.format("电桩ip修改结果信息：%s", args));
 		JSONObject jsonObject = JSONObject.fromObject(args);
 		PileIpUpdateResult bean = (PileIpUpdateResult) JSONObject.toBean(jsonObject, PileIpUpdateResult.class);
 		ElecPile pile = elecPileDao.selectCSByPrimaryKey(bean.getPileNo());
 		if(null ==pile){
 			map.put("result", "success");
 			return map;
 		}
 		pile.setServerIp(bean.getAddr());
 		pile.setIpStatus(2);
 		pile.setServerPort(bean.getPort());
 		elecPileDao.updateByPrimaryKeySelective(pile);
 		map.put("result", "success");
		return map;
 	}
}
