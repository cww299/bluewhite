package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
/**
 * 电商子销售单实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_order_child")
public class OnlineOrderChild  extends BaseEntity<Long> {
	
	
	/**
	 * 商品id
	 */
	@Column(name = "commodity_id")
	private Long commodityId;
	
	/**
	 * 商品实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Commodity commodity;
	
	
	/**
	 * 订单id
	 * 
	 */
	@Column(name = "online_order_id")
	private Long onlineOrderId;
	

	/**
	 * 父订单实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OnlineOrder onlineOrder;
	
	/**
	 * 数量
	 */
	@Column(name = "number")
	private Integer number;
	
	
	/**
	 * 单价
	 */
	@Column(name = "price")
	private Double price;
	

	
	/**
	 * 单价总金额
	 */
	@Column(name = "sum_price")
	private Double sumPrice;
	
	/**
	 * 系统优惠
	 */
	@Column(name = "system_preferential")
	private Double systemPreferential;

	/**
	 * 卖家调价
	 */
	@Column(name = "seller_readjust_prices")
	private Double sellerReadjustPrices;

	/**
	 * 实际金额
	 */
	@Column(name = "actual_sum")
	private Double actualSum;

	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Double getSystemPreferential() {
		return systemPreferential;
	}

	public void setSystemPreferential(Double systemPreferential) {
		this.systemPreferential = systemPreferential;
	}

	public Double getSellerReadjustPrices() {
		return sellerReadjustPrices;
	}

	public void setSellerReadjustPrices(Double sellerReadjustPrices) {
		this.sellerReadjustPrices = sellerReadjustPrices;
	}

	public Double getActualSum() {
		return actualSum;
	}

	public void setActualSum(Double actualSum) {
		this.actualSum = actualSum;
	}
	
	
	
	

}
