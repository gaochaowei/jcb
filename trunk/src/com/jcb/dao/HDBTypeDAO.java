package com.jcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jcb.bean.HDBTypeBean;
import com.jcb.util.DBUtils;

public class HDBTypeDAO {
	public static List<HDBTypeBean> getHDBTypes() throws Exception {
		List<HDBTypeBean> beans = new ArrayList<HDBTypeBean>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select * from APP.HDB_TYPE order by REF");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			HDBTypeBean bean = new HDBTypeBean();
			bean.setRef(rs.getString("REF"));
			bean.setName(rs.getString("NAME"));
			beans.add(bean);
		}
		DBUtils.close(conn, ps, rs);
		return beans;
	}
}
