/**
 * 
 */
package com.jcb.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jcb.util.GuiUtils;
import com.jcb.util.ImageUtils;

/**
 * @author gcw
 * 
 */
public class AppFrame extends JFrame implements ActionListener {

	public static final long serialVersionUID = 1L;

	public AppFrame() {
		super();
		setIconImage(ImageUtils.readImage("money.gif"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeMenuBar();
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
	}

	private void makeMenuBar() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		try {
			if (object instanceof JMenuItem) {
				JMenuItem mi = (JMenuItem) object;
				UIManager.setLookAndFeel(mi.getClientProperty("class")
						.toString());
			}
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
