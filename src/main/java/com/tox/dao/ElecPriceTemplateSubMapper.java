package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPriceTemplateSub;
@Mapper
public interface ElecPriceTemplateSubMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPriceTemplateSub record);

    int insertSelective(ElecPriceTemplateSub record);

    ElecPriceTemplateSub selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecPriceTemplateSub record);

    int updateByPrimaryKey(ElecPriceTemplateSub record);

	List<ElecPriceTemplateSub> getPriceTemplateSubByMasterId(@Param("masterId")Integer id);
}