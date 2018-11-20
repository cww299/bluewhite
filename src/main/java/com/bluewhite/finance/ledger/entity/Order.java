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

import com.bluewhite.base.BaseEntity;

@Entity
@Table(name = "fin_ledger_order" )
public class Order extends BaseEntity<Long>{
	/**
	 * 当月销售编号
	 */
	@Column(name = "sales_number")
    private String salesNumber;
	
	
	
	/**
	 * 合同签订日期
	 */
	@Column(name = "contract_time")
    private Date contractTime;
	
	
	/**
	 * 甲方
	 */
	@Column(name = "first_names")
    private String firstNames;
	
	
	/**
	 * 乙方
	 */
	@Column(name = "party_names")
    private String partyNames;
	
	
	/**
	 * 当批 批次号
	 */
	@Column(name = "batch_number")
    private String batchNumber;
	
	
	/**
	 * 当批计划单号
	 */
	@Column(name = "plan_numbers")
    private String planNumbers;
	
	
	/**
	 * 当批产品名
	 */
	@Column(name = "product_name")
    private String productName;
	
	
	/**
	 * 当批合同数量
	 */
	@Column(name = "contract_number")
    private Integer contractNumber;
	
	
	/**
	 * 当批合同总价
	 */
	@Column(name = "contract_price")
    private Double contractPrice;
	
	
	/**
	 * 预付款备注
	 */
	@Column(name = "remarks_price")
    private Double remarksPrice;
	
	
	/**
	 * 手动填写单只价格
	 */
	@Column(name = "price")
    private Double price;


	public String getSalesNumber() {
		return salesNumber;
	}


	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}


	public Date getContractTime() {
		return contractTime;
	}


	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}




	public String getFirstNames() {
		return firstNames;
	}


	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}


	public String getPartyNames() {
		return partyNames;
	}


	public void setPartyNames(String partyNames) {
		this.partyNames = partyNames;
	}


	public String getBatchNumber() {
		return batchNumber;
	}


	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}


	public String getPlanNumbers() {
		return planNumbers;
	}


	public void setPlanNumbers(String planNumbers) {
		this.planNumbers = planNumbers;
	}




	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Integer getContractNumber() {
		return contractNumber;
	}


	public void setContractNumber(Integer contractNumber) {
		this.contractNumber = contractNumber;
	}


	public Double getContractPrice() {
		return contractPrice;
	}


	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}


	public Double getRemarksPrice() {
		return remarksPrice;
	}


	public void setRemarksPrice(Double remarksPrice) {
		this.remarksPrice = remarksPrice;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
}
