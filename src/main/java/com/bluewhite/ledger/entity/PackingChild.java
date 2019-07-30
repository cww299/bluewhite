package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;
/**
 * 贴包子单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing_child")
public class PackingChild extends BaseEntity<Long> {
	
	/**
	 * 发货单id
	 * 
	 */
	@Column(name = "sendGoods_id")
	private Long sendGoodsId;

	/**
	 * 发货单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sendGoods_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SendGoods sendGoods;
	
	/**
	 * 订单id
	 * 
	 */
	@Column(name = "packing_id")
	private Long packingId;
	

	/**
	 * 父订单实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packing_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Packing packing;
	     
	/**
	 * 批次号
	 */
	@Column(name = "bacth_number")
	private String bacthNumber;

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
	 * 单只价格
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 实际数量
	 */
	@Column(name = "count")
	private Integer count;
	
	/**
	 * 总价
	 */
	@Column(name = "sum_price")
	private Double sumPrice;

	/**
	 * 是否拥有版权
	 */
	@Column(name = "copyright")
	private boolean copyright = false;
	
	/**
	 * 销售编号 
	 */
	@Column(name = "sale_number")
	private String saleNumber;
	
	/**
	 * 发货时间
	 */
	@Column(name = "send_date")
	private Date sendDate;
	
	/**
	 * 是否发货
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 预付款备注
	 */
	@Column(name = "remark")
	private String remark;
	
	
	
	
	public Long getPackingId() {
		return packingId;
	}

	public void setPackingId(Long packingId) {
		this.packingId = packingId;
	}

	public Packing getPacking() {
		return packing;
	}

	public void setPacking(Packing packing) {
		this.packing = packing;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public boolean isCopyright() {
		return copyright;
	}

	public void setCopyright(boolean copyright) {
		this.copyright = copyright;
	}

	public Long getSendGoodsId() {
		return sendGoodsId;
	}

	public void setSendGoodsId(Long sendGoodsId) {
		this.sendGoodsId = sendGoodsId;
	}

	public SendGoods getSendGoods() {
		return sendGoods;
	}

	public void setSendGoods(SendGoods sendGoods) {
		this.sendGoods = sendGoods;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	


}
