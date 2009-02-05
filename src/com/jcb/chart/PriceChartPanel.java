package com.jcb.chart;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.bean.EquityPriceBean;
import com.jcb.chart.geo.TimeCoordinate2D;
import com.jcb.chart.geo.ValueAxis.Scale;
import com.jcb.chart.plot.PriceChartPlot;
import com.jcb.io.EquityReader;
import com.jcb.io.EquityReader.Frequency;
import com.jcb.math.Regression;
import com.jcb.util.CommonUtils;

public class PriceChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private TimeCoordinate2D priceCoord = new TimeCoordinate2D();
	private TimeCoordinate2D volumnCoord = new TimeCoordinate2D(); // @jve:decl-index=0:
	private Map<Date, EquityPriceBean> priceMap; // @jve:decl-index=0:
	private List<EquityPriceBean> priceList; // @jve:decl-index=0:
	private SimpleRegression regression;
	private PriceChartPlot plot;

	/**
	 * This is the default constructor
	 */
	public PriceChartPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 300);
		this.setLayout(new BorderLayout());
		volumnCoord.setTimeAxis(priceCoord.getTimeAxis());
		priceCoord.getYAxis().setScale(Scale.LOG);
		plot = new PriceChartPlot();
		plot.setPriceCoord(priceCoord);
		plot.setVolumnCoord(volumnCoord);
		fitScreen();
		List<EquityPriceBean> priceList = EquityReader.fetchEquityPrice(
				"BS6.SI", DateUtils.addDays(new Date(), -300), new Date(),
				Frequency.WEEK);
		setPriceList(priceList);
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				fitScreen();
				repaint();
			}
		});
	}

	public void setPriceList(List<EquityPriceBean> priceList) {
		this.priceList = priceList;
		priceMap = new HashMap<Date, EquityPriceBean>();
		double priceLow = Double.MAX_VALUE;
		double priceHigh = Double.MIN_VALUE;
		double volumnHigh = 0;
		Date dateLow = new Date(Long.MAX_VALUE);
		Date dateHigh = new Date(Long.MIN_VALUE);
		for (EquityPriceBean price : priceList) {
			priceMap.put(price.getDate(), price);
			dateLow = CommonUtils.min(dateLow, price.getDate());
			dateHigh = CommonUtils.max(dateLow, price.getDate());
			priceLow = Math.min(priceLow, price.getPriceLow());
			priceHigh = Math.max(priceHigh, price.getPriceHigh());
			volumnHigh = Math.max(volumnHigh, price.getVolumn());

		}
		priceCoord.getTimeAxis().setValueLow(dateLow);
		priceCoord.getTimeAxis().setValueHigh(dateHigh);
		priceCoord.getYAxis().setValueLow(priceLow);
		priceCoord.getYAxis().setValueHigh(priceHigh);
		volumnCoord.getYAxis().setValueLow(0d);
		volumnCoord.getYAxis().setValueHigh(volumnHigh);
		plot.setPriceList(priceList);
		regression = Regression.regression(priceList, priceCoord.getYAxis()
				.getScale());
	}

	public List<EquityPriceBean> getPriceList() {
		return priceList;
	}

	private void fitScreen() {
		int width = this.getWidth();
		int height = this.getHeight();
		priceCoord.getTimeAxis().setScreenLow(100);
		priceCoord.getTimeAxis().setScreenHigh(width - 50);
		priceCoord.getYAxis().setScreenLow(50);
		int priceScreenHigh = height - 200;
		priceCoord.getYAxis().setScreenHigh(priceScreenHigh);
		volumnCoord.getYAxis().setScreenLow(priceScreenHigh + 20);
		volumnCoord.getYAxis().setScreenHigh(height - 50);
	}

	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		plot.paintAxis(g);
		plot.plotCandles(g);
		plot.plotVolumn(g);
		plot.plotTrend(g, regression);
	}

}
