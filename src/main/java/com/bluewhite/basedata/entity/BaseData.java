package com.bluewhite.basedata.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

import javassist.expr.NewArray;

@Entity
@Table(name = "sys_base_data")
public class BaseData extends BaseEntity<Long>{
	
	/** 动态表单基础数据类型定义*/
	public static final String BASE_DATA_TYPE_DYNAMICFORM = "dynamicform";
	/**
	 * 数据名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 数据排序
	 */
	@Column(name = "ord")
	private String ord;
	/**
	 * 父级id
	 */
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 数据类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 备注(数据类型的注释)
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 是否启用
	 */
	@Column(name = "flag")
	private Integer flag = 1;
	
	/**
	 * 员工
	 */
	@Transient
	private List<User> users =new ArrayList<User>();

	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	

}
