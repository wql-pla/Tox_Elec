package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecActivity2;
@Mapper
public interface ElecActivity2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecActivity2 record);

    int insertSelective(ElecActivity2 record);

    ElecActivity2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecActivity2 record);

    int updateByPrimaryKey(ElecActivity2 record);

	List<ElecActivity2> getEnableActivity();

	List<ElecActivity2> selectActivitys(ElecActivity2 activity);

	int selectActivitysCount(ElecActivity2 activity);

	List<ElecActivity2> getActivityByTypeAndRule(ElecActivity2 activity);
}