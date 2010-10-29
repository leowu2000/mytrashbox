package com.basesoft.modules.audit;

import java.util.Date;
import java.util.UUID;

import com.basesoft.util.StringUtil;

public class Audit {
	public static final String AU_CHANGEPASS = "1";		//修改密码
	public static final String AU_ADMIN = "2";			//管理员操作
	public static final String AU_FAILLOGIN = "3";		//登录失败
	
	public static final int SUCCESS = 1;				//操作成功
	public static final int FAIL = 2;					//操作失败
	
	public static final int LOCKED = 1;					//用户锁定(锁定后不允许登陆，需由管理员打开)
	
	private String id;
	private String type;
	private String date;
	private String time;
	private String ip;
	private int success;
	private String empcode;
	private String description;
	
	public Audit(String type, String ip, int success, String empcode, String description){
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
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
