<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%
Train train = (Train)request.getAttribute("train");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>培训管理list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
	<script type="text/javascript">
	Ext.onReady(function(){
		var tb = new Ext.Toolbar({renderTo:'toolbar'});
		
		tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
		
		function onBackClick(btn){
    		history.back(-1);
    	}
	});
	</script>
  </head>
  
  <body>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
  	<br>
    <table width="68%" align="center" vlign="middle" id="the-table">
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训名称</td>
    		<td>&nbsp;<%=train.getName() %></td>
    	</tr>
    	<tr align="center" height="30">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训成本</td>
    		<td>&nbsp;<%=train.getCost() %></td>
    	</tr>
    	<tr align="center" height="60">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训目标</td>
    		<td>&nbsp;<%=train.getTarget() %></td>
    	</tr>
    	<tr align="center" height="60">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训计划</td>
    		<td>&nbsp;<%=train.getPlan() %></td>
    	</tr>
    	<tr align="center" height="60">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训过程记录</td>
    		<td>&nbsp;<%=train.getRecord() %></td>
    	</tr>
    	<tr align="center" height="60">
    		<td bgcolor="#E0F1F8" width="30%" class="b_tr">培训结果</td>
    		<td>&nbsp;<%=train.getResult() %></td>
    	</tr>
	</table>
  </form>
  </body>
</html>
