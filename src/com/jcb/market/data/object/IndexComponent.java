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
public class IndexComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	protected IndexComponentPK tblIndexComponentPK;

	public IndexComponent() {
	}

	public IndexComponent(IndexComponentPK tblIndexComponentPK) {
		this.tblIndexComponentPK = tblIndexComponentPK;
	}

	public IndexComponent(String index, String stock) {
		this.tblIndexComponentPK = new IndexComponentPK(index, stock);
	}

	public IndexComponentPK getTblIndexComponentPK() {
		return tblIndexComponentPK;
	}

	public void setTblIndexComponentPK(IndexComponentPK tblIndexComponentPK) {
		this.tblIndexComponentPK = tblIndexComponentPK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tblIndexComponentPK != null ? tblIndexComponentPK.hashCode()
				: 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof IndexComponent)) {
			return false;
		}
		IndexComponent other = (IndexComponent) object;
		if ((this.tblIndexComponentPK == null && other.tblIndexComponentPK != null)
				|| (this.tblIndexComponentPK != null && !this.tblIndexComponentPK
						.equals(other.tblIndexComponentPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "testurl.TblIndexComponent[tblIndexComponentPK="
				+ tblIndexComponentPK + "]";
	}

}
