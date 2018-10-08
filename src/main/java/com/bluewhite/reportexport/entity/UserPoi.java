package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class UserPoi {
	
	
	/**
	 * weizhi
	 */
	@Poi(name = "", column = "A")
	private String number;
	
	/**
	 * 姓名
	 */
	@Poi(name = "", column = "B")
	private String userName;
	
	/**
	 * 联系方式
	 */
	@Poi(name = "", column = "C")
	private String phone;
	
	/**
	 * 身份证号
	 */
	@Poi(name = "", column = "D")
	private String idCard;
	
	/**
	 * 入职时间
	 */
	@Poi(name = "", column = "E")
	private String entry;
	
	/**
	 *离职时间
	 */
	@Poi(name = "", column = "F")
    private String quitDate;
	
	
	/**
	 * 户籍地址
	 */
	@Poi(name = "", column = "G")
    private String permanentAddress;
	
	/**
	 * 现居住地址
	 */
	@Poi(name = "", column = "H")
    private String livingAddress;

	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}



	public String getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public String getIdCard() {
//		return idCard;
//	}
//
//	public void setIdCard(String idCard) {
//		this.idCard = idCard;
//	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getLivingAddress() {
		return livingAddress;
	}

	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}

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
