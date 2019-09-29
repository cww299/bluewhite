package com.bluewhite.system.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

@Entity
@Table(name = "sys_files")
public class Files extends BaseEntity<Long> {

	/**
	 * 文件名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 大小
	 */
	@Column(name = "size")
	private Long size;
	/**
	 * 类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 文件地址
	 */
	@Column(name = "url")
	private String url;
	/**
	 * 文件类型id
	 */
	@Column(name = "files_type_id")
	private Long filesTypeId;
	
	/**
	 * 文件类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "files_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData filesType;
	
	
	
	public Long getFilesTypeId() {
		return filesTypeId;
	}

	public void setFilesTypeId(Long filesTypeId) {
		this.filesTypeId = filesTypeId;
	}

	public BaseData getFilesType() {
		return filesType;
	}

	public void setFilesType(BaseData filesType) {
		this.filesType = filesType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}