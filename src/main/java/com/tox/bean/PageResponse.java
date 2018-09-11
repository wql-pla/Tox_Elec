package com.tox.bean;

import java.util.ArrayList;
import java.util.List;

public class PageResponse <T> extends PageRequest{
    private int totalCount=0;
    private int pageCount=0;
    private List<T> list=new ArrayList<>();
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public  void countPage(int totalCount, List<T> pageList,PageRequest pageRequest) {

        this.totalCount=totalCount;
        this.list=pageList;
        this.pageSize=pageRequest.getPageSize();
        this.pageNum=pageRequest.getPageNum();
        this.pageCount=totalCount/this.pageSize+(totalCount%this.pageSize==0?0:1);

    }
}
