package com.bluewhite.ledger.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

/**
 * 贴包单(发货单)
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing")
public class Packing extends BaseEntity<Long> {
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customr_id")
	private Long customrId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customr_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customr customr;

	/**
	 * 编号 (19N7Y20R01)
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 贴包子单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "packingChild_id")
	private List<PackingChild> packingChilds = new ArrayList<>();
	
	/**
	 * 包装物
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "packingMaterials_id")
	private List<PackingMaterials> packingMaterials = new ArrayList<>();
	
	/**
	 * 贴包时间
	 */
	@Column(name = "packing_date")
	private Date packingDate;
	
	
	/**
	 * 发货时间
	 */
	@Column(name = "send_date")
	private Date sendDate;
	
	/**
	 * 是否发货
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 批次号
	 */
	@Transient
	private String bacthNumber;

	/**
	 * 产品id
	 */
	@Transient
	private Long productId;

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
	 * 新增子单json数据
	 */
	@Transient
	private String childPacking;
	
	
	

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getChildPacking() {
		return childPacking;
	}

	public void setChildPacking(String childPacking) {
		this.childPacking = childPacking;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<PackingChild> getPackingChilds() {
		return packingChilds;
	}

	public void setPackingChilds(List<PackingChild> packingChilds) {
		this.packingChilds = packingChilds;
	}

	public Date getPackingDate() {
		return packingDate;
	}

	public void setPackingDate(Date packingDate) {
		this.packingDate = packingDate;
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

	public List<PackingMaterials> getPackingMaterials() {
		return packingMaterials;
	}

	public void setPackingMaterials(List<PackingMaterials> packingMaterials) {
		this.packingMaterials = packingMaterials;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getCustomrId() {
		return customrId;
	}

	public void setCustomrId(Long customrId) {
		this.customrId = customrId;
	}

	public Customr getCustomr() {
		return customr;
	}

	public void setCustomr(Customr customr) {
		this.customr = customr;
	}

}
