package com.jcb.visual;

public class Range {

	public static class Double {

		public double l = 0d;
		public double h = 0d;

		public double length() {
			return h - l;
		}

		public boolean contains(double v) {
			return l <= v && v <= h;
		}

		public void move(double d) {
			l += d;
			h += d;
		}

		public void wide(double x) {
			l += (h - l) * (x - 1) / 2;
			h -= (h - l) * (x - 1) / 2;
		}

		public void setRange(double l, double h) {
			if (l != h) {
				this.l = Math.min(l, h);
				this.h = Math.max(l, h);
			}
		}

		@Override
		public String toString() {
			return "Range [" + l + "," + h + "]";
		}
	}

	public static class Int {

		public int l = 0;
		public int h = 0;

		public Int() {
		}

		public int length() {
			return h - l;
		}

		public boolean contains(int v) {
			return l <= v && v <= h;
		}

		public void move(int d) {
			l += d;
			h += d;
		}

		public void setRange(int l, int h) {
			if (l != h) {
				this.l = Math.min(l, h);
				this.h = Math.max(l, h);
			}
		}

		@Override
		public String toString() {
			return "Range[" + l + "," + h + "]";
		}
	}
}
