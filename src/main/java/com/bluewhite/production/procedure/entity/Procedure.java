package com.bluewhite.production.procedure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 工序实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_procedure",indexes = {	@Index(columnList = "type"),
											@Index(columnList = "product_id"),
											@Index(columnList = "flag")})
public class Procedure extends BaseEntity<Long> {
	
	/**
	 * 工序名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 工序时间
	 */
	@Column(name = "working_time")
	private Double workingTime;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 是否删除
	 */
	@Column(name = "isDel")
	private Integer isDel;
	
	/**
	 * 工序类型id
	 */
	@Column(name = "procedure_type_id")
	private Long procedureTypeId;
	
	/**
	 * 工序
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "procedure_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData procedureType;
	
	
	/**
	 * 针工价格
	 */
	@Column(name = "deedle_price")
	private Double deedlePrice;
	
	/**
	 * 外发价格
	 */
	@Column(name = "hairPrice")
	private Double hairPrice;
	
    /**
     * 部门生产总价
     */
	@Column(name = "department_price")
    private Double departmentPrice;
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 是否是返工标识符（0=不是，1=是）
	 */
	@Column(name = "flag")
	private Integer flag ;
	
	
	/**
	 * 八号仓库特殊业务，同一种产品会有会有激光和冲床两种类型工序，同时会产生不同的外发单价（0=激光，1=冲床）
	 */
	@Column(name = "sign")
	private Integer sign ;
	
	/**
	 * 批次id（用于确定总数量，和任务）
	 */
	@Transient
	private Long bacthId;
	
	/**
	 * 剩余数量
	 */
	@Transient
	private Integer residualNumber;
	
	
	


	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Double getDeedlePrice() {
		return deedlePrice;
	}

	public void setDeedlePrice(Double deedlePrice) {
		this.deedlePrice = deedlePrice;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getBacthId() {
		return bacthId;
	}

	public void setBacthId(Long bacthId) {
		this.bacthId = bacthId;
	}

	public Integer getResidualNumber() {
		return residualNumber;
	}

	public void setResidualNumber(Integer residualNumber) {
		this.residualNumber = residualNumber;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}

	public BaseData getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(BaseData procedureType) {
		this.procedureType = procedureType;
	}
	
	


}
