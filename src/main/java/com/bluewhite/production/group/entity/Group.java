package com.bluewhite.production.group.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.user.entity.User;
/**
 * 分组表，用于记录生产部分组
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_group")
public class Group  extends BaseEntity<Long>{
	/**
	 * 组名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	/**
	 * 分组人员
	 */
	@OneToMany(mappedBy = "group")
	private Set<User> users = new HashSet<User>();
	
	/**
	 * 工种id
	 */
	@Column(name = "kind_work_id")
	private Long kindWorkId;
	
	
	/**
	 * 工种（二楼针工）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kind_work_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData kindWork;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	public Long getKindWorkId() {
		return kindWorkId;
	}

	public void setKindWorkId(Long kindWorkId) {
		this.kindWorkId = kindWorkId;
	}

	public BaseData getKindWork() {
		return kindWork;
	}

	public void setKindWork(BaseData kindWork) {
		this.kindWork = kindWork;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	

}
