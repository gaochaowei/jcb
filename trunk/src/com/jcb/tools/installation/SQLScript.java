package com.jcb.tools.installation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SQLScript {
	private static Map<String, List<String>> config = new HashMap<String, List<String>>();

	public static void load() throws IOException {
		FileInputStream fis = new FileInputStream(Config.getDbVersionFile());
		Properties props = new Properties();
		props.load(fis);
		for (Object o : props.keySet()) {
			String version = o.toString();
			String sqlList = props.getProperty(version);
			List<String> sqls = Utils.toList(sqlList, Config.getDelimiter());
			config.put(version, sqls);
		}
		fis.close();
	}

	public static List<String> getSQLScripts(String version) {
		if (!config.containsKey(version))
			return new ArrayList<String>();
		return config.get(version);
	}

}
