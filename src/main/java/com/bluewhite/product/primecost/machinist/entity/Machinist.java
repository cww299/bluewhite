package com.bluewhite.product.primecost.machinist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 机工页面
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_machinist")
public class Machinist extends BaseEntity<Long> {

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
	 * 缝纫步骤名称
	 */
	@Column(name = "machinist_name")
	private String machinistName;
	
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
	 * 物料和上道压（裁剪）价
	 */
	@Column(name = "price_down")
    private Double priceDown;
	
	
	/**
	 * 机工的压价总和
	 */
	@Column(name = "sum_price_down_remark")
    private Double sumPriceDownRemark;
	
	/**
	 * 物料和上道压（裁剪,上道机工）价-(只有在当行机工环节单独发放给某个加工店，这个才起作用)
	 */
	@Column(name = "price_down_remark")
    private Double priceDownRemark;
	
	
	
	/**
	 *为针工准备的压价
	 */
	@Column(name = "needlework_price_down")
    private Double needleworkPriceDown;
	
	/**
	 * 单独机工工序外发的压价
	 */
	@Column(name = "machinist_price_down")
    private Double machinistPriceDown;
	
	/**
	 * 用除裁片以外物料
	 */
	@Column(name = "materials")
    private String materials;
	
	
	/**
	 * 用到裁片或上道数量
	 */
	@Column(name = "cutparts_number")
    private Integer cutpartsNumber;
	
	/**
	 * 用到裁片或上道(多个，以逗号分隔)
	 */
	@Column(name = "cutparts")
    private String cutparts;
	
	/**
	 * 用到裁片或上道的压价(多个，以逗号分隔)
	 */
	@Column(name = "cutparts_price")
    private String cutpartsPrice;
	
	/****机封时间 ***/
	/**
	 * 选择针号
	 */
	@Column(name = "needlesize")
    private String needlesize;
	
	/**
	 * 选择线色或线号
	 */
	@Column(name = "wiresize")
    private String wiresize;
	
	
	/**
	 * 选择针距↓
	 */
	@Column(name = "needleSpur")
    private Integer needlespur;
	
	/**
	 * 得到试制净快手时间
	 */
	@Column(name = "time")
    private Double time;
	
	/**
	 *手填该工序回针次数
	 */
	@Column(name = "backStitch_count")
    private Integer backStitchCount;
	
	/**
	 * 1请选直线机缝模式↓
	 */
	@Column(name = "beeline")
    private String beeline;
	
	/**
	 * 手填该工序满足G列的CM↓
	 */
	@Column(name = "beeline_number")
    private Double beelineNumber;
	
	/**
	 * 2请选弧线机缝模式
	 */
	@Column(name = "arc")
    private String arc;
	
	/**
	 * 手填该工序满足I列的CM↓
	 */
	@Column(name = "arc_number")
    private Double arcNumber;
	
	/**
	 * 3请选弯曲复杂机缝模式↓
	 */
	@Column(name = "bend")
    private String bend;
	
	
	/**
	 * 手填该工序满足K列的CM↓
	 */
	@Column(name = "bend_number")
    private Double bendNumber;
	
	/**
	 * 单一机缝需要时间/秒(s)
	 */
	@Column(name = "one_sewing_time")
	private Double oneSewingTime;
	
	/**
	 * 剪线时间/秒(s)
	 */
	@Column(name = "cut_line_time")
	private Double cutLineTime;
	
	/**
	 * 单片机缝放快手时间
	 */
	@Column(name = "sewing_quick_worker_time")
	private Double sewingQuickWorkerTime;
	/**
	 * 单片剪线放快手时间
	 */
	@Column(name = "line_quick_worker_time")
	private Double lineQuickWorkerTime;
	/**
	 * 试制大工工价（含快手)
	 */
	@Column(name = "trial_produce_price")
	private Double trialProducePrice;
	/**
	 * 电脑推算大工工价（含快手)
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
	 * 电脑推算机缝该工序费用
	 */
	@Column(name = "reckoning_sewing_price")
	private Double reckoningSewingPrice;
	/**
	 * 试制机缝该工序费用
	 */
	@Column(name = "trial_sewing_price")
	private Double trialSewingPrice;
	/**
	 * 时间检查
	 */
	@Column(name = "time_check")
	private Integer timeCheck;
	
	
	/**
	 *该工序涉及回针次数时间/秒
	 */
	@Column(name = "back_stitch")
	private Double backStitch;
	
	
	/**
	 * 该工序涉及粘片次数时间/秒
	 */
	@Column(name = "sticking")
	private Double sticking;
	
	
	/**
	 * 1类模式可走（每CM时间/秒）
	 */
	@Column(name = "mode_one")
	private Double modeOne;
	
	/**
	 * 2类模式可走（每CM时间/秒）
	 */
	@Column(name = "mode_two")
	private Double modeTwo;
	
	/**
	 * 3类模式可走（每CM时间/秒）
	 */
	@Column(name = "mode_three")
	private Double modeThree;
	
	
	/**
	 * 得到basefour中数据1
	 */
	@Column(name = "base_four_date_one")
	private Double baseFourDateOne;
	
	/**
	 * 得到basefour中数据2
	 */
	@Column(name = "base_four_date_two")
	private Double baseFourDateTwo;
	
	
	/**
	 * 得到basefour中数据3
	 */
	@Column(name = "base_four_date_three")
	private Double baseFourDateThree;
	
	

	
	
	public Double getBaseFourDateOne() {
		return baseFourDateOne;
	}

