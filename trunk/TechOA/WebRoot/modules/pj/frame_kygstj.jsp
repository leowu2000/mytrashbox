<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
List listDepart = (List)request.getAttribute("listDepart");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>科研工时统计</title>
    
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
  		tb.add(document.getElementById('depart'));
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add('选择年月：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('datepick'));
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  var depart = document.getElementById('depart').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  document.getElementById('list_kygstj').src = "/pj.do?action=kygstj&datepick="+datepick+"&depart="+depart;
	}
	
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
 <body onload="commit();IFrameResize();" onresize="IFrameResize();">
 	<div id="toolbar"></div>
  	<select name="depart" onchange="commit();">
<%
	for(int i=0;i<listDepart.size();i++){
		Map mapDepart = (Map)listDepart.get(i);
%>				
		<option value="<%=mapDepart.get("CODE") %>"><%=mapDepart.get("NAME") %></option>
<%} %>					
	</select>
    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" style="width:50;">
    <iframe name="list_kygstj" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
