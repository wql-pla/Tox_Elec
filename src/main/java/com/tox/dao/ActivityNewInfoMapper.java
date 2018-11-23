package com.tox.dao;

import com.tox.bean.ActivityNewInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface ActivityNewInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewInfo record);

    int insertSelective(ActivityNewInfo record);

    ActivityNewInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewInfo record);

    int updateByPrimaryKey(ActivityNewInfo record);

    List<ActivityNewInfo> selectActivitys(ActivityNewInfo activity);

    ActivityNewInfo selectByPrimaryCode(String activityCode);
}