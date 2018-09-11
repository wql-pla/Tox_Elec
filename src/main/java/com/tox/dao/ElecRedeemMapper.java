package com.tox.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tox.bean.ElecRedeem;

@Component
@Mapper
public interface ElecRedeemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecRedeem record);

    int insertSelective(ElecRedeem record);

    ElecRedeem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecRedeem record);

    int updateByPrimaryKey(ElecRedeem record);
    //根据兑换码获取
	ElecRedeem getRedeemByCode(@Param("redeem")String redeem);
}