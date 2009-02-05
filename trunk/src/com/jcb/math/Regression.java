package com.jcb.math;

import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;
import com.jcb.chart.geo.ValueAxis.Scale;

public class Regression {

	public static SimpleRegression regression(List<EquityPriceBean> priceList,
			Scale scale) {
		SimpleRegression reg = new SimpleRegression();
		for (EquityPriceBean price : priceList) {
			if (scale == Scale.LINEAR)
				reg.addData(price.getDate().getTime(), price.getPriceLow());
			else
				reg.addData(price.getDate().getTime(), Math.log(price
						.getPriceLow()));
		}
		return reg;
	}
}
