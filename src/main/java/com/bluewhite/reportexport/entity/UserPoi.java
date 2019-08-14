package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class UserPoi {
	
	
	/**
	 * 姓名
	 */
	@Poi(name = "姓名", column = "A")
	private String name;
	
	/**
	 * 年龄
	 */
	@Poi(name = "年龄", column = "B")
    private Integer age;

	/**
	 * 生日
	 */
	@Poi(name = "生日", column = "D")
    private String birthDate;
	
	/**
	 * 部门
	 */
	@Poi(name = "部门", column = "E")
	private String orgName;
	
	/**
	 * 性别(0=男，1=女)
	 */
	@Poi(name = "性别", column = "C")
	private String gender;
	
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	


	
	

	
	

}
