package com.bluewhite.production.temporarypack;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 量化单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_quantitative_child")
public class QuantitativeChild {

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
	 * 总包数
	 */
	@Column(name = "sum_package_number")
	private Integer sumPackageNumber;

	/**
	 * 单包个数
	 */
	@Column(name = "single_number")
	private Integer singleNumber;

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

	public Integer getSumPackageNumber() {
		return sumPackageNumber;
	}

	public void setSumPackageNumber(Integer sumPackageNumber) {
		this.sumPackageNumber = sumPackageNumber;
	}

	public Integer getSingleNumber() {
		return singleNumber;
	}

	public void setSingleNumber(Integer singleNumber) {
		this.singleNumber = singleNumber;
	}

	
	
}
