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
	 *  入库申请
	 *  出库申请	
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
	 *  入库：返工申请，调拨申请，退货申请 ，换货申请，盘亏申请
	 *  出库：调拨申请，换货申请，退货申请，盘盈申请，返工申请
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

}
