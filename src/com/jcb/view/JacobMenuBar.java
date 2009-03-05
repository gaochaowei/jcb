package com.jcb.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class JacobMenuBar extends JMenuBar {

	private static final long serialVersionUID = -1323729469984818756L;
	private JMenu jMenuData = null;
	private JMenuItem jMenuItemDataImport = null;
	private JMenuItem jMenuItemDataEdit = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemAbout = null;
	private JMenu jMenuFile = null;
	private JMenuItem jMenuItemExit = null;
	private JMenu jMenuChart = null;
	private JMenu jMenuScale = null;
	private JRadioButtonMenuItem jRadioButtonMenuItemScaleLinear = null;
	private JRadioButtonMenuItem jRadioButtonMenuItemScaleLog = null;
	/**
	 * This method initializes 
	 * 
	 */
	public JacobMenuBar() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.add(getJMenuFile());
        this.add(getJMenuChart());
        this.add(getJMenuData());
        this.add(getJMenuHelp());
        ButtonGroup grp = new ButtonGroup();
        grp.add(getJRadioButtonMenuItemScaleLinear());
        grp.add(getJRadioButtonMenuItemScaleLog());
	}

	/**
	 * This method initializes jMenuData	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuData() {
		if (jMenuData == null) {
			jMenuData = new JMenu();
			jMenuData.setText("Data");
			jMenuData.add(getJMenuItemDataImport());
			jMenuData.add(getJMenuItemDataEdit());
		}
		return jMenuData;
	}

	/**
	 * This method initializes jMenuItemDataImport	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemDataImport() {
		if (jMenuItemDataImport == null) {
			jMenuItemDataImport = new JMenuItem();
			jMenuItemDataImport.setText("Import");
		}
		return jMenuItemDataImport;
	}

	/**
	 * This method initializes jMenuItemDataEdit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemDataEdit() {
		if (jMenuItemDataEdit == null) {
			jMenuItemDataEdit = new JMenuItem();
			jMenuItemDataEdit.setText("Edit");
		}
		return jMenuItemDataEdit;
	}

	/**
	 * This method initializes jMenuHelp	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Help");
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItemAbout	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("About");
		}
		return jMenuItemAbout;
	}

	/**
	 * This method initializes jMenuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("File");
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	/**
	 * This method initializes jMenuItemExit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("Exit");
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes jMenuChart	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuChart() {
		if (jMenuChart == null) {
			jMenuChart = new JMenu();
			jMenuChart.setText("Chart");
			jMenuChart.add(getJMenuScale());
		}
		return jMenuChart;
	}

	/**
	 * This method initializes jMenuScale	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuScale() {
		if (jMenuScale == null) {
			jMenuScale = new JMenu();
			jMenuScale.setText("Scale");
			jMenuScale.add(getJRadioButtonMenuItemScaleLinear());
			jMenuScale.add(getJRadioButtonMenuItemScaleLog());
		}
		return jMenuScale;
	}

	/**
	 * This method initializes jRadioButtonMenuItemScaleLinear	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonMenuItemScaleLinear() {
		if (jRadioButtonMenuItemScaleLinear == null) {
			jRadioButtonMenuItemScaleLinear = new JRadioButtonMenuItem();
			jRadioButtonMenuItemScaleLinear.setSelected(true);
			jRadioButtonMenuItemScaleLinear.setText("Linear");
		}
		return jRadioButtonMenuItemScaleLinear;
	}

	/**
	 * This method initializes jRadioButtonMenuItemScaleLog	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonMenuItemScaleLog() {
		if (jRadioButtonMenuItemScaleLog == null) {
			jRadioButtonMenuItemScaleLog = new JRadioButtonMenuItem();
			jRadioButtonMenuItemScaleLog.setText("Log");
		}
		return jRadioButtonMenuItemScaleLog;
	}

}
