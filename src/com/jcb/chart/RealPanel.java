package com.jcb.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.jcb.chart.geo.RealCoordinate2D;

public class RealPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RealCoordinate2D cd = new RealCoordinate2D(); // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public RealPanel() {
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
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				fitScreen();
				repaint();
			}
		});
		fitScreen();
	}

	public void fitScreen() {
		int width = this.getWidth();
		int height = this.getHeight();
		cd.getXAxis().setScreenLow(50);
		cd.getXAxis().setScreenHigh(width - 10);
		cd.getYAxis().setScreenLow(10);
		cd.getYAxis().setScreenHigh(height - 50);
		cd.getXAxis().setValueLow(0d);
		cd.getXAxis().setValueHigh(width - 60d);
		cd.getYAxis().setValueLow(0d);
		cd.getYAxis().setValueHigh(height - 60d);
	}

	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		cd.paintXAxis(g);
		cd.paintYAxis(g);
	}

}
