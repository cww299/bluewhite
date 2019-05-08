package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 电商采购单实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_procurement")
public class Procurement extends BaseEntity<Long>{
	
	/**
	 * 采购单状态
	 */
	@Column(name = "seller_nick")
	private Integer sellerNick;

	public Integer getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(Integer sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	
	

}
