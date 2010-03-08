package com.basesoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Util类
 * @author 张海军
 *
 */
public class StringUtil {
	
	/**
	 * null转化为""
	 * @param str
	 * @return
	 */
	public static String nullToSpace(String str) {
		
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}
	
	/**
	 * ""转换为" "
	 * @param str
	 * @return
	 */
	public static String spaceToBlank(String str) {
		
		if ("".equals(nullToSpace(str))) {
			return " ";
		} else {
			return str;
		}
	}
	
	/**
	 * list转化为字符串，中间追加分割符
	 * @param strList
	 * @param sep
	 * @return
	 */
	public static String ListToString(String[] strList, String sep) {
		
		if (strList == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strList.length; i++) {
			if (i == 0) {
				sb.append(strList[i]);
			} else {
				sb.append(sep + strList[i]);
			}
		}
		return sb.toString();
		
	}
	
	/**
	 * list转化为字符串，中间追加分割符， 字符串用''引起来，主要hql里面使用
	 * @param strList
	 * @param sep
	 * @return
	 */
	public static String ListToStringAdd(String[] strList, String sep) {
		
		if (strList == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strList.length; i++) {
			if (i == 0) {
				sb.append("'" + strList[i] + "'");
			} else {
				sb.append(sep + "'" + strList[i] + "'");
			}
		}
		return sb.toString();
		
	}
	
	/**
	 * 字符串转化为日期
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date StringToDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 日期转化为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String DateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 取二个日期中间的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getBetweenDays(Date startDate, Date endDate) {
		
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		
		//if(startCal.after(endCal)) {
		//	startCal.setTime(endDate);
		//	endCal.setTime(startDate);
		//}
		
		int betweenYears = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
		int betweenDays = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR);
		
		for (int i = 0; i < betweenYears; i++) {      
			startCal.set(Calendar.YEAR, (startCal.get(Calendar.YEAR) + 1));      //X
            betweenDays += startCal.getActualMaximum(Calendar.DAY_OF_YEAR);      
        }
		
		return betweenDays;
		
	}
	
	/**
	 * 第n天后的日期
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date, int n) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, day + n);
		return cal.getTime();
		
	}
	
	/**
	 * 第n天前的日期
	 * @param date
	 * @return
	 */
	public static Date getBeforeDate(Date date, int n) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, day - n);
		return cal.getTime();
		
	}
	
	public static List<Date> getDateList(String start, String end){
		List returnList = new ArrayList<String>();
		
		Date startDate = StringToDate(start,"yyyy-MM-dd");
		Date endDate = StringToDate(end,"yyyy-MM-dd");
		
		for(Date s=startDate;s.before(endDate);s=getNextDate(s, 1)){
			returnList.add(s);
		}
		return returnList;
	}
	
	public static Date getEndOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.roll(cal.DATE, -1);
		
		return cal.getTime();
	}
	
	/**
	 * 是否达到提醒日期
	 * @param date 需要检查的日期
	 * @param i 提前i天提醒
	 * @return
	 */
	public static boolean isRemind(Date date, int i){
		boolean b = false;
		
		date = getBeforeDate(date, i);
		
		if(date.compareTo(new Date())>=0){
			b = true;
		}
		
		return b;
	}
}
