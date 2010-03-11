<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listPj = (List)request.getAttribute("listPj");
	List listStage = (List)request.getAttribute("listStage");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>计划提醒Frame</title>
    
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
  		tb.add('工作令号');
  		tb.add(document.getElementById('selpj'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('阶段');
  		tb.add(document.getElementById('selstage'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按名字模糊查询');
  		tb.add(document.getElementById('empname'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_remind").height = document.body.offsetHeight - document.getElementById("list_remind").offsetTop-10;
	}
	
	function commit(){
	  var pjcode = document.getElementById('selpj').value;
	  var stagecode = document.getElementById('selstage').value;
	  var empname = document.getElementById('empname').value;
	  
	  document.getElementById('list_remind').src = "/plan.do?action=remind_list&pjcode=" + pjcode + "&stagecode=" + stagecode + "&empname=" + empname;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<div id="toolbar"></div>
	<select name="selpj" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
%>				
		<option value="<%=mapPj.get("PJCODE") %>"><%=mapPj.get("PJNAME") %></option>
<%	} %>					
	</select>
	<select name="selstage" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listStage.size();i++){
		Map mapStage = (Map)listStage.get(i);
%>				
		<option value="<%=mapStage.get("STAGECODE") %>"><%=mapStage.get("STAGENAME") %></option>
<%	} %>					
	</select>
	<input type="text" name="empname" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_remind" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
