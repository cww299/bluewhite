package com.bluewhite.product.primecostbasedata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 
 * 裁剪页面的基础系数
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_prime_coefficient")
public class PrimeCoefficient extends BaseEntity<Long>{
	
	/**
	 *类型
	 */
	@Column(name = "type")
	private String type;
	
	
	/**
	 * (绣花激光固定数值)
	 * 绣花激光撕片/片
	 */
	@Column(name = "embroideryLaser_number")
	private Double embroideryLaserNumber;
	
	/**
	 * (绣花激光固定数值)
	 * 周长小于左侧
	 */
	@Column(name = "perimeter_less")
	private Double perimeterLess;
	
	/**
	 * (绣花激光固定数值)
	 * 周长小于左侧
	 */
	@Column(name = "perimeter_less_number")
	private Double perimeterLessNumber;
	
	
	/**
	 *当下周边地区激光每米/元
	 */
	@Column(name = "peripheral_laser")
	private Double peripheralLaser;
	
	/**
	 *激光机秒走CM
	 */
	@Column(name = "extent")
	private Double extent;
	
	/**
	 *每CM 用时/秒
	 */
	@Column(name = "time")
	private Double time;
	
	/**
	 *激光片每个停顿点用秒？
	 */
	@Column(name = "pause_time")
	private Double pauseTime;
	
	/**
	 *1.5M*1M拉布平铺时间
	 */
	@Column(name = "rabb_time")
	private Double rabbTime;
	
	/**
	 * 被/数
	 */
	@Column(name = "quilt")
	private Double quilt;
	
	/**
	 *放快手比
	 */
	@Column(name = "quick_worker")
	private Double quickWorker;
	
	/**
	 *激光机设备价值
	 */
	@Column(name = "worth")
	private Double worth;
	
	/**
	 *每秒设备折旧费用
	 */
	@Column(name = "depreciation")
	private Double depreciation;
	
	/**
	 *设置分摊天数
	 */
	@Column(name = "share_day")
	private Double shareDay;
	
	/**
	 *每天机器工作时间设置/小时
	 */
	@Column(name = "work_time")
	private Double workTime;
	
	/**
	 *激光管费用(刀模费用,电烫板费用,刀片费用，小零件费用)
	 */
	@Column(name = "laser_tube_price")
	private Double laserTubePrice;
	
	/**
	 *每秒激光管费用(每秒刀模费用,每秒电烫费用,每秒刀片费用，每秒小零件费用)
	 */
	@Column(name = "laser_tube_price_second")
	private Double laserTubePriceSecond;
	
	/**
	 *分摊小时
	 */
	@Column(name = "share_time")
	private Double shareTime;
	
	/**
	 *每秒维护费用
	 */
	@Column(name = "maintenance_charge_second")
	private Double maintenanceChargeSecond;
	
	/**
	 *维护费用
	 */
	@Column(name = "maintenance_charge")
	private Double maintenanceCharge;
	
	/**
	 *分摊小时two
	 */
	@Column(name = "share_time_two")
	private Double shareTimeTwo;
	
	/**
	 *每小时耗电/元
	 */
	@Column(name = "omn_hor_electric")
	private Double omnHorElectric;
	
	/**
	 *每小时耗水/元
	 */
	@Column(name = "omn_hor_water")
	private Double omnHorWater;
	
	/**
	 *每小时耗房租/元
	 */
	@Column(name = "omn_hor_house")
	private Double omnHorHouse;
	
	/**
	 *每秒耗3费
	 */
	@Column(name = "per_second_price")
	private Double perSecondPrice;
	
	
	/**
	 *每秒耗3费(针工页面)
	 */
	@Column(name = "per_second_price_two")
	private Double perSecondPriceTwo;

	
	/**
	 *每小时站机工价
	 */
	@Column(name = "omn_hor_machinist")
	private Double omnHorMachinist;
	
	/**
	 *每秒工价
	 */
	@Column(name = "per_second_machinist")
	private Double perSecondMachinist;
	
	
	/**
	 *每小时辅助工价（电烫，机缝,绣花）
	 */
	@Column(name = "omn_hor_auxiliary")
	private Double omnHorAuxiliary;
	
