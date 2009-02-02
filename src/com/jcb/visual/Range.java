package com.jcb.visual;

import org.apache.commons.lang.time.DateUtils;

import com.jcb.util.CommonUtils;

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

	public static class Date {

		public java.util.Date l = new java.util.Date();
		public java.util.Date h = new java.util.Date();

		public int length() {
			return CommonUtils.diffInDays(l, h);
		}

		public boolean contains(java.util.Date date) {
			return !(date.before(l) || date.after(h));
		}

		public void move(int d) {
			l = DateUtils.addDays(l, d);
			h = DateUtils.addDays(h, d);
		}

		public void wide(double x) {
			int change = (int) Math.ceil(length() * (x - 1) / 2);
			l = DateUtils.addDays(l, -change);
			h = DateUtils.addDays(h, change);
		}

		public void setRange(java.util.Date l, java.util.Date h) {
			if (l != h) {
				if (l.before(h)) {
					this.l = l;
					this.h = h;
				} else {
					this.l = h;
					this.h = l;
				}
			}
		}

		@Override
		public String toString() {
			return "Range [" + l + "," + h + "]";
		}
	}
}
