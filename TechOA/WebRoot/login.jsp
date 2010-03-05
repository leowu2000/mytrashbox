<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.basesoft.util.StringUtil" %>
<%
	String user = StringUtil.nullToSpace((String) request.getAttribute("user"));
	String errorMessage = StringUtil.nullToSpace((String) request.getAttribute("errorMessage"));
	if ("".equals(errorMessage)) {
		errorMessage = new String(StringUtil.nullToSpace(request.getParameter("errorMessage")).getBytes("ISO8859-1"), "UTF-8");
	}
%>
<html>
<head>
<title>科研项目管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<!--
body {
	background-image: url(images/login_01.gif);
	background-repeat:repeat-x;
	
}
.STYLE1 {color: #000000}
.style2 {
	font-size: 12px;
	color: #FFFFFF;
}
input {
	height: 20px;
	width: 130px;
}
.color {
	font-size: 12px;
	color:#FF0000;
	font-weight:bold;
}
-->
</style>
<script type="text/javascript">
function toLogin(){
	var username = document.getElementById('user').value;
	var password = document.getElementById('password').value;
	if(username == ''){
		alert('请输入用户名！');
		return null;
	}
	if(password == ''){
		alert('请输入密码！');
		return null;
	}
	document.getElementById('listForm').submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" scroll="no">
<form id="listForm" name="listForm" action="/login.do?action=login" method="post">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top"><table border="0" cellspacing="0" cellpadding="0">
      <tr align="left" valign="top">
        <td><img src="images/login_02.gif" width="451" height="111"></td>
        <td><img src="images/login_03.jpg" width="290" height="111"></td>
      </tr>
      <tr align="left" valign="top">
        <td><img src="images/login_06.gif" width="451" height="515"></td>
        <td valign="middle" background="images/login_07.gif"><table width="100%" height="125" border="0" cellspacing="2" class="back1">
          <tr>
    	<td height="10" align="center" class="color" colspan="4"><%= errorMessage %></td>
  	  </tr>
          <tr align="left" valign="bottom">
            <td width="20%" height="32" align="right" valign="bottom"><span class="style2"><span class="style2">用户名</span>：</span></td>
            <td width="2%" align="center" valign="bottom">&nbsp;</td>
            <td width="53%" valign="bottom" class="STYLE1"><input name="user" id="user" type="text" size="25" /></td>
            <td width="25%" valign="bottom" class="STYLE1">&nbsp;</td>
          </tr>
          <tr align="left" valign="top">
            <td align="right" valign="middle"><span class="style2">密　码：</span></td>
            <td align="center" valign="middle">&nbsp;</td>
            <td height="43" valign="middle"><input name="password" id="password" type="password" size="25" /></td>
            <td height="43" valign="middle">&nbsp;</td>
          </tr>
          <tr align="left" valign="top">
            <td align="right" valign="middle">&nbsp;</td>
            <td height="34" colspan="2" align="left" valign="middle"><input type="image" src="images/denglu.gif" STYLE="width:61;height:21;"  border="0">&nbsp;&nbsp;&nbsp;<a href="#" onclick="document.getElementById('listForm').reset();"><img src="images/quxiao.gif" width="61" height="21" border="0"></a></td>
            <td height="34" align="center" valign="middle">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr align="left" valign="top">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>