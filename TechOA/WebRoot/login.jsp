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
<title>项目管理系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(images/bg1.jpg);
	font-size: 9px;	
}
div {
	font-size: 9px;
	height: 20px;
	width: 100px;
}
input {
	font-size: 12px;
	border: 1px solid #A9CCDE;
	background-color: #E4F4FD;
	height: 20px;
	line-height: normal;
	width: 120px;
}
.style2 {
	COLOR: #ffffff;
	font-size: 12px;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->

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

<body onload="MM_preloadImages('images/denglu_b.gif','images/quxiao_b.gif')" scroll="no" >
<form id="listForm" name="listForm" action="/login.do?action=login" method="post">
<table width="100%" height="640" border="0" cellspacing="0">
  <tr>
    <td align="center" valign="middle"><table width="629" height="335" border="0" cellspacing="0">
      <tr>
        <td align="center" background="images/login.jpg"><table width="548" border="0">
          <tr>
            <td width="186" height="119">&nbsp;</td>
            <td width="54">&nbsp;</td>
            <td width="132">&nbsp;</td>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center"><span class="style2">用户名：</span></td>
            <td align="right"><input name="user" id="user" type="text" size="25" /></td>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td height="27">&nbsp;</td>
            <td align="center"><span class="style2">密　码：</span></td>
            <td align="right"><input name="password" id="password" type="password" size="25" /></td>
            <td colspan="2">&nbsp;</td>
          </tr>          
          <tr>
            <td height="42">&nbsp;</td>
            <td height="42" colspan="2">&nbsp;</td>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td height="14">&nbsp;</td>
            <td height="48" colspan="2">&nbsp;</td>
            <td width="72"><a href="#" onclick="toLogin()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image1','','images/denglu_b.gif',1)"><img src="images/denglu_a.gif" name="Image1" width="68" height="24" border="0" id="Image1" /></a></td>
            <td width="82"><a href="#" onclick="document.getElementById('listForm').reset();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','images/quxiao_b.gif',1)"><img src="images/quxiao_a.gif" name="Image2" width="68" height="24" border="0" id="Image2" /></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>