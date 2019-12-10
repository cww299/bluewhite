package com.bluewhite.production.temporarypack;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 量化单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_quantitative_child")
public class QuantitativeChild  extends BaseEntity<Long> {

	/**
	 * 下货单id
	 */
	@Column(name = "underGoods_id")
	private Long underGoodsId;

	/**
	 * 下货单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "underGoods_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UnderGoods underGoods;
	
	/**
	 * 单包个数
	 */
	@Column(name = "single_number")
	private Integer singleNumber;
	
	/**
	 * 单包实际发货个数
	 */
	@Column(name = "actual_single_number")
	private Integer actualSingleNumber;
	
	/**
	 * 是否核实
	 * 
	 */
	@Column(name = "checks")
	private Integer checks;
	
	
	public Integer getChecks() {
		return checks;
	}

	public void setChecks(Integer check) {
		this.checks = check;
	}

	public Integer getActualSingleNumber() {
		return actualSingleNumber;
	}

	public void setActualSingleNumber(Integer actualSingleNumber) {
		this.actualSingleNumber = actualSingleNumber;
	}

	public Long getUnderGoodsId() {
		return underGoodsId;
	}

	public void setUnderGoodsId(Long underGoodsId) {
		this.underGoodsId = underGoodsId;
	}

	public UnderGoods getUnderGoods() {
		return underGoods;
	}

	public void setUnderGoods(UnderGoods underGoods) {
		this.underGoods = underGoods;
	}

	public Integer getSingleNumber() {
		return singleNumber;
	}

	public void setSingleNumber(Integer singleNumber) {
		this.singleNumber = singleNumber;
	}

	
	
}
