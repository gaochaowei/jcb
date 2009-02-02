package com.jcb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jcb.bean.EquityPriceBean;
import com.jcb.util.DBUtils;

public class EquityPriceDAO {
	public static List<EquityPriceBean> selectEquityPrice(String symbol) {
		List<EquityPriceBean> pxs = new ArrayList<EquityPriceBean>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select * from TBL_PRICE where STOCK='"
							+ symbol
							+ "' and price_date>DATE('2007-08-26') order by PRICE_DATE");
			while (rs.next()) {
				EquityPriceBean px = new EquityPriceBean();
				px.setSymbol(symbol);
				px.setDate(rs.getDate("PRICE_DATE"));
				px.setPriceClose(rs.getDouble("PRICE_CLOSE"));
				px.setPriceOpen(rs.getDouble("PRICE_OPEN"));
				px.setPriceHigh(rs.getDouble("PRICE_HIGH"));
				px.setPriceLow(rs.getDouble("PRICE_LOW"));
				px.setVolumn(rs.getLong("VOLUMN"));
				pxs.add(px);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, stmt, rs);
		}
		System.out.println(pxs.size() + " rows loaded");
		return pxs;
	}
}
