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
public class Price implements Serializable {

	private static final long serialVersionUID = 1L;
	protected PricePK tblPricePK;
	private Double priceClose;
	private Double priceOpen;
	private Double priceHigh;
	private Double priceLow;
	private Double priceCloseAdj;
	private Long volumn;

	public Price() {
	}

	public Price(PricePK tblPricePK) {
		this.tblPricePK = tblPricePK;
	}

	public Price(String symbol, Date date) {
		this.tblPricePK = new PricePK(symbol, date);
	}

	public PricePK getTblPricePK() {
		return tblPricePK;
	}

	public void setTblPricePK(PricePK tblPricePK) {
		this.tblPricePK = tblPricePK;
	}

	public Double getPriceClose() {
		return priceClose;
	}

	public void setPriceClose(Double priceClose) {
		this.priceClose = priceClose;
	}

	public Double getPriceOpen() {
		return priceOpen;
	}

	public void setPriceOpen(Double priceOpen) {
		this.priceOpen = priceOpen;
	}

	public Double getPriceHigh() {
		return priceHigh;
	}

	public void setPriceHigh(Double priceHigh) {
		this.priceHigh = priceHigh;
	}

	public Double getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(Double priceLow) {
		this.priceLow = priceLow;
	}

	public Double getPriceCloseAdj() {
		return priceCloseAdj;
	}

	public void setPriceCloseAdj(Double priceCloseAdj) {
		this.priceCloseAdj = priceCloseAdj;
	}

	public Long getVolumn() {
		return volumn;
	}

	public void setVolumn(Long volumn) {
		this.volumn = volumn;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tblPricePK != null ? tblPricePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Price)) {
			return false;
		}
		Price other = (Price) object;
		if ((this.tblPricePK == null && other.tblPricePK != null)
				|| (this.tblPricePK != null && !this.tblPricePK
						.equals(other.tblPricePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "testurl.TblPrice[tblPricePK=" + tblPricePK + "]";
	}
}
