<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%
String empcode = request.getAttribute("empcode").toString();
String empname = request.getAttribute("empname").toString();
String departname = request.getAttribute("departname").toString();
PageList pageList = (PageList)request.getAttribute("pageList");
List listAssets = pageList.getList();

int pagenum = pageList.getPageInfo().getCurPage();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>固定资产领用信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="../../common/meta.jsp" %>
	<script src="/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="../../ext-2.2.1/ComboBoxTree.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var win;
var win1;
var win2;
var action;
var url='/assets.do';
var vali = "";
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返  回',cls: 'x-btn-text-icon back',handler: onBackClick});
	
	function onBackClick(btn){
    	history.back(-1);
    }
});

//-->
</script>
  </head>
  
  <body >
  	<h1>固定资产领用信息</h1>
  	<div id="toolbar"></div>
	<form id="listForm" name="listForm" action="" method="post">
<%=pageList.getPageInfo().getHtml("assets.do?action=sellend&empcode=" + empcode) %>
	<br>
    <table width="98%" align="center" vlign="middle" id="the-table">
    	<tr align="center" bgcolor="#E0F1F8"  class="b_tr">
			<td>设备编号</td>
    		<td>设备名称</td>
    		<td>设备型号</td>
    		<td>购买日期</td>
    		<td>出厂日期</td>
    		<td>使用年限</td>
    		<td>购买价格</td>
    		<td>状态</td>
    		<td>领用单位</td>
    		<td>领用人</td>
    		<td>领用时间</td>
    	</tr>
<%
    for(int i=0;i<listAssets.size();i++){
    	Map mapAssets = (Map)listAssets.get(i);
    	
    	String statusname = "";
    	String statuscode = mapAssets.get("STATUS")==null?"":mapAssets.get("STATUS").toString();
    	if("1".equals(statuscode)){
    		statusname = "库中";
    	}else if("2".equals(statuscode)){
    		statusname = "借出";
    	}else if("3".equals(statuscode)){
    		statusname = "损坏";
    	}
    	
%>    	
		<tr align="center">
			<td>&nbsp;<%=mapAssets.get("CODE")==null?"":mapAssets.get("CODE") %></td>
			<td>&nbsp;<%=mapAssets.get("NAME")==null?"":mapAssets.get("NAME") %></td>
			<td>&nbsp;<%=mapAssets.get("MODEL")==null?"":mapAssets.get("MODEL") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYDATE")==null?"":mapAssets.get("BUYDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("PRODUCDATE")==null?"":mapAssets.get("PRODUCDATE") %></td>
			<td>&nbsp;<%=mapAssets.get("LIFE")==null?"":mapAssets.get("LIFE") %></td>
			<td>&nbsp;<%=mapAssets.get("BUYCOST")==null?"":mapAssets.get("BUYCOST") %></td>
			<td>&nbsp;<%=statusname %></td>
			<td>&nbsp;<%=departname %></td>
			<td>&nbsp;<%=empname %></td>
			<td>&nbsp;<%=mapAssets.get("LENDDATE")==null?"":mapAssets.get("LENDDATE") %></td>
		</tr>
<%  } %>
    </table>
    </form>
  </body>
</html>
