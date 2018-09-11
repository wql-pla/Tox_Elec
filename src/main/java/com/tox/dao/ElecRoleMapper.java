package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecRole;
@Mapper
public interface ElecRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecRole record);

    int insertSelective(ElecRole record);

    ElecRole selectByPrimaryKey(Integer id);
    
    List<ElecRole> selectrole(@Param("role") ElecRole record);

   	int selectroleCount(@Param("role")ElecRole record);

    int updateByPrimaryKeySelective(ElecRole record);

    int updateByPrimaryKey(ElecRole record);
}