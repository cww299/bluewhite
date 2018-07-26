package com.bluewhite.product.primecostbasedata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;
/**
 * 基础数据3(成本计算)
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_three")
public class BaseThree extends BaseEntity<Long>{
	
	
	/**
	 * 普通激光按平方M大小
	 */
	@Poi(name = "", column = "A")
	@Column(name = "ordinar_laser")
	private Double ordinaryLaser;
	
	/**
	 * 考证过普通光切费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "B")
	@Column(name = "textual_ordinary_light")
	private Double textualOrdinaryLight;
	
	/**
	 * 考证过定位光切费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "C")
	@Column(name = "textual_position_light")
	private Double textualPositionLight;
	
	/**
	 * 考证过冲床费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "D")
	@Column(name = "textual_puncher")
	private Double textualPuncher;
	
	/**
	 * 考证过电烫费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "E")
	@Column(name = "textual_perm")
	private Double textualPerm;
	
	/**
	 * 考证过电推费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "F")
	@Column(name = "textual_clippers")
	private Double textualClippers;
	
	/**
	 * 考证过手工剪刀费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "G")
	@Column(name = "textual_scissors")
	private Double textualScissors;
	
	/**
	 * 考证过电烫捡片数片时间（秒）（容易）
	 */
	@Poi(name = "", column = "H")
	@Column(name = "textual_easy_time")
	private Double textualEasyTime;
	
	/**
	 * 考证过电烫捡片数片时间（秒）（难）
	 */
	@Poi(name = "", column = "I")
	@Column(name = "textual_hard_time")
	private Double textualHardTime;
	
	/**
	 * 普通光切费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "J")
	@Column(name = "ordinary_light")
	private Double ordinaryLight;
	
	/**
	 * 定位光切费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "K")
	@Column(name = "position_light")
	private Double position_light;
	
	/**
	 * 冲床费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "L")
	@Column(name = "puncher")
	private Double puncher;
	
	/**
	 * 电烫费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "M")
	@Column(name = "perm")
	private Double perm;
	
	/**
	 * 电推费用（含管理等一切费用）/元
	 */
	@Poi(name = "", column = "N")
	@Column(name = "clippers")
	private Double clippers;
	
	/**
	 * 手工剪刀费用（含管理等一切费用）/元手填↓
	 */
	@Poi(name = "", column = "O")
	@Column(name = "scissors")
	private Double scissors;
	
	/**
	 * 电烫捡片数片时间（秒）（容易）手填
	 */
	@Poi(name = "", column = "P")
	@Column(name = "easy_time")
	private Double easyTime;
	
	/**
	 * 电烫捡片数片时间（秒）（难）手填
	 */
	@Poi(name = "", column = "Q")
	@Column(name = "hard_time")
	private Double hardTime;

	public Double getOrdinaryLaser() {
		return ordinaryLaser;
	}

	public void setOrdinaryLaser(Double ordinaryLaser) {
		this.ordinaryLaser = ordinaryLaser;
	}

	public Double getTextualOrdinaryLight() {
		return textualOrdinaryLight;
	}

	public void setTextualOrdinaryLight(Double textualOrdinaryLight) {
		this.textualOrdinaryLight = textualOrdinaryLight;
	}

	public Double getTextualPositionLight() {
		return textualPositionLight;
	}

	public void setTextualPositionLight(Double textualPositionLight) {
		this.textualPositionLight = textualPositionLight;
	}

	public Double getTextualPuncher() {
		return textualPuncher;
	}

	public void setTextualPuncher(Double textualPuncher) {
		this.textualPuncher = textualPuncher;
	}

	public Double getTextualPerm() {
		return textualPerm;
	}

	public void setTextualPerm(Double textualPerm) {
		this.textualPerm = textualPerm;
	}

	public Double getTextualClippers() {
		return textualClippers;
	}

	public void setTextualClippers(Double textualClippers) {
		this.textualClippers = textualClippers;
	}

	public Double getTextualScissors() {
		return textualScissors;
	}

	public void setTextualScissors(Double textualScissors) {
		this.textualScissors = textualScissors;
	}

	public Double getTextualEasyTime() {
		return textualEasyTime;
	}

	public void setTextualEasyTime(Double textualEasyTime) {
		this.textualEasyTime = textualEasyTime;
	}

	public Double getTextualHardTime() {
		return textualHardTime;
	}

	public void setTextualHardTime(Double textualHardTime) {
		this.textualHardTime = textualHardTime;
	}

	public Double getOrdinaryLight() {
		return ordinaryLight;
	}

	public void setOrdinaryLight(Double ordinaryLight) {
		this.ordinaryLight = ordinaryLight;
	}

	public Double getPosition_light() {
		return position_light;
	}

	public void setPosition_light(Double position_light) {
		this.position_light = position_light;
	}

	public Double getPuncher() {
		return puncher;
	}

	public void setPuncher(Double puncher) {
		this.puncher = puncher;
	}

	public Double getPerm() {
		return perm;
	}

	public void setPerm(Double perm) {
		this.perm = perm;
	}

	public Double getClippers() {
		return clippers;
	}

	public void setClippers(Double clippers) {
		this.clippers = clippers;
	}

	public Double getScissors() {
		return scissors;
	}

	public void setScissors(Double scissors) {
		this.scissors = scissors;
	}

	public Double getEasyTime() {
		return easyTime;
	}

	public void setEasyTime(Double easyTime) {
		this.easyTime = easyTime;
	}

	public Double getHardTime() {
		return hardTime;
	}

	public void setHardTime(Double hardTime) {
		this.hardTime = hardTime;
	}
	
	

}
