<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
CarDAO carDAO = (CarDAO)ctx.getBean("carDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>班车信息导入预览</title>
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
    	window.location.href = 'car.do?action=list_manage';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=car.do?action=list_manage&table=CAR';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>班车信息导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="data" id="data" value='<%=data %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此班车，将不予入库！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>班车编号</td>
    			<td>班车车牌号</td>
    			<td>班车路线</td>
    			<td>司机姓名</td>
    			<td>司机电话</td>
    			<td>发车地点</td>
            	</tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			boolean haveCar = carDAO.haveCar(row.optString("CARCODE"));
			if(haveCar){
%>            
            <tr align="center" bgcolor="orange" title="系统中已存在此班车！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>			
            	<td>&nbsp;<%=row.optString("CARCODE") %></td>
            	<td>&nbsp;<%=row.optString("CARNO") %></td>
            	<td>&nbsp;<%=row.optString("WAY") %></td>
            	<td>&nbsp;<%=row.optString("DRIVERNAME") %></td>
            	<td>&nbsp;<%=row.optString("DRIVERPHONE") %></td>
            	<td>&nbsp;<%=row.optString("SENDLOCATE") %></td>
            </tr>
<%
		}
%>            
</table>
</form>
			</div>
		</div>
	</body>
</html>