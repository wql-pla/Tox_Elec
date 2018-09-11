package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecRecharge;
import com.tox.bean.ElecZJ;

@Mapper
public interface ElecRechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecRecharge record);

    int insertSelective(ElecRecharge record);

    ElecRecharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecRecharge record);

    int updateByPrimaryKey(ElecRecharge record);
    
    List<ElecRecharge> rechargeList(@Param("id")Integer id);
    
    List<ElecRecharge> recharges(ElecRecharge record);
    
    Integer rechargesCount(ElecRecharge record);
    
    //根据支付单号查询详情
    List<ElecRecharge> selectByPayIDE(String payIde);
    
    //根据支付订单号修改充值记录
    int updateByPayIDESelective(ElecRecharge record);
    
    List<ElecZJ> details(ElecZJ record);
    
    int detailsCount(ElecZJ record);
    
    Double sumRecharge(ElecRecharge record);

	List<ElecRecharge> getElecRechargByOrderId(@Param("orderId")Integer orderId);

	List<ElecRecharge> getRechargesByUserId(@Param("userId")Integer userId);

}