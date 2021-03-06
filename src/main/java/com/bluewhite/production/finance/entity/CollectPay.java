package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;
/**
 * 绩效汇总类 绩效流水表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_collect_pay",indexes = {	@Index(name="collect_pay_index_1",columnList = "type"),
											@Index(name="collect_pay_index_1",columnList = "allot_time"),
											@Index(columnList = "user_id")})
public class CollectPay extends BaseEntity<Long> {
	
	/**
	 * 员工姓名
	 */
	@Column(name = "user_name")
	@Poi(name = "员工姓名", column = "A"  ,department="machinist")
    private String userName;
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
    private Long userId;
	
	/**
	 * 分组id
	 */
	@Column(name = "group_id")
    private Long groupId;
    
	/**
	 * 考勤时间
	 */
	@Column(name = "time")
	@Poi(name = "考勤时间", column = "B"  ,department="machinist")
	private Double time;
	
	/**
	 * 加班时间(验货和打棉组固有)
	 */
	@Column(name = "over_time")
	private Double overtime;
	
	
	/**
	 * 缺勤时间
	 */
	@Column(name = "duty_time")
	private Double  dutyTime;
    
	/**
	 * A工资数额
	 */
	@Column(name = "payA")
	@Poi(name = "A工资数额", column = "C" , department="machinist")
	private Double payA;
	
	/**
	 * b工资+杂工工资
	 */
	@Column(name = "payB")
	@Poi(name = "B工资数额", column = "D", department="machinist")
	private Double payB;
	
	/**
	 * 上浮后的B工资
	 */
	@Column(name = "add_payB")
	private Double addPayB;
	
	/**
	 * 考虑个人调节上浮后的B
	 */
	@Column(name = "add_self_payB")
	private Double addSelfPayB;
	
	/**
	 * 个人调节发放比例
	 */
	@Column(name = "add_self_number")
	private Double addSelfNumber;
	
	/**
	 * 整体上浮下调比例
	 */
	@Transient
	private Double addNumber;
	
	/**
	 * 上浮后的加绩工资
	 */
	@Column(name = "add_performance_pay")
	private Double addPerformancePay;
	
	/**
	 * 手动上浮后的加绩工资
	 */
	@Column(name = "hard_add_performance_pay")
	private Double hardAddPerformancePay;
	
	/**
	 * 上浮后无加绩固定给予工资
	 */
	@Column(name = "no_performance_number")
	private Double noPerformanceNumber;
	
	/**
	 * 无加绩的配合奖励
	 */
	@Transient
	private Double noPerformancePay;
	
	/**
	 * 无绩效小时工资
	 */
	@Column(name = "no_time_pay")
	private Double noTimePay;
	
	/**
	 * 有绩效小时工资
	 */
	@Column(name = "time_pay")
	private Double timePay;
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 加绩流水生成时间
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	
	/**
	 * 小时单价
	 */
	@Column(name = "time_price")
	private Double timePrice;
	
	/**
	 * B工资比A工资
	 */
	@Column(name = "ratio")
	@Poi(name = "比值", column = "E", department="machinist")
	private Double ratio;
	
	/**
	 * 查询字段开始时间
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	@Transient
	private Date orderTimeEnd;
	
	/**
	 * 显示当日详细
	 */
	@Transient
	private Integer detail;
	
	/**
	 * 日期
	 */
	private String startDate;
	
	
	

	public Double getHardAddPerformancePay() {
		return hardAddPerformancePay;
	}
	public void setHardAddPerformancePay(Double hardAddPerformancePay) {
		this.hardAddPerformancePay = hardAddPerformancePay;
	}
	public Integer getDetail() {
		return detail;
	}
	public void setDetail(Integer detail) {
		this.detail = detail;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	public Double getTimePrice() {
		return timePrice;
	}
	public void setTimePrice(Double timePrice) {
		this.timePrice = timePrice;
	}
	public Double getOvertime() {
		return overtime;
	}
	public void setOvertime(Double overtime) {
		this.overtime = overtime;
	}
	public Double getDutyTime() {
		return dutyTime;
	}
	public void setDutyTime(Double dutyTime) {
		this.dutyTime = dutyTime;
	}
	public Date getAllotTime() {
		return allotTime;
	}
	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getPayA() {
		return payA;
	}
	public void setPayA(Double payA) {
		this.payA = payA;
	}
	public Double getPayB() {
		return payB;
	}
	public void setPayB(Double payB) {
		this.payB = payB;
	}
	public Double getAddPayB() {
		return addPayB;
	}
	public void setAddPayB(Double addPayB) {
		this.addPayB = addPayB;
	}
	public Double getAddSelfPayB() {
		return addSelfPayB;
	}
	public void setAddSelfPayB(Double addSelfPayB) {
		this.addSelfPayB = addSelfPayB;
	}
	public Double getAddSelfNumber() {
		return addSelfNumber;
	}
	public void setAddSelfNumber(Double addSelfNumber) {
		this.addSelfNumber = addSelfNumber;
	}
	public Double getAddNumber() {
		return addNumber;
	}
	public void setAddNumber(Double addNumber) {
		this.addNumber = addNumber;
	}
	public Double getAddPerformancePay() {
		return addPerformancePay;
	}
	public void setAddPerformancePay(Double addPerformancePay) {
		this.addPerformancePay = addPerformancePay;
	}
	public Double getNoPerformanceNumber() {
		return noPerformanceNumber;
	}
	public void setNoPerformanceNumber(Double noPerformanceNumber) {
		this.noPerformanceNumber = noPerformanceNumber;
	}
	public Double getNoPerformancePay() {
		return noPerformancePay;
	}
	public void setNoPerformancePay(Double noPerformancePay) {
		this.noPerformancePay = noPerformancePay;
	}
	public Double getNoTimePay() {
		return noTimePay;
	}
	public void setNoTimePay(Double noTimePay) {
		this.noTimePay = noTimePay;
	}
	public Double getTimePay() {
		return timePay;
	}
	public void setTimePay(Double timePay) {
		this.timePay = timePay;
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
	

}
