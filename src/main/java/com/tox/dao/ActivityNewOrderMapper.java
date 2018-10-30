package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ActivityNewOrder;
@Mapper
public interface ActivityNewOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewOrder record);

    int insertSelective(ActivityNewOrder record);

    ActivityNewOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewOrder record);

    int updateByPrimaryKey(ActivityNewOrder record);
    
    List<ActivityNewOrder> findNewOrder(ActivityNewOrder record);
}