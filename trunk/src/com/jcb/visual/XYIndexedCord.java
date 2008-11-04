package com.jcb.visual;

import java.awt.Color;
import java.awt.Graphics;

import com.jcb.visual.Axis.Align;

public class XYIndexedCord {

	public IndexAxis xaxis = new IndexAxis();
	public Axis yaxis = new Axis();

	public XYIndexedCord() {
		yaxis.align = Align.VERTICAL;
	}

	public XYIndexedCord(int xmin, int xmax, double ymin, double ymax,
			int xlow, int xhigh, int ylow, int yhigh) {
		xaxis.vr.l = xlow;
		xaxis.vr.h = xhigh;
		xaxis.dr.l = xmin;
		xaxis.dr.h = xmax;
		yaxis.dr.l = ymin;
		yaxis.dr.h = ymax;
		yaxis.vr.l = ylow;
		yaxis.vr.h = yhigh;
		yaxis.align = Align.VERTICAL;
	}

	public void showBack(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.black);
		g.fillRect(xaxis.vr.l, yaxis.vr.l, xaxis.length(), yaxis.length());
		g.setColor(temp);
	}

	public void showXLine(Graphics g, int x) {
		if (xaxis.vr.contains(x)) {
			Color temp = g.getColor();
			g.setColor(Color.white);
			x = xaxis.getX(xaxis.getValue(x));
			g.drawLine(x, yaxis.vr.h, x, yaxis.vr.l);
			g.setColor(temp);
		}
	}

	public void showYLine(Graphics g, int y) {
		if (yaxis.vr.contains(y)) {
			Color temp = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xaxis.vr.h, y, xaxis.vr.l, y);
			g.setColor(temp);
		}
	}

	public void paintXAxis(Graphics g) {
		xaxis.paint(g, yaxis.vr.h);
	}

	public void paintYAxis(Graphics g) {
		yaxis.paint(g, xaxis.vr.l, xaxis.length());
	}
}
