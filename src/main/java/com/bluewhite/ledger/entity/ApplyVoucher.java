package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 销售生产系统中的所有的申请单据
 *
 */
@Entity
@Table(name = "ledger_apply_voucher")
public class ApplyVoucher extends BaseEntity<Long> {
	
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
	 * 仓库种类id
	 */
	@Column(name = "warehouse_type_id")
	private Long warehouseTypeId;

	/**
	 * 仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouseType;
	
	/**
	 * 申请单编号
	 */
	@Column(name = "apply_number")
	private String applyNumber;

	/**
	 * 申请时间
	 */
	@Column(name = "time")
	private Date time;

	/**
	 * 申请原因
	 * 
	 */
	@Column(name = "cause")
	private String cause;
	
	/**
	 * 申请单类型id( 
	 *  销售申请
	 *  出库入库申请
	 * )
	 */
	@Column(name = "apply_voucher_type_id")
	private Long applyVoucherTypeId;

	/**
	 * 申请类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_voucher_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData applyVoucherType;

	/**
	 * 申请种类id( 
	 *  销售： 销售员借货申请
	 *  出库入库：调拨申请，换货申请，退货申请，盘亏申请，返工申请，盘盈申请
	 * )
	 */
	@Column(name = "apply_voucher_kind_id")
	private Long applyVoucherKindId;

	/**
	 * 申请种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_voucher_kind_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData applyVoucherKind;

	/**
	 * 申请人员id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 申请人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 是否通过
	 */
	@Column(name = "pass")
	private Integer pass;

	/**
	 * 通过时间
	 */
	@Column(name = "pass_time")
	private Date passTime;
	
	/**
	 * 被申请人员id
	 */
	@Column(name = "approval_user_id")
	private Long approvalUserId;

	/**
	 *  被申请人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User approvalUser;
	
	/**
	 * 申请数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 针对发货单申请
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
	 * 针对加工单申请
	 * 加工单id
	 */
	@Column(name = "orderout_source_id")
	private Long orderOutSourceId;
	
	/**
	 * 加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderout_source_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource orderOutSource;

	/**
	 * 产品名称
	 */
	@Transient
	private String productName;
	
	/**
	 * 产品名编号
	 */
	@Transient
	private String productNumber;
	
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
	
	
	
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}

	public BaseData getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(BaseData warehouseType) {
		this.warehouseType = warehouseType;
	}

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return orderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		this.orderOutSource = orderOutSource;
	}

	public SendGoods getSendGoods() {
		return sendGoods;
	}

	public void setSendGoods(SendGoods sendGoods) {
		this.sendGoods = sendGoods;
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

	public Long getSendGoodsId() {
		return sendGoodsId;
	}

	public void setSendGoodsId(Long sendGoodsId) {
		this.sendGoodsId = sendGoodsId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}

	public Long getApplyVoucherTypeId() {
		return applyVoucherTypeId;
	}

	public void setApplyVoucherTypeId(Long applyVoucherTypeId) {
		this.applyVoucherTypeId = applyVoucherTypeId;
	}

	public BaseData getApplyVoucherType() {
		return applyVoucherType;
	}

	public void setApplyVoucherType(BaseData applyVoucherType) {
		this.applyVoucherType = applyVoucherType;
	}

	public Long getApplyVoucherKindId() {
		return applyVoucherKindId;
	}

	public void setApplyVoucherKindId(Long applyVoucherKindId) {
		this.applyVoucherKindId = applyVoucherKindId;
	}

	public BaseData getApplyVoucherKind() {
		return applyVoucherKind;
	}

	public void setApplyVoucherKind(BaseData applyVoucherKind) {
		this.applyVoucherKind = applyVoucherKind;
	}

	public Long getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(Long approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	public User getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(User approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

}
