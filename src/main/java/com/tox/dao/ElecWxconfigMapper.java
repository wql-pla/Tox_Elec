package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.tox.bean.ElecWxconfig;

@Mapper
@Service
public interface ElecWxconfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecWxconfig record);

    int insertSelective(ElecWxconfig record);

    ElecWxconfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecWxconfig record);

    int updateByPrimaryKeyWithBLOBs(ElecWxconfig record);

    int updateByPrimaryKey(ElecWxconfig record);
    
    ElecWxconfig selectKey(String key);
}