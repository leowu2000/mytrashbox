<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String selbc = request.getAttribute("selbc").toString();
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
  
  <body>
  <div id="toolbar"></div>
  <h1>班车预约统计</h1>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("car.do?action=list_manage_order&selbc=" + selbc + "&datepick=" + datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>班车编号</td>
    		<td>班车车牌号</td>
    		<td>员工姓名</td>
    		<td>预约时间</td>
<%
	List listOrder = pageList.getList();
	for(int i=0;i<listOrder.size();i++){
		Map mapOrder = (Map)listOrder.get(i);
%>
			<td><%=mapOrder.get("CARCODE")==null?"":mapOrder.get("CARCODE") %></td>
			<td><%=mapOrder.get("CARNO")==null?"":mapOrder.get("CARNO") %></td>
			<td><%=mapOrder.get("EMPNAME")==null?"":mapOrder.get("EMPNAME") %></td>
			<td><%=mapOrder.get("ORDERTIME")==null?"":mapOrder.get("ORDERTIME") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
  </body>
</html>
