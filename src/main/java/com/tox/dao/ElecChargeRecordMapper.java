package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecChargeRecord;
@Mapper
public interface ElecChargeRecordMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ElecChargeRecord record);

    int insertSelective(ElecChargeRecord record);

    ElecChargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecChargeRecord record);

    int updateByPrimaryKey(ElecChargeRecord record);
    
    List<ElecChargeRecord> selectByOrderId(@Param("orderId") Integer orderId);
    
}