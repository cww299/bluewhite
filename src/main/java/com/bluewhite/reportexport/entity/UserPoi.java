package com.bluewhite.reportexport.entity;

import java.util.Date;

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
	@Poi(name = "生日", column = "C")
    private Date birthDate;
	
	/**
	 * 部门
	 */
	@Poi(name = "部门", column = "D")
	private String orgName;

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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	


	
	

	
	

}
