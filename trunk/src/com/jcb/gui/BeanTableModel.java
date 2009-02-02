package com.jcb.gui;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<String> columnNames = new ArrayList<String>();
	private Map<String, String> p = new HashMap<String, String>();
	private List<?> data;

	public BeanTableModel(List<?> data, Class<?> dataType) {
		this.data = data;
		PropertyDescriptor[] pds = PropertyUtils
				.getPropertyDescriptors(dataType);
		for (PropertyDescriptor pd : pds) {
			String pName = pd.getName();
			if (!pName.equals("class")) {
				columnNames.add(pd.getName());
				p.put(pd.getName(), pd.getName());
			}
		}
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames.get(col);
	}

	public Object getValueAt(int row, int col) {
		try {
			return PropertyUtils.getProperty(data.get(row), columnNames
					.get(col));
		} catch (Exception e) {
			return null;
		}
	}

	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		try {
			PropertyUtils.setProperty(data.get(row), columnNames.get(col),
					value);
		} catch (Exception e) {
		}
		fireTableCellUpdated(row, col);
	}

}
