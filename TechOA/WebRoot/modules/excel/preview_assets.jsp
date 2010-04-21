<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.assets.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String status = request.getAttribute("status").toString();
String depart = request.getAttribute("depart").toString();
String emp = request.getAttribute("emp").toString();

JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
AssetsDAO assetsDAO = (AssetsDAO)ctx.getBean("assetsDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>固定资产导入预览</title>
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
    	history.back(-1);
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=assets.do?action=list_manage&table=ASSETS';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>固定资产导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="data" id="data" value='<%=data %>'>
<input type="hidden" name="status" value="<%=status %>">
<input type="hidden" name="depart" value="<%=depart %>">
<input type="hidden" name="emp" value="<%=emp %>">
<br>&nbsp;&nbsp;&nbsp;&nbsp;注：橙色行表示已存在此设备，将不予入库；黄色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>设备编号</td>
  	  			<td>设备名称</td>
  	  			<td>设备型号</td>
  	  			<td>购买日期</td>
  	  			<td>出厂日期</td>
    			<td>使用年限</td>
    			<td>购买价格</td>
    			<td>状态</td>
    			<td>领用人</td>
    			<td>领用时间</td>
    			<td>上次年检时间</td>
    			<td>年检间隔</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			Map map = assetsDAO.findByCode("ASSETS", row.optString("CODE"));
			if(map.get("CODE")!=null){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此设备！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>
            	<td>&nbsp;<%=row.optString("CODE") %></td>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("MODEL") %></td>
            	<td>&nbsp;<%=row.optString("BUYDATE") %></td>
				<td>&nbsp;<%=row.optString("PRODUCEDATE") %></td>
				<td>&nbsp;<%=row.optString("LIFE") %></td>
				<td>&nbsp;<%=row.optString("BUYCOST") %></td>
				<td>&nbsp;<%=row.optString("STATUS") %></td>
<%
			String empcode = assetsDAO.findCodeByName("EMPLOYEE", row.optString("EMPNAME"));
			if("".equals(empcode)){
%>            	
            	<td bgcolor="yellow" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("EMPNAME") %></td>
<%
			}
%>
				<td>&nbsp;<%=row.optString("LENDDATE") %></td>
				<td>&nbsp;<%=row.optString("CHECKDATE") %></td>
				<td>&nbsp;<%=row.optString("CHECKYEAR") %></td>
<%
		}
%>				
</table>
</form>
			</div>
		</div>
	</body>
</html>