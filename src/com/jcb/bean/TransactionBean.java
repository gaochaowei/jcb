package com.jcb.bean;

import java.util.Date;

public class TransactionBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3364372018660066827L;

	public enum Type {
		Buy, Sell
	};

	private Type type;
	private Date date;
	private String symbol;
	private int quantity;
	private double price;
	private double commission;

	public TransactionBean() {
	}

	public TransactionBean(Type type, Date date, String symbol, int quantity,
			double price, double commission) {
		setType(type);
		setDate(date);
		setSymbol(symbol);
		setQuantity(quantity);
		setPrice(price);
		setCommission(commission);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

}
