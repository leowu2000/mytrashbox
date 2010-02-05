<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listChildDepart = (List)request.getAttribute("listChildDepart");
	String depart = request.getAttribute("depart").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>人事管理</title>
    
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
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择部门');
  		tb.add(document.getElementById('seldepart'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按名称模糊查询');
  		tb.add(document.getElementById('emname'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function init(){
	  document.getElementById('seldepart').value='<%=depart %>'
	}
	
	function commit(){
	  var seldepart = document.getElementById('seldepart').value;
	  var emname = document.getElementById('emname').value;
	  
	  document.getElementById('list_manage').src = "/em.do?action=list_manage&seldepart=" + seldepart + "&emname=" + emname;
	}
	</script>
  </head>
  
  <body onload="init();commit();IFrameResize();" onresize="IFrameResize();">
  	<div id="toolbar"></div>
	<select name="seldepart" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listChildDepart.size();i++){
		Map mapDepart = (Map)listChildDepart.get(i);
%>				
		<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%} %>					
	</select>
	<input type="text" name="emname" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
