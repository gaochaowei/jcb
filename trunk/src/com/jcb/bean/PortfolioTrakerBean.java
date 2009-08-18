package com.jcb.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcb.bean.TransactionBean.Type;
import com.jcb.util.CommonUtils;

public class PortfolioTrakerBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373683728826725764L;

	private List<TransactionBean> transactions = new ArrayList<TransactionBean>();
	private Map<Date, PortfolioBean> portfolioMap = new HashMap<Date, PortfolioBean>();

	public void addTransaction(TransactionBean tx) {
		transactions.add(tx);
		PortfolioBean p = getPortfolio(tx.getDate());
		if (p == null) {
			p = new PortfolioBean();
		}
		if(!portfolioMap.keySet().contains(tx.getDate())){
			p = p.clone();
		}
		PositionBean ps = new PositionBean();
		ps.setSymbol(tx.getSymbol());
		switch (tx.getType()) {
		case Buy:
			ps.setQuantity(tx.getQuantity());
			break;
		case Sell:
			ps.setQuantity(-tx.getQuantity());
			break;
		default:
			ps.setQuantity(tx.getQuantity());
			break;
		}
		p.addPosition(ps);
		portfolioMap.put(tx.getDate(), p);
	}

	public PortfolioBean getPortfolio(Date date) {
		Date d = CommonUtils.getLastDate(portfolioMap.keySet(), date);
		return portfolioMap.get(d);
	}

	public void addBuyTransaction(String date, String symbol, int quantity,
			double price, double commission) {
		addTransaction(Type.Buy, CommonUtils.getDate(date), symbol, quantity,
				price, commission);
	}

	public void addSellTransaction(String date, String symbol, int quantity,
			double price, double commission) {
		addTransaction(Type.Sell, CommonUtils.getDate(date), symbol, quantity,
				price, commission);
	}

	public void addBuyTransaction(Date date, String symbol, int quantity,
			double price, double commission) {
		addTransaction(Type.Buy, date, symbol, quantity, price, commission);
	}

	public void addSellTransaction(Date date, String symbol, int quantity,
			double price, double commission) {
		addTransaction(Type.Sell, date, symbol, quantity, price, commission);
	}

	public void addTransaction(Type type, Date date, String symbol,
			int quantity, double price, double commission) {
		TransactionBean tx = new TransactionBean(type, date, symbol, quantity,
				price, commission);
		addTransaction(tx);
	}

}
