package com.jcb.visual;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.stat.regression.SimpleRegression;

import com.jcb.math.Expression;
import com.jcb.util.CommonUtils;

public class FunctionPanel extends JPanel implements ComponentListener,
		MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = -3413261450239176866L;
	private XYCord cord = new XYCord();
	private int margin = 50;
	private List<Expression> exps = new ArrayList<Expression>();
	private Expression expReg;
	private double[][] randoms = null;
	private int x;
	// private int y;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	public Mode mode = Mode.ZOOM_X;
	private boolean released = true;

	public enum Mode {

		ZOOM_X, ZOOM_Y, ZOOM_XY, ZOOM_IN, ZOOM_OUT, DRAG, NONE, RANDOM;
	}

	public FunctionPanel() {
		initCord();
		this.addComponentListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
	}

	private void initCord() {
		System.out.println("initCord");
		cord.xaxis.vr.l = margin;
		cord.xaxis.vr.h = margin + 100;
		cord.xaxis.dr.l = 0;
		cord.xaxis.dr.h = 100;
		cord.yaxis.vr.l = 0;
		cord.yaxis.vr.h = 100;
		cord.yaxis.dr.l = 0;
		cord.yaxis.dr.h = 100;
		System.out.println(cord);
	}

	private void drawPoint(Graphics g, double x, double y) {
		int px = cord.xaxis.getPosition(x);
		int py = cord.yaxis.getPosition(y);
		g.setColor(Color.RED);
		g.drawLine(px - 2, py + 2, px + 2, py - 2);
		g.drawLine(px - 2, py - 2, px + 2, py + 2);
		g.setColor(this.getForeground());
	}

	private void drawRandoms(Graphics g) {
		for (double[] p : randoms) {
			drawPoint(g, p[0], p[1]);
		}
	}

	private void paintFunction(Graphics g, Expression exp) {
		g.setColor(Color.black);
		for (int x = cord.xaxis.vr.l + 1; x <= cord.xaxis.vr.h; x++) {
			int y0 = cord.yaxis.getPosition(exp.compute(cord.xaxis
					.getData(x - 1)));
			int y1 = cord.yaxis.getPosition(exp.compute(cord.xaxis.getData(x)));
			if (cord.yaxis.vr.contains(y0) && cord.yaxis.vr.contains(y1)) {
				g.drawLine(x - 1, y0, x, y1);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		cord.paintYAxis(g);
		cord.paintXAxis(g);
		if (expReg != null) {
			paintFunction(g, expReg);
		}
		for (Expression exp : exps) {
			paintFunction(g, exp);
			if (cord.xaxis.vr.contains(x)) {
				int y = cord.yaxis.getPosition(exp.compute(cord.xaxis
						.getData(x)));
				if (cord.yaxis.vr.contains(y)) {
					g.setColor(Color.red);
					g.drawLine(x, y, x, cord.yaxis.vr.h);
					g.drawLine(cord.yaxis.vr.l, y, x, y);
					double dx = cord.xaxis.getData(x);
					double dy = cord.yaxis.getData(y);
					g.drawString(String.valueOf(dx), x, cord.yaxis.vr.h);
					g.drawString(String.valueOf(dy), cord.xaxis.vr.l, y);
				}
			}
		}
		g.setColor(Color.black);
		if (randoms != null) {
			drawRandoms(g);
		}
		if (!released) {
			Composite cp = g2d.getComposite();
			switch (mode) {
			case ZOOM_X:
				int minx = Math.min(x1, x2);
				int dx = Math.abs(x2 - x1);
				System.out.println(dx);
				if (dx > 0) {
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 0.1f));
					g2d.setColor(Color.red);
					g2d.fillRect(minx, cord.yaxis.vr.l, dx, cord.yaxis.vr
							.length());
					g2d.setComposite(cp);
				}
				break;
			case ZOOM_Y:
				int miny = Math.min(y1, y2);
				int dy = Math.abs(y1 - y2);
				if (dy > 0) {
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 0.1f));
					g2d.setColor(Color.red);
					g2d.fillRect(cord.xaxis.vr.l, miny, cord.xaxis.vr.length(),
							dy);
					g2d.setComposite(cp);
				}
				break;
			case ZOOM_XY:
				minx = Math.min(x1, x2);
				dx = Math.abs(x2 - x1);
				miny = Math.min(y1, y2);
				dy = Math.abs(y1 - y2);
				if (dx > 0 & dy > 0) {
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 0.1f));
					g2d.setColor(Color.red);
					g2d.fillRect(minx, miny, dx, dy);
					g2d.setComposite(cp);
				}
				break;
			default:
				break;
			}
		}
	}

	public void componentHidden(ComponentEvent e) {
		System.out.println("componentHidden");
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		System.out.println("componentResized");
		adjustCord();
		repaint();
	}

	public void componentShown(ComponentEvent e) {
		System.out.println("componentShown");
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			switch (mode) {
			case DRAG:
				this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			case ZOOM_X:
			case ZOOM_Y:
			case ZOOM_XY:
				x1 = e.getX();
				y1 = e.getY();
				break;
			default:
				break;
			}
			released = false;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			switch (mode) {
			case DRAG:
				this.setCursor(Cursor.getDefaultCursor());
				break;
			case ZOOM_X:
				cord.xaxis.dr.setRange(cord.xaxis.getData(x1), cord.xaxis
						.getData(e.getX()));
				repaint();
				break;
			case ZOOM_Y:
				cord.yaxis.dr.setRange(cord.yaxis.getData(y1), cord.yaxis
						.getData(e.getY()));
				repaint();
				break;
			case ZOOM_XY:
				cord.xaxis.dr.setRange(cord.xaxis.getData(x1), cord.xaxis
						.getData(e.getX()));
				cord.yaxis.dr.setRange(cord.yaxis.getData(y1), cord.yaxis
						.getData(e.getY()));
				repaint();
				break;
			default:
				break;
			}
			released = true;
		}
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			switch (mode) {
			case DRAG:
				double dx = (e.getX() - x1) * cord.xaxis.getScale();
				double dy = (y1 - e.getY()) * cord.yaxis.getScale();
				cord.xaxis.dr.move(-dx);
				cord.yaxis.dr.move(-dy);
				x1 = e.getX();
				y1 = e.getY();
				repaint();
				break;
			case ZOOM_X:
			case ZOOM_Y:
			case ZOOM_XY:
				x2 = e.getX();
				y2 = e.getY();
				repaint();
				break;
			default:
				break;
			}
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		System.out.println(e.getScrollAmount());
		System.out.println(e.getScrollType());
		System.out.println(e.getWheelRotation());
		System.out.println(e.getUnitsToScroll());
		double x = Math.pow(2, e.getWheelRotation() / 10d);
		if (notches < 0) {
			cord.xaxis.dr.wide(x);
			cord.yaxis.dr.wide(x);
		} else {
			cord.xaxis.dr.wide(x);
			cord.yaxis.dr.wide(x);
		}
		repaint();
	}

	public synchronized void adjustCord() {
		System.out.println("adjustCord");
		int dx = getWidth() - cord.xaxis.vr.h;
		int dy = getHeight() - margin - cord.yaxis.vr.h;
		double xscale = cord.xaxis.getScale();
		double yscale = cord.yaxis.getScale();
		cord.xaxis.vr.h += dx;
		cord.xaxis.dr.h += (dx * xscale);
		cord.yaxis.vr.h += dy;
		cord.yaxis.dr.h += (dy * yscale);
		System.out.println(cord);
	}

	public void addExpression(String s) {
		Expression exp = new Expression(s);
		exps.add(exp);
		repaint();
	}

	public void clear() {
		exps.clear();
		randoms = null;
		expReg = null;
		setVisible(false);
		repaint();
	}

	Move move = new Move(this);

	public void zoomIn() {
		move.mode = Mode.ZOOM_IN;
		move.start();
	}

	public void zoomOut() {
		move.mode = Mode.ZOOM_OUT;
		move.start();
	}

	public void random() {
		move.mode = Mode.RANDOM;
		move.start();
	}

	private class Move implements Runnable {

		FunctionPanel p;
		public Mode mode = Mode.ZOOM_IN;

		public Move(FunctionPanel p) {
			this.p = p;
		}

		Thread thread;

		public void start() {
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}

		public void stop() {
			if (thread != null) {
				thread.interrupt();
			}
			thread = null;
		}

		public void zoomIn() {
			cord.xaxis.dr.l /= 1.01;
			cord.xaxis.dr.h /= 1.01;
			cord.yaxis.dr.l /= 1.01;
			cord.yaxis.dr.h /= 1.01;
			repaint();
		}

		public void zoomOut() {
			cord.xaxis.dr.l *= 1.01;
			cord.xaxis.dr.h *= 1.01;
			cord.yaxis.dr.l *= 1.01;
			cord.yaxis.dr.h *= 1.01;
			repaint();
		}

		public void process(String methodName, long time, long loop) {
			long interval = time / (loop - 1);
			long t = -1;
			long sleep = 1;
			try {
				Method method = getClass().getMethod(methodName);
				for (int i = 0; i < loop && Thread.currentThread() == thread; i++) {
					if (t > 0) {
						sleep = t + interval - System.currentTimeMillis();
						CommonUtils.sleep(sleep);
					}
					t = System.currentTimeMillis();
					method.invoke(this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			// Thread me = Thread.currentThread();
			if (mode == Mode.ZOOM_IN) {
				process("zoomIn", 1000, 10000);
			} else if (mode == Mode.ZOOM_OUT) {
				process("zoomOut", 1000, 10000);
			} else if (mode == Mode.RANDOM) {
				randoms = new double[500][2];
				RandomData randomData = new RandomDataImpl();
				for (int i = 0; i < randoms.length;) {
					double px = randomData.nextGaussian(300, 100);
					double py = randomData.nextGaussian(100, 100);
					if (cord.containData(px, py)) {
						randoms[i][0] = px;
						randoms[i][1] = py;
						i++;
						SimpleRegression reg = new SimpleRegression();
						reg.addData(randoms);
						String s;
						if (reg.getIntercept() < 0) {
							s = reg.getSlope() + "*x" + reg.getIntercept();
						} else {
							s = reg.getSlope() + "*x+" + reg.getIntercept();
						}
						expReg = new Expression(s);
						CommonUtils.sleep(1000);
						repaint();
					}
				}
			}
			thread = null;
		}
	}
}
