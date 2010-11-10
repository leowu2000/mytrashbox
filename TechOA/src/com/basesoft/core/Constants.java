package com.basesoft.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
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
	
	public static void set(String key, String value){
		try{
			URI path =Constants.class.getResource("/config.properties").toURI();
			Writer w=new FileWriter(new File(path));
			p.setProperty(key, value);
			p.store(w, key);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
