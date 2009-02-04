package com.jcb.util;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;

public class CommonUtils {

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date getDate(String s) {
		return getDate(s, "dd/MM/yyyy");
	}

	public static Date getDate(String s, String format) {
		try {
			String[] formats = new String[] { format };
			return DateUtils.parseDate(s, formats);
		} catch (Exception e) {
			return null;
		}
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}

	public static java.sql.Date sqlDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	public static int getYear(Date date) {
		return get(date, Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		return get(date, Calendar.MONTH);
	}

	public static int getDate(Date date) {
		return get(date, Calendar.DAY_OF_MONTH);
	}

	public static int getDay(Date date) {
		return get(date, Calendar.DAY_OF_WEEK);
	}

	public static int getHours(Date date) {
		return get(date, Calendar.HOUR_OF_DAY);
	}

	public static int getMinutes(Date date) {
		return get(date, Calendar.MINUTE);
	}

	public static int getSeconds(Date date) {
		return get(date, Calendar.SECOND);
	}

	public static int get(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	public static int diffInDays(Date start, Date end) {
		return (int) diff(start, end, Calendar.DATE);
	}

	public static double diff(Date start, Date end, int field) {
		double d = end.getTime() - start.getTime();
		switch (field) {
		case Calendar.YEAR:
			return d / (365 * 24 * 60 * 60 * 1000);
		case Calendar.WEEK_OF_YEAR:
			return d / (7 * 24 * 60 * 60 * 1000);
		case Calendar.DATE:
			return d / (24 * 60 * 60 * 1000);
		case Calendar.HOUR:
			return d / (60 * 60 * 1000);
		case Calendar.MINUTE:
			return d / (60 * 60 * 1000);
		case Calendar.SECOND:
			return d / (60 * 60 * 1000);
		default:
			return d;
		}
	}

	public static Number parse(String s, NumberFormat fmt) {
		try {
			return fmt.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void printArray(Object[] arr) {
		for (Object obj : arr) {
			System.out.println(obj);
		}
	}

	public static void printBean(Object bean) {
		System.out.println(getBeanValue(bean));
	}

	public static String getBeanValue(Object bean) {
		try {
			return BeanUtils.describe(bean).toString();
		} catch (Exception e) {
			return bean.hashCode() + "@" + bean.getClass().getCanonicalName();
		}
	}
}
