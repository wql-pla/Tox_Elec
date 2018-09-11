package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecOrganization;
@Mapper
public interface ElecOrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecOrganization record);

    int insertSelective(ElecOrganization record);

    ElecOrganization selectByPrimaryKey(Integer id);
    
    List<ElecOrganization> selectOrganization(@Param("organization") ElecOrganization organization);

   	int selectOrganizationCount(@Param("organization")ElecOrganization organization);

    int updateByPrimaryKeySelective(ElecOrganization record);

    int updateByPrimaryKey(ElecOrganization record);
}