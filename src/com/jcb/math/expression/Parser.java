package com.jcb.math.expression;

import java.util.Stack;

import com.jcb.util.CommonUtils;

/**
 * 
 * @author gaocw
 */
public class Parser {

	private static String[][] OPS = new String[][] { { "+", "-" },
			{ "*", "/" }, { "^", "sqrt", "sin", "cos" } };

	public static Expression parse(String s) {
		s = s.trim();
		if (s.startsWith("(") && s.endsWith(")")) {
			return parse(s.substring(1, s.length() - 1));
		}

		OpToken opt = getPrimaryOpToken(s);
		if (opt == null) {
			if (CommonUtils.isDouble(s)) {
				return new Expression.Value(Double.parseDouble(s));
			} else {
				return new Expression.Var(s);
			}
		}
		if (opt.getOp().equals("+")) {
			String s1 = s.substring(0, opt.getIndex());
			String s2 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Add(parse(s1), parse(s2));
		}
		if (opt.getOp().equals("-")) {
			String s1 = s.substring(0, opt.getIndex());
			String s2 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Minus(parse(s1), parse(s2));
		}
		if (opt.getOp().equals("*")) {
			String s1 = s.substring(0, opt.getIndex());
			String s2 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Times(parse(s1), parse(s2));
		}
		if (opt.getOp().equals("/")) {
			String s1 = s.substring(0, opt.getIndex());
			String s2 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Divide(parse(s1), parse(s2));
		}
		if (opt.getOp().equals("^")) {
			String s1 = s.substring(0, opt.getIndex());
			String s2 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Power(parse(s1), parse(s2));
		}
		if (opt.getOp().equals("sqrt")) {
			String s1 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Sqrt(parse(s1));
		}
		if (opt.getOp().equals("sin")) {
			String s1 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Sin(parse(s1));
		}
		if (opt.getOp().equals("cos")) {
			String s1 = s.substring(opt.getIndex() + opt.getOp().length());
			return new Expression.Cos(parse(s1));
		}
		return null;
	}

	private static OpToken getPrimaryOpToken(String s) {
		if (s.startsWith("-") || s.startsWith("+")) {
			s = "x" + s.substring(1);
		}
		Stack<String> pt = new Stack<String>();
		for (String[] ops : OPS) {
			for (int i = 0; i < s.length(); i++) {
				String tmp = s.substring(i);
				for (String op : ops) {
					if (tmp.startsWith("(")) {
						pt.push("(");
					} else if (tmp.startsWith(")")) {
						pt.pop();
					} else if (pt.isEmpty()) {
						if (tmp.startsWith(op)) {
							return new OpToken(op, i);
						}
					}
				}
			}
		}
		return null;
	}

	private static class OpToken {

		private String op;
		private int index = 0;

		public OpToken(String op, int index) {
			this.op = op;
			this.index = index;
		}

		public String getOp() {
			return op;
		}

		public int getIndex() {
			return index;
		}
	}
}
