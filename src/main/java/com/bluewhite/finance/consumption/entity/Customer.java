package com.bluewhite.finance.consumption.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 关于消费中的具体消费对象，当没有时进行新增
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_customer" )
public class Customer extends BaseEntity<Long>{
	
	/**
	 * 消费对象类型
	 */
	@Column(name = "type")
    private Integer type;
	
	/**
	 * 消费对象名称
	 */
	@Column(name = "name")
    private String name;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
