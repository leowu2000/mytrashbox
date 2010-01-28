<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listDate = (List)request.getAttribute("listDate");
	List listWorkCheck = (List)request.getAttribute("listWorkCheck");
	String departname = request.getAttribute("departname").toString();
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>人员考勤</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
  </head>
  
  <body>
    <center><h2>职工考勤记录</h2></center>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;单位名称:<%=departname %></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <span><%=datepick %></span>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center"  bgcolor="#E0F1F8" class="b_tr">
    		<td rowspan="2">姓名</td>
<%
	for(int i=0;i<listDate.size();i++){
%>    		
			<td rowspan="2"><%=StringUtil.DateToString((Date)listDate.get(i),"MM-dd") %></td>
<%} %>
			<td colspan="5">缺勤小结(小时)</td>			
    	</tr>
    	<tr align="center" bgcolor="#E0F1F8" class="b_tr">
    		<td>迟到</td>
			<td>早退</td>
			<td>病假</td>
			<td>事假</td>
			<td>旷工</td>
    	</tr>
<%
	for(int i=0;i<listWorkCheck.size();i++){
		Map mapWorkCheck = (Map)listWorkCheck.get(i);
%>    	
		<tr align="center">
			<td><%=mapWorkCheck.get("NAME") %></td>
<%
		for(int j=0;j<listDate.size();j++){
%>			
			<td>&nbsp;<%=mapWorkCheck.get(StringUtil.DateToString((Date)listDate.get(j),"yyyy-MM-dd")) %></td>
<%} %>
			<td><%=mapWorkCheck.get("cd") %></td>
			<td><%=mapWorkCheck.get("zt") %></td>
			<td><%=mapWorkCheck.get("bj") %></td>
			<td><%=mapWorkCheck.get("sj") %></td>
			<td><%=mapWorkCheck.get("kg") %></td>
		</tr>
<%} %>		
    </table>
  </body>
</html>
