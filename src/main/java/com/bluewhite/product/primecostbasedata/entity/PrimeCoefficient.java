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
	 *被/数
	 */
	@Column(name = "quilt")
	private Double quilt;
	
	/**
	 *激光机放快手比
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
	 *激光管费用
	 */
	@Column(name = "laser_tube_price")
	private Double laserTubePrice;
	
	/**
	 *每秒激光管费用
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
	 *每秒耗3费
	 */
	@Column(name = "per_second_price")
	private Double perSecondPrice;
	
	/**
	 *每小时耗房租/元
	 */
	@Column(name = "omn_hor_house")
	private Double omnHorHouse;
	
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
	 *设置激光设备利润比
	 */
	@Column(name = "equipment_profit")
	private Double equipmentProfit;

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
