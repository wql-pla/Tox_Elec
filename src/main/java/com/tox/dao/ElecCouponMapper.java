package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tox.bean.ElecCoupon;
@Mapper
public interface ElecCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecCoupon record);

    int insertSelective(ElecCoupon record);

    ElecCoupon selectByPrimaryKey(Integer id);
    
    //查寻订单列表
    List<ElecCoupon> selectListByPrimaryKey(@Param("couponVo")ElecCoupon record);

    int updateByPrimaryKeySelective(ElecCoupon record);

    int updateByPrimaryKey(ElecCoupon record);
    //更改兑换码状态
    int updateStatus(@Param("ID")Integer ID);
}