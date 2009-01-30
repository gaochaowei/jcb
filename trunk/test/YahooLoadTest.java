import java.util.List;

import org.apache.log4j.Logger;

import com.jcb.persistence.io.YahooQuote;
import com.jcb.persistence.po.Equity;

public class YahooLoadTest {
	public static void main(String[] args) {
		Logger log = Logger.getLogger(YahooLoadTest.class);
		log.info("hello world");
		List<Equity> list = YahooQuote.retrieveEquityList("^STI");
		log.info(list);
	}
}
