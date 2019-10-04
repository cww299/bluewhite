package com.bluewhite.personnel.contract.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
/**
 * 合同实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_contract")
public class Contract extends BaseEntity<Long>{
	
	
	/**
	 * 合同种类id
	 */
	@Column(name = "contract_kind_id")
	private Long contractKindId;
	
	/**
	 * 合同种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_kind_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData contractKind;


	/**
	 * 合同类型id
	 */
	@Column(name = "contract_type_id")
	private Long contractTypeId;
	
	/**
	 * 合同类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData contractType;
	
	/**
	 * 合同年限
	 */
	@Column(name = "duration")
	private Double duration;
	
	/**
	 * 合同照片url
	 */
	@Column(name = "picture_url")
	private String pictureUrl;
	
	/**
	 * 开始时间
	 */
	@Column(name = "star_time")
	private Date starTime;
	
	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;
	
	/**
	 * 合同内容
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 保险金额
	 */
	@Column(name = "amount")
	private Double amount;
	
	/**
	 * 是否有效（0=无效，1=有效）
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 查询字段
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	private Date orderTimeEnd;
	
	

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Long getContractKindId() {
		return contractKindId;
	}

	public void setContractKindId(Long contractKindId) {
		this.contractKindId = contractKindId;
	}

	public BaseData getContractKind() {
		return contractKind;
	}

	public void setContractKind(BaseData contractKind) {
		this.contractKind = contractKind;
	}

	public Long getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(Long contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public BaseData getContractType() {
		return contractType;
	}

	public void setContractType(BaseData contractType) {
		this.contractType = contractType;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Date getStarTime() {
		return starTime;
	}

	public void setStarTime(Date starTime) {
		this.starTime = starTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
}
