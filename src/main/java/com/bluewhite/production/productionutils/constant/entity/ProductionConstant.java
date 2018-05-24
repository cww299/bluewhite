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
	 * 常量excel名称
	 */
	@Column(name = "excel_name")
	private String excelName;
	
	/**
	 * 常量类型
	 */
	@Column(name = "type")
	private Integer type;

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

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	
	
}