	/**
	 *每秒工价（电烫，机缝，绣花）
	 */
	@Column(name = "per_second_machinist_two")
	private Double perSecondMachinistTwo;
	
	
	/**
	 *制版分配任务管理人员工资
	 */
	@Column(name = "manage_price")
	private Double managePrice;
	
	/**
	 *每秒管理费用
	 */
	@Column(name = "per_second_manage")
	private Double perSecondManage;
	
	/**
	 *管理设备数量
	 */
	@Column(name = "manage_equipment_number")
	private Double manageEquipmentNumber;
	
	/**
	 *设置激光设备利润比（设置车间利润比）
	 */
	@Column(name = "equipment_profit")
	private Double equipmentProfit;
	
	
	/**** 冲床固定系数 ****/
	/**
	 * 每层拉布时间/秒
	 */
	@Column(name = "puncher_one")
	private Double puncherOne;
	
	/**
	 * 默认最少冲量
	 */
	@Column(name = "puncher_two")
	private Double puncherTwo;
	
	/**
	 * 每层拉布宽度/米
	 */
	@Column(name = "puncher_three")
	private Double puncherThree;
	
	/**
	 * 默认批量少于冲量的叠布和冲压秒数
	 */
	@Column(name = "puncher_four")
	private Double puncherFour;
	
	/** 
	 * 冲压秒数
	 *
	 */
	@Column(name = "puncher_five")
	private Double puncherFive;
	

	
	/**** 电烫perm固定系数 ****/
	/**
	 * 每层拉布时间/秒
	 */
	@Column(name = "perm_one")
	private Double permOne;
	/**
	 * 每层拉布宽度/米
	 */
	@Column(name = "perm_two")
	private Double permTwo;
	
	/**
	 * 电烫秒数
	 */
	@Column(name = "perm_three")
	private Double permThree;

	/**
	 * 请选择撕片难易↓(0=易，1=难)
	 */
	@Column(name = "perm_four")
	private Integer permFour;
	
	/**
	 * 每层拉布宽度/米(ad7)
	 */
	@Column(name = "perm_five")
	private Double permFive;
	
	
	/**** 电推electricPush固定系数 ****/
	/**
	 * 每层拉布时间/秒
	 */
	@Column(name = "electricPush_one")
	private Double electricPushOne;
	/**
	 * 每层拉布宽度/米
	 */
	@Column(name = "electricPush_two")
	private Double electricPushTwo;
	
	/**
	 * 固定边缘/秒
	 */
	@Column(name = "electricPush_three")
	private Double electricPushThree;

	/**
	 *画版时间/片/秒
	 */
	@Column(name = "electricPush_four")
	private Integer electricPushFour;
	
	/**
	 * 电推机秒走CM?
	 */
	@Column(name = "electricPush_five")
	private Double electricPushFive;
	
	/**
	 *每CM 用时/秒
	 */
	@Column(name = "electricPush_six")
	private Double electricPushSix;
	
	/**** 手工剪刀manual固定系数 ****/
	/**
	 * 手剪每秒走CM?
	 */
	@Column(name = "manual_one")
	private Double manualOne;
	/**
	 * 每CM 用时/秒
	 */
	@Column(name = "manual_two")
	private Double manualTwo;
	
	
	/*** 机缝时间   *****/	
	/**
	 * 黏片时间/片/秒
	 */
	@Column(name = "machinist_one")
	private Double machinistOne;
	
	/**
	 * 剪线时间/片/秒
	 */
	@Column(name = "machinist_two")
	private Double machinisttwo;
	
	/**
	 *回针时间/个/秒 
	 */
	@Column(name = "machinist_three")
	private Double machinistThree;
	
	/****   绣花页面   *****/
	
	/**
	 * 薄膜每平价格
	 */
	@Column(name = "embroidery_one")
	private Double embroideryOne;
	
	/**
	 * 每1000针机走时间/秒
	 */
	@Column(name = "embroidery_two")
	private Double embroideryTwo;
	
