package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecGroupCounponsZ;
@Mapper
public interface ElecGroupCounponsZMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecGroupCounponsZ record);

    int insertSelective(ElecGroupCounponsZ record);

    ElecGroupCounponsZ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecGroupCounponsZ record);

    int updateByPrimaryKey(ElecGroupCounponsZ record);

	List<ElecGroupCounponsZ> selectByCounponsFId(@Param("counponsFId")Integer counponsFId);
}