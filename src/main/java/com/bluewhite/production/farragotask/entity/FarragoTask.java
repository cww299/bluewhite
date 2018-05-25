package com.bluewhite.production.farragotask.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 杂工（混杂工作，一些除正常之外的工作）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_farrago_task")
public class FarragoTask {
	
	/**
	 * 杂工所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	
	/**
	 * 杂工名称
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