	/**
	 * 整布绣上绷子铺料铺薄膜时间/秒
	 */
	@Column(name = "embroidery_three")
	private Double embroideryThree;
	
	/**
	 * 裁片秀上绷子贴裁片时间/秒
	 */
	@Column(name = "embroidery_four")
	private Double embroideryFour;
	
	/**
	 *每一贴布/秒
	 */
	@Column(name = "embroidery_five")
	private Double embroideryFive;
	
	/**
	 *单个线头剪时间/秒
	 */
	@Column(name = "embroidery_six")
	private Double embroiderySix;
	
	/**
	 *绣花线每坨价格
	 */
	@Column(name = "embroidery_seven")
	private Double embroiderySeven;
	
	/**
	 *每坨米数
	 */
	@Column(name = "embroidery_eight")
	private Double embroideryEight;
	
	/**
	 *每米价格
	 */
	@Column(name = "embroidery_nine")
	private Double embroideryNine;
	
	/**
	 *每1000针用多少米？
	 */
	@Column(name = "embroidery_ten")
	private Double embroideryTen;
	
	/**
	 *每1针用米？
	 */
	@Column(name = "embroidery_eleven")
	private Double embroideryEleven;
	
	/**
	 *1000针价格
	 */
	@Column(name = "embroidery_twelve")
	private Double embroideryTwelve;
	
	/**
	 *1贴布价格
	 */
	@Column(name = "embroidery_thirteen")
	private Double embroideryThirteen;

	/**
	 *每加一套线色价格
	 */
	@Column(name = "embroidery_fourteen")
	private Double embroideryFourteen;

	/****针工*****/
	/**
	 *	设定同时参与冲棉人员数量
	 */
	@Column(name = "needlework_one")
	private Double needleworkOne;
	
	/**
	 *	冲棉间每小时耗电/元
	 */
	@Column(name = "needlework_two")
	private Double needleworkTwo;
	
	/**
	 *	冲棉间每小时耗水/元
	 */
	@Column(name = "needlework_three")
	private Double needleworkThree;
	
	/**
	 *	冲棉间每小时耗房租/元
	 */
	@Column(name = "needlework_four")
	private Double needleworkFour;
	
	/**
	 *	每小时高端针工工价（面部表情，绣鼻子等）
	 */
	@Column(name = "needlework_five")
	private Double needleworkFive;
	
	/**
	 *	每小时一般针工工价（普通工序）
	 */
	@Column(name = "needlework_six")
	private Double needleworkSix;
	
	/**
	 *	(每小时高端针工工价（面部表情，绣鼻子等）)每秒工价（1等技术工）
	 */
	@Column(name = "needlework_seven")
	private Double needleworkSeven;
	
	/**
	 *	(每小时一般针工工价（普通工序）)每秒工价（2等技术工）
	 */
	@Column(name = "needlework_eight")
	private Double needleworkEight;
	



	public Double getNeedleworkFive() {
		return needleworkFive;
	}

	public void setNeedleworkFive(Double needleworkFive) {
		this.needleworkFive = needleworkFive;
	}

	public Double getNeedleworkSix() {
		return needleworkSix;
	}

	public void setNeedleworkSix(Double needleworkSix) {
		this.needleworkSix = needleworkSix;
	}

	public Double getNeedleworkSeven() {
		return needleworkSeven;
	}

	public void setNeedleworkSeven(Double needleworkSeven) {
		this.needleworkSeven = needleworkSeven;
	}

	public Double getNeedleworkEight() {
		return needleworkEight;
	}

	public void setNeedleworkEight(Double needleworkEight) {
		this.needleworkEight = needleworkEight;
	}

	public Double getNeedleworkOne() {
		return needleworkOne;
	}

	public void setNeedleworkOne(Double needleworkOne) {
		this.needleworkOne = needleworkOne;
	}

	public Double getNeedleworkTwo() {
		return needleworkTwo;
	}

	public void setNeedleworkTwo(Double needleworkTwo) {
		this.needleworkTwo = needleworkTwo;
	}

