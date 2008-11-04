package test;

import java.util.List;

import com.jcb.persistence.bean.Equity;
import com.jcb.persistence.dao.EquityDao;
import com.jcb.persistence.io.YahooQuote;
import com.jcb.util.SpringUtils;

public class YahooLoadTest {
	public static void main(String[] args) {
		EquityDao equiytDao = (EquityDao) SpringUtils.getBean("equityDao");
		List<Equity> list = YahooQuote.retrieveEquityList("^STI");
		System.out.println(list.size());
		for (Equity eq : list) {
			equiytDao.saveOrUpdate(eq);
		}
	}
}
