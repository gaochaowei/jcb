package com.jcb.gui;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jcb.bean.EquityBean;
import com.jcb.io.YahooQuote;

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
		List<EquityBean> data = YahooQuote.retrieveEquityList("^STI");
		jTable = new JTable(new BeanTableModel(data,EquityBean.class));
		//jTable.setFillsViewportHeight(true);
		this.setViewportView(jTable);
	}

}
