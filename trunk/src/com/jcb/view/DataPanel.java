package com.jcb.view;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jcb.bean.EquityBean;
import com.jcb.io.EquityReader;

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
		List<EquityBean> data = EquityReader.fetchEquityList("^STI");
		jTable = new JTable(new BeanTableModel(data, EquityBean.class));
		// jTable.setFillsViewportHeight(true);
		this.setViewportView(jTable);
	}

}
