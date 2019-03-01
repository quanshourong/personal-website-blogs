package com.rong.base.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.collect.Lists;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static long CONST_WEEK = 1000 * 3600 * 24 * 7 * 1L;// 一周毫秒数

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	public static Date getDateStart(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 判断字符串是否是日期
	 * 
	 * @param timeString
	 * @return
	 */
	public static boolean isDate(String timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			format.parse(timeString);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化时间
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String dateFormat(Date timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(timestamp);
	}

	/**
	 * 获取系统时间Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取系统时间Date
	 * 
	 * @return
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 生成时间随机数
	 * 
	 * @return
	 */
	public static String getDateRandom() {
		String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return s;
	}

	// 获取时间的日期部分，如：2019-01-18
	public static String getDailyKnots(Date date) {

		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
	}

	public static List<String> getDayStrings(String format, int dayType, Date startTime, Date endTime) {
		List<String> dayList = Lists.newArrayList();
		DateFormat dateFormat = new SimpleDateFormat(format);

		// 开始时间
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(startTime);

		// 结束时间
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(endTime);
		// tempEnd.add(Calendar.DATE, +0);// 日期加1(包含结束)

		// 循环获取时间
		while (tempStart.before(tempEnd)) {
			dayList.add(dateFormat.format(tempStart.getTime()));
			tempStart.add(dayType, 1);
		}
		return dayList;
	}

	/**
	 * 根据开始和结束时间获取对应的时间段内的每周第一天的日期,<br>
	 * 格式:yyyy-MM-dd
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getWeeksByDate(Date startDate, Date endDate) {
		List<String> weeksList = Lists.newArrayList();

		// 1.转换成joda-time的对象
		// DateTime firstDay = new
		// DateTime(startDate).dayOfWeek().withMinimumValue();
		// DateTime lastDay = new
		// DateTime(endDate).dayOfWeek().withMaximumValue();
		// // 计算两日期间的区间天数
		// Period p = new Period(firstDay, lastDay, PeriodType.days());
		// int days = p.getDays();
		// if (days > 0) {
		// int weekLength = 7;
		// for (int i = 0; i < days; i = i + weekLength) {
		// String monDay = firstDay.plusDays(i).toString("yyyy-MM-dd");
		// weeksList.add(monDay);
		// }
		// }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(startDate);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(endDate);

		// 查找开始日期的那个星期的第一天
		int dayOfWeek = tempStart.get(Calendar.DAY_OF_WEEK);
		tempStart.add(Calendar.DATE, -(dayOfWeek - 1));// 周日是第一天 所以-1
		// 查找结束日期的那个星期的第一天
		dayOfWeek = tempEnd.get(Calendar.DAY_OF_WEEK);
		tempEnd.add(Calendar.DATE, 7 - (dayOfWeek - 1));

		// 计算总共多少周
		int total = (int) ((tempEnd.getTimeInMillis() - tempStart.getTimeInMillis()) / CONST_WEEK);
		for (int i = 0; i < total; i++) {
			tempStart.add(Calendar.DATE, 1);
			String time = sdf.format(tempStart.getTime());// 第一天
			tempStart.add(Calendar.DATE, 6);
			// time += "~"+sdf.format(tempStart.getTime());//最后一天
			weeksList.add(time);
		}
		return weeksList;
	}

}
