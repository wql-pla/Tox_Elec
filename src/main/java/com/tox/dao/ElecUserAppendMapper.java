package com.tox.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecUserAppend;
@Mapper
public interface ElecUserAppendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecUserAppend record);

    int insertSelective(ElecUserAppend record);

    ElecUserAppend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecUserAppend record);

    int updateByPrimaryKey(ElecUserAppend record);
    //通过条件查询追加人信息
    List<ElecUserAppend> selectStationAndAppent(@Param("append")ElecUserAppend append);
    //通过条件查询追加人信息
    Integer selectStationAndAppentCount(@Param("append")ElecUserAppend append);
}