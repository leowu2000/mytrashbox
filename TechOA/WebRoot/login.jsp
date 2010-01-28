<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.basesoft.util.StringUtil" %>
<html>
<head>
<title></title>
<%
	String user = StringUtil.nullToSpace((String) request.getAttribute("user"));
	String errorMessage = StringUtil.nullToSpace((String) request.getAttribute("errorMessage"));
	if ("".equals(errorMessage)) {
		errorMessage = new String(StringUtil.nullToSpace(request.getParameter("errorMessage")).getBytes("ISO8859-1"), "UTF-8");
	}
%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.fount {
	font-size: 12px;
}
.color {
	font-size: 16px;
	color:#FF0000;
	font-weight:bold;
}
.input {
	width: 140px;
	border: 1px solid #86B1D4;
}

-->
</style>
<script language="JavaScript" type="text/JavaScript">
</script>
</head>

<body style="background-position:right top " scroll="no">
<form id="listForm" name="listForm" action="/login.do?action=login" method="post">
<table border="0" cellspacing="0" cellpadding="0" align="center" valign="middle">
      <tr >
    	<td colspan="2" height="150" align="center" class="color"><%= errorMessage %></td>
  	  </tr>
      <tr align="center">
        <td align="left" class="fount">用户名：</td>
        <td align="left"><input name="user" id="user" type="text" class="input" value="<%= user %>"></td>
      </tr>
      <tr align="center">
        <td align="left" class="fount">密&nbsp;&nbsp;码：</td>
        <td align="left"><input name="password" id="password" type="password" class="input"></td>
      </tr>
      <tr align="center">
        <td width="76" ><input type="image" src="images/0807denglu.jpg" width="68" height="20" border="0"></td>
        <td width="68" align="right"><a href="#" onclick="document.getElementById('listForm').reset();"><img src="images/0807quxiao.jpg" width="68" height="20" border="0"></a></td>
      </tr>
</table>
</form>
</body>
</html>