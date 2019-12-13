package com.bluewhite.ledger.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 出库单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_out_storage")
public class OutStorage extends BaseEntity<Long> {

	/**
	 * 编号
	 */
	@Column(name = "serial_number")
	private String serialNumber;

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
	 * 申请请求单id
	 */
	@Column(name = "apply_voucher_id")
	private Long applyVoucherId;

	/**
	 * 申请请求单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_voucher_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ApplyVoucher applyVoucher;
	
	/**
	 * 发货单id
	 */
	@Column(name = "send_goods_id")
	private Long sendGoodsId;

	/**
	 * 发货单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "send_goods_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SendGoods sendGoods;

	/**
	 * 出库单类型（1=销售出库） （2=调拨出库） （3=换货出库 ） （4=退货出库 ） （5=盘盈出库 ）(6=返工出库) （7=生产出库）皮壳
	 * 销售出库：根据发货申请单出库
	 * 调拨出库：根据调拨申请单出库
	 * 换货出库：根据换货申请单出库
	 * 退货出库：根据退货申请单出库
	 * 盘盈出库：根据盘盈申请单出库
	 * 返工出库：根据返工申请单出库
	 */
	@Column(name = "out_status")
	private Integer outStatus;

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
	 * 出库操作人id
	 * 
	 */
	@Column(name = "user_storage_id")
	private Long userStorageId;

	/**
	 * 出库操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User userStorage;

	/**
	 * 物料名称
	 */
	@Transient
	private String productName;

	/**
	 * 物料编号
	 */
	@Transient
	private String productNumber;
	
	/**
	 * 多对多出库单
	 */
	@Transient
	private String putOutStorageIds;

	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	

	public Long getApplyVoucherId() {
		return applyVoucherId;
	}

	public void setApplyVoucherId(Long applyVoucherId) {
		this.applyVoucherId = applyVoucherId;
	}

	public ApplyVoucher getApplyVoucher() {
		return applyVoucher;
	}

	public void setApplyVoucher(ApplyVoucher applyVoucher) {
		this.applyVoucher = applyVoucher;
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

	public String getPutOutStorageIds() {
		return putOutStorageIds;
	}

	public void setPutOutStorageIds(String putOutStorageIds) {
		this.putOutStorageIds = putOutStorageIds;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public Long getUserStorageId() {
		return userStorageId;
	}

	public void setUserStorageId(Long userStorageId) {
		this.userStorageId = userStorageId;
	}

	public User getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(User userStorage) {
		this.userStorage = userStorage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Integer arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

}
