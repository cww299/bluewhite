package com.bluewhite.product.primecostbasedata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 基础数据4(机工页面布类基础数据)
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_four")
public class BaseFour extends BaseEntity<Long>{
	
    /**
     * 布类名
     */
	@Column(name = "name")
    private String name;
	
    /**
     * 备选缝纫序列
     */
	@Column(name = "sewing_order")
    private String sewingOrder;
	
    /**
     * 每CM5-6针每秒机走CM
     */
	@Column(name = "needle56")
    private Double needle56;
	
    /**
     * 每CM4-5针每秒机走CM
     */
	@Column(name = "needle45")
    private Double needle45;
	
    /**
     * 每CM3-4针每秒机走CM
     */
	@Column(name = "needle34")
    private Double needle34;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSewingOrder() {
		return sewingOrder;
	}

	public void setSewingOrder(String sewingOrder) {
		this.sewingOrder = sewingOrder;
	}

	public Double getNeedle56() {
		return needle56;
	}

	public void setNeedle56(Double needle56) {
		this.needle56 = needle56;
	}

	public Double getNeedle45() {
		return needle45;
	}

	public void setNeedle45(Double needle45) {
		this.needle45 = needle45;
	}

	public Double getNeedle34() {
		return needle34;
	}

	public void setNeedle34(Double needle34) {
		this.needle34 = needle34;
	}
	
	
	
	

}
