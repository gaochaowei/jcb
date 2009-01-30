package com.jcb.gui;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DataPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private JTable jTable = null;

	/**
	 * This is the default constructor
	 */
	public DataPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);

		this.setViewportView(getJTable());
		getJTable().setFillsViewportHeight(true);
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

}