	public Double getNeedleworkThree() {
		return needleworkThree;
	}

	public void setNeedleworkThree(Double needleworkThree) {
		this.needleworkThree = needleworkThree;
	}

	public Double getNeedleworkFour() {
		return needleworkFour;
	}

	public void setNeedleworkFour(Double needleworkFour) {
		this.needleworkFour = needleworkFour;
	}

	public Double getPerSecondPriceTwo() {
		return perSecondPriceTwo;
	}

	public void setPerSecondPriceTwo(Double perSecondPriceTwo) {
		this.perSecondPriceTwo = perSecondPriceTwo;
	}

	public Double getOmnHorAuxiliary() {
		return omnHorAuxiliary;
	}

	public void setOmnHorAuxiliary(Double omnHorAuxiliary) {
		this.omnHorAuxiliary = omnHorAuxiliary;
	}

	public Double getEmbroiderySeven() {
		return embroiderySeven;
	}

	public void setEmbroiderySeven(Double embroiderySeven) {
		this.embroiderySeven = embroiderySeven;
	}

	public Double getEmbroideryEight() {
		return embroideryEight;
	}

	public void setEmbroideryEight(Double embroideryEight) {
		this.embroideryEight = embroideryEight;
	}

	public Double getEmbroideryNine() {
		return embroideryNine;
	}

	public void setEmbroideryNine(Double embroideryNine) {
		this.embroideryNine = embroideryNine;
	}

	public Double getEmbroideryTen() {
		return embroideryTen;
	}

	public void setEmbroideryTen(Double embroideryTen) {
		this.embroideryTen = embroideryTen;
	}

	public Double getEmbroideryEleven() {
		return embroideryEleven;
	}

	public void setEmbroideryEleven(Double embroideryEleven) {
		this.embroideryEleven = embroideryEleven;
	}

	public Double getEmbroideryTwelve() {
		return embroideryTwelve;
	}

	public void setEmbroideryTwelve(Double embroideryTwelve) {
		this.embroideryTwelve = embroideryTwelve;
	}

	public Double getEmbroideryThirteen() {
		return embroideryThirteen;
	}

	public void setEmbroideryThirteen(Double embroideryThirteen) {
		this.embroideryThirteen = embroideryThirteen;
	}

	public Double getEmbroideryFourteen() {
		return embroideryFourteen;
	}

	public void setEmbroideryFourteen(Double embroideryFourteen) {
		this.embroideryFourteen = embroideryFourteen;
	}

	public Double getEmbroideryOne() {
		return embroideryOne;
	}

	public void setEmbroideryOne(Double embroideryOne) {
		this.embroideryOne = embroideryOne;
	}

	public Double getEmbroideryTwo() {
		return embroideryTwo;
	}

	public void setEmbroideryTwo(Double embroideryTwo) {
		this.embroideryTwo = embroideryTwo;
	}

	public Double getEmbroideryThree() {
		return embroideryThree;
	}

	public void setEmbroideryThree(Double embroideryThree) {
		this.embroideryThree = embroideryThree;
	}

	public Double getEmbroideryFour() {
		return embroideryFour;
	}

	public void setEmbroideryFour(Double embroideryFour) {
		this.embroideryFour = embroideryFour;
	}

	public Double getEmbroideryFive() {
		return embroideryFive;
	}

	public void setEmbroideryFive(Double embroideryFive) {
		this.embroideryFive = embroideryFive;
	}

	public Double getEmbroiderySix() {
		return embroiderySix;
	}

	public void setEmbroiderySix(Double embroiderySix) {
		this.embroiderySix = embroiderySix;
	}

	public Double getPerSecondMachinistTwo() {
		return perSecondMachinistTwo;
	}

	public void setPerSecondMachinistTwo(Double perSecondMachinistTwo) {
		this.perSecondMachinistTwo = perSecondMachinistTwo;
	}

	public Double getMachinistOne() {
		return machinistOne;
	}

	public void setMachinistOne(Double machinistOne) {
		this.machinistOne = machinistOne;
	}

	public Double getMachinisttwo() {
		return machinisttwo;
	}

