package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ActivityNewUser;
@Mapper
public interface ActivityNewUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewUser record);

    int insertSelective(ActivityNewUser record);

    ActivityNewUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewUser record);

    int updateByPrimaryKey(ActivityNewUser record);
    
    ActivityNewUser selectByPhone(ActivityNewUser record);
}