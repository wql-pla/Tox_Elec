package com.tox.dao;

import com.tox.bean.ElecStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ElecStoreMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

//    int insert(ElecStore record);

    /**
     * 新增
     * @param record
     * @return
     */
    int insertSelective(ElecStore record);

    /**
     * 查询根据id
     * @param id
     * @return
     */
    ElecStore selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ElecStore record);

//    int updateByPrimaryKey(ElecStore record);

    /**
     * 门店名称列表
     * @return
     */
    List<ElecStore> nameList();

    /**
     * 分页数量查询
     * @param elecStore
     * @return
     */
    int pageCount(ElecStore elecStore);

    /**
     * 分页列表查询
     * @param elecStore
     * @return
     */
    List<ElecStore> pageList(ElecStore elecStore);
}