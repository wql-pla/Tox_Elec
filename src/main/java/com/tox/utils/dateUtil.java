package com.tox.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

public class dateUtil {

	//格式(2000-01-01)
	private static final DateFormat formatYmd =new SimpleDateFormat("yyyy-MM-dd");
	
	private static final DateFormat formatBaiDu = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//格式(2000-01-01 00:00:00)
	private static final DateFormat formatHms =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final DateFormat formatYm = new SimpleDateFormat("yyyy-MM");
	
	private static final DateFormat formatYY = new SimpleDateFormat("yyyy-MM-01 00:00:00");
	
	private static final DateFormat format5 = new SimpleDateFormat("yyyy-MM-05 HH:mm:ss");
	
	
	public static String getStrBd(Date date) {
		
		return formatBaiDu.format(date);
		
	}
	
	public static Date getDateYm(String date) throws ParseException{
		
		return formatYm.parse(date);
		
	}
	
	//Date转换String : 年月日格式
	public static String getStrYm(Date date){
		
		return formatYm.format(date);
		
	}
	
	//String转换Date : 年月日格式
	public static Date getDateYmd(String date) throws ParseException{
		
		return formatYmd.parse(date);
		
	}
	
	public static Date getDateYY(String date) throws ParseException{
		
		return formatYY.parse(date);
		
	}
	
	//String转换Date : 年月日 时分秒格式
	public static Date getDateHms(String date) throws ParseException{
		
		return formatHms.parse(date);
		
	}
	
	//Date转换String : 年月日格式
	public static String getStrYmd(Date date){
		
		return formatYmd.format(date);
		
	}
	
	//Date转换String : 年月日 时分秒格式
	public static String getStrHms(Date date){
		
		return formatHms.format(date);
		
	}
	
	//时间计算(秒)
	public static Date reckonSeconds(Date date,int seconds){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        date = calendar.getTime();
        return date;
	}
	
	//计算时间(分钟)
	public static Date reckonMinutes(Date date,int minutes){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        date = calendar.getTime();
        return date;
	}
	
