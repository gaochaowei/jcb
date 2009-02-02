/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcb.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.jcb.bean.EquityPriceBean;
import com.jcb.io.EquityReader;
import com.jcb.io.EquityReader.Frequency;
import com.jcb.math.GMath;
import com.jcb.visual.Axis.Measure;

/**
 * 
 * @author gcw
 */
public class MoneyPanel extends JPanel implements ComponentListener,
		MouseListener, MouseMotionListener, ActionListener {

	private static final long serialVersionUID = -3464685432158185930L;
	private List<EquityPriceBean> pxs = null;
	private double maxPrice = Double.MIN_VALUE;
	private double minPrice = Double.MAX_VALUE;
	private long maxVolumn = 0;
	// private int x = -1, y = -1;
	private TimeSeriesCord cord = null;
	private TimeSeriesCord cord2 = null;
	private double[] avgSMA;
	private double[] avgsEMA;
	private double[] avgSMMA;
	private double[] avgsLWMA;
	private double[] avgsVWMA;
	public double[] avgsVMA;
	private List<Line2D> lines = new ArrayList<Line2D>();
	private Line2D activeLine = null;
	private int x1, y1;
	private int margin = 30;
	// private Date dateFrom = DateUtils.addDays(new Date(), -100);
	// private Date dateTo = new Date();
	private String symbol = null;
	private Date selected = null;
	private JLabel lblSymbol = new JLabel("Symbol");
	private JTextField txtSymbol = new JTextField(6);
	private JToggleButton butLOG = new JToggleButton("LOG");
	private JToggleButton butSMA = new JToggleButton("SMA");
	private JToggleButton butEMA = new JToggleButton("EMA");
	private JToggleButton butSMMA = new JToggleButton("SMMA");
	private JToggleButton butLWMA = new JToggleButton("LWMA");
	private JToggleButton butVWMA = new JToggleButton("VWMA");
	private JToggleButton butVMA = new JToggleButton("VMA");
	private JToggleButton butDRAW = new JToggleButton("DRAW");
	private JButton butZoomIn = new JButton("Zoom In");
	private JButton butZoomOut = new JButton("Zoom Out");
	private JToolBar toolBar = new JToolBar();
	private Frequency freq = Frequency.DATE;
	private State state = new State();

	public void loadPrice(String symbol) {
		if (!StringUtils.isBlank(symbol) && !symbol.equals(this.symbol)) {
			this.symbol = symbol;
			loadPrice();
			repaint();
		}
	}

	private void showHide() {
		if (symbol == null) {
			butLOG.setVisible(false);
			butSMA.setVisible(false);
			butEMA.setVisible(false);
			butSMMA.setVisible(false);
			butLWMA.setVisible(false);
			butVWMA.setVisible(false);
			butVMA.setVisible(false);
			butDRAW.setVisible(false);
			butZoomIn.setVisible(false);
			butZoomOut.setVisible(false);
		} else {
			butLOG.setVisible(true);
			butSMA.setVisible(true);
			butEMA.setVisible(true);
			butSMMA.setVisible(true);
			butLWMA.setVisible(true);
			butVWMA.setVisible(true);
			butVMA.setVisible(true);
			butDRAW.setVisible(true);
			butZoomIn.setVisible(true);
			butZoomOut.setVisible(true);
		}
	}

	private void loadPrice() {
		if (symbol == null) {
			return;
		}
		pxs = EquityReader.fetchEquityPrice(symbol, state.dateFrom,
				state.dateTo, freq);
		maxPrice = Double.MIN_VALUE;
		minPrice = Double.MAX_VALUE;
		maxVolumn = 0;

		Vector<Date> dv = new Vector<Date>();
		for (EquityPriceBean px : pxs) {
			if (px.getPriceHigh() > maxPrice) {
				maxPrice = px.getPriceHigh();
			}
			if (px.getPriceLow() < minPrice) {
				minPrice = px.getPriceLow();
			}
			if (px.getVolumn() > maxVolumn) {
				maxVolumn = px.getVolumn();
			}
			dv.add(px.getDate());
		}

		cord = new TimeSeriesCord(dv.firstElement(), dv.lastElement(),
				minPrice, maxPrice, margin, getWidth() - margin, toolBar
						.getHeight()
						+ margin, getHeight() - margin - 100);
		cord.yaxis.measuer = Measure.LINEAR;
		cord2 = new TimeSeriesCord(dv.firstElement(), dv.lastElement(), 0,
				maxVolumn, margin, getWidth() - margin, getHeight() - 80
						- margin, getHeight() - margin);

		avgSMA = GMath.computeSMA(pxs, 20);
		avgsEMA = GMath.computeEMA(pxs, 20, 0.05);
		avgSMMA = GMath.computeSMMA(pxs, 20);
		avgsLWMA = GMath.computeLWMA(pxs, 20);
		avgsVWMA = GMath.computeVWMA(pxs, 20);
		avgsVMA = GMath.computeVolumeMA(pxs, 20);
	}

	public MoneyPanel() {
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		butLOG.addActionListener(this);
		butSMA.addActionListener(this);
		butEMA.addActionListener(this);
		butSMMA.addActionListener(this);
		butLWMA.addActionListener(this);
		butVWMA.addActionListener(this);
		butVMA.addActionListener(this);
		butDRAW.addActionListener(this);
		butZoomIn.addActionListener(this);
		butZoomOut.addActionListener(this);
		txtSymbol.addActionListener(this);
		toolBar.add(lblSymbol);
		toolBar.add(txtSymbol);
		toolBar.add(butLOG);
		toolBar.add(butSMA);
		toolBar.add(butEMA);
		toolBar.add(butSMMA);
		toolBar.add(butLWMA);
		toolBar.add(butVWMA);
		toolBar.add(butVMA);
		toolBar.add(butDRAW);
		toolBar.add(butZoomIn);
		toolBar.add(butZoomOut);
		add(toolBar, BorderLayout.NORTH);
		loadPrice();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		showHide();
		if (symbol == null) {
			return;
		}
		if (butLOG.isSelected()) {
			cord.yaxis.measuer = Measure.LOG;
		} else {
			cord.yaxis.measuer = Measure.LINEAR;
		}

		cord.showBack(g);
		cord.paintYAxis(g);
		cord.paintXAxis(g);
		cord2.showBack(g);
		cord2.paintYAxis(g);
		g.setColor(Color.GRAY);
		cord.showXLine(g, state.x);
		cord2.showXLine(g, state.x);
		if (cord.xaxis.vr.contains(state.x)) {
			Date selectedDate = cord.xaxis.getValue(state.x);
			EquityPriceBean px = getPrice(pxs, selectedDate);
			g.drawString(px.getSymbol(), cord.xaxis.vr.l, cord.yaxis.vr.l - 5);
			g.drawString(String.format("Date:%1$td/%1$tm/%1$tY", px.getDate()),
					cord.xaxis.vr.l + 50, cord.yaxis.vr.l - 5);
			g.drawString("Open:" + px.getPriceOpen(), cord.xaxis.vr.l + 160,
					cord.yaxis.vr.l - 5);
			g.drawString("Low:" + px.getPriceLow(), cord.xaxis.vr.l + 240,
					cord.yaxis.vr.l - 5);
			g.drawString("High:" + px.getPriceHigh(), cord.xaxis.vr.l + 320,
					cord.yaxis.vr.l - 5);
			g.drawString("Close:" + px.getPriceClose(), cord.xaxis.vr.l + 400,
					cord.yaxis.vr.l - 5);
			g.drawString("Volume:" + px.getVolumn(), cord.xaxis.vr.l + 480,
					cord.yaxis.vr.l - 5);
		}

		for (int i = 0; i < pxs.size(); i++) {
			EquityPriceBean px = pxs.get(i);
			int x0 = cord.xaxis.getX(px.getDate());
			int yo = cord.yaxis.getPosition(px.getPriceOpen());
			int yc = cord.yaxis.getPosition(px.getPriceClose());
			int yl = cord.yaxis.getPosition(px.getPriceLow());
			int yh = cord.yaxis.getPosition(px.getPriceHigh());
			// paintComponent K line
			if (px.getPriceClose() > px.getPriceOpen()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			g.fillRect(x0 - 1, Math.min(yo, yc), 3, Math.abs(yo - yc) + 1);
			g.drawLine(x0, yl, x0, yh);
			// paintComponent volum
			g.fillRect(x0 - 2, cord2.yaxis.getPosition(px.getVolumn()), 5,
					cord2.yaxis.getPosition(0)
							- cord2.yaxis.getPosition(px.getVolumn()));
			// paintComponent average
			if (i > 0) {
				int x00 = cord.xaxis.getX(pxs.get(i - 1).getDate());
				if (butSMA.isSelected()) {
					g.setColor(Color.BLUE);
					g.drawLine(x00, cord.yaxis.getPosition(avgSMA[i - 1]), x0,
							cord.yaxis.getPosition(avgSMA[i]));
					g.setColor(this.getForeground());
				}

				if (butEMA.isSelected()) {
					g.setColor(Color.PINK);
					g.drawLine(x00, cord.yaxis.getPosition(avgsEMA[i - 1]), x0,
							cord.yaxis.getPosition(avgsEMA[i]));
					g.setColor(this.getForeground());
				}

				if (butSMMA.isSelected()) {
					g.setColor(Color.CYAN);
					g.drawLine(x00, cord.yaxis.getPosition(avgSMMA[i - 1]), x0,
							cord.yaxis.getPosition(avgSMMA[i]));
					g.setColor(this.getForeground());
				}

				if (butLWMA.isSelected()) {
					g.setColor(Color.ORANGE);
					g.drawLine(x00, cord.yaxis.getPosition(avgsLWMA[i - 1]),
							x0, cord.yaxis.getPosition(avgsLWMA[i]));
					g.setColor(this.getForeground());
				}

				if (butVWMA.isSelected()) {
					g.setColor(Color.MAGENTA);
					g.drawLine(x00, cord.yaxis.getPosition(avgsVWMA[i - 1]),
							x0, cord.yaxis.getPosition(avgsVWMA[i]));
					g.setColor(this.getForeground());
				}

				if (butVMA.isSelected()) {
					g.setColor(Color.yellow);
					g.drawLine(x00, cord2.yaxis.getPosition(avgsVMA[i - 1]),
							x0, cord2.yaxis.getPosition(avgsVMA[i]));
					g.setColor(this.getForeground());
				}

			}
		}

		g.setColor(Color.YELLOW);
		for (Line2D line : lines) {

			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(line);
		}

		if (activeLine != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(activeLine);
			activeLine = null;

		}

		g.setColor(this.getForeground());
	}

	public void zoomIn() {
		long days = state.dateTo.getTime() - state.dateFrom.getTime();
		days = days / (24 * 60 * 60 * 1000);
		if (days <= 10) {
			return;
		} else {
			state.dateFrom = DateUtils
					.addDays(state.dateFrom, (int) (days / 2));
		}
		switch (freq) {
		case DATE:
			days = days / (24 * 60 * 60 * 1000);
			if (days <= 10) {
				return;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days / 2);
			break;
		case WEEK:
			days = days / (7 * 24 * 60 * 60 * 1000);
			if (days <= 10) {
				freq = Frequency.DATE;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days / 2);
			break;
		case MONTH:
			days = days / (30 * 24 * 60 * 60 * 1000);
			if (days <= 10) {
				freq = Frequency.WEEK;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days / 2);
			break;
		default:
			days = days / (365 * 24 * 60 * 60 * 1000);
			if (days <= 10) {
				freq = Frequency.MONTH;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days / 2);
			break;
		}
		loadPrice();
		repaint();
	}

	public void zoomOut() {
		long days = state.dateTo.getTime() - state.dateFrom.getTime();
		switch (freq) {
		case DATE:
			days = days / (24 * 60 * 60 * 1000);
			if (days >= 200) {
				freq = Frequency.WEEK;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days);
			break;
		case WEEK:
			days = days / (7 * 24 * 60 * 60 * 1000);
			if (days >= 200) {
				freq = Frequency.MONTH;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days);
			break;
		case MONTH:
			days = days / (30 * 24 * 60 * 60 * 1000);
			if (days >= 200) {
				freq = Frequency.YEAR;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days);
			break;
		default:
			days = days / (365 * 24 * 60 * 60 * 1000);
			if (days >= 200) {
				return;
			}
			state.dateFrom = DateUtils.addDays(state.dateFrom, -(int) days);
		}
		loadPrice();
		repaint();
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		if (symbol != null) {
			cord.xaxis.vr.h = this.getWidth() - margin;
			cord2.xaxis.vr.h = this.getWidth() - margin;
			cord.yaxis.vr.h = getHeight() - margin - 100;
			cord2.yaxis.vr.l = getHeight() - 80 - margin;
			cord2.yaxis.vr.h = getHeight() - margin;
			repaint();
		}
	}

	public void componentShown(ComponentEvent e) {
		showHide();
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (butDRAW.isSelected()) {
			this.x1 = e.getX();
			this.y1 = e.getY();
		} else {
			if (cord.xaxis.vr.contains(e.getX())) {
				Date selectedDate = cord.xaxis.getValue(e.getX());
				EquityPriceBean px = getPrice(pxs, selectedDate);
				state.dateFrom = px.getDate();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (butDRAW.isSelected()) {
			lines.add(new Line2D.Float(x1, y1, e.getX(), e.getY()));
			repaint();
		} else {
			if (cord.xaxis.vr.contains(e.getX())) {
				Date selectedDate = cord.xaxis.getValue(e.getX());
				EquityPriceBean px = getPrice(pxs, selectedDate);
				state.dateTo = px.getDate();
				loadPrice();
				repaint();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		state.x = e.getX();
		state.y = e.getY();
		if (symbol != null && !cord.xaxis.getValue(state.x).equals(selected)) {
			selected = cord.xaxis.getValue(state.x);
			repaint();
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (butDRAW.isSelected()) {
			activeLine = new Line2D.Float(x1, y1, e.getX(), e.getY());
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butZoomIn) {
			zoomIn();
		} else if (e.getSource() == butZoomOut) {
			zoomOut();
		} else if (e.getSource() == txtSymbol) {
			loadPrice(txtSymbol.getText());
		} else {
			repaint();
		}
	}

	private class State {

		public Date dateFrom = DateUtils.addDays(new Date(), -100);
		public Date dateTo = new Date();
		public int x, y, x1, y1;
	}

	public EquityPriceBean getPrice(List<EquityPriceBean> pxs, Date date) {
		for (EquityPriceBean px : pxs) {
			if (px.getDate().equals(date))
				return px;
		}
		return null;
	}
}
