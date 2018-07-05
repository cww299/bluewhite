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
@Table(name = "pro_basedate_materiel")
public class Materiel extends BaseEntity<Long>{
	
	/**
     * 物料编号
     */
	@Column(name = "number")
	@Poi(name = "", column = "A")
    private String number;
	
	/**
     * 物料名
     */
	@Column(name = "name")
	@Poi(name = "", column = "B")
    private String name;
	
	/**
     * 物料最新价格
     */
	@Column(name = "price")
	@Poi(name = "", column = "C")
    private String price;
	
	/**
     * 物料克重等备注
     */
	@Column(name = "remark")
	@Poi(name = "", column = "D")
    private String remark;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
