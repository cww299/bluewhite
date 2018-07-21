package com.bluewhite.product.primecost.primecost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 成本价格实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_prime_cost")
public class PrimeCost extends BaseEntity<Long>{
	
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
    private Long productId;
	
	
	/**
	 * 面料价格(含复合物料和加工费）
	 */
	@Column(name = "cut_parts_price")
    private Double cutPartsPrice;
	
	
//	/**
//	 * 除面料以外的其他物料价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	
//	/**
//	 * 裁剪价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	
//	/**
//	 * 机工价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	/**
//	 * 绣花价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	/**
//	 * 针工价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	/**
//	 * 内外包装和出入库的价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
//	
//	/**
//	 *预计运费价格
//	 */
//	@Column(name = "product_id")
//    private Long productId;
	

//	/**
//	 * 是否含开票
//	 */
//	private Integer ;
//	
//	/**
//	 * 综合税负加所得税负
//	 */
//	private Double ;
//	
//	/**
//	 * 剩余到手的
//	 */
//	private Double ;
//	
//	/**
//	 * 预算成本
//	 */
//	private Double ;
//	
//	/**
//	 * 预算成本加价率
//	 */
//	private Double ;
//	
//	/**
//	 * 实战成本
//	 */
//	private Double ;
//	
//	/**
//	 * 实战成本加价率
//	 */
//	private Double ;
	
}
