package com.bluewhite.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DatesUtil {
	
	
	public static int getDay(Date date,Integer type){
//		2、获取date对应的Calendar对象
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
//		3、可以从ca中获取各种该日期的属性值：
		if(type == 1){
			int month = ca.get(Calendar.MONTH);//第几个月
			return month;
		}else{
			int year = ca.get(Calendar.YEAR);//年份数值
			return year;  
		}
    }  
	
	
	/** 
	* 获得该月第一天 
	* @param year 
	* @param month 
	* @return 
	 * @throws ParseException 
	*/  
	public static Date getFirstDayOfMonth(Date dates) {  
			int month = DatesUtil.getDay(dates,1);
			int year = DatesUtil.getDay(dates,2);
	        Calendar cal = Calendar.getInstance();  
	        //设置年份  
	        cal.set(Calendar.YEAR,year);  
	        //设置月份  
	        cal.set(Calendar.MONTH, month-1);  
	        //获取某月最小天数  
	        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最小天数  
	        cal.set(Calendar.DAY_OF_MONTH, firstDay);  
	        Date firstDayOfMonth = cal.getTime();  
	        return firstDayOfMonth;  
	    }  
	  
	/** 
	* 获得该月最后一天 
	* @param year 
	* @param month 
	* @return 
	 * @throws ParseException 
	*/  
	public static Date getLastDayOfMonth(Date dates) { 
			int month = DatesUtil.getDay(dates,1);
			int year = DatesUtil.getDay(dates,2);
	        Calendar cal = Calendar.getInstance();  
	        //设置年份  
	        cal.set(Calendar.YEAR,year);  
	        //设置月份  
	        cal.set(Calendar.MONTH, month-1);  
	        //获取某月最大天数  
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最大天数  
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
	        Date lastDayOfMonth = cal.getTime();  
	        return lastDayOfMonth;  
	    }  

}
