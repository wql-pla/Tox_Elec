package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecPriceRule;
@Mapper
public interface ElecPriceRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecPriceRule record);

    int insertSelective(ElecPriceRule record);

    ElecPriceRule selectByPrimaryKey(Integer id);
    
    //根据场站id获取价格规则列表
    List<ElecPriceRule> selectListByPrimaryKey(@Param("infoVo")ElecPriceRule record);

    int updateByPrimaryKeySelective(ElecPriceRule record);

    int updateByPrimaryKey(ElecPriceRule record);

	List<ElecPriceRule> selectByStationId(@Param("id")Integer id);
	
	//删除规则
	int deleteByPrimary(@Param("prInfo")ElecPriceRule record);
}