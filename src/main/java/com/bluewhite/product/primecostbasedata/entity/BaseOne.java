package com.bluewhite.product.primecostbasedata.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;
/**
 * 基础数据1(成本计算)
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_one")
public class BaseOne extends BaseEntity<Long>{
	
	
    /**
     * 基础数据名称（裁片常用词填写，裁剪方式，压货环节，针工固定工序）
     */
	@Column(name = "name")
	@Poi(name = "", column = "A")
    private String name;
	
    /**
     * 基础数据类型
     */
	@Column(name = "type")
    private String type;
	
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
	 * 名称和时间关联，进行联查
	 */
	@OneToMany(mappedBy = "baseOne")
	private Set<BaseOneTime> baseOneTimes = new HashSet<BaseOneTime>();

	
	
	

	public Double getTextualTime() {
		return textualTime;
	}


	public void setTextualTime(Double textualTime) {
		this.textualTime = textualTime;
	}


	public Double getTime() {
		return time;
	}


	public void setTime(Double time) {
		this.time = time;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Set<BaseOneTime> getBaseOneTimes() {
		return baseOneTimes;
	}


	public void setBaseOneTimes(Set<BaseOneTime> baseOneTimes) {
		this.baseOneTimes = baseOneTimes;
	}






	
	
	
	
	
	

}
