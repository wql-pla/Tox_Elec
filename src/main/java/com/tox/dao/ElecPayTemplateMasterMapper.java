package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPayTemplateMaster;
@Mapper
public interface ElecPayTemplateMasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPayTemplateMaster record);

    int insertSelective(ElecPayTemplateMaster record);

    ElecPayTemplateMaster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecPayTemplateMaster record);

    int updateByPrimaryKey(ElecPayTemplateMaster record);
    //获取充值模板列表
	List<ElecPayTemplateMaster> getPayTemplates(@Param("master")ElecPayTemplateMaster master);

	int getPayTemplatesCount(@Param("master")ElecPayTemplateMaster master);

	ElecPayTemplateMaster getPayTemplateUsed();
}