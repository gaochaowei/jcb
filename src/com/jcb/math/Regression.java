package com.jcb.math;

import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;

public class Regression {

	public static SimpleRegression regression(List<EquityPriceBean> priceList) {
		SimpleRegression reg = new SimpleRegression();
		for (EquityPriceBean price : priceList) {
			reg.addData(price.getDate().getTime(), price.getPriceLow());
		}
		return reg;
	}

}
