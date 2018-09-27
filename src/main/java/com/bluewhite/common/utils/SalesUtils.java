package com.bluewhite.common.utils;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.data.domain.Sort;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;



public class SalesUtils {

    /**
     * 获取系统打印时间
     * @return
     */
    public static String getCurrentTimeStr2Print() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取没有分页的分页参数
     * @return
     */
    public static PageParameter getQueryNoPageParameter() {
        return new PageParameter(0, Integer.MAX_VALUE,new Sort(Sort.Direction.DESC, "id"));
    }

    /**
     * 清理因double精度带来的尾巴问题
     * @param value
     * @return
     */
    public static Double round(Double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取4位数随机码
     */
    public static String findRandomCode(){
        int intCount = (new Random()).nextInt(9999);// 最大值位9999
        if (intCount < 1000) {
            intCount += 1000; // 最小值位1001
        }
        return String.valueOf(intCount);
    }

    /**
     * 获取加密的手机号
     * @param phone
     * @return
     */
    public static String formatMobilePhone(String phone) {
        if (phone == null) {
            throw new ServiceException("手机号格式错误.");
        }
        return phone.replaceFirst("(?<=\\d{4})\\d{4}","****");
    }

    /**
     * 获取左补0的数据
     * @param value
     * @param stringLen
     * @return
     */
    public static String get0LeftString(Integer value, int stringLen) {
        return String.format("%0" + stringLen + "d",value);
    }

    /**
     * 格式化大数字
     * @param value
     * @return
     */
    public static String formatBigDouble(Double value) {
        return new BigDecimal(value.toString()).toPlainString();
    }
}
