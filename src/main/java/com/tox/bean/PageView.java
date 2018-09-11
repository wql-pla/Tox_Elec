package com.tox.bean;

import java.util.List;

public class PageView<T>   {
	
	protected Integer pageNum;
	
	protected Integer pageSize;
	
	protected Integer total;
	
	protected List<T> list;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PageView [pageNum=" + pageNum + ", pageSize=" + pageSize + ", total=" + total + ", list=" + list + "]";
	}
	
}
