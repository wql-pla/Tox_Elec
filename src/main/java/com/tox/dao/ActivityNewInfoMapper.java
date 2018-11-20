package com.tox.dao;

import com.tox.bean.ActivityNewInfo;

public interface ActivityNewInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewInfo record);

    int insertSelective(ActivityNewInfo record);

    ActivityNewInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewInfo record);

    int updateByPrimaryKey(ActivityNewInfo record);
}