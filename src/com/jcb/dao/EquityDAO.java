package com.jcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.jcb.bean.EquityBean;
import com.jcb.bean.EquityPriceBean;
import com.jcb.util.CommonUtils;
import com.jcb.util.DBUtils;

public class EquityDAO {

	public static List<EquityBean> selectEquity() {
		return null;
	}

	public static EquityBean selectEquity(int id) {
		return null;
	}

	public static void save(EquityBean equity) {

	}

	public static boolean create(EquityBean bean) {
		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtils.getConnection();
			String sql = "insert into EQUITY (ID,SYMBOL,NAME,CREATE_DT,UPDATE_DT) values (?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getSymbol());
			ps.setString(3, bean.getName());
			ps.executeUpdate();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con, ps);
		}
		return status;
	}
	
	public static boolean create(List<EquityBean> beanList) {
		for(EquityBean bean:beanList){
			create(bean);
		}
		return true;
	}
	
	public static int delete(EquityBean equity) {
		return 0;
	}

}
