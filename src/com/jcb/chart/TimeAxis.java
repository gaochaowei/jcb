package com.jcb.chart;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Date;
import java.util.Map;

import com.jcb.math.graphics.AxisUtils;

public class TimeAxis extends ValueAxis<Date> {

	protected double convertDouble(Date date) {
		return date.getTime();
	}

	protected Date convertObject(double value) {
		return new Date((long) value);
	}

	public boolean containValue(Date value) {
		return !(value.before(valueLow) && value.after(valueHigh));
	}

	public void paint(Graphics g, int screen) {
		g.setColor(Color.GRAY);
		g.drawLine(screenLow, screen, screenHigh, screen);
		Map<Date, String> labels = AxisUtils
				.getCalibration(valueLow, valueHigh);
		for (Date d : labels.keySet()) {
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(labels.get(d), getScreen(d)
					- metrics.stringWidth(labels.get(d)) / 2, screen
					+ metrics.getAscent());
			g.drawLine(getScreen(d), screen, getScreen(d), screen - 5);
		}
	}

}
