import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jcb.bean.EquityPriceBean;
import com.jcb.bean.PortfolioTrackerBean;
import com.jcb.bean.PositionBean;
import com.jcb.bean.PriceInfoBean;
import com.jcb.io.EquityReader;
import com.jcb.io.EquityReader.Frequency;
import com.jcb.util.CommonUtils;

public class Test {
	public static void main(String[] args) {

		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyPort", "9666");
		System.getProperties().put("proxyHost", "127.0.0.1");

		PortfolioTrackerBean traker = createPortfolioTraker();
		PriceInfoBean priceInfo = createPriceInfo();
		Date date = CommonUtils.getDate("18/03/2009");
		double prev = 0;
		while (date.before(new Date())) {
			date = DateUtils.addDays(date, 1);
			Map<String, PositionBean> positions = traker.getPortfolio(date)
					.getPositionMap();
			double total = 0;
			for (String symbol : positions.keySet()) {				
				int quantity = positions.get(symbol).getQuantity();
				double price = priceInfo.getPrice(symbol, date);
				total += quantity * price;
//				System.out.println(symbol+"\t"+quantity+"\t"+price);
			}
			if (total >= 0) {
				System.out.println(date + " \t $" + total+" \t $"+(total-prev));
				prev = total;
			}
			
		}

		// List<HDBResaleBean> resales = HDBResaleReader.fetchResales(2);
		// HDBResaleDAO.save(resales);
		// System.out.println(resales.size());
		// System.exit(0);
		// DecimalFormat fmt = AxisUtils.getFormat(0.23);
		// System.out.println(fmt.format(1203.5));
		// System.out.println(AxisUtils.getUnit(1203.5));
		// String[] cals = AxisUtils.getLabels(100, 2788);
		// CommonUtils.printArray(cals);
		// List<EquityPriceBean> pxs = EquityReader.fetchEquityPrice("C6L.SI",
		// DateUtils.addDays(new Date(), -20), new Date(), Frequency.DATE);
		// System.out.println(pxs);
	}

	public static PriceInfoBean createPriceInfo() {
		PriceInfoBean priceInfo = new PriceInfoBean();
		Date date = CommonUtils.getDate("18/03/2009");
		priceInfo.addPrice(EquityReader.fetchEquityPrice("D05.SI", date,
				new Date(), Frequency.DATE));
		priceInfo.addPrice(EquityReader.fetchEquityPrice("S68.SI", date,
				new Date(), Frequency.DATE));
		priceInfo.addPrice(EquityReader.fetchEquityPrice("F83.SI", date,
				new Date(), Frequency.DATE));
		priceInfo.addPrice(EquityReader.fetchEquityPrice("N03.SI", date,
				new Date(), Frequency.DATE));
		priceInfo.addPrice(EquityReader.fetchEquityPrice("C38U.SI", date,
				new Date(), Frequency.DATE));
		return priceInfo;
	}

	public static PortfolioTrackerBean createPortfolioTraker() {
		PortfolioTrackerBean traker = new PortfolioTrackerBean();
		traker.addBuyTransaction("18/03/2009", "D05.SI", 1000, 7.36, 30.50);
		traker.addBuyTransaction("18/03/2009", "S68.SI", 1000, 4.76, 29.17);
		traker.addBuyTransaction("18/03/2009", "F83.SI", 12000, 7.355, 31.35);
		traker.addBuyTransaction("26/03/2009", "N03.SI", 7000, 1.20, 30.02);
		traker.addBuyTransaction("04/05/2009", "F83.SI", 12000, 1.10, 49.08);
		traker.addBuyTransaction("28/05/2009", "C38U.SI", 7000, 1.32, 31.89);
		traker.addBuyTransaction("17/06/2009", "N03.SI", 6000, 1.30, 2.00);
		traker.addBuyTransaction("16/07/2009", "F83.SI", 10000, 1.23, 41.14);
		traker.addSellTransaction("17/07/2009", "N03.SI", 7000, 1.57, 36.75);
		return traker;
	}

}
