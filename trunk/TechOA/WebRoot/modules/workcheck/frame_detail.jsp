<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤详细frame</title>
    
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
		tb.add('日期：');
  		tb.add(document.getElementById('datepick'));
  		tb.add('部门：');
  		tb.add(document.getElementById('type'));
  		tb.add('工号：');
  		tb.add(document.getElementById('code'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function commit(){
		document.getElementById('list_info').src = "/workcheck.do?action=detail_list";
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>考勤明细管理</h1>
  	<div id="toolbar"></div>
  	<input type="text" name="datepick" id="datepick" style="width:60;">
  	<select name="type">
  		<option value="">全部</option>
  		<option value="1">三部</option>
  		<option value="2">304室</option>
  	</select>
  	<input type="text" name="code" id="code" style="width:60;">
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
