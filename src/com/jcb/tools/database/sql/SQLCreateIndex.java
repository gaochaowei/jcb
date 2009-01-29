package com.jcb.tools.database.sql;

import com.jcb.tools.database.db.DBIndex;

public class SQLCreateIndex extends SQL {

	private DBIndex idx;

	public SQLCreateIndex(DBIndex idx) {
		this.idx = idx;
	}

	public String getDDL() {
		StringBuffer sb = new StringBuffer();
		if (idx.isNonUnique())
			sb.append("CREATE INDEX ");
		else
			sb.append("CREATE UNIQUE INDEX ");
		sb.append(idx.getQualifiedIndexName()).append(" ON ");
		sb.append(idx.getQualifiedTableName());
		sb.append(grpColumns(idx.getColumns())).append(";\n");
		return new String(sb);
	}
}
