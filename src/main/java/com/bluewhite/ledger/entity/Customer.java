package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;

/**
 * 系统中的所有客户
 *
 */
@Entity
@Table(name = "ledger_customer")
public class Customer extends BaseEntity<Long> {

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
	 * 客户等级
	 * 
	 */
	@Column(name = "grade")
	private Integer grade;

	/**
	 * 客户归属id
	 */
	@Column(name = "customer_attribution_id")
	private Long customerAttributionId;

	/**
	 * 客户归属
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_attribution_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData customerAttribution;

	/**
	 * 客户归属id
	 */
	@Column(name = "customer_type_id")
	private Long customerTypeId;

	/**
	 * (1=电商,2=商超，3=线下，4=面辅料供应商 5=加工点 6=菜商) 
	 * 客户归属
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData customerType;

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
	 * 手机号
	 * 
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 帐号
	 * 
	 */
	@Column(name = "account")
	private String account;

	/**
	 * 联系方式
	 * 
	 */
	@Column(name = "contact")
	private String contact;

	/**
	 * 联系人姓名
	 * 
	 */
	@Column(name = "contact_name")
	private String contactName;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCustomerAttributionId() {
		return customerAttributionId;
	}

	public void setCustomerAttributionId(Long customerAttributionId) {
		this.customerAttributionId = customerAttributionId;
	}

	public BaseData getCustomerAttribution() {
		return customerAttribution;
	}

	public void setCustomerAttribution(BaseData customerAttribution) {
		this.customerAttribution = customerAttribution;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public BaseData getCustomerType() {
		return customerType;
	}

	public void setCustomerType(BaseData customerType) {
		this.customerType = customerType;
	}

	
}
