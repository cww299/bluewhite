package com.bluewhite.finance.ledger.entity;
/**
 * 财务 订单实体
 * @author qiyong
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;

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
	 * 甲方id
	 */
	@Column(name = "first_names_id")
    private Long firstNamesId;
	
	
	/**
	 * 乙方
	 */
	@Column(name = "party_names")
    private String partyNames;
	
	/**
	 * 乙方Id
	 */
	@Column(name = "party_names_id")
    private Long partyNamesId;
	
	
	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "party_names_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Contact contact;
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


	/**
	 * 手动填写到岸数量
	 */
	@Column(name = "ashore_number")
    private Integer ashoreNumber;


	/**
	 * 到岸日期
	 */
	@Column(name = "ashore_time")
    private Date ashoreTime;

	/**
	 * 核对完毕提示
	 */
	@Column(name = "ashore_check")
    private Integer ashoreCheckr;
	
	
	/**
	 * 争议数量
	 */
	@Column(name = "dispute_number")
    private Integer disputeNumber;
	
	/**
	 * 在途数量
	 */
	@Column(name = "road_number")
    private Integer roadNumber;
	
	/**
	 * 争议价格
	 */
	@Column(name = "dispute_price")
    private Double disputePrice;
	public Contact getContact() {
		return contact;
	}


	public void setContact(Contact contact) {
		this.contact = contact;
	}


	public Long getFirstNamesId() {
		return firstNamesId;
	}


	public void setFirstNamesId(Long firstNamesId) {
		this.firstNamesId = firstNamesId;
	}


	public Long getPartyNamesId() {
		return partyNamesId;
	}


	public void setPartyNamesId(Long partyNamesId) {
		this.partyNamesId = partyNamesId;
	}


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
