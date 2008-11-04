package com.jcb.util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class GuiUtils {

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
}
