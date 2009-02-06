package com.jcb.chart.geo;

import java.util.Date;

public interface Function<E, T> {
	public T computer(E x);

	public E solve(T y);

	public Function<Number, Number> NONE = new Function<Number, Number>() {
		public Number computer(Number x) {
			return x;
		}

		public Number solve(Number y) {
			return y;
		}
	};

	public Function<Number, Number> LOG = new Function<Number, Number>() {
		public Number computer(Number x) {
			return Math.log(x.doubleValue());
		}

		public Number solve(Number y) {
			return Math.exp(y.doubleValue());
		}
	};

	public Function<Date, Number> SIMPLE_DATE = new Function<Date, Number>() {
		public Number computer(Date x) {
			return x.getTime();
		}

		public Date solve(Number y) {
			return new Date(Math.round(y.doubleValue()));
		}
	};

}
