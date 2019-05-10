package com.bluewhite.onlineretailers.inventory.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 电商采购单实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_procurement")
public class Procurement extends BaseEntity<Long>{

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
	 * 商品集合 （一个采购单可以有多个商品）
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "online_procurement_commodity", joinColumns = @JoinColumn(name = "online_procurement_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "commodity_id", referencedColumnName = "id"))
	private Set<Commodity> commoditys = new HashSet<Commodity>();
	
	/**
	 * json 储存商品id和数量
	 * 
	 */
	@Column(name = "commodity_number")
	private String commodityNumber;
	
	
	/**
	 * 总数量
	 * 
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 库存状态有两种，第一种是入库单的订单状态，第二种是出库单的订单状态
	 * （ ）
	 * （ ）
	 */
	@Column(name = "status")
	private Integer status;
	
	
	/**
	 *  库存类型(0=入库单，1=出库单,2=生产单，3=针工单)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 发货仓库类型（0=主仓库，1=客供仓库，2=次品）
	 * 
	 */
	@Column(name = "warehouse")
	private Integer warehouse;
	
	/**
	 * 备注
	 * 
	 */
	@Column(name = "remark")
	private String remark;
	
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Set<Commodity> getCommoditys() {
		return commoditys;
	}

	public void setCommoditys(Set<Commodity> commoditys) {
		this.commoditys = commoditys;
	}

	public Integer getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Integer warehouse) {
		this.warehouse = warehouse;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
