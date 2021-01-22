package com.bluewhite.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumUtils {
	
	/**
     * 清理因double精度带来的尾巴问题
     * @param value
     * @return
     */
    public static Double round(Double value,Integer number) {
    	if(number == null){
    		number = 10;
    	}
        return new BigDecimal(value).setScale(number, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
	/**
     * 清理因double精度带来的尾巴问题
     * @param value
     * @return
     */
    public static Integer roundTwo(Double value) {
        return new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

	public static Integer roundInt(Double value) {
		return new BigDecimal(value).setScale(10, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	
	/**
	 * 处理除法结果问题sum.isNaN()?0.0:sum
	 */
	public static Double division(Double value) {
		return value.isNaN() || value.isInfinite() ? 0.0 : value;
	}
	
	
	/**
	 * 当传入字段为空，自动赋值为0；
	 */
	public static Double setzro(Double value) {
		return value==null ?0.0:value;
	}
	
	/**
	 * 当传入字段为空，自动赋值为0；
	 */
	public static Integer setzro(Integer value) {
		return value==null ?0:value;
	}
	
	/**
	 * 当传入实体字段属性为空，自动赋值为0；避免空指针异常
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object setzro(Object model )  {
		Field[] fields = model.getClass().getDeclaredFields();
		 for(Field field : fields){
			 field.setAccessible(true);
			 String type = field.getType().toString();// 得到此属性的类型
			 if(type.endsWith("Double")){
				 Object val;
				try {
					val = field.get(model);
					field.set(model, (Double)val == null ? (Double)0.0 : round((Double)val,5)); 
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			 } 
			 if(type.endsWith("Integer")){
				 Object val;
				try {
					val = field.get(model);
					field.set(model,(Integer)val == null ? (Integer)0 : (Integer)val); 
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			 } 
		 }
		 return model;
	}
	
	/**
	 * 将传入的多个参数转换成Double集合
	 */
	 private static List<Double> reTypeList(Double...properties){
		 List<Double> ignoreList = null;
			if(properties != null){
				ignoreList = Arrays.asList(properties);
		    }
			List<Double> ignoreListRe = ignoreList;
		return ignoreList;
		 
	 }
	
	 /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    } 
    
    /** 
     * 多个 double 相加 
     * @param properties
     * @return
     */ 
    public static double sum(Double...properties){ 
    	List<Double> ignoreList = reTypeList(properties);
    	List<BigDecimal> bigDecimalList = new ArrayList<>();
    	for(Double d : ignoreList){
    		 BigDecimal bd1 = new BigDecimal(Double.toString(d)); 
    		 bigDecimalList.add(bd1);
    	}
		return bigDecimalList.stream().reduce(BigDecimal::add).get().doubleValue();
    }
    
    /** 
     * List double 相加 
     * @param numList
     * @return 
     */ 
    public static double sum(List<Double> numList){
    	List<BigDecimal> bigDecimalList = new ArrayList<>();
    	for(Double d : numList){
    		 BigDecimal bd1 = new BigDecimal(Double.toString(d)); 
    		 bigDecimalList.add(bd1);
    	}
		return bigDecimalList.stream().reduce(BigDecimal::add).get().doubleValue();
    }
    
  

    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 
    
	/**
	 * 多个 double 相减
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(Double... properties) {
		List<Double> ignoreList = reTypeList(properties);
    	List<BigDecimal> bigDecimalList = new ArrayList<>();
    	for(Double d : ignoreList){
    		 BigDecimal bd1 = new BigDecimal(Double.toString(d)); 
    		 bigDecimalList.add(bd1);
    	}
		return bigDecimalList.stream().reduce((a,b)->a.subtract(b)).get().doubleValue();
	}

    
    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
    } 
    
    /** 
     * 多个 double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(Double...properties){
    	List<Double> ignoreList = reTypeList(properties);
    	List<BigDecimal> bigDecimalList = new ArrayList<>();
    	for(Double d : ignoreList){
    		 BigDecimal bd1 = new BigDecimal(Double.toString(d)); 
    		 bigDecimalList.add(bd1);
    	}
		return bigDecimalList.stream().reduce((a,b)->a.multiply(b)).get().doubleValue();
    } 
    
    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2,int scale){ 
        //  当然在此之前，你要判断分母是否为0，    
        //  为0你可以根据实际需求做相应的处理 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    } 
	
}
