/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jcb.market.data.object;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author gaocw
 */

public class PricePK implements Serializable {

	public static final long serialVersionUID = 1;

	private String symbol;
	private Date date;

	public PricePK() {
	}

	public PricePK(String symbol, Date date) {
		this.symbol = symbol;
		this.date = date;
	}

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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (symbol != null ? symbol.hashCode() : 0);
		hash += (date != null ? date.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof PricePK)) {
			return false;
		}
		PricePK other = (PricePK) object;
		if ((this.symbol == null && other.symbol != null)
				|| (this.symbol != null && !this.symbol.equals(other.symbol))) {
			return false;
		}
		if ((this.date == null && other.date != null)
				|| (this.date != null && !this.date.equals(other.date))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "testurl.TblPricePK[symbol=" + symbol + ", date=" + date + "]";
	}

}
