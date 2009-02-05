package com.jcb.chart.geo;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import com.jcb.math.graphics.AxisUtils;
import com.jcb.util.CommonUtils;

public class Axis<E> {

	public enum Orientation {
		HORIZONTAL, VERTICAL
	};

	protected static final int TICKER_SIZE = 5;

	protected Orientation orientation = Orientation.HORIZONTAL;
	protected int screenLow = 0;
	protected int screenHigh = 100;
	protected E valueLow;
	protected E valueHigh;
	protected Function<E, Double> converter;

	protected double pixelInternalValue;
	protected double zeroInternalValueScreen;

	public Axis(Function<E, Double> converter) {
		this.converter = converter;
	}

	public void paint(Graphics g, int screen) {
		g.setColor(Color.GRAY);
		FontMetrics metrics = g.getFontMetrics();
		if (valueLow instanceof Double) {
			double doubleLow = (Double) valueLow;
			double doubleHigh = (Double) valueHigh;
			String[] labels = AxisUtils.getLabels(doubleLow, doubleHigh);
			DecimalFormat fmt = AxisUtils.getFormat(AxisUtils
					.getUnit(doubleHigh - doubleLow));
			if (orientation == Orientation.HORIZONTAL) {
				g.drawLine(screenLow, screen, screenHigh, screen);
				for (String s : labels) {
					E value = (E) (Number) CommonUtils.parse(s, fmt)
							.doubleValue();
					g.drawString(s, getScreen(value) - metrics.stringWidth(s)
							/ 2, screen + metrics.getAscent());
					g.drawLine(getScreen(value), screen, getScreen(value),
							screen - TICKER_SIZE);
				}
			} else {
				g.drawLine(screen, screenLow, screen, screenHigh);
				for (String s : labels) {
					E value = (E) (Number) CommonUtils.parse(s, fmt)
							.doubleValue();
					int w0 = metrics.stringWidth(s);
					g.drawString(s, screen - w0 - 2, getScreen(value)
							+ metrics.getAscent() / 2);
					g.drawLine(screen, getScreen(value), screen + TICKER_SIZE,
							getScreen(value));
				}
			}
		} else if (valueLow instanceof Date) {
			g.drawLine(screenLow, screen, screenHigh, screen);
			Date dateLow = (Date) valueLow;
			Date dateHigh = (Date) valueHigh;
			Map<Date, String> labels = AxisUtils.getLabels(dateLow, dateHigh);
			for (Date d : labels.keySet()) {
				E value = (E) d;
				g.drawString(labels.get(d), getScreen(value)
						- metrics.stringWidth(labels.get(value)) / 2, screen
						+ metrics.getAscent());
				g.drawLine(getScreen(value), screen, getScreen(value),
						screen - 5);
			}
		}
	}

	/***
	 * Triggered by screenLow, screenHigh, valueLow, valueHigh, converter
	 */
	private void preCompute() {
		if (isReady()) {
			double internalValueSize = converter.computer(valueHigh)
					- converter.computer(valueLow);
			pixelInternalValue = internalValueSize / getScreenSize();
			if (orientation == Orientation.HORIZONTAL) {
				zeroInternalValueScreen = screenLow
						- converter.computer(valueLow) / pixelInternalValue;
			} else {
				zeroInternalValueScreen = screenHigh
						+ converter.computer(valueLow) / pixelInternalValue;
			}
		}
	}

	public boolean isReady() {
		return converter != null && valueLow != null && valueHigh != null;
	}

	public E getValue(double screen) {
		double internalValue;
		switch (orientation) {
		case HORIZONTAL:
			internalValue = (screen - zeroInternalValueScreen)
					* pixelInternalValue;
			break;
		default:
			internalValue = (zeroInternalValueScreen - screen)
					* pixelInternalValue;
			break;
		}
		return converter.solve(internalValue);
	}

	public int getScreen(E value) {
		double internalValue = converter.computer(value);
		switch (orientation) {
		case HORIZONTAL:
			return (int) Math.round(zeroInternalValueScreen + internalValue
					/ pixelInternalValue);
		default:
			return (int) Math.round(zeroInternalValueScreen - internalValue
					/ pixelInternalValue);
		}
	}

	@Override
	public String toString() {
		return CommonUtils.getBeanValue(this);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public int getScreenLow() {
		return screenLow;
	}

	public void setScreenLow(int screenLow) {
		this.screenLow = screenLow;
		preCompute();
	}

	public int getScreenHigh() {
		return screenHigh;
	}

	public void setScreenHigh(int screenHigh) {
		this.screenHigh = screenHigh;
		preCompute();
	}

	public E getValueLow() {
		return valueLow;
	}

	public void setValueLow(E valueLow) {
		this.valueLow = valueLow;
		preCompute();
	}

	public E getValueHigh() {
		return valueHigh;
	}

	public void setValueHigh(E valueHigh) {
		this.valueHigh = valueHigh;
		preCompute();
	}

	public Function<E, Double> getConverter() {
		return converter;
	}

	public void setConverter(Function<E, Double> converter) {
		this.converter = converter;
		preCompute();
	}

	public int getScreenSize() {
		return screenHigh - screenLow;
	}

	public boolean containScreen(int screen) {
		return screen >= screenLow && screen <= screenHigh;
	}

}
