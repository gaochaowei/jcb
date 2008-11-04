package com.jcb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemConfig {

	public static String springBeansPath;
	public static String appTitle;
	static {
		init();
	}

	private static void init() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(Constants.SYSTEM_CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		springBeansPath = props.getProperty("spring.beans.path");
		appTitle = props.getProperty("app.title");
	}

}
