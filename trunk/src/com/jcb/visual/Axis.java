package com.jcb.visual;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;

import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class Axis {

	public enum Align {

		HORIZONTAL, VERTICAL
	};

	public enum Measure {

		LINEAR, LOG
	};

	public Range.Int vr = new Range.Int();
	public Range.Double dr = new Range.Double();
	public Align align = Align.HORIZONTAL;
	public Measure measuer = Measure.LINEAR;

	public double getScale() {
		return dr.length() / vr.length();
	}

	private int getP0() {
		switch (align) {
		case HORIZONTAL:
			return vr.l - (int) (dr.l / getScale());
		default:
			return vr.h + (int) Math.round(dr.l / getScale());
		}
	}

	public double getData(int position) {
		switch (measuer) {
		case LINEAR:
			switch (align) {
			case HORIZONTAL:
				return (position - getP0()) * getScale();
			default:
				return (getP0() - position) * getScale();
			}
		default:
			switch (align) {
			case HORIZONTAL:
				double t = 1d * vr.length() / (position - vr.l);
				double v = Math.exp((Math.log(dr.h) - (t + 1) * Math.log(dr.l))
						/ t);
				return v;
			default:
				t = 1d * vr.length() / (position - vr.l);
				v = Math.exp((Math.log(dr.h) - (t + 1) * Math.log(dr.l)) / t);
				return v;
			}
		}
	}

	public int getPosition(double data) {
		switch (measuer) {
		case LINEAR:
			switch (align) {
			case HORIZONTAL:
				return getP0() + (int) (data / getScale());
			default:
				return getP0() - (int) (data / getScale());
			}
		default:
			switch (align) {
			case HORIZONTAL:
				double r = (Math.log(data / dr.l)) / (Math.log(dr.h / dr.l));
				int y = (int) (vr.h - r * vr.length());
				return y;
			default:
				r = (Math.log(data / dr.l)) / (Math.log(dr.h / dr.l));
				y = (int) (vr.h - r * vr.length());
				return y;
			}
		}
	}

	public int length() {
		return vr.length();
	}

	public void paint(Graphics g, int p, int length) {
		g.setColor(Color.GRAY);
		String[] labels = AxisUtils.getCalibration(dr.l, dr.h);
		DecimalFormat fmt = AxisUtils.getFormat(AxisUtils.getUnit(dr.h - dr.l));
		switch (align) {
		case HORIZONTAL:
			g.drawLine(vr.l, p, vr.h, p);
			for (String s : labels) {
				double v = CommonUtils.parse(s, fmt).doubleValue();
				FontMetrics metrics = g.getFontMetrics();
				g.drawString(s, getPosition(v) - metrics.stringWidth(s) / 2, p
						+ metrics.getAscent());
				g.drawLine(getPosition(v), p, getPosition(v), p - length);
			}
			break;
		default:
			g.drawLine(p, vr.l, p, vr.h);
			for (String s : labels) {
				double v = CommonUtils.parse(s, fmt).doubleValue();
				FontMetrics metrics = g.getFontMetrics();
				int w0 = metrics.stringWidth(s);
				g.drawString(s, p - w0 - 2, getPosition(v)
						+ metrics.getAscent() / 2);
				g.drawLine(p, getPosition(v), p + length, getPosition(v));
			}
			break;
		}
	}

	@Override
	public String toString() {
		return "Axis[vr=" + vr + ",dr=" + dr + "]";
	}
}
