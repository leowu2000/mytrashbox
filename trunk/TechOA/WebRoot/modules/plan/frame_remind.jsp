<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listLevel = (List)request.getAttribute("listLevel");
	List listType = (List)request.getAttribute("listType");
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
  		tb.add('考核级别');
  		tb.add(document.getElementById('sellevel'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('考核级别');
  		tb.add(document.getElementById('seltype'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择年月');
  		tb.add(document.getElementById('datepick'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按责任人模糊查询');
  		tb.add(document.getElementById('empname'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
  		
  		function onExportClick(){
  			var level = document.getElementById('sellevel').value;
  			var type = document.getElementById('seltype').value;
	  		var datepick = document.getElementById('datepick').value;
	  		var empname = document.getElementById('empname').value;
    		window.location.href = "/excel.do?action=export&model=PLAN&f_level=" + level + "&f_type=" + type + "&datepick=" + datepick + "&f_empname=" + empname;
  		}
	});
	
	function IFrameResize(){
	  document.getElementById("list_remind").height = document.body.offsetHeight - document.getElementById("list_remind").offsetTop-10;
	}
	
	function commit(){
	  var level = document.getElementById('sellevel').value;
  	  var type = document.getElementById('seltype').value;
	  var datepick = document.getElementById('datepick').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  var empname = document.getElementById('empname').value;
	  
	  document.getElementById('list_remind').src = "/plan.do?action=remind_list&f_level=" + level + "&f_type=" + type + "&datepick=" + datepick + "&f_empname=" + empname;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>考核统计</h1>
  	<div id="toolbar"></div>
	<select name="sellevel" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listLevel.size();i++){
		Map mapLevel = (Map)listLevel.get(i);
%>				
		<option value="<%=mapLevel.get("NAME") %>"><%=mapLevel.get("NAME") %></option>
<%	} %>					
	</select>
	<select name="seltype" onchange="commit();">
		<option value="0">全部</option>
<%
	for(int i=0;i<listType.size();i++){
		Map mapType = (Map)listType.get(i);
%>				
		<option value="<%=mapType.get("CODE") %>"><%=mapType.get("NAME") %></option>
<%	} %>					
	</select>
	<input type="text" name="empname" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" style="width: 50">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_remind" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
