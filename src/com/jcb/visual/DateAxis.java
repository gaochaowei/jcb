package com.jcb.visual;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class DateAxis {
	public Range.Int vr = new Range.Int();
	public Range.Date dr = new Range.Date();

	public double getScale() {
		return (dr.length() + 2d) / vr.length();
	}

	private int getX0() {
		return vr.l + (int) (1 / getScale());
	}

	public Date getValue(int x) {
		int days = (int) Math.round((x - getX0()) * getScale());
		return DateUtils.addDays(dr.l, days);
	}

	public int getX(Date date) {
		return getX0()
				+ (int) (CommonUtils.diffInDays(dr.l, date) / getScale());
	}

	public int length() {
		return vr.length();
	}

	public void paint(Graphics g, int p) {
		g.setColor(Color.GRAY);
		g.drawLine(vr.l, p, vr.h, p);
		Map<Date, String> labels = AxisUtils.getCalibration(dr.l, dr.h);
		for (Date d : labels.keySet()) {
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(labels.get(d), getX(d)
					- metrics.stringWidth(labels.get(d)) / 2, p
					+ metrics.getAscent());
			g.drawLine(getX(d), p, getX(d), p - 5);
		}
	}

	@Override
	public String toString() {
		return "Axis[vr=" + vr + ",dr=" + dr + "]";
	}
}
