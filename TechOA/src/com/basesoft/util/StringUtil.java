package com.basesoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	 * 数组转化为字符串，中间追加分割符， 字符串用''引起来，主要hql里面使用
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
	 * list转化为字符串，中间追加分割符， 字符串用''引起来，主要hql里面使用
	 * @param list list
	 * @param sep 分隔符
	 * @param name 取的名称
	 * @return
	 */
	public static String ListToStringAdd(List list, String sep, String name) {
		if (list == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			String s = map.get(name)==null?"":map.get(name).toString();
			String[] ss = s.split(sep);
			for(int j=0;j<ss.length;j++){
				if ("".equals(sb.toString())) {
					sb.append("'" + ss[j] + "'");
				} else {
					sb.append(sep + "'" + ss[j] + "'");
				}
			}
		}
		if ("".equals(sb.toString())) {
			sb.append("''");
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
	
	/**
	 * 判断日期是否为周末
	 * @param date
	 * @return
	 */
	public static boolean isWeekEnd(Date date){
		boolean b = false;
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int dayOfWeek = ca.get(ca.DAY_OF_WEEK);
		
		if(dayOfWeek==1||dayOfWeek==7){
			b = true;
		}
		
		return b;
	}
	
	/**
	 * 生成length位的由0-9构成的字符串
	 * @param length 字符串长度
	 * @return
	 */
	public static String createNumberString(int length){
		final String numberChar = "0123456789";
		StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(numberChar.charAt(random.nextInt(numberChar.length()))); 
        } 
		return sb.toString();
	}
	
	/**
	 * 格式化字符串
	 * @param s 原始字符串
	 * @param splitechars 分割符
	 * @return 格式化后的字符串数组
	 */
	public static String[] splite(String s, String[] splitechars){
		for(String ch:splitechars){
			s = s.replaceAll(ch,"@");
		}
		String r[]=s.split("@");
		
		List<String> list = new ArrayList<String>();
		for(int i=0;i<r.length;i++){
			if(!"".equals(r[i])){
				list.add(r[i]);
			}
		}
		String[] returnS = new String[list.size()];
		int i = 0;
		for(String o:list){
			returnS[i++] = o;
		}
		return returnS;
	}
	
	/**
	 * 判断ip是否在ip段内
	 * @param ipSection 以'-'分隔的ip段
	 * @param ip 需要判断的ip地址
	 * @return
	 */
	public static boolean ipIsValid(String ipSection, String ip) {   
        if (ipSection == null)   
            throw new NullPointerException("IP段不能为空！");   
        if (ip == null)   
            throw new NullPointerException("IP不能为空！");   
        ipSection = ipSection.trim();   
        ip = ip.trim();   
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";   
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;   
        if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))   
            return false;   
        int idx = ipSection.indexOf('-');   
        String[] sips = ipSection.substring(0, idx).split("\\.");   
        String[] sipe = ipSection.substring(idx + 1).split("\\.");   
        String[] sipt = ip.split("\\.");   
        long ips = 0L, ipe = 0L, ipt = 0L;   
        for (int i = 0; i < 4; ++i) {   
            ips = ips << 8 | Integer.parseInt(sips[i]);   
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);   
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);   
        }   
        if (ips > ipe) {   
            long t = ips;   
            ips = ipe;   
            ipe = t;   
        }   
        return ips <= ipt && ipt <= ipe;   
    }   

}
