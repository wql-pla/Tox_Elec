package com.tox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tox.bean.ElecCoupons;
import com.tox.bean.ElecGroupCounponsF;
@Mapper
public interface ElecGroupCounponsFMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElecGroupCounponsF record);

    int insertSelective(ElecGroupCounponsF record);

    ElecGroupCounponsF selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElecGroupCounponsF record);

    int updateByPrimaryKey(ElecGroupCounponsF record);

	List<ElecGroupCounponsF> getGroupCounpons(ElecGroupCounponsF couponsF);

	int getGroupCounponsCount(ElecGroupCounponsF couponsF);
}