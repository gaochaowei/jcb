package com.jcb.bean;

import java.util.HashMap;
import java.util.Map;

public class PortfolioBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8029663890570495050L;

	private Map<String, PositionBean> positionMap = new HashMap<String, PositionBean>();

	public PositionBean getPosition(String symbol) {
		return positionMap.get(symbol);
	}

	public Map<String, PositionBean> getPositionMap(){
		return positionMap;
	}
	public void addPosition(PositionBean position) {
		PositionBean p = getPosition(position.getSymbol());
		if (p == null) {
			positionMap.put(position.getSymbol(), position);
		} else {
			int quantity = p.getQuantity() + position.getQuantity();
			if (quantity == 0) {
				positionMap.remove(position.getSymbol());
			} else {
				p.setQuantity(quantity);
			}
		}
	}

	public boolean isEmpty() {
		return positionMap.size() > 0;
	}
	
	public PortfolioBean clone(){
		PortfolioBean p = new PortfolioBean();
		for(String symbol:positionMap.keySet()){
			p.addPosition(getPosition(symbol).clone());
		}
		return p;
	}
}