	public void setBaseFourDateOne(Double baseFourDateOne) {
		this.baseFourDateOne = baseFourDateOne;
	}

	public Double getBaseFourDateTwo() {
		return baseFourDateTwo;
	}

	public void setBaseFourDateTwo(Double baseFourDateTwo) {
		this.baseFourDateTwo = baseFourDateTwo;
	}

	public Double getBaseFourDateThree() {
		return baseFourDateThree;
	}

	public void setBaseFourDateThree(Double baseFourDateThree) {
		this.baseFourDateThree = baseFourDateThree;
	}

	public Double getSumPriceDownRemark() {
		return sumPriceDownRemark;
	}

	public void setSumPriceDownRemark(Double sumPriceDownRemark) {
		this.sumPriceDownRemark = sumPriceDownRemark;
	}

	public String getCutpartsPrice() {
		return cutpartsPrice;
	}

	public void setCutpartsPrice(String cutpartsPrice) {
		this.cutpartsPrice = cutpartsPrice;
	}

	public String getBeeline() {
		return beeline;
	}

	public void setBeeline(String beeline) {
		this.beeline = beeline;
	}

	public Double getBeelineNumber() {
		return beelineNumber;
	}

	public void setBeelineNumber(Double beelineNumber) {
		this.beelineNumber = beelineNumber;
	}

	public String getArc() {
		return arc;
	}

	public void setArc(String arc) {
		this.arc = arc;
	}

	public Double getArcNumber() {
		return arcNumber;
	}

	public void setArcNumber(Double arcNumber) {
		this.arcNumber = arcNumber;
	}

	public String getBend() {
		return bend;
	}

	public void setBend(String bend) {
		this.bend = bend;
	}

	public Double getBendNumber() {
		return bendNumber;
	}

	public void setBendNumber(Double bendNumber) {
		this.bendNumber = bendNumber;
	}



	public Integer getBackStitchCount() {
		return backStitchCount;
	}

	public void setBackStitchCount(Integer backStitchCount) {
		this.backStitchCount = backStitchCount;
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



	public Integer getNeedlespur() {
		return needlespur;
	}

	public void setNeedlespur(Integer needlespur) {
		this.needlespur = needlespur;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getBackStitch() {
		return backStitch;
	}

	public void setBackStitch(Double backStitch) {
		this.backStitch = backStitch;
	}

	public Double getSticking() {
		return sticking;
	}

	public void setSticking(Double sticking) {
		this.sticking = sticking;
	}

	public Double getModeOne() {
		return modeOne;
	}

	public void setModeOne(Double modeOne) {
		this.modeOne = modeOne;
	}

	public Double getModeTwo() {
		return modeTwo;
	}

	public void setModeTwo(Double modeTwo) {
		this.modeTwo = modeTwo;
	}

	public Double getModeThree() {
		return modeThree;
	}

	public void setModeThree(Double modeThree) {
		this.modeThree = modeThree;
	}

	public Double getOneSewingTime() {
		return oneSewingTime;
	}

	public void setOneSewingTime(Double oneSewingTime) {
		this.oneSewingTime = oneSewingTime;
	}

	public Double getCutLineTime() {
		return cutLineTime;
	}

	public void setCutLineTime(Double cutLineTime) {
		this.cutLineTime = cutLineTime;
	}

	public Double getSewingQuickWorkerTime() {
		return sewingQuickWorkerTime;
	}

	public void setSewingQuickWorkerTime(Double sewingQuickWorkerTime) {
		this.sewingQuickWorkerTime = sewingQuickWorkerTime;
	}

	public Double getLineQuickWorkerTime() {
		return lineQuickWorkerTime;
	}

	public void setLineQuickWorkerTime(Double lineQuickWorkerTime) {
		this.lineQuickWorkerTime = lineQuickWorkerTime;
	}

	public Double getTrialProducePrice() {
		return trialProducePrice;
	}

	public void setTrialProducePrice(Double trialProducePrice) {
		this.trialProducePrice = trialProducePrice;
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

	public Double getReckoningSewingPrice() {
		return reckoningSewingPrice;
	}

	public void setReckoningSewingPrice(Double reckoningSewingPrice) {
		this.reckoningSewingPrice = reckoningSewingPrice;
	}

	public Double getTrialSewingPrice() {
		return trialSewingPrice;
	}

	public void setTrialSewingPrice(Double trialSewingPrice) {
		this.trialSewingPrice = trialSewingPrice;
	}

	public Integer getTimeCheck() {
		return timeCheck;
	}

	public void setTimeCheck(Integer timeCheck) {
		this.timeCheck = timeCheck;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}


	public Integer getCutpartsNumber() {
		return cutpartsNumber;
	}

	public void setCutpartsNumber(Integer cutpartsNumber) {
		this.cutpartsNumber = cutpartsNumber;
	}

	public String getCutparts() {
		return cutparts;
	}

	public void setCutparts(String cutparts) {
		this.cutparts = cutparts;
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

	public String getMachinistName() {
		return machinistName;
	}

	public void setMachinistName(String machinistName) {
		this.machinistName = machinistName;
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



	public Double getNeedleworkPriceDown() {
		return needleworkPriceDown;
	}

	public void setNeedleworkPriceDown(Double needleworkPriceDown) {
		this.needleworkPriceDown = needleworkPriceDown;
	}

	public Double getMachinistPriceDown() {
		return machinistPriceDown;
	}

	public void setMachinistPriceDown(Double machinistPriceDown) {
		this.machinistPriceDown = machinistPriceDown;
	}
	
	
	
	

}
