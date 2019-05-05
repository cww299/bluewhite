package com.bluewhite.system.user.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 菜单管理类
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Long> {

	/**
	 * 菜单名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 菜单身份
	 */
	@Column(name = "identity")
	private String identity;
	/**
	 * url
	 */
	@Column(name = "url")
	private String url;
	/**
	 * 父id
	 */
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 父ids
	 */
	@Column(name = "parent_ids")
	private String parentIds;
	/**
	 * span
	 */
	@Column(name = "span")
	private String span;
	/**
	 * icon
	 */
	@Column(name = "icon")
	private String icon;
	/**
	 * 顺序
	 */
	@Column(name = "order_no")
	private Integer orderNo;
	/**
	 * 是否显示
	 */
	@Column(name = "is_show")
	private Boolean isShow;
	/**
	 * 子类
	 */
	@Transient
	private List<Menu> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public int getOrderNo() {
		return orderNo == null ?  0 : orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public int compareTo(Menu arg0) {
		try {
			return orderNo.compareTo(arg0.getOrderNo());
		} catch (Exception e) {
			return 0;
		}
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	

}
