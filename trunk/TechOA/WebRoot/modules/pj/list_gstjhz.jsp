<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listDepart = (List)request.getAttribute("listDepart");
	List listGstjhz = (List)request.getAttribute("listGstjhz");
	
	int colcount = 3 + listDepart.size();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工时统计汇总</title>
    
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
		document.getElementById('tjt').src = "/pj.do?action=imageout&type=gstjhz";
		parent.IFrameResize();
	}
	</script>
  </head>
  
  <body onload="setTjt();">
  	<center><h2>工时统计汇总表</h2></center>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center">
			<td colspan="<%=colcount %>"><image id="tjt" name="tjt"></td>
		</tr>
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>序号</td>
    		<td>工作令号</td>
    		<td>本月合计</td>
<%
	for(int i=0;i<listDepart.size();i++){
		Map mapDepart = (Map)listDepart.get(i);
%>
			<td><%=mapDepart.get("NAME") %></td>
<%} %>
    	</tr>
<%
	for(int i=0;i<listGstjhz.size();i++){
		Map mapGstjhz = (Map)listGstjhz.get(i);
%>
		<tr align="center">
			<td><%=i+1 %></td>
			<td><%=mapGstjhz.get("NAME") %></td>
			<td><%=mapGstjhz.get("totalCount") %></td>
<%
		for(int j=0;j<listDepart.size();j++){
%>
			<td><%=mapGstjhz.get("departCount" + j) %></td>
<%} %>
		</tr>
<%} %>
	</table>
  </body>
</html>
