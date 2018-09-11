package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.Admin;
import com.tox.bean.Coupon;
@Mapper
public interface CouponMapper {
   
	List<Coupon> getCouponsByUserId(@Param("userId")Integer userId);
	
}