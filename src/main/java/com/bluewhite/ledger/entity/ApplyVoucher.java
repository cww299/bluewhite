package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
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
	 * 申请类型id( 
	 *  销售： 销售员借货申请
	 *  入库：返工申请，调拨申请，退货申请 ，换货申请，盘亏申请
	 *  出库：调拨申请，换货申请，退货申请，盘盈申请，返工申请
	 * )
	 */
	@Column(name = "apply_voucher_id")
	private Long applyVoucherId;

	/**
	 * 申请类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_voucher_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData applyVoucher;

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
	 * 审核人员id
	 */
	@Column(name = "approval_user_id")
	private Long approvalUserId;

	/**
	 * 审核人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User approvalUser;
	

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

	public Long getApplyVoucherId() {
		return applyVoucherId;
	}

	public void setApplyVoucherId(Long applyVoucherId) {
		this.applyVoucherId = applyVoucherId;
	}

	public BaseData getApplyVoucher() {
		return applyVoucher;
	}

	public void setApplyVoucher(BaseData applyVoucher) {
		this.applyVoucher = applyVoucher;
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
