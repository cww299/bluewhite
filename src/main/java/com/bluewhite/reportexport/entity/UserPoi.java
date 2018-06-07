package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class UserPoi {
	
	/**
	 * 部门
	 */
	@Poi(name = "", column = "A")
	private String orgName;
	
	/**
	 * 姓名
	 */
	@Poi(name = "", column = "B")
	private String userName;
	
	/**
	 * 职位
	 */
	@Poi(name = "", column = "C")
	private String position;

	
	/**
	 * 入职时间
	 */
	@Poi(name = "", column = "D")
	private String entry;
	
	/**
	 * 出生日期
	 */
	@Poi(name = "", column = "E")
    private String birthday;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	

	

	
	

}
