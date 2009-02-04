package com.jcb.visual;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;

import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class RealAxis extends ValueAxis<Double> {

	protected double convertDouble(Double value) {
		return value;
	}

	protected Double convertObject(double value) {
		return value;
	}

	public boolean containValue(Double value) {
		return value >= valueLow && value <= valueHigh;
	}

	public void paint(Graphics g, int screen) {
		g.setColor(Color.GRAY);
		String[] labels = AxisUtils.getCalibration(convertDouble(valueLow),
				convertDouble(valueHigh));
		DecimalFormat fmt = AxisUtils.getFormat(AxisUtils
				.getUnit(getValueSize()));
		switch (orientation) {
		case HORIZONTAL:
			g.drawLine(screenLow, screen, screenHigh, screen);
			for (String s : labels) {
				double v = CommonUtils.parse(s, fmt).doubleValue();
				FontMetrics metrics = g.getFontMetrics();
				g.drawString(s, getScreen(v) - metrics.stringWidth(s) / 2,
						screen + metrics.getAscent());
				g.drawLine(getScreen(v), screen, getScreen(v), screen
						- tickerSize);
			}
			break;
		default:
			g.drawLine(screen, screenLow, screen, screenHigh);
			for (String s : labels) {
				double v = CommonUtils.parse(s, fmt).doubleValue();
				FontMetrics metrics = g.getFontMetrics();
				int w0 = metrics.stringWidth(s);
				g.drawString(s, screen - w0 - 2, getScreen(v)
						+ metrics.getAscent() / 2);
				g.drawLine(screen, getScreen(v), screen + tickerSize,
						getScreen(v));
			}
			break;
		}
	}
}
