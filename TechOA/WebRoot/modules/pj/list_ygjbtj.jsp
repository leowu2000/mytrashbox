<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
	List listYgjbtj = (List)request.getAttribute("listYgjbtj");
	
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	EmployeeDAO employeeDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工加班统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<style type="text/css">
body {
	font-size: 20px;
	font-weight: normal;
}
#the-table { border:1px solid #bbb;border-collapse:collapse;margin: 5px auto 5px auto; font-size: 20px;color:#696969;}
	</style>
	<script type="text/javascript">
	function setTjt(){
		document.getElementById('tjt').src = "/pj.do?action=imageout&type=ygjbtj";
		parent.IFrameResize();
	}
	</script>
  </head>
  
  <body onload="setTjt();">
  	<center><h2>员工加班统计</h2></center>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center">
			<td colspan="7"><image id="tjt" name="tjt"></td>
		</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>姓名</td>
    		<td>部门</td>
    		<td>职位</td>
    		<td>学历</td>
    		<td>职称</td>
    		<td>加班工时</td>
    	</tr>
<%
	for(int i=0;i<listYgjbtj.size();i++){
		Map mapYgjbtj = (Map)listYgjbtj.get(i);
%>
		<tr align="center">
			<td><%=mapYgjbtj.get("NAME") %></td>
			<td><%=mapYgjbtj.get("DEPARTNAME") %></td>
			<td><%=mapYgjbtj.get("LEVEL") %></td>
			<td><%=mapYgjbtj.get("DEGREENAME") %></td>
			<td><%=mapYgjbtj.get("PRONAME") %></td>
			<td><%=mapYgjbtj.get("AMOUNT") %></td>
		</tr>
<%} %>
	</table>
  </body>
</html>
