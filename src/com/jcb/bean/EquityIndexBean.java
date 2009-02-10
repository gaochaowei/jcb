package com.jcb.bean;

import java.util.ArrayList;
import java.util.List;

public class EquityIndexBean extends EquityBean {

	private static final long serialVersionUID = 8884436307464785223L;

	public enum IndexWeight {
		PriceWeighted, MarketCapsWeighted, FloatAdjustedWeighted, ModifiedMarketCapsWeighted
	};

	private List<EquityBean> components = new ArrayList<EquityBean>();

	public List<EquityBean> getComponents() {
		return components;
	}

	public void setComponents(List<EquityBean> components) {
		this.components = components;
	}

}
