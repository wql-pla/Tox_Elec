package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPayTemplateMaster;
import com.tox.bean.ElecPriceTemplateMaster;
@Mapper
public interface ElecPriceTemplateMasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPriceTemplateMaster record);

    int insertSelective(ElecPriceTemplateMaster record);

    ElecPriceTemplateMaster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecPriceTemplateMaster record);

    int updateByPrimaryKey(ElecPriceTemplateMaster record);
    
    //获取价格模板列表
	List<ElecPriceTemplateMaster> getPriceTemplates(@Param("master")ElecPriceTemplateMaster master);

	List<ElecPriceTemplateMaster> getPriceTemplatesPages(@Param("master")ElecPriceTemplateMaster master);

	int getPriceTemplatesPagesCount(@Param("master")ElecPriceTemplateMaster master);

	ElecPriceTemplateMaster getPriceTemplateUsed();
}