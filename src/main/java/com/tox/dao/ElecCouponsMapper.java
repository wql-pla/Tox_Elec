package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.tox.bean.ElecCoupons;

@Service
@Mapper
public interface ElecCouponsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecCoupons record);

    int insertSelective(ElecCoupons record);
    
    List<ElecCoupons> selectCoupons(@Param("coupons") ElecCoupons coupons);

	int selectCouponsCount(@Param("coupons")ElecCoupons coupons);

    ElecCoupons selectByPrimaryKey(Integer id);
    
    ElecCoupons selectByPrimaryKeys(Integer id);

    int updateByPrimaryKeySelective(ElecCoupons record);

    int updateByPrimaryKey(ElecCoupons record);

	List<ElecCoupons> getFreeCoupon();
}