package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.tox.bean.ElecActivity;

@Service
@Mapper
public interface ElecActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecActivity record);

    int insertSelective(ElecActivity record);
    
    List<ElecActivity> selectActivity(@Param("activity") ElecActivity activity);

   	int selectActivityCount(@Param("activity")ElecActivity activity);

    ElecActivity selectByPrimaryKey(Integer id);
    
    ElecActivity selectByPrimaryKeys(Integer id);

    int updateByPrimaryKeySelective(ElecActivity record);

    int updateByPrimaryKey(ElecActivity record);

	List<ElecActivity> selectActivityMore(@Param("activity")ElecActivity activity);

	int selectActivityMoreCount(@Param("activity")ElecActivity activity);

	ElecActivity selectMoreActivityById(Integer id);

	ElecActivity selectActivityById(Integer id);
}