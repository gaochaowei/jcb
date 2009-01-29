package com.jcb.tools.installation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Version {
	private static String version;
	private static String env;
	private static List<String> versionHistory;

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		Version.version = version;
	}

	public static String getEnv() {
		return env;
	}

	public static void setEnv(String env) {
		Version.env = env;
	}

	public static List<String> getVersionHistory() {
		return versionHistory;
	}

	public static void setVersionHistory(List<String> versionHistory) {
		Version.versionHistory = versionHistory;
	}

	public static void load() throws IOException {
		FileInputStream fis = new FileInputStream(Config.getVersionFile());
		Properties props = new Properties();
		props.load(fis);
		Version.setEnv(props.getProperty("env"));
		Version.setVersion(props.getProperty("version"));
		String versionHistory = props.getProperty("versionHistory");
		Version.setVersionHistory(Utils.toList(versionHistory, Config
				.getDelimiter()));
		fis.close();
	}

	public static void updateVersion() throws IOException {
		Properties properties = new Properties();
		String versionHistory = Utils.toString(Version.getVersionHistory(),
				Config.getDelimiter());
		properties.setProperty("env", Version.getEnv());
		properties.setProperty("version", Version.getVersion());
		properties.setProperty("versionHistory", versionHistory);
		properties.store(new FileOutputStream(Config.getVersionFile()), null);
	}
}
