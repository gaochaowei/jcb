package com.jcb.chart;

import java.awt.Graphics;

public abstract class Axis<E> {
	public enum Orientation {
		HORIZONTAL, VERTICAL
	};

	protected int tickerSize = 5;

	protected Orientation orientation = Orientation.HORIZONTAL;

	protected int screenLow = 0;
	protected int screenHigh = 100;

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
	}

	public int getScreenHigh() {
		return screenHigh;
	}

	public void setScreenHigh(int screenHigh) {
		this.screenHigh = screenHigh;
	}

	public abstract int getScreen(E value);

	public abstract E getValue(int screen);

	public boolean containScreen(int screen) {
		return screen >= screenLow && screen <= screenHigh;
	}

	public int getScreenSize() {
		return screenHigh - screenLow;
	}

	public abstract void paint(Graphics g, int screen);

	public abstract boolean containValue(E value);

}
