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
String path = request.getAttribute("path").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
CardDAO cardDAO = (CardDAO)ctx.getBean("cardDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>班车刷卡信息导入预览</title>
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
    	window.location.href = 'pos.do?action=list_manage';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=pos.do?action=list_manage&table=EMP_POS';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>班车刷卡导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>刷卡时间</td>
    			<td>人员编号</td>
    			<td>姓名</td>
    			<td>部门</td>
    			<td>车载POS机</td>
    			<td>金额</td>
    			<td>POS流水号</td>
    			<td>卡号</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>            
            <tr align="center">
            	<td>&nbsp;<%=row.optString("SWIPETIME") %></td>
<%
			String empname = cardDAO.findNameByCode("EMPLOYEE", row.optString("EMPCODE"));
			if("".equals(empname)){
%>            	
            	<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPCODE") %></td>
            	<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("EMPCODE") %></td>
<%
				if(!empname.equals(row.optString("EMPNAME"))){//姓名跟系统中姓名不对应
%>				
				<td bgcolor="#FF0088" title="姓名跟系统中姓名不对应！">&nbsp;<%=row.optString("EMPNAME") %></td>
				
<%
				}else {
%>
				<td>&nbsp;<%=row.optString("EMPNAME") %></td>
<%				
				}
			}
			String departcode = cardDAO.findCodeByName("DEPARTMENT", row.optString("DEPARTNAME"));
			if("".equals(departcode)){
%>            	
				<td bgcolor="#FF0088" title="系统无法识别此部门！">&nbsp;<%=row.optString("DEPARTNAME") %></td>
	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("DEPARTNAME") %></td>
<%
			}
%>
            	<td>&nbsp;<%=row.optString("POSMACHINE") %></td>
            	<td>&nbsp;<%=row.optString("COST") %></td>
            	<td>&nbsp;<%=row.optString("POSCODE") %></td>
<%
			String cardno = cardDAO.getCardnoByEmpcode(row.optString("EMPCODE"));
			if(!cardno.equals(row.optString("CARDNO"))){
%>            	
				<td bgcolor="#FF0088" title="卡号和系统中此人的卡号不一致！">&nbsp;<%=row.optString("CARDNO") %></td>
	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("CARDNO") %></td>
<%
			}
%>
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