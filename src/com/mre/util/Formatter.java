package com.mre.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatter {

	public static String format(Date time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String jspTime = format.format(time);
		return jspTime;

	}

	public static String formatm(Date time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String jspTime = format.format(time);
		return jspTime;

	}
	
	/**
	 * 根据输入的时间和格式格式化时间
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatWithPattern(Date time, String pattern) {
		String strtime = "";
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			strtime = format.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strtime;
	}

	public static String getNewString(String str)
			throws UnsupportedEncodingException {
		return new String(str.getBytes("iso-8859-1"), "utf-8");
	}

	public static String formatISOTime(Date time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String jspTime = format.format(time);
		return jspTime;
	}

	public static Date getDateFromString(String time) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.parse(time);

	}

	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}
}
