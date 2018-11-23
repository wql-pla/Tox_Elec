package com.tox.dao;

import com.tox.bean.ActivityNewMonthInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ActivityNewMonthInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewMonthInfo record);

    int insertSelective(ActivityNewMonthInfo record);

    ActivityNewMonthInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewMonthInfo record);

    int updateByPrimaryKey(ActivityNewMonthInfo record);

    List<ActivityNewMonthInfo> selectMonthInfos(ActivityNewMonthInfo monthInfo);

    int selectMonthInfosCount(ActivityNewMonthInfo monthInfo);
}