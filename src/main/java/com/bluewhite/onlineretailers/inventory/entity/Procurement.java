package com.bluewhite.onlineretailers.inventory.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 电商采购单（对于商品的库存管理）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_procurement")
public class Procurement extends BaseEntity<Long>{
	
	/**
	 * 批次号
	 */
	@Column(name = "batch_number")
	private String batchNumber;

	/**
	 * 经手人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 经手人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 子订单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "procurement_id")
	private List<ProcurementChild> procurementChilds = new ArrayList<>();
	
	
	/**
	 * 总数量
	 * 
	 */
	@Column(name = "number")
	private Integer number;
	
	
	/**
	 *  单据类型(0=生产单，1=针工单,2=入库单，3=出库单)
	 */
	@Column(name = "type")
	private Integer type;
	
	
	/**
	 * 备注
	 * 
	 */
	@Column(name = "remark")
	private String remark;
	
	
	/**
	 * 是否反冲（0=否，1=是）
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * json 储存商品id和数量
	 * 
	 */
	@Transient
	private String commodityNumber;

	

	
	
	public List<ProcurementChild> getProcurementChilds() {
		return procurementChilds;
	}

	public void setProcurementChilds(List<ProcurementChild> procurementChilds) {
		this.procurementChilds = procurementChilds;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCommodityNumber() {
		return commodityNumber;
	}

	public void setCommodityNumber(String commodityNumber) {
		this.commodityNumber = commodityNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
