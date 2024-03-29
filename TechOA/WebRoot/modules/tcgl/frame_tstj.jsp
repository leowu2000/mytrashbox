<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调试情况统计frame</title>
    
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
  		tb.add('令号');
  		tb.add(document.getElementById('selstatus'));
  		tb.add('整件号');
  		tb.add(document.getElementById('zjh'));
  		tb.add('加工情况');
  		tb.add(document.getElementById('jgqk'));
  		tb.add('调试状态');
  		tb.add(document.getElementById('tszt'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function commit(){
		document.getElementById('list_info').src = "/tcgl.do?action=tstj_list";
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>调试情况统计</h1>
  	<div id="toolbar"></div>
  	<select name="selstatus">
  		<option value="">全部</option>
  		<option value="1">工作令一</option>
  		<option value="2">工作令二</option>
  		<option value="3">工作令三</option>
  		<option value="4">工作令四</option>
  	</select>
  	<input type="text" name="zjh" id="zjh" style="width:60;">
  	<input type="text" name="jgqk" id="jgqk" style="width:60;">
  	<select name="tszt" id="tszt">
  		<option value="1">未开始</option>
  		<option value="2">交付设计师</option>
  		<option value="3">设计状态</option>
  		<option value="3">已完成</option>
  	</select>
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
