<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.modules.tcgl.*" %>
<%
	List listPj = (List)request.getAttribute("listPj");
	List listStatus = (List)request.getAttribute("listStatus");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调试跟踪frame</title>
    
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
		tb.add('令号：');
  		tb.add(document.getElementById('sel_pjcode'));
  		tb.add('状态：');
  		tb.add(document.getElementById('sel_status'));
  		tb.add('整件号：');
  		tb.add(document.getElementById('sel_zjh'));
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
		var sel_pjcode = document.getElementById('sel_pjcode').value;
		var sel_zjh = document.getElementById('sel_zjh').value;
		var sel_status = document.getElementById('sel_status').value;
		document.getElementById('list_info').src = "/tcgl.do?action=tcgz_list&sel_pjcode=" + sel_pjcode + "&sel_zjh=" + sel_zjh + "&sel_status=" + sel_status;
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
	<h1>调试情况跟踪</h1>
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
  	<select name="sel_status" id="sel_status" onchange="commit();">
  		<option value="">全部</option>
<%
	for(int i=0;i<listStatus.size();i++){
		Map mapStatus = (Map)listStatus.get(i);
		String name = mapStatus.get("NAME")==null?"":mapStatus.get("NAME").toString();
%>		
		<option value="<%=mapStatus.get("CODE") %>"><%=name %></option>
<%
	}
%>
  	</select>
  	<input type="text" name="sel_zjh" id="sel_zjh" style="width:60;">
  	<input type="button" class="btn" value="查询" name="search" onclick="commit();">
    <iframe name="list_info" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
