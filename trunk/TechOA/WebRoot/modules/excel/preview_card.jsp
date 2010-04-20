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

JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");

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
    	history.back(-1);
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=card.do?action=list_manage&table=EMP_CARD&seldepart=<%=seldepart %>&emname=<%=emname %>';
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
<input type="hidden" name="data" id="data" value='<%=data %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;注：红色格子表示入库后将无法进行关联，无法参与统计汇总。可返回修改excel表，或者保存入库后在相应管理模块下重新编辑选择！
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
%>            
            <tr align="center">
<%
			String empname = cardDAO.findNameByCode("EMPLOYEE", row.optString("EMPCODE"));
			if("".equals(empname)){
%>            	
            	<td bgcolor="red" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPCODE") %></td>
            	<td bgcolor="red" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPNAME") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("EMPCODE") %></td>
<%
				if(!empname.equals(row.optString("EMPNAME"))){//姓名跟系统中姓名不对应
%>				
				<td bgcolor="red" title="姓名跟系统中姓名不对应！">&nbsp;<%=row.optString("EMPNAME") %></td>
				
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
				<td bgcolor="red" title="系统无法识别此部门！">&nbsp;<%=row.optString("DEPARTNAME") %></td>
	
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