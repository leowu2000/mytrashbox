<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.modules.announce.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>公告管理Frame</title>
    
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
  		tb.add('类别');
  		tb.add(document.getElementById('sel_type'));
  		tb.add('日期');
  		tb.add(document.getElementById('datepick'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var sel_type = document.getElementById('sel_type').value;
  		var datepick = document.getElementById('datepick').value;
	    document.getElementById('list_search').src = "/announce.do?action=list&sel_type=" + sel_type + "&datepick=" + datepick;
	});
	
	function IFrameResize(){
	  document.getElementById("list_search").height = document.body.offsetHeight - document.getElementById("list_search").offsetTop-10;
	}
	
	function commit(){
	  	var sel_type = document.getElementById('sel_type').value;
  		var datepick = document.getElementById('datepick').value;
	    document.getElementById('list_search').src = "/announce.do?action=list&sel_type=" + sel_type + "&datepick=" + datepick;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>公告管理</h1>
  	<div id="toolbar"></div>
  	<select name="sel_type" id="sel_type" onchange="commit();">
  		<option value="">全部</option>
  		<option value="<%=Announce.TYPE_NEWS %>">新闻</option>
  		<option value="<%=Announce.TYPE_NOTICE %>">通知</option>
  		<option value="<%=Announce.TYPE_OTHERS%>">其他</option>
  	</select>
  	
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange="commit();" name="datepick" id="datepick" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_search" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
