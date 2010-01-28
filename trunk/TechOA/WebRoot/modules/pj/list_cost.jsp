<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listCost = (List)request.getAttribute("listCost");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>课题费用</title>
    
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
  	<center><h2>课题费用</h2></center>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>日期</td>
    		<td>单据编号</td>
    		<td>工作令号</td>
    		<td>分系统</td>
    		<td>整件号</td>
    		<td>编码</td>
    		<td>型号规格</td>
    		<td>单位</td>
    		<td>数量</td>
    		<td>金额</td>
    		<td>姓名</td>
    		<td>领料单位</td>
    		<td>结算单位</td>
    		<td>用途</td>
<%
	for(int i=0;i<listCost.size();i++){
		Map mapCost = (Map)listCost.get(i);
%>
		<tr>
			<td><%=mapCost.get("RQ") %></td>
			<td><%=mapCost.get("BILLCODE") %></td>
			<td><%=mapCost.get("PJCODE") %></td>
			<td><%=mapCost.get("FXT") %></td>
			<td><%=mapCost.get("ZJH") %></td>
			<td><%=mapCost.get("CODE") %></td>
			<td><%=mapCost.get("XHGG") %></td>
			<td><%=mapCost.get("DW") %></td>
			<td><%=mapCost.get("SL") %></td>
			<td><%=mapCost.get("JE") %></td>
			<td><%=mapCost.get("XM") %></td>
			<td><%=mapCost.get("LLDW") %></td>
			<td><%=mapCost.get("JSDW") %></td>
			<td><%=mapCost.get("YT") %></td>
		</tr>
<%} %>
	</table>
  </body>
</html>
