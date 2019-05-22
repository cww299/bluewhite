package com.bluewhite.finance.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;


/**
 * 财务 订单实体
 * @author qiyong
 *
 */
@Entity
@Table(name = "fin_ledger_order"  ,indexes = {	@Index(columnList = "party_names_id")})
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
	 * 乙方关联
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
	 * 产品Id
	 */
	@Column(name = "product_id")
    private Long productId;
	
	/**
	 * 当批产品编号
	 */
	@Column(name = "product_number")
    private String productNumber;
	
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
    private String remarksPrice;
	
	
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
	 * 核对完毕提示(0 未核对 1已核对 ) 
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
	
	
	/**
	 * 到岸合同价
	 */
	@Column(name = "ashore_price")
    private Double ashorePrice;
	
	/**
	 * (1.预算成本 2.实战成本)
	 */
	@Column(name = "dispute")
    private Integer dispute;
	
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
	
	/**
	 * 查询字段 争议数字
	 */
	@Transient
	private Integer type;
	
	
	/**
	 * 在线状态（0==线下 1==线上） 
	 */
	@Column(name = "online")
    private Integer online;
	
	
	public String getProductNumber() {
		return productNumber;
	}




	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}




	public Integer getOnline() {
		return online;
	}


	

	public Long getProductId() {
		return productId;
	}




	public void setProductId(Long productId) {
		this.productId = productId;
	}




	public void setOnline(Integer online) {
		this.online = online;
	}





	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
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


	public Double getAshorePrice() {
		return ashorePrice;
	}


	public void setAshorePrice(Double ashorePrice) {
		this.ashorePrice = ashorePrice;
	}


	public Integer getAshoreNumber() {
		return ashoreNumber;
	}


	public void setAshoreNumber(Integer ashoreNumber) {
		this.ashoreNumber = ashoreNumber;
	}


	public Date getAshoreTime() {
		return ashoreTime;
	}


	public void setAshoreTime(Date ashoreTime) {
		this.ashoreTime = ashoreTime;
	}


	public Integer getAshoreCheckr() {
		return ashoreCheckr;
	}


	public void setAshoreCheckr(Integer ashoreCheckr) {
		this.ashoreCheckr = ashoreCheckr;
	}


	public Integer getDisputeNumber() {
		return disputeNumber;
	}


	public void setDisputeNumber(Integer disputeNumber) {
		this.disputeNumber = disputeNumber;
	}


	public Integer getRoadNumber() {
		return roadNumber;
	}


	public void setRoadNumber(Integer roadNumber) {
		this.roadNumber = roadNumber;
	}


	public Double getDisputePrice() {
		return disputePrice;
	}


	public void setDisputePrice(Double disputePrice) {
		this.disputePrice = disputePrice;
	}


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


	


	public Integer getDispute() {
		return dispute;
	}


	public void setDispute(Integer dispute) {
		this.dispute = dispute;
	}


	public String getRemarksPrice() {
		return remarksPrice;
	}


	public void setRemarksPrice(String remarksPrice) {
		this.remarksPrice = remarksPrice;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
}
