package org.social.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author caowenhua
 *
 */
public class DateUtil {

	public static String CalcMinutes(String date, int minutes) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tempDate);
		calendar.add(Calendar.MINUTE, minutes);
		tempDate = calendar.getTime();
		SimpleDateFormat dfs2 = new SimpleDateFormat("HH:mm",
				Locale.getDefault());
		return dfs2.format(tempDate);

	}

	public static int getYear(String date) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return tempDate.getYear();
	}

	public static int getMonth(String date) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return tempDate.getMonth();
	}

	public static int getDay(String date) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return tempDate.getDay();
	}

	public static Date getDateFormatDate(String date) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return tempDate;

	}

	public static String getDateFormat(String date) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date tempDate = null;
		try {
			tempDate = dfs.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return dfs.format(tempDate);

	}

	public static int getDifferDays(String strBegin, String strEnd) {
		long DAY = 24L * 60L * 60L * 1000L;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(strBegin);
			d2 = df.parse(strEnd);
		} catch (Exception e) {

		}
		return (int) ((d2.getTime() - d1.getTime()) / DAY);
	}

	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",
				Locale.getDefault());
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取当前日期,返回格式String(yyy-mm-dd)
	 */
	public static String getNowDate() {
		String temp = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	public static String getNowDateWithTime() {
		String temp = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	public static String DataFormat(int year, int month, int day) {
		String monthString = "";
		String dayString;
		if (month < 10)
			monthString = "0" + month;
		else {
			monthString = month + "";
		}
		if (day < 10)
			dayString = "0" + day;
		else {
			dayString = "" + day;
		}
		return year + "-" + monthString + "-" + dayString;

	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * long 转成 yyyy-MM
	 * 
	 * @param time
	 * @return
	 */
	public static String getYearMouthByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM",
				Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	/**
	 * long 转成 yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static String getYearMouthDayByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	/**
	 * "yyyy-MM-dd HH:mm"
	 * @param time
	 * @return
	 */
	public static String getDateByTime(long time) {
		String temp = "";
		Date dt = new Date(time * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	/**
	 * long 转成 MM-dd HH:mm
	 * 
	 * @param time
	 * @return
	 */
	public static String getMouthDayHourMinByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}
	
	/**
	 * long 转成 HH:mm
	 * 
	 * @param time
	 * @return
	 */
	public static String getHourMinByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}
	
	/**
	 * long 转成 HH
	 * 
	 * @param time
	 * @return
	 */
	public static String getHourByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}
	
	/**
	 * long 转成 dd
	 * 
	 * @param time
	 * @return
	 */
	public static String getDayByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}
	
	/**
	 * long 转成 MM
	 * 
	 * @param time
	 * @return
	 */
	public static String getMouthByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}
	
	/**
	 * long 转成 MM
	 * 
	 * @param time
	 * @return
	 */
	public static String getYearByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	/**
	 * long 转成 mm
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinuteByTime(long time) {
		String temp = "";
		Date dt = new Date(time*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("mm", Locale.getDefault());
		temp = sdf.format(dt);
		return temp;
	}

	/**
	 * 
	 * @param str
	 *            日期字符串(eg:"08/31/2006 21:08:00")
	 * @param format
	 *            日期字符串(eg:"MM/dd/yyyy HH:mm:ss")
	 * @return 0为异常
	 */
	public static long getTimeByDateStr(String str, String format) {
		long time = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date dt2 = sdf.parse(str);
			// 继续转换得到秒数的long型
			time = dt2.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss
	// yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date date, String formatType) {
		return new SimpleDateFormat(formatType).format(date);
	}

	// long类型转换为String类型
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType)
			throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType)
			throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}
}
