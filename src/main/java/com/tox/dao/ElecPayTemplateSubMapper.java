package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPayTemplateSub;
@Mapper
public interface ElecPayTemplateSubMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPayTemplateSub record);

    int insertSelective(ElecPayTemplateSub record);

    ElecPayTemplateSub selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecPayTemplateSub record);

    int updateByPrimaryKey(ElecPayTemplateSub record);

	List<ElecPayTemplateSub> getPayTemplateSubsByMasterId(@Param("masterId")Integer id);
}