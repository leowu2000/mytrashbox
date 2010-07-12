<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>付款申请</title>
    
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
  		tb.add('年月：');
  		tb.add(document.getElementById('datepick'));
  		tb.add('合同编号：');
  		tb.add(document.getElementById('sel_contractcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var sel_contractcode = document.getElementById('sel_contractcode').value;
	  
	  document.getElementById('list_info').src = "/c_pay.do?action=list_pay_collect&datepick=" + datepick + "&sel_contractcode=" + sel_contractcode;
	  
	}
	
	function IFrameResize(){
	  document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>付款申请报表</h1>
  	<div id="toolbar"></div>
  	<input type="text" style="width:60;" name="sel_contractcode" id="sel_contractcode">
  	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width: 50" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
