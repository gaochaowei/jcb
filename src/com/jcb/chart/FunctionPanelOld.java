package com.jcb.chart;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.jcb.util.CommonUtils;

public class FunctionPanelOld extends JPanel implements ComponentListener,
		MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = -3413261450239176866L;

	public Mode mode = Mode.ZOOM_X;

	public enum Mode {
		ZOOM_X, ZOOM_Y, ZOOM_XY, ZOOM_IN, ZOOM_OUT, DRAG, NONE, RANDOM;
	}

	public FunctionPanelOld() {
		initCord();
		this.addComponentListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
	}

	private void initCord() {
		System.out.println("initCord");

	}

	@Override
	public void paintComponent(Graphics g) {
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
				// x1 = e.getX();
				// y1 = e.getY();
				break;
			default:
				break;
			}
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
		}
	}

	public void mouseMoved(MouseEvent e) {
		// x = e.getX();
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
				// x2 = e.getX();
				// y2 = e.getY();
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
		if (notches < 0) {

		}
		repaint();
	}

	public synchronized void adjustCord() {
		System.out.println("adjustCord");
	}

	public void addExpression(String s) {
	}

	public void clear() {
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

		FunctionPanelOld p;
		public Mode mode = Mode.ZOOM_IN;

		public Move(FunctionPanelOld p) {
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
		}

		public void zoomOut() {
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
			}
			thread = null;
		}
	}
}
