<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="java.net.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.announce.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>汇总投产对比表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var win2;
var action;
var url='/tcgl.do';
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '返回',cls: 'x-btn-text-icon back', handler: onBackClick});
	tb.add({text: '导出异常项',cls: 'x-btn-text-icon export'});
	
	function onBackClick(btn){
		history.back(-1);
	}
});

//-->
</script>
  </head>
  
  <body>
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>异常原因</td>
			<td>工作令号</td>
    		<td>图号</td>
    		<td>名称</td>
    		<td>调试备份数</td>
    		<td>备注</td>
    	</tr>
    	<tr align="center" >
    		<td>投产比组成多出1个</td>
			<td>工作令一</td>
			<td>AL2.827.661/7</td>
    		<td>控制7</td>
    		<td>2</td>
    		<td>多一个作为备份</td>
    	</tr>
    </table>
    </form>
  </body>
</html>
