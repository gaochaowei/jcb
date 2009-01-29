package com.jcb.tools.database.sql;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jcb.tools.database.db.DBColumn;
import com.jcb.tools.database.db.DBTable;

public abstract class SQL {
	protected DBTable table;

	public SQL() {

	}

	public SQL(DBTable table) {
		this.table = table;
	}

	public DBTable getTable() {
		return table;
	}

	public void setTable(DBTable table) {
		this.table = table;
	}

	public abstract String getDDL();

	public String grpColumns(List<DBColumn> columns) {
		StringBuffer sb = new StringBuffer("(");
		for (DBColumn c : columns) {
			sb.append(c.getColumnName()).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(")");
		return new String(sb);
	}

	public static String getSQLString(DBColumn column, Object value) {
		if (value == null)
			return "NULL";
		String s = StringUtils.replace(value.toString(), "'", "''");
		if (column.getTypeName().equals("CHAR")
				|| column.getTypeName().equals("VARCHAR")) {
			return "'" + s + "'";
		}
		if (column.getTypeName().equals("DATE")
				|| column.getTypeName().equals("TIME")
				|| column.getTypeName().equals("TIMESTAMP")) {
			return "'" + s + "'";
		}
		return s.toString();
	}
}
