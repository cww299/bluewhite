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
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

/**
 * 采购（采购面辅料订单）(由生产下单用料转化得到) 当生产耗料库存不足时，生成采购单
 * 
 * 采购单作为库存记录单使用，所以一个物料的库存同时拥有多个采购单，采购单内容包括物料的库位，价格
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_procurement")
public class OrderProcurement extends BaseEntity<Long> {

	/**
	 * 采购单编号(批次+产品名称+物料名称+订货客户名称生成的新编号)
	 */
	@Column(name = "order_procurement_number")
	private String orderProcurementNumber;
	
	/**
	 * 订单（下单合同）生产用料id
	 */
	@Column(name = "order_material_id")
	private Long orderMaterialId;

	/**
	 * 订单（下单合同）生产用料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_material_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderMaterial orderMaterial;
	
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
	 * 平方克重
	 */
	@Column(name = "square_gram")
    private Double squareGram;
	
	/**
	 * 下单数量
	 */
	@Column(name = "place_order_number")
	private Double placeOrderNumber;

	/**
	 * 下单日期
	 */
	@Column(name = "place_order_time")
	private Date placeOrderTime;
	
	/**
	 * 预计到货日期
	 */
	@Column(name = "expect_arrival_time")
	private Date expectArrivalTime;  

	/**
	 * 到货日期
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;  
	
	/**
	 * 到货数量
	 */
	@Column(name = "arrival_number")
	private Double arrivalNumber;

	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * 订料人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 订料人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 库位
	 * 
	 */
	@Column(name = "materiel_location")
	private String materielLocation;
	
	/**
     * 面料价格
     */
	@Column(name = "price")
    private Double price;
	
	/**
	 * 根据客户来的新编号
	 */
	@Column(name = "new_code")
	private String newCode;
	
	/**
	 * 是否耗尽（当批次的采购单全部使用完，将字段标记）0=否，1=是
	 */
	@Column(name = "use_up")
	private Integer useUp;
	

	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	
	/**
	 * 订单id
	 * 
	 */
	@Transient
	private Long orderId;
	
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
	
	

	
	

	public Integer getUseUp() {
		return useUp;
	}

	public void setUseUp(Integer useUp) {
		this.useUp = useUp;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public Double getSquareGram() {
		return squareGram;
	}

	public void setSquareGram(Double squareGram) {
		this.squareGram = squareGram;
	}

	public Date getExpectArrivalTime() {
		return expectArrivalTime;
	}

	public void setExpectArrivalTime(Date expectArrivalTime) {
		this.expectArrivalTime = expectArrivalTime;
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

	public String getMaterielLocation() {
		return materielLocation;
	}

	public void setMaterielLocation(String materielLocation) {
		this.materielLocation = materielLocation;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPlaceOrderNumber() {
		return placeOrderNumber;
	}

	public Double getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Double arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public void setPlaceOrderNumber(Double placeOrderNumber) {
		this.placeOrderNumber = placeOrderNumber;
	}

	public String getOrderProcurementNumber() {
		return orderProcurementNumber;
	}

	public void setOrderProcurementNumber(String orderProcurementNumber) {
		this.orderProcurementNumber = orderProcurementNumber;
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

	public Long getOrderMaterialId() {
		return orderMaterialId;
	}

	public void setOrderMaterialId(Long orderMaterialId) {
		this.orderMaterialId = orderMaterialId;
	}

	public OrderMaterial getOrderMaterial() {
		return orderMaterial;
	}

	public void setOrderMaterial(OrderMaterial orderMaterial) {
		this.orderMaterial = orderMaterial;
	}

	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}

	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
