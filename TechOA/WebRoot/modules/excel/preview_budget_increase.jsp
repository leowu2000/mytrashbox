<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.goods.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String import_year = request.getAttribute("import_year").toString();
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
GoodsDAO goodsDAO = (GoodsDAO)ctx.getBean("goodsDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>预计到款表</title>
		<style type="text/css">
		<!--
		input{
			width:80px;
		}
		.ainput{
			width:20px;
		}		
		th {
			white-space: nowrap;
		}
		-->
		</style>		
<%@ include file="../../common/meta.jsp" %>
<script type="text/javascript">
Ext.onReady(function(){
	var tb = new Ext.Toolbar({renderTo:'toolbar'});
	tb.add({text: '返回',cls: 'x-btn-text-icon back',handler: onBackClick});
	tb.add({text: '保存入库',cls: 'x-btn-text-icon import',handler: onImportClick});
	
	function onBackClick(btn){
    	window.location.href = 'b_increase.do?action=list';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=b_increase.do?action=list&table=BUDGET_INCREASE&import_year=<%=import_year %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>增量预算导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>
<table cellspacing="0" id="the-table" width="98%" align="center">
        <tr align="center" bgcolor="#E0F1F8"  class="b_tr">
    		<td>序号</td>
    		<td>名称</td>
    		<td>分管所领导</td>
    		<td>分管首席</td>
    		<td>预算总经费</td>
    		<td>研制、批产</td>
    		<td>数量</td>
    		<td>计划节点</td>
    		<td><%=import_year %>年预算增量</td>
    		<td>1季度</td>
    		<td>2季度</td>
    		<td>3季度</td>
    		<td>4季度</td>
    		<td>预投</td>
    		<td>1部</td>
    		<td>2部</td>
    		<td>3部</td>
    		<td>4部</td>
    		<td>5部</td>
    		<td>6部</td>
    		<td>9部</td>
    		<td>装备部</td>
    		<td>项目主管</td>
    	</tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>			
			<tr align="center">
            	<td>&nbsp;<%=row.optString("ORDERCODE") %></td>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("LEADER_STATION") %></td>
				<td>&nbsp;<%=row.optString("LEADER_TOP") %></td>
				<td>&nbsp;<%=row.optString("BUDGET_FUNDS") %></td>
				<td>&nbsp;<%=row.optString("TYPE") %></td>
				<td>&nbsp;<%=row.optString("AMOUNT") %></td>
				<td>&nbsp;<%=row.optString("PLAN_NODE") %></td>
				<td>&nbsp;<%=row.optString("BUDGET_INCREASE") %></td>
				<td>&nbsp;<%=row.optString("FUNDS1") %></td>
				<td>&nbsp;<%=row.optString("FUNDS2") %></td>
				<td>&nbsp;<%=row.optString("FUNDS3") %></td>
				<td>&nbsp;<%=row.optString("FUNDS4") %></td>
				<td>&nbsp;<%=row.optString("PREFUNDS") %></td>
				<td>&nbsp;<%=row.optString("DEPART1") %></td>
				<td>&nbsp;<%=row.optString("DEPART2") %></td>
				<td>&nbsp;<%=row.optString("DEPART3") %></td>
				<td>&nbsp;<%=row.optString("DEPART4") %></td>
				<td>&nbsp;<%=row.optString("DEPART5") %></td>
				<td>&nbsp;<%=row.optString("DEPART6") %></td>
				<td>&nbsp;<%=row.optString("DEPART9") %></td>
				<td>&nbsp;<%=row.optString("DEPART10") %></td>
				<td>&nbsp;<%=row.optString("MANAGER") %></td>
<%
		}
%>				
</table>
</form>
			</div>
		</div>
	</body>
</html>