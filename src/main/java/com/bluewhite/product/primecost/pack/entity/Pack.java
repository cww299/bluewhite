package com.bluewhite.product.primecost.pack.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 内外包装和杂工
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_pack")
public class Pack extends BaseEntity<Long>{
	
	

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;

	/**
	 *内外包装和杂工名称
	 */
	@Column(name = "pack_name")
	private String packName;
	
	/**
	 * 有无档位（1=无，2=有）
	 */
	@Column(name = "gear")
	private Integer gear;
	
	/**
	 * 请选择在该工序下的分类
	 */
	@Column(name = "type")
    private String type;
	
	
	/**
	 * 自动得到工序的用时/秒
	 */
	@Column(name = "time")
    private Double time;
	
	/**
	 * 手填该包装可装单品数量/只↓
	 */
	@Column(name = "pack_number")
    private Double packNumber;
	
	/**
	 * 单只产品用时/秒
	 */
	@Column(name = "one_time")
    private Double oneTime;
	
	
	/**
	 * 手工输入一个不常见的工序
	 */
	@Column(name = "uncommon_pack")
    private String uncommonPack;
	
	/**
	 * 请手动输入这个工序单只的用时/秒
	 */
	@Column(name = "uncommon_time")
    private Double uncommonTime;
	
	/**
	 * 工种定性↓(0=杂工，1=力工)
	 */
	@Column(name = "kind_work")
    private Integer kindWork;
	
	/**
	 * 内外包装定性↓
	 */
	@Column(name = "exterior_interior")
    private String exteriorInterior;
	
	/**
	 * 批量用时/秒(含快手）
	 */
	@Column(name = "batch_time")
    private Double batchTime;
	
	
	/**
	 * 单只时间（含快手）
	 */
	@Column(name = "one_pack_time")
    private Double onePackTime;
	
	
	/**
	 * 该工序有可能用到的物料
	 */
	@Column(name = "materiel")
    private String materiel;
	
	/**
	 * 工价
	 */
	@Column(name = "pack_price")
	private Double packPrice;
	/**
	 * 设备折旧和房水电费
	 */
	@Column(name = "equipment_price")
    private Double equipmentPrice;	
	/**
	 * 管理人员费用
	 */
	@Column(name = "administrative_staff")
    private Double administrativeAtaff;		
	
	/**
	 * 包装车间利润
	 */
	@Column(name = "pack_shop_price")
	private Double packShopPrice;
	
	
	/**
	 * 总入成本价格
	 */
	@Column(name = "all_cost_price")
    private Double allCostPrice;
	
	/**
	 * 物料压价
	 */
	@Column(name = "price_down")
    private Double priceDown;
	
	

	public Integer getGear() {
		return gear;
	}

	public void setGear(Integer gear) {
		this.gear = gear;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}


	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(Double packNumber) {
		this.packNumber = packNumber;
	}

	public Double getOneTime() {
		return oneTime;
	}

	public void setOneTime(Double oneTime) {
		this.oneTime = oneTime;
	}

	public String getUncommonPack() {
		return uncommonPack;
	}

	public void setUncommonPack(String uncommonPack) {
		this.uncommonPack = uncommonPack;
	}

	public Double getUncommonTime() {
		return uncommonTime;
	}

	public void setUncommonTime(Double uncommonTime) {
		this.uncommonTime = uncommonTime;
	}


	public Integer getKindWork() {
		return kindWork;
	}

	public void setKindWork(Integer kindWork) {
		this.kindWork = kindWork;
	}

	public String getExteriorInterior() {
		return exteriorInterior;
	}

	public void setExteriorInterior(String exteriorInterior) {
		this.exteriorInterior = exteriorInterior;
	}

	public Double getBatchTime() {
		return batchTime;
	}

	public void setBatchTime(Double batchTime) {
		this.batchTime = batchTime;
	}

	public Double getOnePackTime() {
		return onePackTime;
	}

	public void setOnePackTime(Double onePackTime) {
		this.onePackTime = onePackTime;
	}

	public String getMateriel() {
		return materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	public Double getPackPrice() {
		return packPrice;
	}

	public void setPackPrice(Double packPrice) {
		this.packPrice = packPrice;
	}

	public Double getEquipmentPrice() {
		return equipmentPrice;
	}

	public void setEquipmentPrice(Double equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}

	public Double getAdministrativeAtaff() {
		return administrativeAtaff;
	}

	public void setAdministrativeAtaff(Double administrativeAtaff) {
		this.administrativeAtaff = administrativeAtaff;
	}

	public Double getPackShopPrice() {
		return packShopPrice;
	}

	public void setPackShopPrice(Double packShopPrice) {
		this.packShopPrice = packShopPrice;
	}

	public Double getAllCostPrice() {
		return allCostPrice;
	}

	public void setAllCostPrice(Double allCostPrice) {
		this.allCostPrice = allCostPrice;
	}

	public Double getPriceDown() {
		return priceDown;
	}

	public void setPriceDown(Double priceDown) {
		this.priceDown = priceDown;
	}
	

}
