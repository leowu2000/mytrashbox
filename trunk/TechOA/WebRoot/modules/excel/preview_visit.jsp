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
EmployeeDAO emDAO = (EmployeeDAO)ctx.getBean("employeeDAO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>系统访问工号导入预览</title>
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
    	window.location.href = 'visit.do?action=list';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=visit.do?action=list&table=VISIT';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>访问工号预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
  	  			<td>工号</td>
  	  			<td>IP</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
%>            
			<tr align="center">
<%
			String empname = emDAO.findNameByCode("EMPLOYEE", row.optString("EMPCODE"));
			if("".equals(empname)){
%>            	
            	<td bgcolor="#FF0088" title="系统无法识别此员工！">&nbsp;<%=row.optString("EMPCODE") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("EMPCODE") %></td>
				<td>&nbsp;<%=row.optString("IP") %></td>
<%
			}
		}
%>			
			</tr>	
</table>
</form>
			</div>
		</div>
	</body>
</html>