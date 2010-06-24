<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listLevel = (List)request.getAttribute("listLevel");
	List listType = (List)request.getAttribute("listType");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>计划跟踪Frame</title>
    
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
  		tb.add('内容');
  		tb.add(document.getElementById('sel_note'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var sel_note = document.getElementById('sel_note').value;
	  var datepick = document.getElementById('datepick').value;
	  
	  document.getElementById('list_manage').src = "/plan.do?action=list_follow_lead&datepick=" + datepick + "&sel_note=" + encodeURI(sel_note);
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>计划跟踪(领导)</h1>
  	<div id="toolbar"></div>
	<input type="text" name="sel_note" id="sel_note" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width: 50" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
