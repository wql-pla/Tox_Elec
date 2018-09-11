package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecWxevent;

@Mapper
public interface ElecWxeventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecWxevent record);

    int insertSelective(ElecWxevent record);

    ElecWxevent selectByPrimaryKey(Integer id);
    
	List<ElecWxevent> getChannlNum();

    int updateByPrimaryKeySelective(ElecWxevent record);

    int updateByPrimaryKey(ElecWxevent record);
}