<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.employee.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
String seldepart = request.getAttribute("seldepart").toString();
String emname = request.getAttribute("emname").toString();
String sel_empcode = request.getAttribute("sel_empcode").toString();

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
		<title>一卡通信息导入预览</title>
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
    	window.location.href = 'card.do?action=list_manage&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=card.do?action=list_manage&table=EMP_CARD&seldepart=<%=seldepart %>&emname=<%=emname %>&sel_empcode=<%=sel_empcode %>';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>一卡通信息导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此卡号，将不予入库；红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>员工编号</td>
                <td>员工姓名</td>  
                <td>性别</td>
                <td>卡号</td>
                <td>电话1</td>
                <td>电话2</td>
                <td>地址</td>
                <td>部门名称</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			String haveCardno = cardDAO.haveCard(row.optString("CARDNO"), row.optString("EMPCODE"));
			if(!"false".equals(haveCardno)){
%>            
            <tr align="center" bgcolor="orange" title="系统中已存在此卡号！">
<%
			}else {
%>
			<tr align="center">
<%
			}
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
%>
            	<td>&nbsp;<%=row.optString("SEX") %></td>
            	<td>&nbsp;<%=row.optString("CARDNO") %></td>
            	<td>&nbsp;<%=row.optString("PHONE1") %></td>
            	<td>&nbsp;<%=row.optString("PHONE2") %></td>
            	<td>&nbsp;<%=row.optString("ADDRESS") %></td>
<%
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