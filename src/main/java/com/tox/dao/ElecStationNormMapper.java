package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecStationNorm;

@Mapper
public interface ElecStationNormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecStationNorm record);

    int insertSelective(ElecStationNorm record);

    ElecStationNorm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecStationNorm record);

    int updateByPrimaryKey(ElecStationNorm record);
    
    //通过场站外键删除场站电价信息
    int deleteByStationId(Integer stationId);
    
    //通过场站外键查询电价详情
    List<ElecStationNorm> selectByStationId(Integer stationId);
    //根据结束时间进行查询
    List<ElecStationNorm> selectByEndTime(String toDate);
    
    ElecStationNorm selectByEndTimeAndId(@Param("toDate")String toDate,@Param("stationId")Integer stationId);

	List<ElecStationNorm> selectByStationIdandType(@Param("stationId")Integer stationId, @Param("type")Integer type);

	List<ElecStationNorm> selectMaxAmountByStationIdandType(@Param("stationId")Integer stationId, @Param("type")Integer type);
}