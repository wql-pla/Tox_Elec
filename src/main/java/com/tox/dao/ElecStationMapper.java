package com.tox.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.BaiduStationParams;
import com.tox.bean.DirectStation;
import com.tox.bean.ElecStation;
import com.tox.bean.StationInfo;
@Mapper
public interface ElecStationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecStation record);

    int insertSelective(ElecStation record);

    ElecStation selectByPrimaryKey(Integer id);
    
    DirectStation selectDirectStationByPrimaryKey(Integer id);
    
    //根据电桩id获取场站信息
    ElecStation selectByPrimaryPileId(Integer pileid);
    
    int updateByPrimaryKeySelective(ElecStation record);

    int updateByPrimaryKey(ElecStation record);

	List<ElecStation> selectStations(@Param("station") ElecStation station);

	int selectStationsCount(@Param("station")ElecStation station);

	Double selectDS(ElecStation record);
	
	Integer updateByIds(@Param("status")Integer status,@Param("array")Integer...id);
	
	List<ElecStation> selectStationNames();

	List<ElecStation> selectAllStations();

	List<ElecStation> selectStationsAndPilesNum(@Param("station") ElecStation station);

	List<Map<String, Object>> selectStationCity();

	List<ElecStation> queryStationsInfo(@Param("param")BaiduStationParams param);

	Integer queryStationsInfoCount(@Param("param")BaiduStationParams param);
	
	int insertDirectStation(DirectStation directStation);
	
	int updateByPrimaryKeyDirectStation(DirectStation directStation);
	
	List<Map<String,Object>> selectCityAndRegion();
	
	List<DirectStation> elecPriceMap(DirectStation directStation);
	
	Map<String,Object> minAndMaxPrice(DirectStation directStation);
	
	DirectStation priceMapDetail(@Param("id")Integer id);

	int selectPileNumById(@Param("id")Integer id, @Param("type")Integer type);
	
}