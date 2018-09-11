package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecBasisRoleRel;
@Mapper
public interface ElecBasisRoleRelMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByRoleid(Integer id);
    
    int insert(ElecBasisRoleRel record);

    int insertSelective(ElecBasisRoleRel record);

    ElecBasisRoleRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecBasisRoleRel record);

    int updateByPrimaryKey(ElecBasisRoleRel record);
}