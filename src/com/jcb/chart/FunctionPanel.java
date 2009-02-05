package com.jcb.chart;

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
	private RealCoordinate2D cord = new RealCoordinate2D();
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
		cord.xAxis.setScreenLow(margin);
		cord.xAxis.setScreenHigh(margin + 100);
		cord.xAxis.setValueLow(0d);
		cord.xAxis.setValueHigh(100d);
		cord.yAxis.setScreenLow(0);
		cord.yAxis.setScreenHigh(100);
		cord.yAxis.setValueLow(0d);
		cord.yAxis.setValueHigh(100d);
		System.out.println(cord);
	}

	private void drawPoint(Graphics g, double x, double y) {
		int px = cord.xAxis.getScreen(x);
		int py = cord.yAxis.getScreen(y);
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
		for (int x = cord.xAxis.getScreenLow() + 1; x <= cord.xAxis
				.getScreenHigh(); x++) {
			int y0 = cord.yAxis.getScreen(exp.compute(cord.xAxis
					.getValue(x - 1)));
			int y1 = cord.yAxis.getScreen(exp.compute(cord.xAxis.getValue(x)));
			if (cord.yAxis.containScreen(y0) && cord.yAxis.containScreen(y1)) {
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
			if (cord.xAxis.containScreen(x)) {
				int y = cord.yAxis.getScreen(exp
						.compute(cord.xAxis.getValue(x)));
				if (cord.yAxis.containScreen(y)) {
					g.setColor(Color.red);
					g.drawLine(x, y, x, cord.yAxis.getScreenHigh());
					g.drawLine(cord.yAxis.getScreenLow(), y, x, y);
					double dx = cord.xAxis.getValue(x);
					double dy = cord.yAxis.getValue(y);
					g.drawString(String.valueOf(dx), x, cord.yAxis
							.getScreenHigh());
					g.drawString(String.valueOf(dy), cord.xAxis.getScreenLow(),
							y);
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
					g2d.fillRect(minx, cord.yAxis.getScreenLow(), dx,
							cord.yAxis.getScreenSize());
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
					g2d.fillRect(cord.xAxis.getScreenLow(), miny, cord.xAxis
							.getScreenSize(), dy);
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
				// cord.xAxis.dr.setRange(cord.xAxis.getValue(x1), cord.xAxis
				// .getValue(e.getX()));
				repaint();
				break;
			case ZOOM_Y:
				// cord.yAxis.dr.setRange(cord.yAxis.getValue(y1), cord.yAxis
				// .getValue(e.getY()));
				repaint();
				break;
			case ZOOM_XY:
				// cord.xAxis.dr.setRange(cord.xAxis.getValue(x1), cord.xAxis
				// .getValue(e.getX()));
				// cord.yAxis.dr.setRange(cord.yAxis.getValue(y1), cord.yAxis
				// .getValue(e.getY()));
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
				// double dx = (e.getX() - x1) * cord.xAxis.getScale();
				// double dy = (y1 - e.getY()) * cord.yAxis.getScale();
				// cord.xAxis.dr.move(-dx);
				// cord.yAxis.dr.move(-dy);
				// x1 = e.getX();
				// y1 = e.getY();
				// repaint();
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
		// double x = Math.pow(2, e.getWheelRotation() / 10d);
		if (notches < 0) {
			// cord.xAxis.dr.wide(x);
			// cord.yAxis.dr.wide(x);
			// } else {
			// cord.xAxis.dr.wide(x);
			// cord.yAxis.dr.wide(x);
		}
		repaint();
	}

	public synchronized void adjustCord() {
		System.out.println("adjustCord");
		// int dx = getWidth() - cord.xAxis.getScreenHigh();
		// int dy = getHeight() - margin - cord.yAxis.getScreenHigh();
		// double xscale = cord.xAxis.getScale();
		// double yscale = cord.yAxis.getScale();
		// cord.xAxis.getScreenHigh() += dx;
		// cord.xAxis.getValueHigh() += (dx * xscale);
		// cord.yAxis.getScreenHigh() += dy;
		// cord.yAxis.getValueHigh() += (dy * yscale);
		// System.out.println(cord);
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
			// cord.xAxis.getValueLow() /= 1.01;
			// cord.xAxis.getValueHigh() /= 1.01;
			// cord.yAxis.getValueLow() /= 1.01;
			// cord.yAxis.getValueHigh() /= 1.01;
			// repaint();
		}

		public void zoomOut() {
			// cord.xAxis.getValueLow() *= 1.01;
			// cord.xAxis.getValueHigh() *= 1.01;
			// cord.yAxis.getValueLow() *= 1.01;
			// cord.yAxis.getValueHigh() *= 1.01;
			// repaint();
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
					if (cord.containValue(px, py)) {
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
