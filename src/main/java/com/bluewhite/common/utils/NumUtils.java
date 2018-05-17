package com.bluewhite.common.utils;

import java.math.BigDecimal;

public class NumUtils {
	
	/**
     * 清理因double精度带来的尾巴问题
     * @param value
     * @return
     */
    public static Double round(Double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
