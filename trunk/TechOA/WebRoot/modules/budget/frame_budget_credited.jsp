<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listPj = (List)request.getAttribute("listPj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>预计到款表</title>
    
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
		tb.add('年份：');
  		tb.add(document.getElementById('sel_year'));
  		tb.add('项目名称：');
  		tb.add(document.getElementById('sel_name'));
  		tb.add('令号：');
  		tb.add(document.getElementById('sel_pjcode'));
  		tb.add('主管：');
  		tb.add(document.getElementById('sel_empname'));
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
		pjcombo.on('select',function(){
			commit();
		});
	});
	
	function commit(){
	  var sel_year = document.getElementById('sel_year').value;
	  var sel_name = document.getElementById('sel_name').value;
	  var sel_pjcode = document.getElementById('sel_pjcode').value;
	  var sel_empname = document.getElementById('sel_empname').value;
	  
	  document.getElementById('list_info').src = "/b_credited.do?action=list&sel_year=" + sel_year + "&sel_name=" + sel_name + "&sel_pjcode=" + sel_pjcode + "&sel_empname=" + sel_empname;
	  
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>预计到款表</h1>
  	<div id="toolbar"></div>
  	<input type="text" onclick="WdatePicker({dateFmt:'yyyy'})" name="sel_year" style="width: 50" onchange="commit();" value="<%=StringUtil.DateToString(new Date(), "yyyy") %>">
  	<input type="text" style="width:60;" name="sel_name" id="sel_name">
  	<select name="sel_pjcode" id="sel_pjcode" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
%>				
		<option value="<%=mapPj.get("CODE") %>"><%=mapPj.get("NAME") %></option>
<%} %>					
	</select>
	<input type="text" style="width:60;" name="sel_empname" id="sel_empname">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
