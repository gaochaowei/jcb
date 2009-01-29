package com.jcb.tools.installation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Config {

	private static final String INSTALL_CFG_FILE = "install/install.properties";

	private static String version;
	private static String env;
	private static List<String> versionList;
	private static List<String> reqVersionList;
	private static String userDir;
	private static String dbDriver;
	private static String dbUrl;
	private static String dbUser;
	private static String dbPass;
	private static String dbVersionFile;
	private static String installConfigFile;
	private static String versionFile;
	private static String filesetFile;
	private static String logFile;
	private static String delimiter;
	private static String fileset;

	public static String getUserDir() {
		return userDir;
	}

	public static void setUserDir(String userDir) {
		Config.userDir = userDir;
	}

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		Config.version = version;
	}

	public static String getEnv() {
		return env;
	}

	public static void setEnv(String env) {
		Config.env = env;
	}

	public static List<String> getVersionList() {
		return versionList;
	}

	public static void setVersionList(List<String> versionList) {
		Config.versionList = versionList;
	}

	public static List<String> getReqVersionList() {
		return reqVersionList;
	}

	public static void setReqVersionList(List<String> reqVersionList) {
		Config.reqVersionList = reqVersionList;
	}

	public static String getDbDriver() {
		return dbDriver;
	}

	public static void setDbDriver(String dbDriver) {
		Config.dbDriver = dbDriver;
	}

	public static String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		Config.dbUrl = dbUrl;
	}

	public static String getDbUser() {
		return dbUser;
	}

	public static void setDbUser(String dbUser) {
		Config.dbUser = dbUser;
	}

	public static String getDbPass() {
		return dbPass;
	}

	public static void setDbPass(String dbPass) {
		Config.dbPass = dbPass;
	}

	public static String getDbVersionFile() {
		return dbVersionFile;
	}

	public static void setDbVersionFile(String dbVersionFile) {
		Config.dbVersionFile = dbVersionFile;
	}

	public static String getInstallConfigFile() {
		return installConfigFile;
	}

	public static void setInstallConfigFile(String installConfigFile) {
		Config.installConfigFile = installConfigFile;
	}

	public static String getVersionFile() {
		return versionFile;
	}

	public static void setVersionFile(String versionFile) {
		Config.versionFile = versionFile;
	}

	public static String getFilesetFile() {
		return filesetFile;
	}

	public static void setFilesetFile(String filesetFile) {
		Config.filesetFile = filesetFile;
	}

	public static String getLogFile() {
		return logFile;
	}

	public static void setLogFile(String logFile) {
		Config.logFile = logFile;
	}

	public static String getDelimiter() {
		return delimiter;
	}

	public static void setDelimiter(String delimiter) {
		Config.delimiter = delimiter;
	}

	public static String getFileset() {
		return fileset;
	}

	public static void setFileset(String fileset) {
		Config.fileset = fileset;
	}

	public static void init() throws IOException {
		FileInputStream fis = new FileInputStream(INSTALL_CFG_FILE);
		Properties props = new Properties();
		props.load(fis);
		Config.setUserDir(System.getProperty("user.dir"));
		Config.setDelimiter(props.getProperty("delimiter"));
		Config.setEnv(props.getProperty("env"));
		Config.setVersion(props.getProperty("version"));
		Config.setVersionList(Utils.toList(props.getProperty("versionList"),
				Config.getDelimiter()));
		Config.setReqVersionList(Utils.toList(props
				.getProperty("reqVersionList"), Config.getDelimiter()));
		Config.setVersionFile(props.getProperty("version.file"));
		Config.setDbDriver(props.getProperty("db.driver"));
		Config.setDbUrl(props.getProperty("db.url"));
		Config.setDbUser(props.getProperty("db.user"));
		Config.setDbPass(props.getProperty("db.password"));
		Config.setFileset(props.getProperty("fileset"));
		Config.setDbVersionFile(props.getProperty("dbversion.file"));
		Config.setLogFile(props.getProperty("install.logfile"));
		fis.close();
	}

}
