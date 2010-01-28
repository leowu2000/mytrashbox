package com.basesoft.core;

import java.io.IOException;
import java.util.Properties;

public class Constants {
	public static String ROOTPATH;

	public static Properties p = new Properties();
	static {
		try {
			p.load(Constants.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return p.getProperty(key, "");
	}
	public static String get(String key,String defaultValue) {
		return p.getProperty(key, defaultValue);
	}
}
