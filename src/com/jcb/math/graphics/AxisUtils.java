package com.jcb.math.graphics;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jcb.util.CommonUtils;

public class AxisUtils {

	public static String[] getLabels(double min, double max) {
		double unit = AxisUtils.getUnit(max - min);
		DecimalFormat format = AxisUtils.getFormat(unit);
		int low = (int) Math.ceil(min / unit);
		int high = (int) Math.floor(max / unit);
		String[] cals = new String[high - low + 1];
		for (int i = 0; i < cals.length; i++) {
			String label = format.format((low + i) * unit);
			cals[i] = label;
		}
		return cals;
	}

	public static double getUnit(double length) {
		double unit = 1d;
		double t = 0;
		while (true) {
			t = length / unit;
			if (t < 1) {
				unit = unit / 10;
			} else if (t >= 10) {
				unit = unit * 10;
			} else {
				if (t < 4) {
					if (t * 2 >= 4) {
						t = t * 2;
						unit = unit / 2;
					} else {
						t = t * 5;
						unit = unit / 5;
					}
				}
				break;
			}
		}
		return unit;
	}

	public static DecimalFormat getFormat(double unit) {
		DecimalFormat format = new DecimalFormat();
		String s = String.valueOf(unit);
		format.setGroupingSize(3);
		int fractionSize = 0;
		if (s.indexOf('.') >= 0 && !s.endsWith(".0")) {
			fractionSize = s.length() - s.indexOf('.') - 1;
		}
		format.setMinimumFractionDigits(fractionSize);
		format.setMaximumFractionDigits(fractionSize);
		return format;
	}

	public static Map<Date, String> getLabels(Date start, Date end) {
		int days = CommonUtils.diffInDays(start, end);
		int[][] spans = { { Calendar.DATE, 1 }, { Calendar.DATE, 2 },
				{ Calendar.DATE, 5 }, { Calendar.DATE, 10 },
				{ Calendar.DATE, 15 }, { Calendar.MONTH, 1 },
				{ Calendar.MONTH, 2 }, { Calendar.MONTH, 3 },
				{ Calendar.MONTH, 6 }, { Calendar.YEAR, 1 },
				{ Calendar.YEAR, 2 }, { Calendar.YEAR, 5 },
				{ Calendar.YEAR, 10 }, { Calendar.YEAR, 20 },
				{ Calendar.YEAR, 25 }, { Calendar.YEAR, 50 },
				{ Calendar.YEAR, 100 } };

		int unit = 0;
		int interval = 0;
		for (int i = 0; i < spans.length; i++) {
			int dur = AxisUtils.getSpanDate(spans[i][0], spans[i][1]);
			int n = days / dur;
			if (n < 15 && n >= 6) {
				unit = spans[i][0];
				interval = spans[i][1];
				break;
			}
		}
		Map<Date, String> map = new HashMap<Date, String>();

		for (int i = 0; i < days; i++) {
			Date date = DateUtils.addDays(start, i);
			if (isRoundDate(date, unit, interval)) {
				map.put(date, format(date, unit));
			}
		}
		return map;
	}

	public static int getSpanDate(int unit, int interval) {
		switch (unit) {
		case Calendar.DATE:
			return interval;
		case Calendar.MONTH:
			return interval * 30;
		case Calendar.YEAR:
			return interval * 365;
		default:
			return -100;
		}
	}

	public static boolean isSamePrevRoundDate(Date date1, Date date2, int unit,
			int interval) {
		int count = 0;
		Date start = (date1.before(date2)) ? date1 : date2;
		Date end = (date1.before(date2)) ? date2 : date1;
		for (Date temp = start; !temp.after(end); temp = DateUtils.addDays(
				temp, 1)) {
			if (isRoundDate(temp, unit, interval)) {
				if (temp.after(start) && temp.before(end)) {
					return false;
				} else if (temp.equals(end)) {
					return false;
				}
				count++;
			}
		}
		return true;
	}

	public static String format(Date date, int unit) {
		SimpleDateFormat df;
		switch (unit) {
		case Calendar.DATE:
			df = new SimpleDateFormat("dd MMM");
			return df.format(date);
		case Calendar.MONTH:
			df = new SimpleDateFormat("MMM yyyy");
			return df.format(date);
		case Calendar.YEAR:
			df = new SimpleDateFormat("yyyy");
			return df.format(date);
		default:
			df = new SimpleDateFormat("dd MMM yyyy");
			return df.format(date);
		}
	}

	public static boolean isRoundDate(Date date, int unit, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (unit == Calendar.DATE) {
			int dd = c.get(Calendar.DATE);
			if (interval >= 5 && dd > 27) {
				return false;
			}
			return dd == 1 || dd % interval == 0;
		} else if (unit == Calendar.MONTH) {
			int dd = c.get(Calendar.DATE);
			int mm = c.get(Calendar.MONTH);
			return dd == 1 && (mm == 1 || (mm + 1) % interval == 0);
		} else if (unit == Calendar.YEAR) {
			int dd = c.get(Calendar.DATE);
			int mm = c.get(Calendar.MONTH);
			int yyyy = c.get(Calendar.YEAR);
			return dd == 0 && mm == 0
					&& (yyyy == 0 || (yyyy + 1) % interval == 0);
		} else {
			return false;
		}
	}
}
