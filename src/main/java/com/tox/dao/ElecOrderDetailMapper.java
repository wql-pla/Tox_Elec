package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecOrderDetail;

@Mapper
public interface ElecOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecOrderDetail record);

    int insertSelective(ElecOrderDetail record);

    ElecOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecOrderDetail record);

    int updateByPrimaryKey(ElecOrderDetail record);
    
    //获取订单时段列表
    List<ElecOrderDetail> selectByOrderId(Integer orderId);
    
    Double selectCountByOrderId(Integer orderId);
    
    List<ElecOrderDetail> selectPageDetails(Integer id);
}