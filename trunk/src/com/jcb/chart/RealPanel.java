package com.jcb.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.jcb.chart.geo.Coordinate;
import com.jcb.chart.geo.Function;

public class RealPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Coordinate<Number, Number> cd;

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
		cd = new Coordinate<Number, Number>(Function.NONE, Function.NONE);
		fitScreen();
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				fitScreen();
				repaint();
			}
		});
	}

	public void fitScreen() {
		int width = this.getWidth();
		int height = this.getHeight();
		cd.setScreenRange(50, width - 10, 10, height - 50);
		cd.setValueRange(0d, width - 60d, 0d, height - 60d);
	}

	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		cd.paintAxis(g);
	}

}
