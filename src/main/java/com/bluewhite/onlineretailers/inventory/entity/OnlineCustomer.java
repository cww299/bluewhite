package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.sys.entity.RegionAddress;

/**
 * 电商客户
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_customer")
public class OnlineCustomer extends BaseEntity<Long>{
	
	
	
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
	 * 客户类型
	 * 
	 */
	@Column(name = "type")
	private Integer type;
	
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
	 * 帐号
	 * 
	 */
	@Column(name = "account")
	private String account;
	
	/**
	 * 邮编
	 */
	@Column(name = "zip_code")
	private String zipCode;
	

	/**
	 * 买家电话号
	 * 
	 */
	@Column(name = "telephone")
	private String telephone;
	
	

	
	 

	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
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


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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


	public Integer getGrade() {
		return grade;
	}


	public void setGrade(Integer grade) {
		this.grade = grade;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	

}
