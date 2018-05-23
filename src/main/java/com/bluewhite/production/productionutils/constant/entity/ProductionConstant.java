package com.bluewhite.production.productionutils.constant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 生产控制常量表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_production_constant")
public class ProductionConstant extends BaseEntity<Long>{
	/**
	 * 常量名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 常量数值
	 */
	@Column(name = "number")
	private Double number;
	/**
	 * 常量英文名称
	 */
	@Column(name = "pro_name")
	private String proName;
	
	/**
	 * 常量类型
	 */
	@Column(name = "type")
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
