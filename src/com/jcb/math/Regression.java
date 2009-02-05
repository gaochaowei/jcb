package com.jcb.math;

import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;
import com.jcb.chart.geo.Function;

public class Regression {

	public static SimpleRegression regression(List<EquityPriceBean> priceList,
			Function<Double, Double> convertor) {
		SimpleRegression reg = new SimpleRegression();
		for (EquityPriceBean price : priceList) {
			reg.addData(price.getDate().getTime(), convertor.computer(price
					.getPriceLow()));
		}
		return reg;
	}
}
