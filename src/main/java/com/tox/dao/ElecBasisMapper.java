package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecBasis;
import com.tox.bean.ElecBasisRoleRel;

@Mapper
public interface ElecBasisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecBasis record);

    int insertSelective(ElecBasis record);

    ElecBasis selectByPrimaryKey(Integer id);
    
    List<ElecBasis> selectBasisOne(@Param("basis") ElecBasis basis);
    
    List<ElecBasis> selectBasisAll(@Param("basis") ElecBasis basis);
    
    List<ElecBasis> selectBasisByRoleIds(@Param("brr") ElecBasisRoleRel basisRoleRel);

    int updateByPrimaryKeySelective(ElecBasis record);

    int updateByPrimaryKey(ElecBasis record);
}