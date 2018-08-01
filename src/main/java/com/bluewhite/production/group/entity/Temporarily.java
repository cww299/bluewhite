package com.bluewhite.production.group.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 借调人员
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_temporarily")
public class Temporarily extends BaseEntity<Long>{
	
	/**
	 * id
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
	
	/**
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装)
	 */
	@Column(name = "type")
	private Integer type;


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
