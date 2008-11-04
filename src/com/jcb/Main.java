/**
 * 
 */
package com.jcb;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jcb.gui.AppFrame;
import com.jcb.util.GuiUtils;
import com.jcb.util.SpringUtils;

/**
 * @author gcw
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AppFrame appFrame = (AppFrame) SpringUtils.getBean("appFrame");
				JFrame.setDefaultLookAndFeelDecorated(true);
				appFrame.setVisible(true);
				GuiUtils.maximize(appFrame);
			}
		});
	}
}
