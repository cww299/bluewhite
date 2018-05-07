package bluewhite.common.entity;

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
	public PageResult(){
		
	}
	public PageResult(Page<T> pageResult){
		setRows(pageResult.getContent());
		setTotal(pageResult.getTotalElements());
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
