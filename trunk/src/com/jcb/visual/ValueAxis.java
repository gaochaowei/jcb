package com.jcb.visual;

import com.jcb.util.CommonUtils;

public abstract class ValueAxis<E> extends Axis<E> {

	public enum Scale {
		LINEAR, LOG, UNKNOWN
	};

	protected E valueLow;
	protected E valueHigh;

	protected Scale scale = Scale.LINEAR;

	public E getValueLow() {
		return valueLow;
	}

	public void setValueLow(E valueLow) {
		this.valueLow = valueLow;
	}

	public E getValueHigh() {
		return valueHigh;
	}

	public void setValueHigh(E valueHigh) {
		this.valueHigh = valueHigh;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}

	public Scale getScale() {
		return scale;
	}

	public double getValueSize() {
		return trasform(convertDouble(valueHigh))
				- trasform(convertDouble(valueLow));
	}

	public double trasform(double value) {
		switch (scale) {
		case LINEAR:
			return value;
		case LOG:
			return Math.log(value);
		default:
			return value;
		}
	}

	public double inverseTrasform(double value) {
		switch (scale) {
		case LINEAR:
			return value;
		case LOG:
			return Math.exp(value);
		default:
			return value;
		}
	}

	public double getPixelSize() {
		return getValueSize() / getScreenSize();
	}

	private double getZeroValueScreen() {
		switch (orientation) {
		case HORIZONTAL:
			return screenLow - trasform(convertDouble(valueLow))
					/ getPixelSize();
		default:
			return screenHigh + trasform(convertDouble(valueLow))
					/ getPixelSize();
		}
	}

	private double getInternalValue(int screen) {
		double value;
		switch (orientation) {
		case HORIZONTAL:
			value = (screen - getZeroValueScreen()) * getPixelSize();
			break;
		default:
			value = (getZeroValueScreen() - screen) * getPixelSize();
			break;
		}
		return inverseTrasform(value);
	}

	public E getValue(int screen) {
		return convertObject(getInternalValue(screen));
	}

	private int getScreenUsingInternalValue(double value) {
		double screen;
		switch (orientation) {
		case HORIZONTAL:
			screen = getZeroValueScreen() + trasform(value) / getPixelSize();
			break;
		default:
			screen = getZeroValueScreen() - trasform(value) / getPixelSize();
			break;
		}
		return (int) Math.round(screen);
	}

	public int getScreen(E value) {
		return getScreenUsingInternalValue(trasform(convertDouble(value)));
	}

	@Override
	public String toString() {
		return CommonUtils.getBeanValue(this);
	}

	protected abstract double convertDouble(E obj);

	protected abstract E convertObject(double value);

}