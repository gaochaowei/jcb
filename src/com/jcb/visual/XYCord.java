package com.jcb.visual;

import java.awt.Color;
import java.awt.Graphics;

import com.jcb.visual.Axis.Align;

public class XYCord {

	public Axis xaxis = new Axis();
	public Axis yaxis = new Axis();

	public XYCord() {
		xaxis.align = Align.HORIZONTAL;
		yaxis.align = Align.VERTICAL;
	}

	public XYCord(double xmin, double xmax, double ymin, double ymax, int xlow,
			int xhigh, int ylow, int yhigh) {
		xaxis.dr.l = xmin;
		xaxis.dr.h = xmax;
		xaxis.vr.l = xlow;
		xaxis.vr.h = xhigh;
		yaxis.dr.l = ymin;
		yaxis.dr.h = ymax;
		yaxis.vr.l = ylow;
		yaxis.vr.h = yhigh;
		xaxis.align = Align.HORIZONTAL;
		yaxis.align = Align.VERTICAL;
	}

	public void showBack(Graphics g) {
		Color temp = g.getColor();
		g.setColor(Color.black);
		g.fillRect(xaxis.vr.l, yaxis.vr.l, xaxis.vr.length(), yaxis.length());
		g.setColor(temp);
	}

	public void showXLine(Graphics g, int x) {
		if (xaxis.vr.contains(x)) {
			Color temp = g.getColor();
			g.setColor(Color.white);
			g.drawLine(x, yaxis.vr.h, x, yaxis.vr.l);
			g.setColor(temp);
		}
	}

	public void showYLine(Graphics g, int y) {
		if (yaxis.vr.contains(y)) {
			Color temp = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xaxis.vr.h, y, xaxis.vr.h, y);
			g.setColor(temp);
		}
	}

	public void paintXAxis(Graphics g) {
		// xaxis.paint(g, yaxis.vr.h, yaxis.length());
		xaxis.paint(g, yaxis.vr.h, 5);
	}

	public void paintYAxis(Graphics g) {
		// yaxis.paint(g, xaxis.vr.l, xaxis.vr.length());
		yaxis.paint(g, xaxis.vr.l, 5);
	}

	public boolean containPosition(int x, int y) {
		return xaxis.vr.contains(x) && yaxis.vr.contains(y);
	}

	public boolean containData(double x, double y) {
		return xaxis.dr.contains(x) && yaxis.dr.contains(y);
	}

	@Override
	public String toString() {
		return "XYCord[xaxis=" + xaxis + ",yaxis=" + yaxis + "]";
	}
}
