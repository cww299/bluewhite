package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class User2Poi {
	
	
	/**
	 * 姓名
	 */
	@Poi(name = "姓名", column = "A")
	private String name;
	
	
	/**
	 * 部门
	 */
	@Poi(name = "部门", column = "B")
	private String orgName;
	
	/**
	 * 性别(0=男，1=女)
	 */
	@Poi(name = "性别", column = "E")
	private String gender;
	
	/**
	 * 身份证号
	 */
	@Poi(name = "身份证号码", column = "D")
	private String idCard;

	/**
	 * 职位
	 */
	@Poi(name = "职位", column = "C")
	private String positionName;
	
	/**
	 * 职位
	 */
	@Poi(name = "在职状态", column = "F")
	private String quit;
	
	public String getQuit() {
		return quit;
	}

	public void setQuit(String quit) {
		this.quit = quit;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	


	
	

	
	

}
