package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
/**
 * 仓库种类
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_warehouse")
public class Warehouse extends BaseEntity<Long>{
	
	
	/**
	 * 仓库类型(所属的仓库管理员)
	 */
	@Column(name = "warehouse_type")
	private String warehouseType;

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	
	
	

	
}
