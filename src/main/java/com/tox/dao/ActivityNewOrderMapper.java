package com.tox.dao;

import com.tox.bean.ActivityNewOrder;

public interface ActivityNewOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewOrder record);

    int insertSelective(ActivityNewOrder record);

    ActivityNewOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewOrder record);

    int updateByPrimaryKey(ActivityNewOrder record);
}