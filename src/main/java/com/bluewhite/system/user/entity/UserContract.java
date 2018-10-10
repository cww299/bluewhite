package com.bluewhite.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;

/**
 * 员工用户合同档案袋中物品数量
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_user_contract")
public class UserContract extends BaseEntity<Long>{
	
	/**
	 * 位置编号
	 */
	@Column(name = "number")
	@Poi(name = "", column = "A")
	private String number;
	
	/**
	 * 姓名
	 */
	@Column(name = "username")
	@Poi(name = "", column = "B")
	private String username;
	
	/**
	 * 档案
	 */
	@Column(name = "archives")
	@Poi(name = "", column = "C")
	private String archives;
	
	/**
	 * 照片
	 */
	@Column(name = "pic")
	@Poi(name = "", column = "D")
	private String pic;
	
	/**
	 * 身份证
	 */
	@Column(name = "id_card")
	@Poi(name = "", column = "E")
	private String idCard;
	
	/**
	 * 银行卡
	 */
	@Column(name = "bank_card")
	@Poi(name = "", column = "F")
	private String bankCard;
	
	/**
	 * 体检
	 */
	@Column(name = "physical")
	@Poi(name = "", column = "G")
	private String physical;
	
	/**
	 * 资格证书
	 */
	@Column(name = "qualification")
	@Poi(name = "", column = "H")
	private String qualification;
	
	/**
	 * 学历证书
	 */
	@Column(name = "formal_schooling")
	@Poi(name = "", column = "I")
	private String formalSchooling;
	
	/**
	 * 其他协议
	 */
	@Column(name = "agreement")
	@Poi(name = "", column = "J")
	private String  agreement;
	
	/**
	 * 保密协议
	 */
	@Column(name = "secrecy_agreement")
	@Poi(name = "", column = "K")
	private String secrecyAgreement;
	
	/**
	 * 合同
	 */
	@Column(name = "contract")
	@Poi(name = "", column = "L")
	private String contract;
	
	
	/**
	 * 其他资料
	 */
	@Column(name = "remark")
	@Poi(name = "", column = "M")
	private String remark;
	
	/**
	 *工作状态(在职离职)
	 */
	@Column(name = "quit")
    private Integer quit;
	
	


	public Integer getQuit() {
		return quit;
	}


	public void setQuit(Integer quit) {
		this.quit = quit;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getArchives() {
		return archives;
	}


	public void setArchives(String archives) {
		this.archives = archives;
	}


	public String getPic() {
		return pic;
	}


	public void setPic(String pic) {
		this.pic = pic;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public String getBankCard() {
		return bankCard;
	}


	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}


	public String getPhysical() {
		return physical;
	}


	public void setPhysical(String physical) {
		this.physical = physical;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public String getFormalSchooling() {
		return formalSchooling;
	}


	public void setFormalSchooling(String formalSchooling) {
		this.formalSchooling = formalSchooling;
	}


	public String getAgreement() {
		return agreement;
	}


	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}


	public String getSecrecyAgreement() {
		return secrecyAgreement;
	}


	public void setSecrecyAgreement(String secrecyAgreement) {
		this.secrecyAgreement = secrecyAgreement;
	}


	public String getContract() {
		return contract;
	}


	public void setContract(String contract) {
		this.contract = contract;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
