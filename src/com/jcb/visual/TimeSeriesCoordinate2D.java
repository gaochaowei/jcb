package com.jcb.visual;

import java.awt.Color;
import java.awt.Graphics;

import com.jcb.util.CommonUtils;
import com.jcb.visual.Axis.Orientation;

public class TimeSeriesCoordinate2D {

	private TimeSeriesAxis timeAxis = new TimeSeriesAxis();
	private RealAxis yAxis = new RealAxis();

	public TimeSeriesCoordinate2D() {
		yAxis.setOrientation(Orientation.VERTICAL);
	}

	public TimeSeriesAxis getTimeAxis() {
		return timeAxis;
	}

	public void setTimeAxis(TimeSeriesAxis timeAxis) {
		this.timeAxis = timeAxis;
	}

	public RealAxis getYAxis() {
		return yAxis;
	}

	public void setYAxis(RealAxis axis) {
		yAxis = axis;
	}

	public void showBack(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.black);
		g.fillRect(timeAxis.getScreenLow(), yAxis.getScreenLow(), timeAxis
				.getScreenSize(), yAxis.getScreenSize());
		g.setColor(temp);
	}

	public void showXLine(Graphics g, int x) {
		if (timeAxis.containScreen(x)) {
			Color temp = g.getColor();
			g.setColor(Color.white);
			x = timeAxis.getScreen(timeAxis.getValue(x));
			g.drawLine(x, yAxis.getScreenHigh(), x, yAxis.getScreenLow());
			g.setColor(temp);
		}
	}

	public void showYLine(Graphics g, int y) {
		if (yAxis.containScreen(y)) {
			Color temp = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(timeAxis.getScreenHigh(), y, timeAxis.getScreenLow(), y);
			g.setColor(temp);
		}
	}

	public void paintXAxis(Graphics g) {
		timeAxis.paint(g, yAxis.getScreenHigh());
	}

	public void paintYAxis(Graphics g) {
		yAxis.paint(g, timeAxis.getScreenLow());
	}

	public void paintAxis(Graphics g) {
		paintXAxis(g);
		paintYAxis(g);
	}

	public String toString() {
		return CommonUtils.getBeanValue(this);
	}
}
