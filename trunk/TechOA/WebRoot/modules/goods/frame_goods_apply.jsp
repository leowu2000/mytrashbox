<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>领料申请Frame</title>
    
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
		tb.add('制单人');
  		tb.add(document.getElementById('sel_empname'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('存货编码');
  		tb.add(document.getElementById('sel_code'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
	    document.getElementById('list_manage').src = "/goods.do?action=list_goodsapply";
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var sel_empname = document.getElementById('sel_empname').value;
	  var sel_code = document.getElementById('sel_code').value;
	  document.getElementById('list_manage').src = "/goods.do?action=list_goodsapply&sel_empname=" + encodeURI(sel_empname) + "&sel_code=" + sel_code;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>领料申请统计</h1>
  	<div id="toolbar"></div>
  	<input type="text" name="sel_empname" id="sel_empname" style="width:60;">
	<input type="text" name="sel_code" id="sel_code" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
