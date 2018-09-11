package com.tox.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPile;
@Mapper
public interface ElecPileMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPile record);

    int insertSelective(ElecPile record);

    ElecPile selectByPrimaryKey(Integer id);
    
    //根据电桩编号查询场站详情
    ElecPile selectCSByPrimaryKey(@Param("pileNum") String pileNum);
    
    int updateByPrimaryKeySelective(ElecPile record);

    int updateByPrimaryKey(ElecPile record);

	List<ElecPile> selectPiles(@Param("pile")ElecPile elecPile);
	
	Integer selectPilesCount(@Param("pile")ElecPile elecPile);

	int getPileCountByStationId(@Param("stationId")Integer stationId);
	
	int getPileCount(@Param("stationId")Integer stationId);
	
	List<ElecPile> selectPilePage(ElecPile record);
	
	Integer selectPilePageCount(ElecPile record);
	
	Double selectSumCount(ElecPile record);
	
	ElecPile selectDetailById(@Param("id")Integer id);

	ElecPile findChargeInfoByPileNum(@Param("pileNum")String pileNum);
	
	Integer updateByIds(@Param("status")Integer status,@Param("array")Integer...id);
	
	Double SUMByIDS(@Param("list")List<Integer> id);
	
	ElecPile pilePageDetail(ElecPile record);

	List<ElecPile> selectPilePages(ElecPile elecPile);
	
	List<ElecPile> selectFaBu(ElecPile elecPile);
	
	int selectFaBuCount(ElecPile elecPile);

	ElecPile selectPileByWxCode(@Param("wxCode")String wxCode);

	ElecPile findChargeInfoByPileNum2(@Param("pileNum")String pileNum);

	ElecPile getPileInfoByPileNumOrWxCode(@Param("code")String code);

	List<ElecPile> getPilesByTradeTypeCode(@Param("typeCode")String typeCode);

	List<ElecPile> selectPileList(ElecPile elecPile);

	double selectPileAllCount(ElecPile elecPile);

	int selectPileListCount(ElecPile elecPile);
	
	List<ElecPile> selectPileByOnline();
	
	List<ElecPile> selectPileByOnline1();

	int getFreePileCount(@Param("stationId")Integer stationId,@Param("type")Integer type);

	int getPileCountByType(@Param("stationId")Integer stationId,@Param("type")Integer type);

	List<ElecPile> selectPilesByStationId(@Param("stationId")Integer stationId);

	ElecPile getQrCode(@Param("pileNo")String pileNo);
	
}