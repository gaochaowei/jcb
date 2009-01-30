/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jcb.market.data.object;

import java.io.Serializable;

/**
 * 
 * @author gaocw
 */
public class Index implements Serializable {
	private static final long serialVersionUID = 1L;
	private String symbol;

	public Index() {
	}

	public Index(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (symbol != null ? symbol.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Index)) {
			return false;
		}
		Index other = (Index) object;
		if ((this.symbol == null && other.symbol != null)
				|| (this.symbol != null && !this.symbol.equals(other.symbol))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "testurl.TblIndex[symbol=" + symbol + "]";
	}

}