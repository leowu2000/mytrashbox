<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.basesoft.core.*" %>
<%@ page import="com.basesoft.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.basesoft.modules.project.*" %>
<%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>
<%
JSONObject data = (JSONObject)request.getAttribute("data");
JSONArray rows = data.optJSONArray("row");
String path = request.getAttribute("path").toString();

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ProjectDAO projectDAO = (ProjectDAO)ctx.getBean("projectDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>工作令导入预览</title>
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
    	window.location.href = 'pj.do?action=list';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=pj.do?action=list&table=PROJECT';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>工作令导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此工作令号，将不予入库；红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>工作令号</td>              
                <td>工作令负责人</td>
                <td>计划工作量</td>
                <td>开始时间</td>
                <td>截止时间</td>
                <td>描述</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			String pjcode = projectDAO.findCodeByName("PROJECT", row.optString("PJNAME"));
			if(!"".equals(pjcode)){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此工作令号！">
<%
			}else {
%>
			<tr align="center">
<%
			}
%>            
            	<td>&nbsp;<%=row.optString("PJNAME") %></td>
<%
			String managercode = projectDAO.findCodeByName("EMPLOYEE", row.optString("MANAGERNAME"));
			if("".equals(managercode)){
%>            	
            	<td bgcolor="#FF0088" title="系统无法识别此负责人！">&nbsp;<%=row.optString("MANAGERNAME") %></td>
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("MANAGERNAME") %></td>
<%
			}
%>
            	<td>&nbsp;<%=row.optString("PLANEDWORKLOAD") %></td>
				<td>&nbsp;<%=row.optString("STARTDATE") %></td>
				<td>&nbsp;<%=row.optString("ENDDATE") %></td>
				<td>&nbsp;<%=row.optString("NOTE") %></td>
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