	public void setMachinisttwo(Double machinisttwo) {
		this.machinisttwo = machinisttwo;
	}

	public Double getMachinistThree() {
		return machinistThree;
	}

	public void setMachinistThree(Double machinistThree) {
		this.machinistThree = machinistThree;
	}

	public Double getManualOne() {
		return manualOne;
	}

	public void setManualOne(Double manualOne) {
		this.manualOne = manualOne;
	}

	public Double getManualTwo() {
		return manualTwo;
	}

	public void setManualTwo(Double manualTwo) {
		this.manualTwo = manualTwo;
	}

	public Double getElectricPushOne() {
		return electricPushOne;
	}

	public void setElectricPushOne(Double electricPushOne) {
		this.electricPushOne = electricPushOne;
	}

	public Double getElectricPushTwo() {
		return electricPushTwo;
	}

	public void setElectricPushTwo(Double electricPushTwo) {
		this.electricPushTwo = electricPushTwo;
	}

	public Double getElectricPushThree() {
		return electricPushThree;
	}

	public void setElectricPushThree(Double electricPushThree) {
		this.electricPushThree = electricPushThree;
	}

	public Integer getElectricPushFour() {
		return electricPushFour;
	}

	public void setElectricPushFour(Integer electricPushFour) {
		this.electricPushFour = electricPushFour;
	}

	public Double getElectricPushFive() {
		return electricPushFive;
	}

	public void setElectricPushFive(Double electricPushFive) {
		this.electricPushFive = electricPushFive;
	}

	public Double getElectricPushSix() {
		return electricPushSix;
	}

	public void setElectricPushSix(Double electricPushSix) {
		this.electricPushSix = electricPushSix;
	}

	public Double getPermOne() {
		return permOne;
	}

	public void setPermOne(Double permOne) {
		this.permOne = permOne;
	}

	public Double getPermTwo() {
		return permTwo;
	}

	public void setPermTwo(Double permTwo) {
		this.permTwo = permTwo;
	}

	public Double getPermThree() {
		return permThree;
	}

	public void setPermThree(Double permThree) {
		this.permThree = permThree;
	}

	public Integer getPermFour() {
		return permFour;
	}

	public void setPermFour(Integer permFour) {
		this.permFour = permFour;
	}

	public Double getPermFive() {
		return permFive;
	}

	public void setPermFive(Double permFive) {
		this.permFive = permFive;
	}

	public Double getPuncherOne() {
		return puncherOne;
	}

	public void setPuncherOne(Double puncherOne) {
		this.puncherOne = puncherOne;
	}

	public Double getPuncherTwo() {
		return puncherTwo;
	}

	public void setPuncherTwo(Double puncherTwo) {
		this.puncherTwo = puncherTwo;
	}


	public Double getPuncherThree() {
		return puncherThree;
	}

	public void setPuncherThree(Double puncherThree) {
		this.puncherThree = puncherThree;
	}

	public Double getPuncherFour() {
		return puncherFour;
	}

	public void setPuncherFour(Double puncherFour) {
		this.puncherFour = puncherFour;
	}

	public Double getPuncherFive() {
		return puncherFive;
	}

	public void setPuncherFive(Double puncherFive) {
		this.puncherFive = puncherFive;
	}

	public Double getPeripheralLaser() {
		return peripheralLaser;
	}

	public void setPeripheralLaser(Double peripheralLaser) {
		this.peripheralLaser = peripheralLaser;
	}

	public Double getExtent() {
		return extent;
	}

	public void setExtent(Double extent) {
		this.extent = extent;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(Double pauseTime) {
		this.pauseTime = pauseTime;
	}

	public Double getRabbTime() {
		return rabbTime;
	}

	public void setRabbTime(Double rabbTime) {
		this.rabbTime = rabbTime;
	}

	public Double getQuilt() {
		return quilt;
	}

	public void setQuilt(Double quilt) {
		this.quilt = quilt;
	}

	public Double getQuickWorker() {
		return quickWorker;
	}

	public void setQuickWorker(Double quickWorker) {
		this.quickWorker = quickWorker;
	}

	public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
	}

