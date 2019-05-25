package com.bluewhite.onlineretailers.inventory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
/**
 * 预警实体
 * 三个预警设置（1.库存下限预警，2库存上限预警，3库存时间预警）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_warning")
public class Warning extends BaseEntity<Long>{
	
	
	/**
	 * 预警类型（1.库存下限预警，2库存上限预警，3库存时间过长预警）
	 * 
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 预警数量
	 * 
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 预警时间段（按天作为单位计算）
	 * 
	 */
	@Column(name = "time")
	private Integer time;
	
	
	/**
	 * 仓库类型id
	 */
	@Column(name = "warehouse_id")
	private Long warehouseId;
	
	/**
	 * 仓库类型（0=主仓库，1=客供仓库，2=次品）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouse;
	
	
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public BaseData getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(BaseData warehouse) {
		this.warehouse = warehouse;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	
	
	
	
	

}
