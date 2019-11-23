package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 退货单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_refund_bills")
public class RefundBills extends BaseEntity<Long>{
	
	/**
	 * 加工单id
	 * 
	 */
	@Column(name = "order_outSource_Id")
	private Long orderOutSourceId;

	/**
	 *  加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_outSource_Id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource OrderOutSource;
	
	/**
	 * 退货数量
	 */
	@Column(name = "return_number")
	private Double returnNumber;
	
	/**
	 * 退货日期
	 */
	@Column(name = "return_time")
	private Date returnTime;
	
	/**
	 * 退货原因
	 */
	@Column(name = "return_remark")
	private String returnRemark;
	
	

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return OrderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		OrderOutSource = orderOutSource;
	}

	public Double getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Double returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}
	
	
	
	
	

	
}
