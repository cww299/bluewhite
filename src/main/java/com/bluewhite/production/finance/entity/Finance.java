package com.bluewhite.production.finance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 生产控制部  财务实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_finance")
public class Finance  extends BaseEntity<Long>{
	
	/**
	 * 员工姓名
	 */
	@Column(name = "user_name")
    private String userName;
	
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
    private Long userId;


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	

}
