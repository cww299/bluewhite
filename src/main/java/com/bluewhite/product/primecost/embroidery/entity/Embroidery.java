package com.bluewhite.product.primecost.embroidery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 绣花页面
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_embroidery")
public class Embroidery extends BaseEntity<Long>{
	
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 裁剪页面id
	 */
	@Column(name = "tailor_id")
	private Long tailorId;
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;

	/**
	 * 绣花步骤名称
	 */
	@Column(name = "embroidery_name")
	private String embroideryName;
	
	/**
	 * 选择针数
	 */
	@Column(name = "needle_bumber")
    private Integer needleNumber;
	
	/**
	 * 选择针号
	 */
	@Column(name = "needlesize")
    private String needlesize;
	
	/**
	 * 选择线粗细号
	 */
	@Column(name = "wiresize")
    private String wiresize;
	
	/**
	 * 选择贴布数
	 */
	@Column(name = "applique")
    private Integer applique;
	
	/**
	 * 选择贴布面积(原excel种是多个，现整合为一个字段，将选择的线色用字符串储存，用逗号分隔)
	 */
	@Column(name = "applique_size")
    private String appliqueSize;
	
	/**
	 * 选择贴布面积对应的贴布时间(原excel种是多个，现整合为一个字段，将选择的线色用字符串储存，用逗号分隔)
	 */
	@Column(name = "applique_time")
    private String appliqueTime;
	
	/**
	 * 被选裁片1面积
	 */
	@Column(name = "size_name")
    private String sizeName;
	/**
	 * 被选裁片2面积
	 */
	@Column(name = "size_two_name")
    private String sizeTwoName;
	
	/**
	 * 被选裁片1面积
	 */
	@Column(name = "size")
    private Double size;
	/**
	 * 被选裁片2面积
	 */
	@Column(name = "size_two")
    private Double sizeTwo;
	/**
	 *再次确认绣片面积
	 */
	@Column(name = "affirm_size")
    private Double affirmSize;
	/**
	 *再次确认绣片面积
	 */
	@Column(name = "affirm_size_two")
    private Double affirmSizeTwo;
	
	/**
	 * 选择蒙薄膜层数
	 */
	@Column(name = "membrane")
    private Integer membrane;
	
	/**
	 * 选择垫纸性质
	 */
	@Column(name = "packing_paper")
    private String packingPaper;
	
	/**
	 * 请选择绣花模式
	 */
	@Column(name = "embroidery_mode")
    private String embroideryMode;
	
	/**
	 * 整布绣问
	 */
	@Column(name = "cloth")
    private String cloth;
	/**
	 * 手填可绣多少片
	 */
	@Column(name = "embroidery_slice")
    private Integer embroiderySlice;
	
	/**
	 * 请选择几头操作
	 */
	@Column(name = "few")
    private Integer few;
	/**
	 * 请选择绣花针号
	 */
	@Column(name = "embroidery_needlesize")
    private Integer embroideryNeedlesize;
	
	/**
	 * 请选择绣花线号
	 */
	@Column(name = "embroidery_wiresize")
    private String embroideryWiresize;
	
	
	/**
	 * 请选择绣花线色(原excel种是多个，现整合为一个字段，将选择的线色用字符串储存，用逗号分隔)
	 */
	@Column(name = "embroidery_color")
    private String embroideryColor;
	
	
	/**
	 * 请选择绣花线色(数量）
	 */
	@Column(name = "embroidery_color_number")
    private Integer embroideryColorNumber;
	
	/**
	 * 单片机走时间
	 */
	@Column(name = "singlechip_time")
    private Double singlechipApplique;
	
	/**
	 * 铺布或裁片秀贴布和上绷子时间
	 */
	@Column(name = "time")
    private Double time;
	
	/**
	 * 贴片时间
	 */
	@Column(name = "paster_time")
    private Double pasterTime;
	
	/**
	 * 站机含快手时间
	 */
	@Column(name = "ventilator_station_time")
    private Double ventilatorStationTime;
	
	/**
	 * 剪线时间含快手
	 */
	@Column(name = "cutting_line_time")
    private Double cuttingLineTime;
	
	/**
	 * 绣花线用量/米
	 */
	@Column(name = "line_dosage")
    private Double lineDosage;
	
	/**
	 * 垫纸用量
	 */
	@Column(name = "packingPaper_dosage")
    private Double packingPaperDosage;
	
	/**
	 * 薄膜用量/平方米
	 */
	@Column(name = "membrane_dosage")
    private Double membraneDosage;
	
	/**
	 *绣花物料线价格
	 */
	@Column(name = "embroidery_price")
    private Double embroideryPrice;
	
