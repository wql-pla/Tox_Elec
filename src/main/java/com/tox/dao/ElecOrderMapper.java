package com.tox.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecOrder;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface ElecOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecOrder record);

    int insertSelective(ElecOrder record);

    ElecOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecOrder record);

    int updateByPrimaryKey(ElecOrder record);
	
	List<ElecOrder> selectOrdersByOpenIdPages(@Param("order")ElecOrder order);
    
    int selectOrdersByOpenIdPagesCount(@Param("order")ElecOrder order);
    
    List<ElecOrder> selectOrdersByStatus(String status);
    
    int updateOrderStatus(@Param("ID")Integer ID);
    
    int updateOrderCid(@Param("CID")Integer CID,@Param("ID")Integer ID);

	List<Map<String, Object>> selectOrdersPages(@Param("order")ElecOrder order);

	int selectOrdersPagesCount(@Param("order")ElecOrder order);
	
	ElecOrder getOrderByOpenid(String openid);
	
	List<ElecOrder> getOrderListByOpenid(@Param("openId")String openid);
	
	List<ElecOrder> selectBills(@Param("openid")String openid);
	
	List<ElecOrder> selectOrders(ElecOrder record);
	
	List<ElecOrder> selectOrdersByStationId(@Param("stationId")Integer stationId);
	
	Integer selectOrderCount(ElecOrder record);
	
	Double selectSumCount(ElecOrder record);
	
	Double selectSumAmount(ElecOrder record);
	
	ElecOrder selectDetailById(@Param("id")Integer id);

	List<ElecOrder> getOverOrderListByOpenid(@Param("openId")String openId);

	ElecOrder getOrderAndCouponByOrderId(@Param("orderId")Integer orderId);
	
	Integer orderIngCount(@Param("list")List<Integer> list);

	List<ElecOrder> getOrderListByOpenidPages(@Param("openId")String openId, @Param("pageSize")Integer pageSize, @Param("pageNum")Integer pageNum);

	int getOrderListByOpenidPagesCount(@Param("openId")String openId);

	List<ElecOrder> getPageOrderListByOpenidPages(@Param("openId")String openId, @Param("pageSize")Integer pageSize, @Param("pageNum")Integer pageNum);
	
}