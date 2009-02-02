package com.jcb.bean;

import java.util.Date;

public class EquityPriceBean extends BaseBean {

	private static final long serialVersionUID = -1532458335496961189L;
	private String symbol;
	private Date date;
	private double priceClose;
	private double priceOpen;
	private double priceHigh;
	private double priceLow;
	private double priceCloseAdj;
	private long volumn;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPriceClose() {
		return priceClose;
	}

	public void setPriceClose(double priceClose) {
		this.priceClose = priceClose;
	}

	public double getPriceOpen() {
		return priceOpen;
	}

	public void setPriceOpen(double priceOpen) {
		this.priceOpen = priceOpen;
	}

	public double getPriceHigh() {
		return priceHigh;
	}

	public void setPriceHigh(double priceHigh) {
		this.priceHigh = priceHigh;
	}

	public double getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(double priceLow) {
		this.priceLow = priceLow;
	}

	public double getPriceCloseAdj() {
		return priceCloseAdj;
	}

	public void setPriceCloseAdj(double priceCloseAdj) {
		this.priceCloseAdj = priceCloseAdj;
	}

	public long getVolumn() {
		return volumn;
	}

	public void setVolumn(long volumn) {
		this.volumn = volumn;
	}

}
