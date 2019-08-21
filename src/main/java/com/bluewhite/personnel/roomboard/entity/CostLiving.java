package com.bluewhite.personnel.roomboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
/**
 * 水，电，房租 费用
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_cost_living")
public class CostLiving extends BaseEntity<Long>{
	
	/**
	 * 缴费开始日期
	 */
	@Column(name = "begin_time")
    private Date beginTime;
	
	/**
	 * 缴费结束日期
	 */
	@Column(name = "end_time")
    private Date endTime;
	
	/**
	 * 公司所在场地id
	 */
	@Column(name = "site_type_id")
	private Long siteTypeId;
	
	/**
	 * 公司所在场地
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData siteType;
	
	/**
	 * 费用类型id
	 */
	@Column(name = "cost_type_id")
	private Long costTypeId;
	
	/**
	 * 费用类型(1.水费 2.电费 3.房租)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cost_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData costType;
	
	/**
	 * 备注
	 */
	@Column(name = "live_remark")
	private String liveRemark;
	
	/**
	 * 平均每天费用
	 */
	@Column(name = "average_cost")
	private Double averageCost;

	/**
	 * 总量
	 */
	@Column(name = "total")
	private Double total;
	
	/**
	 * 总费用
	 */
	@Column(name = "total_cost")
	private Double totalCost;

	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(Long siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public BaseData getSiteType() {
		return siteType;
	}

	public void setSiteType(BaseData siteType) {
		this.siteType = siteType;
	}

	public Long getCostTypeId() {
		return costTypeId;
	}

	public void setCostTypeId(Long costTypeId) {
		this.costTypeId = costTypeId;
	}

	public BaseData getCostType() {
		return costType;
	}

	public void setCostType(BaseData costType) {
		this.costType = costType;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getLiveRemark() {
		return liveRemark;
	}

	public void setLiveRemark(String liveRemark) {
		this.liveRemark = liveRemark;
	}
	
}
