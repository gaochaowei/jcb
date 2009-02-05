package com.jcb.chart.geo;

import java.awt.Color;
import java.awt.Graphics;

import com.jcb.chart.geo.Axis.Orientation;
import com.jcb.util.CommonUtils;

public class RealCoordinate2D {

	protected RealAxis xAxis = new RealAxis();
	protected RealAxis yAxis = new RealAxis();

	public RealAxis getXAxis() {
		return xAxis;
	}

	public void setXAxis(RealAxis axis) {
		xAxis = axis;
	}

	public RealAxis getYAxis() {
		return yAxis;
	}

	public void setYAxis(RealAxis axis) {
		yAxis = axis;
	}

	public RealCoordinate2D() {
		yAxis.setOrientation(Orientation.VERTICAL);
	}

	public void showBack(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.black);
		g.fillRect(xAxis.getScreenLow(), yAxis.getScreenLow(), xAxis
				.getScreenSize(), yAxis.getScreenSize());
		g.setColor(temp);
	}

	public void showXLine(Graphics g, int x) {
		if (xAxis.containScreen(x)) {
			Color temp = g.getColor();
			g.setColor(Color.white);
			g.drawLine(x, yAxis.getScreenHigh(), x, yAxis.getScreenLow());
			g.setColor(temp);
		}
	}

	public void showYLine(Graphics g, int y) {
		if (yAxis.containScreen(y)) {
			Color temp = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xAxis.getScreenHigh(), y, xAxis.getScreenHigh(), y);
			g.setColor(temp);
		}
	}

	public void paintXAxis(Graphics g) {
		xAxis.paint(g, yAxis.getScreenHigh());
	}

	public void paintYAxis(Graphics g) {
		yAxis.paint(g, xAxis.getScreenLow());
	}

	public boolean containPosition(int x, int y) {
		return xAxis.containScreen(x) && yAxis.containScreen(y);
	}

	public boolean containValue(double x, double y) {
		return xAxis.containValue(x) && yAxis.containValue(y);
	}

	@Override
	public String toString() {
		return CommonUtils.getBeanValue(this);
	}
}
