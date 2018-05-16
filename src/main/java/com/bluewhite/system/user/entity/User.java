package com.bluewhite.system.user.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 员工用户实体
 * @author zhangliang
 *
 */
/**
 * @author LB-BY06
 *
 */
@Entity
@Table(name = "sys_user")
// @Inheritance 的 strategy 属性是指定继承关系的生成策略，JOINED
// 是将父类、子类分别存放在不同的表中，并且建立相应的外键，以确定相互之间的关系。
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<Long> {
	/**
	 * 系统管理员标识
	 */
	@Column(name = "isAdmin", nullable = false)
	private Boolean isAdmin = false;
	
	/**
	 * 是否锁定
	 */
	@Column(name = "del_flag")
	private Integer delFlag = 1;

	/**
	 * 登录 名
	 */
	@Column(name = "login_name")
	private String loginName;

	/**
	 * 用户密码,加密后
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 员工姓名
	 */
	@Column(name = "username")
	private String userName;
	
	/**
	 * 员工编号
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 民族
	 */
	@Column(name = "nation")
	private String nation;
	
	/**
	 * 手机
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;

	/**
	 * 性别
	 */
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 生日
	 */
	@Column(name = "birth_date")
	private Date birthDate;

	/**
	 * idcard
	 */
	@Column(name = "id_card")
	private String idCard;
	
	/**
	 * 户籍地址
	 */
	@Column(name = "permanent_address")
	private String permanentAddress;
	/**
	 * 现居住地址
	 */
	@Column(name = "living_address")
    private String livingAddress;
    /**
     * 婚姻状况
     */
	@Column(name = "marriage")
    private String marriage;
    /**
     * 生育状况
     */
	@Column(name = "procreate")
    private String procreate;
    /**
     * 学历
     */
	@Column(name = "education")
    private String education;
    /**
     * 毕业学校
     */
	@Column(name = "school")
    private String school;
    /**
     * 专业
     */
	@Column(name = "major")
    private String major;
    /**
     * 联系人
     */
	@Column(name = "contacts")
    private String contacts;
    /**
     * 联系方式
     */
	@Column(name = "information")
    private String information;
    /**
     *入职时间
     */
	@Column(name = "entry")
    private Date entry;
    /**
     *预计转正时间 
     */
	@Column(name = "estimate")
    private Date estimate;
    /**
     * 实际转正开始时间
     */
	@Column(name = "actua")
    private Date actua;
	/**
	 *社保缴纳时间
	 */
	@Column(name = "social_security")
	private Date socialSecurity;
	/**
	 * 出生日期
	 */
	@Column(name = "birthday")
    private Date birthday;
	/**
	 * 银行卡1
	 */
	@Column(name = "bank_card1")
    private String bankCard1;
	/**
	 * 银行卡2
	 */
	@Column(name = "bank_card2")
    private String bankCard2;
	/**
	 * 协议
	 */
	@Column(name = "agreement")
    private String agreement;
	/**
	 * 承诺书
	 */
	@Column(name = "promise")
    private String promise;
	/**
	 * 合同
	 */
	@Column(name = "contract")
    private String contract;
	/**
	 * 合同签订开始日期
	 */
	@Column(name = "contract_date")
    private Date contractDate;
	/**
	 * 合同签订次数
	 */
	@Column(name = "frequency")
	private Integer frequency;
	/**
	 *工作状态(在职离职)
	 */
	@Column(name = "quit")
    private String quit;
	/**
	 *离职时间
	 */
	@Column(name = "quit_date")
    private Date quitDate;
	/**
	 * 理由
	 */
	@Column(name = "reason")
    private String reason;
	/**
	 * 培训
	 */
	@Column(name = "train")
    private String train;
	/**
	 * 简介
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 职位id
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 职位
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData position;
	

	/**
	 * 部门id
	 */
	@Column(name = "orgName_id")
	private Long orgNameId;
	
	/**
	 * 部门
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;



	/**
	 * 角色集合
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * 权限
	 */
	@Transient
	@JSONField(serialize = false)
	private Set<String> permissions = new HashSet<>();

	/**
	 * 角色
	 */
	@Transient
	@JSONField(serialize = false)
	private Set<String> role = new HashSet<>();
	
	
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getLivingAddress() {
		return livingAddress;
	}

	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getProcreate() {
		return procreate;
	}

	public void setProcreate(String procreate) {
		this.procreate = procreate;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public String getBankCard1() {
		return bankCard1;
	}

	public void setBankCard1(String bankCard1) {
		this.bankCard1 = bankCard1;
	}

	public String getBankCard2() {
		return bankCard2;
	}

	public void setBankCard2(String bankCard2) {
		this.bankCard2 = bankCard2;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getPromise() {
		return promise;
	}

	public void setPromise(String promise) {
		this.promise = promise;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getQuit() {
		return quit;
	}

	public void setQuit(String quit) {
		this.quit = quit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public BaseData getPosition() {
		return position;
	}

	public void setPosition(BaseData position) {
		this.position = position;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public BaseData getOrgName() {
		return orgName;
	}

	public void setOrgName(BaseData orgName) {
		this.orgName = orgName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public Date getEntry() {
		return entry;
	}

	public void setEntry(Date entry) {
		this.entry = entry;
	}

	public Date getEstimate() {
		return estimate;
	}

	public void setEstimate(Date estimate) {
		this.estimate = estimate;
	}

	public Date getActua() {
		return actua;
	}

	public void setActua(Date actua) {
		this.actua = actua;
	}

	public Date getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(Date socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}
	
	

}
