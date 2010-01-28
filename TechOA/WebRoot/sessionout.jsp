<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登陆超时</title>
<%@ include file="/common/meta.jsp" %>
<%
	String errorMessage = (String)request.getAttribute("errorMessage");
%>
</head>
<body>
<form action="" method="post" id="listForm" name="listForm" target="_top">
	<input type="hidden" name="errorMessage" value="<%= errorMessage %>">
</form>
</body>
<script type="text/javascript">
	function submitForm(action) {
		Ext.getDom('listForm').action = action;
		Ext.getDom('listForm').submit();
	}
	submitForm('login.jsp');
</script>
</html>
