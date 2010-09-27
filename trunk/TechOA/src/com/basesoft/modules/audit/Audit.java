package com.basesoft.modules.audit;

import java.util.Date;
import java.util.UUID;

import com.basesoft.util.StringUtil;

public class Audit {
	public static final int AU_CHANGEPASS = 1;
	public static final int AU_ADMIN = 2;
	public static final int AU_FAILLOGIN = 3;
	
	public static final int SUCCESS = 1;
	public static final int FAIL = 2;
	
	public static final int LOCKED = 1;
	
	private String id;
	private int type;
	private String date;
	private String time;
	private String ip;
	private int success;
	private String empcode;
	private String description;
	
	public Audit(int type, String ip, int success, String empcode, String description){
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.type = type;
		this.date = StringUtil.DateToString(new Date(), "yyyy-MM-dd");
		this.time = StringUtil.DateToString(new Date(), "HH:mm:ss");
		this.ip = ip;
		this.success = success;
		this.empcode = empcode;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getSuccess() {
		return success;
	}
	
	public void setSuccess(int success) {
		this.success = success;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
