package com.jcb.tools.installation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Utils {
	public static List<String> toList(String s, String delimiter) {
		if (StringUtils.isEmpty(s))
			return new ArrayList<String>();
		String[] ss = StringUtils.split(s, delimiter);
		return new ArrayList<String>(Arrays.asList(ss));
	}

	public static String toString(List<String> list, String delimiter) {
		if (list == null)
			return "";
		String s = "";
		for (String s1 : list) {
			if (!StringUtils.isEmpty(s))
				s += delimiter.trim();
			s += s1.trim();
			System.out.println(s);
		}
		return s;
	}
}
