package com.bluewhite.common.utils;

import java.math.BigDecimal;

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
	
}
