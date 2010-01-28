package com.basesoft.modules.project;

import java.util.Date;

public class Project {

	public String id;
	public String code;
	public String name;
	public String status;
	public String manager;
	public String member;
	public int planedworkload;
	public int nowworkload;
	public String startdate;
	public String enddate;
	public String note;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
	
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getManager() {
		return manager;
	}
	
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public String getMember() {
		return member;
	}
	
	public void setMember(String member) {
		this.member = member;
	}
	
	public int getPlanedworkload() {
		return planedworkload;
	}
	
	public void setPlanedworkload(int planedworkload) {
		this.planedworkload = planedworkload;
	}
	
	public int getNowworkload() {
		return nowworkload;
	}
	
	public void setNowworkload(int nowworkload) {
		this.nowworkload = nowworkload;
	}
	
	public String getStartdate() {
		return startdate;
	}
	
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	public String getEnddate() {
		return enddate;
	}
	
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
