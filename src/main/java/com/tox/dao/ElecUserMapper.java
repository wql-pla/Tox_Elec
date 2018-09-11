package com.tox.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tox.bean.ElecUser;

@Mapper
public interface ElecUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecUser record);

    int insertSelective(ElecUser record);

    ElecUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecUser record);

    int updateByPrimaryKey(ElecUser record);
    
    ElecUser selectByPhone(ElecUser record);
    
    ElecUser selectByOpenId(@Param("openId")String openId);
    
    List<ElecUser> getUsers(ElecUser record);
    
    Integer getUserCount(ElecUser record);

	ElecUser userDetail(Integer id);
	
	Integer changeAdmins(List<Integer> list);
    
	Integer changeCommons(List<Integer> list);
	
	Integer selectByPrimaryKeyByFreeCounts(Integer id);
}