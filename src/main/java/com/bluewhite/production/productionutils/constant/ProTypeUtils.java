package com.bluewhite.production.productionutils.constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluewhite.common.Constants;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.entity.ProductionConstant;
import com.bluewhite.production.productionutils.constant.service.ProductionConstantService;
import com.bluewhite.production.task.entity.Task;
/**
 * 生产控制计算工具类
 * @author zhangliang
 *
 */
@Component
public  class ProTypeUtils {
	
	@Autowired
	private  ProductionConstantService service;
	
	private static ProTypeUtils proTypeUtils;
	
    @PostConstruct
    public void init() {
    	proTypeUtils = this;
    	proTypeUtils.service = this.service;
    }
	
	
	
	/**
	 * 时间常量
	 */
	private final static Integer  TIME = 60;
	
	/**
	 * excel正常工序常量
	 */
	private final static double  EXCELONE = 1.08;
	
	
	/**
	 * excel常量
	 */
	private final static double  EXCELTWO = 1.0;
	
	/**
	 * 当部门预计生产价格计算系数 1=一楼质检
	 */
	private final static double  FRIST_QUALITY = 0.00750375;
	/**
	 * 当部门预计生产价格计算系数 2=一楼包装
	 */
	private final static double  FRIST_PACK = 0.00750375;
	/**
	 * 当部门预计生产价格计算系数 3=二楼针工
	 */
	private final static double  TOW_DEEDLE = 0.0067275;
	
	/**
	 * 当部门预计生产价格计算系数 4=二楼机工
	 */
	private final static double  TOW_MACHINIST = 0.00383;
	
	/**
	 * 当部门预计生产价格计算系数 5=八号裁剪
	 */
	private final static double  RIGHT_TAILOR = 0.00750375;
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数1
	 */
	private final static String  QUALITY_STRING = "剪线头";
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数2
	 */
	private final static double  QUALITY_DOUBLE = 0.00621;
	
