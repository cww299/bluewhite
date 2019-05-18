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
	 * 商品ID（电商线上同步信息字段）
	 * 
	 */
	@Column(name = "product_ID")
	private Long productID;
	
	/**
	 * 商品编号（sku）
	 * 
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 产品图片id
	 */
	@Column(name = "file_id")
	private Long fileId;
	
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
	 * 商品详情描述，可包含图片中心的图片URL
	 * 
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * 商品重量
	 * 
	 */
	@Column(name = "weight")
	private Double weight;
	
	/**
	 * 商品高度
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
	 * 商品分类
	 * 
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 仓库类型（0=主仓库，1=客供仓库，2=次品）
	 * 
	 */
	@Column(name = "warehouse")
	private Integer warehouse;

	
	
	

	public Long getFileId() {
		return fileId;
	}


	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Long getProductID() {
		return productID;
	}


	public void setProductID(Long productID) {
		this.productID = productID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
