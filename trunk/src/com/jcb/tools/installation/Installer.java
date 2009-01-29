package com.jcb.tools.installation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.taskdefs.SQLExec.OnError;

public class Installer {

	private static BufferedWriter bw;

	public static void main(String[] args) throws Exception {
		System.out.println("installtion start");
		Config.init();
		System.out.println("Config.init() completed");
		Version.load();
		System.out.println("Version.load() completed");
		if (isUpdateEligible()) {
			bw = new BufferedWriter(new FileWriter(Config.getLogFile(), true));
			System.out.println("Create log file completed");
			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			String dateStr = df.format(new Date());
			addInstallLog("install : " + Config.getVersion() + " " + dateStr);
			addInstallLog("------------------------------------------");
			installFiles();
			System.out.println("installFiles() completed");
			installDatabase();
			System.out.println("installDatabase() completed");
			Version.setVersion(Config.getVersion());
			Version.setEnv(Config.getEnv());
			System.out.println(Version.getVersionHistory());
			System.out.println(Config.getVersion());
			Version.getVersionHistory().add(Config.getVersion());
			Version.updateVersion();
			System.out.println("updateVersion() completed");
			clearFiles();
			System.out.println("clearFiles() completed");
			addInstallLog("install : done");
			bw.newLine();
			bw.flush();
			bw.close();
		}
	}

	private static void addInstallLog(String s) throws IOException {
		System.out.println(s);
		bw.write(s);
		bw.newLine();
		bw.flush();
	}

	public static boolean isUpdateEligible() throws IOException {
		if (StringUtils.isEmpty(Version.getVersion()))
			return true;
		if (Config.getReqVersionList().contains(Version.getVersion()))
			return true;
		return false;
	}

	public static void installFiles() throws IOException {

		File file = new File(Config.getFileset());
		if (!file.exists()) {
			addInstallLog("fileset : no action required");
			return;
		}
		BufferedReader in = new BufferedReader(new FileReader(Config
				.getFileset()));
		try {
			String line = null; // not declared within while loop
			while ((line = in.readLine()) != null) {
				if (line.startsWith(" D ")) {
					String fn = line.substring(3);
					File f = new File(fn);
					FileUtils.deleteQuietly(f);
				}
				addInstallLog("fileset : " + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
	}

	public static void installDatabase() throws IOException {
		List<String> sqlScripts = getSqlScripts();
		if (sqlScripts.size() > 0) {
			SQLExec sql = new SQLExec();
			sql.setProject(new Project());
			sql.setDriver(Config.getDbDriver());
			sql.setUrl(Config.getDbUrl());
			sql.setUserid(Config.getDbUser());
			sql.setPassword(Config.getDbPass());
			for (int i = 0; i < sqlScripts.size(); i++) {
				String sqlScript = sqlScripts.get(i) + ".sql";
				sql.createTransaction().setSrc(
						new File("install/sql/" + sqlScript));
				addInstallLog("db : execute " + sqlScript);
			}
			sql.setEncoding("utf8");
			OnError oe = new OnError();
			oe.setValue("continue");
			sql.setOnerror(oe);
			sql.setOutput(new File("install/ant.log"));
			sql.setPrint(true);
			sql.execute();
		} else {
			addInstallLog("db : no action required");
		}
	}

	private static List<String> getSqlScripts() throws IOException {
		SQLScript.load();
		List<String> sqlScripts = new ArrayList<String>();
		int start = Config.getVersionList().indexOf(Version.getVersion());
		for (int i = start + 1; i < Config.getVersionList().size(); i++) {
			String version = Config.getVersionList().get(i);
			sqlScripts.addAll(SQLScript.getSQLScripts(version));
		}
		return sqlScripts;
	}

	public static void clearFiles() throws IOException {
		FileUtils.deleteQuietly(new File("install"));
		// addInstallLog("install : delete temporary files");
	}

}
