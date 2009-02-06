package com.jcb.chart.plot;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;
import com.jcb.chart.geo.Coordinate;
import com.jcb.math.expression.Expression;

public class PriceChartPlot {

	private Coordinate<Date, Number> priceCoord;
	private Coordinate<Date, Number> volumnCoord;
	private List<EquityPriceBean> priceList;

	public Coordinate<Date, Number> getPriceCoord() {
		return priceCoord;
	}

	public void setPriceCoord(Coordinate<Date, Number> priceCoord) {
		this.priceCoord = priceCoord;
	}

	public Coordinate<Date, Number> getVolumnCoord() {
		return volumnCoord;
	}

	public void setVolumnCoord(Coordinate<Date, Number> volumnCoord) {
		this.volumnCoord = volumnCoord;
	}

	public List<EquityPriceBean> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<EquityPriceBean> priceList) {
		this.priceList = priceList;
	}

	public void paintAxis(Graphics g) {
		priceCoord.paintAxis(g);
		volumnCoord.paintAxis(g);
	}

	public void plotCandles(Graphics g) {
		g.setColor(Color.GRAY);
		for (EquityPriceBean price : priceList) {
			int x0 = priceCoord.getXScreen(price.getDate());
			int yo = priceCoord.getYScreen(price.getPriceOpen());
			int yc = priceCoord.getYScreen(price.getPriceClose());
			int yl = priceCoord.getYScreen(price.getPriceLow());
			int yh = priceCoord.getYScreen(price.getPriceHigh());
			if (price.getPriceClose() > price.getPriceOpen()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			g.fillRect(x0 - 2, Math.min(yo, yc), 5, Math.abs(yo - yc) + 2);
			g.drawLine(x0, yl, x0, yh);
		}
	}

	public void plotVolumn(Graphics g) {
		g.setColor(Color.BLACK);
		for (EquityPriceBean price : priceList) {
			int x0 = priceCoord.getXScreen(price.getDate());
			int yvl = volumnCoord.getYScreen(0d);
			int yvh = volumnCoord.getYScreen(price.getVolumn() + 0d);
			g.fillRect(x0 - 2, yvh, 5, yvl - yvh);
		}
	}

	public void plotTrend(Graphics g, SimpleRegression regression) {
		Expression exp = new Expression.Var("x").times(regression.getSlope())
				.add(regression.getIntercept());
		g.setColor(Color.black);
		int screenLow = priceCoord.getXScreenLow();
		int screenHigh = priceCoord.getXScreenHigh();
		for (int x = screenLow + 1; x <= screenHigh; x++) {
			double time0 = priceCoord.getXValue(x - 1).getTime();
			double time1 = priceCoord.getXValue(x).getTime();
			double yv0 = priceCoord.getYConverter().solve(exp.compute(time0))
					.doubleValue();
			double yv1 = priceCoord.getYConverter().solve(exp.compute(time1))
					.doubleValue();
			int y0 = priceCoord.getYScreen(yv0);
			int y1 = priceCoord.getYScreen(yv1);
			if (priceCoord.containYScreen(y0) && priceCoord.containYScreen(y1)) {
				g.drawLine(x - 1, y0, x, y1);
			}
		}
	}
}
