package com.jcb.visual;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

public class IndexAxis {

	public Range.Int vr = new Range.Int();
	public Range.Int dr = new Range.Int();

	public Date[] dates = new Date[100];

	public int length() {
		return vr.length();
	}

	public int getValue(int x) {
		int v = (int) Math.round((x - getX0()) * getScale());
		return Math.max(Math.min(v, dr.h), dr.l);
	}

	public int getX(int value) {
		return getX0() + (int) Math.round((value / getScale()));
	}

	public void paint(Graphics g, int y0) {
		g.setColor(Color.GRAY);
		g.drawLine(vr.l, y0, vr.h, y0);
		Map<Integer, String> labels = getCalibration(dates);
		for (Integer i : labels.keySet()) {
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(labels.get(i), getX(i)
					- metrics.stringWidth(labels.get(i)) / 2, y0
					+ metrics.getAscent());
			g.drawLine(getX(i), y0, getX(i), y0 - 5);
		}
	}

	private int getX0() {
		return vr.l - (int) ((dr.l - 1) / getScale());
	}

	public double getScale() {
		return 1d * (dr.h - dr.l + 2) / vr.length();
	}

	public Map<Integer, String> getCalibration(Date[] dates) {
		int[][] spans = { { Calendar.DATE, 1 }, { Calendar.DATE, 2 },
				{ Calendar.DATE, 5 }, { Calendar.DATE, 10 },
				{ Calendar.DATE, 15 }, { Calendar.MONTH, 1 },
				{ Calendar.MONTH, 2 }, { Calendar.MONTH, 3 },
				{ Calendar.MONTH, 6 }, { Calendar.YEAR, 1 },
				{ Calendar.YEAR, 2 }, { Calendar.YEAR, 5 },
				{ Calendar.YEAR, 10 }, { Calendar.YEAR, 20 },
				{ Calendar.YEAR, 25 }, { Calendar.YEAR, 50 },
				{ Calendar.YEAR, 100 } };

		Date start = dates[0];
		Date end = dates[dates.length - 1];
		long span = end.getTime() - start.getTime();
		int unit = 0;
		int interval = 0;
		for (int i = 0; i < spans.length; i++) {
			long dur = getSpanTime(spans[i][0], spans[i][1]);
			long n = span / dur;
			if (n < 15 && n >= 6) {
				unit = spans[i][0];
				interval = spans[i][1];
				break;
			}
		}
		// System.out.println("unit = " + unit + " interval = " + interval);
		Map<Integer, String> map = new HashMap<Integer, String>();

		for (int i = 0; i < dates.length; i++) {
			if (i == 0) {
				// map.put(i + 1, format(dates[i], unit, interval));
			} else if (!isSamePrevRoundDate(dates[i - 1], dates[i], unit,
					interval)) {
				map.put(i + 1, format(dates[i], unit));
			}
		}
		// System.out.println(map.keySet());
		return map;
	}

	public static long getSpanTime(int unit, int interval) {
		long dayspan = 1000 * 60 * 60 * 24;
		switch (unit) {
		case Calendar.DATE:
			return dayspan * interval;
		case Calendar.MONTH:
			return dayspan * interval * 30;
		case Calendar.YEAR:
			return dayspan * interval * 365;
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

	@Override
	public String toString() {
		return "Axis[vr=" + vr + ",dr=" + dr + "]";
	}
}
