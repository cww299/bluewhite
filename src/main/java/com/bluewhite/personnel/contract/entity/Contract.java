package com.bluewhite.personnel.contract.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.sys.entity.Files;
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
	 * 签订公司
	 */
	@Column(name = "company")
	private String company;
	
	/**
	 * 合同年限
	 */
	@Column(name = "duration")
	private Double duration;
	
	/**
	 * 合同照片
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "contract_id")
	private Set<Files> fileSet = new HashSet<>();
	
	/**
	 * 开始时间
	 */
	@Column(name = "start_time")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;
	
	/**
	 * 付款时间
	 */
	@Column(name = "payment_time")
	private Date paymentTime;
	
	/**
	 * 付款方式
	 */
	@Column(name = "payment_way")
	private String paymentWay;
	
	/**
	 * 合同内容
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 合同金额
	 */
	@Column(name = "amount")
	private String amount;
	
	/**
	 * 是否有效（0=无效，1=有效）
	 */
	@Column(name = "flag")
	private Integer flag;
	
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
	
	/**
	 * 照片ids
	 */
	@Transient
	private String fileIds;
	
	

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public Set<Files> getFileSet() {
		return fileSet;
	}

	public void setFileSet(Set<Files> fileSet) {
		this.fileSet = fileSet;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	
	
	
	
	
	
}
