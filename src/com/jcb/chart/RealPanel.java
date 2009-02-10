package com.jcb.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.jcb.chart.geo.Function;
import com.jcb.chart.geo.Space2D;

public class RealPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Space2D<Number, Number> space; // @jve:decl-index=0:

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
		space = new Space2D<Number, Number>(Function.NONE, Function.NONE);
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
		space.setScreenRange(50, width - 10, 10, height - 50);
		space.setValueRange(0d, width - 60d, 0d, height - 60d);
	}

	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		space.paintAxis(g, false, false);
	}

}
