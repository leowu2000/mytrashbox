<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.excel.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
TableSelectDAO tsDAO = (TableSelectDAO)ctx.getBean("tableSelectDAO");

String sel_table = request.getAttribute("sel_table").toString();
String table_comment = tsDAO.getTableComment(sel_table);
String columns = request.getAttribute("columns").toString();
String[] column = columns.split(",");
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>导入预览</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
	</head>
	<body>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value="<%=path %>">
<table cellspacing="0" id="the-table" width="100%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
<%
			for(int i=0;i<column.length;i++){
				String col_comment = tsDAO.getColumnComment(sel_table, column[i]);
%>            
  	  			<td><%=col_comment %></td>
<%
			}
%>  	  			
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>
			<tr align="center">
<%		
			for(int j=0;j<column.length;j++){
%>			
				<td><%=row.optString(column[j]) %></td>
<%
			}
%>				
		</tr>
<%
		}
%>		
</table>
</form>
			</div>
		</div>
	</body>
</html>