package com.bluewhite.common.utils;

import java.math.BigDecimal;

public class NumUtils {
	
	/**
     * 清理因double精度带来的尾巴问题
     * @param value
     * @return
     */
    public static Double round(Double value) {
        return new BigDecimal(value).setScale(16, BigDecimal.ROUND_HALF_UP).doubleValue();
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
}
