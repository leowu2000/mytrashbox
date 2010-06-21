<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>临时调查管理Frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		tb.add('标题模糊查询');
		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('sel_title'));
  		tb.add('选择日期');
  		tb.add(document.getElementById('startdate'));
  		tb.add('到');
  		tb.add(document.getElementById('enddate'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var sel_title = document.getElementById('sel_title').value;
	  var startdate = document.getElementById('startdate').value;
	  var enddate = document.getElementById('enddate').value;
	  
	  document.getElementById('list_manage').src = "/ins.do?action=manage&sel_title=" + encodeURI(sel_title) + "&startdate=" + startdate + "&enddate=" + enddate;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>临时调查管理</h1>
  	<div id="toolbar"></div>
  	<input type="text" name="sel_title" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="startdate" onchange="commit();" style="width: 70">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="enddate" onchange="commit();" style="width: 70">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