	/**
	 *绣花物料垫纸价格
	 */
	@Column(name = "packingPaper_price")
    private Double packingPaperPrice;
	
	
	/**
	 *绣花物料薄膜价格
	 */
	@Column(name = "membrane_price")
    private Double membranePrice;
	
	
	/**
	 * 电脑推算站机工价（含快手)
	 */
	@Column(name = "reckoning_price")
	private Double reckoningPrice;
	
	/**
	 * 剪线工价
	 */
	@Column(name = "cut_line_price")
	private Double cutLinePrice;
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
	 * 电脑推算绣花价格
	 */
	@Column(name = "reckoning_embroidery_price")
	private Double reckoningEmbroideryPrice;
	
	
	/**
	 * 目前市场价格
	 */
	@Column(name = "reckoning_sewing_price")
	private Double reckoningSewingPrice;
	
	/**
	 * 选择单个入成本价格
	 */
	@Column(name = "cost_price")
    private Double costPrice;
	
	/**
	 * 总入成本价格
	 */
	@Column(name = "all_cost_price")
    private Double allCostPrice;
	
	
	/**
	 * 各单片比全套工价
	 */
	@Column(name = "scale_material")
    private Double scaleMaterial;
	
	
	/**
	 * 物料压价
	 */
	@Column(name = "price_down")
    private Double priceDown;
	
	
	/**
	 * 为机工准备的压价
	 */
	@Column(name = "price_down_remark")
    private Double priceDownRemark;
	

	/**
	 * 垫纸的价格
	 */
	@Transient
    private Double paperPrice;
	
	
	/**
	 * 单只绣花价格
	 */
	@Transient
    private Double oneEmbroiderPrice;
	
	

	public Double getOneEmbroiderPrice() {
		return oneEmbroiderPrice;
	}


	public void setOneEmbroiderPrice(Double oneEmbroiderPrice) {
		this.oneEmbroiderPrice = oneEmbroiderPrice;
	}


	public String getAppliqueSize() {
		return appliqueSize;
	}


	public void setAppliqueSize(String appliqueSize) {
		this.appliqueSize = appliqueSize;
	}


	public String getAppliqueTime() {
		return appliqueTime;
	}


	public void setAppliqueTime(String appliqueTime) {
		this.appliqueTime = appliqueTime;
	}


	public Long getTailorId() {
		return tailorId;
	}


	public void setTailorId(Long tailorId) {
		this.tailorId = tailorId;
	}


	public Double getPaperPrice() {
		return paperPrice;
	}


	public void setPaperPrice(Double paperPrice) {
		this.paperPrice = paperPrice;
	}


	public Double getSizeTwo() {
		return sizeTwo;
	}


	public void setSizeTwo(Double sizeTwo) {
		this.sizeTwo = sizeTwo;
	}


	public Double getAffirmSizeTwo() {
		return affirmSizeTwo;
	}


	public void setAffirmSizeTwo(Double affirmSizeTwo) {
		this.affirmSizeTwo = affirmSizeTwo;
	}


	public String getEmbroideryColor() {
		return embroideryColor;
	}


	public void setEmbroideryColor(String embroideryColor) {
		this.embroideryColor = embroideryColor;
	}


	public Integer getEmbroideryColorNumber() {
		return embroideryColorNumber;
	}


	public void setEmbroideryColorNumber(Integer embroideryColorNumber) {
		this.embroideryColorNumber = embroideryColorNumber;
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


	public String getEmbroideryName() {
		return embroideryName;
	}


	public void setEmbroideryName(String embroideryName) {
		this.embroideryName = embroideryName;
	}


	public Integer getNeedleNumber() {
		return needleNumber;
	}


	public void setNeedleNumber(Integer needleNumber) {
		this.needleNumber = needleNumber;
	}


	public String getNeedlesize() {
		return needlesize;
	}


	public void setNeedlesize(String needlesize) {
		this.needlesize = needlesize;
	}


	public String getWiresize() {
		return wiresize;
	}


	public void setWiresize(String wiresize) {
		this.wiresize = wiresize;
	}


	public Integer getApplique() {
		return applique;
	}


	public void setApplique(Integer applique) {
		this.applique = applique;
	}


	public Double getSize() {
		return size;
	}


	public void setSize(Double size) {
		this.size = size;
	}


	public Double getAffirmSize() {
		return affirmSize;
	}


	public void setAffirmSize(Double affirmSize) {
		this.affirmSize = affirmSize;
	}


	public Integer getMembrane() {
		return membrane;
	}


	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}


	public String getPackingPaper() {
		return packingPaper;
	}


	public void setPackingPaper(String packingPaper) {
		this.packingPaper = packingPaper;
	}


	public String getEmbroideryMode() {
		return embroideryMode;
	}


	public void setEmbroideryMode(String embroideryMode) {
		this.embroideryMode = embroideryMode;
	}


	public String getCloth() {
		return cloth;
	}


	public void setCloth(String cloth) {
		this.cloth = cloth;
	}


