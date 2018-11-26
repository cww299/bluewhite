package com.bluewhite.finance.ledger.entity;
/**
 * 财务 除货款以外的杂支
 * @author qiyong
 *
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
@Entity
@Table(name = "fin_ledger_Mixed" )
public class Mixed extends BaseEntity<Long>{
	
	/**
	 * 所属月份
	 */
	@Column(name = "mix_subordinate_time")
    private Date mixtSubordinateTime;
	
	
	
	/**
	 * 乙方
	 */
	@Column(name = "mix_party_names")
    private String mixPartyNames;
	
	
	/**
	 * 乙方
	 */
	@Column(name = "mix_party_names_id")
    private Long mixPartyNamesId;
	
	/**
	 * 来往日期
	 */
	@Column(name = "mix_time")
    private Date mixtTime;
	
	
	/**
	 * 来往明细
	 */
	@Column(name = "mix_detailed")
    private String mixDetailed;

	
	/**
	 * 来往金额
	 */
	@Column(name = "mix_price")
    private Double mixPrice;


	public Long getMixPartyNamesId() {
		return mixPartyNamesId;
	}


	public void setMixPartyNamesId(Long mixPartyNamesId) {
		this.mixPartyNamesId = mixPartyNamesId;
	}


	public Date getMixtSubordinateTime() {
		return mixtSubordinateTime;
	}


	public void setMixtSubordinateTime(Date mixtSubordinateTime) {
		this.mixtSubordinateTime = mixtSubordinateTime;
	}


	public String getMixPartyNames() {
		return mixPartyNames;
	}


	public void setMixPartyNames(String mixPartyNames) {
		this.mixPartyNames = mixPartyNames;
	}


	public Date getMixtTime() {
		return mixtTime;
	}


	public void setMixtTime(Date mixtTime) {
		this.mixtTime = mixtTime;
	}


	public String getMixDetailed() {
		return mixDetailed;
	}


	public void setMixDetailed(String mixDetailed) {
		this.mixDetailed = mixDetailed;
	}


	public Double getMixPrice() {
		return mixPrice;
	}


	public void setMixPrice(Double mixPrice) {
		this.mixPrice = mixPrice;
	}
	
}
