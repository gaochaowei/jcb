import java.util.List;

import com.jcb.bean.EquityBean;
import com.jcb.dao.EquityDao;
import com.jcb.util.Msg;

public class HibernateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("load equity ...");
		EquityBean equity = EquityDao.getEquity(1);
		System.out.println(equity);

		System.out.println("save equity ...");
		equity = new EquityBean();
		equity.setId(1);
		equity.setSymbol("C6L.SI");
		equity.setName("Singapore Airlines Limited");
		EquityDao.saveOrUpdate(equity);

		System.out.println("load equity ...");
		equity = EquityDao.getEquity(1);
		System.out.println(equity);

		System.out.println("update equity ...");
		equity.setName("SIA");
		EquityDao.saveOrUpdate(equity);

		System.out.println("load equity ...");
		equity = EquityDao.getEquity(1);
		System.out.println(equity);

		System.out.println("delete equity ...");
		EquityDao.deleteEquity(equity);

		System.out.println("load equity ...");
		equity = EquityDao.getEquity(1);
		System.out.println(equity);

		List<EquityBean> all = EquityDao.getAll();
		System.out.println(all);
		System.out.println("${test}=" + Msg.get("test"));

	}

}
