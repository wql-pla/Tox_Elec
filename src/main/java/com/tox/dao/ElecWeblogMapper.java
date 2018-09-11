package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecWeblog;
@Mapper
public interface ElecWeblogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecWeblog record);

    int insertSelective(ElecWeblog record);

    ElecWeblog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecWeblog record);

    int updateByPrimaryKey(ElecWeblog record);
}