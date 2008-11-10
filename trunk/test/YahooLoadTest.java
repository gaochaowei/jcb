

import java.util.List;

import com.jcb.persistence.dao.EquityDao;
import com.jcb.persistence.io.YahooQuote;
import com.jcb.persistence.po.Equity;

public class YahooLoadTest {
	public static void main(String[] args) {
		EquityDao equiytDao = null;
		List<Equity> list = YahooQuote.retrieveEquityList("^STI");
		System.out.println(list.size());
		for (Equity eq : list) {
			equiytDao.saveOrUpdate(eq);
		}
	}
}
