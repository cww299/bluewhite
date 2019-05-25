package com.bluewhite.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public class DatesUtil {

	public static int getDay(Date date, Integer type) {
		// 2、获取date对应的Calendar对象
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		// 3、可以从ca中获取各种该日期的属性值：
		if (type == 1) {
			int month = ca.get(Calendar.MONTH);// 第几个月
			return month;
		} else {
			int year = ca.get(Calendar.YEAR);// 年份数值
			return year;
		}
	}

	/**
	 * 获得该月第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(Date dates) {
		int month = DatesUtil.getDay(dates, 1);
		int year = DatesUtil.getDay(dates, 2);
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR_OF_DAY, 0);// 设置时为0点
		cal.set(Calendar.MINUTE, 0);// 设置分钟为0分
		cal.set(Calendar.SECOND, 0);// 设置秒为0秒
		cal.set(Calendar.MILLISECOND, 000);// 设置毫秒为000
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		Date firstDayOfMonth = cal.getTime();
		return firstDayOfMonth;
	}

	/**
	 * 获得该月最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfMonth(Date dates) {
		int month = DatesUtil.getDay(dates, 1);
		int year = DatesUtil.getDay(dates, 2);
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		Date lastDayOfMonth = cal.getTime();
		return lastDayOfMonth;
	}

	/**
	 * 获取前一个月第一天
	 * 
	 * @param dates
	 * @return
	 */
	public static Date getFristDayOfLastMonth(Date dates) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		return calendar1.getTime();

	}

	/**
	 * 获取前一个月最后一天
	 * 
	 * @param dates
	 * @return
	 */
	public static Date getLastDayOLastMonth(Date dates) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}

	/**
	 * 获取某一天的开始时间
	 * 
	 * @param dates
	 * @return
	 */
	public static Date getfristDayOftime(Date dates) {
		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.setTime(dates); // 获得实体对象里面一个Date类型的属性，set进Calender对象中。
		calendarFrom.set(Calendar.HOUR_OF_DAY, 0);// 设置时为0点
		calendarFrom.set(Calendar.MINUTE, 0);// 设置分钟为0分
		calendarFrom.set(Calendar.SECOND, 0);// 设置秒为0秒
		calendarFrom.set(Calendar.MILLISECOND, 000);// 设置毫秒为000
		return calendarFrom.getTime();
	}

	/**
	 * 获取某一天的结束时间
	 * 
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
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean sameDate(Date d1, Date d2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		// fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
		return fmt.format(d1).equals(fmt.format(d2));
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static long getDaySub(Date beginDate, Date endDate) {
		long day = (endDate.getTime() - beginDate.getTime() + 1000) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * <li>功能描述：时间相减得到时间（分钟）
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Double getTime(Date beginDate, Date endDate) {
		Long time = (endDate.getTime() - beginDate.getTime()) / (60 * 1000);
		Double day = time.doubleValue();
		return day;
	}
	
	/**
	 * <li>功能描述：时间相减得到时间（秒）
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Double getTimeSec(Date beginDate, Date endDate) {
		Long time = (endDate.getTime() - beginDate.getTime()) / 1000;
		Double day = time.doubleValue();
		return day;
	}
	
	/**
	 * <li>功能描述：日期加上分钟数返回一个新日期
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Date getDaySum(Date beginDate, Double minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.MINUTE, minute.intValue());
		return calendar.getTime();
	}
	
	/**
	 * <li>功能描述：日期加上天数返回一个新日期
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Date getDaySum(Date beginDate, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);// 设置时为0点
		calendar.set(Calendar.MINUTE, 0);// 设置分钟为0分
		calendar.set(Calendar.SECOND, 0);// 设置秒为0秒
		calendar.set(Calendar.MILLISECOND, 000);// 设置毫秒为000
		return calendar.getTime();
	}

	/**
	 * <li>功能描述：考勤特殊处理时间方法
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Double getTimeHour(Date beginDate, Date endDate) {
		Double day = getTime(beginDate, endDate);
		// 获取整除60分钟之后的剩余分钟数
		double alltime = Math.floor(day / 60);
		double timele = day % 60;
		double time = 0.0;
		if (day > 0) {
			if (timele < 25) {
				time = alltime;
			}
			if (25 <= timele && timele <= 55) {
				time = NumUtils.sum(alltime, 0.5);
			}
			if (timele > 55) {
				time = NumUtils.sum(alltime, 1);
			}
		}
		return time;
	}
	
	/**
	 * <li>功能描述：考勤特殊处理时间方法
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 */
	public static Double getTimeHour(double day) {
		// 获取整除60分钟之后的剩余分钟数
		double alltime = Math.floor(day / 60);
		double timele = day % 60;
		double time = 0.0;
		if (day > 0) {
			if (timele < 20) {
				time = alltime;
			}
			if (20 <= timele && timele <= 50) {
				time = NumUtils.sum(alltime, 0.5);
			}
			if (timele > 50) {
				time = NumUtils.sum(alltime, 1);
			}
		}
		return time;
	}
	
	
	/**
	 * 获取某个日期的下一天
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date nextDay(Date beginDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.DATE, 1);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 判断两个日期是否属于同一个月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean equalsDate(Date date1, Date date2) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String JudgeWeek(Date date) {
		String week = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			week = "星期日";
		} else {
			week = "星期" + (i - 1);
		}
		return week;
	}

	/**
	 * 根据生日计算年龄工具
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间
			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				// 当前日期大于生日，多加一岁
				// if (now.get(Calendar.DAY_OF_YEAR) >
				// birth.get(Calendar.DAY_OF_YEAR)) {
				// age += 1;
				// }
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}

	/**
	 * 日期转星期
	 * 
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(Date datetime) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		cal.setTime(datetime);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 将某个日期转换成参数里需要的时间
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date dayTime(Date date, String time) {
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		cal.setTime(date);
		String[] timeArr = time.trim().split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArr[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
		cal.set(Calendar.SECOND, Integer.valueOf(timeArr[2]));
		return cal.getTime();
	}

	/**
	 * 将某个日期转换成需要的时间
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date getTime(Date date) {
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		cal.setTime(date);
		return cal.getTime();
	}

	public static Date getdate(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);
		return calendar.getTime();

	}

	/**
	 * 判断是否是weekend
	 *
	 * @param sdate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断是冬令时还是夏令时（5.1-9.30）       
	 * @throws ParseException 
	 */
	public static boolean belongCalendar(Date nowTime) throws ParseException {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		
		Calendar begin = Calendar.getInstance();
		// 设置年份
		begin.set(Calendar.YEAR, date.get(Calendar.YEAR));
		// 设置月份
		begin.set(Calendar.MONTH, 3);
		//设置天
		begin.set(Calendar.DAY_OF_MONTH, 30);
		begin.set(Calendar.HOUR_OF_DAY, 23);// 设置时为0点
		begin.set(Calendar.MINUTE, 59);// 设置分钟为0分
		begin.set(Calendar.SECOND, 59);// 设置秒为0秒
		Calendar end = Calendar.getInstance();
		// 设置年份
		end.set(Calendar.YEAR, date.get(Calendar.YEAR));
		// 设置月份
		end.set(Calendar.MONTH, 8);
		//设置天
		end.set(Calendar.DAY_OF_MONTH, 30);
		end.set(Calendar.HOUR_OF_DAY, 23);// 设置时为0点
		end.set(Calendar.MINUTE, 59);// 设置分钟为0分
		end.set(Calendar.SECOND, 59);// 设置秒为0秒
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(begin.getTime()) == 0 || nowTime.compareTo(end.getTime()) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 获取某日期区间的所有日期  日期倒序
	 *
	 * @param startDate  开始日期
	 * @param endDate    结束日期
	 * @param dateFormat 日期格式
	 * @return 区间内所有日期
	 */
	public static List<Date> getPerDaysByStartAndEndDate(String startDate, String endDate, String dateFormat) {
	    DateFormat format = new SimpleDateFormat(dateFormat);
	    try {
	        Date sDate = format.parse(startDate);
	        Date eDate = format.parse(endDate);
	        long start = sDate.getTime();
	        long end = eDate.getTime();
	        if (start > end) {
	            return null;
	        }
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(eDate);
	        List<Date> res = new ArrayList<Date>();
	        while (end >= start) {
	            res.add(calendar.getTime());
	            calendar.add(Calendar.DAY_OF_MONTH, -1);
	            end = calendar.getTimeInMillis();
	        }
	        return res.stream()
					.sorted(Comparator.comparing(Date::getTime)).collect(Collectors.toList());
	    } catch (ParseException e) {
	    	
	    }
	    return null;
	}

}
