package com.jcb.visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.jcb.visual.FunctionPanel.Mode;

public class GXYPanel extends JPanel implements ComponentListener,
		ActionListener {

	private static final long serialVersionUID = 8583690297982299577L;
	private JLabel lblExpression = new JLabel("Expression");
	private JTextField txtExpression = new JTextField(20);
	private JButton butClear = new JButton("Clear");
	private JButton butRandom = new JButton("Random");
	private JButton butReg = new JButton("Regression");
	private JButton butDrag = new JButton(new ImageIcon(
			"images/icons/toolbarButtonGraphics/general/ContextualHelp16.gif"));
	private JButton butZoomX = new JButton(
			new ImageIcon(
					"images/icons/toolbarButtonGraphics/general/AlignJustifyVertical16.gif"));
	private JButton butZoomY = new JButton(
			new ImageIcon(
					"images/icons/toolbarButtonGraphics/general/AlignJustifyHorizontal16.gif"));
	private JButton butZoomXY = new JButton(new ImageIcon(
			"images/icons/toolbarButtonGraphics/general/AlignCenter16.gif"));
	private JButton butZoomIn = new JButton(new ImageIcon(
			"images/icons/toolbarButtonGraphics/general/ZoomIn16.gif"));
	private JButton butZoomOut = new JButton(new ImageIcon(
			"images/icons/toolbarButtonGraphics/general/ZoomOut16.gif"));
	private JToolBar toolBar = new JToolBar("Function tools");
	private FunctionPanel fPanel = new FunctionPanel();

	public GXYPanel() {
		this.addComponentListener(this);
		setLayout(new BorderLayout());
		txtExpression.addActionListener(this);
		butClear.addActionListener(this);
		butRandom.addActionListener(this);
		butReg.addActionListener(this);
		butDrag.addActionListener(this);
		butZoomX.addActionListener(this);
		butZoomY.addActionListener(this);
		butZoomXY.addActionListener(this);
		butZoomIn.addActionListener(this);
		butZoomOut.addActionListener(this);
		toolBar.add(lblExpression);
		toolBar.add(txtExpression);
		toolBar.add(butClear);
		toolBar.add(butRandom);
		toolBar.add(butReg);
		toolBar.add(butDrag);
		toolBar.add(butZoomX);
		toolBar.add(butZoomY);
		toolBar.add(butZoomXY);
		toolBar.add(butZoomIn);
		toolBar.add(butZoomOut);
		add(BorderLayout.NORTH, toolBar);
		add(BorderLayout.CENTER, fPanel);
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == txtExpression) {
			fPanel.addExpression(txtExpression.getText());
		} else if (e.getSource() == butClear) {
			fPanel.clear();
		} else if (e.getSource() == butRandom) {
			fPanel.random();
		} else if (e.getSource() == butReg) {

		} else if (e.getSource() == butZoomIn) {
			fPanel.zoomIn();
		} else if (e.getSource() == butZoomOut) {
			fPanel.zoomOut();
		} else if (e.getSource() == butZoomX) {
			fPanel.mode = Mode.ZOOM_X;
		} else if (e.getSource() == butZoomY) {
			fPanel.mode = Mode.ZOOM_Y;
		} else if (e.getSource() == butZoomXY) {
			fPanel.mode = Mode.ZOOM_XY;
		} else if (e.getSource() == butDrag) {
			fPanel.mode = Mode.DRAG;
		}
	}
}
