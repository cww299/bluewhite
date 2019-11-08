package com.bluewhite.ledger.entity;
/**
 * 
 * 出库单
 * @author zhangliang
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.product.entity.Product;

@Entity
@Table(name = "ledger_out_storage")
public class OutStorage extends BaseEntity<Long> {

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Product product;

	/**
	 * （1=生产出库） （2=调拨出库） （3=销售换货出库 ） （4=采购退货出库 ） （5=盘盈出库 ）
	 */
	@Column(name = "out_status")
	private Integer outStatus;
	
	/**
	 * 仓管指定 出库仓库种类id
	 */
	@Column(name = "out_warehouse_type_id")
	private Long outWarehouseTypeId;

	/**
	 * 出库仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "out_warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData inWarehouseType;
	
	
	/**
	 * 出库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 出库数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;
	
	/**
	 * 库位
	 */
	@Column(name = "location")
	private String location;

}
