package com.bluewhite.product.product.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.sys.entity.Files;

/**
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_product")
public class Product extends BaseEntity<Long>{
	
	//产品编号
	@Column(name = "number")
    private String number;
	
    //产品名
	@Column(name = "name")
    private String name;
	
	
	
    //产品图片地址
	@Column(name = "url")
    private String url;
	
	/**
	 * 产品图片
	 */
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Files> productFile = new HashSet<Files>();
	
	//该单品目前总销量(产值）
//	@Column(name = "total_sales")
//    private Integer totalSales;
	
	//产值可回收的研发成本
//	@Column(name = "development_costs")
//    private Double developmentCosts;
	
    //开发姿态
	@Column(name = "posture")
    private String posture;
	
    //开发尺寸
	@Column(name = "dimension")
    private String dimension;
	
    //产品颜色
	@Column(name = "color")
    private String color;
	
    //定价日期
	@Column(name = "pricing_date")
    private String pricingDate;
	
    //成本价
	@Column(name = "cost_price")
    private String costPrice;
	
    //研发费用
	@Column(name = "cost_research")
    private String costResearch;
	
    //出厂价
	@Column(name = "factory")
    private String factory;
	
    //图片
	@Column(name = "picture")
    private String picture;
	
    //视频
	@Column(name = "video")
    private String video;
	
    //研发个数
	@Column(name = "dev_quantity")
    private String devQuantity;
	
    //研发开始时间
	@Column(name = "dev_start")
    private String devStart;
	
    //研发结束时间
	@Column(name = "dev_end")
    private String devEnd;
	
    //研发来源
	@Column(name = "source")
    private String source;
    //研发性质
	@Column(name = "nature")
    private String nature;
	
    //设计产品责任人
	@Column(name = "respon_id")
    private String responId;
	
    //设计产品责任人姓名
	@Column(name = "respon_name")
    private String responName;
	
    //责任客户
	@Column(name = "customer")
    private String customer;
	
    //责任业务销售员
	@Column(name = "salesman_id")
    private String salesmanId;
	
    //平面制图提成人
	@Column(name = "planner_id")
    private String plannerId;
	
    //平面督导提成人
	@Column(name = "supervisor_id")
    private String supervisorId;
	
    //三维提成人
	@Column(name = "threed_id")
    private String threedId;
	
    //手工开版提成人
	@Column(name = "manual_open_id")
    private String manualOpenId;
	
    //成本价格核算人
	@Column(name = "cost_pricing_id")
    private String costPricingId;
	
    //成本督导
	@Column(name = "super_cost_id")
    private String superCostId;
	
    //面料价格
	@Column(name = "fab_price")
    private Double fabPrice;
	
    //除面料之外的价格
	@Column(name = "all_price")
    private Double allPrice;
	
    //裁剪价格
	@Column(name = "cut_price")
    private Double cutPrice;
	
    //默认生产数量
	@Column(name = "default_number")
    private Integer defaultNumber;
	
    //默认耗损值
	@Column(name = "default_loss")
    private Double defaultLoss;
	
    //给二级客户的价格
	@Column(name = "bt_price")
    private String btPrice;
	
	
	
	
    //产品本身外发价格
	@Transient
    private Double hairPrice;
	
    //当部门预计生产价格
	@Transient
    private Double departmentPrice;
	
	//工序部门类型
	@Transient
	private Integer type;	
	
	/**
	 * 针工价格
	 */
	@Transient
	private Double deedlePrice;
	
	
	
	public Double getDeedlePrice() {
		return deedlePrice;
	}

	public void setDeedlePrice(Double deedlePrice) {
		this.deedlePrice = deedlePrice;
	}

	public Double getHairPrice() {
		return hairPrice;
	}

	public void setHairPrice(Double hairPrice) {
		this.hairPrice = hairPrice;
	}

	public Double getDepartmentPrice() {
		return departmentPrice;
	}

	public void setDepartmentPrice(Double departmentPrice) {
		this.departmentPrice = departmentPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
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

	public String getPosture() {
		return posture;
	}

	public void setPosture(String posture) {
		this.posture = posture;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(String pricingDate) {
		this.pricingDate = pricingDate;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getCostResearch() {
		return costResearch;
	}

	public void setCostResearch(String costResearch) {
		this.costResearch = costResearch;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDevQuantity() {
		return devQuantity;
	}

	public void setDevQuantity(String devQuantity) {
		this.devQuantity = devQuantity;
	}

	public String getDevStart() {
		return devStart;
	}

	public void setDevStart(String devStart) {
		this.devStart = devStart;
	}

	public String getDevEnd() {
		return devEnd;
	}

	public void setDevEnd(String devEnd) {
		this.devEnd = devEnd;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getResponId() {
		return responId;
	}

	public void setResponId(String responId) {
		this.responId = responId;
	}

	public String getResponName() {
		return responName;
	}

	public void setResponName(String responName) {
		this.responName = responName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getPlannerId() {
		return plannerId;
	}

	public void setPlannerId(String plannerId) {
		this.plannerId = plannerId;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getThreedId() {
		return threedId;
	}

	public void setThreedId(String threedId) {
		this.threedId = threedId;
	}

	public String getManualOpenId() {
		return manualOpenId;
	}

	public void setManualOpenId(String manualOpenId) {
		this.manualOpenId = manualOpenId;
	}

	public String getCostPricingId() {
		return costPricingId;
	}

	public void setCostPricingId(String costPricingId) {
		this.costPricingId = costPricingId;
	}

	public String getSuperCostId() {
		return superCostId;
	}

	public void setSuperCostId(String superCostId) {
		this.superCostId = superCostId;
	}

	public Double getFabPrice() {
		return fabPrice;
	}

	public void setFabPrice(Double fabPrice) {
		this.fabPrice = fabPrice;
	}

	public Double getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}

	public Double getCutPrice() {
		return cutPrice;
	}

	public void setCutPrice(Double cutPrice) {
		this.cutPrice = cutPrice;
	}

	public Integer getDefaultNumber() {
		return defaultNumber;
	}

	public void setDefaultNumber(Integer defaultNumber) {
		this.defaultNumber = defaultNumber;
	}

	public Double getDefaultLoss() {
		return defaultLoss;
	}

	public void setDefaultLoss(Double defaultLoss) {
		this.defaultLoss = defaultLoss;
	}

	public String getBtPrice() {
		return btPrice;
	}

	public void setBtPrice(String btPrice) {
		this.btPrice = btPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Files> getProductFile() {
		return productFile;
	}

	public void setProductFile(Set<Files> productFile) {
		this.productFile = productFile;
	}

	
	
	

}
