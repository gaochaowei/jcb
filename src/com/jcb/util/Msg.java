package com.jcb.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Msg {
	private static ResourceBundle bundle;
	
	static{
//		setBundle("i18n.ApplicationResources",Locale.getDefault());
		setBundle("i18n.ApplicationResources",Locale.SIMPLIFIED_CHINESE);
	}
	
	public static void setBundle(String baseName, Locale locale){
		bundle = ResourceBundle.getBundle(baseName, locale);
	}
	
	public static String get(String key){
		if(bundle.containsKey(key))
			return bundle.getString(key);
		else
			return "${key} undefined";
	}
	
}
