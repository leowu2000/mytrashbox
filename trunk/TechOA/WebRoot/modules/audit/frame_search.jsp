<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.modules.audit.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>审计记录查询Frame</title>
    
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
  		tb.add('类别');
  		tb.add(document.getElementById('sel_type'));
  		tb.add('日期');
  		tb.add(document.getElementById('datepick'));
  		tb.add('工号');
  		tb.add(document.getElementById('sel_empcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var sel_type = document.getElementById('sel_type').value;
  		var datepick = document.getElementById('datepick').value;
  		var sel_empcode = document.getElementById('sel_empcode').value;
	    document.getElementById('list_search').src = "/audit.do?action=list_search&sel_type=" + sel_type + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
	});
	
	function IFrameResize(){
	  document.getElementById("list_search").height = document.body.offsetHeight - document.getElementById("list_search").offsetTop-10;
	}
	
	function commit(){
	  	var sel_type = document.getElementById('sel_type').value;
  		var datepick = document.getElementById('datepick').value;
  		var sel_empcode = document.getElementById('sel_empcode').value;
	    document.getElementById('list_search').src = "/audit.do?action=list_search&sel_type=" + sel_type + "&datepick=" + datepick + "&sel_empcode=" + sel_empcode;
	}
	</script>
  </head>
  
  <body onload="IFrameResize();" onresize="IFrameResize();">
  	<h1>审计记录查询</h1>
  	<div id="toolbar"></div>
  	<select name="sel_type" id="sel_type" onchange="commit();">
  		<option value="<%=Audit.AU_ADMIN %>">管理员操作</option>
  		<option value="<%=Audit.AU_CHANGEPASS %>">修改密码</option>
  		<option value="<%=Audit.AU_FAILLOGIN %>">违规操作</option>
  	</select>
  	
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange="commit();" name="datepick" id="datepick" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_search" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
