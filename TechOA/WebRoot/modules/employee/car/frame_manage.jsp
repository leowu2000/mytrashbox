<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../../common/meta.jsp" %>
	<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('按编号模糊查询');
  		tb.add(document.getElementById('sel_carcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var sel_carcode = document.getElementById('sel_carcode').value;
	    document.getElementById('list_info').src = "/car.do?action=list_manage&sel_carcode=" + sel_carcode;
	});
	
	function commit(){
	  var sel_carcode = document.getElementById('sel_carcode').value;
	  document.getElementById('list_info').src = "/car.do?action=list_manage&sel_carcode=" + sel_carcode;
	}
	
	function IFrameResize(){
	  document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>班车管理</h1>
  	<div id="toolbar"></div>
  	<span id="departspan" name="departspan"></span>
  	<input type="text" name="sel_carcode" id="sel_carcode" style="width:60;">
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
