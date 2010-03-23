package com.basesoft.modules.excel.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * 读取配置文件
 * @author 张海军
 *
 */
public class Config {
	
	public static String XML_PATH = java.net.URLDecoder.decode(Config.class.getResource("").getPath());
	
	/**
	 * 根据名称读取xml
	 * @param xml
	 * @return file对象
	 */
	public static File getFileByName(String xml) {
		
		File file = new File(XML_PATH + xml + ".xml");
		return file;
	}
	
	/**
	 * 根据名称读取xml
	 * @param xml
	 * @return String
	 * @throws IOException
	 */
	public static String getStringByName(String xml) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		File file = getFileByName(xml);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		return sb.toString();
	}
	
	/**
	 * 根据名称读取xml
	 * @param xml
	 * @return JSON对象
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject getJSONObjectByName(String xml) throws IOException, JSONException {
		
		String str = getStringByName(xml);
		return XML.toJSONObject(str).optJSONObject("config");
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		JSONObject object = getJSONObjectByName("Template_Check");
		JSONArray array = object.optJSONObject("body").optJSONArray("data");
		JSONObject data = array.optJSONObject(2);
		String format = data.optString("format");
		int format_int = data.optInt("format");
	}

}
