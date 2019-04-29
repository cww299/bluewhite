package com.bluewhite.finance.ledger.entity;
/**
 * 财务 订单实体
 * @author qiyong
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;

@Entity
@Table(name = "fin_ledger_Actualprice" )
public class Actualprice extends BaseEntity<Long>{
	/**
	 * 批次号
	 */
	@Poi(name = "", column = "E")
	@Column(name = "batch_number")
    private String batchNumber;
	
	
	/**
	 * 产品名
	 */
	@Column(name = "product_name")
	@Poi(name = "", column = "G")
    private String productName;
	
	
	/**
	 * 产品编号
	 */
	@Column(name = "product_number")
	@Poi(name = "", column = "H")
    private String productNumber;
	
	/**
	 * 预算成本价
	 */
	@Column(name = "budget_price")
	@Poi(name = "", column = "L")
    private Double budgetPrice;
	
	
	/**
	 * 实战成本
	 */
	@Column(name = "combat_price")
	@Poi(name = "", column = "N")
    private Double combatPrice;
	
	/**
	 * 所属月份
	 */
	@Column(name = "current_month")
	private Date currentMonth;
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
	
	
	public String getProductNumber() {
		return productNumber!=null ? productNumber.trim() : productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber.trim();
	}
	public Date getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getProductName() {
		return productName!=null ? productName.trim() : productName;
	}
	public void setProductName(String productName) {
		this.productName = productName.trim();
	}
	public Double getBudgetPrice() {
		return budgetPrice;
	}
	public void setBudgetPrice(Double budgetPrice) {
		this.budgetPrice = budgetPrice;
	}
	public Double getCombatPrice() {
		return combatPrice;
	}
	public void setCombatPrice(Double combatPrice) {
		this.combatPrice = combatPrice;
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
	
	
	
	

	
	
	
	
}
