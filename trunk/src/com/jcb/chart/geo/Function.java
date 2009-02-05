package com.jcb.chart.geo;

import java.util.Date;

public interface Function<E, T> {
	public T computer(E x);

	public E solve(T y);

	public Function<Double, Double> NONE = new Function<Double, Double>() {
		public Double computer(Double x) {
			return x;
		}

		public Double solve(Double y) {
			return y;
		}
	};

	public Function<Date, Double> SIMPLE_DATE = new Function<Date, Double>() {
		public Double computer(Date x) {
			return (double) x.getTime();
		}

		public Date solve(Double y) {
			return new Date(Math.round(y));
		}
	};

}
