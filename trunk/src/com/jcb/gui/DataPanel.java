package com.jcb.gui;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jcb.persistence.io.YahooQuote;
import com.jcb.persistence.po.Equity;

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
		List<Equity> data = YahooQuote.retrieveEquityList("^STI");
		jTable = new JTable(new BeanTableModel(data,Equity.class));
		//jTable.setFillsViewportHeight(true);
		this.setViewportView(jTable);
	}

}
