package com.bluewhite.finance.ledger.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;


/**
 * 财务 客户产品价格
 * @author qiyong
 *
 */
@Entity
@Table(name = "fin_ledger_contact" )
public class Contact extends BaseEntity<Long>{
	/**
	 * 乙方
	 */
	@Column(name = "con_party_names")
    private String conPartyNames;
	
	/**
	 * 客户电话
	 */
	@Column(name = "con_phone")
    private String conPhone;

	
	/**
	 * 客户微信等
	 */
	@Column(name = "con_wechat")
    private String conWechat;


	public String getConPartyNames() {
		return conPartyNames;
	}


	public void setConPartyNames(String conPartyNames) {
		this.conPartyNames = conPartyNames;
	}


	public String getConPhone() {
		return conPhone;
	}


	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}


	public String getConWechat() {
		return conWechat;
	}


	public void setConWechat(String conWechat) {
		this.conWechat = conWechat;
	}

	
	
	
}