	public Integer getEmbroiderySlice() {
		return embroiderySlice;
	}


	public void setEmbroiderySlice(Integer embroiderySlice) {
		this.embroiderySlice = embroiderySlice;
	}


	public Integer getFew() {
		return few;
	}


	public void setFew(Integer few) {
		this.few = few;
	}


	public Integer getEmbroideryNeedlesize() {
		return embroideryNeedlesize;
	}


	public void setEmbroideryNeedlesize(Integer embroideryNeedlesize) {
		this.embroideryNeedlesize = embroideryNeedlesize;
	}


	

	public String getEmbroideryWiresize() {
		return embroideryWiresize;
	}


	public void setEmbroideryWiresize(String embroideryWiresize) {
		this.embroideryWiresize = embroideryWiresize;
	}


	public Double getSinglechipApplique() {
		return singlechipApplique;
	}


	public void setSinglechipApplique(Double singlechipApplique) {
		this.singlechipApplique = singlechipApplique;
	}


	public Double getCostPrice() {
		return costPrice;
	}


	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}


	public Double getAllCostPrice() {
		return allCostPrice;
	}


	public void setAllCostPrice(Double allCostPrice) {
		this.allCostPrice = allCostPrice;
	}


	public Double getScaleMaterial() {
		return scaleMaterial;
	}


	public void setScaleMaterial(Double scaleMaterial) {
		this.scaleMaterial = scaleMaterial;
	}


	public Double getPriceDown() {
		return priceDown;
	}


	public void setPriceDown(Double priceDown) {
		this.priceDown = priceDown;
	}


	public Double getPriceDownRemark() {
		return priceDownRemark;
	}


	public void setPriceDownRemark(Double priceDownRemark) {
		this.priceDownRemark = priceDownRemark;
	}


	public Double getTime() {
		return time;
	}


	public void setTime(Double time) {
		this.time = time;
	}


	public Double getPasterTime() {
		return pasterTime;
	}


	public void setPasterTime(Double pasterTime) {
		this.pasterTime = pasterTime;
	}


	public Double getVentilatorStationTime() {
		return ventilatorStationTime;
	}


	public void setVentilatorStationTime(Double ventilatorStationTime) {
		this.ventilatorStationTime = ventilatorStationTime;
	}


	public Double getCuttingLineTime() {
		return cuttingLineTime;
	}


	public void setCuttingLineTime(Double cuttingLineTime) {
		this.cuttingLineTime = cuttingLineTime;
	}


	public Double getLineDosage() {
		return lineDosage;
	}


	public void setLineDosage(Double lineDosage) {
		this.lineDosage = lineDosage;
	}


	public Double getPackingPaperDosage() {
		return packingPaperDosage;
	}


	public void setPackingPaperDosage(Double packingPaperDosage) {
		this.packingPaperDosage = packingPaperDosage;
	}


	public Double getMembraneDosage() {
		return membraneDosage;
	}


	public void setMembraneDosage(Double membraneDosage) {
		this.membraneDosage = membraneDosage;
	}


	public Double getEmbroideryPrice() {
		return embroideryPrice;
	}


	public void setEmbroideryPrice(Double embroideryPrice) {
		this.embroideryPrice = embroideryPrice;
	}


	public Double getPackingPaperPrice() {
		return packingPaperPrice;
	}


	public void setPackingPaperPrice(Double packingPaperPrice) {
		this.packingPaperPrice = packingPaperPrice;
	}


	public Double getMembranePrice() {
		return membranePrice;
	}


	public void setMembranePrice(Double membranePrice) {
		this.membranePrice = membranePrice;
	}


	public Double getReckoningPrice() {
		return reckoningPrice;
	}


	public void setReckoningPrice(Double reckoningPrice) {
		this.reckoningPrice = reckoningPrice;
	}


	public Double getCutLinePrice() {
		return cutLinePrice;
	}


	public void setCutLinePrice(Double cutLinePrice) {
		this.cutLinePrice = cutLinePrice;
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


	public Double getReckoningEmbroideryPrice() {
		return reckoningEmbroideryPrice;
	}


	public void setReckoningEmbroideryPrice(Double reckoningEmbroideryPrice) {
		this.reckoningEmbroideryPrice = reckoningEmbroideryPrice;
	}


	public Double getReckoningSewingPrice() {
		return reckoningSewingPrice;
	}


	public void setReckoningSewingPrice(Double reckoningSewingPrice) {
		this.reckoningSewingPrice = reckoningSewingPrice;
	}


	public String getSizeName() {
		return sizeName;
	}


	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}


	public String getSizeTwoName() {
		return sizeTwoName;
	}


	public void setSizeTwoName(String sizeTwoName) {
		this.sizeTwoName = sizeTwoName;
	}
	
	
	
	
	

}
