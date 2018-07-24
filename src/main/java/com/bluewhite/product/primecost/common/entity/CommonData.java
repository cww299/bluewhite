package com.bluewhite.product.primecost.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 关于填写流程所用到的所有公共部分的数据
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_common_data")
public class CommonData  extends BaseEntity<Long>{
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 面料损耗默认值
	 */
	@Column(name = "loss")
	private Double loss;
	
	/**
	 * 类型
	 */
	@Column(name = "type")
	private Double type;
	
	
	public Double getType() {
		return type;
	}

	public void setType(Double type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}
	
	
	

}
