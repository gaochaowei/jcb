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

public class IndexComponentPK implements Serializable {

	public static final long serialVersionUID = 1;

	private String index;
	private String stock;

	public IndexComponentPK() {
	}

	public IndexComponentPK(String index, String stock) {
		this.index = index;
		this.stock = stock;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (index != null ? index.hashCode() : 0);
		hash += (stock != null ? stock.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof IndexComponentPK)) {
			return false;
		}
		IndexComponentPK other = (IndexComponentPK) object;
		if ((this.index == null && other.index != null)
				|| (this.index != null && !this.index.equals(other.index))) {
			return false;
		}
		if ((this.stock == null && other.stock != null)
				|| (this.stock != null && !this.stock.equals(other.stock))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "testurl.TblIndexComponentPK[index=" + index + ", stock="
				+ stock + "]";
	}

}
