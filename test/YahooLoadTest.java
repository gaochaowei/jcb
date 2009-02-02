import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.jcb.bean.EquityBean;
import com.jcb.io.YahooQuote;
import com.jcb.util.SystemConfig;

public class YahooLoadTest {
	public static void main(String[] args)throws Exception {
		Logger log = Logger.getLogger(YahooLoadTest.class);
		System.out.println(SystemConfig.dbDriver);
		//Map m = BeanUtils.describe(new Equity());
		//System.out.println(m);
		//log.info("hello world");
		//List<Equity> list = YahooQuote.retrieveEquityList("^STI");
		//log.info(list);
	}
}
