package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecFirm;
@Mapper
public interface ElecFirmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecFirm record);

    int insertSelective(ElecFirm record);

    ElecFirm selectByPrimaryKey(Integer id);
    
    //根据订单的id获取厂商信息
    ElecFirm selectByPrimaryPileId(Integer pileid);

    int updateByPrimaryKeySelective(ElecFirm record);

    int updateByPrimaryKey(ElecFirm record);

	List<ElecFirm> selectFirmsPages(@Param("firm") ElecFirm firm);

	int selectFirmsPagesPagesCount(@Param("firm") ElecFirm firm);
	
	List<ElecFirm> selectFirmName();
}