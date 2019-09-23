package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

/**
 * 订单（下单合同）生产用料
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_material")
public class OrderMaterial extends BaseEntity<Long>{

	/**
	 * 订单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;
	
	/**
	 * 物料名id
	 */
	@Column(name = "materiel_id")
    private Long materielId;
	
	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;
	
	/**
	 * 领取模式(手选裁剪方式id或者压货环节id)
	 */
	@Column(name = "receive_mode_id")
    private Long receiveModeId;
	
	/**
	 * 裁剪方式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receive_mode_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne receiveMode;
	
	/**
	 * 单位id
	 */
	@Column(name = "unit_id")
	private Long unitId;
	
	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne unit;
	
	/**
	 * 领取用量
	 */
	@Column(name = "dosage")
    private Double dosage;
	
	/**
	 * 是否删除
	 */
	@Column(name = "flag")
    private Integer flag;

	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	
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
	
	
	

	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
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


	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Long getMaterielId() {
		return materielId;
	}


	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}


	public Materiel getMateriel() {
		return materiel;
	}


	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}


	public Long getReceiveModeId() {
		return receiveModeId;
	}


	public void setReceiveModeId(Long receiveModeId) {
		this.receiveModeId = receiveModeId;
	}


	public BaseOne getReceiveMode() {
		return receiveMode;
	}


	public void setReceiveMode(BaseOne receiveMode) {
		this.receiveMode = receiveMode;
	}

	public Long getUnitId() {
		return unitId;
	}


	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}


	public BaseOne getUnit() {
		return unit;
	}


	public void setUnit(BaseOne unit) {
		this.unit = unit;
	}


	public Double getDosage() {
		return dosage;
	}


	public void setDosage(Double dosage) {
		this.dosage = dosage;
	}
	
	
}
