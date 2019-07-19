package com.bluewhite.ledger.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 财务 除货款以外的杂支
 * @author qiyong
 *
 */
@Entity
@Table(name = "fin_ledger_mixed")
public class Mixed extends BaseEntity<Long> {

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
	 * 乙方id
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
