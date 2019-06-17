package com.bluewhite.onlineretailers.inventory.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.bluewhite.system.sys.entity.RegionAddress;
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
	 * 订单编号
	 */
	@Column(name = "document_number")
	private String documentNumber;
	
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
	private List<OnlineOrderChild> onlineOrderChilds = new ArrayList<>();
	
	/**
	 * 子发货单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "online_order_id")
	private List<Delivery> deliverys = new ArrayList<>();
	
	
	/**
	 * 客户昵称
	 * 
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 收货人姓名
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
	 * 货品总价。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name = "sumPrice")
	private Double sumPrice;
	
	/**
	 * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name = "payment")
	private Double payment;
	
	/**
	 * 邮费
	 */
	@Column(name = "post_fee")
	private Double postFee;
	
	/**
	 * 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）
	 */
	@Column(name = "received_payment")
	private Double receivedPayment;
	
	/**
	 * 	商品购买数量
	 */
	@Column(name = "num")
	private Integer num;
	
	/**
	 * 剩余发货数量
	 */
	@Column(name = "residue_number")
	private Integer residueNumber;
	
	/**
	 * 订单状态交易状态。可选值: 
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) 
	 * WAIT_BUYER_PAY(等待买家付款) 
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * SELLER_CONSIGNED_PART(卖家部分发货) 
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) 
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
	 * TRADE_FINISHED(交易成功) *
	 * 
	 */
	@Column(name = "status")
	private String status;
	
	/**
	 * 整单优惠
	 */
	@Column(name = "all_bill_preferential")
	private Double allBillPreferential;



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
	private Integer buyerFlag;

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
	private Integer sellerFlag;
	

	/**
	 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。
	 * 可选值：
	 * free(卖家包邮),
	 * post(平邮),
	 * express(快递),
	 * ems(EMS),
	 * virtual(虚拟发货)，
	 * 25(次日必达)，
	 * 26(预约配送)
	 * 
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
	 * 买家电话号
	 * 
	 */
	@Column(name = "telephone")
	private String telephone;
	
	/**
	 * 邮编
	 */
	@Column(name = "zip_code")
	private String zipCode;

	/**
	 * 是否反冲（0=否，1=是）
	 */
	@Column(name = "flag")
	private Integer flag=0;
	
	/**
	 * 新增子订单json数据
	 */
	@Transient
	private String childOrder;
	
	/**
	 * 客户名称
	 * 
	 */
	@Transient
	private String onlineCustomerName;
	
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
	/**
	 * 报表report(1=日，2=月，3=员工，4=客户)
	 */
	@Transient
	private Integer report;
	
	/**
	 * 按多个省份查询
	 */
	@Transient
	private String provincesIds;
	
	
	@Transient
	private List<List<Delivery>> deliveryLists = new ArrayList<>();
	
	
	
	public List<List<Delivery>> getDeliveryLists() {
		return deliveryLists;
	}

	public void setDeliveryLists(List<List<Delivery>> deliveryLists) {
		this.deliveryLists = deliveryLists;
	}


	public List<Delivery> getDeliverys() {
		return deliverys;
	}

	public void setDeliverys(List<Delivery> deliverys) {
		this.deliverys = deliverys;
	}

	public Integer getResidueNumber() {
		return residueNumber;
	}

	public void setResidueNumber(Integer residueNumber) {
		this.residueNumber = residueNumber;
	}

	public Integer getBuyerFlag() {
		return buyerFlag;
	}

	public void setBuyerFlag(Integer buyerFlag) {
		this.buyerFlag = buyerFlag;
	}

	public Integer getSellerFlag() {
		return sellerFlag;
	}

	public void setSellerFlag(Integer sellerFlag) {
		this.sellerFlag = sellerFlag;
	}

	public String getProvincesIds() {
		return provincesIds;
	}

	public void setProvincesIds(String provincesIds) {
		this.provincesIds = provincesIds;
	}

	public String getOnlineCustomerName() {
		return onlineCustomerName;
	}

	public void setOnlineCustomerName(String onlineCustomerName) {
		this.onlineCustomerName = onlineCustomerName;
	}

	public Integer getReport() {
		return report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

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

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public Double getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(Double receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
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

	public Double getAllBillPreferential() {
		return allBillPreferential;
	}

	public void setAllBillPreferential(Double allBillPreferential) {
		this.allBillPreferential = allBillPreferential;
	}


}
