package com.tox.service;

import com.tox.bean.ElecStore;
import com.tox.bean.PageResponse;
import com.tox.dao.ElecStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.font.CreatedFontTracker;

import java.util.List;

@Service("storeService")
@Transactional(propagation= Propagation.REQUIRES_NEW)
public class ElecStoreService {
    @Autowired
    private ElecStoreMapper elecStoreMapper;

    /**
     * 编辑与新增
     * @param elecStore
     * @return
     */
    public ElecStore  addOrEdit(ElecStore elecStore) {
        if (elecStore!=null){
            if (elecStore.getId()!=null){
                elecStoreMapper.updateByPrimaryKeySelective(elecStore);
            }else {
                elecStoreMapper.insertSelective(elecStore);
            }
        }

        return elecStore;
    }

    /**
     * 删除
     * @param id
     */
    public void del(Integer id) {
        elecStoreMapper.deleteByPrimaryKey(id);
    }

    /**
     * 门店名称列表
     * @return
     */
    public List<ElecStore> nameList() {
       return elecStoreMapper.nameList();
    }

    /**
     * 分页列表查询
     * @param elecStore
     * @return
     */
    public PageResponse<ElecStore> list(ElecStore elecStore) {
        Integer totalCount=0;
        if(elecStore.getId()!=null){
            totalCount=1;
        }else {
            totalCount=elecStoreMapper.pageCount(elecStore);
        }

       PageResponse<ElecStore> pageResponse=new PageResponse<>();
       if(totalCount>0){
           List<ElecStore> pageList=elecStoreMapper.pageList(elecStore);
           pageResponse.countPage(totalCount,pageList,elecStore);

       }

        return pageResponse;
    }
}
