package com.tox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tox.bean.BaiduStationParams;
import com.tox.bean.ElecPile;
import com.tox.bean.ElecStation;
import com.tox.bean.EquipmentInfo;
import com.tox.bean.StationInfo;
import com.tox.dao.ElecPileMapper;
import com.tox.dao.ElecStationMapper;
import com.tox.utils.date.dateUtil;
@Transactional
@Service
public class BaiDuService {
	@Autowired
	private ElecStationMapper stationDao;
	@Autowired
	private ElecPileMapper pileDao;

	public Map<String,Object> queryStationsInfo(BaiduStationParams param){
		HashMap<String, Object> map = new HashMap<>();
		param.setPageNo((param.getPageNo()-1)*param.getPageSize());
		List<ElecStation> stations= stationDao.queryStationsInfo(param);
		int count= stationDao.queryStationsInfoCount(param);
		//得到一共多少页
		map.put("itemSize",count);
		count = (int)Math.ceil(count/param.getPageSize());
		map.put("stations", stations);
		map.put("count", count);
		return map;
	}

	public List<ElecPile> queryEquipmentsByStationId(Integer stationId) {
		List<ElecPile> piles = pileDao.selectPilesByStationId(stationId);
		return piles;
	}

	public StationInfo toStationInfo(ElecStation elecStation) {
		StationInfo stationInfo = new StationInfo();
		String coord = elecStation.getCoord();
		stationInfo.setStationId(elecStation.getId().toString());
		stationInfo.setStationName(elecStation.getStationName());
		stationInfo.setAddress(elecStation.getAddress());
		stationInfo.setStationTel(elecStation.getPersonPhone());
		stationInfo.setStationType(50);
		stationInfo.setStationStatus(elecStation.getStatus());
		stationInfo.setStationLat(Float.valueOf(coord.split(",")[0]));
		stationInfo.setStationLng(Float.valueOf(coord.split(",")[1]));
		return stationInfo;
	}

	public EquipmentInfo toEquipmentInfo(ElecPile elecPile,String coord) {
		EquipmentInfo equipmentInfo = new EquipmentInfo();
		equipmentInfo.setEquipmentId(elecPile.getId().toString());
		equipmentInfo.setManufacturerName("");
		switch(elecPile.getType().intValue()) {
		case 1:equipmentInfo.setEquipmentType(2);
		case 2:equipmentInfo.setEquipmentType(1);
		}
		equipmentInfo.setProductionDate(dateUtil.getStrYmd(elecPile.getCreateTime()));
		equipmentInfo.setEquipmentLat(Float.valueOf(coord.split(",")[0]));
		equipmentInfo.setEquipmentLng(Float.valueOf(coord.split(",")[1]));
		return equipmentInfo;
	}

}
