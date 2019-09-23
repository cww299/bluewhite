package com.bluewhite.product.primecostbasedata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;
/**
 * 面辅料（库存）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_materiel")
public class Materiel extends BaseEntity<Long>{
	
	/**
     * 面料编号
     */
	@Column(name = "number")
	@Poi(name = "", column = "A")
    private String number;
	
	/**
     * 面料名
     */
	@Column(name = "name")
	@Poi(name = "", column = "B")
    private String name;
	
	/**
     * 面料价格
     */
	@Column(name = "price")
	@Poi(name = "", column = "C")
    private Double price;
	
	/**
	 * 单位id
	 */
	@Column(name = "unit_id")
	private Long unitId;
	
	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne unit;
	
	/**
     * 类型
     */
	@Column(name = "type")
    private String type;
		
	/**
	 * 需要的数字填写
	 * @return
	 */
	@Column(name = "count")
	private Integer count;
	
	/**
	 * 换算之后的单位
	 * @return
	 */
	@Column(name = "convert_unit")
    private String convertUnit;
	
	/**
	 * 换算之后的单位的价格
	 * @return
	 */
	@Column(name = "convert_price")
    private Double convertPrice;
	
	/**
	 * 需要的数字填写
	 * @return
	 */
	@Column(name = "convert_number")
    private Double convertNumber;
	
	
	/**
	 * 库存数量
	 * 
	 */
	@Column(name = "inventory_number")
	private Integer inventoryNumber;
	
	
	
	
	public Double getConvertNumber() {
		return convertNumber;
	}

	public void setConvertNumber(Double convertNumber) {
		this.convertNumber = convertNumber;
	}

	public String getConvertUnit() {
		return convertUnit;
	}

	public void setConvertUnit(String convertUnit) {
		this.convertUnit = convertUnit;
	}

	public Double getConvertPrice() {
		return convertPrice;
	}

	public void setConvertPrice(Double convertPrice) {
		this.convertPrice = convertPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
