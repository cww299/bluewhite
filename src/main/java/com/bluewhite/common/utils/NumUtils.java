package com.bluewhite.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.springframework.ui.Model;

import com.bluewhite.product.primecost.cutparts.entity.CutParts;

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
		return value.isNaN() || value.isInfinite() ?0.0:value;
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
				 Object target =  field.getName();
				 Object val;
				try {
					val = field.get(model);
					field.set(model,(Double)val == null ? (Double)0.0 : (Double)val); 
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			 } 
			 if(type.endsWith("Integer")){
				 Object target =  field.getName();
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
	
}
