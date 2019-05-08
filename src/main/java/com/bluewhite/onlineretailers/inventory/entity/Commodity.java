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
	 * 商品编号
	 * 
	 */
	@Column(name = "number")
	private String number;
	
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
	private Double size;
	
	
	/**
	 * 商品材质
	 * 
	 */
	@Column(name = "material")
	private Double material;
	
	/**
	 * 商品填充物
	 * 
	 */
	@Column(name = "fillers")
	private Double fillers;
	
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


	public Double getSize() {
		return size;
	}


	public void setSize(Double size) {
		this.size = size;
	}


	public Double getMaterial() {
		return material;
	}


	public void setMaterial(Double material) {
		this.material = material;
	}


	public Double getFillers() {
		return fillers;
	}


	public void setFillers(Double fillers) {
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
