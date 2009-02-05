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
import com.jcb.chart.geo.Coordinate;
import com.jcb.chart.geo.Function;
import com.jcb.chart.plot.PriceChartPlot;
import com.jcb.io.EquityReader;
import com.jcb.io.EquityReader.Frequency;
import com.jcb.math.Regression;
import com.jcb.util.CommonUtils;

public class PriceChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Coordinate<Date, Double> pcd;
	private Coordinate<Date, Double> vcd;
	private Map<Date, EquityPriceBean> priceMap;
	private List<EquityPriceBean> priceList;
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
		pcd = new Coordinate<Date, Double>(Function.SIMPLE_DATE, Function.NONE);
		vcd = new Coordinate<Date, Double>(Function.SIMPLE_DATE, Function.NONE);
		plot = new PriceChartPlot();
		plot.setPriceCoord(pcd);
		plot.setVolumnCoord(vcd);
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
		pcd.setValueRange(dateLow, dateHigh, priceLow, priceHigh);
		vcd.setValueRange(dateLow, dateHigh, 0d, volumnHigh);
		plot.setPriceList(priceList);
		regression = Regression.regression(priceList, pcd.getYConverter());
	}

	public List<EquityPriceBean> getPriceList() {
		return priceList;
	}

	private void fitScreen() {
		int width = this.getWidth();
		int height = this.getHeight();
		pcd.setScreenRange(100, width - 50, 50, height - 200);
		vcd.setScreenRange(100, width - 50, height - 180, height - 50);
	}

	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		plot.paintAxis(g);
		plot.plotCandles(g);
		plot.plotVolumn(g);
		plot.plotTrend(g, regression);
	}

}
