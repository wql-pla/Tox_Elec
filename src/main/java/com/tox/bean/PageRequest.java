package com.tox.bean;

public class PageRequest {
    protected int pageSize=10;
    protected  int pageNum=0;
    private  int offset=0;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getOffset() {
     return  (pageNum)*pageSize;


    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

	@Override
	public String toString() {
		return "PageRequest [pageSize=" + pageSize + ", pageNum=" + pageNum + ", offset=" + offset + "]";
	}
    
}
