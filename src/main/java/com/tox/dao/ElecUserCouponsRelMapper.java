package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.tox.bean.ElecUserCouponsRel;

@Mapper
@Service
public interface ElecUserCouponsRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecUserCouponsRel record);

    int insertSelective(ElecUserCouponsRel record);

    ElecUserCouponsRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecUserCouponsRel record);

    int updateByPrimaryKey(ElecUserCouponsRel record);
    //根据用户id获取优惠券列表
	List<ElecUserCouponsRel> getCouponsByUserId(ElecUserCouponsRel record);
	
	int getCouponsCountByUserId(ElecUserCouponsRel record);
	//根据用户id和兑换码id获取兑换记录
	List<ElecUserCouponsRel> getUserCouponsRelByUserIdAndRedeemId(@Param("userId")Integer userId,@Param("redeemId") Integer redeemId);

	List<ElecUserCouponsRel> getCouponsNumByUserId(@Param("userId")Integer userId);

	int getCouponsNumByRedeemId(@Param("redeemId")Integer redeemId);

}