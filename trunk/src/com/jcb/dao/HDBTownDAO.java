package com.jcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jcb.bean.HDBTownBean;
import com.jcb.util.DBUtils;

public class HDBTownDAO {
	public static List<HDBTownBean> selectHDBTowns() throws Exception {
		List<HDBTownBean> beans = new ArrayList<HDBTownBean>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select * from APP.HDB_TOWN order by ID");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			HDBTownBean bean = new HDBTownBean();
			bean.setId(rs.getInt("ID"));
			bean.setName(rs.getString("NAME"));
			beans.add(bean);
		}
		DBUtils.close(conn, ps, rs);
		return beans;
	}
}
