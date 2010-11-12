<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>计划反馈Frame</title>
    
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
  		tb.add('年月');
  		tb.add(document.getElementById('datepick'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  document.getElementById('list_manage').src = "/plan.do?action=feedback&datepick=" + datepick;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>计划反馈</h1>
  	<div id="toolbar"></div>
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width:45;" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
