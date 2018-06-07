package com.bluewhite.production.bacth.entity;

import java.util.Date;
import java.util.HashSet;
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
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.task.entity.Task;

/**
 * 产品批次实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_bacth")
public class Bacth extends BaseEntity<Long>{
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Product product;
	/**
	 * 批次号
	 */
	@Column(name = "bacth_number")
    private String bacthNumber;
    /**
     * 该批次产品数量
     */
	@Column(name = "number")
    private Integer number;
    /**
     * 备注
     */
	@Column(name = "remarks")
    private String remarks;
    /**
     * 状态，是否完成（0=未完成，1=完成，完成时，会转入包装列表中）
     */
	@Column(name = "status")
    private Integer status = 0;
	
	/**
	 * 任务
	 */
	@OneToMany(mappedBy = "bacth",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Task> tasks = new HashSet<Task>();
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
    /**
     * 批次外发价格
     */
	@Column(name = "bacth_hair_price")
    private Double bacthHairPrice;
	
    /**
     * 批次部门预计生产价格
     */
	@Column(name = "bacth_department_price")
    private Double bacthDepartmentPrice;
	
	
	/**
	 * 地区差价
	 */
	@Column(name = "regional_price")
    private Double regionalPrice;
	
	/**
	 * 总返工任务价值(实际返工成本费用总和)
	 */
	@Column(name = "sum_rework_price")
	private Double sumReworkPrice;
	
	
	
	/**
	 * 总任务价值(实际成本费用总和)
	 */
	@Column(name = "sum_task_price")
	private Double sumTaskPrice;
	
	/**
	 * 批次分配时间（默认当前时间前一天）
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	
	/**
	 * 产品名称
	 */
	@Transient
	private String name;
	
	/**
	 * 产品编号
	 */
	@Transient
	private String productNumber;
	

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
	 * 产值
	 */
	@Transient
	private Double productPrice;
	
    /**
     * 不同部门外发价格
     */
	@Transient
    private Double hairPrice;
	

	public Double getProductPrice() {
		return (number==null ? 0.0 : number) * (hairPrice==null ? 0.0 : hairPrice);
	}

	
	
	public Double getHairPrice() {
		return hairPrice;
	}

	public void setHairPrice(Double hairPrice) {
		this.hairPrice = hairPrice;
	}

	public Double getSumReworkPrice() {
		return sumReworkPrice;
	}
	public void setSumReworkPrice(Double sumReworkPrice) {
		this.sumReworkPrice = sumReworkPrice;
	}
	public Date getAllotTime() {
		return allotTime;
	}
	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}
	public Double getSumTaskPrice() {
		return sumTaskPrice;
	}
	public void setSumTaskPrice(Double sumTaskPrice) {
		this.sumTaskPrice = sumTaskPrice;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getBacthNumber() {
		return bacthNumber;
	}
	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getBacthHairPrice() {
		return bacthHairPrice;
	}
	public void setBacthHairPrice(Double bacthHairPrice) {
		this.bacthHairPrice = bacthHairPrice;
	}
	public Double getBacthDepartmentPrice() {
		return bacthDepartmentPrice;
	}
	public void setBacthDepartmentPrice(Double bacthDepartmentPrice) {
		this.bacthDepartmentPrice = bacthDepartmentPrice;
	}
	public Double getRegionalPrice() {
		return regionalPrice;
	}
	public void setRegionalPrice(Double regionalPrice) {
		this.regionalPrice = regionalPrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
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
    
	
	
	
	

	
	
}
