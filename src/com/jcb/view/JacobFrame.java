package com.jcb.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JacobFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane jContentPane = null;
	private JacobMenuBar jacobMenuBar = null;
	/**
	 * This is the default constructor
	 */
	public JacobFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setJMenuBar(getJacobMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Jacob");
		this.setExtendedState(this.getExtendedState()
				| JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JTabbedPane();
		}
		return jContentPane;
	}

	/**
	 * This method initializes jacobMenuBar	
	 * 	
	 * @return com.jcb.view.JacobMenuBar	
	 */
	private JacobMenuBar getJacobMenuBar() {
		if (jacobMenuBar == null) {
			jacobMenuBar = new JacobMenuBar();
		}
		return jacobMenuBar;
	}

}