	//计算时间(小时)
	public static Date reckonHours(Date date,int hours){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,hours);
        date = calendar.getTime();
        return date;
	}
	
	//时间计算(天)
	public static Date reckonDays(Date date,int days){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        date = calendar.getTime();
        return date;
	}
	
	//时间计算(月)
	public static Date reckonMonths(Date date,int months){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        date = calendar.getTime();
        return date;
	}
	
	//时间计算(年)
	public static Date reckonYears(Date date,int years){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        date = calendar.getTime();
        return date;
	}
	
	//按照hour获取日期的      年-月-日  hour-分-秒  
	public static Date getHourMinSeconed(Date date,int hour) throws ParseException{
		
		String strDate = formatYmd.format(date);
		
		String formatDate = strDate + " "+hour+":00:00";
		
		return formatHms.parse(formatDate);
		
	}
	
	//按照hour获取日期的  年-月-日  (hour-1)-59-59
	public static Date getHourEnd(Date date,int hour) throws ParseException{
		
		String strDate = formatYmd.format(date);
		
		String formatDate = strDate + " "+(hour-1)+":59:59";
		
		return formatHms.parse(formatDate);
		
	}
	
	//得到今天的开始00:00:00
	public static Date getFiveDate(Date date) throws ParseException{
		
		String strDate = format5.format(date);
		
		String formatDate = strDate + " 00:00:00";
		
		return formatHms.parse(formatDate);
		
	}
	
	//得到该月份5号的00:00:00
	public static Date getBeginDate(Date date) throws ParseException{
		
		String strDate = formatYmd.format(date);
		
		String formatDate = strDate + " 00:00:00";
		
		return formatHms.parse(formatDate);
		
	}
	
	//得到今天06:00:00
	public static Date getSixDate(Date date) throws ParseException{
		
		String strDate = formatYmd.format(date);
		
		String formatDate = strDate + " 06:00:00";
		
		return formatHms.parse(formatDate);
		
	}
	
	//获取两个日期的时间差(天)
	public static int getDay(Date date1,Date date2) throws ParseException{
		
		String fromDate = formatHms.format(date1);
		
		String toDate = formatHms.format(date2);  
		
		long from = formatHms.parse(fromDate).getTime(); 
		
		long to = formatHms.parse(toDate).getTime();  
		
		double hours = (double)(to - from)/(1000 * 60 * 60 * 24);
		
		int day = (int) Math.ceil(hours);
		
		return day;
		
	}
	
	//获取两个日期的时间差(小时)
	public static long getHour(Date date1,Date date2) throws ParseException{
		
		String fromDate = formatHms.format(date1);
		
		String toDate = formatHms.format(date2);  
		
		long from = formatHms.parse(fromDate).getTime(); 
		
		long to = formatHms.parse(toDate).getTime();  
		
		long hours = (to - from)/(1000 * 60 * 60); 
		
		return hours;
		
	}
	
	//获取两个日期的时间差(分钟)
	public static long getMinutes(Date date1,Date date2) throws ParseException{
		
		String fromDate = formatHms.format(date1);
		
		String toDate = formatHms.format(date2);  
		
		long from = formatHms.parse(fromDate).getTime(); 
		
		long to = formatHms.parse(toDate).getTime();  
		
		long minutes = (to - from)/(1000 * 60); 
		
		return minutes;
		
	}
	
	//获取两个日期的时间差(秒)
	public static long getSeconds(Date date1,Date date2) throws ParseException{
		
		String fromDate = formatHms.format(date1);
		
		String toDate = formatHms.format(date2);  
		
		long from = formatHms.parse(fromDate).getTime(); 
		
		long to = formatHms.parse(toDate).getTime();  
		
		long minutes = (to - from)/(1000); 
		
		return minutes;
		
	}
	
	//得到今天的结束时间 23:59:59
	public static Date getEndDate(Date date) throws ParseException{
		
		String strDate = formatYmd.format(date);
		
		String formatDate = strDate + " 23:59:59";
		
		return formatHms.parse(formatDate);
		
	}
	
	
	public static long get_D_Plaus_1(Calendar c) {  
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);  
        return c.getTimeInMillis();  
    }  
	
	// yyyymmdd 转yyyy-mm-dd
	public static String strToDateFormat(String date) throws ParseException {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
       formatter.setLenient(false);
       Date newDate= formatter.parse(date);
       formatter = new SimpleDateFormat("yyyy-MM-dd");
       return formatter.format(newDate);
	}
	
	//返回日期String类型
	public static String getStrDay(Date date){
		
		return formatYmd.format(date).substring(8);
		
	}
	
	//获取制定日期最后一天
	public static int getLastDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int MaxDate=calendar.get(Calendar.DATE);
		System.out.println("该月最大天数:"+MaxDate);
        return MaxDate;
	} 
	
	//获取两个日期间的所有月份(返回每个月1号00:00:00)
	public static List<Date> getMonthBetween(String minDate, String maxDate,int type) throws ParseException {
	    ArrayList<Date> result = new ArrayList<Date>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();

	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	    Calendar curr = min;
	    while (curr.before(max)) {
	    	
	    	if(type == 1){
	    		
	    		result.add(getBeginDate(curr.getTime()));
	   	     	curr.add(Calendar.MONTH, 1);
	    		
	    	}else if(type == 2){
	    		
	    		result.add(getFiveDate(curr.getTime()));
	    		curr.add(Calendar.MONTH, 1);
	    	}
	    	
	    }

	    return result;
	  }
	
	/**
	 * 获取当月第一天日期
	 * 返回 xxxx-xx-01 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getFirstDate() {
	    Calendar c = Calendar.getInstance();    
        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH) , 1, 0, 0, 0);
        String first = getStrHms(c.getTime());
        return c.getTime();
	}
	
	/**
	 * 获取当月最后一天日期
	 * 返回xxxx-xx-最后一天 23:59:59
	 * @return
	 */
	public static Date getLastDate() {
		Calendar c = Calendar.getInstance();    
        //c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        String last = getStrHms(c.getTime());
        return c.getTime();
	}
	
	/**
	 * 根据指定日期的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDate(Date date) {
	    Calendar c = Calendar.getInstance();   
	    c.setTime(date);
        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH) , 1, 0, 0, 0);
        String first = getStrHms(c.getTime());
        return c.getTime();
	}
	
	/**
	 * 判断当前月份是不是最后一天
	 * @param date
	 * @return
	 */
	public static boolean isMonthEnd(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.DATE)==cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        else
            return false;
    }
	
	
	/**
	 * 根据指定日期的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDate(Date date) {
		Calendar c = Calendar.getInstance();    
        c.setTime(date);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        String last = getStrHms(c.getTime());
        return c.getTime();
	}
	
	public static boolean isSameDate(Date date1,Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}
	
	public static void main(String[] args) throws ParseException{
		List<Date> monthBetween = getMonthBetween("2018-02-02","2018-09-09",1);
		for(Date date : monthBetween) {
			System.out.println(date);
		}
	}
}
