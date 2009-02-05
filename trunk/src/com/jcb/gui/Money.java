package com.jcb.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jcb.chart.PriceChartPanel;
import com.jcb.util.ImageUtils;

public class Money extends JFrame implements ActionListener {

	public static final long serialVersionUID = 1;

	public Money() {
		setTitle("MM");
		setIconImage(ImageUtils.readImage("money.gif"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeMenuBar();
		PriceChartPanel chartPanel = new PriceChartPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Price", new ImageIcon("images/icons/market.gif"),
				chartPanel, "Price");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		// Create a panel and add components to it.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createLineBorder(Color.red));
		setContentPane(contentPane);
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

	public void makeMenuBar() {
		JMenuBar mainMenuBar = new JMenuBar();
		JMenu menuGeneral = new JMenu("General");
		mainMenuBar.add(menuGeneral);
		JMenu menuLook = new JMenu("Look and Feel");
		menuGeneral.add(menuLook);

		// System
		JMenuItem miLook = new JMenuItem("System");
		miLook.addActionListener(this);
		miLook.putClientProperty("class", UIManager
				.getSystemLookAndFeelClassName());
		menuLook.add(miLook);
		// CrossPlatform
		miLook = new JMenuItem("Cross Platform");
		miLook.addActionListener(this);
		miLook.putClientProperty("class", UIManager
				.getCrossPlatformLookAndFeelClassName());
		menuLook.add(miLook);

		menuLook.addSeparator();

		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : infos) {
			miLook = new JMenuItem(info.getName());
			miLook.addActionListener(this);
			miLook.putClientProperty("class", info.getClassName());
			menuLook.add(miLook);
		}
		this.setJMenuBar(mainMenuBar);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// try {
				// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				// } catch (Exception e) {
				// }
				JFrame.setDefaultLookAndFeelDecorated(true);
				Money money = new Money();
				money.setVisible(true);
				maximize(money);
			}
		});

	}

	public static void maximize(Frame frame) {
		Point frameLocation = frame.getLocationOnScreen();
		Dimension frameSize = frame.getSize();

		int maximizeX = frameLocation.x + frameSize.width - 30;
		int maximizeY = frameLocation.y + 10;
		try {
			Robot robot = new Robot();
			robot.mouseMove(maximizeX, maximizeY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent event) {
		Object object = event.getSource();
		try {
			if (object instanceof JMenuItem) {
				JMenuItem mi = (JMenuItem) object;
				UIManager.setLookAndFeel(mi.getClientProperty("class")
						.toString());
			}
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
			maximize(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
