<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listLevel = (List)request.getAttribute("listLevel");
	List listType = (List)request.getAttribute("listType");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>计划管理Frame</title>
    
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
  		tb.add('考核级别');
  		tb.add(document.getElementById('sellevel'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('计划分类');
  		tb.add(document.getElementById('seltype'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择年月');
  		tb.add(document.getElementById('datepick'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按状态查看');
  		tb.add(document.getElementById('sel_status'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按责任人工号模糊查询');
  		tb.add(document.getElementById('sel_empcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按责任人姓名模糊查询');
  		tb.add(document.getElementById('empname'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
	});
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var level = document.getElementById('sellevel').value;
	  var type = document.getElementById('seltype').value;
	  var empname = document.getElementById('empname').value;
	  var sel_empcode = document.getElementById('sel_empcode').value;
	  var datepick = document.getElementById('datepick').value;
	  var sel_status = document.getElementById('sel_status').value;
	  
	  document.getElementById('list_manage').src = "/plan.do?action=list_planner&f_level=" + level + "&f_type=" + type + "&f_empname=" + encodeURI(empname) + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode + "&sel_status=" + sel_status;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>计划管理</h1>
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
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" style="width: 50" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy-MM") %>">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
	<select name="sel_status" id="sel_status" onchange="commit();">
		<option value="0">全部</option>
		<option value="1">新下发</option>
		<option value="2">已反馈无问题</option>
		<option value="6">已反馈有问题</option>
		<option value="3">已确认</option>
		<option value="4">已退回</option>
		<option value="5">新下发</option>
	</select>
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
