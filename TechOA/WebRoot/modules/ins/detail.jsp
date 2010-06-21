<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*"%>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.modules.ins.*" %>
<%
	Ins ins = (Ins)request.getAttribute("ins");
	List listBakcs = (List)request.getAttribute("listBakcs");
	String ins_id = request.getAttribute("ins_id").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>临时调查统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../../common/meta.jsp" %>
<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: 'excel导出',cls: 'x-btn-text-icon export',handler: onExportClick});
    
    function onBackClick(btn){
    	history.back(-1);
    }
    
    function onExportClick(btn){
    	window.location.href = "/excel.do?action=export&model=INS&id=<%=ins_id %>";
    }
    
});

//-->
</script>
  </head>
  
  <body>
  <h1>临时调查统计</h1>
  <div id="toolbar"></div>
<form id="listForm" name="listForm" action="" method="post">
  	<br>
    <table width="70%" align="center" vlign="middle" id="the-table">
    	<tr align="left">
    		<td bgcolor="#E0F1F8" class="b_tr" width="10%">调查标题</td>
    		<td><%=ins.getTitle() %></td>
    	</tr>
    	<tr align="left">
    		<td bgcolor="#E0F1F8" class="b_tr" width="10%">调查内容</td>
    		<td><%=ins.getNote() %></td>
    	</tr>
    	<tr align="left">
    		<td bgcolor="#E0F1F8" class="b_tr" width="10%">调查日期</td>
    		<td><%=ins.getStartdate() %></td>
    	</tr>
<%
	for(int i=0;i<listBakcs.size();i++){
		Map mapBacks = (Map)listBakcs.get(i);
%>
		<tr>
			<td bgcolor="#E0F1F8" class="b_tr" width="10%"><%=mapBacks.get("EMPNAME")==null?"":mapBacks.get("EMPNAME") %></td>
			<td><%=mapBacks.get("NOTE")==null?"":mapBacks.get("NOTE") %></td>
		</tr>
<%
	} 
%>
	</table>
  </form>
  </body>
</html>
