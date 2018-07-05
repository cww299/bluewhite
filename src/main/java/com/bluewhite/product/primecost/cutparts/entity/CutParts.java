package com.bluewhite.product.primecost.cutparts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * cc裁片填写
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_cutparts")
public class CutParts extends BaseEntity<Long>{
	
	/**
	 * 选择该样品的裁片名字
	 */
	@Column(name = "cutparts_name")
    private String cutPartsName;
	
	/**
	 * 手填单个使用片数，不填默认1片
	 */
	@Column(name = "cutparts_number")
    private Integer cutPartsNumber;
	
	
	/**
	 * 使用片数周长
	 */
	@Column(name = "all_perimeter")
    private Double allPerimeter;
	
	/**
	 * 单片周长
	 */
	@Column(name = "perimeter")
    private Double perimeter;

}
