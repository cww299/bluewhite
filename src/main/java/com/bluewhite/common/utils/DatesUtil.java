package com.bluewhite.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.tools.extcheck.Main;

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
	        cal.set(Calendar.MONTH, month);  
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
	        cal.set(Calendar.MONTH, month);  
	        //获取某月最大天数  
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最大天数  
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
	        Date lastDayOfMonth = cal.getTime();  
	        return lastDayOfMonth;  
	    } 
	
	/**
	 * 获取某一天的开始时间
	 * @param dates
	 * @return
	 */
	public static Date getfristDayOftime(Date dates) { 
		Calendar calendarFrom = Calendar.getInstance();     
		calendarFrom.setTime(dates); //获得实体对象里面一个Date类型的属性，set进Calender对象中。  
		calendarFrom.set(Calendar.HOUR_OF_DAY, 0);//设置时为0点  
		calendarFrom.set(Calendar.MINUTE, 0);//设置分钟为0分  
		calendarFrom.set(Calendar.SECOND, 0);//设置秒为0秒  
		calendarFrom.set(Calendar.MILLISECOND, 000);//设置毫秒为000  
        return calendarFrom.getTime();  
    } 
	
	/**
	 * 获取某一天的结束时间
	 * @param dates
	 * @return
	 */
	public static Date getLastDayOftime(Date dates) { 
		Calendar calendarEnd = Calendar.getInstance();     
		calendarEnd.setTime(dates);   
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);  
		calendarEnd.set(Calendar.MINUTE, 59);  
		calendarEnd.set(Calendar.SECOND, 59);  
		calendarEnd.set(Calendar.MILLISECOND, 999);  
        return calendarEnd.getTime();  
    }
	
	/**
	 * 比较两个日期是否同一天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean sameDate(Date d1, Date d2){  
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");  
	    //fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置  
	    return fmt.format(d1).equals(fmt.format(d2));  
	}
	
	
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     */
    public static long getDaySub(Date beginDate,Date endDate){
        long day = (endDate.getTime()-beginDate.getTime()+1000)/(24*60*60*1000);    
        return day;
    }
    
	/**
     * <li>功能描述：时间相减得到时间（分钟）
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     */
    public static Double getTime(Date beginDate,Date endDate){
    	Long time =  (endDate.getTime()-beginDate.getTime())/(60*1000);
    	Double day = time.doubleValue();    
        return day;
    }
    
    /**
     * 获取某个日期的下一天
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date nextDay(Date beginDate){
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(beginDate);  
	    calendar.add(Calendar.DATE, 1);  
	    Date date = calendar.getTime();
		return date;  
    }

}
