package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 电商在售商品
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_commodity")
public class Commodity extends BaseEntity<Long>{
	
	/**
	 * 商品编号（sku）
	 * 
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 商品类型，在线批发商品(wholesale)
	 * 		 或者询盘商品(sourcing)，1688网站缺省为wholesale
	 * 
	 */
	@Column(name = "type")
	private String type;
	/**
     * 产品图片地址
     */
	@Column(name = "pic_url")
    private String picUrl;
	
	/**
	 * 商品名称
	 * 
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 商品重量
	 * 
	 */
	@Column(name = "weight")
	private Double weight;
	
	/**
	 * 商品销售属性
	 * 
	 */
	@Column(name = "size")
	private String size;
	
	
	/**
	 * 商品材质
	 * 
	 */
	@Column(name = "material")
	private String  material;
	
	/**
	 * 商品填充物
	 * 
	 */
	@Column(name = "fillers")
	private String fillers;
	
	/**
	 * 商品成本
	 * 
	 */
	@Column(name = "cost")
	private Double cost;
	
	/**
	 * 商品广宣成本
	 * 
	 */
	@Column(name = "propaganda_cost")
	private Double propagandaCost;
	
	/**
	 * 商品备注
	 * 
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 商品单价
	 * 
	 */
	@Column(name = "price")
	private Double price;
	
	
	/**
	 * 库存数量
	 * 
	 */
	@Column(name = "quantity")
	private Integer quantity;
	
	
	/**
	 * 仓库类型（0=主仓库，1=客供仓库，2=次品）
	 * 
	 */
	@Column(name = "warehouse")
	private Integer warehouse;

	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getWeight() {
		return weight;
	}


	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


	public String getFillers() {
		return fillers;
	}


	public void setFillers(String fillers) {
		this.fillers = fillers;
	}


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public Double getPropagandaCost() {
		return propagandaCost;
	}


	public void setPropagandaCost(Double propagandaCost) {
		this.propagandaCost = propagandaCost;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Integer getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Integer warehouse) {
		this.warehouse = warehouse;
	}
	
	
	

}
