<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
	List listPj = (List)request.getAttribute("listPj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工作报告审核Frame</title>
    
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
  		tb.add('令号');
  		tb.add(document.getElementById('sel_pjcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按工号模糊查询');
  		tb.add(document.getElementById('sel_empcode'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('按名字模糊查询');
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
	
	function IFrameResize(){
	  document.getElementById("list_manage").height = document.body.offsetHeight - document.getElementById("list_manage").offsetTop-10;
	}
	
	function commit(){
	  var sel_pjcode = document.getElementById('sel_pjcode').value;
	  var sel_empcode = document.getElementById('sel_empcode').value;
	  var sel_empname = document.getElementById('sel_empname').value;
	  
	  document.getElementById('list_manage').src = "/workreport.do?action=auditlist&sel_pjcode=" + encodeURI(sel_pjcode) + "&sel_empcode=" + sel_empcode + "&sel_empname=" + encodeURI(sel_empname);
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
  	<h1>审核工作报告</h1>
  	<div id="toolbar"></div>
	<select name="sel_pjcode" id="sel_pjcode" onchange="commit();">
		<option value="">全部</option>
<%
	for(int i=0;i<listPj.size();i++){
		Map mapPj = (Map)listPj.get(i);
		String name = mapPj.get("NAME")==null?"":mapPj.get("NAME").toString();
		if(name.length()>14){
			name = name.substring(0, 13) + "...";
		}
%>		
		<option value="<%=mapPj.get("CODE") %>"><%=name %></option>
<%
	}
%>
	</select>
	<input type="text" name="sel_empcode" id="sel_empcode" style="width:60;">
	<input type="text" name="sel_empname" id="sel_empname" style="width:60;">
	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_manage" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
