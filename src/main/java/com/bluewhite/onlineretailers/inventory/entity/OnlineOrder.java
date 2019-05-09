package com.bluewhite.onlineretailers.inventory.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.entity.User;

/**
 * 电商销售单实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_order")
public class OnlineOrder extends BaseEntity<Long> {

	/**
	 * 销售人员id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 销售人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "online_customer_id")
	private Long onlineCustomerId;
	

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OnlineCustomer onlineCustomer;
	
	
	/**
	 * 子订单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "online_order_id")
	private List<OnlineOrderChild> onlineOrderChilds;
	
	
	/**
	 * 客户昵称
	 * 
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 客户真实姓名
	 */
	@Column(name = "buyer_name")
	private String buyerName;
	
	
	/**
	 * 卖家昵称
	 */
	@Column(name = "seller_nick")
	private String sellerNick;
	
	/**
	 * 商品图片绝对途径
	 */
	@Column(name = "pic_path")
	private String picPath;
	
	/**
	 * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name = "payment")
	private String payment;
	
	/**
	 * 卖家是否已评价。可选值:true(已评价),false(未评价)
	 */
	@Column(name = "seller_rate")
	private Boolean sellerRate;
	
	/**
	 * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name = "post_fee")
	private Double postFee;
	
	/**
	 * 卖家发货时间
	 */
	@Column(name = "consign_time")
	private Date consignTime;
	
	/**
	 * 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name = "received_payment")
	private Double receivedPayment;
	
	/**
	 * 交易编号 (父订单的交易编号)
	 * 
	 */
	@Column(name = "tid")
	private Number tid;
	
	/**
	 * 	商品购买数量。取值范围：大于零的整数,对于一个trade对应多个order的时候（一笔主订单，对应多笔子订单），num=0，num是一个跟商品关联的属性，一笔订单对应多比子订单的时候，主订单上的num无意义。
	 */
	@Column(name = "num")
	private Number num;
	
	/**
	 * 付款时间。格式:yyyy-MM-dd HH:mm:ss。订单的付款时间即为物流订单的创建时间
	 */
	@Column(name = "pay_time")
	private Double payTime;
	
	/**
	 * 交易结束时间。交易成功时间(更新交易状态为成功的同时更新)/确认收货时间或者交易关闭时间 。格式:yyyy-MM-dd HH:mm:ss
	 */
	@Column(name = "end_time")
	private Double endTime;

	/**
	 * 订单状态交易状态。可选值: 
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) 
	 * WAIT_BUYER_PAY(等待买家付款) 
	 * SELLER_CONSIGNED_PART(卖家部分发货) 
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) 
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
	 * TRADE_FINISHED(交易成功) *
	 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) *
	 * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易) 
	 * PAY_PENDING(国际信用卡支付付款确认中) *
	 * WAIT_PRE_AUTH_CONFIRM(0元购合约中) *
	 * PAID_FORBID_CONSIGN(拼团中订单或者发货强管控的订单，已付款但禁止发货)
	 * 
	 */
	@Column(name = "status")
	private String status;
	
	/**
	 * 单据号
	 */
	@Column(name = "document_number")
	private String documentNumber;
	
	
	/**
	 * 整单优惠
	 */
	@Column(name = "all_bill_preferential")
	private Double allBillPreferential;


	/**
	 * 运单号
	 */
	@Column(name = "tracking_number")
	private String trackingNumber;

	/**
	 * 买家留言
	 * 
	 */
	@Column(name = "buyer_message")
	private String buyerMessage;
	
	/**
	 * 买家备注（与淘宝网上订单的买家备注对应，只有买家才能查看该字段）
	 * 
	 */
	@Column(name = "buyer_memo")
	private String buyerMemo;

	
	/**
	 * 买家备注旗帜（与淘宝网上订单的买家备注旗帜对应，只有买家才能查看该字段）红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
	 * 
	 */
	@Column(name = "buyer_flag")
	private Number buyerFlag;

	/**
	 * 卖家备注（与淘宝网上订单的卖家备注对应，只有卖家才能查看该字段）
	 * 
	 */
	@Column(name = "seller_memo")
	private String sellerMemo;
	
	
	/**
	 * 卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段）红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
	 * 
	 */
	@Column(name = "seller_flag")
	private Number sellerFlag;
	
	/**
	 * 买家是否已评价。可选值:true(已评价),false(未评价)。如买家只评价未打分，此字段仍返回false
	 * 
	 */
	@Column(name = "buyer_rate")
	private Number buyerRate;
	

	/**
	 * 发货仓库类型（0=主仓库，1=客供仓库，2=次品）
	 * 
	 */
	@Column(name = "warehouse")
	private Integer warehouse;

	/**
	 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。可
	 * 选值：free(卖家包邮),post(平邮),express(快递),ems(EMS),virtual(虚拟发货)，25(次日必达)，26(预约配送)
	 * 
	 * @return
	 */
	@Column(name = "shipping_type")
	private String shippingType;
	
	
	/**
	 * 收货人的所在省份
	 * 
	 */
	@Column(name = "provinces_id")
	private Long provincesId;
	
	/**
	 * 省份
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinces_id", referencedColumnName = "id", insertable = false, updatable = false)
	private RegionAddress provinces;
	
	
	
	/**
	 * 收货人的所在市
	 * 
	 */
	@Column(name = "city_id")
	private Long cityId;
	/**
	 * 市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", referencedColumnName = "id", insertable = false, updatable = false)
	private RegionAddress city;
	
	
	/**
	 * 收货人的所在县
	 * 
	 */
	@Column(name = "county_id")
	private Long countyId;
	
	/**
	 * 市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id", referencedColumnName = "id", insertable = false, updatable = false)
	private RegionAddress county;
	
	
	/**
	 * 收货人的详细地址
	 * 
	 */
	@Column(name = "address")
	private String address;
	
	/**
	 * 买家手机号
	 * 
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 邮编
	 */
	@Column(name = "zip_code")
	private String zipCode;

	
	/**
	 * 新增子订单json数据
	 */
	@Transient
	private String childOrder;

	
	
	

	public String getChildOrder() {
		return childOrder;
	}

	public void setChildOrder(String childOrder) {
		this.childOrder = childOrder;
	}

	public List<OnlineOrderChild> getOnlineOrderChilds() {
		return onlineOrderChilds;
	}

	public void setOnlineOrderChilds(List<OnlineOrderChild> onlineOrderChilds) {
		this.onlineOrderChilds = onlineOrderChilds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Long getProvincesId() {
		return provincesId;
	}

	public void setProvincesId(Long provincesId) {
		this.provincesId = provincesId;
	}

	public RegionAddress getProvinces() {
		return provinces;
	}

	public void setProvinces(RegionAddress provinces) {
		this.provinces = provinces;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public RegionAddress getCity() {
		return city;
	}

	public void setCity(RegionAddress city) {
		this.city = city;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public RegionAddress getCounty() {
		return county;
	}

	public void setCounty(RegionAddress county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Boolean getSellerRate() {
		return sellerRate;
	}

	public void setSellerRate(Boolean sellerRate) {
		this.sellerRate = sellerRate;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public Date getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	public Double getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(Double receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public Number getTid() {
		return tid;
	}

	public void setTid(Number tid) {
		this.tid = tid;
	}

	public Number getNum() {
		return num;
	}

	public void setNum(Number num) {
		this.num = num;
	}

	public Double getPayTime() {
		return payTime;
	}

	public void setPayTime(Double payTime) {
		this.payTime = payTime;
	}

	public Double getEndTime() {
		return endTime;
	}

	public void setEndTime(Double endTime) {
		this.endTime = endTime;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public Number getBuyerFlag() {
		return buyerFlag;
	}

	public void setBuyerFlag(Number buyerFlag) {
		this.buyerFlag = buyerFlag;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public Number getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(Number sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	public Number getBuyerRate() {
		return buyerRate;
	}

	public void setBuyerRate(Number buyerRate) {
		this.buyerRate = buyerRate;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public Integer getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Integer warehouse) {
		this.warehouse = warehouse;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOnlineCustomerId() {
		return onlineCustomerId;
	}

	public void setOnlineCustomerId(Long onlineCustomerId) {
		this.onlineCustomerId = onlineCustomerId;
	}

	public OnlineCustomer getOnlineCustomer() {
		return onlineCustomer;
	}

	public void setOnlineCustomer(OnlineCustomer onlineCustomer) {
		this.onlineCustomer = onlineCustomer;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Double getAllBillPreferential() {
		return allBillPreferential;
	}

	public void setAllBillPreferential(Double allBillPreferential) {
		this.allBillPreferential = allBillPreferential;
	}


}
