package com.bluewhite.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.production.group.entity.Group;

/**
 * 
 * 临时员工用户实体，系统生产部门会请临时人员进行生产任务，为了让正式员工和临时员工分离 用于控制脏数据的产生
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_temporary_user")
public class TemporaryUser extends BaseEntity<Long> {

	/**
	 * 员工姓名
	 */
	@Column(name = "username")
	private String userName;

	/**
	 * 手机
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * idcard
	 */
	@Column(name = "id_card")
	private String idCard;

	/**
	 * 银行卡1
	 */
	@Column(name = "bank_card1")
	private String bankCard1;

	/**
	 * 分组id
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 分组
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Group group;

	/**
	 * 工作状态（0=工作，1 等于休息）
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 出勤时长
	 */
	@Column(name = "turn_work_time")
	private Double turnWorkTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTurnWorkTime() {
		return turnWorkTime == null ? 0 : turnWorkTime ;
	}

	public void setTurnWorkTime(Double turnWorkTime) {
		this.turnWorkTime = turnWorkTime;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankCard1() {
		return bankCard1;
	}

	public void setBankCard1(String bankCard1) {
		this.bankCard1 = bankCard1;
	}

}
