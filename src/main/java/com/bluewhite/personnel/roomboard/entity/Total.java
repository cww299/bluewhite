package com.bluewhite.personnel.roomboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 水，电，房租 费用
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_total")
public class Total extends BaseEntity<Long>{
	
	
	/**
	 * 月份
	 */
	@Column(name = "monthDate")
    private Date monthDate;
	
	
	/**
	 * 备注
	 */
	@Column(name = "live_remark")
	private String liveRemark;
	
	
	/**
	 * (1.宿舍 2.蓝白 3.八号)
	 */
	@Column(name = "state")
	private Integer state;
	
	/**
	 * 费用类型(1.水费 2.电费 3.房租)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 二楼上月抄表数
	 */
	@Column(name = "one_now_num")
	private Double oneNowNum;
	
	/**
	 * 二楼当月抄表度数
	 */
	@Column(name = "one_upper_num")
	private Double oneUpperNum;
	
	/**
	 * 三楼上月抄表数
	 */
	@Column(name = "two_now_num")
	private Double twoNowNum;
	
	/**
	 * 三楼当月抄表度数
	 */
	@Column(name = "two_upper_num")
	private Double twoUpperNum;
	
	/**
	 * 四楼上月抄表数
	 */
	@Column(name = "three_now_num")
	private Double threeNowNum;
	
	/**
	 * 四楼当月抄表度数
	 */
	@Column(name = "three_upper_num")
	private Double threeUpperNum;
	
	
	/**
	 * 损耗（消防水量）
	 */
	@Column(name = "loss")
	private Double loss;
	
	/**
	 * （按面积比例核算）（耗损水量）
	 */
	@Column(name = "buse")
	private Double buse;
	
	/**
	 * 铜损耗
	 */
	@Column(name = "copper")
	private Double copper;
	
	/**
	 * 个体之间的损耗
	 */
	@Column(name = "individual")
	private Double individual;
	
	/**
	 * 总量
	 */
	@Column(name = "summary")
	private Double summary;
	
	/**
	 * 总费用
	 */
	@Column(name = "summary_price")
	private Double summaryPrice;

	public Date getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(Date monthDate) {
		this.monthDate = monthDate;
	}

	public String getLiveRemark() {
		return liveRemark;
	}

	public void setLiveRemark(String liveRemark) {
		this.liveRemark = liveRemark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getOneNowNum() {
		return oneNowNum;
	}

	public void setOneNowNum(Double oneNowNum) {
		this.oneNowNum = oneNowNum;
	}

	public Double getOneUpperNum() {
		return oneUpperNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setOneUpperNum(Double oneUpperNum) {
		this.oneUpperNum = oneUpperNum;
	}

	public Double getTwoNowNum() {
		return twoNowNum;
	}

	public void setTwoNowNum(Double twoNowNum) {
		this.twoNowNum = twoNowNum;
	}

	public Double getTwoUpperNum() {
		return twoUpperNum;
	}

	public void setTwoUpperNum(Double twoUpperNum) {
		this.twoUpperNum = twoUpperNum;
	}

	public Double getThreeNowNum() {
		return threeNowNum;
	}

	public void setThreeNowNum(Double threeNowNum) {
		this.threeNowNum = threeNowNum;
	}

	public Double getThreeUpperNum() {
		return threeUpperNum;
	}

	public void setThreeUpperNum(Double threeUpperNum) {
		this.threeUpperNum = threeUpperNum;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public Double getBuse() {
		return buse;
	}

	public void setBuse(Double buse) {
		this.buse = buse;
	}

	public Double getCopper() {
		return copper;
	}

	public void setCopper(Double copper) {
		this.copper = copper;
	}

	public Double getIndividual() {
		return individual;
	}

	public void setIndividual(Double individual) {
		this.individual = individual;
	}

	public Double getSummary() {
		return summary;
	}

	public void setSummary(Double summary) {
		this.summary = summary;
	}

	public Double getSummaryPrice() {
		return summaryPrice;
	}

	public void setSummaryPrice(Double summaryPrice) {
		this.summaryPrice = summaryPrice;
	}
	
	
}
