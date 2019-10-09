package com.cloud.provider.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间工具（可以用joda.jar,比Date强大得多）
 *
 * @author LinFeng
 */
public class DatesUtil {

    private static final Logger logger = LoggerFactory.getLogger(DatesUtil.class);
    private static final String COLON = ":";
    public static final String WEEK_NAMES[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
    public static final String WEEK_EN_NAMES[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
    public static final String CHINE_DATE_FORMAT_TO_MINUTE="yyyy-MM-dd HH:mm";
    public static final String CHINESE_DATE_FORMAT_LONG_TO_MINUTE="yyyyMMdd HH:mm";
    public static final String CHINESE_DATE_FORMAT_LONG = "yyyyMMdd";
    public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String CHINESE_DATE_FORMAT_LINE_YM = "yyyy-MM";
    public static final String CHINESE_DATE_FORMAT_SPOT = "yyyy.MM.dd";
    public static final String CHINESE_DATE_FORMAT_SPOT_ALL = "yyyy.MM.dd HH:mm:ss";
    public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
    public static final String CHINESE_DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String CHINESE_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String DATETIME_NOT_SEPARATOR="yyyyMMddHHmmssSSS";
    public static final String TIME_NOT_SEPARATOR="HHmmssSSS";
    public static final String MONTH_DAY="M月dd日";
    public static final String MONTH_WEEK_DAY="M月dd日 (周)";
    public static final String MONTH_DAY_WEEK_TIMES="M.dd(周) HH:mm";
    public static final String MONTH_DAY_WEEK_DATE="M月dd日(周)";
    public static final String MONTH_DAY_WEEK_EN_TIMES="yyyy.MM.dd 星期  HH:mm";
    public static final String HM_TIME_SPOT = "HH:mm";
    public static final long DAY_LONG = 60*60*24l;

    private static final String[] SUPPORT_ALL_FORMATS = new String[]{CHINESE_DATE_FORMAT_LINE,CHINESE_DATE_FORMAT_LONG,
            CHINESE_DATETIME_FORMAT_LINE, CHINESE_DATE_FORMAT_SLASH, CHINESE_DATETIME_FORMAT_SLASH};

    private static final String DEFAULT_DATE_FORMAT = CHINESE_DATETIME_FORMAT_LINE;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    /**
     * 日期格式化
     * @param date 日期
     * @param pattern 例：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public synchronized static String format(Date date, String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    /**
     * 将日期转换成yyyy-MM-dd HH:mm:ss的形式
     * @param date 要转换的日期
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将当前时间转换成需要的形式，如：yyyy-MM-dd HH:mm:ss，yyyy-MM-dd
     * @param pattern
     * @return
     */
    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 将时间字符串，转换成pattern格式的时间
     * @param dateString 时间字符串
     * @param pattern
     * @return
     */
    public synchronized static Date parse(String dateString, String pattern) {
        sdf.applyPattern(pattern);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("parse String[" + dateString + "] to Date faulure with pattern[" + pattern
                    + "].");
        }
    }

    public synchronized static Date parse(String dateString, String[] patterns) {
        for (String pattern : patterns) {
            if (StringUtils.isBlank(pattern)) {
                continue;
            }
            sdf.applyPattern(pattern);
            try {
                return sdf.parse(dateString);
            } catch (Exception e) {
                // ignore exception
                continue;
            }
        }
        throw new UnsupportedOperationException("Parse String[" + dateString + "] to Date faulure with patterns["
                + Arrays.toString(patterns) + "]");

    }

    /**
     * 将时间字符串转为时间
     * @param dateString
     * @return
     */
    public static Date parse(String dateString) {
        return parse(dateString, SUPPORT_ALL_FORMATS);
    }

    /**
     * 获取某个时间多少毫秒后的时间
     * @param date 时间
     * @param millisecond 毫秒
     * @return
     */
    public static Date addDate(Date date, long millisecond) {
        return new Date(date.getTime() + millisecond);
    }

    /**
     * 获取某一天的明天的当前时间
     * @param date
     * @return
     */
    public static Date addDay(Date date) {
        long oneDayMillisecond = 24 * 60 * 60 * 1000l;
        return addDate(date, oneDayMillisecond);
    }

    /**
     * 获取某一天的昨天的当前时间
     * @param date 日期
     * @return
     */
    public static Date minusDay(Date date) {
        long oneDayMillisecond = 24 * 60 * 60 * 1000l;
        return addDate(date, -oneDayMillisecond);
    }

    /**
     * 获取当前时间的时间戳
     * @return
     */
    public static Long getCurrentLongDate() {
        return new Date().getTime();
    }

    /**
     * 获取某个时间相隔多少天前或者后的的时间
     * @param date 时间
     * @param day 相隔的天上，之前为负，之后为正，例：dateAddDay(new Date(),-35)
     * @return
     */
    public static String dateAddDay(Date date,int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE,day);
        cl.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CHINESE_DATETIME_FORMAT_LINE);
        return sdf.format(cl.getTime());
    }


    /**
     * 将时间戳，转换为Date
     * @param date
     * @return
     */
    public static Date parseLong2Date(Long date) {
        return new Date(date*1000);
    }


    /**
     * 获取当前天 的long值  即：2015-01-01 00：00：00的 long值
     * @return
     */
    public static  Long getCurrentLongDay(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis()/1000;
    }


    /**
     * 获取当前天 的long值  即：2016-01-01 00：00：00的 long值/2016-01-01 hour_of_day：minute：second
     * @param hour_of_day 小时
     * @param minute  分钟
     * @param second  秒钟
     * @return
     */
    public static  Long getCurrentLongDay(int hour_of_day,int minute,int second){
        Calendar  calendar  = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 获取当前天 的long值  即：2015-01-01 23：59：59的 long值
     * @return
     */
    public static  Long getLastLongDay(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取下前天 的long值  即：2015-01-01 00：00：00的 long值
     * @return
     */
    public static  Long getNextLongDay(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取 传入的日期的 0：0：0对应的long值
     * @return
     */
    public static  Long getDateZeroLong(Long date){
        Calendar  calendar  = Calendar.getInstance();
        calendar.setTime(parseLong2Date(date));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 取当前月的第一天,按pattern显示
     * @param pattern
     * @return
     */
    public static String getnowMonthFristDay(String pattern){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return DatesUtil.format(c.getTime(), pattern);
    }


    /**
     * 取某个月之前或之后的第几天,并以pattern显示
     * @param month 月
     * @param day 天
     * @param pattern
     * @return
     */
    public static String getnextThreeMonthtoday(int month,int day,String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);//设置为1号,当前日期既为本月第一天
        Date threeMonth =calendar.getTime();
        return DatesUtil.format(threeMonth, pattern);
    }

    /**
     * 获取昨天凌晨的long值
     * @return
     */
    public static Long getYestodayZeroLong(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,1);
        return calendar.getTimeInMillis();
    }


    /**
     * 根据天数获取几天前的最早时间
     * @param daynum 天数
     * @return
     * @date 2015年2月9日
     */
    public static Long getDatebyDaynum(int daynum){
        Calendar  calendar  = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,daynum);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前天时间 23:59:59的 long值
     * @return
     */
    public static  Long getLastLongDayNum(int daynum){
        Calendar  calendar  = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,daynum);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取前两天的时间long值  如：20150114
     * @return
     */
    public static Long getBeforeTwoDaysLong(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        String daysLong = format(calendar.getTime(), CHINESE_DATE_FORMAT_LONG);
        return NumberUtils.toLong(daysLong);
    }

    /**
     * 两个时间相差天数
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static int daysBetween(Date date1,Date date2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 相差天数,不足一天按一天算
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static int daysBetween2(Date date1,Date date2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        between_days = (time2-time1)%(1000*3600*24) > 0 ? between_days + 1 : between_days;
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定天的long值
     * @param dateStr 时间
     * @param pattern 格式
     * @param subtractDate  减的天数
     * @return
     */
    public static long getAssignDateLong(String dateStr, String pattern,long subtractDate){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        long dateLong = 0;
        try {
            dateLong = (df.parse(dateStr).getTime()-subtractDate*1000)/1000;
        } catch (ParseException e) {
            logger.error("日期格式化错误:" + e.getMessage());
        }
        return dateLong;
    }


    /**
     * 验证是否日期
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String str,String pattern) {
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
        return true;
    }


    /**
     * 获取包含星期几的 日期格式
     * @param date
     * @param pattern
     * @return MM.dd 周- HH:mm
     * pattern格式 中必须包含【周】处理时进行替换
     */
    public static String getContainsWeekStr(Long date,String pattern){
        Date  dateTime = new Date(date);;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        String resultDate = DatesUtil.format(dateTime, pattern);
        return resultDate.replace("周",WEEK_NAMES[dayOfWeek]);
    }

    /**
     * 获取包含星期几的 日期格式
     * @param date
     * @param pattern
     * @return MM.dd 星期- HH:mm
     * pattern格式 中必须包含【星期】处理时进行替换
     */
    public static String getContainsEnWeekStr(Long date,String pattern){
        Date  dateTime = new Date(date);;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        String resultDate = DatesUtil.format(dateTime, pattern);
        return resultDate.replace("星期",WEEK_EN_NAMES[dayOfWeek]);
    }

    /**
     * 时间相加
     * @param date 时间
     * @param day 天数
     * @return
     */
    public static Date addDay(Date date,int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE,day);
        return cl.getTime();
    }


    /**
     * 获取某日期【第一天日期】 的long值  即：2016-07-01 00:00:00的 long值/dataStr hour_of_day：minute：second
     * @param dataStr 日期  如：2016-07-20
     * @param hour_of_day 小时
     * @param minute  分钟
     * @param second  秒钟
     * @return
     */
    public static  Long getFirstLongDay(String dataStr,String pattern,int hour_of_day,int minute,int second){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(DatesUtil.parse(dataStr,pattern));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,gc.get(GregorianCalendar.YEAR));
        cal.set(Calendar.MONTH, gc.get(GregorianCalendar.MONTH)+1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.set(Calendar.HOUR_OF_DAY,hour_of_day);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);

        return cal.getTimeInMillis();
    }

    /**
     * 获取某日期【最后一天日期】 的long值  即：2016-07-31 00:00:00的 long值/dataStr hour_of_day：minute：second
     * @param dataStr 日期  如：2016-07-20
     * @param hour_of_day 小时
     * @param minute  分钟
     * @param second  秒钟
     * @return
     */
    public static  Long getLastLongDay(String dataStr,String pattern,int hour_of_day,int minute,int second){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(DatesUtil.parse(dataStr,pattern));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,gc.get(GregorianCalendar.YEAR));
        cal.set(Calendar.MONTH, gc.get(GregorianCalendar.MONTH)+1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY,hour_of_day);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);
        return cal.getTimeInMillis()/1000;

    }


    /**
     * 判断是否是周末
     * @return true:是,false:不是
     */
    public static boolean isWeekend(Date  date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week=cal.get(Calendar.DAY_OF_WEEK)-1;
        if(week ==6 || week==0){//0代表周日，6代表周六
            return true;
        }
        return false;
    }

    /**
     * 获得本周一0点时间
     * @return
     */
    public static Long getWeekMorningLong() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return  cal.getTime().getTime()/1000;
    }

    /**
     * 获得下周一0点时间
     * @return
     */
    public  static Long getWeekNightLong() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(getWeekMorningLong()*1000));
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime().getTime();
    }


    /**
     * 校验时间格式 HH:mm
     * @param time
     * @return
     */
    public static boolean validateTime(String time){
        time = StringUtils.trim(time);
        if (StringUtils.isBlank(time)){
            return false;
        }

        if (StringUtils.length(time) !=5 ){
            return false;
        }

        if (!StringUtils.contains(time,COLON)){
            return false;
        }

        String[] times = time.split(COLON);
        if (times.length != 2){
            return false;
        }

        String hours = times[NumberUtils.INTEGER_ZERO];
        String minites = times[NumberUtils.INTEGER_ONE];
        if (hours.length()!=2 || minites.length() !=2
                ||  !NumberUtils.isDigits(hours)
                || !NumberUtils.isDigits(minites)){
            return false;
        }

        int hour  = NumberUtils.toInt(hours);
        if (hour < 0 || hour > 23){
            return false;
        }

        int minite = NumberUtils.toInt(minites);
        if (minite < 0 || minite > 59 ){
            return false;
        }

        return true;
    }

    /**
     * 获取传入时间开始的long值  即：2015-01-01 00：00：00的 long值
     * @return
     */
    public static  Long getStartLongDayByCalendar(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis()/1000*1000;
    }

    /**
     * 获取传入时间结束的long值  即：2015-01-01 23：59：59的 long值
     * @return
     */
    public static  Long getLastLongDayByCalendar(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTimeInMillis()/1000*1000;
    }

    /**
     * 获取前昨天的时间long值  如：20150114
     * @return
     */
    public static Calendar getBeforeOneDaysLong(){
        Calendar  calendar  = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        return calendar;
    }

    /**
     * 获取上周一时间
     */
    public static Calendar lastMonday() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        return calendar;
    }

    /**
     * 获取上周五时间
     */
    public static Calendar lastFirday() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        return calendar;
    }

}