package com.tox.dao;

import com.tox.bean.ActivityNewMonthInfo;

public interface ActivityNewMonthInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewMonthInfo record);

    int insertSelective(ActivityNewMonthInfo record);

    ActivityNewMonthInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewMonthInfo record);

    int updateByPrimaryKey(ActivityNewMonthInfo record);
}