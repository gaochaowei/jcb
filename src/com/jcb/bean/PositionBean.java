package com.jcb.bean;


public class PositionBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2818965813214212437L;

	private String symbol;
	private int quantity;

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
	
	public PositionBean clone(){
		PositionBean p = new PositionBean();
		p.setQuantity(quantity);
		p.setSymbol(symbol);
		return p;
	}

}
