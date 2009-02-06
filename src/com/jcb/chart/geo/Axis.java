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

	private static final int TICKER_SIZE = 5;

	private Orientation orientation = Orientation.HORIZONTAL;
	private int screenLow = 0;
	private int screenHigh = 100;
	private E valueLow;
	private E valueHigh;
	private Function<E, Number> converter;

	private double pixelInternalValue;
	private double zeroInternalValueScreen;
	private String[] numberLabels;
	private Map<Date, String> dateLabels;
	private DecimalFormat numberFormat;

	public Axis(Function<E, Number> converter) {
		this.converter = converter;
	}

	public void paint(Graphics g, Axis<?> axis, boolean showGrid) {
		g.setColor(Color.GRAY);
		FontMetrics metrics = g.getFontMetrics();
		int screen;
		if (orientation == Orientation.HORIZONTAL) {
			screen = axis.getScreenHigh();
			g.drawLine(screenLow, screen, screenHigh, screen);
		} else {
			screen = axis.getScreenLow();
			g.drawLine(screen, screenLow, screen, screenHigh);
		}

		int gridSize = showGrid ? axis.getScreenSize() : TICKER_SIZE;

		if (valueLow instanceof Number) {
			for (String s : numberLabels) {
				E value = (E) CommonUtils.parse(s, numberFormat);
				if (orientation == Orientation.HORIZONTAL) {
					g.drawString(s, getScreen(value) - metrics.stringWidth(s)
							/ 2, screen + metrics.getAscent());

					g.drawLine(getScreen(value), screen, getScreen(value),
							screen - gridSize);
				} else {
					g.drawString(s, screen - metrics.stringWidth(s) - 2,
							getScreen(value) + metrics.getAscent() / 2);
					g.drawLine(screen, getScreen(value), screen + gridSize,
							getScreen(value));
				}
			}

		} else if (valueLow instanceof Date) {
			for (Date date : dateLabels.keySet()) {
				E value = (E) date;
				g.drawString(dateLabels.get(date), getScreen(value)
						- metrics.stringWidth(dateLabels.get(value)) / 2,
						screen + metrics.getAscent());
				if (showGrid) {
					g.drawLine(getScreen(value), screen, getScreen(value),
							screen - axis.getScreenSize());
				} else {
					g.drawLine(getScreen(value), screen, getScreen(value),
							screen - TICKER_SIZE);
				}
			}
		}
	}

	/***
	 * Triggered by screenLow, screenHigh, valueLow, valueHigh, converter
	 */
	private void preCompute() {
		if (isReady()) {
			double internalValueSize = converter.computer(valueHigh)
					.doubleValue()
					- converter.computer(valueLow).doubleValue();
			pixelInternalValue = internalValueSize / getScreenSize();
			if (orientation == Orientation.HORIZONTAL) {
				zeroInternalValueScreen = screenLow
						- converter.computer(valueLow).doubleValue()
						/ pixelInternalValue;
			} else {
				zeroInternalValueScreen = screenHigh
						+ converter.computer(valueLow).doubleValue()
						/ pixelInternalValue;
			}
			if (valueLow instanceof Number) {
				double doubleLow = ((Number) valueLow).doubleValue();
				double doubleHigh = ((Number) valueHigh).doubleValue();
				numberLabels = AxisUtils.getLabels((Number) valueLow,
						(Number) valueHigh);
				numberFormat = AxisUtils.getFormat(AxisUtils.getUnit(doubleHigh
						- doubleLow));
			} else if (valueLow instanceof Date) {
				dateLabels = AxisUtils.getLabels((Date) valueLow,
						(Date) valueHigh);
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
		double internalValue = converter.computer(value).doubleValue();
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

	public Function<E, Number> getConverter() {
		return converter;
	}

	public void setConverter(Function<E, Number> converter) {
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
