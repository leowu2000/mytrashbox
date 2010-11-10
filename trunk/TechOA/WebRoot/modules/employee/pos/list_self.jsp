<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%
	PageList pageList = (PageList)request.getAttribute("pageList");
	List listPos = pageList.getList();
	int pagenum = pageList.getPageInfo().getCurPage();
	
	String datepick = request.getAttribute("datepick").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>刷卡信息list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("pos.do?action=self_list&datepick="+datepick) %>
  	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>刷卡时间</td>
    		<td>人员编号</td>
    		<td>姓名</td>
    		<td>部门</td>
    		<td>车载POS机</td>
    		<td>金额</td>
    		<td>POS流水号</td>
    		<td>卡号</td>
<%
	for(int i=0;i<listPos.size();i++){
		Map mapPos = (Map)listPos.get(i);
%>
		<tr>
			<td><%=mapPos.get("SWIPETIME")==null?"":mapPos.get("SWIPETIME") %></td>
			<td><%=mapPos.get("EMPCODE")==null?"":mapPos.get("EMPCODE") %></td>
			<td><%=mapPos.get("EMPNAME")==null?"":mapPos.get("EMPNAME") %></td>
			<td><%=mapPos.get("DEPARTNAME")==null?"":mapPos.get("DEPARTNAME") %></td>
			<td><%=mapPos.get("POSMACHINE")==null?"":mapPos.get("POSMACHINE") %></td>
			<td><%=mapPos.get("COST")==null?"0":mapPos.get("COST") %></td>
			<td><%=mapPos.get("POSCODE")==null?"":mapPos.get("POSCODE") %></td>
			<td><%=mapPos.get("CARDNO")==null?"":mapPos.get("CARDNO") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
  </body>
</html>
