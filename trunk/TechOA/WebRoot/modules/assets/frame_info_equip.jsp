<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listType = (List)request.getAttribute("listType");
	List listStatus = (List)request.getAttribute("listStatus");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>信息设备维护</title>
    
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
  		tb.add('资产编号：');
  		tb.add(document.getElementById('sel_code'));
  		tb.add('资产属性：');
  		tb.add(document.getElementById('sel_type'));
  		tb.add('设备状态：');
  		tb.add(document.getElementById('sel_status'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function commit(){
	  var sel_code = document.getElementById('sel_code').value;
	  var sel_type = document.getElementById('sel_type').value;
	  var sel_status = document.getElementById('sel_status').value;
	  
	  document.getElementById('list_info').src = "/assets.do?action=list_info_equip&sel_code=" + sel_code + "&sel_type=" + sel_type + "&sel_status=" + sel_status;
	  
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>信息设备维护</h1>
  	<div id="toolbar"></div>
  	<input type="text" style="width:60;" name="sel_code" id="sel_code">
	<select name="sel_type" id="sel_type" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listType.size();i++){
		Map mapType = (Map)listType.get(i);
%>				
		<option value="<%=mapType.get("CODE") %>"><%=mapType.get("NAME") %></option>
<%} %>					
	</select>
	<select name="sel_status" id="sel_status" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listStatus.size();i++){
		Map mapStatus = (Map)listStatus.get(i);
%>				
		<option value="<%=mapStatus.get("CODE") %>"><%=mapStatus.get("NAME") %></option>
<%} %>					
	</select>
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
