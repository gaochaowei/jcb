package com.jcb.visual;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

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
				double s = Math.log(dr.h / dr.l);
				double t = 1d * vr.length() / (position - vr.l);
				double v = Math.exp((Math.log(dr.h) - (t + 1) * Math.log(dr.l))
						/ t);
				return v;
			default:
				s = Math.log(dr.h / dr.l);
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
		String[] labels = getCalibration();
		switch (align) {
		case HORIZONTAL:
			g.drawLine(vr.l, p, vr.h, p);
			for (String s : labels) {
				String num = StringUtils.replace(StringUtils.replace(s, "M",
						"000000"), "K", "000");
				num = StringUtils.replace(num, "B", "000000000");
				double v = Double.parseDouble(num);
				FontMetrics metrics = g.getFontMetrics();
				g.drawString(s, getPosition(v) - metrics.stringWidth(s) / 2, p
						+ metrics.getAscent());
				g.drawLine(getPosition(v), p, getPosition(v), p - length);
			}
			break;
		default:
			g.drawLine(p, vr.l, p, vr.h);
			for (String s : labels) {
				String num = StringUtils.replace(StringUtils.replace(s, "M",
						"000000"), "K", "000");
				num = StringUtils.replace(num, "B", "000000000");
				double v = Double.parseDouble(num);
				FontMetrics metrics = g.getFontMetrics();
				int w0 = metrics.stringWidth(s);
				g.drawString(s, p - w0 - 2, getPosition(v)
						+ metrics.getAscent() / 2);
				g.drawLine(p, getPosition(v), p + length, getPosition(v));
			}
			break;
		}
	}

	private String[] getCalibration() {
		double d = 1d;
		double t = 0;
		while (true) {
			t = dr.length() / d;
			if (t < 1) {
				d = d / 10;
			} else if (t >= 10) {
				d = d * 10;
			} else {
				if (t < 4) {
					if (t * 2 >= 4) {
						t = t * 2;
						d = d / 2;
					} else {
						t = t * 5;
						d = d / 5;
					}
				}
				break;
			}
		}

		String s = String.valueOf(d);
		// System.out.println(s);
		String pattern = "0";
		if (s.indexOf('.') >= 0 && !s.endsWith(".0")) {
			int dec = s.length() - s.indexOf('.') - 1;
			pattern += ".";
			for (int i = 0; i < dec; i++) {
				pattern += "0";
			}
		}
		DecimalFormat fmt = new DecimalFormat(pattern);
		int i0 = (int) Math.ceil(dr.l / d);
		int i1 = (int) Math.floor(dr.h / d);
		// System.out.println("i0 = "+i0+" i1 = "+i1+" d = "+d);
		String[] cals = new String[i1 - i0 + 1];
		for (int i = 0; i < cals.length; i++) {
			String label = fmt.format((i + i0) * d);
			if (d > 100) {
				if (((long) d) % 1000000000 == 0) {
					label = ((long) ((i + i0) * d)) / 1000000000 + "B";
				} else if (((long) d) % 1000000 == 0) {
					label = ((long) ((i + i0) * d)) / 1000000 + "M";
				} else if (((long) d) % 1000 == 0) {
					label = ((long) ((i + i0) * d)) / 1000 + "K";
				}
			}
			cals[i] = label;
		}
		return cals;
	}

	@Override
	public String toString() {
		return "Axis[vr=" + vr + ",dr=" + dr + "]";
	}
}
