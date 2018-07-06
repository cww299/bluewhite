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
	
	
	/**
	 * 自动得到面料产品编号
	 */
	@Column(name = "materiel_number")
    private String materielNumber;
	
	/**
	 * 自动得到面料产品名字
	 */
	@Column(name = "materiel_name")
    private String materielName;
	
	/**
	 * 需要复合选
	 */
	@Column(name = "composite")
    private Integer composite;

	/**
	 * 填写单片用料（单片的用料）
	 */
	@Column(name = "one_material")
    private Double oneMaterial;
	
	/**
	 * 单位填写选择
	 */
	@Column(name = "unit")
    private String unit;
	
	/**
	 * 各单片比全套用料
	 */
	@Column(name = "scale_material")
    private Double scaleMaterial;
	
	/**
	 * 该片在这个货中的单只用料（累加处）
	 */
	@Column(name = "add_material")
    private Double addMaterial;
	
}
