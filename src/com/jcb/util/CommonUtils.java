package com.jcb.util;

import java.util.Date;

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
}