	/**
	 * 一楼质检
	 * 当外发价格计算系数3
	 */
	private final static double  QUALITY_DOUBLETOW = 0.08;
	
	
	/**
	 * 一楼打包
	 * 当外发价格
	 */
	private final static double  FRIST_PACKTWO = 0.20;
	
	
	/**
	 * 一楼质检
	 * 放快手包装工秒支出(AC8)
	 */
	private static double getAC8(){
		return NumUtils.div(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()*ProTypeUtils.EXCELONE
				,proTypeUtils.service.findByExcelNameAndType("AC3" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC7" , 1).getNumber())
				,NumUtils.mul(ProTypeUtils.TIME,ProTypeUtils.TIME),5);
	}
	
	/********************   一楼质检加绩系数          **************************/
	
	/**
	 * 
	 * 设定小批量浪工1加价比(AD12)
	 */
	private static double getAD12(){
		return  NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber() 
							, proTypeUtils.service.findByExcelNameAndType("AC12" , 1).getNumber())
							, proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定小批量浪工2加价比(AD13)
	 */
	private static double getAD13(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC13" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定小批量浪工3加价比(AD14)
	 */
	private static double getAD14(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC14" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定小批量浪工4加价比(AD15)
	 */
	private static double getAD15(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC15" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	
	
	/**
	 * 正常任务加绩工序显示
	 */
	public static List<Map<String,Object>>  taskPerformance(){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>(4);
		Map<String,Object> mapAD12 = new HashMap<String,Object>();
		mapAD12.put("name", "小批量浪工1上浮10%");
		mapAD12.put("number", NumUtils.round(ProTypeUtils.getAD12()/ProTypeUtils.TIME, null));
		mapList.add(mapAD12);
		Map<String,Object> mapAD13 = new HashMap<String,Object>();
		mapAD13.put("name", "小批量浪工1上浮25%");
		mapAD13.put("number",NumUtils.round( ProTypeUtils.getAD13()/ProTypeUtils.TIME, null));
		mapList.add(mapAD13);
		Map<String,Object> mapAD14 = new HashMap<String,Object>();
		mapAD14.put("name", "小批量浪工1上浮40%");
		mapAD14.put("number",NumUtils.round( ProTypeUtils.getAD14()/ProTypeUtils.TIME, null));
		mapList.add(mapAD14);
		Map<String,Object> mapAD15 = new HashMap<String,Object>();
		mapAD15.put("name", "小批量浪工1上浮60%");
		mapAD15.put("number",NumUtils.round( ProTypeUtils.getAD15()/ProTypeUtils.TIME, null));
		mapList.add(mapAD15);
		return mapList;
	};
	
	
	
	
	
	/********************   一楼质检杂工加绩系数          **************************/
	
	/**
	 * 
	 * 设定精细填写包装工加价比(AD12)
	 */
	private static double getZGAD12(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("ZGAC12" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定装箱包装工加价比(AD13)
	 */
	private static double getZGAD13(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("ZGAC13" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定推货工加价比(AD14)
	 */
	private static double getZGAD14(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("ZGAC14" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 
	 * 设定上下车力工加价比(AD15)
	 */
	private static double getZGAD15(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("ZGAC15" , 1).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 1).getNumber());
	}
	
	/**
	 * 杂工加绩工序显示
	 */
	public static List<Map<String,Object>>  farragoTaskPerformance(){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>(4);
		Map<String,Object> mapAD12 = new HashMap<String,Object>();
		mapAD12.put("name", "精细填写工序");
		mapAD12.put("number", NumUtils.round(ProTypeUtils.getZGAD12()/ProTypeUtils.TIME, null));
		mapList.add(mapAD12);
		Map<String,Object> mapAD13 = new HashMap<String,Object>();
		mapAD13.put("name", "装箱装包工序");
		mapAD13.put("number",NumUtils.round( ProTypeUtils.getZGAD13()/ProTypeUtils.TIME, null));
		mapList.add(mapAD13);
		Map<String,Object> mapAD14 = new HashMap<String,Object>();
		mapAD14.put("name", "推货工序");
		mapAD14.put("number",NumUtils.round( ProTypeUtils.getZGAD14()/ProTypeUtils.TIME, null));
		mapList.add(mapAD14);
		Map<String,Object> mapAD15 = new HashMap<String,Object>();
		mapAD15.put("name", "上下车力工工序");
		mapAD15.put("number",NumUtils.round( ProTypeUtils.getZGAD15()/ProTypeUtils.TIME, null));
		mapList.add(mapAD15);
		return mapList;
	};
	
	
	/******** 一楼包装 ******************************/
	/**
	 * 一楼包装
	 * 放快手包装工秒支出(AC8)
	 */
	private static double getPackAC8(){
		return NumUtils.div(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber()*ProTypeUtils.EXCELONE
				,proTypeUtils.service.findByExcelNameAndType("AC3" , 2).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC7" , 2).getNumber())
				,NumUtils.mul(ProTypeUtils.TIME,ProTypeUtils.TIME),5);
	}
	
	
	
	/**
	 * 
	 * 设定精细填写包装工加价比(AD12)
	 */
	private static double getBZAD12(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("BZAC12" , 2).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber());
	}
	
	/**
	 * 
	 * 设定装箱包装工加价比(AD13)
	 */
	private static double getBZAD13(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("BZAC13" , 2).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber());
	}
	
	/**
	 * 
	 * 设定推货工加价比(AD14)
	 */
	private static double getBZAD14(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("BZAC14" , 2).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber());
	}
	
	/**
	 * 
	 * 设定上下车力工加价比(AD15)
	 */
	private static double getBZAD15(){
		return NumUtils.sub(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("BZAC15" , 2).getNumber())
				,proTypeUtils.service.findByExcelNameAndType("AC5" , 2).getNumber());
	}
	
	/**
	 * 加绩工序显示
	 */
	public static List<Map<String,Object>>  pickTaskPerformance(){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>(4);
		Map<String,Object> mapAD12 = new HashMap<String,Object>();
		mapAD12.put("name", "精细填写工序");
		mapAD12.put("number", NumUtils.round(ProTypeUtils.getBZAD12()/ProTypeUtils.TIME, null));
		mapList.add(mapAD12);
		Map<String,Object> mapAD13 = new HashMap<String,Object>();
		mapAD13.put("name", "装箱装包工序");
		mapAD13.put("number",NumUtils.round( ProTypeUtils.getBZAD13()/ProTypeUtils.TIME, null));
		mapList.add(mapAD13);
		Map<String,Object> mapAD14 = new HashMap<String,Object>();
		mapAD14.put("name", "推货工序");
		mapAD14.put("number",NumUtils.round( ProTypeUtils.getBZAD14()/ProTypeUtils.TIME, null));
		mapList.add(mapAD14);
		Map<String,Object> mapAD15 = new HashMap<String,Object>();
		mapAD15.put("name", "上下车力工工序");
		mapAD15.put("number",NumUtils.round( ProTypeUtils.getBZAD15()/ProTypeUtils.TIME, null));
		mapList.add(mapAD15);
		return mapList;
	};
	
	
	
	/******** 八号仓库  ******************************/
	/**
	 * 八号裁剪
	 * 放快手包装工秒支出(AC8)
	 */
	private static double getAC8N(){
		return NumUtils.div(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 5).getNumber()*ProTypeUtils.EXCELONE
				,proTypeUtils.service.findByExcelNameAndType("AC3" , 5).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC7" , 5).getNumber())
				,NumUtils.mul(ProTypeUtils.TIME,ProTypeUtils.TIME),5);
	}
	
	
	
	
	
	/******** 二楼  ******************************/
	/**
	 * 二楼针工
	 * 放快手包装工秒支出(AC8)
	 */
	private static double getAC8TWO(){
		return  NumUtils.div(NumUtils.mul(proTypeUtils.service.findByExcelNameAndType("AC5" , 3).getNumber()*ProTypeUtils.EXCELONE
				,proTypeUtils.service.findByExcelNameAndType("AC3" , 3).getNumber()
				,proTypeUtils.service.findByExcelNameAndType("AC7" , 3).getNumber())
				,NumUtils.mul(ProTypeUtils.TIME,ProTypeUtils.TIME) , 5);
	}
	
	
	/**
	 * 二楼机工(杂工和返工)
	 * 放快手包装工秒支出(AC8)
	 */
	private static double getAC8TWOREWORK(Double AC5){
		return NumUtils.div(NumUtils.mul((AC5==null ? proTypeUtils.service.findByExcelNameAndType("AC5" , 4).getNumber() : AC5 ) , ProTypeUtils.EXCELTWO
				, proTypeUtils.service.findByExcelNameAndType("AC3" , 4).getNumber()
				, proTypeUtils.service.findByExcelNameAndType("AC7" , 4).getNumber())
				, NumUtils.mul(ProTypeUtils.TIME , ProTypeUtils.TIME),5);
	}
	
	/**
	 *  二楼针工价格
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double getDEEDLE(Double time,Integer type){
		Double sumPrice = 0.0 ;
		switch (type) {
		case 1://生产部一楼质检
			break;
		case 2://生产部一楼打包
				break;
		case 3://生产部二楼针工
			sumPrice = NumUtils.div(NumUtils.mul(NumUtils.sub(time,0.5),1.08,1.15,0.167,1.5), ProTypeUtils.TIME, 5);
			break;
		default:
			break;
		}
		return sumPrice;
	}
	
	
	/********   ******************************  ******************************  ******************************/
	
	/**
	 * 根据不同权限返回工序的不同类型
	 * 1=一楼质检，2=一楼包装，3=二楼针工
	 * @return 
	 */
	public static Integer roleGetProType(){
		CurrentUser cu = SessionManager.getUserSession();
		Integer type = null ;
		// 生产部一楼质检
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_QUALITY)){
			type = 1;
		}
		//生产部一楼打包
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_PACK)){
			type = 2;
		}
		//生产部二楼针工
		if(cu.getRole().contains(Constants.PRODUCT_TWO_DEEDLE)){
			type = 3;
		}
		if(cu.getRole().contains(Constants.PRODUCT_TWO_MACHINIST)){
			type = 4;
		}
		if(cu.getRole().contains(Constants.PRODUCT_TWO_MACHINIST)){
			type = 5;
		}
		return type;
	}
	
	/**
	 * 根据不同的部门，计算出当部门预计生产价格
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumProTypePrice(Double time,Integer type){
		Double sumPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumPrice =  ProTypeUtils.FRIST_QUALITY;
			break;
		case 2://生产部一楼打包
			sumPrice = ProTypeUtils.FRIST_PACK;
				break;
		case 3://生产部二楼针工
			sumPrice = ProTypeUtils.TOW_DEEDLE;
			break;
		case 4://生产部二楼机工
			sumPrice = ProTypeUtils.TOW_MACHINIST;
			break;
		case 5://8号裁剪
			sumPrice = ProTypeUtils.RIGHT_TAILOR;
			break;
		default:
			break;
		}
		return NumUtils.mul(time, sumPrice);
	}
	
	/**
	 * 根据不同的部门，计算出外发价格
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumProTypeHairPrice(List<Procedure> procedureList, Integer type) {
		Double sumPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			for(Procedure procedure : procedureList){
				if(procedure.getName().equals(ProTypeUtils.QUALITY_STRING)){
					sumPrice = NumUtils.sum(NumUtils.mul(procedure.getWorkingTime(),ProTypeUtils.QUALITY_DOUBLE),ProTypeUtils.QUALITY_DOUBLETOW);
				}
			}
			break;
		case 2://生产部一楼打包
			sumPrice=ProTypeUtils.FRIST_PACKTWO;
			break;
		case 3://生产部二楼针工
			for(Procedure procedure : procedureList){
				if(procedure.getHairPrice()!=null){
					sumPrice = procedure.getHairPrice();
				}
			}
			break;
		case 4://生产部二楼机工
			for(Procedure procedure : procedureList){
				if(procedure.getHairPrice()!=null){
					sumPrice = procedure.getHairPrice();
				}
			}
			break;
		case 5://生产部八号裁剪
			for(Procedure procedure : procedureList){
				if(procedure.getHairPrice()!=null){
					sumPrice = procedure.getHairPrice();
				}
			}
			break;
		default:
			break;
		}
		return sumPrice;
	}
	
	/**
	 * 根据不同的部门，计算预计完成时间
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumExpectTime(Procedure procedure, Integer type,Integer number) {
		Double sumExpectTime = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumExpectTime = NumUtils.div(NumUtils.mul(procedure.getWorkingTime(),number ),ProTypeUtils.TIME,5);
			break;
		case 2://生产部一楼打包
			sumExpectTime = NumUtils.div(NumUtils.mul(procedure.getWorkingTime(),number ),ProTypeUtils.TIME,5);
			break;
		case 3://生产部二楼针工
			sumExpectTime = NumUtils.div(NumUtils.mul(procedure.getWorkingTime(),number ),ProTypeUtils.TIME,5);
			break;
		case 4://生产部二楼机工
			sumExpectTime = NumUtils.div(NumUtils.mul(procedure.getWorkingTime(),number ),ProTypeUtils.TIME,5);
			break;
		case 5://生产部二楼机工
			sumExpectTime = NumUtils.div(NumUtils.mul(procedure.getWorkingTime(),number ),ProTypeUtils.TIME,5);
			break;
		default:
			break;
		}
		return sumExpectTime;
	}
	
	
	/**
	 * 根据不同的部门，计算实际完成时间
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumTaskTime(Double expectTime, Integer type,Integer number) {
		Double sumExpectTime = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumExpectTime =  NumUtils.div(NumUtils.mul(expectTime,number),ProTypeUtils.TIME,5);
			break;
		case 2://生产部一楼打包
			sumExpectTime =  NumUtils.div(NumUtils.mul(expectTime,number),ProTypeUtils.TIME,5);
			break;
		case 3://生产部二楼针工
			sumExpectTime =  NumUtils.div(NumUtils.mul(expectTime,number),ProTypeUtils.TIME,5);
			break;
		case 4://生产部二楼机工
			sumExpectTime =  NumUtils.div(NumUtils.mul(expectTime,number),ProTypeUtils.TIME,5);
			break;
		case 5://生产部二楼机工
			sumExpectTime =  NumUtils.div(NumUtils.mul(expectTime,number),ProTypeUtils.TIME,5);
			break;
		default:
			break;
		}
		return sumExpectTime;
	}
	
	
	/**
	 * 根据不同的部门，计算实际任务数量
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double getTaskNumber(Double expectTime, Integer type,Double workingTime) {
		Double taskNumber = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			taskNumber = NumUtils.div(NumUtils.mul(expectTime,ProTypeUtils.TIME),workingTime,5);
			break;
		case 2://生产部一楼打包
			taskNumber = NumUtils.div(NumUtils.mul(expectTime,ProTypeUtils.TIME),workingTime,5);
			break;
		case 3://生产部二楼针工
			taskNumber = NumUtils.div(NumUtils.mul(expectTime,ProTypeUtils.TIME),workingTime,5);
			break;
		case 4://生产部二楼机工
			taskNumber = NumUtils.div(NumUtils.mul(expectTime,ProTypeUtils.TIME),workingTime,5);
			break;
		case 5://生产部二楼机工
			taskNumber = NumUtils.div(NumUtils.mul(expectTime,ProTypeUtils.TIME),workingTime,5);
			break;
		default:
			break;
		}
		return taskNumber;
	}
	
	
	/**
	 * 根据不同的部门，计算预计任务价值
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumTaskPrice(Double taskTime, Integer type, Integer flag,Double AC5) {
		Double sumTaskPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumTaskPrice = NumUtils.mul(taskTime,ProTypeUtils.getAC8(),(double)ProTypeUtils.TIME);
			break;
		case 2://生产部一楼打包
			sumTaskPrice = NumUtils.mul(taskTime,ProTypeUtils.getPackAC8(),(double)ProTypeUtils.TIME);
			break;
		case 3://生产部二楼针工
			sumTaskPrice = NumUtils.mul(taskTime,ProTypeUtils.getAC8TWO(),(double)ProTypeUtils.TIME);
			break;
		case 4://生产部二楼机工
			sumTaskPrice = NumUtils.mul(taskTime,ProTypeUtils.getAC8TWOREWORK(AC5),(double)ProTypeUtils.TIME);
			break;
		case 5://生产部八号裁剪
		    sumTaskPrice = NumUtils.mul(taskTime,ProTypeUtils.getAC8N(),(double)ProTypeUtils.TIME);
		break;
		default:
			break;
		}
		return sumTaskPrice;
	}
	
	
	/**
	 * 根据不同的部门，计算b工资净值
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumBPrice(Double BPrice, Integer type) {
		Double sumBPrice = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumBPrice = NumUtils.div(BPrice,proTypeUtils.service.findByExcelNameAndType("AC7" , 1).getNumber(),5);
			break;
		case 2://生产部一楼打包
			sumBPrice = NumUtils.div(BPrice,proTypeUtils.service.findByExcelNameAndType("AC7" , 2).getNumber(),5);
			break;
		case 3://生产部二楼针工
			sumBPrice = NumUtils.div(BPrice,proTypeUtils.service.findByExcelNameAndType("AC7" , 3).getNumber(),5);
			break;
		case 4://生产部二楼机工
			sumBPrice = NumUtils.div(BPrice,proTypeUtils.service.findByExcelNameAndType("AC7" , 4).getNumber(),5);
			break;
		case 5://生产部二楼机工
			sumBPrice = NumUtils.div(BPrice,proTypeUtils.service.findByExcelNameAndType("AC7" , 5).getNumber(),5);
			break;
		default:
			break;
		}
		return sumBPrice;
	}
	
	/**
	 * 根据不同的部门，计算出该批次的地区差价
	 * @param price
	 * @param type
	 * @return
	 */
	public static Double sumRegionalPrice(Bacth bacth, Integer type) {
		Double sumRegionalPrice = 0.0 ;
		bacth.setBacthHairPrice(NumUtils.setzro(bacth.getBacthHairPrice()));
		bacth.setBacthDepartmentPrice(bacth.getBacthDepartmentPrice()==null?0.0:bacth.getBacthDepartmentPrice());
		switch (type) {
		case 1:// 生产部一楼质检
			sumRegionalPrice = NumUtils.sub(bacth.getSumTaskPrice(),NumUtils.mul(NumUtils.div(bacth.getBacthHairPrice(),bacth.getBacthDepartmentPrice(),5),bacth.getSumTaskPrice()));
			break;
		case 2://生产部一楼打包
			sumRegionalPrice = NumUtils.sub(bacth.getSumTaskPrice(),NumUtils.mul(NumUtils.div(bacth.getBacthHairPrice(),bacth.getBacthDepartmentPrice(),5),bacth.getSumTaskPrice()));
			break;
		case 3://生产部二楼针工
			sumRegionalPrice = NumUtils.sub(bacth.getSumTaskPrice(),NumUtils.mul(NumUtils.div(bacth.getBacthHairPrice(),bacth.getBacthDepartmentPrice(),5),bacth.getSumTaskPrice()));
			break;
		case 4://生产部二楼机工
			sumRegionalPrice = NumUtils.sub(bacth.getSumTaskPrice(),NumUtils.mul(NumUtils.div(bacth.getBacthHairPrice(),bacth.getBacthDepartmentPrice(),5),bacth.getSumTaskPrice()));
			break;
		case 5://八号裁剪
			sumRegionalPrice = NumUtils.sub(bacth.getSumTaskPrice(),NumUtils.mul(NumUtils.div(bacth.getBacthHairPrice(),bacth.getBacthDepartmentPrice(),5),bacth.getSumTaskPrice()));
			break;
		default:
			break;
		}
		if(bacth.getBacthHairPrice()==0){
			sumRegionalPrice = 0.0;
		}
		return sumRegionalPrice;
	}
	
	/**
	 *  根据不同的部门，计算出该杂工加绩具体工资数值
	 * @param farragoTask
	 * @return
	 */
	public static Double sumPerformancePrice(FarragoTask farragoTask) {
		
		Double sumPerformancePrice = 0.0 ;
		switch (farragoTask.getType()) {
		case 1:// 生产部一楼质检
			sumPerformancePrice = NumUtils.mul(farragoTask.getPerformanceNumber(),farragoTask.getTime());
			break;
		case 2://生产部一楼打包
			sumPerformancePrice =NumUtils.mul(farragoTask.getPerformanceNumber(),farragoTask.getTime());
			break;
		case 3://生产部二楼针工
			sumPerformancePrice =NumUtils.mul(farragoTask.getPerformanceNumber(),farragoTask.getTime());
			break;
		case 4://生产部二楼机工工
			sumPerformancePrice =NumUtils.mul(farragoTask.getPerformanceNumber(),farragoTask.getTime());
			break;
		case 5://生产部二楼机工工
			sumPerformancePrice =NumUtils.mul(farragoTask.getPerformanceNumber(),farragoTask.getTime());
			break;
		default:
			break;
		}
		return sumPerformancePrice;
	}
	
	
	/**
	 *  根据不同的部门，计算出该任务加绩具体工资数值
	 * @param farragoTask
	 * @return
	 */
	public static Double sumtaskPerformancePrice(Task task) {
		Double sumPerformancePrice = 0.0 ;
		switch (task.getType()) {
		case 1:// 生产部一楼质检
			sumPerformancePrice =  NumUtils.mul(task.getPerformanceNumber(),task.getTaskTime());
			break;
		case 2://生产部一楼打包
			sumPerformancePrice = NumUtils.mul(task.getPerformanceNumber(),task.getTaskTime());
			break;
		case 3://生产部二楼针工
			sumPerformancePrice = NumUtils.mul(task.getPerformanceNumber(),task.getTaskTime());
			break;
		case 4://生产部二楼机工
			sumPerformancePrice = NumUtils.mul(task.getPerformanceNumber(),task.getTaskTime());
			break;
		case 5://生产部二楼机工
			sumPerformancePrice = NumUtils.mul(task.getPerformanceNumber(),task.getTaskTime());
			break;
		default:
			break;
		}
		return sumPerformancePrice;
	}
	
	
	/********************** 日常消费   ***********************************/
	
	
	/**
	 *  根据不同的部门，获取不同的日常数值
	 * @param farragoTask
	 * @return
	 */
	public static UsualConsume usualConsume(UsualConsume usualConsume) {
		
		switch (usualConsume.getType()) {
		case 1:// 生产部一楼质检
			usualConsume.setPeopleLogistics(proTypeUtils.service.findByExcelNameAndType("E7" , 1).getNumber());
			usualConsume.setPeopleNumber(proTypeUtils.service.findByExcelNameAndType("E8" , 1).getNumber());
			usualConsume.setMonthChummage(proTypeUtils.service.findByExcelNameAndType("E9" , 1).getNumber());
			usualConsume.setMonthHydropower(proTypeUtils.service.findByExcelNameAndType("E10" , 1).getNumber());
			break;
		case 2://生产部一楼打包
			usualConsume.setPeopleLogistics(proTypeUtils.service.findByExcelNameAndType("E7" , 2).getNumber());
			usualConsume.setPeopleNumber(proTypeUtils.service.findByExcelNameAndType("E8" , 2).getNumber());
			usualConsume.setMonthChummage(proTypeUtils.service.findByExcelNameAndType("E9" , 2).getNumber());
			usualConsume.setMonthHydropower(proTypeUtils.service.findByExcelNameAndType("E10" , 2).getNumber());
			break;
		case 3://生产部二楼针工
			usualConsume.setPeopleLogistics(proTypeUtils.service.findByExcelNameAndType("E7" , 3).getNumber());
			usualConsume.setPeopleNumber(proTypeUtils.service.findByExcelNameAndType("E8" , 3).getNumber());
			usualConsume.setMonthChummage(proTypeUtils.service.findByExcelNameAndType("E9" , 3).getNumber());
			usualConsume.setMonthHydropower(proTypeUtils.service.findByExcelNameAndType("E10" , 3).getNumber());
			break;
		case 4://生产部二楼机工
			usualConsume.setPeopleLogistics(proTypeUtils.service.findByExcelNameAndType("E7" , 4).getNumber());
			usualConsume.setPeopleNumber(proTypeUtils.service.findByExcelNameAndType("E8" , 4).getNumber());
			usualConsume.setMonthChummage(proTypeUtils.service.findByExcelNameAndType("E9" , 4).getNumber());
			usualConsume.setMonthHydropower(proTypeUtils.service.findByExcelNameAndType("E10" , 4).getNumber());
			break;
		case 5://生产部八号仓库
			usualConsume.setPeopleLogistics(proTypeUtils.service.findByExcelNameAndType("E7" , 5).getNumber());
			usualConsume.setPeopleNumber(proTypeUtils.service.findByExcelNameAndType("E8" , 5).getNumber());
			usualConsume.setMonthChummage(proTypeUtils.service.findByExcelNameAndType("E9" , 5).getNumber());
			usualConsume.setMonthHydropower(proTypeUtils.service.findByExcelNameAndType("E10" , 5).getNumber());
			break;
		default:
			break;
		}
		return usualConsume;
	}
	
	
	/**
	 *  根据不同的部门，修改不同的日常数值
	 * @param farragoTask
	 * @return
	 */
	public static void updateUsualConsume(UsualConsume usualConsume) {
		ProductionConstant E7 = null;
		ProductionConstant E8 = null;
		ProductionConstant E9 = null;
		ProductionConstant E10 = null;
		switch (usualConsume.getType()) {
		case 1:// 生产部一楼质检
			 E7 = proTypeUtils.service.findByExcelNameAndType("E7" , 1);
			 E8 = proTypeUtils.service.findByExcelNameAndType("E8" , 1);
			 E9 = proTypeUtils.service.findByExcelNameAndType("E9" , 1);
			 E10 = proTypeUtils.service.findByExcelNameAndType("E10" , 1);
			break;
		case 2://生产部一楼打包
			 E7 = proTypeUtils.service.findByExcelNameAndType("E7" , 2);
			 E8 = proTypeUtils.service.findByExcelNameAndType("E8" , 2);
			 E9 = proTypeUtils.service.findByExcelNameAndType("E9" , 2);
			 E10 = proTypeUtils.service.findByExcelNameAndType("E10" , 2);
			break;
		case 3://生产部二楼针工
			 E7 = proTypeUtils.service.findByExcelNameAndType("E7" , 3);
			 E8 = proTypeUtils.service.findByExcelNameAndType("E8" , 3);
			 E9 = proTypeUtils.service.findByExcelNameAndType("E9" , 3);
			 E10 = proTypeUtils.service.findByExcelNameAndType("E10",3);
			break;
		case 4://生产部二楼机工
			 E7 = proTypeUtils.service.findByExcelNameAndType("E7" , 4);
			 E8 = proTypeUtils.service.findByExcelNameAndType("E8" , 4);
			 E9 = proTypeUtils.service.findByExcelNameAndType("E9" , 4);
			 E10 = proTypeUtils.service.findByExcelNameAndType("E10",4);
			break;
		case 5://生产部八号仓库
			 E7 = proTypeUtils.service.findByExcelNameAndType("E7" , 5);
			 E8 = proTypeUtils.service.findByExcelNameAndType("E8" , 5);
			 E9 = proTypeUtils.service.findByExcelNameAndType("E9" , 5);
			 E10 = proTypeUtils.service.findByExcelNameAndType("E10",5);
			break;
		default:
			break;
		}
		if(!usualConsume.getPeopleLogistics().equals(E7.getNumber())){
			E7.setNumber(usualConsume.getPeopleLogistics());
			proTypeUtils.service.save(E7);
		}
		if(!usualConsume.getPeopleNumber().equals(E8.getNumber())){
			E8.setNumber(usualConsume.getPeopleNumber());
			proTypeUtils.service.save(E8);
		}
		if(!usualConsume.getMonthChummage().equals(E9.getNumber())){
			E9.setNumber(usualConsume.getMonthChummage());
			proTypeUtils.service.save(E9);
		}
		if(!usualConsume.getMonthHydropower().equals(E10.getNumber())){
			E10.setNumber(usualConsume.getMonthHydropower());
			proTypeUtils.service.save(E10);
		}
	}
	
	
	/**
	 *  根据不同的部门，得到不同的填写日期
	 * @param farragoTask
	 * @return
	 */
	public static Date countAllotTime(Date allotTime) {
		if(allotTime == null ){
			Calendar  cal = Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			allotTime = cal.getTime();
		}
		return allotTime;
	}

	public static Double sumFarragoTaskTime(Double procedureTime, Integer type, Integer number) {
		Double sumExpectTime = 0.0 ;
		switch (type) {
		case 1:// 生产部一楼质检
			sumExpectTime = procedureTime;
			break;
		case 2://生产部一楼打包
			sumExpectTime = procedureTime;
			break;
		case 3://生产部二楼针工
			sumExpectTime =NumUtils.div(NumUtils.mul(procedureTime,number),ProTypeUtils.TIME,5);
			break;
		case 4://生产部二楼机工
			sumExpectTime =NumUtils.div(NumUtils.mul(procedureTime,number),ProTypeUtils.TIME,5);
			break;
		case 5://生产部八号仓库
			sumExpectTime =NumUtils.div(NumUtils.mul(procedureTime,number),ProTypeUtils.TIME,5);
			break;
		default:
			break;
		}
		return sumExpectTime;
	}
	

}
