package com.bluewhite.system.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 系统日志类
 * @author long.xin
 *
 */
@Entity
@Table(name = "sys_log")
public class SysLog extends BaseEntity<Long>{
	
	
	public static final String VIEW_LOG_TYPE = "前端用户日志";
	
	public static final String ADMIN_LOG_TYPE = "后台管理日志";

	/**
	 * 功能模块名
	 */
	@Column(name = "module")
	private String module;

	/**
	 * 操作名
	 */
	@Column(name = "operate_name")
	private String operateName;
	
	/**
	 * 操作类型
	 */
	@Column(name = "operate_type")
	private String opertionType;
	/**
	 * 操作uri
	 */
	@Column(name = "uri")
	private String uri;
	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private String opertator;
	/**
	 * 操作人id
	 */
	@Column(name = "operator_id")
	private Long opertatorId;
	

	/**
	 * 日志分类
	 */
	@Column(name = "logType")
	private String logType;//日志类型 ，后台管理日志，前台管理日志等

	/**
	 * 操作时间
	 */
	@Column(name = "operate_time")
	private Date operateTime;

	/**
	 * 操作内容
	 */
	@Column(columnDefinition="longtext", name = "operate_detail") 
	private String operateDetail;
	
	/**
	 * 操作是否成功
	 */
	@Column(name = "success")
	private String success = "成功";
	
	/**
	 * 异常信息
	 */
	@Column(name = "exception", columnDefinition = "text")
	private String exception;

	/**
	 * 操作ip
	 */
	@Column(name = "operate_ip")
	private String operateIp;
	@Transient
	private Date endTime;
	

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(String opertionType) {
		this.opertionType = opertionType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getOpertator() {
		return opertator;
	}

	public void setOpertator(String opertator) {
		this.opertator = opertator;
	}

	public Long getOpertatorId() {
		return opertatorId;
	}

	public void setOpertatorId(Long opertatorId) {
		this.opertatorId = opertatorId;
	}
	

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateDetail() {
		return operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
