package com.jcb.chart.geo;

import java.awt.Color;
import java.awt.Graphics;

import com.jcb.chart.geo.Axis.Orientation;
import com.jcb.util.CommonUtils;

public class Coordinate<X, Y> {

	private Axis<X> xAxis;
	private Axis<Y> yAxis;

	private boolean xGridVisible = false;
	private boolean yGridVisible = false;

	public boolean isXGridVisible() {
		return xGridVisible;
	}

	public void setXGridVisible(boolean gridVisible) {
		xGridVisible = gridVisible;
	}

	public boolean isYGridVisible() {
		return yGridVisible;
	}

	public void setYGridVisible(boolean gridVisible) {
		yGridVisible = gridVisible;
	}

	public Coordinate(Function<X, Number> xc, Function<Y, Number> yc) {
		xAxis = new Axis<X>(xc);
		yAxis = new Axis<Y>(yc);
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
			x = xAxis.getScreen(xAxis.getValue(x));
			g.drawLine(x, yAxis.getScreenHigh(), x, yAxis.getScreenLow());
			g.setColor(temp);
		}
	}

	public void showYLine(Graphics g, int y) {
		if (yAxis.containScreen(y)) {
			Color temp = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xAxis.getScreenHigh(), y, xAxis.getScreenLow(), y);
			g.setColor(temp);
		}
	}

	public void paintAxis(Graphics g, boolean showXGrid, boolean showYGrid) {
		xAxis.paint(g, yAxis, showXGrid);
		yAxis.paint(g, xAxis, showYGrid);
	}

	public String toString() {
		return CommonUtils.getBeanValue(this);
	}

	public void setConverter(Function<X, Number> xc, Function<Y, Number> yc) {
		xAxis.setConverter(xc);
		yAxis.setConverter(yc);
	}

	public void setScreenRange(int xLow, int xHigh, int yLow, int yHigh) {
		xAxis.setScreenLow(xLow);
		xAxis.setScreenHigh(xHigh);
		yAxis.setScreenLow(yLow);
		yAxis.setScreenHigh(yHigh);
	}

	public void setValueRange(X xLow, X xHigh, Y yLow, Y yHigh) {
		xAxis.setValueLow(xLow);
		xAxis.setValueHigh(xHigh);
		yAxis.setValueLow(yLow);
		yAxis.setValueHigh(yHigh);
	}

	public Function<X, Number> getXConverter() {
		return xAxis.getConverter();
	}

	public Function<Y, Number> getYConverter() {
		return yAxis.getConverter();
	}

	public int getXScreenLow() {
		return xAxis.getScreenLow();
	}

	public int getXScreenHigh() {
		return xAxis.getScreenHigh();
	}

	public int getYScreenLow() {
		return yAxis.getScreenLow();
	}

	public int getYScreenHigh() {
		return yAxis.getScreenHigh();
	}

	public int getXScreen(X value) {
		return xAxis.getScreen(value);
	}

	public int getYScreen(Y value) {
		return yAxis.getScreen(value);
	}

	public X getXValue(int value) {
		return xAxis.getValue(value);
	}

	public Y getYValue(int value) {
		return yAxis.getValue(value);
	}

	public boolean containYScreen(int screen) {
		return yAxis.containScreen(screen);
	}
}
