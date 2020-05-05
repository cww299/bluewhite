package com.bluewhite.system.user.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

/**
 * 导出劳动人员合同到期实体
 * @author cww299
 */
public class UserPoi {
	
	@ExcelIgnore
	private Long id;
	
	/**
	 * 姓名
	 */
	@ExcelProperty("姓名")
	private String name;
	
	/**
	 * 年龄
	 */
	@ExcelProperty("年龄")
	private Integer age;
	
	/**
	 * 所在部门
	 */
	@ExcelProperty("部门")
	private String org;
	
	/**
	 * 所在部门
	 */
	@ExcelProperty("编号")
	private String numbers;
	
	/**
	 * 所在部门
	 */
	@ExcelProperty("岗位")
	private String post;
	
	/**
	 * 入职日期
	 */
	@DateTimeFormat("yyyy-MM-dd")
	@ExcelProperty("入职日期")
	private Date inputTime;

	/**
     * 出生日期
     */
	@DateTimeFormat("yyyy-MM-dd")
	@ExcelProperty("出生日期")
    private Date birthTime;

	/**
	 * 签订合同
	 */
	@ExcelProperty("签订合同")
	private String contract;

	/**
	 * 位置编号
	 */
	@ExcelProperty("位置编号")
	private String lotionNumber;

	/**
	 * 合同到期时间
	 */
	@DateTimeFormat("yyyy-MM-dd")
	@ExcelProperty("合同到期时间")
	private Date contractTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public Date getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(Date brithTime) {
		this.birthTime = brithTime;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getLotionNumber() {
		return lotionNumber;
	}

	public void setLotionNumber(String lotionNumber) {
		this.lotionNumber = lotionNumber;
	}

	public Date getContractTime() {
		return contractTime;
	}

	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}
	
	
}
