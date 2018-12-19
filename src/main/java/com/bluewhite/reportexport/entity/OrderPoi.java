package com.bluewhite.reportexport.entity;
/**
 * 财务 订单实体
 * @author qiyong
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.bluewhite.common.utils.excel.Poi;
import com.bluewhite.finance.ledger.entity.Contact;


public class OrderPoi{
	/**
	 * 当月销售编号
	 */
	@Poi(name = "当月销售编号", column = "A")
    private String salesNumber;
	
	
	
	/**
	 * 合同签订日期
	 */
	@Poi(name = "合同签订日期", column = "B")
    private String contractTime;
	
	
	/**
	 * 甲方
	 */
	@Column(name = "first_names")
	@Poi(name = "甲方", column = "C")
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
	@Poi(name = "乙方", column = "D")
    private String partyNames;
	
	/**
	 * 乙方Id
	 */
	@Column(name = "party_names_id")
    private Long partyNamesId;
	
	

	
	/**
	 * 当批 批次号
	 */
	@Column(name = "batch_number")
	@Poi(name = "当批 批次号", column = "E")
    private String batchNumber;
	
	
	
	/**
	 * 当批产品名
	 */
	@Column(name = "product_name")
	@Poi(name = "产品名", column = "F")
    private String productName;
	
	
	/**
	 * 当批合同数量
	 */
	@Poi(name = "当批合同数量", column = "G")
    private Double contractNumber;
	
	
	/**
	 * 当批合同总价
	 */
	@Poi(name = "当批合同总价", column = "H")
    private Double contractPrice;
	
	
	/**
	 * 预付款备注
	 */
	@Poi(name = "预付款备注", column = "I")
    private String remarksPrice;
	
	
	/**
	 * 手动填写单只价格
	 */
	@Poi(name = "单只价格", column = "J")
    private Double price;


	/**
	 * 手动填写到岸数量
	 */
	@Poi(name = "到岸数量", column = "K")
    private Double ashoreNumber;


	/**
	 * 到岸日期
	 */
	@Poi(name = "预计到岸日期", column = "L")
    private String ashoreTime;

	
	/**
	 * 在线状态（0==线下 1==线上） 
	 */
	@Poi(name = "", column = "N")
    private Double online;
	
	
	public Double getOnline() {
		return online;
	}

	public void setOnline(Double online) {
		this.online = online;
	}

	public String getSalesNumber() {
		return salesNumber;
	}

	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}

	

	public String getContractTime() {
		return contractTime;
	}

	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public Long getFirstNamesId() {
		return firstNamesId;
	}

	public void setFirstNamesId(Long firstNamesId) {
		this.firstNamesId = firstNamesId;
	}

	public String getPartyNames() {
		return partyNames;
	}

	public void setPartyNames(String partyNames) {
		this.partyNames = partyNames;
	}

	public Long getPartyNamesId() {
		return partyNamesId;
	}

	public void setPartyNamesId(Long partyNamesId) {
		this.partyNamesId = partyNamesId;
	}



	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Double getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
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


	public Double getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(Double contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Double getAshoreNumber() {
		return ashoreNumber;
	}

	public void setAshoreNumber(Double ashoreNumber) {
		this.ashoreNumber = ashoreNumber;
	}

	public String getAshoreTime() {
		return ashoreTime;
	}

	public void setAshoreTime(String ashoreTime) {
		this.ashoreTime = ashoreTime;
	}

	



	
	
	
	
}
