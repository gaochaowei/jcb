package com.jcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.jcb.bean.HDBResaleBean;
import com.jcb.util.CommonUtils;
import com.jcb.util.DBUtils;

public class HDBResaleDAO {
	public static int save(List<HDBResaleBean> resales) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn
					.prepareStatement("insert into APP.HDB_RESALES (town,street,story,hdb_type,floor_area,lease_commence_dt,resale_price,resale_approval_dt,blk,create_dt) values (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)");
			for (HDBResaleBean resale : resales) {
				ps.setString(1, resale.getTown());
				ps.setString(2, resale.getStreet());
				ps.setString(3, resale.getStory());
				ps.setString(4, resale.getType());
				ps.setFloat(5, resale.getFloorArea());
				ps.setDate(6, CommonUtils
						.sqlDate(resale.getLeaseCommenceDate()));
				ps.setInt(7, resale.getResalePrice());
				ps.setDate(8, CommonUtils.sqlDate(resale
						.getResaleApprovalDate()));
				ps.setString(9, resale.getBlk());
				int status = ps.executeUpdate();
				result += status;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			DBUtils.close(conn, ps);
		}
	}

}
