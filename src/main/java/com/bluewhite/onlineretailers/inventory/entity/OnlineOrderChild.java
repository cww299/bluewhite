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
	 * 订单状态交易状态。可选值: 
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) 
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
	 * TRADE_FINISHED(交易成功) 
	 * 
	 */
	@Column(name = "status")
	private String status;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOnlineOrderId() {
		return onlineOrderId;
	}

	public void setOnlineOrderId(Long onlineOrderId) {
		this.onlineOrderId = onlineOrderId;
	}

	public OnlineOrder getOnlineOrder() {
		return onlineOrder;
	}

	public void setOnlineOrder(OnlineOrder onlineOrder) {
		this.onlineOrder = onlineOrder;
	}

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
