package com.bluewhite.system.user.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

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
	private String number;

	
	/**
	 * 档案
	 */
	@Column(name = "archives")
	private String archives;
	
	/**
	 * 照片
	 */
	@Column(name = "pic")
	private String pic;
	
	/**
	 * 身份证
	 */
	@Column(name = "id_card")
	private String idCard;
	
	/**
	 * 银行卡
	 */
	@Column(name = "bank_card")
	private String bankCard;
	
	/**
	 * 体检
	 */
	@Column(name = "physical")
	private String physical;
	
	/**
	 * 资格证书
	 */
	@Column(name = "qualification")
	private String qualification;
	
	/**
	 * 学历证书
	 */
	@Column(name = "formal_schooling")
	private String formalSchooling;
	
	/**
	 * 其他协议
	 */
	@Column(name = "agreement")
	private String  agreement;
	
	/**
	 * 保密协议
	 */
	@Column(name = "secrecy_agreement")
	private String secrecyAgreement;
	
	/**
	 * 合同
	 */
	@Column(name = "contract")
	private String contract;
	
	
	/**
	 * 其他资料
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 工作状态(在职离职)
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
