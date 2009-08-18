package com.jcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jcb.bean.EquityPriceBean;
import com.jcb.util.CommonUtils;
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
				px.getEquity().setName(symbol);
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

	public static boolean create(EquityPriceBean bean) {
		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtils.getConnection();
			String sql = "insert into PRICE (EQUITY,TRADE_DT,PRICE_OPEN,PRICE_LOW,PRICE_HIGH,PRICE_CLOSE,PRICE_ADJ,TRADE_VOLUMN,CREATE_DT,UPDATE_DT) values (?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getEquity().getId());
			ps.setDate(2, CommonUtils.sqlDate(bean.getDate()));
			ps.setDouble(3, bean.getPriceOpen());
			ps.setDouble(4, bean.getPriceLow());
			ps.setDouble(5, bean.getPriceHigh());
			ps.setDouble(6, bean.getPriceClose());
			ps.setDouble(7, bean.getPriceCloseAdj());
			ps.setLong(8, bean.getVolumn());
			ps.executeUpdate();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con, ps);
		}
		return status;
	}
	
	public static boolean create(List<EquityPriceBean> beanList) {
		for(EquityPriceBean bean:beanList){
			create(bean);
		}
		return true;
	}
}
