package com.jcb.tools.database.sql;

import java.util.Map;

import com.jcb.tools.database.db.DBColumn;
import com.jcb.tools.database.db.DBTable;

public class SQLDelete extends SQL {
	Map<String, Object> row;

	public SQLDelete(DBTable table, Map<String, Object> row) {
		super(table);
		this.row = row;
	}

	public String getDDL() {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ").append(table.getQualifiedName());
		sb.append(" WHERE ");
		for (DBColumn c : table.getPrimaryKey().getColumns()) {
			String cName = c.getColumnName();
			sb.append(cName).append("=");
			sb.append(getSQLString(c, row.get(cName))).append(" AND ");
		}
		sb.delete(sb.lastIndexOf(" AND "), sb.length());
		sb.append(";\n");
		return new String(sb);
	}

}
