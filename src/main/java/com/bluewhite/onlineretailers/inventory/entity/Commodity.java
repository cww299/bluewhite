package com.bluewhite.onlineretailers.inventory.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

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
	@Column(name = "sku_code")
	private String skuCode;
	
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
	private Double size;
	
	
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
	 * 商品天猫单价
	 * 
	 */
	@Column(name = "tianmao_price")
	private Double tianmaoPrice;
	
	/**
	 * 1688批发价
	 * 
	 */
	@Column(name = "osee_price")
	private Double oseePrice;
	
	
	/**
	 * 线下批发价
	 * 
	 */
	@Column(name = "offline_price")
	private Double offlinePrice;
	

	
	/**
	 * 库存数量
	 */
	@OneToMany(mappedBy = "commodity" ,cascade = CascadeType.ALL)
	private Set<Inventory> inventorys = new HashSet<Inventory>();

	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	
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


	public Set<Inventory> getInventorys() {
		return inventorys;
	}


	public void setInventorys(Set<Inventory> inventorys) {
		this.inventorys = inventorys;
	}


	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
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

	public String getSkuCode() {
		return skuCode;
	}


	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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

	public Double getSize() {
		return size;
	}


	public void setSize(Double size) {
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


	


	public Double getTianmaoPrice() {
		return tianmaoPrice;
	}


	public void setTianmaoPrice(Double tianmaoPrice) {
		this.tianmaoPrice = tianmaoPrice;
	}

	public Double getOseePrice() {
		return oseePrice;
	}


	public void setOseePrice(Double oseePrice) {
		this.oseePrice = oseePrice;
	}


	public Double getOfflinePrice() {
		return offlinePrice;
	}


	public void setOfflinePrice(Double offlinePrice) {
		this.offlinePrice = offlinePrice;
	}


	
	
	

}
