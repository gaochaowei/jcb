/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcb.market.data.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.jcb.market.data.object.Price;
import com.jcb.market.data.object.PricePK;

/**
 * 
 * @author gaocw
 */
public class DBAccess {

	public static Vector<Price> loadPrice(String stock) {
		Vector<Price> v = new Vector<Price>();
		try {
			Connection con = DBManager.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from TBL_PRICE where STOCK='"
							+ stock
							+ "' and price_date>DATE('2007-08-26') order by PRICE_DATE");
			while (rs.next()) {
				Price px = new Price();
				px.setTblPricePK(new PricePK(stock, rs.getDate("PRICE_DATE")));
				px.setPriceClose(rs.getDouble("PRICE_CLOSE"));
				px.setPriceOpen(rs.getDouble("PRICE_OPEN"));
				px.setPriceHigh(rs.getDouble("PRICE_HIGH"));
				px.setPriceLow(rs.getDouble("PRICE_LOW"));
				px.setVolumn(rs.getLong("VOLUMN"));
				v.add(px);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(v.size() + " rows loaded");
		return v;
	}
}
