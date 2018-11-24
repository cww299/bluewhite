package com.bluewhite.finance.ledger.entity;
/**
 * 财务 客户产品价格
 * @author qiyong
 *
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
@Entity
@Table(name = "fin_ledger_customer" )
public class Customer extends BaseEntity<Long>{
	/**
	 * 当批产品名
	 */
	@Column(name = "cus_product_name")
    private String cusProductName;
	
	/**
	 * 乙方
	 */
	@Column(name = "cus_party_names")
    private String cusPartyNames;
	
	/**
	 * 客户单只价格
	 */
	@Column(name = "cus_price")
    private Double cusPrice;

	public String getCusProductName() {
		return cusProductName;
	}

	public void setCusProductName(String cusProductName) {
		this.cusProductName = cusProductName;
	}

	public String getCusPartyNames() {
		return cusPartyNames;
	}

	public void setCusPartyNames(String cusPartyNames) {
		this.cusPartyNames = cusPartyNames;
	}

	public Double getCusPrice() {
		return cusPrice;
	}

	public void setCusPrice(Double cusPrice) {
		this.cusPrice = cusPrice;
	}
	
	
}
