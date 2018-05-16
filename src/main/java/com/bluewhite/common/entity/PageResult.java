package com.bluewhite.common.entity;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 分页搜索的结果bean
 * @author ZhouYong
 *
 */
public class PageResult<T> {
	private Long total;
	private List<T> rows;
	private Long totalPages;
	private Integer pageNum;
	public PageResult(){
		
	}
	public PageResult(Page<T> pageResult,PageParameter page){
		setRows(pageResult.getContent());
		setTotal(pageResult.getTotalElements());
		setTotalPages( ((pageResult.getTotalElements() + page.getSize()) / page.getSize()) -1);
		setPageNum(page.getPage());
		
	}
	

	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	

}
