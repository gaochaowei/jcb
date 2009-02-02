package com.jcb.bean;

public class EquityBean extends BaseBean {

	private static final long serialVersionUID = 2697585565284168350L;

	private int id;
	private String symbol;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return id + ", " + symbol + ", " + name;
	}
}
