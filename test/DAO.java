import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DAO {
	public static List<HdbTypeBean> getHdbTypes()throws Exception{
		List<HdbTypeBean> beans = new ArrayList<HdbTypeBean>();
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:file:data/jacob", "ADMIN", "20082009");
		PreparedStatement ps = con.prepareStatement("select * from APP.HDB_TYPE order by REF");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			HdbTypeBean bean = new HdbTypeBean();
			bean.setRef(rs.getString("REF"));
			bean.setName(rs.getString("NAME"));
			beans.add(bean);
		}
		rs.close();
		ps.close();
		con.close();
		return beans;
	}
	
	public static List<TownBean> getHdbTowns()throws Exception{
		List<TownBean> beans = new ArrayList<TownBean>();
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:file:data/jacob", "ADMIN", "20082009");
		PreparedStatement ps = con.prepareStatement("select * from APP.HDB_TOWN order by ID");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			TownBean bean = new TownBean();
			bean.setId(rs.getInt("ID"));
			bean.setName(rs.getString("NAME"));
			beans.add(bean);
		}
		rs.close();
		ps.close();
		con.close();
		return beans;
	}

}
