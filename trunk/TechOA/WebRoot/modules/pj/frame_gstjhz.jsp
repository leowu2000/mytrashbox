<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.util.*" %>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工时统计汇总</title>
    
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
  		tb.add('选择年月：');
  		tb.add('&nbsp;&nbsp;&nbsp;');
  		tb.add(document.getElementById('datepick'));
	});
	
	function commit(){
	  var datepick = document.getElementById('datepick').value;
	  if(datepick == ''){
	  	document.getElementById('datepick').value = '<%=StringUtil.DateToString(new Date(),"yyyy-MM") %>';
	  	datepick = document.getElementById('datepick').value;
	  }
	  document.getElementById('list_gstjhz').src = "/pj.do?action=gstjhz&datepick="+datepick;
	}
	function IFrameResize(){
	 document.getElementById("list_info").height = document.body.offsetHeight - document.getElementById("list_info").offsetTop-10;
	}
	</script>
  </head>
  
  <body onload="commit();IFrameResize();" onresize="IFrameResize();">
    <div id="toolbar"></div>
    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="datepick" onchange="commit();" style="width: 50">
    <iframe name="list_gstjhz" width="100%" frameborder="0" height="500"></iframe>
  </body>
</html>
