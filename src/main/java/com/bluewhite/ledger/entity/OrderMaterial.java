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
 * 生产耗料单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_material")
public class OrderMaterial extends BaseEntity<Long> {
	/**
	 * 耗料单编号
	 * 
	 */
	@Column(name = "order_material_number")
	private String orderMaterialNumber;
	
	/**
     * 选择该样品的裁片名字
     */
    @Column(name = "cutparts_name")
    private String cutPartsName;

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
	 * 领取模式id(手选裁剪方式id或者压货环节id) 有复合物的就是物料2次加工采购领取（复合物需要在复合厂进行二次处理，所以是采购领取）
	 */
	@Column(name = "receive_mode_id")
	private Long receiveModeId;

	/**
	 * 领取模式
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
	 * 是否审核（0=未审核，1=已审核）审核成功后
	 * 采购部可以正常查看
	 */
	@Column(name = "audit")
	private Integer audit;
	
	/**
	 * 是否出库（0=否，1=是）
	 * 是否生成出库单
	 */
	@Column(name = "outbound")
	private Integer outbound;
	
	/**
	 * 当前耗料单的出库单是否被审核
	 */
	@Transient
	private Integer outAudit;
	
	/**
	 * 状态（1=库存充足，2无库存，3有库存量不足）
	 */
	@Transient
	private Integer state;
	
	/**
	 * 库存总数
	 */
	@Transient
	private Double inventoryTotal;

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
	
	
	
	 
	public String getCutPartsName() {
        return cutPartsName;
    }

    public void setCutPartsName(String cutPartsName) {
        this.cutPartsName = cutPartsName;
    }

    public Integer getOutAudit() {
		return outAudit;
	}

	public void setOutAudit(Integer outAudit) {
		this.outAudit = outAudit;
	}

	public String getOrderMaterialNumber() {
		return orderMaterialNumber;
	}

	public void setOrderMaterialNumber(String orderMaterialNumber) {
		this.orderMaterialNumber = orderMaterialNumber;
	}

	public Double getInventoryTotal() {
		return inventoryTotal;
	}

	public void setInventoryTotal(Double inventoryTotal) {
		this.inventoryTotal = inventoryTotal;
	}

	public Integer getOutbound() {
		return outbound;
	}

	public void setOutbound(Integer outbound) {
		this.outbound = outbound;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
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
