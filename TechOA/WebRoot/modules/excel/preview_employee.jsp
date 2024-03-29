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
		<title>员工导入预览</title>
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
    	window.location.href = 'em.do?action=infolist';
    }
    
    function onImportClick(){
    	document.getElementById('listForm').action = 'excel.do?action=import&redirect=em.do?action=infolist&table=EMPLOYEE';
    	document.getElementById('listForm').submit();
    }
});
</script>
	</head>
	<body>
	<h1>员工导入预览</h1>
	<div id="toolbar"></div>
		<div id="tabs1">
			<div id="main" class="tab-content">
<form id="listForm" name="listForm" action="" method="post">
<input type="hidden" name="path" id="path" value='<%=path %>'>
<br>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0088">注：橙色行表示已存在此用户，将不予入库；红色格子表示入库后将无法进行关联，无法参与统计，可返回修改excel表，或入库后在相应管理模块下编辑！</font>
<table cellspacing="0" id="the-table" width="98%" align="center">
            <tr align="center" bgcolor="#E0F1F8" class="b_tr">
                <td>姓名</td>              
                <td>人员编号</td>
                <td>部门</td>
                <td>主岗</td>
                <td>副岗</td>
                <td>职务级别</td>
                <td>电子邮件</td>
                <td>博客链接</td>
                <td>个人网页</td>
                <td>固定电话</td>
                <td>手机号码</td>
                <td>家庭住址</td>
                <td>邮政编码</td>
                <td>专业</td>
                <td>学历</td>
            </tr>
<%
		for(int i=0;i<rows.length();i++){
			JSONObject row = rows.optJSONObject(i);
			Map map = emDAO.findByCode("EMPLOYEE", row.optString("CODE"));
			if(map.get("CODE")!=null){
%>            
			<tr align="center" bgcolor="orange" title="系统中已存在此用户！">
<%
			}else {
				boolean exist = false;
				for(int j=0;j<rows.length();j++){
					JSONObject row_check = rows.optJSONObject(j);
					if(row.optString("CODE").trim().equals(row_check.optString("CODE").trim())&&i > j){
						exist = true;
						break;
					}
				}
				if(exist){
%>
			<tr align="center" bgcolor="orange" title="重复的用户编码！">
<%
				}else {
%>
			<tr align="center">
<%					
				}
			}
%>
            	<td>&nbsp;<%=row.optString("NAME") %></td>
            	<td>&nbsp;<%=row.optString("CODE") %></td>
<%
			String departcode = emDAO.findCodeByName("DEPARTMENT", row.optString("DEPARTNAME"));
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
            	<td>&nbsp;<%=row.optString("MAINJOB") %></td>
            	<td>&nbsp;<%=row.optString("SECJOB") %></td>
            	<td>&nbsp;<%=row.optString("LEVEL") %></td>
            	<td>&nbsp;<%=row.optString("EMAIL") %></td>
            	<td>&nbsp;<%=row.optString("BLOG") %></td>
            	<td>&nbsp;<%=row.optString("SELFWEB") %></td>
            	<td>&nbsp;<%=row.optString("STCPHONE") %></td>
            	<td>&nbsp;<%=row.optString("MOBPHONE") %></td>
            	<td>&nbsp;<%=row.optString("ADDRESS") %></td>
            	<td>&nbsp;<%=row.optString("POST") %></td>
<%
			String major = emDAO.findCodeByName("DICT", row.optString("MAJOR"));
			if("".equals(major)){
%>            	
            	<td bgcolor="#FF0088" title="无法识别此专业！">&nbsp;<%=row.optString("MAJOR") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("MAJOR") %></td>
<%
			}
%>      
<%
			String degree = emDAO.findCodeByName("DICT", row.optString("DEGREE"));
			if("".equals(degree)){
%>            	
            	<td bgcolor="#FF0088" title="无法识别此学历！">&nbsp;<%=row.optString("DEGREE") %></td>
            	
<%
			}else {
%>            	
				<td>&nbsp;<%=row.optString("DEGREE") %></td>
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