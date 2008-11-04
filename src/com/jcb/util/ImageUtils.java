package com.jcb.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage readImage(String file) {
		try {
			return ImageIO.read(new File(file));
		} catch (IOException e) {
			return null;
		}
	}

}
