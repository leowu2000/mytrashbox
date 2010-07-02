<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	List listPj = (List)request.getAttribute("listPj");
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
  		tb.add('项目编号：');
  		tb.add(document.getElementById('sel_code'));
  		tb.add('工作令号：');
  		tb.add(document.getElementById('sel_pjcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('search'));
  		
  		var pjcombo = new Ext.form.ComboBox({
        	typeAhead: true,
        	triggerAction: 'all',
        	emptyText:'',
        	mode: 'local',
        	selectOnFocus:true,
        	transform:'sel_pjcode',
        	width:203,
        	maxHeight:300
		});
	});
	
	function commit(){
	  var sel_code = document.getElementById('sel_code').value;
	  var sel_pjcode = document.getElementById('sel_pjcode').value;
	  
	  document.getElementById('list_info').src = "/c_apply.do?action=list_apply&sel_code=" + sel_code + "&sel_code=" + sel_code;
	  
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
  	<select name="sel_pjcode" id="sel_pjcode" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
%>				
		<option value="<%=mapPj.get("CODE") %>"><%=mapPj.get("NAME") %></option>
<%} %>					
	</select>
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
