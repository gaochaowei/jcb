package com.jcb.math;

public class Expression {

	private Expression e;
	protected Expression e1;
	protected Expression e2;

	public Expression() {
	}

	public Expression(String exp) {
		e = Parser.parse(exp);
	}

	public double value() {
		return e.value();
	}

	public double compute(double var) {
		return e.compute(var);
	}

	public static class Var extends Expression {

		private String name;
		Expression value;

		public Var(String name) {
			this.name = name;
		}

		public void setValue(Expression value) {
			this.value = value;
		}

		@Override
		public double value() {
			return Double.NaN;
		}

		@Override
		public double compute(double var) {
			return var;
		}
	}

	public static class Value extends Expression {

		private double value;

		public Value(double value) {
			this.value = value;
		}

		@Override
		public double value() {
			return value;
		}

		@Override
		public double compute(double var) {
			return value;
		}
	}

	public static class Add extends Expression {

		public Add(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
		}

		@Override
		public double value() {
			return e1.value() + e2.value();
		}

		@Override
		public double compute(double var) {
			return e1.compute(var) + e2.compute(var);
		}
	}

	public static class Minus extends Expression {

		public Minus(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
		}

		@Override
		public double value() {
			return e1.value() - e2.value();
		}

		@Override
		public double compute(double var) {
			return e1.compute(var) - e2.compute(var);
		}
	}

	public static class Times extends Expression {

		public Times(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
		}

		@Override
		public double value() {
			return e1.value() * e2.value();
		}

		@Override
		public double compute(double var) {
			return e1.compute(var) * e2.compute(var);
		}
	}

	public static class Divide extends Expression {

		public Divide(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
		}

		@Override
		public double value() {
			return e1.value() / e2.value();
		}

		@Override
		public double compute(double var) {
			return e1.compute(var) / e2.compute(var);
		}
	}

	public static class Power extends Expression {

		public Power(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
		}

		@Override
		public double value() {
			return Math.pow(e1.value(), e2.value());
		}

		@Override
		public double compute(double var) {
			return Math.pow(e1.compute(var), e2.compute(var));
		}
	}

	public static class Sqrt extends Expression {

		public Sqrt(Expression e1) {
			this.e1 = e1;
		}

		@Override
		public double value() {
			return Math.sqrt(e1.value());
		}

		@Override
		public double compute(double var) {
			return Math.sqrt(e1.compute(var));
		}
	}

	public static class Sin extends Expression {

		public Sin(Expression e1) {
			this.e1 = e1;
		}

		@Override
		public double value() {
			return Math.sin(e1.value());
		}

		@Override
		public double compute(double var) {
			return Math.sin(e1.compute(var));
		}
	}

	public static class Cos extends Expression {

		public Cos(Expression e1) {
			this.e1 = e1;
		}

		@Override
		public double value() {
			return Math.cos(e1.value());
		}

		@Override
		public double compute(double var) {
			return Math.cos(e1.compute(var));
		}
	}

}
