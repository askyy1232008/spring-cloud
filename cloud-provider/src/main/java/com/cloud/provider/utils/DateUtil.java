package com.cloud.provider.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 关于java.util.Date、java.sql.Timestamp和String之间的互相转换的方法
 * 
 * @Description: TODO
 * @CreateTime: 2017年10月25日 下午3:20:44
 * @version V1.0
 */
public class DateUtil {

	/**
	 * 将String字符串转换为java.util.Date格式日期
	 * 
	 * @param strDate
	 *            表示日期的字符串
	 * @param dateFormat
	 *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.util.Date类型日期对象（如果转换失败则返回null）
	 */
	public static java.util.Date strToUtilDate(String strDate, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		java.util.Date date = null;
		try {
			date = sf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
	 * 
	 * @param strDate
	 *            表示日期的字符串
	 * @param dateFormat
	 *            传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
	 */
	public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		java.util.Date date = null;
		try {
			date = sf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
		return dateSQL;
	}

	/**
	 * 将java.util.Date对象转化为String字符串
	 * 
	 * @param date
	 *            要格式的java.util.Date对象
	 * @param strFormat
	 *            输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
	 * @return 表示日期的字符串
	 */
	public static String dateToStr(java.util.Date date, String strFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(strFormat);
		String str = sf.format(date);
		return str;
	}

	/**
	 * 将java.sql.Timestamp对象转化为String字符串
	 * 
	 * @param time
	 *            要格式的java.sql.Timestamp对象
	 * @param strFormat
	 *            输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
	 * @return 表示日期的字符串
	 */
	public static String dateToStr(java.sql.Timestamp time, String strFormat) {
		DateFormat df = new SimpleDateFormat(strFormat);
		String str = df.format(time);
		return str;
	}

	/**
	 * 将java.sql.Timestamp对象转化为java.util.Date对象
	 * 
	 * @param time
	 *            要转化的java.sql.Timestamp对象
	 * @return 转化后的java.util.Date对象
	 */
	public static java.util.Date timeToDate(java.sql.Timestamp time) {
		return time;
	}

	/**
	 * 将java.util.Date对象转化为java.sql.Timestamp对象
	 * 
	 * @param date
	 *            要转化的java.util.Date对象
	 * @return 转化后的java.sql.Timestamp对象
	 */
	public static java.sql.Timestamp dateToTime(java.util.Date date) {
		String strDate = dateToStr(date, "yyyy-MM-dd HH:mm:ss SSS");
		return strToSqlDate(strDate, "yyyy-MM-dd HH:mm:ss SSS");
	}

	/**
	 * 返回表示系统当前时间的java.util.Date对象
	 * 
	 * @return 返回表示系统当前时间的java.util.Date对象
	 */
	public static java.util.Date nowDate() {
		return new java.util.Date();
	}

	/**
	 * 返回表示系统当前时间的java.sql.Timestamp对象
	 * 
	 * @return 返回表示系统当前时间的java.sql.Timestamp对象
	 */
	public static java.sql.Timestamp nowTime() {
		return dateToTime(new java.util.Date());
	}

	/**
	 * 比较2个Date是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean sameDate(Date d1, Date d2) {
		if (null == d1 || null == d2)
			return false;
		// return getOnlyDate(d1).equals(getOnlyDate(d2));
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 *             calendar 对日期进行时间操作 getTimeInMillis() 获取日期的毫秒显示形式
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**   
	 * 获取两个日期相差的月数  按天计   
	 * @param d2  较大的日期     
	 * @param d1  较小的日期    
	 * @return 如果d1>d2返回 月数差 否则返回0    
	 */
	public static int getMonthDiff(String d1, String d2) throws ParseException {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		// 将String日期转换成date

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = sdf.parse(d1);
		java.util.Date date2 = sdf.parse(d2);
		c1.setTime(date1);
		c2.setTime(date2);

		// 判断两个日期的大小

		if (c2.getTimeInMillis() < c1.getTimeInMillis())
			return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-9-30   d2 = 2015-12-16
		int yearInterval = year2 - year1;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month2 < month1 || month1 == month2 && day2 < day1)
			yearInterval--;
		// 获取月数差值
		int monthInterval = (month2 + 12) - month1;
		if (day2 > day1)
			monthInterval++;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

	/**
	 * 字符串日期格式的计算
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串日期格式和date日期格式的计算
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/***
	 * 增加几个月
	 * 
	 * @param date
	 * @param dateType
	 *            yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @return
	 */
	public static String addMonth(String date, String dateType, int month) {
		String nowDate = null;
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		try {
			Date parse = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.MONTH, month);
			nowDate = format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowDate;
	}

	/**
	 * 当前日期增加3天的datetime
	 * 
	 * @return
	 */
	public static String nextThreeDay() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 3);
		return sf.format(c.getTime());
	}

	/**
	 * 当前datetime
	 * 
	 * @return
	 */
	public static String nowDateTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		return sf.format(c.getTime());
	}
	
	/**
     * 获取相差的月数。按月计
     * @param date1 <String>
     * @param date2 <String>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2)
            throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result == 0 ? 0 : Math.abs(result);
    }

	public static void main(String[] args) {
		// String time = "".toString();
		// System.out.println(strToSqlDate(time, "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateUtil.nowDateTime());
		try {
			System.out.println(DateUtil.getMonthSpace("2019-01-06", "2019-01-08"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}