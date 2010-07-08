<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预算汇总</title>
    
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
  		tb.add('项目类别：');
  		tb.add(document.getElementById('sel_type'));
  		tb.add('项目编号：');
  		tb.add(document.getElementById('sel_applycode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
	});
	
	function commit(){
	  var sel_type = document.getElementById('sel_type').value;
	  var sel_applycode = document.getElementById('sel_applycode').value;
	  
	  document.getElementById('list_info').src = "/c_budget.do?action=list_budget_collect&sel_type=" + sel_type + "&sel_applycode=" + sel_applycode;
	  
	}
	
	function IFrameResize(){
	  document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>预算汇总</h1>
  	<div id="toolbar"></div>
  	<input type="text" style="width:60;" name="sel_applycode" id="sel_applycode">
  	<select name="sel_type" id="sel_type" onchange="commit();">
		<option value="">全部</option>
		<option value="KW">科研外协</option>
		<option value="KD">定制器材</option>				
	</select>
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
