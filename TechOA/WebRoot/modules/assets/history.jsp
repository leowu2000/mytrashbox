<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>固定资产历史情况</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bs_base.css" type="text/css" rel="stylesheet">
	<link href="css/bs_button.css" type="text/css" rel="stylesheet">
	<link href="css/bs_custom.css" type="text/css" rel="stylesheet">
	<%@ include file="../../common/meta.jsp" %>
  </head>
  
  <body>
  	<div id="toolbar1"></div>
	<form id="listForm" name="listForm" action="" method="post">
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>设备编号</td>
    		<td>设备名称</td>
    		<td>设备型号</td>
    		<td>状态</td>
    		<td>领用单位</td>
    		<td>领用人</td>
    		<td>领用时间</td>
    	</tr>
		<tr align="center">
			<td>&nbsp;3</td>
			<td>&nbsp;仪表1</td>
			<td>&nbsp;08-10</td>
			<td>&nbsp;借出</td>
			<td>&nbsp;一级部门</td>
			<td>&nbsp;刘一</td>
			<td>&nbsp;2010-01-08</td>
		</tr>
    	
		<tr align="center">
			<td>&nbsp;3</td>
			<td>&nbsp;仪表1</td>
			<td>&nbsp;08-10</td>
			<td>&nbsp;归还</td>
			<td>&nbsp;一级部门</td>
			<td>&nbsp;刘一</td>
			<td>&nbsp;2010-02-01</td>
		</tr>
    	
		<tr align="center">
			<td>&nbsp;3</td>
			<td>&nbsp;仪表1</td>
			<td>&nbsp;08-10</td>
			<td>&nbsp;借出</td>
			<td>&nbsp;二级部门</td>
			<td>&nbsp;王五</td>
			<td>&nbsp;2010-02-03</td>
		</tr>
    	
		<tr align="center">
			<td>&nbsp;3</td>
			<td>&nbsp;仪表1</td>
			<td>&nbsp;08-10</td>
			<td>&nbsp;归还</td>
			<td>&nbsp;二级部门</td>
			<td>&nbsp;王五</td>
			<td>&nbsp;2010-02-04</td>
		</tr>
    	
		<tr align="center">
			<td>&nbsp;3</td>
			<td>&nbsp;仪表1</td>
			<td>&nbsp;08-10</td>
			<td>&nbsp;借出</td>
			<td>&nbsp;一级部门</td>
			<td>&nbsp;刘一</td>
			<td>&nbsp;2010-02-08</td>
		</tr>
	
    </table>
    <br>
    <center><input type="button" value="返回" class="btn" onclick="history.back(-1);"></center>
    </form>
  </body>
</html>
