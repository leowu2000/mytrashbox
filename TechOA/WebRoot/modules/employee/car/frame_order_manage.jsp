<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listCar = (List)request.getAttribute("listCar");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>班车预约统计Frame</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
	<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择班次');
  		tb.add(document.getElementById('carid'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择日期');
  		tb.add(document.getElementById('datepick'));
  		
  		var carid = document.getElementById('carid').value;
	    var datepick = document.getElementById('datepick').value;
	    document.getElementById('list_order').src = "/car.do?action=list_order_manage&carid=" + carid + "&datepick=" + datepick;
	});
	
	function IFrameResize(){
	  document.getElementById("list_order").height = document.body.offsetHeight - document.getElementById("list_order").offsetTop-10;
	}
	
	function commit(){
	  var carid = document.getElementById('carid').value;
	  var datepick = document.getElementById('datepick').value;
	  
	  document.getElementById('list_order').src = "/car.do?action=list_order_manage&carid=" + carid + "&datepick=" + datepick;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>班车预约统计</h1>
  	<div id="toolbar"></div>
	<select id="carid" name="carid" onchange="commit();">
		<option value="0">请选择...</option>
<%
	for(int i=0;i<listCar.size();i++){
		Map mapCar = (Map)listCar.get(i);
%>		
		<option value="<%=mapCar.get("ID") %>"><%=mapCar.get("CARCODE") %></option>
<%
	}
%>
	</select>
	<input type="text" onclick="WdatePicker()" name="datepick" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM-dd") %>" style="width: 70">
    <iframe name="list_order" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
