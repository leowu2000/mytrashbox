package com.basesoft.modules.plan;

public class Plan {

	public String id;
	public String empcode;
	public String pjcode;
	public String pjcode_d;
	public String stagecode;
	public String startdate;
	public String enddate;
	public int planedworkload;
	public String note;
	public String empname;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmpcode() {
		return empcode;
	}
	
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	
	public String getPjcode() {
		return pjcode;
	}
	
	public void setPjcode(String pjcode) {
		this.pjcode = pjcode;
	}
	
	public String getPjcode_d() {
		return pjcode_d;
	}
	
	public void setPjcode_d(String pjcode_d) {
		this.pjcode_d = pjcode_d;
	}
	
	public String getStagecode() {
		return stagecode;
	}
	
	public void setStagecode(String stagecode) {
		this.stagecode = stagecode;
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
	
	public int getPlanedworkload() {
		return planedworkload;
	}
	
	public void setPlanedworkload(int planedworkload) {
		this.planedworkload = planedworkload;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}
}
