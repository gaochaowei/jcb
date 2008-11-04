package test;

import java.util.List;

import com.jcb.persistence.bean.Equity;
import com.jcb.persistence.dao.EquityDao;
import com.jcb.util.Msg;
import com.jcb.util.SpringUtils;

public class SpringHibernateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EquityDao equiytDao = (EquityDao) SpringUtils.getBean("equityDao");

		System.out.println("load equity ...");
		Equity equity = equiytDao.getEquity(1);
		System.out.println(equity);

		System.out.println("save equity ...");
		equity = new Equity();
		equity.setId(1);
		equity.setSymbol("C6L.SI");
		equity.setName("Singapore Airlines Limited");
		equiytDao.saveOrUpdate(equity);

		System.out.println("load equity ...");
		equity = equiytDao.getEquity(1);
		System.out.println(equity);

		System.out.println("update equity ...");
		equity.setName("SIA");
		equiytDao.saveOrUpdate(equity);

		System.out.println("load equity ...");
		equity = equiytDao.getEquity(1);
		System.out.println(equity);

		System.out.println("delete equity ...");
		equiytDao.deleteEquity(equity);

		System.out.println("load equity ...");
		equity = equiytDao.getEquity(1);
		System.out.println(equity);

		List<Equity> all =  equiytDao.getAll();
		System.out.println(all);
		System.out.println("${test}="+Msg.get("test"));
		
	}

}
