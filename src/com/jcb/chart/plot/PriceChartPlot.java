package com.jcb.chart.plot;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;
import com.jcb.chart.geo.TimeCoordinate2D;
import com.jcb.chart.geo.ValueAxis.Scale;
import com.jcb.math.expression.Expression;

public class PriceChartPlot {

	private TimeCoordinate2D priceCoord;
	private TimeCoordinate2D volumnCoord;
	private List<EquityPriceBean> priceList;

	public TimeCoordinate2D getPriceCoord() {
		return priceCoord;
	}

	public void setPriceCoord(TimeCoordinate2D priceCoord) {
		this.priceCoord = priceCoord;
	}

	public TimeCoordinate2D getVolumnCoord() {
		return volumnCoord;
	}

	public void setVolumnCoord(TimeCoordinate2D volumnCoord) {
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
			int x0 = priceCoord.getTimeAxis().getScreen(price.getDate());
			int yo = priceCoord.getYAxis().getScreen(price.getPriceOpen());
			int yc = priceCoord.getYAxis().getScreen(price.getPriceClose());
			int yl = priceCoord.getYAxis().getScreen(price.getPriceLow());
			int yh = priceCoord.getYAxis().getScreen(price.getPriceHigh());
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
			int x0 = priceCoord.getTimeAxis().getScreen(price.getDate());
			int yvl = volumnCoord.getYAxis().getScreen(0d);
			int yvh = volumnCoord.getYAxis().getScreen(price.getVolumn() + 0d);
			g.fillRect(x0 - 2, yvh, 5, yvl - yvh);
		}
	}

	public void plotTrend(Graphics g, SimpleRegression regression) {
		Expression exp = new Expression.Var("x").times(regression.getSlope())
				.add(regression.getIntercept());
		g.setColor(Color.black);
		int screenLow = priceCoord.getTimeAxis().getScreenLow();
		int screenHigh = priceCoord.getTimeAxis().getScreenHigh();
		for (int x = screenLow + 1; x <= screenHigh; x++) {
			double time0 = priceCoord.getTimeAxis().getValue(x - 1).getTime();
			double time1 = priceCoord.getTimeAxis().getValue(x).getTime();
			double yv0 = exp.compute(time0);
			double yv1 = exp.compute(time1);
			if (priceCoord.getYAxis().getScale() == Scale.LOG) {
				yv0 = Math.exp(yv0);
				yv1 = Math.exp(yv1);
			}
			int y0 = priceCoord.getYAxis().getScreen(yv0);
			int y1 = priceCoord.getYAxis().getScreen(yv1);
			if (priceCoord.getYAxis().containScreen(y0)
					&& priceCoord.getYAxis().containScreen(y1)) {
				g.drawLine(x - 1, y0, x, y1);
			}
		}
	}
}
