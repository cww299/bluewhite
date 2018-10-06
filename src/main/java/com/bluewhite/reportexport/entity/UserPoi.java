package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class UserPoi {
	
	
	/**
	 * 姓名
	 */
	@Poi(name = "", column = "A")
	private String userName;
	
	
	/**
	 * 性别
	 */
	@Poi(name = "", column = "B")
	private String sex;
	
	
	/**
	 * 是否签订合同
	 */
	@Poi(name = "", column = "C")
	private String ore;
	
	
	/**
	 * 签订时间
	 */
	@Poi(name = "", column = "D")
	private String oreTime;
	
	
	/**
	 * 合同到期时间
	 */
	@Poi(name = "", column = "E")
	private String oreTimeEnd;
	
	
	/**
	 * 签订单位
	 */
	@Poi(name = "", column = "F")
	private String company;
	
	/**
	 * 保险情况
	 */
	@Poi(name = "", column = "G")
	private String safe;
	
	
	/**
	 * 承诺书是否签订
	 */
	@Poi(name = "", column = "H")
	private String commitment;
	
    /**
     * 紧急联系人
     */
	@Poi(name = "", column = "I")
    private String contacts;
	
	/**
     * 紧急联系人关系
     */
	@Poi(name = "", column = "J")
    private String nexus;
    /**
     * 紧急联系方式
     */
	@Poi(name = "", column = "K")
    private String information;
	
	
	
	
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOre() {
		return ore;
	}

	public void setOre(String ore) {
		this.ore = ore;
	}

	public String getOreTime() {
		return oreTime;
	}

	public void setOreTime(String oreTime) {
		this.oreTime = oreTime;
	}

	public String getOreTimeEnd() {
		return oreTimeEnd;
	}

	public void setOreTimeEnd(String oreTimeEnd) {
		this.oreTimeEnd = oreTimeEnd;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getNexus() {
		return nexus;
	}

	public void setNexus(String nexus) {
		this.nexus = nexus;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	//	/**
//	 * 固定电话
//	 */
//	@Poi(name = "", column = "B")
//	private String telephone;
//	
//	/**
//	 * 联系方式
//	 */
//	@Poi(name = "", column = "C")
//	private String phone;
//	
//	/**
//	 * 身份证号
//	 */
//	@Poi(name = "", column = "D")
//	private String idCard;
//	
//	/**
//	 * 入职时间
//	 */
//	@Poi(name = "", column = "E")
//	private String entry;
//	
//	/**
//	 * 出生日期
//	 */
//	@Poi(name = "", column = "F")
//    private String birthday;
//	
//	/**
//	 * 户籍地址
//	 */
//	@Poi(name = "", column = "G")
//    private String permanentAddress;
//	
//	/**
//	 * 现居住地址
//	 */
//	@Poi(name = "", column = "H")
//    private String livingAddress;
//
//	
//
//	public String getTelephone() {
//		return telephone;
//	}
//
//	public void setTelephone(String telephone) {
//		this.telephone = telephone;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public String getIdCard() {
//		return idCard;
//	}
//
//	public void setIdCard(String idCard) {
//		this.idCard = idCard;
//	}
//
//	public String getPermanentAddress() {
//		return permanentAddress;
//	}
//
//	public void setPermanentAddress(String permanentAddress) {
//		this.permanentAddress = permanentAddress;
//	}
//
//	public String getLivingAddress() {
//		return livingAddress;
//	}
//
//	public void setLivingAddress(String livingAddress) {
//		this.livingAddress = livingAddress;
//	}
//
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
//
//
//
//	public String getEntry() {
//		return entry;
//	}
//
//	public void setEntry(String entry) {
//		this.entry = entry;
//	}
//
//	public String getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(String birthday) {
//		this.birthday = birthday;
//	}
//

	

	

	
	

}
