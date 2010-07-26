<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%
String errorMsg = request.getAttribute("errorMsg").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>导入结果</title>
<%@ include file="../../common/meta.jsp" %>
	</head>
	<body>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<table cellspacing="0" id="the-table" width="100%" align="center">
	<tr align="left" bgcolor="#E0F1F8" class="b_tr">
		<td>
			<%=errorMsg %>
		</td>
	</tr>
</table>
</form>
			</div>
		</div>
	</body>
</html>