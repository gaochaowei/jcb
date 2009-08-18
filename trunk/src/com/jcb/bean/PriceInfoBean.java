package com.jcb.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceInfoBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6895017210598105761L;
	private Map<String, Map<Date, EquityPriceBean>> priceInfo = new HashMap<String, Map<Date, EquityPriceBean>>();

	public void addPrice(List<EquityPriceBean> priceList) {
		Map<Date, EquityPriceBean> map = new HashMap<Date, EquityPriceBean>();
		for (EquityPriceBean px : priceList) {
			map.put(px.getDate(), px);
		}
		String symbol = priceList.get(0).getEquity().getSymbol();
		priceInfo.put(symbol, map);
	}

	public double getPrice(String symbol, Date date) {
		EquityPriceBean p = priceInfo.get(symbol).get(date);
		if (p == null)
			return -1;
		return priceInfo.get(symbol).get(date).getPriceClose();
	}

}
