<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listChildDepart = (List)request.getAttribute("listChildDepart");
	String manage = request.getAttribute("manage").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人员基本情况</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../../My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="../../common/meta.jsp" %>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
  		tb.add('选择部门：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('seldepart'));
	});
	
	function commit(){
	  var seldepart = document.getElementById('seldepart').value;
	  document.getElementById('list_info').src = "/em.do?action=infolist&seldepart="+seldepart+"&manage="+'<%=manage %>';
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<div id="toolbar"></div>
	<select name="seldepart" onchange="commit();">
<%
	for(int i=0;i<listChildDepart.size();i++){
		Map mapDepart = (Map)listChildDepart.get(i);
%>				
		<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%} %>					
	</select>
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
