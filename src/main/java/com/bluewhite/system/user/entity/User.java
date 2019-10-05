package com.bluewhite.system.user.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.production.group.entity.Group;

/**
 * 员工用户实体
 * @author zhangliang
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
	 * 照片id
	 */
	@Column(name = "file_id")
	private Long fileId;
	
	/**
	 * 照片url
	 */
	@Column(name = "picture_url")
	private String pictureUrl;
	
	/**
	 * 用户密码
	 */
	@Column(name = "password")
	private String password; 

	/**
	 * 员工姓名
	 */
	@Column(name = "username")
	private String userName;
	
	/**
	 * 员工编号（考勤机上的编号）
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 民族
	 */
	@Column(name = "nation")
	private String nation;
	
	/**
	 * 固定电话
	 */
	@Column(name = "telephone")
	private String telephone;
	
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
	 * 性别(0=男，1=女)
	 */
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 生日
	 */
	@Column(name = "birth_date")
	private Date birthDate;
	
	/**
	 * 年龄
	 */
	@Column(name = "age")
	private Integer age;
	
	/**
	 * 身份证到期时间
	 */
	@Column(name = "id_card_end")
	private Date idCardEnd;

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
    private Integer marriage;
    /**
     * 生育状况
     */
	@Column(name = "procreate")
    private Integer procreate;
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
     * 紧急联系人
     */
	@Column(name = "contacts")
    private String contacts;
	
    /**
     * 紧急联系人关系
     */
	@Column(name = "nexus")
    private String nexus;
	
    /**
     * 紧急联系方式
     */
	@Column(name = "information")
    private String information;
	
    /**
     * 入职时间
     */
	@Column(name = "entry")
    private Date entry;
	
    /**
     * 预计转正时间 
     */
	@Column(name = "estimate")
    private Date estimate;
	
    /**
     * 实际转正开始时间
     */
	@Column(name = "actua")
    private Date actua;
	
	/**
	 * 社保缴纳时间
	 */
	@Column(name = "social_security")
	private Date socialSecurity;
	
	/**
	 * 银行卡1
	 */
	@Column(name = "bank_card1")
    private String bankCard1;
	
	/**
	 * 所属银行1
	 */
	@Column(name = "ascription_bank1")
    private String ascriptionBank1;
	
	/**
	 * 银行卡2
	 */
	@Column(name = "bank_card2")
    private String bankCard2;
	
	/**
	 * 保险情况
	 * 0=未缴，1=已缴，
	 */
	@Column(name = "safe")
	private Integer safe;
	
	/**
	 * 是否签订承诺书
	 *  0=未签，1=已签，
	 */
	@Column(name = "promise")
    private Integer promise;
	/**
	 * 是否签订合同
	 * 0=未签，1=已签，2=续签
	 */
	@Column(name = "commitment")
    private Integer commitment;
	/**
	 * 合同签订开始日期
	 */
	@Column(name = "contract_date")
    private Date contractDate;
	/**
	 * 合同签订结束日期
	 */
	@Column(name = "contract_date_end")
    private Date contractDateEnd;
	
	/**
	 * 合同签订次数
	 */
	@Column(name = "frequency")
	private Integer frequency;
	/**
	 * 工作状态(0=在职1=离职)
	 */
	@Column(name = "quit")
    private Integer quit;
	/**
	 * 离职时间
	 */
	@Column(name = "quit_date")
    private Date quitDate;
	/**
	 * 离职原因
	 */
	@Column(name = "reason")
    private String reason;
	/**
	 * 培训
	 */
	@Column(name = "train")
    private String train;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 合同id
	 */
	@Column(name = "commitment_id")
	private Long commitmentId;
	
	/**
	 * 合同
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commitment_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData commitments;
	
	
	/**
	 * 协议id
	 */
	@Column(name = "agreement_id")
	private String agreementId;
	

	/**
	 * 职位id
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 职位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;
	
	
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
	 * 角色集合
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<Role>();
	
	/**
	 * 合同id
	 */
	@Column(name = "user_contract_id")
    private Long userContractId;
	
	/**
	 * 合同位置实体
	 */
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL) 
    @JoinColumn(name = "user_contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserContract userContract;
	
	/**
	 * 到岗小时预计收入
	 */
	@Column(name = "price")
	private Double price;
	
	
	/**
	 * 工作状态（0=工作，1 等于休息）
	 */
	@Column(name = "status")
	private Integer status;
	
	
	/**
	 * 工作时长
	 */
	@Column(name = "work_time")
	private Double workTime;
	
	/**
	 * 签订单位
	 */
	@Column(name = "company")
	private String company;
	
	/**
	 * 宿舍ID
	 */
	@Column(name = "hostel_id")
	private Long hostelId;
	
	/**
	 * 
	 * 位置编号
	 */
	@Transient
	private Integer lotionNumber;
	
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
	
	
	/**
	 * 部门ids
	 */
	@Transient
	private String orgNameIds;
	
	/**
	 * 是否退休返聘
	 */
	@Transient
	private Integer retire;
	
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	/**
	 * 按位置编号排序
	 */
	@Transient
	private Integer numberSort;
	
	/**
	 * 出勤时长
	 */
	@Transient
	private Double  turnWorkTime;
	
	/**
	 * 对于人员显示出勤时间特殊字段
	 */
	@Transient
	private Integer isType;
	
	
	  
	public Integer getIsType() {
		return isType;
	}

	public void setIsType(Integer isType) {
		this.isType = isType;
	}

	public Double getTurnWorkTime() {
		return turnWorkTime;
	}

	public void setTurnWorkTime(Double turnWorkTime) {
		this.turnWorkTime = turnWorkTime;
	}

	public Integer getNumberSort() {
		return numberSort;
	}

	public void setNumberSort(Integer numberSort) {
		this.numberSort = numberSort;
	}

	public Long getUserContractId() {
		return userContractId;
	}

	public void setUserContractId(Long userContractId) {
		this.userContractId = userContractId;
	}

	public String getAscriptionBank1() {
		return ascriptionBank1;
	}

	public void setAscriptionBank1(String ascriptionBank1) {
		this.ascriptionBank1 = ascriptionBank1;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}


	public Integer getLotionNumber() {
		return lotionNumber;
	}

	public void setLotionNumber(Integer lotionNumber) {
		this.lotionNumber = lotionNumber;
	}

	public Integer getRetire() {
		return retire;
	}

	public void setRetire(Integer retire) {
		this.retire = retire;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getCommitmentId() {
		return commitmentId;
	}

	public void setCommitmentId(Long commitmentId) {
		this.commitmentId = commitmentId;
	}

	public BaseData getCommitments() {
		return commitments;
	}

	public void setCommitments(BaseData commitments) {
		this.commitments = commitments;
	}



	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public Date getIdCardEnd() {
		return idCardEnd;
	}

	public void setIdCardEnd(Date idCardEnd) {
		this.idCardEnd = idCardEnd;
	}

	public UserContract getUserContract() {
		return userContract;
	}

	public void setUserContract(UserContract userContract) {
		this.userContract = userContract;
	}

	public Integer getSafe() {
		return safe;
	}

	public void setSafe(Integer safe) {
		this.safe = safe;
	}

	public Date getContractDateEnd() {
		return contractDateEnd;
	}

	public void setContractDateEnd(Date contractDateEnd) {
		this.contractDateEnd = contractDateEnd;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgNameIds() {
		return orgNameIds;
	}

	public void setOrgNameIds(String orgNameIds) {
		this.orgNameIds = orgNameIds;
	}

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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public Integer getProcreate() {
		return procreate;
	}

	public void setProcreate(Integer procreate) {
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

	public Integer getPromise() {
		return promise;
	}

	public void setPromise(Integer promise) {
		this.promise = promise;
	}

	public String getNexus() {
		return nexus;
	}

	public void setNexus(String nexus) {
		this.nexus = nexus;
	}

	public Integer getCommitment() {
		return commitment;
	}

	public void setCommitment(Integer commitment) {
		this.commitment = commitment;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getQuit() {
		return quit;
	}

	public void setQuit(Integer quit) {
		this.quit = quit;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public Long getHostelId() {
		return hostelId;
	}

	public void setHostelId(Long hostelId) {
		this.hostelId = hostelId;
	}


	
	
	
	
	

}
