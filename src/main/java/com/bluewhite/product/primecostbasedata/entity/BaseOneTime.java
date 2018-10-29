package com.bluewhite.product.primecostbasedata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;

/**
 * 基础数据1，2中的项目所需要的时间(成本计算)
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_one_time")
public class BaseOneTime extends BaseEntity<Long>{
	
	
	/**
	 * 已考证的时间/秒
	 */
	@Poi(name = "", column = "B")
	@Column(name = "textual_time")
	private Double textualTime;
	
	/**
	 * 需要时间考证设定/秒
	 */
	@Poi(name = "", column = "C")
	@Column(name = "time")
	private Double time;
	
	
	
	/**
	 * 被选工序的各种类别设定
	 */
	@Poi(name = "", column = "A")
	@Column(name = "category_setting")
	private String categorySetting;
	
	/**
	 * 基础数据1 id (冗余字段，用于前台的展示)
	 */
	@Column(name = "baseone_id")
	private Long baseOneId;
	
	/**
	 * 多对一
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "baseone_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne baseOne;

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public String getCategorySetting() {
		return categorySetting;
	}

	public void setCategorySetting(String categorySetting) {
		this.categorySetting = categorySetting;
	}

	public Long getBaseOneId() {
		return baseOneId;
	}

	public void setBaseOneId(Long baseOneId) {
		this.baseOneId = baseOneId;
	}

	public BaseOne getBaseOne() {
		return baseOne;
	}

	public void setBaseOne(BaseOne baseOne) {
		this.baseOne = baseOne;
	}

	public Double getTextualTime() {
		return textualTime;
	}

	public void setTextualTime(Double textualTime) {
		this.textualTime = textualTime;
	}
	
	

}
