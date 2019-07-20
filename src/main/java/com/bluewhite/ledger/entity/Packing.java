package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.sys.entity.RegionAddress;

/**
 * 贴包
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing")
public class Packing  extends BaseEntity<Long>{
	
	/**
	 * 编号
	 */
	@Column(name = "number")
	private String number;
	
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customr_id")
	private Long customrId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customr_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customr customr;
	
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
	 * 当批外包编号
	 */
	@Column(name = "packaging_number")
	private Integer packagingNumber;
	
	
	/**
	 * 包装物名称id
	 */
	@Column(name = "packaging_id")
	private Long packagingId;
	
	/**
	 * 包装物名称
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packaging_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData packaging;
}
