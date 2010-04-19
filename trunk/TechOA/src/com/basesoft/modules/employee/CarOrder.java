package com.basesoft.modules.employee;

public class CarOrder {

	public String id;
	public String carid;
	public String empcode;
	public String ordersendtime;
	public String orderdate;
	public String status;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCarid() {
		return carid;
	}
	
	public void setCarid(String carid) {
		this.carid = carid;
	}
	
	public String getEmpcode() {
		return empcode;
	}
	
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	
	public String getOrdersendtime() {
		return ordersendtime;
	}
	
	public void setOrdersendtime(String ordersendtime) {
		this.ordersendtime = ordersendtime;
	}
	
	public String getOrderdate() {
		return orderdate;
	}
	
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