	public Double getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public Double getShareDay() {
		return shareDay;
	}

	public void setShareDay(Double shareDay) {
		this.shareDay = shareDay;
	}

	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}

	public Double getLaserTubePrice() {
		return laserTubePrice;
	}

	public void setLaserTubePrice(Double laserTubePrice) {
		this.laserTubePrice = laserTubePrice;
	}

	public Double getLaserTubePriceSecond() {
		return laserTubePriceSecond;
	}

	public void setLaserTubePriceSecond(Double laserTubePriceSecond) {
		this.laserTubePriceSecond = laserTubePriceSecond;
	}

	public Double getShareTime() {
		return shareTime;
	}

	public void setShareTime(Double shareTime) {
		this.shareTime = shareTime;
	}

	public Double getMaintenanceChargeSecond() {
		return maintenanceChargeSecond;
	}

	public void setMaintenanceChargeSecond(Double maintenanceChargeSecond) {
		this.maintenanceChargeSecond = maintenanceChargeSecond;
	}

	public Double getMaintenanceCharge() {
		return maintenanceCharge;
	}

	public void setMaintenanceCharge(Double maintenanceCharge) {
		this.maintenanceCharge = maintenanceCharge;
	}

	public Double getShareTimeTwo() {
		return shareTimeTwo;
	}

	public void setShareTimeTwo(Double shareTimeTwo) {
		this.shareTimeTwo = shareTimeTwo;
	}

	public Double getOmnHorElectric() {
		return omnHorElectric;
	}

	public void setOmnHorElectric(Double omnHorElectric) {
		this.omnHorElectric = omnHorElectric;
	}

	public Double getOmnHorWater() {
		return omnHorWater;
	}

	public void setOmnHorWater(Double omnHorWater) {
		this.omnHorWater = omnHorWater;
	}

	public Double getPerSecondPrice() {
		return perSecondPrice;
	}

	public void setPerSecondPrice(Double perSecondPrice) {
		this.perSecondPrice = perSecondPrice;
	}

	public Double getOmnHorHouse() {
		return omnHorHouse;
	}

	public void setOmnHorHouse(Double omnHorHouse) {
		this.omnHorHouse = omnHorHouse;
	}

	public Double getOmnHorMachinist() {
		return omnHorMachinist;
	}

	public void setOmnHorMachinist(Double omnHorMachinist) {
		this.omnHorMachinist = omnHorMachinist;
	}

	public Double getPerSecondMachinist() {
		return perSecondMachinist;
	}

	public void setPerSecondMachinist(Double perSecondMachinist) {
		this.perSecondMachinist = perSecondMachinist;
	}

	public Double getManagePrice() {
		return managePrice;
	}

	public void setManagePrice(Double managePrice) {
		this.managePrice = managePrice;
	}

	public Double getPerSecondManage() {
		return perSecondManage;
	}

	public void setPerSecondManage(Double perSecondManage) {
		this.perSecondManage = perSecondManage;
	}

	public Double getManageEquipmentNumber() {
		return manageEquipmentNumber;
	}

	public void setManageEquipmentNumber(Double manageEquipmentNumber) {
		this.manageEquipmentNumber = manageEquipmentNumber;
	}


	public Double getEquipmentProfit() {
		return equipmentProfit;
	}

	public void setEquipmentProfit(Double equipmentProfit) {
		this.equipmentProfit = equipmentProfit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getEmbroideryLaserNumber() {
		return embroideryLaserNumber;
	}

	public void setEmbroideryLaserNumber(Double embroideryLaserNumber) {
		this.embroideryLaserNumber = embroideryLaserNumber;
	}

	public Double getPerimeterLess() {
		return perimeterLess;
	}

	public void setPerimeterLess(Double perimeterLess) {
		this.perimeterLess = perimeterLess;
	}

	public Double getPerimeterLessNumber() {
		return perimeterLessNumber;
	}

	public void setPerimeterLessNumber(Double perimeterLessNumber) {
		this.perimeterLessNumber = perimeterLessNumber;
	}
	
	
	

}
