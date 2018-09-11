package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecHoster;
@Mapper
public interface ElecHosterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecHoster record);

    int insertSelective(ElecHoster record);

    ElecHoster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecHoster record);

    int updateByPrimaryKey(ElecHoster record);

	ElecHoster getHosterByPhoneAndType(ElecHoster record);
}