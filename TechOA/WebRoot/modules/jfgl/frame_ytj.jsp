<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>月统计frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('月份：');
		tb.add(document.getElementById('datepick'));
  		tb.add('令号：');
  		tb.add(document.getElementById('pj'));
  		tb.add('部门：');
  		tb.add(document.getElementById('depart'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function commit(){
		document.getElementById('list_info').src = "/jfgl.do?action=ytj_list";
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>月统计</h1>
  	<div id="toolbar"></div>
  	<input type="text" style="width:60" name="datepick">
  	<select name="pj">
  		<option value="">全部</option>
  		<option value="1">工作令一</option>
  		<option value="2">工作令二</option>
  		<option value="3">工作令三</option>
  		<option value="4">工作令四</option>
  	</select>
  	<select name="depart">
  		<option value="">全部</option>
  		<option value="1">三部</option>
  		<option value="2">304室</option>
  	</select>
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
