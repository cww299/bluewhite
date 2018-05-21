package com.bluewhite.production.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.bluewhite.base.BaseEntity;
import com.bluewhite.production.bacth.entity.Bacth;
/**
 * 产品批次任务
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_task")
public class Task  extends BaseEntity<Long>{
	
	/**
	 * 批次id  
	 */
	@Column(name = "bacth_id")
	private Long bacthId;
	
	/**
	 * 批次   任务多对一批次
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bacth_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Bacth Bacth;
	
    /**
     * 任务数量
     */
	@Column(name = "number")
    private Integer number;
 
    /**
     * 任务状态
     */
	@Column(name = "status")
    private Integer  status;
    
    /**
     * 预计完成时间
     */
	@Column(name = "expect_time")
    private Double expectTime;
    /**
     * 实际任务完成时间
     */
	@Column(name = "task_time")
    private Double taskTime;
	

	public Long getBacthId() {
		return bacthId;
	}
	public void setBacthId(Long bacthId) {
		this.bacthId = bacthId;
	}
	public Bacth getBacth() {
		return Bacth;
	}
	public void setBacth(Bacth bacth) {
		Bacth = bacth;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(Double expectTime) {
		this.expectTime = expectTime;
	}
	public Double getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Double taskTime) {
		this.taskTime = taskTime;
	}
    
    
    
    
    
    


}
